package com.seuprojeto.igreja.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.seuprojeto.igreja.model.Assinatura;

public interface AssinaturaRepository extends JpaRepository<Assinatura, Long> {
    Optional<Assinatura> findByIgrejaId(Long igrejaId);
}
