package com.seuprojeto.igreja.service;

import com.seuprojeto.igreja.model.Igreja;
import com.seuprojeto.igreja.repository.IgrejaRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class IgrejaService {

    private final IgrejaRepository repository;

    public IgrejaService(IgrejaRepository repository) {
        this.repository = repository;
    }

    public Igreja salvar(Igreja igreja) {
        if (igreja.getDataCadastro() == null) {
            igreja.setDataCadastro(LocalDate.now());
        }
        return repository.save(igreja);
    }

    public Optional<Igreja> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Optional<Igreja> buscarPorEmail(String email) {
        return repository.findByEmail(email);
    }

    public List<Igreja> listarTodas() {
        return repository.findAll();
    }

    public Igreja atualizar(Long id, Igreja igrejaAtualizada) {
        return repository.findById(id)
                .map(igreja -> {
                    igreja.setNome(igrejaAtualizada.getNome());
                    igreja.setEmail(igrejaAtualizada.getEmail());
                    if (igrejaAtualizada.getSenha() != null && !igrejaAtualizada.getSenha().isEmpty()) {
                        igreja.setSenha(igrejaAtualizada.getSenha());
                    }
                    return repository.save(igreja);
                })
                .orElseThrow(() -> new RuntimeException("Igreja não encontrada com ID: " + id));
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}

