package com.example.accessingdatamongodb;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import com.example.accessingdatamongodb.documents.Customer;
import com.example.accessingdatamongodb.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.data.domain.Example;

import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
public class CustomerRepositoryTests {

	@ServiceConnection
	static MongoDBContainer container = new MongoDBContainer("mongo:7.0.2");

	@Autowired
	CustomerRepository repository;

	Customer dave, oliver, carter;

	static {
		container.start();
	}

	@BeforeEach
	public void setUp() throws Exception {

		repository.deleteAll();

		dave = repository.save(Customer.createNewCustomer("Dave", "Matthews", 25));
		oliver = repository.save(Customer.createNewCustomer("Oliver August", "Matthews", 35));
		carter = repository.save(Customer.createNewCustomer("Carter", "Beauford", 45));
	}

	@Test
	public void setsIdOnSave() throws Exception {

		Customer dave = repository.save(Customer.createNewCustomer("Dave", "Matthews", 18));

		assertThat(dave.id).isNotNull();
	}

	@Test
	public void findsByLastName() {

		List<Customer> result = repository.findByLastName("Beauford");

		assertThat(result).hasSize(1).extracting("firstName").contains("Carter");
	}

	@Test
	public void findsByExample() throws Exception {

		Customer probe = new Customer();
		probe.lastName = "Matthews";

		List<Customer> result = repository.findAll(Example.of(probe));

		assertThat(result).hasSize(2).extracting("firstName").contains("Dave", "Oliver August");
	}
}
