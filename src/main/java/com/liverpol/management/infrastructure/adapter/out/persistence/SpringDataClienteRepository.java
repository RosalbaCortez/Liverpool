package com.liverpol.management.infrastructure.adapter.out.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface SpringDataClienteRepository extends MongoRepository<ClientDocument, String> {
}
