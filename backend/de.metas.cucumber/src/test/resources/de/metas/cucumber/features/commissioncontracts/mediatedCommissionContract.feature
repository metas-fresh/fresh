@from:cucumber
@topic:commissionContracts
Feature: Mediated commission

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has current date and time
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

  @ignore # https://github.com/metasfresh/metasfresh/issues/19699
  @from:cucumber
  @topic:commissionContracts
  Scenario: Happy flow for mediated commission contract
    Given taxCategory 'Normal' is updated to work with all productTypes
    And metasfresh contains M_Products:
      | Identifier          | Name                | ProductType | OPT.X12DE355 |
      | commission_product  | commission_product  | S           | PTS          |
      | transaction_product | transaction_product |             | PCE          |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                    | Value            | OPT.IsActive |
      | psv_1      | pricing_system_vendor_1 | pricing_system_1 | true         |
    And metasfresh contains M_PriceLists
      | Identifier   | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                 | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_vendor_po | psv_1                         | DE                        | EUR                 | price_list_vendor_po | false | false         | 2              | true         |
      | pl_vendor_so | psv_1                         | DE                        | EUR                 | price_list_vendor_so | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name         | ValidFrom  |
      | plv_pl_po  | pl_vendor_po              | pl_vendor_po | 2021-11-01 |
      | plv_pl_so  | pl_vendor_so              | pl_vendor_so | 2021-11-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_pl_so                         | commission_product      | 1.0      | PTS               | Normal                        |
      | pp_4       | plv_pl_po                         | transaction_product     | 20.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier      | OPT.C_BPartner_Location_ID.Identifier | Name            | M_PricingSystem_ID.Identifier | OPT.IsVendor | OPT.IsCustomer | OPT.IsSalesRep | OPT.C_PaymentTerm_ID | OPT.CompanyName     | OPT.GLN       |
      | mediated_vendor | mediated_vendor_location              | mediated_vendor | psv_1                         | Y            | Y              | Y              | 1000009              | mediated_vendor cmp | 1234567891236 |
    And metasfresh contains C_MediatedCommissionSettings:
      | C_MediatedCommissionSettings_ID.Identifier | Name       | Commission_Product_ID.Identifier | PointsPrecision |
      | mediatedSettings_1                         | mediated_1 | commission_product               | 2               |
    And metasfresh contains C_MediatedCommissionSettingsLine:
      | C_MediatedCommissionSettingsLine_ID.Identifier | C_MediatedCommissionSettings_ID.Identifier | SeqNo | PercentOfBasePoints |
      | mediatedSettingsLine_1                         | mediatedSettings_1                         | 10    | 50                  |
    And metasfresh contains C_Flatrate_Conditions:
      | C_Flatrate_Conditions_ID.Identifier | Name          | Type_Conditions    | OPT.C_MediatedCommissionSettings_ID.Identifier |
      | mediatedConditions_1                | mediated-test | MediatedCommission | mediatedSettings_1                             |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier         | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.NrOfDaysFromNow |
      | mediatedContract_1 | mediatedConditions_1                | mediated_vendor             | commission_product          | 365                 |
    When a 'POST' request with the below payload is sent to the metasfresh REST-API '/api/v2/order/purchase/createCandidates' and fulfills with '200' status code
  """
{
   "purchaseCandidates":[
      {
         "orgCode":"001",
         "productIdentifier":"val-transaction_product",
         "vendor":{
            "bpartnerIdentifier":"val-mediated_vendor",
            "locationIdentifier":"gln-1234567891236"
         },
         "warehouseIdentifier":"540008",
         "externalPurchaseOrderUrl": "www.ExternalReferenceURL.com",
         "externalHeaderId":"99898",
         "externalLineId":"898978",
         "poReference":"poRef1",
         "purchaseDatePromised":"2021-12-05T23:17:35.644Z",
         "purchaseDateOrdered":"2021-12-05T23:17:35.644Z",
         "qty":{
            "qty":1,
            "uomCode":"PCE"
         }
      }
   ]
}
"""
    And a 'POST' request with the below payload is sent to the metasfresh REST-API '/api/v2/order/purchase/enqueueForOrdering' and fulfills with '202' status code
"""
{
  "purchaseCandidates": [
    {
      "externalHeaderId": "99898",
      "externalLineIds": [
        "898978"
      ]
    }
  ]
}
"""
    Then a PurchaseOrder with externalId '99898' is created after not more than 90 seconds and has values
      | ExternalPurchaseOrderURL     | POReference | OPT.C_Order_ID.Identifier |
      | www.ExternalReferenceURL.com | poRef1      | purchaseOrder_1           |
    And after not more than 60s the order is found
      | C_Order_ID.Identifier | DocStatus |
      | purchaseOrder_1       | CO        |
    And perform document action
      | DocAction | C_Order_ID.Identifier |
      | RE        | purchaseOrder_1       |
    And after not more than 60s the order is found
      | C_Order_ID.Identifier | DocStatus |
      | purchaseOrder_1       | IP        |
    And update order
      | C_Order_ID.Identifier | OPT.DocBaseType | OPT.DocSubType |
      | purchaseOrder_1       | POO             | MED            |
    And perform document action
      | DocAction | C_Order_ID.Identifier |
      | CO        | purchaseOrder_1       |
    And after not more than 60s the order is found
      | C_Order_ID.Identifier | DocStatus |
      | purchaseOrder_1       | CO        |
    And validate created commission instance
      | C_Commission_Instance_ID.Identifier | C_Order_ID.Identifier | Bill_BPartner_ID.Identifier | M_Product_Order_ID.Identifier | PointsBase_Forecasted | PointsBase_Invoiceable | PointsBase_Invoiced |
      | commissionInstance_1                | purchaseOrder_1       | mediated_vendor             | transaction_product           | 0                     | 0                      | 20                  |
    And validate commission deed for commission instance commissionInstance_1
      | C_Commission_Share_ID.Identifier | C_BPartner_SalesRep_ID.Identifier | C_BPartner_Payer_ID.Identifier | C_Flatrate_Term_ID.Identifier | Commission_Product_ID.Identifier | LevelHierarchy | OPT.C_MediatedCommissionSettingsLine.Identifier | IsSOTrx | IsSimulation | PointsSum_Forecasted | PointsSum_Invoiceable | PointsSum_Invoiced | PointsSum_ToSettle | PointsSum_Settled |
      | commissionShare_1                | metasfresh                        | mediated_vendor                | mediatedContract_1            | commission_product               | 0              | mediatedSettingsLine_1                          | true    | false        | 0                    | 0                     | 10.00              | 10.00              | 0                 |
    And validate commission fact for commissionShare_1
      | OPT.C_Invoice_Candidate_Commission_ID.Identifier | CommissionPoints | Commission_Fact_State |
      | settlement_1                                     | 10.00            | TO_SETTLE             |
      |                                                  | 10.00            | INVOICED              |
    And validate invoice candidate
      | C_Invoice_Candidate_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.NetAmtToInvoice | OPT.IsSOTrx |
      | settlement_1                      | mediated_vendor                 | commission_product          | 10                  | true        |
    And process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | settlement_1                      |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoiceSettled_1        | settlement_1                      |
    And recompute invoice candidates if required
      | C_Invoice_Candidate_ID.Identifier | Bill_BPartner_ID.Identifier | M_Product_ID.Identifier | OPT.NetAmtInvoiced |
      | settlement_1                      | mediated_vendor             | commission_product      | 10                 |
    And validate invoice candidate
      | C_Invoice_Candidate_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.NetAmtToInvoice | OPT.IsSOTrx | OPT.NetAmtInvoiced |
      | settlement_1                      | mediated_vendor                 | commission_product          | 0                   | true        | 10                 |
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm | processed | docStatus | OPT.DocSubType |
      | invoiceSettled_1        | mediated_vendor          | mediated_vendor_location          | 10 Tage 1 % | true      | CO        | RD             |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | qtyinvoiced | processed |
      | invoiceLineSettled_1        | invoiceSettled_1        | commission_product      | 10.00       | true      |
    And validate commission deed for commission instance commissionInstance_1
      | C_Commission_Share_ID.Identifier | C_BPartner_SalesRep_ID.Identifier | C_BPartner_Payer_ID.Identifier | C_Flatrate_Term_ID.Identifier | Commission_Product_ID.Identifier | LevelHierarchy | OPT.C_MediatedCommissionSettingsLine_ID.Identifier | IsSOTrx | IsSimulation | PointsSum_Forecasted | PointsSum_Invoiceable | PointsSum_Invoiced | PointsSum_ToSettle | PointsSum_Settled |
      | commissionShare_1                | metasfresh                        | mediated_vendor                | mediatedContract_1            | commission_product               | 0              | mediatedSettingsLine_1                             | true    | false        | 0                    | 0                     | 10.00              | 0                  | 10.00             |
    And validate commission fact for commissionShare_1
      | OPT.C_Invoice_Candidate_Commission_ID.Identifier | CommissionPoints | Commission_Fact_State |
      | settlement_1                                     | 10.00            | SETTLED               |
      | settlement_1                                     | -10.00           | TO_SETTLE             |
      | settlement_1                                     | 10.00            | TO_SETTLE             |
      |                                                  | 10.00            | INVOICED              |

