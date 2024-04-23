package com.example.accessingdatamongodb.documents;

import org.springframework.data.annotation.Id;

public class Product {
  @Id
  public String id;
  public Monetary boughtPrice;
  public Monetary sellingPrice;
  public Integer weight;
  public String name;
  public String description;
  public Long barCode;
  private Product(String name, String description){
    this.name=name;
    this.description=description;
  }
  public static Product addProduct(String name, String description) throws Exception {
    validate(name,description);
    var product = new Product(name,description);
    return product;
  }
  private static void validate(String name, String description) throws Exception {
    if(name == null || name.isBlank()){
      throw new Exception("name is required!");
    }
  }
}
