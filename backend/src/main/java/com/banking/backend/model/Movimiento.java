package com.banking.backend.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "movimientos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movimiento {
    @Id
    private String id;

    private String cuentaId; // ID de la cuenta a la que pertenece el movimiento
    private Double monto;
    private String tipo; // Ej: "DEPOSITO", "RETIRO", "TRANSFERENCIA"
    private LocalDateTime fecha;
    private String descripcion;
}

// This class represents a financial transaction (movement) in the banking system. It includes fields for the transaction ID, associated account ID, amount, type of transaction, date, and a description. The class uses Lombok annotations for boilerplate code reduction and is mapped to a MongoDB collection named "movimientos". The `LocalDateTime` type is used for the date field to capture both date and time of the transaction.