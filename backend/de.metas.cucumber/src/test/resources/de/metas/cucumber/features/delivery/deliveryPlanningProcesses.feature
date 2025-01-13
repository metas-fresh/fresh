@from:cucumber
Feature: Delivery planning processes interaction

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2023-02-01T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config de.metas.deliveryplanning.DeliveryPlanningService.M_Delivery_Planning_CreateAutomatically

    Given metasfresh contains M_PricingSystems
      | Identifier    | Name              | Value                                  | OPT.IsActive |
      | pricingSystem | PricingSystemName | PricingSystemValueDPProcesses_03022023 | true         |
    And metasfresh contains M_PriceLists
      | Identifier   | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                              | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | priceList_SO | pricingSystem                 | DE                        | EUR                 | PriceListNameDPProcesses_03022023 | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier          | M_PriceList_ID.Identifier | Name           | ValidFrom  |
      | priceListVersion_SO | priceList_SO              | SalesOrder-PLV | 2023-02-01 |
    And metasfresh contains M_Products:
      | Identifier | Name                            |
      | product    | ProductNameDPProcesses_03022023 |
    And metasfresh contains M_ProductPrices
      | Identifier      | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | productPrice_SO | priceListVersion_SO               | product                 | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners without locations:
      | Identifier | Name                         | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | customer   | CustomerDPProcesses_03022023 | N            | Y              | pricingSystem                 |
    And metasfresh contains C_BPartner_Locations:
      | Identifier       | GLN           | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipToDefault |
      | customerLocation | 1234568110599 | customer                 | true                | true                |
    And load M_Shipper:
      | M_Shipper_ID.Identifier | Name |
      | shipper_DHL             | Dhl  |

  Scenario: Create additional delivery plannings

    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier |
      | orderAdd   | true    | customer                 | 2023-02-03  | 2023-02-25T00:00:00Z | customerLocation                      |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLineAdd | orderAdd              | product                 | 5          | shipper_DHL                 |

    When the order identified by orderAdd is completed

    Then after not more than 30s, M_ShipmentSchedules are found:
      | Identifier         | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentSchedule_1 | orderLineAdd              | N             |
    And after not more than 30s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID.Identifiers | C_OrderLine_ID.Identifier |
      | deliveryPlanningAdd_1              | orderLineAdd              |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID.Identifier | QtyOrdered | QtyTotalOpen | M_Delivery_Planning_Type | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.C_BPartner_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Shipper_ID.Identifier | OPT.PlannedDeliveryDate |
      | deliveryPlanningAdd_1             | 5          | 5            | Outgoing                 | orderAdd                  | orderLineAdd                  | customer                     | product                     | customerLocation                      | shipper_DHL                 | 2023-02-25              |

    When generate M_ShipperTransportation for M_Delivery_Planning:
      | M_ShipperTransportation_ID.Identifier | M_Delivery_Planning_ID.Identifier |
      | deliveryInstructionAdd                | deliveryPlanningAdd_1             |

    Then validate M_ShipperTransportation:
      | M_ShipperTransportation_ID.Identifier | M_Shipper_ID.Identifier | Shipper_BPartner_ID.Identifier | Shipper_Location_ID.Identifier | OPT.DeliveryDate |
      | deliveryInstructionAdd                | shipper_DHL             | customer                       | customerLocation               | 2023-02-25       |
    And load M_Package for M_ShipperTransportation: deliveryInstructionAdd
      | M_Package_ID.Identifier | M_Product_ID.Identifier |
      | packageAdd              | product                 |
    And validate M_Package:
      | M_Package_ID.Identifier | M_Shipper_ID.Identifier | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.ShipDate |
      | packageAdd              | shipper_DHL             | customer                     | customerLocation                      | 2023-02-01   |
    And load M_Shipping_Package:
      | M_ShippingPackage_ID.Identifier | M_Package_ID.Identifier | M_ShipperTransportation_ID.Identifier |
      | shippingPackageAdd              | packageAdd              | deliveryInstructionAdd                |
    And validate M_Shipping_Package:
      | M_ShippingPackage_ID.Identifier | M_Package_ID.Identifier | M_ShipperTransportation_ID.Identifier | C_BPartner_Location_ID.Identifier | ActualLoadQty | OPT.C_BPartner_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.C_OrderLine_ID.Identifier |
      | shippingPackageAdd              | packageAdd              | deliveryInstructionAdd                | customerLocation                  | 0             | customer                     | product                     | orderLineAdd                  |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID.Identifier | QtyOrdered | QtyTotalOpen | M_Delivery_Planning_Type | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.C_BPartner_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Shipper_ID.Identifier | OPT.PlannedDeliveryDate | OPT.M_ShipperTransportation_ID.Identifier |
      | deliveryPlanningAdd_1             | 5          | 5            | Outgoing                 | orderAdd                  | orderLineAdd                  | customer                     | product                     | customerLocation                      | shipper_DHL                 | 2023-02-25              | deliveryInstructionAdd                    |

    When generate 2 additional M_Delivery_Planning records for: deliveryPlanningAdd_1

    Then after not more than 30s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID.Identifiers                                | C_OrderLine_ID.Identifier |
      | deliveryPlanningAdd_1,deliveryPlanningAdd_2,deliveryPlanningAdd_3 | orderLineAdd              |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID.Identifier | QtyOrdered | QtyTotalOpen | M_Delivery_Planning_Type | OPT.PlannedLoadedQuantity | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.C_BPartner_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Shipper_ID.Identifier | OPT.PlannedDeliveryDate | OPT.M_ShipperTransportation_ID.Identifier |
      | deliveryPlanningAdd_1             | 5          | 5            | Outgoing                 | 0                         | orderAdd                  | orderLineAdd                  | customer                     | product                     | customerLocation                      | shipper_DHL                 | 2023-02-25              | deliveryInstructionAdd                    |
      | deliveryPlanningAdd_2             | 5          | 5            | Outgoing                 | 3                         | orderAdd                  | orderLineAdd                  | customer                     | product                     | customerLocation                      | shipper_DHL                 | 2023-02-25              | null                                      |
      | deliveryPlanningAdd_3             | 5          | 5            | Outgoing                 | 2                         | orderAdd                  | orderLineAdd                  | customer                     | product                     | customerLocation                      | shipper_DHL                 | 2023-02-25              | null                                      |


  Scenario: Generate outgoing delivery planning, close it then reopen it, create additional lines and cancel delivery for a referenced delivery planning

    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier |
      | order      | true    | customer                 | 2023-02-03  | 2023-02-10T00:00:00Z | customerLocation                      |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLine  | order                 | product                 | 5          | shipper_DHL                 |

    When the order identified by order is completed

    Then after not more than 30s, M_ShipmentSchedules are found:
      | Identifier       | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentSchedule | orderLine                 | N             |
    And after not more than 30s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID.Identifiers | C_OrderLine_ID.Identifier |
      | deliveryPlanning                   | orderLine                 |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID.Identifier | QtyOrdered | QtyTotalOpen | M_Delivery_Planning_Type | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.C_BPartner_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Shipper_ID.Identifier | OPT.PlannedDeliveryDate | OPT.IsClosed | OPT.Processed |
      | deliveryPlanning                  | 5          | 5            | Outgoing                 | order                     | orderLine                     | customer                     | product                     | customerLocation                      | shipper_DHL                 | 2023-02-10              | false        | false         |

    When generate 1 additional M_Delivery_Planning records for: deliveryPlanning

    Then after not more than 30s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID.Identifiers  | C_OrderLine_ID.Identifier |
      | deliveryPlanning,deliveryPlanning_2 | orderLine                 |

    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID.Identifier | QtyOrdered | QtyTotalOpen | M_Delivery_Planning_Type | OPT.PlannedLoadedQuantity | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.C_BPartner_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Shipper_ID.Identifier | OPT.PlannedDeliveryDate | OPT.IsClosed | OPT.Processed |
      | deliveryPlanning                  | 5          | 5            | Outgoing                 | 0                         | order                     | orderLine                     | customer                     | product                     | customerLocation                      | shipper_DHL                 | 2023-02-10              | false        | false         |
      | deliveryPlanning_2                | 5          | 5            | Outgoing                 | 5                         | order                     | orderLine                     | customer                     | product                     | customerLocation                      | shipper_DHL                 | 2023-02-10              | false        | false         |

    When M_Delivery_Planning identified by deliveryPlanning_2 is closed

    Then validate M_Delivery_Planning:
      | M_Delivery_Planning_ID.Identifier | QtyOrdered | QtyTotalOpen | M_Delivery_Planning_Type | OPT.IsClosed | OPT.Processed | OPT.PlannedLoadedQuantity |
      | deliveryPlanning_2                | 5          | 5            | Outgoing                 | true         | true          | 5                         |

    When M_Delivery_Planning identified by deliveryPlanning_2 is opened

    Then validate M_Delivery_Planning:
      | M_Delivery_Planning_ID.Identifier | QtyOrdered | QtyTotalOpen | M_Delivery_Planning_Type | OPT.IsClosed | OPT.Processed | OPT.PlannedLoadedQuantity |
      | deliveryPlanning_2                | 5          | 5            | Outgoing                 | false        | false         | 5                         |

    When generate M_ShipperTransportation for M_Delivery_Planning:
      | M_ShipperTransportation_ID.Identifier | M_Delivery_Planning_ID.Identifier |
      | deliveryInstruction                   | deliveryPlanning_2                |

    Then validate M_ShipperTransportation:
      | M_ShipperTransportation_ID.Identifier | M_Shipper_ID.Identifier | Shipper_BPartner_ID.Identifier | Shipper_Location_ID.Identifier | OPT.DeliveryDate | OPT.DocStatus |
      | deliveryInstruction                   | shipper_DHL             | customer                       | customerLocation               | 2023-02-10       | CO            |
    And load M_Package for M_ShipperTransportation: deliveryInstruction
      | M_Package_ID.Identifier | M_Product_ID.Identifier |
      | package                 | product                 |
    And validate M_Package:
      | M_Package_ID.Identifier | M_Shipper_ID.Identifier | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.ShipDate |
      | package                 | shipper_DHL             | customer                     | customerLocation                      | 2023-02-01   |
    And load M_Shipping_Package:
      | M_ShippingPackage_ID.Identifier | M_Package_ID.Identifier | M_ShipperTransportation_ID.Identifier |
      | shippingPackage                 | package                 | deliveryInstruction                   |
    And validate M_Shipping_Package:
      | M_ShippingPackage_ID.Identifier | M_Package_ID.Identifier | M_ShipperTransportation_ID.Identifier | C_BPartner_Location_ID.Identifier | ActualLoadQty | OPT.C_BPartner_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.C_OrderLine_ID.Identifier |
      | shippingPackage                 | package                 | deliveryInstruction                   | customerLocation                  | 5             | customer                     | product                     | orderLine                     |

    When M_Delivery_Planning identified by deliveryPlanning_2 is canceled

    Then validate M_Delivery_Planning:
      | M_Delivery_Planning_ID.Identifier | QtyOrdered | QtyTotalOpen | M_Delivery_Planning_Type | OPT.IsClosed | OPT.Processed | OPT.PlannedLoadedQuantity | OPT.OrderStatus |
      | deliveryPlanning_2                | 5          | 5            | Outgoing                 | true         | true          | 0                         | Canceled        |
    And validate M_ShipperTransportation:
      | M_ShipperTransportation_ID.Identifier | M_Shipper_ID.Identifier | Shipper_BPartner_ID.Identifier | Shipper_Location_ID.Identifier | OPT.DeliveryDate | OPT.DocStatus |
      | deliveryInstruction                   | shipper_DHL             | customer                       | customerLocation               | 2023-02-10       | VO            |