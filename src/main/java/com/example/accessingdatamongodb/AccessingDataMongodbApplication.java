package com.example.accessingdatamongodb;

import com.example.accessingdatamongodb.documents.*;
import com.example.accessingdatamongodb.enums.AddressType;
import com.example.accessingdatamongodb.enums.Currency;
import com.example.accessingdatamongodb.enums.ShipMethod;
import com.example.accessingdatamongodb.repositories.CustomerRepository;
import com.example.accessingdatamongodb.repositories.OrderRepository;
import com.example.accessingdatamongodb.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;

@SpringBootApplication
public class AccessingDataMongodbApplication implements CommandLineRunner {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private ProductRepository productRepository;

	public static void main(String[] args) {
		SpringApplication.run(AccessingDataMongodbApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		customerRepository.deleteAll();

		// save a couple of customers
		var customer1 = Customer.createNewCustomer("Alice", "Smith", 30);
		customer1.addresses.add(new CustomerAddress(300,"Josephine Street","38111", "Pierre","Memphis","USA", AddressType.BILLING));
		customerRepository.save(customer1);
		var customer2 = Customer.createNewCustomer("Bob", "Smith", 40);
		customer2.addresses.add(new CustomerAddress(540,"Grand Ave","50312","Des Moines","Iowa","USA", AddressType.SHIP));
		customer2.addresses.add(new CustomerAddress(1205,"N Walters St","78202","San Antonio", "Texas","USA", AddressType.BILLING));
		customerRepository.save(customer2);

		System.out.println("\n\n");
		System.out.println("--------------------------------");

		// fetch all customers
		System.out.println("Customers found with findAll():");
		for (Customer customer : customerRepository.findAll()) {
			System.out.println(customer);
		}
		System.out.println("-------------------------------");
		System.out.println();

		// fetch an individual customer
		System.out.println("Customer found with findByFirstName('Alice'):");
		System.out.println(customerRepository.findByFirstName("Alice"));
		System.out.println("--------------------------------");
		System.out.println();

		System.out.println("Customers found with findByLastName('Smith'):");
		System.out.println("--------------------------------");
		for (Customer customer : customerRepository.findByLastName("Smith")) {
			System.out.println(customer);
		}
		System.out.println();

		orderRepository.deleteAll();

		ZoneId zone = ZoneId.of("America/Sao_Paulo");
		ZonedDateTime now = ZonedDateTime.now(zone);

		System.out.println("Orders and Customer");
		System.out.println("--------------------------------");

		//********* orders and customer ***********//
		Order order1 = Order.createNewOrder(customer1, ShipMethod.STANDARD_SHIPPING, new Monetary(100.10,Currency.BR_REAL));
		order1.orderDate = now;
		orderRepository.save(order1);

		Order order2 = Order.createNewOrder(customer1, ShipMethod.AIR_FREIGHT, new Monetary(10.00,Currency.US_DOLAR));
		order2.orderDate = now;
		order2.status = OrderStatus.PENDING;
		orderRepository.save(order2);

		//***** it is possible to use Customer.orders using document reference *****//
		customer1.addOrder(order1);
		customer1.addOrder(order2);
		customerRepository.save(customer1);

  	//****** but if the number of customers and orders increases a lot, you'll fall into a performance problem, to query all orders from customer, if many customers would be big company with many purchases ******//
		//****** one approach to improve the performance is to change (docref) List<Order> to a List<CustomerOrder> containing only some fields
		//****** CustomerOrder { public String id; public Monetary totalAmount; }

		System.out.println("Finding all orders from a customer with performance");
		System.out.println(customer1.getOrders().toString());
		System.out.println("--------------------------------");
		System.out.println();

		System.out.println("Orders found by status");
		System.out.println("--------------------------------");
		System.out.println(orderRepository.findByStatus(OrderStatus.PENDING).toString());
		System.out.println();

		System.out.println("All orders from a customer");
		System.out.println("--------------------------------");
		System.out.println(orderRepository.findByCustomer(customer1).toString());
		System.out.println();

		var monetary = new Monetary(new BigDecimal(10200.50), Currency.BTC);
		customer1.credits = monetary;
		customerRepository.save(customer1);

		var customer3 = Customer.createNewCustomer("Bath", "Scott", 30);
		customer3.dateFirstPurchase = now;
		customer3.addresses.add(new CustomerAddress(300,"Race Street","115", "Columbus","Memphis","USA", AddressType.SHIP));
		customerRepository.save(customer3);

		System.out.println("--------------------------------");
		System.out.println("\n\n");

		var customer4 = Customer.createNewCustomer("Watter", "Wood", 50);
		customer4.dateFirstPurchase = ZonedDateTime.of(2000,10,1,11,30,5,1303,zone);
		customer4.credits = new Monetary(10000D,Currency.SATOSHI);
		customerRepository.save(customer4);
		var order3 = Order.createNewOrder(customer4,ShipMethod.AIR_FREIGHT,new Monetary(300D,Currency.SATOSHI));
		var product1 = Product.addProduct("camiseta nerd wolverine","camiseta marvel com desenho do wolverine");
		productRepository.save(product1);
		order3.addOrderItem(product1,3,new Monetary(300D,Currency.SATOSHI));
		var product2 = Product.addProduct("caneca java","caneca de javeiro");
		productRepository.save(product2);
		order3.addOrderItem(product2,1,new Monetary(10D,Currency.SATOSHI));
		customer4.addOrder(order3);
		orderRepository.save(order3);
		customerRepository.save(customer4);

		//printZonedDateTimeSamples();
	}

	private void printZonedDateTimeSamples() {
		System.out.println("\n\n");
		System.out.println("########################################################################## ZonedDateTime ##########################################################################");
		var losangeles = "America/Los_Angeles";
		ZoneId zoneLA = ZoneId.of(losangeles);
		var saopaulo = "America/Sao_Paulo";
		ZoneId zoneSP = ZoneId.of(saopaulo);

		var localFeb = LocalDateTime.of(2024,2,1,13,10,5,0);
		System.out.println(String.format("#################### Time offset when february %s %s",losangeles,zoneLA.getRules().getOffset(localFeb)));
		System.out.println(String.format("#################### Time offset when february %s %s",saopaulo,zoneSP.getRules().getOffset(localFeb)));
		var localApr = LocalDateTime.of(2024,4,22,13,10,5,0);
		System.out.println(String.format("#################### Time offset when april %s %s",losangeles,zoneLA.getRules().getOffset(localApr)));
		System.out.println(String.format("#################### Time offset when april %s %s",saopaulo,zoneSP.getRules().getOffset(localApr)));

		ZonedDateTime laZonedFeb = ZonedDateTime.of(localFeb,zoneLA);
		System.out.println("#################### TIME without daylight saving time");
		System.out.println(String.format("#################### TIME %s %s",losangeles,laZonedFeb));
		ZonedDateTime spZonedFeb = laZonedFeb.withZoneSameInstant(zoneSP);
		System.out.println(String.format("#################### TIME %s %s",saopaulo,spZonedFeb));
		System.out.println("#################### TIME when daylight saving time is on, in USA in 2024 is between March 10 and November 3, in Brazil doesnt have it");
		ZonedDateTime laApr = ZonedDateTime.of(localApr,zoneLA);
		System.out.println(String.format("#################### TIME %s %s",losangeles,laApr));
		ZonedDateTime spApr = laApr.withZoneSameInstant(zoneSP);
		System.out.println(String.format("#################### TIME %s %s",saopaulo,spApr));
		System.out.println("#################### Without daylight saving time (february) the difference between Los Angeles and Sao Paulo is 5 hours");
		System.out.println("#################### With daylight saving time (april) the difference between Los Angeles and Sao Paulo is 4 hours");

		//************* java.util.Date always uses a timezone, here prints BRT ***************//
		//************* if you change the timezone setting on Ubuntu the date value will change *************//
		var date = Date.from(laZonedFeb.toInstant());
		System.out.println("#################### Date Ubuntu timezone " + date);
		var zoned=  date.toInstant().atZone(ZoneOffset.UTC);
		System.out.println("#################### Date UTC timezone " + zoned);
		ZonedDateTime zonedDTsp1 = zoned.withZoneSameInstant(zoneSP);
		System.out.println("#################### ZonedDate SaoPaulo " + zonedDTsp1);
		var zonedDTsp=  date.toInstant().atZone(zoneSP);
		System.out.println("#################### ZonedDate SaoPaulo " + zonedDTsp);
		ZonedDateTime zonedDTla1 = zoned.withZoneSameInstant(zoneLA);
		System.out.println("#################### ZonedDate LosAngeles " + zonedDTla1);
		var zonedDTla=  date.toInstant().atZone(zoneLA);
		System.out.println("#################### ZonedDate LosAngeles " + zonedDTla);
		System.out.println("########################################################################## ZonedDateTime ##########################################################################");
		System.out.println("\n\n");
	}

}
