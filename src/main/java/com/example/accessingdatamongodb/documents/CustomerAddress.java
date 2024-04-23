package com.example.accessingdatamongodb.documents;

import com.example.accessingdatamongodb.enums.AddressType;

public class CustomerAddress {

  public Integer unit;
  public String street;
  public String postalCode;
  public String city;
  public String state;
  public String country;
  public AddressType type;

  public CustomerAddress(Integer unit, String street, String postalCode, String city, String state, String country, AddressType type){
    this.unit = unit;
    this.street = street;
    this.postalCode = postalCode;
    this.city = city;
    this.state = state;
    this.country = country;
    this.type = type;
  }
}
