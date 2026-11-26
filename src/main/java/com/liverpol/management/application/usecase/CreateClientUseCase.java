package com.liverpol.management.application.usecase;

import com.liverpol.management.domain.model.dto.Client;

public interface CreateClientUseCase {
    public Client saveClient(Client client);
}
