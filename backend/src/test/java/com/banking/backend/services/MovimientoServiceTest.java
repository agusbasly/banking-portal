package com.banking.backend.services;

import com.banking.backend.model.Movimiento;
import com.banking.backend.model.dtos.MovimientoDTO;
import com.banking.backend.repository.MovimientoRepository;

import org.junit.jupiter.api.*;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MovimientoServiceTest {

    @Mock
    private MovimientoRepository repo;

    @InjectMocks
    private MovimientoService service;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void testRegistrarMovimiento() {
        // No devuelve nada, solo se verifica que guarde correctamente
        service.registrarMovimiento("abc", "DEPOSITO", 100.0, "Carga inicial");

        verify(repo, times(1)).save(any(Movimiento.class));
    }

    @Test
    void testObtenerMovimientosDeCuenta() {
        Movimiento m1 = Movimiento.builder()
                .cuentaId("abc")
                .tipo("RETIRO")
                .monto(50.0)
                .descripcion("Extracción")
                .fecha(LocalDateTime.now())
                .build();

        Movimiento m2 = Movimiento.builder()
                .cuentaId("abc")
                .tipo("DEPOSITO")
                .monto(200.0)
                .descripcion("Ingreso")
                .fecha(LocalDateTime.now().minusDays(1))
                .build();

        when(repo.findByCuentaIdOrderByFechaDesc("abc")).thenReturn(List.of(m1, m2));

        List<MovimientoDTO> result = service.obtenerMovimientosDeCuenta("abc");

        assertEquals(2, result.size());
        assertEquals("RETIRO", result.get(0).getTipo());
        assertEquals("DEPOSITO", result.get(1).getTipo());
    }
}
