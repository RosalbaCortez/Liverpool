package com.liverpol.management.infrastructure.dto;

public record ItemMockApi(String itemId,
                          String skuId,
                          String quantity,
                          String displayName,
                          String deliveryStatus,
                          String id) {
}

