package com.seuprojeto.igreja.controller;

import com.seuprojeto.igreja.model.Membro;
import com.seuprojeto.igreja.service.MembroService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/membros")
public class MembroController {

    private final MembroService service;

    public MembroController(MembroService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Membro> criar(@RequestBody Membro membro) {
        Membro membroSalvo = service.salvar(membro);
        return ResponseEntity.ok(membroSalvo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Membro> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/igreja/{igrejaId}")
    public ResponseEntity<List<Membro>> listarPorIgreja(@PathVariable Long igrejaId) {
        return ResponseEntity.ok(service.listarPorIgreja(igrejaId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Membro>> buscarPorNome(
            @RequestParam String nome,
            @RequestParam Long igrejaId) {
        return ResponseEntity.ok(service.buscarPorNome(nome, igrejaId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Membro> atualizar(@PathVariable Long id, @RequestBody Membro membro) {
        try {
            Membro membroAtualizado = service.atualizar(id, membro);
            return ResponseEntity.ok(membroAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/ativar")
    public ResponseEntity<Void> ativar(@PathVariable Long id) {
        service.ativar(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/desativar")
    public ResponseEntity<Void> desativar(@PathVariable Long id) {
        service.desativar(id);
        return ResponseEntity.ok().build();
    }
}

