package com.example.accessingdatamongodb.repositories;

import com.example.accessingdatamongodb.documents.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
