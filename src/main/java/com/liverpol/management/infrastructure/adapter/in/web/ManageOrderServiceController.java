package com.liverpol.management.infrastructure.adapter.in.web;

import com.liverpol.management.application.service.ManageOrderService;
import com.liverpol.management.domain.model.dto.Item;
import com.liverpol.management.domain.model.dto.Order;
import com.liverpol.management.domain.model.dto.OrderDTO;
import com.liverpol.management.domain.model.dto.OrderStoreDTO;
import com.liverpol.management.domain.model.validation.ValidOrderRefPrefix;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class ManageOrderServiceController {
    private final ManageOrderService manageOrderService;


    @Operation(
            summary = "Search all Orders with associated Items",
            description = "Return Orders with associated Items"
    )
    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getOrders() {
        return ResponseEntity.ok(manageOrderService.getOrderItems());
    }


    /**
     * Obtiene las órdenes filtradas por orderRef (Número de órden)}.
     *
     * @param orderRef texto exacto con el número de la órden
     * @return respuesta {@code 200 OK} con la lista de {@link OrderDTO} que coinciden con el criterio
     */
    @Operation(
            summary = "Search Orders",
            description = "Return Orders with associated Items searching by orderRef"
    )
    @GetMapping(value = "/orders", params = "orderRef")
    public ResponseEntity<List<OrderDTO>> getOrdersByRef(@RequestParam("orderRef") String orderRef) {
        return ResponseEntity.ok(manageOrderService.getOrderByRef(orderRef));
    }

    /**
     * Obtiene las órdenes filtradas por la fecha}.
     *
     * @param orderStatus texto exacto de la órden
     * @return respuesta {@code 200 OK} con la lista de {@link OrderDTO} que coinciden con el criterio
     */
    @Operation(
            summary = "Search Orders",
            description = "Return Orders with associated Items searching by OrderStatus"
    )
    @GetMapping(value = "/orders", params = "orderStatus")
    public ResponseEntity<List<OrderDTO>> getOrderByStatus(@RequestParam(name = "orderStatus", required = false) @ValidOrderRefPrefix String orderStatus) {
        return ResponseEntity.ok(manageOrderService.getOrderByStatus(orderStatus));
    }


    /**
     * Obtiene las órdenes filtradas de forma flexible ({@code type ahead}) por {@code storeName}.
     *
     * <p>La búsqueda ignora diferencias de mayúsculas/minúsculas, acentos, puntuación y errores
     * menores de ortografía.</p>
     *
     * @param storeName texto parcial o aproximado del nombre de la Tienda; si llega vacío o en blanco,
     *                  se devuelven todos los artículos disponibles
     * @return respuesta {@code 200 OK} con la lista de {@link OrderStoreDTO} que coinciden con el criterio
     */
    @Operation(
            summary = "Search Orders",
            description = "Return Orders with associated with Stored Name"
    )
    @GetMapping(value = "/orders", params = "storeName")
    public ResponseEntity<List<OrderStoreDTO>> getOrderByStoreName(@RequestParam(name = "storeName", required = false) String storeName) {

        return ResponseEntity.ok(manageOrderService.getOrderByStoreName(storeName));
    }

    /**
     * Obtiene los artículos filtrados de forma flexible ({@code type ahead}) por {@code displayName}.
     *
     * <p>La búsqueda ignora diferencias de mayúsculas/minúsculas, acentos, puntuación y errores
     * menores de ortografía, y delega la consulta y el filtrado a {@code manageOrderService}.</p>
     *
     * @param displayName texto parcial o aproximado del nombre del artículo; si llega vacío o en blanco,
     *                    se devuelven todos los artículos disponibles
     * @return respuesta {@code 200 OK} con la lista de {@link Item} que coinciden con el criterio
     */
    @Operation(
            summary = "Search Items by displayName",
            description = "Search Items by displayName"
    )
    @GetMapping(value = "/items", params = "displayName")
    public ResponseEntity<List<Item>> getItemsByDisplayName(@RequestParam(name = "displayName", required = false) String displayName) {
        return ResponseEntity.ok(manageOrderService.getItemsByDisplayName(displayName));
    }
}
