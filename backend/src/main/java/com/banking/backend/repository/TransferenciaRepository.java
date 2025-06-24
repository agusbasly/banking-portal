package com.banking.backend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.banking.backend.model.Transferencia;

@Repository
public interface TransferenciaRepository extends MongoRepository<Transferencia, String> {
}
