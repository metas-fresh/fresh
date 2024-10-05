package de.metas.payment.sumup;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.metas.currency.Amount;
import de.metas.organization.ClientAndOrgId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.time.Instant;

@Value
public class SumUpTransaction
{
	@NonNull SumUpConfigId configId;
	@NonNull SumUpTransactionExternalId externalId;
	@NonNull SumUpClientTransactionId clientTransactionId;
	@NonNull SumUpMerchantCode merchantCode;
	@NonNull Instant timestamp;

	@NonNull SumUpTransactionStatus status;
	@NonNull Amount amount;
	@NonNull Amount amountRefunded;

	@Nullable String json;

	@NonNull ClientAndOrgId clientAndOrgId;
	@Nullable SumUpPOSRef posRef;

	@Builder(toBuilder = true)
	@Jacksonized
	private SumUpTransaction(
			@NonNull final SumUpConfigId configId,
			@NonNull final SumUpTransactionExternalId externalId,
			@NonNull final SumUpClientTransactionId clientTransactionId,
			@NonNull final SumUpMerchantCode merchantCode,
			@NonNull final Instant timestamp,
			@NonNull final SumUpTransactionStatus status,
			@NonNull final Amount amount,
			@Nullable final Amount amountRefunded,
			@Nullable final String json,
			@NonNull final ClientAndOrgId clientAndOrgId,
			@Nullable final SumUpPOSRef posRef)
	{
		this.configId = configId;
		this.externalId = externalId;
		this.clientTransactionId = clientTransactionId;
		this.merchantCode = merchantCode;
		this.timestamp = timestamp;
		this.status = status;
		this.amount = amount;
		this.amountRefunded = amountRefunded != null ? amountRefunded : Amount.zero(amount.getCurrencyCode());
		this.json = json;
		this.clientAndOrgId = clientAndOrgId;
		this.posRef = posRef;

		Amount.assertSameCurrency(this.amount, this.amountRefunded);
	}

	@JsonIgnore
	public boolean isRefunded()
	{
		return amount.signum() != 0 && amountRefunded.signum() != 0;
	}
}
