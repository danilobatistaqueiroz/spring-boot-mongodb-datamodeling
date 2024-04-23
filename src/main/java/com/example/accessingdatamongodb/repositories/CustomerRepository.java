package com.example.accessingdatamongodb.repositories;

import java.util.List;

import com.example.accessingdatamongodb.documents.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerRepository extends MongoRepository<Customer, String> {

	public Customer findByFirstName(String firstName);
	public List<Customer> findByLastName(String lastName);

}
