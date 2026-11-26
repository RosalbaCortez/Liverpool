package com.liverpol.management.domain.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Detalle de un Cliente")
public class Client {
    @Schema(description = "Identificador único del Cliente", example = "75c97531-abf5-4524-8107-90aa48d08efc")
    private String userId;
    @Schema(description = "Nombre del Cliente", example = "Martha")
    private String nombre;
    @Schema(description = "Apellido Paterno del Cliente", example = "Perez")
    private String apellidoPaterno;
    @Schema(description = "Apellido Materno del Cliente", example = "Martinez")
    private String apellidoMaterno;
    @Schema(description = "Correo Eletrónico del Cliente", example = "martha@gmail.com")
    private String email;
    @Schema(description = "Lista de Ordenes realizadas por el Usuario")
    private List<Order> orders;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }


}
