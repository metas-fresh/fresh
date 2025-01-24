@from:cucumber
Feature: Delivery planning automatically generated when M_ShipmentSchedule is created

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2023-02-01T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config de.metas.deliveryplanning.DeliveryPlanningService.M_Delivery_Planning_CreateAutomatically

  Scenario: Outgoing delivery planning is automatically created after ShipmentSchedule is created; SO is delivered to customer location

    Given metasfresh contains M_PricingSystems
      | Identifier      | Name               | Value                               | OPT.IsActive |
      | pricingSystem_1 | PricingSystemName1 | PricingSystemValueOutgoing_02022023 | true         |
    And metasfresh contains M_PriceLists
      | Identifier   | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                           | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | priceList_SO | pricingSystem_1               | DE                        | EUR                 | PriceListNameOutgoing_02022023 | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier          | M_PriceList_ID.Identifier | Name           | ValidFrom  |
      | priceListVersion_SO | priceList_SO              | SalesOrder-PLV | 2023-02-01 |
    And metasfresh contains M_Products:
      | Identifier | Name                         |
      | product    | ProductNameOutgoing_02022023 |
    And metasfresh contains M_ProductPrices
      | Identifier      | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | productPrice_SO | priceListVersion_SO               | product                 | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners without locations:
      | Identifier  | Name                         | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | warehouseBP | warehouseBPOutgoing_02022023 |              |                |                               |
      | customer    | CustomerOutgoing_02022023    | N            | Y              | pricingSystem_1               |
    And metasfresh contains C_BPartner_Locations:
      | Identifier        | GLN           | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipToDefault |
      | customerLocation  | 1234567890599 | customer                 | true                | true                |
      | warehouseLocation | 1234522899346 | warehouseBP              | true                | true                |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID.Identifier | Value                           | Name                           | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier |
      | warehouse                 | warehouseValueOutgoing_02022023 | warehouseNameOutgoing_02022023 | warehouseBP                  | warehouseLocation                     |
    And metasfresh contains M_Locator:
      | M_Locator_ID.Identifier | Value                    | M_Warehouse_ID.Identifier |
      | locator                 | LocatorOutgoing_02022023 | warehouse                 |
    And load M_Shipper:
      | M_Shipper_ID.Identifier | Name |
      | shipper_DHL             | Dhl  |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier |
      | order_SO   | true    | customer                 | 2023-02-02  | 2023-02-25T00:00:00Z | customerLocation                      | warehouse                     |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLine_SO | order_SO              | product                 | 10         | shipper_DHL                 |

    When the order identified by order_SO is completed

    Then after not more than 30s, M_ShipmentSchedules are found:
      | Identifier       | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentSchedule | orderLine_SO              | N             |
    And after not more than 30s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID.Identifiers | C_OrderLine_ID.Identifier |
      | deliveryPlanning_SO                | orderLine_SO              |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID.Identifier | QtyOrdered | QtyTotalOpen | M_Delivery_Planning_Type | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.C_BPartner_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Shipper_ID.Identifier | OPT.PlannedDeliveryDate | OPT.M_Warehouse_ID.Identifier |
      | deliveryPlanning_SO               | 10         | 10           | Outgoing                 | order_SO                  | orderLine_SO                  | customer                     | product                     | customerLocation                      | shipper_DHL                 | 2023-02-25              | warehouse                     |


  Scenario: Incoming delivery planning is automatically created after ReceiptSchedule is created; PO is delivered to warehouse

    Given metasfresh contains M_PricingSystems
      | Identifier      | Name                               | Value                               | OPT.IsActive |
      | pricingSystem_2 | PricingSystemNameIncoming_03022023 | PricingSystemValueIncoming_03022023 | true         |
    And metasfresh contains M_PriceLists
      | Identifier     | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                           | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | priceList_PO_2 | pricingSystem_2               | DE                        | EUR                 | PriceListNameIncoming_03022023 | false | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier            | M_PriceList_ID.Identifier | Name               | ValidFrom  |
      | priceListVersion_PO_2 | priceList_PO_2            | PurchaseOrder-PLV6 | 2023-02-01 |
    And metasfresh contains C_BPartners without locations:
      | Identifier  | Name        | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | vendor      | Vendor      | Y            | N              | pricingSystem_2               |
      | warehouseBP | warehouseBP |              |                |                               |
    And metasfresh contains C_BPartner_Locations:
      | Identifier        | GLN           | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipToDefault |
      | vendorLocation    | 1234564396446 | vendor                   | true                | true                |
      | warehouseLocation | 1203522892346 | warehouseBP              | true                | true                |
    And metasfresh contains M_Products:
      | Identifier | Name                         |
      | product    | ProductNameIncoming_02022023 |
    And metasfresh contains M_ProductPrices
      | Identifier        | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | productPrice_PO_2 | priceListVersion_PO_2             | product                 | 5.0      | PCE               | Normal                        |
    And metasfresh contains C_BPartner_Products:
      | C_BPartner_ID.Identifier | M_Product_ID.Identifier |
      | vendor                   | product                 |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID.Identifier | Value                           | Name                           | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier |
      | warehouse                 | warehouseValueIncoming_03022023 | warehouseNameIncoming_03022023 | warehouseBP                  | warehouseLocation                     |
    And metasfresh contains M_Locator:
      | M_Locator_ID.Identifier | Value                    | M_Warehouse_ID.Identifier |
      | locator                 | LocatorIncoming_03022023 | warehouse                 |
    And load M_Shipper:
      | M_Shipper_ID.Identifier | Name          |
      | shipper_DPD             | DPD - Classic |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.DocBaseType |
      | order_PO   | false   | vendor                   | 2023-02-03  | 2023-02-20T00:00:00Z | vendorLocation                        | warehouse                     | POO             |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLine_PO | order_PO              | product                 | 5          | shipper_DPD                 |

    When the order identified by order_PO is completed

    Then after not more than 30s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier |
      | receiptSchedule                 | order_PO              | orderLine_PO              | vendor                   | vendorLocation                    | product                 | 5          | warehouse                 |
    And after not more than 30s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID.Identifiers | C_OrderLine_ID.Identifier |
      | deliveryPlanning_PO                | orderLine_PO              |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID.Identifier | QtyOrdered | QtyTotalOpen | M_Delivery_Planning_Type | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.C_BPartner_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Shipper_ID.Identifier | OPT.PlannedDeliveryDate | OPT.M_Warehouse_ID.Identifier |
      | deliveryPlanning_PO               | 5          | 5            | Incoming                 | order_PO                  | orderLine_PO                  | vendor                       | product                     | warehouseLocation                     | shipper_DPD                 | 2023-02-20              | warehouse                     |


  @from:cucumber
  @ignore
  Scenario: Delivery planning is updated after SO is reactivated and SO line's qty is changed

    Given metasfresh contains M_PricingSystems
      | Identifier      | Name                               | Value                               | OPT.IsActive |
      | pricingSystem_3 | PricingSystemNameIncoming_09022023 | PricingSystemValueIncoming_09022023 | true         |
    And metasfresh contains M_PriceLists
      | Identifier     | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                           | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | priceList_SO_3 | pricingSystem_3               | DE                        | EUR                 | PriceListNameIncoming_09022023 | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier            | M_PriceList_ID.Identifier | Name            | ValidFrom  |
      | priceListVersion_SO_3 | priceList_SO_3            | SalesOrder-PLV3 | 2023-02-01 |
    And metasfresh contains M_Products:
      | Identifier | Name                         |
      | product_3  | ProductNameIncoming_09022023 |
    And metasfresh contains M_ProductPrices
      | Identifier        | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | productPrice_SO_3 | priceListVersion_SO_3             | product_3               | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners without locations:
      | Identifier | Name                      | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | customer_3 | CustomerIncoming_09022023 | N            | Y              | pricingSystem_3               |
    And metasfresh contains C_BPartner_Locations:
      | Identifier         | GLN           | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipToDefault |
      | customerLocation_3 | 1234447890599 | customer_3               | true                | true                |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_BPartner_Location_ID.Identifier |
      | order_SO_3 | true    | customer_3               | 2023-02-09  | customerLocation_3                    |
    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine_SO_3 | order_SO_3            | product_3               | 10         |

    When the order identified by order_SO_3 is completed

    Then after not more than 30s, M_ShipmentSchedules are found:
      | Identifier         | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentSchedule_3 | orderLine_SO_3            | N             |
    And after not more than 30s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID.Identifiers | C_OrderLine_ID.Identifier |
      | deliveryPlanning_SO_3              | orderLine_SO_3            |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID.Identifier | QtyOrdered | QtyTotalOpen | M_Delivery_Planning_Type | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.C_BPartner_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier |
      | deliveryPlanning_SO_3             | 10         | 10           | Outgoing                 | order_SO_3                | orderLine_SO_3                | customer_3                   | product_3                   | customerLocation_3                    |

    When the order identified by order_SO_3 is reactivated

    Then update C_OrderLine:
      | C_OrderLine_ID.Identifier | OPT.QtyEntered |
      | orderLine_SO_3            | 20             |

    When the order identified by order_SO_3 is completed

    Then after not more than 30s, M_ShipmentSchedules are found:
      | Identifier         | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentSchedule_3 | orderLine_SO_3            | N             |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID.Identifier | QtyOrdered | QtyTotalOpen | M_Delivery_Planning_Type | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.C_BPartner_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier |
      | deliveryPlanning_SO_3             | 20         | 20           | Outgoing                 | order_SO_3                | orderLine_SO_3                | customer_3                   | product_3                   | customerLocation_3                    |
