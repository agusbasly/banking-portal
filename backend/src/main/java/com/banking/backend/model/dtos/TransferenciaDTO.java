package com.banking.backend.model.dtos;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class TransferenciaDTO {
    
    @NotBlank(message = "El ID de la cuenta origen no puede estar vacío")
    private String origenId;

    @NotBlank(message = "El ID de la cuenta destino no puede estar vacío")
    private String destinoId;

    @NotNull(message = "El monto es obligatorio")
    @Min(value = 1, message = "El monto debe ser mayor a 0")
    private Double monto;
}
