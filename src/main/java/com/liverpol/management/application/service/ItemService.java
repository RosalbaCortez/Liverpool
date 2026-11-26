package com.liverpol.management.application.service;

import com.liverpol.management.application.usecase.GetItemUseCase;
import com.liverpol.management.domain.model.dto.ItemProduct;
import com.liverpol.management.infrastructure.adapter.out.rest.ItemMockApiAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService implements GetItemUseCase {

    private final ItemMockApiAdapter itemMockApiAdapter;

    @Override
    public List<ItemProduct> getProducts() {
        return itemMockApiAdapter.getMockApiItems();
    }
}
