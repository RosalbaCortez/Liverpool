package com.liverpol.management.domain.model.port.output;

import com.liverpol.management.domain.model.dto.ItemProduct;

import java.util.List;

public interface ItemExternalPort {
    public List<ItemProduct> getMockApiItems();
}
