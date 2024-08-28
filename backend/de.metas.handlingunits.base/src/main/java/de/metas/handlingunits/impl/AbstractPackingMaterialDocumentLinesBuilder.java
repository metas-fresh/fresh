package de.metas.handlingunits.impl;

import java.util.Collection;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.OverridingMethodsMustInvokeSuper;

import ch.qos.logback.classic.Level;
import de.metas.logging.LogManager;
import de.metas.util.Loggables;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;

import de.metas.handlingunits.IPackingMaterialDocumentLine;
import de.metas.handlingunits.IPackingMaterialDocumentLineSource;
import de.metas.handlingunits.model.I_M_HU_PackingMaterial;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.slf4j.Logger;

/**
 * Base implementation for {@link IPackingMaterialDocumentLinesBuilder}.
 *
 * @author tsa
 */
public abstract class AbstractPackingMaterialDocumentLinesBuilder implements IPackingMaterialDocumentLinesBuilder
{
	private static final Logger logger = LogManager.getLogger(AbstractPackingMaterialDocumentLinesBuilder.class);
	
	/**
	 * Map of "packing material key" to {@link IPackingMaterialDocumentLine}.
	 * <p>
	 * For creating packing material key, see {@link #createPackingMaterialKey(ProductId)}.
	 */
	private final Map<ArrayKey, IPackingMaterialDocumentLine> packingMaterialKey2packingMaterialLine = new HashMap<>();

	/**
	 * Set of sources which have no packing materials.
	 * <p>
	 * We will use this set to be able to unlink those sources from existing packing material lines (if any).
	 */
	private final Set<IPackingMaterialDocumentLineSource> sourcesWithoutPackingMaterials = new HashSet<>();

	@Override
	public boolean isEmpty()
	{
		// If we already have packing material lines, consider it not empty
		return packingMaterialKey2packingMaterialLine.isEmpty();
	}

	/**
	 * Adds an existing {@link IPackingMaterialDocumentLine} to our builder to be considered on subsequent {@link #addSource(IPackingMaterialDocumentLineSource)} calls.
	 *
	 * @param line packing material document line
	 */
	protected final void addPackingMaterialDocumentLine(@NonNull final IPackingMaterialDocumentLine line)
	{
		final ArrayKey pmKey = createPackingMaterialKey(line.getProductId());
		final IPackingMaterialDocumentLine lineExisting = packingMaterialKey2packingMaterialLine.put(pmKey, line);
		if (lineExisting != null)
		{
			final String productName = Services.get(IProductBL.class).getProductValueAndName(line.getProductId());
			throw new AdempiereException("An packing material line was already added for product " + productName);
		}
	}

	private ArrayKey createPackingMaterialKey(@NonNull final ProductId productId)
	{
		return Util.mkKey(productId);
	}

	@Override
	public final void addSource(@NonNull final IPackingMaterialDocumentLineSource source)
	{
		assertValid(source);

		final List<I_M_HU_PackingMaterial> packingMaterials = source.getM_HU_PackingMaterials();
		if (packingMaterials == null || packingMaterials.isEmpty())
		{
			// If our source has no packing materials, remember it,
			// and later, when we are creating the document lines we will call "linkSourceDocumentLine()" method
			// to unlink the source from an existing packing material document line
			sourcesWithoutPackingMaterials.add(source);
		}
		else
		{
			for (final I_M_HU_PackingMaterial packingMaterial : packingMaterials)
			{
				if (packingMaterial.getM_Product_ID() <= 0)
				{
					Loggables.withLogger(logger, Level.WARN).addLog("M_HU_PackingMaterial_ID={}, Name={} has no M_Product_ID; skipping", packingMaterial.getM_HU_PackingMaterial_ID(), packingMaterial.getName());
					continue;
				}
				final IPackingMaterialDocumentLine packingMaterialLine = getCreatePackingMaterialDocumentLine(packingMaterial);
				packingMaterialLine.addSourceOrderLine(source);
			}
		}
	}

	public final void addSources(final Collection<IPackingMaterialDocumentLineSource> sources)
	{
		if(sources == null || sources.isEmpty())
		{
			return;
		}

		sources.forEach(this::addSource);
	}


	private IPackingMaterialDocumentLine getCreatePackingMaterialDocumentLine(@NonNull final I_M_HU_PackingMaterial packingMaterial)
	{
		final ProductId productId = ProductId.ofRepoId(packingMaterial.getM_Product_ID());
		final ArrayKey pmKey = createPackingMaterialKey(productId);

		//
		// Check if we already have a packing material line
		final IPackingMaterialDocumentLine pmLineExisting = packingMaterialKey2packingMaterialLine.get(pmKey);
		if (pmLineExisting != null)
		{
			return pmLineExisting;
		}

		//
		// Packing material order line was not found => Create New
		final IPackingMaterialDocumentLine pmLineNew = createPackingMaterialDocumentLine(packingMaterial);

		// NOTE: we are not saving here, but later
		addPackingMaterialDocumentLine(pmLineNew);

		return pmLineNew;
	}

	/**
	 * Create {@link IPackingMaterialDocumentLine} from given {@link I_M_HU_PackingMaterial}.
	 * <p>
	 * NOTE: when implementing this method, if there are some underlying database records, please don't save it because you will be asked to save them when needed (see
	 * {@link #createDocumentLine(IPackingMaterialDocumentLine)}).
	 *
	 * @return packing material document line.
	 */
	protected abstract IPackingMaterialDocumentLine createPackingMaterialDocumentLine(final I_M_HU_PackingMaterial packingMaterial);

	@Override
	@OverridingMethodsMustInvokeSuper
	public AbstractPackingMaterialDocumentLinesBuilder create()
	{
		//
		// Process all packing material lines
		for (final IPackingMaterialDocumentLine pmLine : packingMaterialKey2packingMaterialLine.values())
		{
			createUpdateDeleteDocumentLineAndUpdateSources(pmLine);
		}

		//
		// Iterate those sources without packing materials
		// and make sure they are not linked to some packing material lines (i.e. unlink them)
		for (final IPackingMaterialDocumentLineSource source : sourcesWithoutPackingMaterials)
		{
			final IPackingMaterialDocumentLine pmLine = null; // no packing material line
			linkSourceToDocumentLine(source, pmLine);
		}

		return this;
	}

	/**
	 * Create/update <b>or delete</b> the given packing material line.
	 * <p>
	 * Also, the linked source lines will be updated.
	 */
	private void createUpdateDeleteDocumentLineAndUpdateSources(final IPackingMaterialDocumentLine pmLine)
	{
		final IPackingMaterialDocumentLine pmLineToLink;

		// If there is no qty on current packing material order line, just delete it
		if (pmLine.getQty().signum() <= 0)
		{
			removeDocumentLine(pmLine);
			pmLineToLink = null; // we need to unlink our sources
		}
		// We have packing material qty => create the actual line
		else
		{
			createDocumentLine(pmLine);
			pmLineToLink = pmLine;
		}

		//
		// Update the link between "source" to "packing material line"
		for (final IPackingMaterialDocumentLineSource source : pmLine.getSources())
		{
			linkSourceToDocumentLine(source, pmLineToLink);
		}
	}

	/**
	 * Makes sure the given is <code>source</code> accepted by this builder
	 */
	protected abstract void assertValid(final IPackingMaterialDocumentLineSource source);

	/**
	 * Called when we need to delete the given packing material line.
	 * <p>
	 * NOTE: Here is the place to delete your underlying database models.
	 *
	 * @param pmLine packing material line to delete
	 */
	protected abstract void removeDocumentLine(final IPackingMaterialDocumentLine pmLine);

	/**
	 * Called when we need to create/update the given packing material line.
	 * <p>
	 * NOTE: Here is the place to save your underlying database models.
	 *
	 * @param pmLine packing material line to create/update
	 */
	protected abstract void createDocumentLine(final IPackingMaterialDocumentLine pmLine);

	/**
	 * Called when we need to create a link from source line to packing material line.
	 * <p>
	 * NOTE: please make sure you are saving your database changes.
	 *
	 * @param pmLine packing material line; could be <code>null</code> in case we want to unlink any packing material lines from given <code>source</code>.
	 */
	protected abstract void linkSourceToDocumentLine(final IPackingMaterialDocumentLineSource source, final IPackingMaterialDocumentLine pmLine);
}
