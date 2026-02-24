package com.seuprojeto.igreja.controller;

import com.seuprojeto.igreja.model.Igreja;
import com.seuprojeto.igreja.service.IgrejaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/igrejas")
public class IgrejaController {

    private final IgrejaService service;

    public IgrejaController(IgrejaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Igreja> criar(@RequestBody Igreja igreja) {
        Igreja igrejaSalva = service.salvar(igreja);
        return ResponseEntity.ok(igrejaSalva);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Igreja> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Igreja>> listarTodas() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Igreja> buscarPorEmail(@PathVariable String email) {
        return service.buscarPorEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Igreja> atualizar(@PathVariable Long id, @RequestBody Igreja igreja) {
        try {
            Igreja igrejaAtualizada = service.atualizar(id, igreja);
            return ResponseEntity.ok(igrejaAtualizada);
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

