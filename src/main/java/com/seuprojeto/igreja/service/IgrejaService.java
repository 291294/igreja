package com.seuprojeto.igreja.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.seuprojeto.igreja.dto.IgrejaDTO;
import com.seuprojeto.igreja.model.Igreja;
import com.seuprojeto.igreja.repository.IgrejaRepository;

@Service
public class IgrejaService {

    private final IgrejaRepository repository;
    private final PasswordEncoder passwordEncoder;

    public IgrejaService(IgrejaRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public IgrejaDTO salvar(IgrejaDTO dto) {
        Igreja igreja = new Igreja();
        igreja.setNome(dto.getNome());
        igreja.setEmail(dto.getEmail());
        // Criptografar senha com BCrypt
        igreja.setSenhaHash(passwordEncoder.encode(dto.getSenha()));
        
        Igreja igrejaSalva = repository.save(igreja);
        return converterParaDTO(igrejaSalva);
    }

    public Optional<IgrejaDTO> buscarPorId(Long id) {
        return repository.findById(id).map(this::converterParaDTO);
    }

    public Optional<IgrejaDTO> buscarPorEmail(String email) {
        return repository.findByEmail(email).map(this::converterParaDTO);
    }

    public List<IgrejaDTO> listarTodas() {
        return repository.findAll()
                .stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    public IgrejaDTO atualizar(Long id, IgrejaDTO dtoAtualizado) {
        return repository.findById(id)
                .map(igreja -> {
                    igreja.setNome(dtoAtualizado.getNome());
                    igreja.setEmail(dtoAtualizado.getEmail());
                    
                    // Atualizar senha apenas se fornecida
                    if (dtoAtualizado.getSenha() != null && !dtoAtualizado.getSenha().isEmpty()) {
                        igreja.setSenhaHash(passwordEncoder.encode(dtoAtualizado.getSenha()));
                    }
                    
                    Igreja igrejaAtualizada = repository.save(igreja);
                    return converterParaDTO(igrejaAtualizada);
                })
                .orElseThrow(() -> new RuntimeException("Igreja não encontrada com ID: " + id));
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }

    // Método auxiliar para conversão
    private IgrejaDTO converterParaDTO(Igreja igreja) {
        IgrejaDTO dto = new IgrejaDTO();
        dto.setId(igreja.getId());
        dto.setNome(igreja.getNome());
        dto.setEmail(igreja.getEmail());
        // Nunca retornar hash de senha ao cliente!
        return dto;
    }
}

