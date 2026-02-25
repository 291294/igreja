package com.seuprojeto.igreja.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.seuprojeto.igreja.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmailAndIgrejaId(String email, Long igrejaId);
    List<Usuario> findByIgrejaIdAndAtivo(Long igrejaId, Boolean ativo);
    Optional<Usuario> findByIdAndIgrejaId(Long id, Long igrejaId);
}
