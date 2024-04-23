package com.example.accessingdatamongodb.documents;

import com.example.accessingdatamongodb.enums.Currency;

import java.math.BigDecimal;

public class Monetary {
  BigDecimal value;
  Currency currency;

  public Monetary(){}

  public Monetary(BigDecimal value, Currency currency){
    this.value=value;
    this.currency=currency;
  }

  public Monetary(Double value, Currency currency){
    var big = new BigDecimal(value);
    this.value=big;
    this.currency=currency;
  }
}
