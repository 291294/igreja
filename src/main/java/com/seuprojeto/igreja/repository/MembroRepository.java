package com.seuprojeto.igreja.repository;

import com.seuprojeto.igreja.model.Membro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MembroRepository extends JpaRepository<Membro, Long> {
    List<Membro> findByIgrejaId(Long igrejaId);
    Optional<Membro> findByIdAndIgrejaId(Long id, Long igrejaId);
    List<Membro> findByNomeContainingIgnoreCaseAndIgrejaId(String nome, Long igrejaId);
}
