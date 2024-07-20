@from:cucumber
@ghActions:run_on_executor7
Feature: create distribution to balance demand

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2021-04-14T08:00:00+00:00
    And metasfresh contains M_Products:
      | Identifier |
      | p_1        |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx |
      | pl_1       | ps_1               | DE                    | EUR                 | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_1      | pl_1           |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | plv_1                  | p_1          | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier | IsVendor | IsCustomer | M_PricingSystem_ID |
      | bpartner_1 | N        | Y          | ps_1               |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN          | C_BPartner_ID.Identifier |
      | location_1 | bPLocation_1 | bpartner_1               |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID | C_BPartner_ID | C_BPartner_Location_ID | IsInTransit |
      | inTransit      | bpartner_1    | location_1             | true        |
      | sourceWH       | bpartner_1    | location_1             | false       |
      | targetWH       | bpartner_1    | location_1             | false       |
    And contains M_Shippers
      | Identifier |
      | shipper    |
    And metasfresh contains DD_NetworkDistribution
      | DD_NetworkDistribution_ID |
      | ddNetwork_1               |
    And metasfresh contains DD_NetworkDistributionLine
      | DD_NetworkDistribution_ID | M_Warehouse_ID | M_WarehouseSource_ID | M_Shipper_ID |
      | ddNetwork_1               | targetWH       | sourceWH             | shipper      |
    And metasfresh contains PP_Product_Plannings
      | M_Product_ID | IsCreatePlan | DD_NetworkDistribution_ID | M_Warehouse_ID |
      | p_1          | true         | ddNetwork_1               | targetWH       |


    
    
    



# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
  @from:cucumber
  @Id:S0171.300
  Scenario: One distribution candidate is created to balance the full demand of the sales order
    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.PreparationDate  | OPT.M_Warehouse_ID.Identifier |
      | SO         | true    | bpartner_1               | 2022-07-04  | 2022-07-04T00:00:00Z | targetWH                      |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | SO                    | p_1                     | 14         |
    And the order identified by SO is completed

    Then after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | Qty_AvailableToPromise | M_Warehouse_ID |
      | c_1        | DEMAND            | SHIPMENT                  | p_1          | 2022-07-04T00:00:00Z | -14 | -14                    | targetWH       |
      | c_2        | SUPPLY            | DISTRIBUTION              | p_1          | 2022-07-04T00:00:00Z | 14  | 0                      | targetWH       |
      | c_3        | DEMAND            | DISTRIBUTION              | p_1          | 2022-07-04T00:00:00Z | -14 | -14                    | sourceWH       |
      | c_4        | SUPPLY            |                           | p_1          | 2022-07-04T00:00:00Z | 14  | 0                      | sourceWH       |
    And after not more than 60s, following DD_Order_Candidates are found
      | Identifier | M_Product_ID | M_Warehouse_From_ID | M_WarehouseTo_ID | Qty | Processed |
      | c1         | p_1          | sourceWH            | targetWH         | 14  | N         |


    
    
    



# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
  @from:cucumber
  @Id:S0171.300
  Scenario: One distribution candidate is created to partially balance the demand of the sales order. The other part is covered by inventory
    Given metasfresh initially has this MD_Candidate data
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | Qty_AvailableToPromise | M_Warehouse_ID |
      | s_1        | INVENTORY_UP      |                           | p_1          | 2021-07-01T21:00:00Z | 3   | 3                      | targetWH       |

    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.PreparationDate  | OPT.M_Warehouse_ID.Identifier |
      | SO         | true    | bpartner_1               | 2022-07-04  | 2022-07-04T00:00:00Z | targetWH                      |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | SO                    | p_1                     | 14         |
    And the order identified by SO is completed

    Then after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | Qty_AvailableToPromise | M_Warehouse_ID |
      | s_1        | INVENTORY_UP      |                           | p_1          | 2021-07-01T21:00:00Z | 3   | 3                      | targetWH       |
      | c_1        | DEMAND            | SHIPMENT                  | p_1          | 2022-07-04T00:00:00Z | -14 | -11                    | targetWH       |
      | c_2        | SUPPLY            | DISTRIBUTION              | p_1          | 2022-07-04T00:00:00Z | 11  | 0                      | targetWH       |
      | c_3        | DEMAND            | DISTRIBUTION              | p_1          | 2022-07-04T00:00:00Z | -11 | -11                    | sourceWH       |
      | c_4        | SUPPLY            |                           | p_1          | 2022-07-04T00:00:00Z | 11  | 0                      | sourceWH       |
    And after not more than 60s, following DD_Order_Candidates are found
      | Identifier | M_Product_ID | M_Warehouse_From_ID | M_WarehouseTo_ID | Qty | Processed |
      | c1         | p_1          | sourceWH            | targetWH         | 11  | N         |






# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
  @from:cucumber
  Scenario: targetWH <- sourceWH <- sourceWH2 <- sourceWH3 (with partial stock)
    Given metasfresh contains M_Warehouse:
      | M_Warehouse_ID | C_BPartner_ID | C_BPartner_Location_ID |
      | sourceWH2      | bpartner_1    | location_1             |
      | sourceWH3      | bpartner_1    | location_1             |
    And metasfresh contains DD_NetworkDistributionLine
      | DD_NetworkDistribution_ID | M_Warehouse_ID | M_WarehouseSource_ID | M_Shipper_ID |
      | ddNetwork_1               | sourceWH       | sourceWH2            | shipper      |
      | ddNetwork_1               | sourceWH2      | sourceWH3            | shipper      |
    And metasfresh contains PP_Product_Plannings
      | M_Product_ID | IsCreatePlan | DD_NetworkDistribution_ID | M_Warehouse_ID |
      | p_1          | true         | ddNetwork_1               | targetWH       |
      | p_1          | true         | ddNetwork_1               | sourceWH       |
      | p_1          | true         | ddNetwork_1               | sourceWH2      |
    And metasfresh initially has this MD_Candidate data
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | Qty_AvailableToPromise | M_Warehouse_ID |
      | stock1     | INVENTORY_UP      |                           | p_1          | 2021-07-01T21:00:00Z | 3   | 3                      | targetWH       |
      | stock2     | INVENTORY_UP      |                           | p_1          | 2021-07-01T21:00:00Z | 4   | 4                      | sourceWH       |
      | stock3     | INVENTORY_UP      |                           | p_1          | 2021-07-01T21:00:00Z | 5   | 5                      | sourceWH2      |
      | stock4     | INVENTORY_UP      |                           | p_1          | 2021-07-01T21:00:00Z | 6   | 6                      | sourceWH3      |


    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.PreparationDate  | OPT.M_Warehouse_ID.Identifier |
      | SO         | true    | bpartner_1               | 2022-07-04  | 2022-07-04T00:00:00Z | targetWH                      |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | SO                    | p_1                     | 100        |
    And the order identified by SO is completed


    Then after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty  | Qty_AvailableToPromise | M_Warehouse_ID |
      # Already existing stock (see above):
      | stock1     | INVENTORY_UP      |                           | p_1          | 2021-07-01T21:00:00Z | 3    | 3                      | targetWH       |
      | stock2     | INVENTORY_UP      |                           | p_1          | 2021-07-01T21:00:00Z | 4    | 4                      | sourceWH       |
      | stock3     | INVENTORY_UP      |                           | p_1          | 2021-07-01T21:00:00Z | 5    | 5                      | sourceWH2      |
      | stock4     | INVENTORY_UP      |                           | p_1          | 2021-07-01T21:00:00Z | 6    | 6                      | sourceWH3      |
      # Sales Order / Shipment Schedule
      |            | DEMAND            | SHIPMENT                  | p_1          | 2022-07-04T00:00:00Z | -100 | -97                    | targetWH       |
      # DD_Order_Candidate: targetWH <- sourceWH
      |            | SUPPLY            | DISTRIBUTION              | p_1          | 2022-07-04T00:00:00Z | 97   | 0                      | targetWH       |
      |            | DEMAND            | DISTRIBUTION              | p_1          | 2022-07-04T00:00:00Z | -97  | -93                    | sourceWH       |
      # DD_Order_Candidate: sourceWH <- sourceWH2
      |            | SUPPLY            | DISTRIBUTION              | p_1          | 2022-07-04T00:00:00Z | 93   | 0                      | sourceWH       |
      |            | DEMAND            | DISTRIBUTION              | p_1          | 2022-07-04T00:00:00Z | -93  | -88                    | sourceWH2      |
      # DD_Order_Candidate: sourceWH2 <- sourceWH3
      |            | SUPPLY            | DISTRIBUTION              | p_1          | 2022-07-04T00:00:00Z | 88   | 0                      | sourceWH2      |
      |            | DEMAND            | DISTRIBUTION              | p_1          | 2022-07-04T00:00:00Z | -88  | -82                    | sourceWH3      |
      |            | SUPPLY            |                           | p_1          | 2022-07-04T00:00:00Z | 82   | 0                      | sourceWH3      |
    And after not more than 60s, following DD_Order_Candidates are found
      | M_Product_ID | M_Warehouse_From_ID | M_WarehouseTo_ID | Qty | Processed |
      | p_1          | sourceWH            | targetWH         | 97  | N         |
      | p_1          | sourceWH2           | sourceWH         | 93  | N         |
      | p_1          | sourceWH3           | sourceWH2        | 88  | N         |
    



# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
# ###############################################################################################################################################
  @from:cucumber
  Scenario: Expect infinite loop to be detected and the last DD_Order_Candidate to not be generated
    Given metasfresh contains DD_NetworkDistributionLine
      | DD_NetworkDistribution_ID | M_Warehouse_ID | M_WarehouseSource_ID | M_Shipper_ID |
      | ddNetwork_1               | sourceWH       | targetWH             | shipper      |
    And metasfresh contains PP_Product_Plannings
      | M_Product_ID | IsCreatePlan | DD_NetworkDistribution_ID | M_Warehouse_ID |
      | p_1          | true         | ddNetwork_1               | targetWH       |
      | p_1          | true         | ddNetwork_1               | sourceWH       |
    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.PreparationDate  | OPT.M_Warehouse_ID.Identifier |
      | SO         | true    | bpartner_1               | 2022-07-04  | 2022-07-04T00:00:00Z | targetWH                      |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | SO                    | p_1                     | 14         |
    And the order identified by SO is completed

    Then after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | Qty_AvailableToPromise | M_Warehouse_ID |
      | c_1        | DEMAND            | SHIPMENT                  | p_1          | 2022-07-04T00:00:00Z | -14 | -14                    | targetWH       |
      | c_2        | SUPPLY            | DISTRIBUTION              | p_1          | 2022-07-04T00:00:00Z | 14  | 0                      | targetWH       |
      | c_3        | DEMAND            | DISTRIBUTION              | p_1          | 2022-07-04T00:00:00Z | -14 | -14                    | sourceWH       |
      | c_4        | SUPPLY            |                           | p_1          | 2022-07-04T00:00:00Z | 14  | 0                      | sourceWH       |
    And after not more than 60s, following DD_Order_Candidates are found
      | Identifier | M_Product_ID | M_Warehouse_From_ID | M_WarehouseTo_ID | Qty | Processed |
      | c1         | p_1          | sourceWH            | targetWH         | 14  | N         |
