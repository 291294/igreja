package com.seuprojeto.igreja.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seuprojeto.igreja.dto.IgrejaDTO;
import com.seuprojeto.igreja.service.IgrejaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/igrejas")
@Tag(name = "Igreja", description = "Endpoints para gerenciamento de igrejas")
public class IgrejaController {

    private final IgrejaService service;

    public IgrejaController(IgrejaService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Criar uma nova Igreja", description = "Criar um cadastro de Igreja com validação de dados")
    @ApiResponse(responseCode = "200", description = "Igreja criada com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    @ApiResponse(responseCode = "409", description = "Email já cadastrado")
    public ResponseEntity<IgrejaDTO> criar(@Valid @RequestBody IgrejaDTO dto) {
        IgrejaDTO igrejaSalva = service.salvar(dto);
        return ResponseEntity.ok(igrejaSalva);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar Igreja por ID", description = "Retorna os dados de uma Igreja específica")
    @ApiResponse(responseCode = "200", description = "Igreja encontrada")
    @ApiResponse(responseCode = "404", description = "Igreja não encontrada")
    public ResponseEntity<IgrejaDTO> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "Listar todas as Igrejas", description = "Retorna uma lista de todas as igrejas cadastradas")
    @ApiResponse(responseCode = "200", description = "Lista de igrejas")
    public ResponseEntity<List<IgrejaDTO>> listarTodas() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @GetMapping("/email/{email}")
    @Operation(summary = "Buscar Igreja por email", description = "Retorna os dados de uma Igreja pelo email")
    @ApiResponse(responseCode = "200", description = "Igreja encontrada")
    @ApiResponse(responseCode = "404", description = "Igreja não encontrada")
    public ResponseEntity<IgrejaDTO> buscarPorEmail(@PathVariable String email) {
        return service.buscarPorEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar Igreja", description = "Atualiza os dados de uma Igreja existente")
    @ApiResponse(responseCode = "200", description = "Igreja atualizada com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    @ApiResponse(responseCode = "404", description = "Igreja não encontrada")
    public ResponseEntity<IgrejaDTO> atualizar(@PathVariable Long id, @Valid @RequestBody IgrejaDTO dto) {
        try {
            IgrejaDTO igrejaAtualizada = service.atualizar(id, dto);
            return ResponseEntity.ok(igrejaAtualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar Igreja", description = "Remove uma Igreja do sistema")
    @ApiResponse(responseCode = "204", description = "Igreja deletada com sucesso")
    @ApiResponse(responseCode = "404", description = "Igreja não encontrada")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

