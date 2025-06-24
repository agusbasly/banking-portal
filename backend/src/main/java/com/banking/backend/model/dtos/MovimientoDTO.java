package com.banking.backend.model.dtos;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class MovimientoDTO {
    private String cuentaId;
    private String tipo;
    private Double monto;
    private String descripcion;
    private LocalDateTime fecha;
}
