package com.example.accessingdatamongodb.repositories;

import com.example.accessingdatamongodb.documents.Customer;
import com.example.accessingdatamongodb.documents.Order;
import com.example.accessingdatamongodb.documents.OrderStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {

  public List<Order> findByCustomer(Customer customer);

  public List<Order> findByStatus(OrderStatus status);
}
