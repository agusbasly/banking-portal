package com.banking.backend.controller;

import com.banking.backend.model.Account;
import com.banking.backend.model.dtos.CreateAccountDTO;
import com.banking.backend.services.AccountService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/accounts")
@CrossOrigin(origins = "*")
public class AccountController {

    @Autowired
    private AccountService service;

    @Operation(summary = "Crea una nueva cuenta")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Cuenta creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
    @PostMapping
    public Account crear(@Valid @RequestBody CreateAccountDTO accDTO) {
        return service.crearDesdeDTO(accDTO);
    }

    @Operation(summary = "Obtiene todas las cuentas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente"),
        @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
    @GetMapping
    public List<Account> obtenerTodas() {
        return service.obtenerAccounts();
    }

    @Operation(summary = "Obtiene una cuenta por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cuenta encontrada"),
        @ApiResponse(responseCode = "404", description = "Cuenta no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
    @GetMapping("/{id}")
    public Account obtenerPorId(@PathVariable String id) {
        return service.obtenerAccountPorId(id)
                      .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));
    }

    @Operation(summary = "Actualiza el saldo de una cuenta")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Saldo actualizado"),
        @ApiResponse(responseCode = "404", description = "Cuenta no encontrada"),
        @ApiResponse(responseCode = "400", description = "Parámetros inválidos"),
        @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
    @PutMapping("/{id}")
    public Account actualizarSaldo(@PathVariable String id, @RequestParam Double saldo) {
        return service.actualizarSaldo(id, saldo);
    }

    @Operation(summary = "Elimina una cuenta")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Cuenta eliminada"),
        @ApiResponse(responseCode = "404", description = "Cuenta no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable String id) {
        service.eliminarAccount(id);
    }
}

// This code defines a REST controller for managing bank accounts. It includes endpoints for creating, retrieving, updating, and deleting accounts, with appropriate Swagger documentation for API responses. The controller uses the `AccountService` to handle business logic and data access. Validation is applied to incoming requests using annotations like `@Valid`. The controller also supports CORS for cross-origin requests.