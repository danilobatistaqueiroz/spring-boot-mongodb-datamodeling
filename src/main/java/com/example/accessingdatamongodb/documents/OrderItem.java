package com.example.accessingdatamongodb.documents;

public class OrderItem {
  String productId;
  String productName;
  Monetary unitPrice;
  Integer quantity;
  Monetary discount;

  public static OrderItem createOrderItem(Product product, Integer quantity, Monetary discount) throws Exception {
    String productId = product.id;
    String productName = product.name;
    Monetary unitPrice = product.sellingPrice;
    validate(productId, productName, unitPrice, quantity, discount);
    var item = new OrderItem(productId, productName, unitPrice, quantity, discount);
    return item;
  }

  private OrderItem(String productId, String productName, Monetary unitPrice, Integer quantity, Monetary discount){
    this.productId = productId;
    this.productName = productName;
    this.unitPrice = unitPrice;
    this.quantity = quantity;
    this.discount = discount;
  }
  private static void validate(String productId, String productName, Monetary unitPrice, Integer quantity, Monetary discount) throws Exception {
    if(productId == null){
      throw new Exception("product id is required!");
    }
  }
}
