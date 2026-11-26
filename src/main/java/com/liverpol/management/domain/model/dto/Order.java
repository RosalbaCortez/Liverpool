package com.liverpol.management.domain.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "Detalle del Pedido")
public record Order(
        @Schema(description = "Identificador único del Producto", example = "3010091676-1132351437")
        String itemId,
        @Schema(description = "Cantidad", example = "3")
        int quantity,
        @Schema(description = "Canal de Venta", example = "online")
        String chanel,
        @Schema(description = "Fecha estimada de entrega", example = "2025-12-06")
        LocalDate dateStatus,
        @Schema(description = "Identificador único del Pedido", example = "3010091676")
        String orderRef
) {
}