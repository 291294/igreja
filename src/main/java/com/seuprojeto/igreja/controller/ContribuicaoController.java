package com.seuprojeto.igreja.controller;

import com.seuprojeto.igreja.model.Contribuicao;
import com.seuprojeto.igreja.service.ContribuicaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.math.BigDecimal;

@RestController
@RequestMapping("/contribuicoes")
public class ContribuicaoController {

    private final ContribuicaoService service;

    public ContribuicaoController(ContribuicaoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Contribuicao> criar(@RequestBody Contribuicao contribuicao) {
        Contribuicao contribuicaoSalva = service.salvar(contribuicao);
        return ResponseEntity.ok(contribuicaoSalva);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contribuicao> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/igreja/{igrejaId}")
    public ResponseEntity<List<Contribuicao>> listarPorIgreja(@PathVariable Long igrejaId) {
        return ResponseEntity.ok(service.listarPorIgreja(igrejaId));
    }

    @GetMapping("/membro/{membroId}/igreja/{igrejaId}")
    public ResponseEntity<List<Contribuicao>> listarPorMembro(
            @PathVariable Long membroId,
            @PathVariable Long igrejaId) {
        return ResponseEntity.ok(service.listarPorMembro(membroId, igrejaId));
    }

    @GetMapping("/periodo/{igrejaId}")
    public ResponseEntity<List<Contribuicao>> listarPorPeriodo(
            @PathVariable Long igrejaId,
            @RequestParam LocalDate dataInicio,
            @RequestParam LocalDate dataFim) {
        return ResponseEntity.ok(service.listarPorPeriodo(igrejaId, dataInicio, dataFim));
    }

    @GetMapping("/total/{igrejaId}")
    public ResponseEntity<BigDecimal> totalPorPeriodo(
            @PathVariable Long igrejaId,
            @RequestParam LocalDate dataInicio,
            @RequestParam LocalDate dataFim) {
        BigDecimal total = service.somarPorPeriodo(igrejaId, dataInicio, dataFim);
        return ResponseEntity.ok(total);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contribuicao> atualizar(@PathVariable Long id, @RequestBody Contribuicao contribuicao) {
        try {
            Contribuicao contribuicaoAtualizada = service.atualizar(id, contribuicao);
            return ResponseEntity.ok(contribuicaoAtualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

