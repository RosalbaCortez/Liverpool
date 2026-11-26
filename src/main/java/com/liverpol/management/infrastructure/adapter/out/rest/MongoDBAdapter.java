package com.liverpol.management.infrastructure.adapter.out.rest;

import com.liverpol.management.domain.model.dto.Client;
import com.liverpol.management.domain.model.dto.Order;
import com.liverpol.management.domain.model.port.output.ClientExternalPort;
import com.liverpol.management.infrastructure.adapter.out.persistence.ClientDocument;
import com.liverpol.management.infrastructure.adapter.out.persistence.ClientPersistenceMapper;
import com.liverpol.management.infrastructure.adapter.out.persistence.SpringDataClienteRepository;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.Optional;


@Component
public class MongoDBAdapter implements ClientExternalPort {

    private final SpringDataClienteRepository repository;
    private final MongoTemplate mongoTemplate;
    private final ClientPersistenceMapper clientPersistenceMapper;

    public MongoDBAdapter(SpringDataClienteRepository repository, MongoTemplate mongoTemplate, ClientPersistenceMapper clientPersistenceMapper) {
        this.repository = repository;
        this.mongoTemplate = mongoTemplate;
        this.clientPersistenceMapper = clientPersistenceMapper;
    }

    @Override
    public Client save(Client client) {
        ClientDocument document = clientPersistenceMapper.clientToDocument(client);
        ClientDocument savedDocument = repository.save(document);
        return clientPersistenceMapper.clientDocumentToClient(savedDocument);
    }

    @Override
    public Optional<Client> findByID(String userId) {
        return repository.findById(userId).map(clientPersistenceMapper::clientDocumentToClient);
    }

    @Override
    public Client updateClient(String idClient, Client client) {
        return null;
    }

    @Override
    public Optional<Client> updateOrdersByUserId(String userId, List<Order> orders) {
        Query query = Query.query(Criteria.where("userId").is(userId));
        Update update = new Update().set("orders", orders != null ? orders : List.of());
        FindAndModifyOptions options = FindAndModifyOptions.options().returnNew(true);
        ClientDocument updated = mongoTemplate.findAndModify(query, update, options, ClientDocument.class);
        return Optional.ofNullable(updated).map(clientPersistenceMapper::clientDocumentToClient);
    }


}
