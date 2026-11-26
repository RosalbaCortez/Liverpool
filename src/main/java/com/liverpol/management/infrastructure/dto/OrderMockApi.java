package com.liverpol.management.infrastructure.dto;

import java.time.LocalDate;
import java.util.List;

public record OrderMockApi(
        String id,
        String orderRef,
        String userId,
        String canal,
        LocalDate orderStatus,
        boolean marketPlace,
        List<String> items,
        String storeName
) {
}
