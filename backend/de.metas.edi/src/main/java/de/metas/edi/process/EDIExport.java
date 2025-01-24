package de.metas.edi.process;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/*
 * #%L
 * de.metas.edi
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.List;

import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.ProcessPreconditionsResolution;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import de.metas.edi.api.IEDIDocumentBL;
import de.metas.edi.model.I_EDI_Document;
import de.metas.edi.process.export.IExport;
import de.metas.i18n.IMsgBL;
import de.metas.process.JavaProcess;
import de.metas.util.Services;

/**
 * EDI-Exports a single EDI document. Locally and synchronously, i.e. without an async-workpackage.
 * If there is a validation error, then it updates the record with the error message(s) as well.
 */
public class EDIExport extends JavaProcess implements IProcessPrecondition
{
	private int recordId = -1;

	// Services
	private final IEDIDocumentBL ediDocumentBL = Services.get(IEDIDocumentBL.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}
		return ProcessPreconditionsResolution.accept();
	}
	
	@Override
	protected void prepare()
	{
		recordId = getRecord_ID();
	}

	@Override
	protected String doIt()
	{
		final IExport<? extends I_EDI_Document> export = ediDocumentBL.createExport(
				getCtx(),
				getClientID(),
				getTable_ID(),
				recordId,
				get_TrxName());
		final List<Exception> feedback = export.doExport();
		if (feedback == null || feedback.isEmpty())
		{
			return MSG_OK;
		}

		final String errorTitle = buildAndTrlTitle(export.getTableIdentifier(), export.getDocument());
		final String errorMessage = ediDocumentBL.buildFeedback(feedback);

		final I_EDI_Document document = export.getDocument();
		document.setEDIErrorMsg(errorMessage);
		saveRecord(document);

		throw new AdempiereException(errorTitle + "\n" + errorMessage).markAsUserValidationError();
	}

	private String buildAndTrlTitle(final String tableNameIdentifier, final I_EDI_Document document)
	{
		return Services.get(IMsgBL.class).parseTranslation(getCtx(), "@" + tableNameIdentifier + "@ " + document.getDocumentNo());
	}
}
