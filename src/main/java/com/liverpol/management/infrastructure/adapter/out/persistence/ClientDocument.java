package com.liverpol.management.infrastructure.adapter.out.persistence;

import com.liverpol.management.domain.model.dto.Order;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Document(collection = "clients")
public class ClientDocument {
    @Id
    private String userId;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String email;
    private List<Order> orders;


}
