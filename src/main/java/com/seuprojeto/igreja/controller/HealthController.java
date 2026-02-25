package com.seuprojeto.igreja.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api")
@Tag(name = "Health", description = "Endpoints de status da API")
public class HealthController {

    @GetMapping
    @Operation(summary = "Status da API", description = "Retorna mensagem indicando que a API está funcionando")
    public String welcome() {
        return "✅ Sistema Igreja v1.0.0 - API funcionando! Acesse /api/igrejas, /api/membros ou /api/contribuicoes";
    }

    @GetMapping("/health")
    @Operation(summary = "Health Check", description = "Verifica se a API está operacional")
    public String health() {
        return "{\"status\": \"UP\", \"message\": \"API operacional\"}";
    }
}

