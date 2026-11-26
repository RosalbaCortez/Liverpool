package com.liverpol.management.infrastructure.adapter.mapper;

import com.liverpol.management.domain.model.dto.Item;
import com.liverpol.management.infrastructure.dto.ItemMockApi;

public class ItemMapper {

    public static Item toDomain(ItemMockApi itemMockApi) {
        return new Item(itemMockApi.itemId(), itemMockApi.skuId(), itemMockApi.quantity(), itemMockApi.displayName(), itemMockApi.deliveryStatus(), itemMockApi.id());
    }
}
