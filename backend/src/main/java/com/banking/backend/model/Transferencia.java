package com.banking.backend.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "transferencias")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Transferencia {
    @Id
    private String id;
    private String origenId;
    private String destinoId;
    private Double monto;
    private LocalDateTime fecha;
}
