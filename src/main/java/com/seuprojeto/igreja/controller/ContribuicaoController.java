package com.seuprojeto.igreja.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
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

import com.seuprojeto.igreja.model.Contribuicao;
import com.seuprojeto.igreja.service.ContribuicaoService;
import com.seuprojeto.igreja.security.TenantContextHolder;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/contribuicoes")
@Tag(name = "Contribuições", description = "Gerenciamento de contribuições e ofertas da Igreja")
public class ContribuicaoController {

    private final ContribuicaoService service;

    public ContribuicaoController(ContribuicaoService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Registrar contribuição", description = "Registra uma nova contribuição/oferta na sua Igreja")
    @ApiResponse(responseCode = "200", description = "Contribuição registrada com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    public ResponseEntity<Contribuicao> criar(@RequestBody Contribuicao contribuicao) {
        Long igrejaId = TenantContextHolder.getIgrejaId();
        contribuicao.getIgreja().setId(igrejaId);
        Contribuicao contribuicaoSalva = service.salvar(contribuicao);
        return ResponseEntity.ok(contribuicaoSalva);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar contribuição por ID", description = "Retorna os detalhes de uma contribuição específica")
    @ApiResponse(responseCode = "200", description = "Contribuição encontrada")
    @ApiResponse(responseCode = "404", description = "Contribuição não encontrada")
    public ResponseEntity<Contribuicao> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "Listar contribuições", description = "Retorna todas as contribuições da sua Igreja")
    @ApiResponse(responseCode = "200", description = "Lista de contribuições")
    public ResponseEntity<List<Contribuicao>> listar() {
        Long igrejaId = TenantContextHolder.getIgrejaId();
        return ResponseEntity.ok(service.listarPorIgreja(igrejaId));
    }

    @GetMapping("/membro/{membroId}")
    @Operation(summary = "Listar contribuições por membro", description = "Retorna todas as contribuições de um membro da sua Igreja")
    @ApiResponse(responseCode = "200", description = "Lista de contribuições do membro")
    public ResponseEntity<List<Contribuicao>> listarPorMembro(@PathVariable Long membroId) {
        Long igrejaId = TenantContextHolder.getIgrejaId();
        return ResponseEntity.ok(service.listarPorMembro(membroId, igrejaId));
    }

    @GetMapping("/periodo")
    @Operation(summary = "Listar contribuições por período", description = "Retorna contribuições da sua Igreja em um período específico")
    @ApiResponse(responseCode = "200", description = "Lista de contribuições no período")
    public ResponseEntity<List<Contribuicao>> listarPorPeriodo(
            @RequestParam LocalDate dataInicio,
            @RequestParam LocalDate dataFim) {
        Long igrejaId = TenantContextHolder.getIgrejaId();
        return ResponseEntity.ok(service.listarPorPeriodo(igrejaId, dataInicio, dataFim));
    }

    @GetMapping("/total")
    @Operation(summary = "Total de contribuições por período", description = "Calcula o total de contribuições da sua Igreja em um período")
    @ApiResponse(responseCode = "200", description = "Total de contribuições")
    public ResponseEntity<BigDecimal> totalPorPeriodo(
            @RequestParam LocalDate dataInicio,
            @RequestParam LocalDate dataFim) {
        Long igrejaId = TenantContextHolder.getIgrejaId();
        BigDecimal total = service.somarPorPeriodo(igrejaId, dataInicio, dataFim);
        return ResponseEntity.ok(total);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar contribuição", description = "Atualiza os dados de uma contribuição existente")
    @ApiResponse(responseCode = "200", description = "Contribuição atualizada com sucesso")
    @ApiResponse(responseCode = "404", description = "Contribuição não encontrada")
    public ResponseEntity<Contribuicao> atualizar(@PathVariable Long id, @RequestBody Contribuicao contribuicao) {
        try {
            Contribuicao contribuicaoAtualizada = service.atualizar(id, contribuicao);
            return ResponseEntity.ok(contribuicaoAtualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar contribuição", description = "Remove uma contribuição do sistema")
    @ApiResponse(responseCode = "204", description = "Contribuição deletada com sucesso")
    @ApiResponse(responseCode = "404", description = "Contribuição não encontrada")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

