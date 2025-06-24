package com.banking.backend.controller;

import com.banking.backend.model.dtos.MovimientoDTO;
import com.banking.backend.services.MovimientoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movimientos")
@CrossOrigin(origins = "*")
public class MovimientoController {

    @Autowired
    private MovimientoService service;

    @GetMapping("/{cuentaId}")
    @Operation(summary = "Obtiene los movimientos de una cuenta")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Movimientos obtenidos"),
        @ApiResponse(responseCode = "404", description = "Cuenta no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error del servidor")
})
    public List<MovimientoDTO> obtenerPorCuenta(@PathVariable String cuentaId) {
        return service.obtenerMovimientosDeCuenta(cuentaId);
    }
}
