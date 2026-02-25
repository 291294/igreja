package com.seuprojeto.igreja.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.seuprojeto.igreja.model.Plano;

public interface PlanoRepository extends JpaRepository<Plano, Long> {
    Optional<Plano> findByNome(String nome);
    Optional<Plano> findByAtivoTrue();
}
