package com.example.accessingdatamongodb.documents;

import com.example.accessingdatamongodb.enums.ShipMethod;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Document
public class Customer {

	@Id
	public String id;
	public String firstName;
	public String lastName;
	public ZonedDateTime dateFirstPurchase;
	public Integer age;
	public List<CustomerAddress> addresses;
	public Monetary credits;

	//******* lazy=true, it's to delay the retrieval of the orders until first access of the property
	@DocumentReference(collection="Order",lazy=true)
	private List<Order> orders = new ArrayList<>();
	//******** It'll be an array of strings into the document with a link to the order document

	//******** to improve performance if many customers have a lot of purchases, remove @DocumentReference and create a new embedded document
	//public List<CustomerOrder> orders = new ArrayList<>();
	//class CustomerOrder would have only needed fields, e.g., order id and total amount
  //********

	public Customer() {}

	public static Customer createNewCustomer(String firstName, String lastName, Integer age) throws Exception {
		validate(firstName, lastName, age);
		var customer = new Customer();
		customer.firstName = firstName;
		customer.lastName = lastName;
		customer.age = age;
		customer.addresses = new ArrayList<>();
		return customer;
	}

	public List<Order> getOrders(){
		return Collections.unmodifiableList(orders);
	}

	public void addOrder(Order order) throws Exception {
		orders.add(order);
	}

	@Override
	public String toString() {
		return String.format(
				"Customer[id=%s, firstName='%s', lastName='%s']",
				id, firstName, lastName);
	}

	public static void validate(String firstName, String lastName, Integer age) throws Exception {
		if(firstName==null || firstName.isEmpty() || firstName.isBlank()){
			throw new Exception("first name is required");
		}
		if(lastName==null || lastName.isEmpty() || lastName.isBlank()){
			throw new Exception("last name is required");
		}
		if(age==null){
			throw new Exception("age is required");
		}
	}

}

