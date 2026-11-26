package com.liverpol.management.domain.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Producto")
public record Item(
        @Schema(description = "Identificador  del Producto", example = "20251216366900020031-1171500610")
        String itemId,
        @Schema(description = "Variante del Producto", example = "1179743767")
        String skuId,
        @Schema(description = "Cantidad", example = "1")
        String quantity,
        @Schema(description = "Nombre", example = "Control Xbox one")
        String displayName,
        @Schema(description = "Estatus", example = "Pedido entregado")
        String deliveryStatus,
        @Schema(description = "Identificador Iterno", example = "1")
        String id
) {
}
