package com.banking.backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.banking.backend.model.Account;
import com.banking.backend.model.dtos.CreateAccountDTO;
import com.banking.backend.repository.AccountRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account crearDesdeDTO(CreateAccountDTO dto) {
        log.info("Creando cuenta con número: {}, dueño: {}", dto.getNumeroCuenta(), dto.getNombreDueno());

        Account nueva = Account.builder()
                .numeroCuenta(dto.getNumeroCuenta())
                .nombreDueno(dto.getNombreDueno())
                .email(dto.getEmail())
                .balance(dto.getBalance() == null ? 0.0 : dto.getBalance())
                .build();

        Account saved = accountRepository.save(nueva);
        log.info("Cuenta creada con ID: {}", saved.getId());
        return saved;
    }

    public List<Account> obtenerAccounts() {
        log.info("Listando todas las cuentas");
        return accountRepository.findAll();
    }

    @Cacheable(value = "saldos", key = "#id")
    public Optional<Account> obtenerAccountPorId(String id) {
        log.info("Buscando cuenta con ID: {}", id);
        return accountRepository.findById(id);
    }

    @CachePut(value = "saldos", key = "#id")
    public Account actualizarSaldo(String id, Double nuevoSaldo) {
        log.info("Actualizando saldo de cuenta ID: {} a ${}", id, nuevoSaldo);

        Account acc = accountRepository.findById(id).orElseThrow(() -> {
            log.error("Cuenta no encontrada con ID: {}", id);
            return new RuntimeException("Cuenta no encontrada");
        });

        acc.setBalance(nuevoSaldo);
        Account updated = accountRepository.save(acc);
        log.info("Saldo actualizado para cuenta ID: {}. Nuevo saldo: {}", id, nuevoSaldo);
        return updated;
    }
    
    @CacheEvict(value = "saldos", key = "#id")
    public void eliminarAccount(String id) {
        log.info("Eliminando cuenta con ID: {}", id);
        accountRepository.deleteById(id);
        log.info("Cuenta eliminada con éxito: {}", id);
    }
}

