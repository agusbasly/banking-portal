package com.banking.backend.controller;

import com.banking.backend.model.Transferencia;
import com.banking.backend.model.dtos.TransferenciaDTO;
import com.banking.backend.services.TransferenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/transferencias")
@CrossOrigin(origins = "*")
public class TransferenciaController {

    @Autowired
    private TransferenciaService service;

    @PostMapping
    public Transferencia transferir(@Valid @RequestBody TransferenciaDTO dto) {
        return service.realizarTransferencia(dto);
    }
}
// This controller handles transfer requests. It uses the TransferenciaService to process the transfer
// and returns the Transferencia object representing the completed transfer. The endpoint is POST /transferencias