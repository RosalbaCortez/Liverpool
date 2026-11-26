package com.liverpol.management.application.usecase;

import com.liverpol.management.domain.model.dto.Client;

public interface GetClient {

    public Client findByID(String userId);
}
