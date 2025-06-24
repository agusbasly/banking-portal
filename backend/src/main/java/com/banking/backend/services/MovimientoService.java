package com.banking.backend.services;

import com.banking.backend.model.Movimiento;
import com.banking.backend.model.dtos.MovimientoDTO;
import com.banking.backend.repository.MovimientoRepository;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MovimientoService {

    @Autowired
    private MovimientoRepository repo;

    public void registrarMovimiento(String cuentaId, String tipo, Double monto, String descripcion) {
        log.info("Registrando movimiento: cuentaId={}, tipo={}, monto={}, descripcion={}", 
                 cuentaId, tipo, monto, descripcion);

        Movimiento mov = Movimiento.builder()
                .cuentaId(cuentaId)
                .tipo(tipo)
                .monto(monto)
                .descripcion(descripcion)
                .fecha(LocalDateTime.now())
                .build();

        try {
            repo.save(mov);
            log.info("Movimiento guardado correctamente para cuenta {}", cuentaId);
        } catch (Exception e) {
            log.error("Error al guardar movimiento para cuenta {}: {}", cuentaId, e.getMessage(), e);
            throw e;
        }
    }

    public List<MovimientoDTO> obtenerMovimientosDeCuenta(String cuentaId) {
        log.info("Obteniendo movimientos para la cuenta {}", cuentaId);

        List<MovimientoDTO> movimientos = repo.findByCuentaIdOrderByFechaDesc(cuentaId)
                                               .stream()
                                               .map(this::toDTO)
                                               .collect(Collectors.toList());

        log.info("Se obtuvieron {} movimientos para la cuenta {}", movimientos.size(), cuentaId);
        return movimientos;
    }

    private MovimientoDTO toDTO(Movimiento mov) {
        return MovimientoDTO.builder()
                .cuentaId(mov.getCuentaId())
                .tipo(mov.getTipo())
                .monto(mov.getMonto())
                .descripcion(mov.getDescripcion())
                .fecha(mov.getFecha())
                .build();
    }
}

