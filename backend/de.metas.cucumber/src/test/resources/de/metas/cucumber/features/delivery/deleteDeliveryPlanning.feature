@from:cucumber
Feature: Delete delivery planning

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2023-02-01T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config de.metas.deliveryplanning.DeliveryPlanningService.M_Delivery_Planning_CreateAutomatically

  Scenario: Delete delivery planning

    Given metasfresh contains M_PricingSystems
      | Identifier    | Name              | Value                             | OPT.IsActive |
      | pricingSystem | PricingSystemName | PricingSystemValueDelete_03022023 | true         |
    And metasfresh contains M_PriceLists
      | Identifier   | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                         | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | priceList_SO | pricingSystem                 | DE                        | EUR                 | PriceListNameDelete_03022023 | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier          | M_PriceList_ID.Identifier | Name           | ValidFrom  |
      | priceListVersion_SO | priceList_SO              | SalesOrder-PLV | 2023-02-01 |
    And metasfresh contains M_Products:
      | Identifier | Name                       |
      | product    | ProductNameDelete_03022023 |
    And metasfresh contains M_ProductPrices
      | Identifier        | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | productPrice_SO_4 | priceListVersion_SO               | product                 | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners without locations:
      | Identifier | Name                    | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | customer   | CustomerDelete_03022023 | N            | Y              | pricingSystem                 |
    And metasfresh contains C_BPartner_Locations:
      | Identifier       | GLN           | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipToDefault |
      | customerLocation | 1230367890599 | customer                 | true                | true                |
    And load M_Shipper:
      | M_Shipper_ID.Identifier | Name |
      | shipper_DHL             | Dhl  |
    And metasfresh contains C_Orders:
      | Identifier  | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier |
      | orderDelete | true    | customer                 | 2023-02-03  | 2023-02-25T00:00:00Z | customerLocation                      |
    And metasfresh contains C_OrderLines:
      | Identifier      | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLineDelete | orderDelete           | product                 | 5          | shipper_DHL                 |

    When the order identified by orderDelete is completed

    Then after not more than 30s, M_ShipmentSchedules are found:
      | Identifier       | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentSchedule | orderLineDelete           | N             |
    And after not more than 30s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID.Identifiers | C_OrderLine_ID.Identifier |
      | deliveryPlanningDelete_1           | orderLineDelete           |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID.Identifier | QtyOrdered | QtyTotalOpen | M_Delivery_Planning_Type | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.C_BPartner_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Shipper_ID.Identifier | OPT.PlannedDeliveryDate |
      | deliveryPlanningDelete_1          | 5          | 5            | Outgoing                 | orderDelete               | orderLineDelete               | customer                     | product                     | customerLocation                      | shipper_DHL                 | 2023-02-25              |

    And delete M_Delivery_Planning:
      | M_Delivery_Planning_ID.Identifier | OPT.ErrorMessage                                                               |
      | deliveryPlanningDelete_1          | Line can not be deleted as it is the last delivery planning for this Orderline |

    When generate 1 additional M_Delivery_Planning records for: deliveryPlanningDelete_1

    Then after not more than 30s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID.Identifiers                | C_OrderLine_ID.Identifier |
      | deliveryPlanningDelete_1,deliveryPlanningDelete_2 | orderLineDelete           |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID.Identifier | QtyOrdered | QtyTotalOpen | M_Delivery_Planning_Type | OPT.PlannedLoadedQuantity | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.C_BPartner_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Shipper_ID.Identifier | OPT.PlannedDeliveryDate |
      | deliveryPlanningDelete_1          | 5          | 5            | Outgoing                 | 0                         | orderDelete               | orderLineDelete               | customer                     | product                     | customerLocation                      | shipper_DHL                 | 2023-02-25              |
      | deliveryPlanningDelete_2          | 5          | 5            | Outgoing                 | 5                         | orderDelete               | orderLineDelete               | customer                     | product                     | customerLocation                      | shipper_DHL                 | 2023-02-25              |

    When generate M_ShipperTransportation for M_Delivery_Planning:
      | M_ShipperTransportation_ID.Identifier | M_Delivery_Planning_ID.Identifier |
      | deliveryInstructionDelete             | deliveryPlanningDelete_1          |

    Then validate M_ShipperTransportation:
      | M_ShipperTransportation_ID.Identifier | M_Shipper_ID.Identifier | Shipper_BPartner_ID.Identifier | Shipper_Location_ID.Identifier | OPT.DeliveryDate |
      | deliveryInstructionDelete             | shipper_DHL             | customer                       | customerLocation               | 2023-02-25       |
    And load M_Package for M_ShipperTransportation: deliveryInstructionDelete
      | M_Package_ID.Identifier | M_Product_ID.Identifier |
      | packageDelete           | product                 |
    And validate M_Package:
      | M_Package_ID.Identifier | M_Shipper_ID.Identifier | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.ShipDate |
      | packageDelete           | shipper_DHL             | customer                     | customerLocation                      | 2023-02-01   |
    And load M_Shipping_Package:
      | M_ShippingPackage_ID.Identifier | M_Package_ID.Identifier | M_ShipperTransportation_ID.Identifier |
      | shippingPackageDelete           | packageDelete           | deliveryInstructionDelete             |
    And validate M_Shipping_Package:
      | M_ShippingPackage_ID.Identifier | M_Package_ID.Identifier | M_ShipperTransportation_ID.Identifier | C_BPartner_Location_ID.Identifier | ActualLoadQty | OPT.C_BPartner_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.C_OrderLine_ID.Identifier |
      | shippingPackageDelete           | packageDelete           | deliveryInstructionDelete             | customerLocation                  | 0             | customer                     | product                     | orderLineDelete               |
    And delete M_Delivery_Planning:
      | M_Delivery_Planning_ID.Identifier | OPT.ErrorMessage                                                 |
      | deliveryPlanningDelete_1          | Line can not be deleted as it is referenced to another document. |
      | deliveryPlanningDelete_2          |                                                                  |
