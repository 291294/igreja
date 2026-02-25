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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.seuprojeto.igreja.model.Membro;
import com.seuprojeto.igreja.service.MembroService;
import com.seuprojeto.igreja.security.TenantContextHolder;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/membros")
@Tag(name = "Membros", description = "Gerenciamento de membros da Igreja")
public class MembroController {

    private final MembroService service;

    public MembroController(MembroService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Criar novo membro", description = "Registra um novo membro na sua Igreja")
    @ApiResponse(responseCode = "200", description = "Membro criado com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    public ResponseEntity<Membro> criar(@RequestBody Membro membro) {
        // Extrair igrejaId do JWT (tenant do usuário autenticado)
        Long igrejaId = TenantContextHolder.getIgrejaId();
        membro.getIgreja().setId(igrejaId);
        Membro membroSalvo = service.salvar(membro);
        return ResponseEntity.ok(membroSalvo);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar membro por ID", description = "Retorna os detalhes de um membro específico")
    @ApiResponse(responseCode = "200", description = "Membro encontrado")
    @ApiResponse(responseCode = "404", description = "Membro não encontrado")
    public ResponseEntity<Membro> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "Listar membros", description = "Retorna todos os membros da sua Igreja (multi-tenant seguro)")
    @ApiResponse(responseCode = "200", description = "Lista de membros")
    public ResponseEntity<List<Membro>> listar() {
        Long igrejaId = TenantContextHolder.getIgrejaId();
        return ResponseEntity.ok(service.listarPorIgreja(igrejaId));
    }

    @GetMapping("/search")
    @Operation(summary = "Buscar membros por nome", description = "Pesquisa membros pelo nome na sua Igreja")
    @ApiResponse(responseCode = "200", description = "Lista de membros encontrados")
    public ResponseEntity<List<Membro>> buscarPorNome(@RequestParam String nome) {
        Long igrejaId = TenantContextHolder.getIgrejaId();
        return ResponseEntity.ok(service.buscarPorNome(nome, igrejaId));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar membro", description = "Atualiza os dados de um membro existente")
    @ApiResponse(responseCode = "200", description = "Membro atualizado com sucesso")
    @ApiResponse(responseCode = "404", description = "Membro não encontrado")
    public ResponseEntity<Membro> atualizar(@PathVariable Long id, @RequestBody Membro membro) {
        try {
            Membro membroAtualizado = service.atualizar(id, membro);
            return ResponseEntity.ok(membroAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar membro", description = "Remove um membro do sistema")
    @ApiResponse(responseCode = "204", description = "Membro deletado com sucesso")
    @ApiResponse(responseCode = "404", description = "Membro não encontrado")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/ativar")
    @Operation(summary = "Ativar membro", description = "Ativa um membro inativo")
    @ApiResponse(responseCode = "200", description = "Membro ativado com sucesso")
    public ResponseEntity<Void> ativar(@PathVariable Long id) {
        service.ativar(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/desativar")
    @Operation(summary = "Desativar membro", description = "Desativa um membro ativo")
    @ApiResponse(responseCode = "200", description = "Membro desativado com sucesso")
    public ResponseEntity<Void> desativar(@PathVariable Long id) {
        service.desativar(id);
        return ResponseEntity.ok().build();
    }
}

