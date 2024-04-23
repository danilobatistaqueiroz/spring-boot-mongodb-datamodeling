package com.example.accessingdatamongodb.documents;

import com.example.accessingdatamongodb.enums.ShipMethod;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Order {

  @Id
  public String id;
  @DocumentReference
  Customer customer;
  public ZonedDateTime orderDate;
  LocalDateTime deliveredDate;
  ShipMethod shipMethod;
  Monetary totalAmount;
  public OrderStatus status;
  private List<OrderItem> orderItems = new ArrayList<>();

  public static Order createNewOrder(Customer customer, ShipMethod shipMethod, Monetary totalAmount) throws Exception {
    validate(shipMethod);
    var order = new Order();
    order.customer = customer;
    order.shipMethod = shipMethod;
    order.totalAmount = totalAmount;
    order.status = OrderStatus.DRAFT;
    return order;
  }

  public List<OrderItem> getOrderItems() {
    return Collections.unmodifiableList(orderItems);
  }

  public void addOrderItem(Product product, Integer quantity, Monetary discount) throws Exception {
    var item = OrderItem.createOrderItem(product,quantity,discount);
    orderItems.add(item);
  }

  private Order(){}

  public static void validate(ShipMethod shipMethod) throws Exception {
    if(shipMethod.equals(ShipMethod.SMART_MAIL)){
      throw new Exception("ship method smart mail is only for enterprises!");
    }
  }

  @Override
  public String toString(){
    return String.format("Order[id=%s, customer=%s, orderDate=%s, deliverdDate=%s, shipMethod=%s, totalAmount=%s, status=%s]", id, customer, orderDate, deliveredDate, shipMethod, totalAmount, status);
  }
}
