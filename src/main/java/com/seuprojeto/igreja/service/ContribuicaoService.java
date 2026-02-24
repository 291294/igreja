package com.seuprojeto.igreja.service;

import com.seuprojeto.igreja.model.Contribuicao;
import com.seuprojeto.igreja.repository.ContribuicaoRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.math.BigDecimal;

@Service
public class ContribuicaoService {

    private final ContribuicaoRepository repository;

    public ContribuicaoService(ContribuicaoRepository repository) {
        this.repository = repository;
    }

    public Contribuicao salvar(Contribuicao contribuicao) {
        if (contribuicao.getDataCadastro() == null) {
            contribuicao.setDataCadastro(LocalDate.now());
        }
        if (contribuicao.getData() == null) {
            contribuicao.setData(LocalDate.now());
        }
        return repository.save(contribuicao);
    }

    public Optional<Contribuicao> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public List<Contribuicao> listarPorIgreja(Long igrejaId) {
        return repository.findByIgrejaId(igrejaId);
    }

    public List<Contribuicao> listarPorMembro(Long membroId, Long igrejaId) {
        return repository.findByMembroIdAndIgrejaId(membroId, igrejaId);
    }

    public List<Contribuicao> listarPorPeriodo(Long igrejaId, LocalDate dataInicio, LocalDate dataFim) {
        return repository.findByDataBetweenAndIgrejaId(dataInicio, dataFim, igrejaId);
    }

    public BigDecimal somarPorPeriodo(Long igrejaId, LocalDate dataInicio, LocalDate dataFim) {
        BigDecimal total = repository.somarPorPeriodo(igrejaId, dataInicio, dataFim);
        return total != null ? total : BigDecimal.ZERO;
    }

    public Contribuicao atualizar(Long id, Contribuicao contribuicaoAtualizada) {
        return repository.findById(id)
                .map(contribuicao -> {
                    contribuicao.setTipo(contribuicaoAtualizada.getTipo());
                    contribuicao.setValor(contribuicaoAtualizada.getValor());
                    contribuicao.setData(contribuicaoAtualizada.getData());
                    contribuicao.setObservacao(contribuicaoAtualizada.getObservacao());
                    return repository.save(contribuicao);
                })
                .orElseThrow(() -> new RuntimeException("Contribuição não encontrada com ID: " + id));
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}

