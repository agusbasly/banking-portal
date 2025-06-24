package com.banking.backend.repository;

import com.banking.backend.model.Movimiento;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovimientoRepository extends MongoRepository<Movimiento, String> {
    List<Movimiento> findByCuentaIdOrderByFechaDesc(String cuentaId);
}

// This repository interface extends MongoRepository to provide CRUD operations for Movimiento entities.
// It includes a method to find all movements associated with a specific account ID, ordered by date in descending order.
// The `Movimiento` class represents a financial transaction, and this repository allows for easy access to
// those transactions in the MongoDB database. The `String` type parameter indicates that the ID of the Movimiento is a String.