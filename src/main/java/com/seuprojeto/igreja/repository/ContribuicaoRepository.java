package com.seuprojeto.igreja.repository;

import com.seuprojeto.igreja.model.Contribuicao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.math.BigDecimal;

@Repository
public interface ContribuicaoRepository extends JpaRepository<Contribuicao, Long> {
    List<Contribuicao> findByIgrejaId(Long igrejaId);
    List<Contribuicao> findByMembroIdAndIgrejaId(Long membroId, Long igrejaId);
    List<Contribuicao> findByDataBetweenAndIgrejaId(LocalDate dataInicio, LocalDate dataFim, Long igrejaId);

    @Query("SELECT SUM(c.valor) FROM Contribuicao c WHERE c.igreja.id = :igrejaId AND c.data BETWEEN :dataInicio AND :dataFim")
    BigDecimal somarPorPeriodo(@Param("igrejaId") Long igrejaId, @Param("dataInicio") LocalDate dataInicio, @Param("dataFim") LocalDate dataFim);
}
