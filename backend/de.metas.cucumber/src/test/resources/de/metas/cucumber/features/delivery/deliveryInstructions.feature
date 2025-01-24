@from:cucumber
Feature: Generate delivery instructions from delivery plannings

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2023-02-01T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config de.metas.deliveryplanning.DeliveryPlanningService.M_Delivery_Planning_CreateAutomatically

  Scenario: Generate and regenerate delivery instructions from outgoing delivery planning
    Given  metasfresh contains M_PricingSystems
      | Identifier    | Name              | Value                          | OPT.IsActive |
      | pricingSystem | PricingSystemName | PricingSystemValueGen_02022023 | true         |
    And metasfresh contains M_PriceLists
      | Identifier   | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                        | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | priceList_SO | pricingSystem                 | DE                        | EUR                 | PriceListNameSOGen_02022023 | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier          | M_PriceList_ID.Identifier | Name           | ValidFrom  |
      | priceListVersion_SO | priceList_SO              | SalesOrder-PLV | 2023-02-01 |
    And metasfresh contains C_BPartners without locations:
      | Identifier | Name                 | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | customer   | CustomerGen_02022023 | N            | Y              | pricingSystem                 |
    And metasfresh contains C_BPartner_Locations:
      | Identifier       | GLN           | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipToDefault |
      | customerLocation | 1234562090546 | customer                 | true                | true                |
    And metasfresh contains M_Products:
      | Identifier | Name                    |
      | product    | ProductNameGen_02022023 |
    And metasfresh contains M_ProductPrices
      | Identifier      | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | productPrice_SO | priceListVersion_SO               | product                 | 10.0     | PCE               | Normal                        |
    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        | OPT.C_BPartner_Location_ID.Identifier |
      | warehouseStd              | StdWarehouse | warehouseStdLocation                  |
    And load M_Shipper:
      | M_Shipper_ID.Identifier | Name |
      | shipper_DHL             | Dhl  |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier |
      | orderDI_SO | true    | customer                 | 2023-02-02  | 2023-02-10T00:00:00Z | customerLocation                      |
    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLineDI_SO | orderDI_SO            | product                 | 10         | shipper_DHL                 |

    When the order identified by orderDI_SO is completed

    Then after not more than 30s, M_ShipmentSchedules are found:
      | Identifier       | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentSchedule | orderLineDI_SO            | N             |
    And after not more than 30s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID.Identifiers | C_OrderLine_ID.Identifier |
      | deliveryPlanningDI_SO              | orderLineDI_SO            |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID.Identifier | QtyOrdered | QtyTotalOpen | M_Delivery_Planning_Type | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.C_BPartner_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Shipper_ID.Identifier | OPT.PlannedDeliveryDate |
      | deliveryPlanningDI_SO             | 10         | 10           | Outgoing                 | orderDI_SO                | orderLineDI_SO                | customer                     | product                     | customerLocation                      | shipper_DHL                 | 2023-02-10              |

    When generate M_ShipperTransportation for M_Delivery_Planning:
      | M_ShipperTransportation_ID.Identifier | M_Delivery_Planning_ID.Identifier |
      | deliveryInstruction_SO                | deliveryPlanningDI_SO             |

    Then validate M_ShipperTransportation:
      | M_ShipperTransportation_ID.Identifier | M_Shipper_ID.Identifier | Shipper_BPartner_ID.Identifier | Shipper_Location_ID.Identifier | OPT.C_BPartner_Location_Delivery_ID.Identifier | OPT.C_BPartner_Location_Loading_ID.Identifier | OPT.DeliveryDate | OPT.DocStatus |
      | deliveryInstruction_SO                | shipper_DHL             | customer                       | customerLocation               | customerLocation                               | warehouseStdLocation                          | 2023-02-10       | CO            |
    And load M_Package for M_ShipperTransportation: deliveryInstruction_SO
      | M_Package_ID.Identifier | M_Product_ID.Identifier |
      | packageDI               | product                 |
    And validate M_Package:
      | M_Package_ID.Identifier | M_Shipper_ID.Identifier | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.ShipDate |
      | packageDI               | shipper_DHL             | customer                     | customerLocation                      | 2023-02-01   |
    And load M_Shipping_Package:
      | M_ShippingPackage_ID.Identifier | M_Package_ID.Identifier | M_ShipperTransportation_ID.Identifier |
      | shippingPackageDI               | packageDI               | deliveryInstruction_SO                |
    And validate M_Shipping_Package:
      | M_ShippingPackage_ID.Identifier | M_Package_ID.Identifier | M_ShipperTransportation_ID.Identifier | C_BPartner_Location_ID.Identifier | ActualLoadQty | OPT.C_BPartner_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.C_OrderLine_ID.Identifier |
      | shippingPackageDI               | packageDI               | deliveryInstruction_SO                | customerLocation                  | 0             | customer                     | product                     | orderLineDI_SO                |

    When regenerate M_ShipperTransportation for M_Delivery_Planning:
      | M_ShipperTransportation_ID.Identifier | M_Delivery_Planning_ID.Identifier |
      | deliveryInstructionRegenerated_SO     | deliveryPlanningDI_SO             |

    Then validate M_ShipperTransportation:
      | M_ShipperTransportation_ID.Identifier | M_Shipper_ID.Identifier | Shipper_BPartner_ID.Identifier | Shipper_Location_ID.Identifier | OPT.C_BPartner_Location_Delivery_ID.Identifier | OPT.C_BPartner_Location_Loading_ID.Identifier | OPT.DeliveryDate | OPT.DocStatus |
      | deliveryInstructionRegenerated_SO     | shipper_DHL             | customer                       | customerLocation               | customerLocation                               | warehouseStdLocation                          | 2023-02-10       | CO            |
      | deliveryInstruction_SO                | shipper_DHL             | customer                       | customerLocation               | customerLocation                               | warehouseStdLocation                          | 2023-02-10       | VO            |
    And load M_Package for M_ShipperTransportation: deliveryInstructionRegenerated_SO
      | M_Package_ID.Identifier | M_Product_ID.Identifier |
      | packageDI_RE            | product                 |
    And validate M_Package:
      | M_Package_ID.Identifier | M_Shipper_ID.Identifier | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.ShipDate |
      | packageDI_RE            | shipper_DHL             | customer                     | customerLocation                      | 2023-02-01   |
    And load M_Shipping_Package:
      | M_ShippingPackage_ID.Identifier | M_Package_ID.Identifier | M_ShipperTransportation_ID.Identifier |
      | shippingPackageDI_RE            | packageDI_RE            | deliveryInstructionRegenerated_SO     |
    And validate M_Shipping_Package:
      | M_ShippingPackage_ID.Identifier | M_Package_ID.Identifier | M_ShipperTransportation_ID.Identifier | C_BPartner_Location_ID.Identifier | ActualLoadQty | OPT.C_BPartner_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.C_OrderLine_ID.Identifier |
      | shippingPackageDI_RE            | packageDI_RE            | deliveryInstructionRegenerated_SO     | customerLocation                  | 0             | customer                     | product                     | orderLineDI_SO                |



