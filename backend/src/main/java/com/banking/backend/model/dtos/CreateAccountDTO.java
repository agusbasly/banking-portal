package com.banking.backend.model.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAccountDTO {
    @NotBlank(message = "El número de cuenta no puede estar vacío")
    @Pattern(regexp = "^[0-9]{8}$", message = "Debe tener exactamente 8 dígitos")
    private String numeroCuenta;

    @NotBlank(message = "El nombre del dueño es obligatorio")
    private String nombreDueno;

    @Email(message = "El email no es válido")
    private String email;

    private Double balance;

}
