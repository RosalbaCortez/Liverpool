package com.liverpol.management.domain.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Producto con su Nombre")
public class ItemDTO {
    @Schema(description = "Identificador  del Producto", example = "20251216366900020031-1171500610")
    String item;
    @Schema(description = "Nombre del Producto", example = "Control Xbox one")
    String displayName;

}
