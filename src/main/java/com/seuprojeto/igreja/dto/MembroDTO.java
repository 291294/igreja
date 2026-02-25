package com.seuprojeto.igreja.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class MembroDTO {
    
    private Long id;
    
    @NotBlank(message = "Nome do Membro é obrigatório")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    private String nome;
    
    @Size(max = 20, message = "Telefone deve ter no máximo 20 caracteres")
    private String telefone;
    
    @Email(message = "Email deve ser válido")
    private String email;
    
    private LocalDate dataNascimento;
    private LocalDate dataBatismo;
    
    @NotNull(message = "Status ativo/inativo é obrigatório")
    private Boolean ativo = true;
    
    @NotNull(message = "Igreja ID é obrigatório")
    private Long igrejaId;

    // Construtores
    public MembroDTO() {
    }

    public MembroDTO(String nome, Long igrejaId) {
        this.nome = nome;
        this.igrejaId = igrejaId;
        this.ativo = true;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public LocalDate getDataBatismo() {
        return dataBatismo;
    }

    public void setDataBatismo(LocalDate dataBatismo) {
        this.dataBatismo = dataBatismo;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Long getIgrejaId() {
        return igrejaId;
    }

    public void setIgrejaId(Long igrejaId) {
        this.igrejaId = igrejaId;
    }
}
