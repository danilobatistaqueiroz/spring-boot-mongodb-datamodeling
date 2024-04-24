# Spring Boot and MongoDB

* This is a sample application using Spring Boot, Spring Data, MongoDB, Testcontainers.  

* This application demonstrates some data modeling using noSQL (document database).  

* Demonstrates how to work with data types ZonedDateTime, Long, Integer, String, Currency, Enums, Double, BigDecimal.  

* How to model one-to-many, one-to-one, many-to-many, embedded documents, embedded array of documents, reference to another document, reference to a list of another document.  

## Why MongoDB?

In many cases a relational database is a great option, or maybe the best one.  
But MongoDB has some advantages over relational databases:
* MongoDB uses javascript to query and to manipulate data, the data format used is based on JSON, this enables a smooth integration between systems using JSON and javascript.  
* The nature of Content Management, Collect of High Traffic Data, Blog, Chat, Ticket systems have dynamic data fitting very well with noSQL databases.  
* Ecommerce systems get the benefit of using aggregates, instead of creating auxiliary tables for properties that varies depending on the type of product, you can use embedded documents that can have many formats.  
* Tables from relation databases have an impedance to objects from systems, generally this problem is attenuated using ORMs, but this isn't solve the problem, using a document database as MongoDB, the structure of document can reflect the object from your system.
* Sharding - MongoDB is designed to work well with sharding, it's easy to implement:  
 https://www.percona.com/blog/an-overview-of-sharding-in-postgresql-and-how-it-relates-to-mongodbs/
* The schemaless or flexible schema nature is a great advantage on projects that the data structure changes over time, when using relational database to has a change in the table, the developer needs to get an approval from database administrators and meanwhile the change happens the database is stopped. 

## Sharding

When sharding is useful:
* To scale out (horizontally), when even after partitioning a table, the amount of data is too great or too complex to be processed by a single server.
* A common example of a scenario is of a company whose customers are evenly spread across the world and whose searches to a target table involves the customer country. A shard could then be used to host entries of customers located on the USA and another for customers on Brazil.
* Sharding can also be used to reduce the complexity of queries by dividing a large table into smaller, more manageable pieces. This can lead to faster query response times and improved performance for the end user.
* In some cases, sharding can be used to address regulatory or compliance requirements by physically separating data that has different levels of sensitivity or access requirements. This can help organizations meet their legal obligations and ensure that data is stored and accessed appropriately.

## Disadvantages of MongoDB

* PostgreSQL now can uses document store, key/value store, and then you can mix with tables solving many the problems related above.   
* You can scale PostgreSQL using synchronous or asynchronous, streaming, physical or logical replication, then you can horizontally scale your database.
* You can set up auto healing from fail over, automatically shutting down a broken master and promoting a slave to master, easily scaling horizontally.  
* MongoDB get very slow when the total amount of data in the database is more than the amount of ram memory in the server, relational databases manage very well this problem.  


## Prerequisites

* In order to start the application, a MongoDB database is required.
* The application contains a docker-compose file which can be used to start a MongoDB database with URI=mongodb://localhost:27017. This requires docker-compose to be installed (e.g. with Docker Desktop)

## Running the applicaton

* Enter in the mongo folder and type: `docker-compose up`

* Go to root folder and type: `./mvnw spring-boot:run`

## Running the tests

* The tests use MongoDB Testcontainer

* Go to root folder and type: `mvn clean install`