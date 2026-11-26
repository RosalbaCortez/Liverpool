package com.liverpol.management.infrastructure.adapter.out.persistence;

import com.liverpol.management.domain.model.dto.Client;
import com.liverpol.management.domain.model.dto.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class ClientPersistenceMapper {


    public ClientDocument clientToDocument(Client client) {
        ClientDocument clientDocument = new ClientDocument();
        clientDocument.setUserId(client.getUserId());
        clientDocument.setApellidoMaterno(client.getApellidoMaterno());
        clientDocument.setApellidoPaterno(client.getApellidoPaterno());
        clientDocument.setEmail(client.getEmail());
        clientDocument.setNombre(client.getNombre());
        clientDocument.setOrders(client.getOrders() == null ? new ArrayList<>() : new ArrayList<Order>(client.getOrders()));
        return clientDocument;
    }

    public Client clientDocumentToClient(ClientDocument clientDocument) {
        Client client = new Client();
        client.setUserId(clientDocument.getUserId());
        client.setApellidoMaterno(clientDocument.getApellidoMaterno());
        client.setApellidoPaterno(clientDocument.getApellidoPaterno());
        client.setEmail(clientDocument.getEmail());
        client.setNombre(clientDocument.getNombre());
        client.setOrders(clientDocument.getOrders() == null ? new ArrayList<>() : new ArrayList<Order>(clientDocument.getOrders()));
        return client;
    }

}
