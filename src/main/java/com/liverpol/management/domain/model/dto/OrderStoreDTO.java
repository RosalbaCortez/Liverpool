package com.liverpol.management.domain.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Schema(description = "Detalle del Pedido con la lista de productos asociadas")
public class OrderStoreDTO {
    @Schema(description = "Identificador único del Pedido", example = "3010091676")
    String orderRef;
    @Schema(description = "Identificador único del Cliente", example = "75c97531-abf5-4524-8107-90aa48d08efc")
    String userId;
    @Schema(description = "Canal de Venta", example = "online")
    String canal;
    @Schema(description = "Fecha estimada de entrega", example = "2025-12-06")
    String orderStatus;
    @Schema(description = "Nombre de la tienda", example = "Liverpool Galerías Toluca")
    String storeName;
    @Schema(description = "Productos asociados al Pedido")
    List<ItemDTO> items;
}
