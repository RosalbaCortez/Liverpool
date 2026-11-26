package com.liverpol.management.infrastructure.adapter.in.web;


import com.liverpol.management.application.service.ClientService;
import com.liverpol.management.domain.model.dto.Client;
import com.liverpol.management.domain.model.dto.Order;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Client", description = "Client´s operation.")
public class ClientController {
    private final ClientService clientService;

    @Operation(
            summary = "Find all orders associated with the userID",
            description = "Return Orders searching by userID."
    )
    @GetMapping("/clients/orders/{userID}")
    public ResponseEntity<List<Order>> getOrdersByUser(@PathVariable("userID") String userID) {
        return ResponseEntity.ok(clientService.getOrdersByUser(userID));
    }

    @Operation(
            summary = "Update the Order´s Client",
            description = "Given and userId find the Orders of this userID and Update de Client sending the orders"
    )
    @PostMapping("/clients/updateOrder/{userID}")
    public ResponseEntity<Client> updateClient(@PathVariable("userID") String userID) {
        return ResponseEntity.ok(clientService.updateOrdersClient(userID));
    }

    @Operation(
            summary = "Create new Client",
            description = "Save the Client."
    )
    @PostMapping("/clients/save")
    public ResponseEntity<Client> saveClient(@RequestBody Client client) {
        return ResponseEntity.ok(clientService.saveClient(client));
    }

    @Operation(
            summary = "Search a Client by userID",
            description = "Get the Client searching by userID"
    )
    @GetMapping("/client/find/{userID}")
    public ResponseEntity<Client> getClient(@PathVariable("userID") String userID) {
        return ResponseEntity.ok(clientService.findByID(userID));
    }
}
