@from:cucumber
Feature: Order to delivery instructions

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2023-02-01T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config de.metas.deliveryplanning.DeliveryPlanningService.M_Delivery_Planning_CreateAutomatically


    Given metasfresh contains M_PricingSystems
      | Identifier    | Name                              | Value                              | OPT.IsActive |
      | pricingSystem | PricingSystemNameOrderDI_03022023 | PricingSystemValueOrderDI_03022023 | true         |
    And metasfresh contains M_PriceLists
      | Identifier   | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                            | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | priceList_SO | pricingSystem                 | DE                        | EUR                 | PriceListNameSOOrderDI_03022023 | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier          | M_PriceList_ID.Identifier | Name           | ValidFrom  |
      | priceListVersion_SO | priceList_SO              | SalesOrder-PLV | 2023-02-01 |
    And metasfresh contains C_BPartners without locations:
      | Identifier | Name                     | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | customer   | CustomerOrderDI_03022023 | N            | Y              | pricingSystem                 |
    And metasfresh contains C_BPartner_Locations:
      | Identifier       | GLN           | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipToDefault |
      | customerLocation | 1234567090599 | customer                 | true                | true                |
    And load M_AttributeSet:
      | M_AttributeSet_ID.Identifier  | Name               |
      | attributeSetConvenienceSalate | Convenience Salate |
    And load M_Product_Category:
      | M_Product_Category_ID.Identifier | Name     | Value    |
      | standardCategory                 | Standard | Standard |
    And update M_Product_Category:
      | M_Product_Category_ID.Identifier | OPT.M_AttributeSet_ID.Identifier |
      | standardCategory                 | attributeSetConvenienceSalate    |
    And load M_Shipper:
      | M_Shipper_ID.Identifier | Name          |
      | shipper_DHL             | Dhl           |
      | shipper_DPD             | DPD - Classic |

  Scenario: Order to delivery instructions.
  _Given initial SO with 2 order lines : QtyEntered = 2, different delivery dates in the future and attributes assigned
  _When the SO is completed
  _Then 2 delivery plannings are created for the existing order lines with QtyOrdered = 2
  _And different forwarders are set on both delivery planning records so that delivery instructions can be generated later
  _When generate PO from SO process is triggered for the current vendor & purchaseType = `Dropship`
  _Then a dropShip PO with 2 orderLines is created for `Vendor` having as dropShip partner - SO's customer
  _When the PO is completed
  _Then two B2B delivery plannings are created for the 2 purchase orderLines
  _And different forwarders are set on both delivery planning records so that delivery instructions can be generated
  _When generate Delivery Instruction is invoked for both delivery plannings created after SO completion
  _Then validate 2 delivery instructions: LoadingLocation = dropshipWarehouseLocation, deliveryLocation = customerLocation, 2 new packages & 2 new shipping packages with ActualLoadQty = 0 are created
  _When generate Delivery Instruction is invoked for both delivery plannings created after PO completion
  _Then validate 2 delivery instructions - LoadingLocation = vendorLocation, deliveryLocation = dropshipWarehouseLocation, 2 new packages & 2 new shipping packages - ActualLoadQty = 0 are created

    Given load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value             | OPT.C_BPartner_Location_ID.Identifier |
      | dropShipWarehouse         | DropshipWarehouse | dropShipWarehouseLocation             |
    And load C_Currency by ISO Code:
      | C_Currency_ID.Identifier | ISO_Code |
      | currency                 | EUR      |
    And metasfresh contains M_PriceLists
      | Identifier     | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                            | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | priceList_PO_6 | pricingSystem                 | DE                        | EUR                 | PriceListNamePOOrderDI_03022023 | false | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier            | M_PriceList_ID.Identifier | Name               | ValidFrom  |
      | priceListVersion_PO_6 | priceList_PO_6            | PurchaseOrder-PLV6 | 2023-02-01 |
    And metasfresh contains C_BPartners without locations:
      | Identifier | Name                   | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | vendor     | VendorOrderDI_03022023 | Y            | N              | pricingSystem                 |
    And metasfresh contains C_BPartner_Locations:
      | Identifier     | GLN           | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipToDefault |
      | vendorLocation | 1234567899346 | vendor                   | true                | true                |
    And metasfresh contains M_Products:
      | Identifier | Name                        | OPT.M_Product_Category_ID.Identifier |
      | product    | ProductNameOrderDI_03022023 | standardCategory                     |
    And metasfresh contains M_ProductPrices
      | Identifier        | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | productPrice_PO_6 | priceListVersion_PO_6             | product                 | 5.0      | PCE               | Normal                        |
      | productPrice_SO_6 | priceListVersion_SO               | product                 | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartner_Products:
      | C_BPartner_ID.Identifier | M_Product_ID.Identifier | OPT.IsCurrentVendor |
      | vendor                   | product                 | true                |
    And metasfresh contains C_Orders:
      | Identifier        | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier |
      | orderSO_Unchanged | true    | customer                 | 2023-02-03  | customerLocation                      | dropShipWarehouse             |
    And metasfresh contains M_AttributeSetInstance with identifier "line1ASI":
  """
  {
    "attributeInstances":[
      {
        "attributeCode":"1000002",
          "valueStr":"Bio"
      }
    ]
  }
  """
    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.DateOrdered | OPT.DatePromised     | OPT.M_AttributeSetInstance_ID.Identifier | OPT.C_BPartner_ID.Identifier | OPT.C_Currency_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier |
      | orderLine_1 | orderSO_Unchanged     | product                 | 2          | 2023-02-03      | 2023-05-10T00:00:00Z | line1ASI                                 | customer                     | currency                     | customerLocation                      |

    And metasfresh contains M_AttributeSetInstance with identifier "line2ASI":
  """
  {
    "attributeInstances":[
      {
        "attributeCode":"1000015",
          "valueStr":"02"
      }
    ]
  }
  """

    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.DateOrdered | OPT.DatePromised     | OPT.M_AttributeSetInstance_ID.Identifier | OPT.C_BPartner_ID.Identifier | OPT.C_Currency_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier |
      | orderLine_2 | orderSO_Unchanged     | product                 | 2          | 2023-02-03      | 2023-04-10T00:00:00Z | line2ASI                                 | customer                     | currency                     | customerLocation                      |

    When the order identified by orderSO_Unchanged is completed

    Then after not more than 30s, M_ShipmentSchedules are found:
      | Identifier         | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentSchedule_1 | orderLine_1               | N             |
      | shipmentSchedule_2 | orderLine_2               | N             |
    And after not more than 30s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID.Identifiers | C_OrderLine_ID.Identifier |
      | deliveryPlanningSO_line1           | orderLine_1               |
      | deliveryPlanningSO_line2           | orderLine_2               |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID.Identifier | QtyOrdered | QtyTotalOpen | M_Delivery_Planning_Type | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.C_BPartner_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.PlannedDeliveryDate | OPT.IsClosed | OPT.Processed | OPT.M_Warehouse_ID.Identifier |
      | deliveryPlanningSO_line1          | 2          | 2            | Outgoing                 | orderSO_Unchanged         | orderLine_1                   | customer                     | product                     | customerLocation                      | 2023-05-10              | false        | false         | dropShipWarehouse             |
      | deliveryPlanningSO_line2          | 2          | 2            | Outgoing                 | orderSO_Unchanged         | orderLine_2                   | customer                     | product                     | customerLocation                      | 2023-04-10              | false        | false         | dropShipWarehouse             |

    And update M_Delivery_Planning:
      | M_Delivery_Planning_ID.Identifier | OPT.M_Shipper_ID.Identifier |
      | deliveryPlanningSO_line1          | shipper_DHL                 |
      | deliveryPlanningSO_line2          | shipper_DPD                 |

    When generate PO from SO is invoked with parameters:
      | C_BPartner_ID.Identifier | C_Order_ID.Identifier | PurchaseType |
      | vendor                   | orderSO_Unchanged     | Dropship     |

    Then the order is created:
      | Link_Order_ID.Identifier | IsSOTrx | DocBaseType | DocSubType | OPT.IsDropShip | OPT.DocStatus | OPT.DropShip_BPartner_ID.Identifier |
      | orderSO_Unchanged        | false   | POO         |            | true           | DR            | customer                            |
    And the purchase order 'orderPO' with document subtype '' linked to order 'orderSO_Unchanged' has lines:
      | OPT.C_OrderLine_ID.Identifier | QtyOrdered | LineNetAmt | M_Product_ID.Identifier | OPT.C_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.DatePromised     |
      | orderLinePO_1                 | 2          | 10         | product                 | vendor                       | dropShipWarehouse             | 2023-05-10T00:00:00Z |
      | orderLinePO_2                 | 2          | 10         | product                 | vendor                       | dropShipWarehouse             | 2023-04-10T00:00:00Z |

    When the order identified by orderPO is completed

    Then after not more than 30s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier |
      | receiptSchedule_1               | orderPO               | orderLinePO_1             | vendor                   | vendorLocation                    | product                 | 2          | dropShipWarehouse         |
      | receiptSchedule_2               | orderPO               | orderLinePO_2             | vendor                   | vendorLocation                    | product                 | 2          | dropShipWarehouse         |
    And after not more than 30s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID.Identifiers | C_OrderLine_ID.Identifier |
      | deliveryPlanningPO_line1           | orderLinePO_1             |
      | deliveryPlanningPO_line2           | orderLinePO_2             |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID.Identifier | QtyOrdered | QtyTotalOpen | IsB2B | M_Delivery_Planning_Type | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.C_BPartner_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.PlannedDeliveryDate | OPT.IsClosed | OPT.Processed | OPT.M_Warehouse_ID.Identifier |
      | deliveryPlanningPO_line1          | 2          | 2            | true  | Incoming                 | orderPO                   | orderLinePO_1                 | vendor                       | product                     | dropShipWarehouseLocation             | 2023-05-10              | false        | false         | dropShipWarehouse             |
      | deliveryPlanningPO_line2          | 2          | 2            | true  | Incoming                 | orderPO                   | orderLinePO_2                 | vendor                       | product                     | dropShipWarehouseLocation             | 2023-04-10              | false        | false         | dropShipWarehouse             |
    And update M_Delivery_Planning:
      | M_Delivery_Planning_ID.Identifier | OPT.M_Shipper_ID.Identifier |
      | deliveryPlanningPO_line1          | shipper_DHL                 |
      | deliveryPlanningPO_line2          | shipper_DPD                 |

    When generate M_ShipperTransportation for M_Delivery_Planning:
      | M_ShipperTransportation_ID.Identifier | M_Delivery_Planning_ID.Identifier |
      | deliveryInstructionSO_1               | deliveryPlanningSO_line1          |

    Then validate M_ShipperTransportation:
      | M_ShipperTransportation_ID.Identifier | M_Shipper_ID.Identifier | Shipper_BPartner_ID.Identifier | Shipper_Location_ID.Identifier | OPT.C_BPartner_Location_Delivery_ID.Identifier | OPT.C_BPartner_Location_Loading_ID.Identifier | OPT.DeliveryDate | OPT.DocStatus |
      | deliveryInstructionSO_1               | shipper_DHL             | customer                       | customerLocation               | customerLocation                               | dropShipWarehouseLocation                     | 2023-05-10       | CO            |
    And load M_Package for M_ShipperTransportation: deliveryInstructionSO_1
      | M_Package_ID.Identifier | M_Product_ID.Identifier |
      | packageLineSO_1         | product                 |
    And validate M_Package:
      | M_Package_ID.Identifier | M_Shipper_ID.Identifier | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.ShipDate |
      | packageLineSO_1         | shipper_DHL             | customer                     | customerLocation                      | 2023-02-01   |
    And load M_Shipping_Package:
      | M_ShippingPackage_ID.Identifier | M_Package_ID.Identifier | M_ShipperTransportation_ID.Identifier |
      | shippingPackageSO_1             | packageLineSO_1         | deliveryInstructionSO_1               |
    And validate M_Shipping_Package:
      | M_ShippingPackage_ID.Identifier | M_Package_ID.Identifier | M_ShipperTransportation_ID.Identifier | C_BPartner_Location_ID.Identifier | ActualLoadQty | OPT.C_BPartner_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.C_OrderLine_ID.Identifier |
      | shippingPackageSO_1             | packageLineSO_1         | deliveryInstructionSO_1               | customerLocation                  | 0             | customer                     | product                     | orderLine_1                   |

    When generate M_ShipperTransportation for M_Delivery_Planning:
      | M_ShipperTransportation_ID.Identifier | M_Delivery_Planning_ID.Identifier |
      | deliveryInstructionSO_2               | deliveryPlanningSO_line2          |

    Then validate M_ShipperTransportation:
      | M_ShipperTransportation_ID.Identifier | M_Shipper_ID.Identifier | Shipper_BPartner_ID.Identifier | Shipper_Location_ID.Identifier | OPT.C_BPartner_Location_Delivery_ID.Identifier | OPT.C_BPartner_Location_Loading_ID.Identifier | OPT.DeliveryDate | OPT.DocStatus |
      | deliveryInstructionSO_2               | shipper_DPD             | customer                       | customerLocation               | customerLocation                               | dropShipWarehouseLocation                     | 2023-04-10       | CO            |
    And load M_Package for M_ShipperTransportation: deliveryInstructionSO_2
      | M_Package_ID.Identifier | M_Product_ID.Identifier |
      | packageLineSO_2         | product                 |
    And validate M_Package:
      | M_Package_ID.Identifier | M_Shipper_ID.Identifier | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.ShipDate |
      | packageLineSO_2         | shipper_DPD             | customer                     | customerLocation                      | 2023-02-01   |
    And load M_Shipping_Package:
      | M_ShippingPackage_ID.Identifier | M_Package_ID.Identifier | M_ShipperTransportation_ID.Identifier |
      | shippingPackageSO_2             | packageLineSO_2         | deliveryInstructionSO_2               |
    And validate M_Shipping_Package:
      | M_ShippingPackage_ID.Identifier | M_Package_ID.Identifier | M_ShipperTransportation_ID.Identifier | C_BPartner_Location_ID.Identifier | ActualLoadQty | OPT.C_BPartner_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.C_OrderLine_ID.Identifier |
      | shippingPackageSO_2             | packageLineSO_2         | deliveryInstructionSO_2               | customerLocation                  | 0             | customer                     | product                     | orderLine_2                   |

    When generate M_ShipperTransportation for M_Delivery_Planning:
      | M_ShipperTransportation_ID.Identifier | M_Delivery_Planning_ID.Identifier |
      | deliveryInstructionPO_1               | deliveryPlanningPO_line1          |

    Then validate M_ShipperTransportation:
      | M_ShipperTransportation_ID.Identifier | M_Shipper_ID.Identifier | Shipper_BPartner_ID.Identifier | Shipper_Location_ID.Identifier | OPT.C_BPartner_Location_Delivery_ID.Identifier | OPT.C_BPartner_Location_Loading_ID.Identifier | OPT.DeliveryDate | OPT.DocStatus |
      | deliveryInstructionPO_1               | shipper_DHL             | vendor                         | dropShipWarehouseLocation      | dropShipWarehouseLocation                      | vendorLocation                                | 2023-05-10       | CO            |
    And load M_Package for M_ShipperTransportation: deliveryInstructionPO_1
      | M_Package_ID.Identifier | M_Product_ID.Identifier |
      | packageLinePO_1         | product                 |
    And validate M_Package:
      | M_Package_ID.Identifier | M_Shipper_ID.Identifier | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.ShipDate |
      | packageLinePO_1         | shipper_DHL             | vendor                       | dropShipWarehouseLocation             | 2023-02-01   |
    And load M_Shipping_Package:
      | M_ShippingPackage_ID.Identifier | M_Package_ID.Identifier | M_ShipperTransportation_ID.Identifier |
      | shippingPackagePO_1             | packageLinePO_1         | deliveryInstructionPO_1               |
    And validate M_Shipping_Package:
      | M_ShippingPackage_ID.Identifier | M_Package_ID.Identifier | M_ShipperTransportation_ID.Identifier | C_BPartner_Location_ID.Identifier | ActualLoadQty | OPT.C_BPartner_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.C_OrderLine_ID.Identifier |
      | shippingPackagePO_1             | packageLinePO_1         | deliveryInstructionPO_1               | dropShipWarehouseLocation         | 0             | vendor                       | product                     | orderLinePO_1                 |

    When generate M_ShipperTransportation for M_Delivery_Planning:
      | M_ShipperTransportation_ID.Identifier | M_Delivery_Planning_ID.Identifier |
      | deliveryInstructionPO_2               | deliveryPlanningPO_line2          |

    Then validate M_ShipperTransportation:
      | M_ShipperTransportation_ID.Identifier | M_Shipper_ID.Identifier | Shipper_BPartner_ID.Identifier | Shipper_Location_ID.Identifier | OPT.C_BPartner_Location_Delivery_ID.Identifier | OPT.C_BPartner_Location_Loading_ID.Identifier | OPT.DeliveryDate | OPT.DocStatus |
      | deliveryInstructionPO_2               | shipper_DPD             | vendor                         | dropShipWarehouseLocation      | dropShipWarehouseLocation                      | vendorLocation                                | 2023-04-10       | CO            |
    And load M_Package for M_ShipperTransportation: deliveryInstructionPO_2
      | M_Package_ID.Identifier | M_Product_ID.Identifier |
      | packageLinePO_2         | product                 |
    And validate M_Package:
      | M_Package_ID.Identifier | M_Shipper_ID.Identifier | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.ShipDate |
      | packageLinePO_2         | shipper_DPD             | vendor                       | dropShipWarehouseLocation             | 2023-02-01   |
    And load M_Shipping_Package:
      | M_ShippingPackage_ID.Identifier | M_Package_ID.Identifier | M_ShipperTransportation_ID.Identifier |
      | shippingPackagePO_2             | packageLinePO_2         | deliveryInstructionPO_2               |
    And validate M_Shipping_Package:
      | M_ShippingPackage_ID.Identifier | M_Package_ID.Identifier | M_ShipperTransportation_ID.Identifier | C_BPartner_Location_ID.Identifier | ActualLoadQty | OPT.C_BPartner_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.C_OrderLine_ID.Identifier |
      | shippingPackagePO_2             | packageLinePO_2         | deliveryInstructionPO_2               | dropShipWarehouseLocation         | 0             | vendor                       | product                     | orderLinePO_2                 |


  Scenario: Order to delivery instructions. Delivery planning management when SO is changed.
  _Given initial SO with one order line : QtyEntered = 2, delivery date in the future and attribute assigned
  _When the SO is completed
  _Then delivery planning is created for the existing order line with QtyOrdered = 2
  _When the SO is reactivated
  _Then add new orderLine : QtyEntered = 4, different delivery date & different attribute
  _When the SO is completed
  _Then new delivery planning is generate only for the new line with QtyOrdered = orderLine2.QtyEntered
  _And different forwarders are set on both delivery planning records so that delivery instructions can be generated later
  _When generate PO from SO process is triggered for the current vendor & purchaseType = `Dropship`
  _Then dropShip PO is created for `Vendor` having as dropShip partner the SO's customer - having 2 orderLines assigned
  _When the PO is completed
  _Then B2B delivery plannings are created for the 2 purchase orderLines
  _And different forwarders are set on both delivery planning records so that delivery instructions can be generated
  _When generate Delivery Instruction is invoked for both delivery plannings created after SO completion
  _Then validate 2 delivery instructions - LoadingLocation = dropShipWarehouseLocation & deliveryLocation = customerLocation & different delivery dates, 2 new packages & 2 new shipping packages - ActualLoadQty = 0 are created
  _When generate Delivery Instruction is invoked for both delivery plannings created after PO completion
  _Then validate 2 delivery instructions - LoadingLocation = vendorLocation & deliveryLocation = dropshipWarehouseLocation & different delivery dates, 2 new packages & 2 new shipping packages - ActualLoadQty = 0 are created

    Given load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value             | OPT.C_BPartner_Location_ID.Identifier |
      | dropShipWarehouse_7       | DropshipWarehouse | dropShipWarehouseLocation             |
    And load C_Currency by ISO Code:
      | C_Currency_ID.Identifier | ISO_Code |
      | currency                 | EUR      |
    And metasfresh contains M_PriceLists
      | Identifier     | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                            | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | priceList_PO_7 | pricingSystem                 | DE                        | EUR                 | PriceListNamePOOrderDI_09022023 | false | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier            | M_PriceList_ID.Identifier | Name               | ValidFrom  |
      | priceListVersion_PO_7 | priceList_PO_7            | PurchaseOrder-PLV7 | 2023-02-01 |
    And metasfresh contains C_BPartners without locations:
      | Identifier | Name                   | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | vendor_7   | VendorOrderDI_09022023 | Y            | N              | pricingSystem                 |
    And metasfresh contains C_BPartner_Locations:
      | Identifier       | GLN           | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipToDefault |
      | vendorLocation_7 | 1232067899346 | vendor_7                 | true                | true                |
    And metasfresh contains M_Products:
      | Identifier | Name                        | OPT.M_Product_Category_ID.Identifier |
      | product_7  | ProductNameOrderDI_09022023 | standardCategory                     |
    And metasfresh contains M_ProductPrices
      | Identifier        | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | productPrice_PO_7 | priceListVersion_PO_7             | product_7               | 5.0      | PCE               | Normal                        |
      | productPrice_SO_7 | priceListVersion_SO               | product_7               | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartner_Products:
      | C_BPartner_ID.Identifier | M_Product_ID.Identifier | OPT.IsCurrentVendor |
      | vendor_7                 | product_7               | true                |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier |
      | orderSO_7  | true    | customer                 | 2023-02-09  | customerLocation                      | dropShipWarehouse_7           |
    And metasfresh contains M_AttributeSetInstance with identifier "lineASI_7.1":
  """
  {
    "attributeInstances":[
      {
        "attributeCode":"1000002",
          "valueStr":"Bio"
      }
    ]
  }
  """
    And metasfresh contains C_OrderLines:
      | Identifier    | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.DateOrdered | OPT.DatePromised     | OPT.M_AttributeSetInstance_ID.Identifier | OPT.C_BPartner_ID.Identifier | OPT.C_Currency_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier |
      | orderLine_7.1 | orderSO_7             | product_7               | 2          | 2023-02-09      | 2023-05-10T00:00:00Z | lineASI_7.1                              | customer                     | currency                     | customerLocation                      |

    When the order identified by orderSO_7 is completed

    Then after not more than 30s, M_ShipmentSchedules are found:
      | Identifier           | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentSchedule_7.1 | orderLine_7.1             | N             |
    And after not more than 30s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID.Identifiers | C_OrderLine_ID.Identifier |
      | deliveryPlanningSO_7.1             | orderLine_7.1             |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID.Identifier | QtyOrdered | QtyTotalOpen | M_Delivery_Planning_Type | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.C_BPartner_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.PlannedDeliveryDate | OPT.IsClosed | OPT.Processed | OPT.M_Warehouse_ID.Identifier |
      | deliveryPlanningSO_7.1            | 2          | 2            | Outgoing                 | orderSO_7                 | orderLine_7.1                 | customer                     | product_7                   | customerLocation                      | 2023-05-10              | false        | false         | dropShipWarehouse_7           |

    When the order identified by orderSO_7 is reactivated

    Then metasfresh contains M_AttributeSetInstance with identifier "lineASI_7.2":
  """
  {
    "attributeInstances":[
      {
        "attributeCode":"1000015",
          "valueStr":"02"
      }
    ]
  }
  """
    And metasfresh contains C_OrderLines:
      | Identifier    | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.DateOrdered | OPT.DatePromised     | OPT.M_AttributeSetInstance_ID.Identifier | OPT.C_BPartner_ID.Identifier | OPT.C_Currency_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier |
      | orderLine_7.2 | orderSO_7             | product_7               | 4          | 2023-02-09      | 2023-04-10T00:00:00Z | lineASI_7.2                              | customer                     | currency                     | customerLocation                      |

    When the order identified by orderSO_7 is completed

    Then after not more than 30s, M_ShipmentSchedules are found:
      | Identifier           | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentSchedule_7.1 | orderLine_7.1             | N             |
      | shipmentSchedule_7.2 | orderLine_7.2             | N             |
    And after not more than 30s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID.Identifiers | C_OrderLine_ID.Identifier |
      | deliveryPlanningSO_7.1             | orderLine_7.1             |
      | deliveryPlanningSO_7.2             | orderLine_7.2             |

    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID.Identifier | QtyOrdered | QtyTotalOpen | M_Delivery_Planning_Type | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.C_BPartner_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.PlannedDeliveryDate | OPT.IsClosed | OPT.Processed | OPT.M_Warehouse_ID.Identifier |
      | deliveryPlanningSO_7.1            | 2          | 2            | Outgoing                 | orderSO_7                 | orderLine_7.1                 | customer                     | product_7                   | customerLocation                      | 2023-05-10              | false        | false         | dropShipWarehouse_7           |
      | deliveryPlanningSO_7.2            | 4          | 4            | Outgoing                 | orderSO_7                 | orderLine_7.2                 | customer                     | product_7                   | customerLocation                      | 2023-04-10              | false        | false         | dropShipWarehouse_7           |

    And update M_Delivery_Planning:
      | M_Delivery_Planning_ID.Identifier | OPT.M_Shipper_ID.Identifier |
      | deliveryPlanningSO_7.1            | shipper_DHL                 |
      | deliveryPlanningSO_7.2            | shipper_DPD                 |

    When generate PO from SO is invoked with parameters:
      | C_BPartner_ID.Identifier | C_Order_ID.Identifier | PurchaseType |
      | vendor_7                 | orderSO_7             | Dropship     |

    Then the order is created:
      | Link_Order_ID.Identifier | IsSOTrx | DocBaseType | DocSubType | OPT.IsDropShip | OPT.DocStatus | OPT.DropShip_BPartner_ID.Identifier |
      | orderSO_7                | false   | POO         |            | true           | DR            | customer                            |
    And the purchase order 'orderPO_7' with document subtype '' linked to order 'orderSO_7' has lines:
      | OPT.C_OrderLine_ID.Identifier | QtyOrdered | LineNetAmt | M_Product_ID.Identifier | OPT.C_BPartner_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.DatePromised     |
      | orderLinePO_7.1               | 2          | 10         | product_7               | vendor_7                     | dropShipWarehouse_7           | 2023-05-10T00:00:00Z |
      | orderLinePO_7.2               | 4          | 20         | product_7               | vendor_7                     | dropShipWarehouse_7           | 2023-04-10T00:00:00Z |

    When the order identified by orderPO_7 is completed

    Then after not more than 30s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier |
      | receiptSchedule_7.1             | orderPO_7             | orderLinePO_7.1           | vendor_7                 | vendorLocation_7                  | product_7               | 2          | dropShipWarehouse_7       |
      | receiptSchedule_7.2             | orderPO_7             | orderLinePO_7.2           | vendor_7                 | vendorLocation_7                  | product_7               | 4          | dropShipWarehouse_7       |
    And after not more than 30s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID.Identifiers | C_OrderLine_ID.Identifier |
      | deliveryPlanningPO_7.1             | orderLinePO_7.1           |
      | deliveryPlanningPO_7.2             | orderLinePO_7.2           |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID.Identifier | QtyOrdered | QtyTotalOpen | IsB2B | M_Delivery_Planning_Type | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.C_BPartner_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.PlannedDeliveryDate | OPT.IsClosed | OPT.Processed | OPT.M_Warehouse_ID.Identifier |
      | deliveryPlanningPO_7.1            | 2          | 2            | true  | Incoming                 | orderPO_7                 | orderLinePO_7.1               | vendor_7                     | product_7                   | dropShipWarehouseLocation             | 2023-05-10              | false        | false         | dropShipWarehouse_7           |
      | deliveryPlanningPO_7.2            | 4          | 4            | true  | Incoming                 | orderPO_7                 | orderLinePO_7.2               | vendor_7                     | product_7                   | dropShipWarehouseLocation             | 2023-04-10              | false        | false         | dropShipWarehouse_7           |

    When generate M_ShipperTransportation for M_Delivery_Planning:
      | M_ShipperTransportation_ID.Identifier | M_Delivery_Planning_ID.Identifier |
      | deliveryInstructionSO_7.1             | deliveryPlanningSO_7.1            |

    And validate M_ShipperTransportation:
      | M_ShipperTransportation_ID.Identifier | M_Shipper_ID.Identifier | Shipper_BPartner_ID.Identifier | Shipper_Location_ID.Identifier | OPT.C_BPartner_Location_Delivery_ID.Identifier | OPT.C_BPartner_Location_Loading_ID.Identifier | OPT.DeliveryDate | OPT.DocStatus |
      | deliveryInstructionSO_7.1             | shipper_DHL             | customer                       | customerLocation               | customerLocation                               | dropShipWarehouseLocation                     | 2023-05-10       | CO            |
    And load M_Package for M_ShipperTransportation: deliveryInstructionSO_7.1
      | M_Package_ID.Identifier | M_Product_ID.Identifier |
      | packageLineSO_7.1       | product_7               |
    And validate M_Package:
      | M_Package_ID.Identifier | M_Shipper_ID.Identifier | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.ShipDate |
      | packageLineSO_7.1       | shipper_DHL             | customer                     | customerLocation                      | 2023-02-01   |
    And load M_Shipping_Package:
      | M_ShippingPackage_ID.Identifier | M_Package_ID.Identifier | M_ShipperTransportation_ID.Identifier |
      | shippingPackageSO_7.1           | packageLineSO_7.1       | deliveryInstructionSO_7.1             |
    And validate M_Shipping_Package:
      | M_ShippingPackage_ID.Identifier | M_Package_ID.Identifier | M_ShipperTransportation_ID.Identifier | C_BPartner_Location_ID.Identifier | ActualLoadQty | OPT.C_BPartner_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.C_OrderLine_ID.Identifier |
      | shippingPackageSO_7.1           | packageLineSO_7.1       | deliveryInstructionSO_7.1             | customerLocation                  | 0             | customer                     | product_7                   | orderLine_7.1                 |

    When generate M_ShipperTransportation for M_Delivery_Planning:
      | M_ShipperTransportation_ID.Identifier | M_Delivery_Planning_ID.Identifier |
      | deliveryInstructionSO_7.2             | deliveryPlanningSO_7.2            |

    Then validate M_ShipperTransportation:
      | M_ShipperTransportation_ID.Identifier | M_Shipper_ID.Identifier | Shipper_BPartner_ID.Identifier | Shipper_Location_ID.Identifier | OPT.C_BPartner_Location_Delivery_ID.Identifier | OPT.C_BPartner_Location_Loading_ID.Identifier | OPT.DeliveryDate | OPT.DocStatus |
      | deliveryInstructionSO_7.2             | shipper_DPD             | customer                       | customerLocation               | customerLocation                               | dropShipWarehouseLocation                     | 2023-04-10       | CO            |
    And load M_Package for M_ShipperTransportation: deliveryInstructionSO_7.2
      | M_Package_ID.Identifier | M_Product_ID.Identifier |
      | packageLineSO_7.2       | product_7               |
    And validate M_Package:
      | M_Package_ID.Identifier | M_Shipper_ID.Identifier | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.ShipDate |
      | packageLineSO_7.2       | shipper_DPD             | customer                     | customerLocation                      | 2023-02-01   |
    And load M_Shipping_Package:
      | M_ShippingPackage_ID.Identifier | M_Package_ID.Identifier | M_ShipperTransportation_ID.Identifier |
      | shippingPackageSO_7.2           | packageLineSO_7.2       | deliveryInstructionSO_7.2             |
    And validate M_Shipping_Package:
      | M_ShippingPackage_ID.Identifier | M_Package_ID.Identifier | M_ShipperTransportation_ID.Identifier | C_BPartner_Location_ID.Identifier | ActualLoadQty | OPT.C_BPartner_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.C_OrderLine_ID.Identifier |
      | shippingPackageSO_7.2           | packageLineSO_7.2       | deliveryInstructionSO_7.2             | customerLocation                  | 0             | customer                     | product_7                   | orderLine_7.2                 |
    And update M_Delivery_Planning:
      | M_Delivery_Planning_ID.Identifier | OPT.M_Shipper_ID.Identifier |
      | deliveryPlanningPO_7.1            | shipper_DHL                 |
      | deliveryPlanningPO_7.2            | shipper_DPD                 |

    When generate M_ShipperTransportation for M_Delivery_Planning:
      | M_ShipperTransportation_ID.Identifier | M_Delivery_Planning_ID.Identifier |
      | deliveryInstructionPO_7.1             | deliveryPlanningPO_7.1            |

    Then validate M_ShipperTransportation:
      | M_ShipperTransportation_ID.Identifier | M_Shipper_ID.Identifier | Shipper_BPartner_ID.Identifier | Shipper_Location_ID.Identifier | OPT.C_BPartner_Location_Delivery_ID.Identifier | OPT.C_BPartner_Location_Loading_ID.Identifier | OPT.DeliveryDate | OPT.DocStatus |
      | deliveryInstructionPO_7.1             | shipper_DHL             | vendor_7                       | dropShipWarehouseLocation      | dropShipWarehouseLocation                      | vendorLocation_7                              | 2023-05-10       | CO            |
    And load M_Package for M_ShipperTransportation: deliveryInstructionPO_7.1
      | M_Package_ID.Identifier | M_Product_ID.Identifier |
      | packageLinePO_7.1       | product_7               |
    And validate M_Package:
      | M_Package_ID.Identifier | M_Shipper_ID.Identifier | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.ShipDate |
      | packageLinePO_7.1       | shipper_DHL             | vendor_7                     | dropShipWarehouseLocation             | 2023-02-01   |
    And load M_Shipping_Package:
      | M_ShippingPackage_ID.Identifier | M_Package_ID.Identifier | M_ShipperTransportation_ID.Identifier |
      | shippingPackagePO_7.1           | packageLinePO_7.1       | deliveryInstructionPO_7.1             |
    And validate M_Shipping_Package:
      | M_ShippingPackage_ID.Identifier | M_Package_ID.Identifier | M_ShipperTransportation_ID.Identifier | C_BPartner_Location_ID.Identifier | ActualLoadQty | OPT.C_BPartner_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.C_OrderLine_ID.Identifier |
      | shippingPackagePO_7.1           | packageLinePO_7.1       | deliveryInstructionPO_7.1             | dropShipWarehouseLocation         | 0             | vendor_7                     | product_7                   | orderLinePO_7.1               |

    When generate M_ShipperTransportation for M_Delivery_Planning:
      | M_ShipperTransportation_ID.Identifier | M_Delivery_Planning_ID.Identifier |
      | deliveryInstructionPO_7.2             | deliveryPlanningPO_7.2            |

    Then validate M_ShipperTransportation:
      | M_ShipperTransportation_ID.Identifier | M_Shipper_ID.Identifier | Shipper_BPartner_ID.Identifier | Shipper_Location_ID.Identifier | OPT.C_BPartner_Location_Delivery_ID.Identifier | OPT.C_BPartner_Location_Loading_ID.Identifier | OPT.DeliveryDate | OPT.DocStatus |
      | deliveryInstructionPO_7.2             | shipper_DPD             | vendor_7                       | dropShipWarehouseLocation      | dropShipWarehouseLocation                      | vendorLocation_7                              | 2023-04-10       | CO            |
    And load M_Package for M_ShipperTransportation: deliveryInstructionPO_7.2
      | M_Package_ID.Identifier | M_Product_ID.Identifier |
      | packageLinePO_7.2       | product_7               |
    And validate M_Package:
      | M_Package_ID.Identifier | M_Shipper_ID.Identifier | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.ShipDate |
      | packageLinePO_7.2       | shipper_DPD             | vendor_7                     | dropShipWarehouseLocation             | 2023-02-01   |
    And load M_Shipping_Package:
      | M_ShippingPackage_ID.Identifier | M_Package_ID.Identifier | M_ShipperTransportation_ID.Identifier |
      | shippingPackagePO_7.2           | packageLinePO_7.2       | deliveryInstructionPO_7.2             |
    And validate M_Shipping_Package:
      | M_ShippingPackage_ID.Identifier | M_Package_ID.Identifier | M_ShipperTransportation_ID.Identifier | C_BPartner_Location_ID.Identifier | ActualLoadQty | OPT.C_BPartner_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.C_OrderLine_ID.Identifier |
      | shippingPackagePO_7.2           | packageLinePO_7.2       | deliveryInstructionPO_7.2             | dropShipWarehouseLocation         | 0             | vendor_7                     | product_7                   | orderLinePO_7.2               |