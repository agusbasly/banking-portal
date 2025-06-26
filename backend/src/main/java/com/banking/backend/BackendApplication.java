package com.banking.backend;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {
    // mongod.exe --repair
    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

	// Elimina el archivo app.log al iniciar la aplicación
	// Esto es útil para evitar que el archivo crezca indefinidamente durante el desarrollo
	// En producción, deberías configurar un manejo de logs más robusto
    @PostConstruct
    public void init() {
        try {
            Files.write(Paths.get("logs/app.log"), new byte[0]);
            System.out.println("🧹 app.log vaciado al iniciar");
        } catch (IOException e) {
        System.err.println("No se pudo vaciar app.log: " + e.getMessage());
        }
    }

}
