package com.liverpol.management.domain.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Detalle del Producto")
public record ItemProduct(
        @Schema(description = "Identificador del Producto", example = "3010091676-1132351437")
        String itemId,
        @Schema(description = "Variante del Producto", example = "1179743767")
        String skuId,
        @Schema(description = "Cantidad", example = "1")
        String quantity,
        @Schema(description = "Nombre del Producto", example = "Vasos cristal 250ml")
        String displayName,
        @Schema(description = "Estatus del Pedido", example = "Compra en tienda")
        String deliveryStatus,
        @Schema(description = "Identificador interno", example = "1")
        String id) {
}
