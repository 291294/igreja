package com.seuprojeto.igreja.service;

import com.seuprojeto.igreja.model.Membro;
import com.seuprojeto.igreja.model.Igreja;
import com.seuprojeto.igreja.repository.MembroRepository;
import com.seuprojeto.igreja.repository.IgrejaRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MembroService {

    private final MembroRepository repository;
    private final IgrejaRepository igrejaRepository;

    public MembroService(MembroRepository repository, IgrejaRepository igrejaRepository) {
        this.repository = repository;
        this.igrejaRepository = igrejaRepository;
    }

    public Membro salvar(Membro membro) {
        if (membro.getDataCadastro() == null) {
            membro.setDataCadastro(LocalDate.now());
        }
        if (membro.getAtivo() == null) {
            membro.setAtivo(true);
        }
        return repository.save(membro);
    }

    public Optional<Membro> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public List<Membro> listarPorIgreja(Long igrejaId) {
        return repository.findByIgrejaId(igrejaId);
    }

    public List<Membro> buscarPorNome(String nome, Long igrejaId) {
        return repository.findByNomeContainingIgnoreCaseAndIgrejaId(nome, igrejaId);
    }

    public Membro atualizar(Long id, Membro membroAtualizado) {
        return repository.findById(id)
                .map(membro -> {
                    membro.setNome(membroAtualizado.getNome());
                    membro.setTelefone(membroAtualizado.getTelefone());
                    membro.setEmail(membroAtualizado.getEmail());
                    membro.setDataNascimento(membroAtualizado.getDataNascimento());
                    membro.setDataBatismo(membroAtualizado.getDataBatismo());
                    membro.setAtivo(membroAtualizado.getAtivo());
                    return repository.save(membro);
                })
                .orElseThrow(() -> new RuntimeException("Membro não encontrado com ID: " + id));
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }

    public void ativar(Long id) {
        repository.findById(id).ifPresent(membro -> {
            membro.setAtivo(true);
            repository.save(membro);
        });
    }

    public void desativar(Long id) {
        repository.findById(id).ifPresent(membro -> {
            membro.setAtivo(false);
            repository.save(membro);
        });
    }
}

