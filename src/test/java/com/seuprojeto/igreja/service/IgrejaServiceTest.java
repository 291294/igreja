package com.seuprojeto.igreja.service;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.seuprojeto.igreja.dto.IgrejaDTO;
import com.seuprojeto.igreja.model.Igreja;
import com.seuprojeto.igreja.repository.IgrejaRepository;

public class IgrejaServiceTest {

    @Mock
    private IgrejaRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private IgrejaService service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSalvarIgreja() {
        // Arrange
        IgrejaDTO dto = new IgrejaDTO();
        dto.setNome("Igreja Teste");
        dto.setEmail("teste@igreja.com");
        dto.setSenha("Senha123");

        Igreja igreja = new Igreja("Igreja Teste", "teste@igreja.com", "$2a$10$hashedPassword");
        igreja.setId(1L);

        when(passwordEncoder.encode("Senha123")).thenReturn("$2a$10$hashedPassword");
        when(repository.save(any(Igreja.class))).thenReturn(igreja);

        // Act
        IgrejaDTO resultado = service.salvar(dto);

        // Assert
        assertNotNull(resultado);
        assertEquals("Igreja Teste", resultado.getNome());
        assertEquals("teste@igreja.com", resultado.getEmail());
        assertEquals(1L, resultado.getId());
        verify(passwordEncoder, times(1)).encode("Senha123");
        verify(repository, times(1)).save(any(Igreja.class));
    }

    @Test
    public void testBuscarPorId() {
        // Arrange
        Igreja igreja = new Igreja("Igreja Teste", "teste@igreja.com", "$2a$10$hash");
        igreja.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(igreja));

        // Act
        Optional<IgrejaDTO> resultado = service.buscarPorId(1L);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals("Igreja Teste", resultado.get().getNome());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    public void testBuscarPorIdNaoEncontrado() {
        // Arrange
        when(repository.findById(999L)).thenReturn(Optional.empty());

        // Act
        Optional<IgrejaDTO> resultado = service.buscarPorId(999L);

        // Assert
        assertTrue(resultado.isEmpty());
        verify(repository, times(1)).findById(999L);
    }

    @Test
    public void testAtualizarIgreja() {
        // Arrange
        Igreja igrejaExistente = new Igreja("Igreja Antiga", "antiga@igreja.com", "$2a$10$hash");
        igrejaExistente.setId(1L);

        IgrejaDTO dtoAtualizado = new IgrejaDTO();
        dtoAtualizado.setNome("Igreja Nova");
        dtoAtualizado.setEmail("nova@igreja.com");
        dtoAtualizado.setSenha("NovaSenha123");

        when(repository.findById(1L)).thenReturn(Optional.of(igrejaExistente));
        when(passwordEncoder.encode("NovaSenha123")).thenReturn("$2a$10$novaHash");
        when(repository.save(any(Igreja.class))).thenReturn(igrejaExistente);

        // Act
        IgrejaDTO resultado = service.atualizar(1L, dtoAtualizado);

        // Assert
        assertNotNull(resultado);
        assertEquals("Igreja Nova", resultado.getNome());
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(any(Igreja.class));
    }

    @Test
    public void testDeletarIgreja() {
        // Act
        service.deletar(1L);

        // Assert
        verify(repository, times(1)).deleteById(1L);
    }
}
