package com.seuprojeto.igreja.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

public class IgrejaTest {

    @Test
    public void testCriarIgreja() {
        // Arrange
        Igreja igreja = new Igreja();
        igreja.setNome("Igreja Evangélica Central");
        igreja.setEmail("contato@igrejcentral.com");
        igreja.setSenha("senha123");

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
        Igreja igreja = new Igreja("Minha Igreja", "email@igreja.com", "senha456");

        // Assert
        assertEquals("Minha Igreja", igreja.getNome());
        assertEquals("email@igreja.com", igreja.getEmail());
        assertEquals("senha456", igreja.getSenha());
    }
}
