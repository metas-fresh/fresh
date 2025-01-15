package de.metas.payment.sumup.rest_api;

import de.metas.Profiles;
import de.metas.payment.sumup.SumUp;
import de.metas.payment.sumup.SumUpClientTransactionId;
import de.metas.payment.sumup.SumUpService;
import de.metas.util.web.security.UserAuthTokenFilterConfiguration;
import lombok.NonNull;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(SumUp.ENDPOINT_PaymentCheckoutCallback)
@RestController
@Profile(Profiles.PROFILE_App)
public class SumUpCallbackRestController
{
	@NonNull private final SumUpService sumUpService;

	public SumUpCallbackRestController(
			@NonNull final UserAuthTokenFilterConfiguration userAuthTokenFilterConfiguration,
			@NonNull final SumUpService sumUpService)
	{
		this.sumUpService = sumUpService;

		userAuthTokenFilterConfiguration.doNotAuthenticatePathsContaining(SumUp.ENDPOINT_PaymentCheckoutCallback);
	}

	@PostMapping
	public void readerCheckoutCallback(@RequestBody @NonNull final JsonReaderCheckoutCallbackRequest request)
	{
		final SumUpClientTransactionId trxId = request.getPayload().getClient_transaction_id();
		sumUpService.updateTransactionFromRemote(trxId);
	}
}
