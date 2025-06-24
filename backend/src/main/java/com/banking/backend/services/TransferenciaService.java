package com.banking.backend.services;

import com.banking.backend.model.Account;
import com.banking.backend.model.Transferencia;
import com.banking.backend.model.dtos.TransferenciaDTO;
import com.banking.backend.repository.AccountRepository;
import com.banking.backend.repository.TransferenciaRepository;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class TransferenciaService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransferenciaRepository transferenciaRepository;

    @Autowired
    private MovimientoService movimientoService;

    public Transferencia realizarTransferencia(TransferenciaDTO dto) {
    String origenId = dto.getOrigenId();
    String destinoId = dto.getDestinoId();
    Double monto = dto.getMonto();

    log.info("Iniciando transferencia: origen={}, destino={}, monto={}", origenId, destinoId, monto);

    // ✅ Validación del monto
    if (monto == null || monto <= 0) {
        throw new IllegalArgumentException("El monto debe ser positivo y distinto de cero");
    }

    try {
        // Buscar cuentas
        Account origen = accountRepository.findById(origenId)
                .orElseThrow(() -> {
                    log.error("Cuenta origen no encontrada: {}", origenId);
                    return new RuntimeException("Cuenta origen no encontrada");
                });

        Account destino = accountRepository.findById(destinoId)
                .orElseThrow(() -> {
                    log.error("Cuenta destino no encontrada: {}", destinoId);
                    return new RuntimeException("Cuenta destino no encontrada");
                });

        // Verificar fondos
        if (origen.getBalance() < monto) {
            log.warn("Saldo insuficiente en cuenta {}. Saldo actual: {}, Monto requerido: {}", 
                      origenId, origen.getBalance(), monto);
            throw new RuntimeException("Saldo insuficiente");
        }

        // Actualizar saldos
        origen.setBalance(origen.getBalance() - monto);
        destino.setBalance(destino.getBalance() + monto);
        accountRepository.save(origen);
        accountRepository.save(destino);
        log.info("Saldos actualizados: origen={}, nuevoSaldo={}, destino={}, nuevoSaldo={}",
                origenId, origen.getBalance(), destinoId, destino.getBalance());

        // Registrar transferencia
        Transferencia transferencia = Transferencia.builder()
                .origenId(origenId)
                .destinoId(destinoId)
                .monto(monto)
                .fecha(LocalDateTime.now())
                .build();
        transferenciaRepository.save(transferencia);
        log.info("Transferencia registrada con ID: {}", transferencia.getId());

        // Registrar movimientos
        movimientoService.registrarMovimiento(
                origenId,
                "ENVIO",
                -monto,
                "Transferencia enviada a la cuenta " + destinoId
        );
        movimientoService.registrarMovimiento(
                destinoId,
                "RECEPCION",
                monto,
                "Transferencia recibida de la cuenta " + origenId
        );
        log.info("Movimientos registrados para cuentas {} y {}", origenId, destinoId);

        return transferencia;

    } catch (Exception e) {
        log.error("Error durante la transferencia: {}", e.getMessage(), e);
        throw e;
    }
}


// This service handles the logic for transferring funds between accounts. It checks if the source account has sufficient funds, updates the balances of both accounts, records the transfer in the Transferencia collection, and logs the movements in the Movimiento collection. The method returns a Transferencia object representing the completed transfer.
}