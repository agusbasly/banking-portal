package com.banking.backend.services;

import com.banking.backend.model.Account;
import com.banking.backend.model.dtos.CreateAccountDTO;
import com.banking.backend.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    private AutoCloseable closeable;

    @BeforeEach
    void setup() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() throws Exception {
        if (closeable != null) {
            closeable.close();
        }
    }

    @Test
    void testCrearDesdeDTO() {
        CreateAccountDTO dto = new CreateAccountDTO();
        dto.setNumeroCuenta("123");
        dto.setNombreDueno("Juan");
        dto.setEmail("juan@test.com");
        dto.setBalance(null);

        Account savedAccount = Account.builder()
                .id("abc")
                .numeroCuenta("123")
                .nombreDueno("Juan")
                .email("juan@test.com")
                .balance(0.0)
                .build();

        when(accountRepository.save(any(Account.class))).thenReturn(savedAccount);

        Account result = accountService.crearDesdeDTO(dto);

        assertNotNull(result);
        assertEquals("abc", result.getId());
        assertEquals(0.0, result.getBalance());
    }

    @Test
    void testObtenerAccounts() {
        List<Account> accounts = List.of(
                new Account("1", "123", "Pepe", "pepe@mail.com", 100.0),
                new Account("2", "456", "Ana", "ana@mail.com", 200.0)
        );

        when(accountRepository.findAll()).thenReturn(accounts);

        List<Account> result = accountService.obtenerAccounts();

        assertEquals(2, result.size());
        verify(accountRepository, times(1)).findAll();
    }

    @Test
    void testObtenerAccountPorId() {
        Account acc = new Account("1", "123", "Luis", "luis@mail.com", 500.0);
        when(accountRepository.findById("1")).thenReturn(Optional.of(acc));

        Optional<Account> result = accountService.obtenerAccountPorId("1");

        assertTrue(result.isPresent());
        assertEquals("Luis", result.get().getNombreDueno());
    }

    @Test
    void testActualizarSaldo() {
        Account acc = new Account("1", "123", "Lola", "lola@mail.com", 100.0);
        when(accountRepository.findById("1")).thenReturn(Optional.of(acc));
        when(accountRepository.save(any(Account.class))).thenAnswer(i -> i.getArgument(0));

        Account updated = accountService.actualizarSaldo("1", 250.0);

        assertEquals(250.0, updated.getBalance());
    }

    @Test
    void testActualizarSaldoThrowsIfNotFound() {
        when(accountRepository.findById("404")).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            accountService.actualizarSaldo("404", 200.0);
        });

        assertEquals("Cuenta no encontrada", ex.getMessage());
    }

    @Test
    void testEliminarAccount() {
        doNothing().when(accountRepository).deleteById("1");
        accountService.eliminarAccount("1");
        verify(accountRepository, times(1)).deleteById("1");
    }
}
