package de.metas.banking.process;

import de.metas.banking.PaySelectionId;
import de.metas.banking.payment.IPaySelectionBL;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Services;
import org.compiere.model.I_C_PaySelection;
import org.compiere.model.I_C_PaySelectionLine;

import java.util.Optional;

/**
 * Creates an links payments to {@link I_C_PaySelectionLine}s.
 *
 * @author tsa
 *
 */
public class C_PaySelection_CreatePayments extends JavaProcess implements IProcessPrecondition
{
	// services
	private final transient IPaySelectionBL paySelectionBL = Services.get(IPaySelectionBL.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		if(!context.isSingleSelection())
		{
			ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		final Optional<I_C_PaySelection> paySelection = paySelectionBL.getById(PaySelectionId.ofRepoId(context.getSingleSelectedRecordId()));
		if (paySelection.isEmpty() || !paySelection.get().isProcessed())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("not processed");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final I_C_PaySelection paySelection = paySelectionBL.getByIdNotNull(PaySelectionId.ofRepoId(getRecord_ID()));
		paySelectionBL.createPayments(paySelection);
		return MSG_OK;
	}
}
