package com.liverpol.management.application.usecase;

import com.liverpol.management.domain.model.dto.ItemProduct;

import java.util.List;

public interface GetItemUseCase {

    public List<ItemProduct> getProducts();

}
