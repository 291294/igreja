package com.seuprojeto.igreja.repository;

import com.seuprojeto.igreja.model.Igreja;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IgrejaRepository extends JpaRepository<Igreja, Long> {
    Optional<Igreja> findByEmail(String email);
}
