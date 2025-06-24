package com.banking.backend.model;

import lombok.*;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "accounts")
@Getter
@Setter
@NoArgsConstructor  
@AllArgsConstructor
@Builder
public class Account implements Serializable{

    private static final long serialVersionUID = 1L;    
    @Id
    private String id;

    @Indexed(unique = true) // Ensure that the account number is unique
    @Pattern(regexp = "^[0-9]+$", message = "El número de cuenta solo debe contener números")
    @Size(min = 8, max = 12, message = "El número de cuenta debe tener entre 8 y 12 dígitos")
    private String numeroCuenta;

    @NotBlank(message = "El nombre del dueño es obligatorio")
    private String nombreDueno;
    
    @Email(message = "El email no es válido")
    private String email;

    @Min(value = 0, message = "El balance no puede ser negativo")
    private Double balance;

}
