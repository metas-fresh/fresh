/*
 * Selling Partner API for Orders
 * The Selling Partner API for Orders helps you programmatically retrieve order information. These APIs let you develop fast, flexible, custom applications in areas like order synchronization, order research, and demand-based decision support tools.
 *
 * OpenAPI spec version: v0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package com.adekia.exchange.amazonsp.client.orders.model;

import com.google.gson.annotations.SerializedName;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

/**
 * OrderItemsInner
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2022-07-02T14:17:07.399+02:00")
public class OrderItemsInner {
  @SerializedName("orderItemId")
  private String orderItemId = null;

  @SerializedName("quantity")
  private Integer quantity = null;

  public OrderItemsInner orderItemId(String orderItemId) {
    this.orderItemId = orderItemId;
    return this;
  }

   /**
   * the unique identifier for the order item
   * @return orderItemId
  **/
  @Schema(description = "the unique identifier for the order item")
  public String getOrderItemId() {
    return orderItemId;
  }

  public void setOrderItemId(String orderItemId) {
    this.orderItemId = orderItemId;
  }

  public OrderItemsInner quantity(Integer quantity) {
    this.quantity = quantity;
    return this;
  }

   /**
   * the quantity of items that needs an update of the shipment status
   * @return quantity
  **/
  @Schema(description = "the quantity of items that needs an update of the shipment status")
  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrderItemsInner orderItemsInner = (OrderItemsInner) o;
    return Objects.equals(this.orderItemId, orderItemsInner.orderItemId) &&
        Objects.equals(this.quantity, orderItemsInner.quantity);
  }

  @Override
  public int hashCode() {
    return Objects.hash(orderItemId, quantity);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrderItemsInner {\n");
    
    sb.append("    orderItemId: ").append(toIndentedString(orderItemId)).append("\n");
    sb.append("    quantity: ").append(toIndentedString(quantity)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}
