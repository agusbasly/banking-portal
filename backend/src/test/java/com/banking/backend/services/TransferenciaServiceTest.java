package com.banking.backend.services;

import com.banking.backend.model.Account;
import com.banking.backend.model.Transferencia;
import com.banking.backend.model.dtos.TransferenciaDTO;
import com.banking.backend.repository.AccountRepository;
import com.banking.backend.repository.TransferenciaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class TransferenciaServiceTest {

    @Mock
    private AccountRepository accountRepo;

    @Mock
    private TransferenciaRepository transferenciaRepo;

    @Mock
    private MovimientoService movimientoService;

    @InjectMocks
    private TransferenciaService transferenciaService;

    private AutoCloseable closeable;

    @BeforeEach
    void init() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() throws Exception {
        if (closeable != null) {
            closeable.close();
        }
    }

    @Test
    void testTransferenciaExitosa() {
        String origenId = "1";
        String destinoId = "2";

        Account origen = new Account(origenId, "123", "Juan", "juan@mail.com", 500.0);
        Account destino = new Account(destinoId, "456", "Ana", "ana@mail.com", 300.0);

        TransferenciaDTO dto = new TransferenciaDTO(origenId, destinoId, 100.0);

        when(accountRepo.findById(origenId)).thenReturn(Optional.of(origen));
        when(accountRepo.findById(destinoId)).thenReturn(Optional.of(destino));
        when(accountRepo.save(any(Account.class))).thenAnswer(i -> i.getArgument(0));
        when(transferenciaRepo.save(any(Transferencia.class))).thenAnswer(i -> i.getArgument(0));

        Transferencia result = transferenciaService.realizarTransferencia(dto);

        assertNotNull(result);
        assertEquals(origenId, result.getOrigenId());
        assertEquals(destinoId, result.getDestinoId());
        assertEquals(100.0, result.getMonto());

        assertEquals(400.0, origen.getBalance());
        assertEquals(400.0, destino.getBalance());

        verify(accountRepo, times(2)).save(any(Account.class));
        verify(transferenciaRepo, times(1)).save(any(Transferencia.class));
        verify(movimientoService, times(2)).registrarMovimiento(anyString(), anyString(), anyDouble(), anyString());
    }

    @Test
    void testSaldoInsuficiente() {
        String origenId = "1";
        String destinoId = "2";

        Account origen = new Account(origenId, "123", "Juan", "juan@mail.com", 50.0);
        Account destino = new Account(destinoId, "456", "Ana", "ana@mail.com", 300.0);

        TransferenciaDTO dto = new TransferenciaDTO(origenId, destinoId, 100.0);

        when(accountRepo.findById(origenId)).thenReturn(Optional.of(origen));
        when(accountRepo.findById(destinoId)).thenReturn(Optional.of(destino));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            transferenciaService.realizarTransferencia(dto);
        });

        assertEquals("Saldo insuficiente", ex.getMessage());
        verify(transferenciaRepo, never()).save(any());
        verify(accountRepo, never()).save(any());
        verify(movimientoService, never()).registrarMovimiento(any(), any(), any(), any());
    }

    @Test
    void testCuentaOrigenNoExiste() {
        TransferenciaDTO dto = new TransferenciaDTO("999", "2", 100.0);
        when(accountRepo.findById("999")).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            transferenciaService.realizarTransferencia(dto);
        });

        assertEquals("Cuenta origen no encontrada", ex.getMessage());
    }

    @Test
    void testCuentaDestinoNoExiste() {
        String origenId = "1";
        String destinoId = "999";

        Account origen = new Account(origenId, "123", "Juan", "juan@mail.com", 500.0);
        when(accountRepo.findById(origenId)).thenReturn(Optional.of(origen));
        when(accountRepo.findById(destinoId)).thenReturn(Optional.empty());

        TransferenciaDTO dto = new TransferenciaDTO(origenId, destinoId, 100.0);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            transferenciaService.realizarTransferencia(dto);
        });

        assertEquals("Cuenta destino no encontrada", ex.getMessage());
    }

    @Test
    void testTransferenciaMontoCeroOLnulo() {
        TransferenciaDTO dtoCero = new TransferenciaDTO("1", "2", 0.0);

        IllegalArgumentException ex1 = assertThrows(IllegalArgumentException.class, () ->
            transferenciaService.realizarTransferencia(dtoCero)
        );
        assertEquals("El monto debe ser positivo y distinto de cero", ex1.getMessage());

        // Test for null amount, but avoid NullPointerException in DTO constructor
        IllegalArgumentException ex2 = assertThrows(IllegalArgumentException.class, () ->
            transferenciaService.realizarTransferencia(new TransferenciaDTO("1", "2", (Double) null))
        );
        assertEquals("El monto debe ser positivo y distinto de cero", ex2.getMessage());
    }

    @Test
    void testTransferenciaConMontoNegativo() {
        TransferenciaDTO dto = new TransferenciaDTO("1", "2", -100.0);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            transferenciaService.realizarTransferencia(dto);
        });

        assertEquals("El monto debe ser positivo y distinto de cero", ex.getMessage());
    }
}


