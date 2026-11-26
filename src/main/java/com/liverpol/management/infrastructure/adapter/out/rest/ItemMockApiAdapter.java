package com.liverpol.management.infrastructure.adapter.out.rest;

import com.liverpol.management.domain.model.dto.ItemProduct;
import com.liverpol.management.domain.model.port.output.ItemExternalPort;
import com.liverpol.management.infrastructure.dto.ItemMockApi;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class ItemMockApiAdapter implements ItemExternalPort {
    private final RestClient restClient;

    public ItemMockApiAdapter() {
        this.restClient = RestClient.builder()
                .baseUrl("https://6994a4eab081bc23e9c0f61e.mockapi.io/api/v1")
                .build();
    }

    @Cacheable("itemsMock")
    @Override
    public List<ItemProduct> getMockApiItems() {
        List<ItemMockApi> itemsMockApi = restClient.get().uri("/items").retrieve().body(new ParameterizedTypeReference<List<ItemMockApi>>() {
        });
        List<ItemProduct> itemsProducts = itemsMockApi.stream().map(element -> new ItemProduct(element.itemId(), element.skuId(), element.quantity(), element.displayName(), element.deliveryStatus(), element.id())).toList();
        return itemsProducts;
    }

    @Cacheable("itemsMockAll")
    public List<ItemMockApi> getAllMockApiItems() {
        List<ItemMockApi> itemsMockApi = restClient.get().uri("/items").retrieve().body(new ParameterizedTypeReference<List<ItemMockApi>>() {
        });
        return itemsMockApi;
    }

}
