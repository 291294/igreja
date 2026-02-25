package com.seuprojeto.igreja.model;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

public class IgrejaTest {

    @Test
    public void testCriarIgreja() {
        // Arrange
        Igreja igreja = new Igreja();
        igreja.setNome("Igreja Evangélica Central");
        igreja.setEmail("contato@igrejcentral.com");
        igreja.setSenhaHash("$2a$10$hashedPassword");

        // Act
        igreja.prePersist();

        // Assert
        assertEquals("Igreja Evangélica Central", igreja.getNome());
        assertEquals("contato@igrejcentral.com", igreja.getEmail());
        assertNotNull(igreja.getDataCadastro());
        assertEquals(LocalDate.now(), igreja.getDataCadastro());
    }

    @Test
    public void testConstrutorIgreja() {
        // Arrange
        Igreja igreja = new Igreja("Minha Igreja", "email@igreja.com", "$2a$10$senhaHasheada");

        // Assert
        assertEquals("Minha Igreja", igreja.getNome());
        assertEquals("email@igreja.com", igreja.getEmail());
        assertEquals("$2a$10$senhaHasheada", igreja.getSenhaHash());
    }
}

