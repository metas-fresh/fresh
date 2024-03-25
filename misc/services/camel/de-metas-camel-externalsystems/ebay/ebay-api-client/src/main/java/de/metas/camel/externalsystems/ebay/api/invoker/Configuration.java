/*
 * Fulfillment API
 * Use the Fulfillment API to complete the process of packaging, addressing, handling, and shipping each order on behalf of the seller, in accordance with the payment method and timing specified at checkout.
 *
 * The version of the OpenAPI document: v1.19.7
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package de.metas.camel.externalsystems.ebay.api.invoker;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-07-20T14:45:55.981728+02:00[Europe/Berlin]")
public class Configuration
{
	private static ApiClient defaultApiClient = new ApiClient();

	/**
	 * Get the default API client, which would be used when creating API
	 * instances without providing an API client.
	 *
	 * @return Default API client
	 */
	public static ApiClient getDefaultApiClient()
	{
		return defaultApiClient;
	}

	/**
	 * Set the default API client, which would be used when creating API
	 * instances without providing an API client.
	 *
	 * @param apiClient API client
	 */
	public static void setDefaultApiClient(ApiClient apiClient)
	{
		defaultApiClient = apiClient;
	}
}