package com.seuprojeto.igreja.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO para Registro de Nova Igreja (Plano FREE automático)
 */
public class RegistroDTO {

    @NotBlank(message = "Nome da Igreja é obrigatório")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    private String nomeIgreja;

    @NotBlank(message = "Email da Igreja é obrigatório")
    @Email(message = "Email deve ser válido")
    private String emailIgreja;

    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres")
    private String senhaIgreja;

    @NotBlank(message = "Nome do Admin é obrigatório")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    private String nomeAdmin;

    @NotBlank(message = "Email do Admin é obrigatório")
    @Email(message = "Email deve ser válido")
    private String emailAdmin;

    @NotBlank(message = "Senha do Admin é obrigatória")
    @Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres")
    private String senhaAdmin;

    // Getters e Setters
    public String getNomeIgreja() {
        return nomeIgreja;
    }

    public void setNomeIgreja(String nomeIgreja) {
        this.nomeIgreja = nomeIgreja;
    }

    public String getEmailIgreja() {
        return emailIgreja;
    }

    public void setEmailIgreja(String emailIgreja) {
        this.emailIgreja = emailIgreja;
    }

    public String getSenhaIgreja() {
        return senhaIgreja;
    }

    public void setSenhaIgreja(String senhaIgreja) {
        this.senhaIgreja = senhaIgreja;
    }

    public String getNomeAdmin() {
        return nomeAdmin;
    }

    public void setNomeAdmin(String nomeAdmin) {
        this.nomeAdmin = nomeAdmin;
    }

    public String getEmailAdmin() {
        return emailAdmin;
    }

    public void setEmailAdmin(String emailAdmin) {
        this.emailAdmin = emailAdmin;
    }

    public String getSenhaAdmin() {
        return senhaAdmin;
    }

    public void setSenhaAdmin(String senhaAdmin) {
        this.senhaAdmin = senhaAdmin;
    }
}
