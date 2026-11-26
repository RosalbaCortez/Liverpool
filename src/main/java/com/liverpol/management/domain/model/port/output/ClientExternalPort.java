package com.liverpol.management.domain.model.port.output;

import com.liverpol.management.domain.model.dto.Client;
import com.liverpol.management.domain.model.dto.Order;

import java.util.List;
import java.util.Optional;

public interface ClientExternalPort {
    public Client save(Client client);

    public Optional<Client> findByID(String userId);

    public Client updateClient(String idClient, Client client);

    public Optional<Client> updateOrdersByUserId(String userId, List<Order> orders);
}
