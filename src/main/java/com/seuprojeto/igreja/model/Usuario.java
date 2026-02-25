package com.seuprojeto.igreja.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

/**
 * Entidade Usuario - Usuários que acessam o sistema (Admin, Tesoureiro, etc)
 */
@Entity
@Table(name = "usuario", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"email", "igreja_id"})
})
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false, length = 255)
    private String senhaHash;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "igreja_id", nullable = false)
    private Igreja igreja;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private Role role; // ADMIN, TESOUREIRO, MEMBRO, OBSERVADOR

    @Column(nullable = false)
    private Boolean ativo = true;

    @Column(nullable = false)
    private LocalDateTime dataCriacao;

    @Column(nullable = true)
    private LocalDateTime dataUltimoAcesso;

    @PrePersist
    protected void onCreate() {
        dataCriacao = LocalDateTime.now();
    }

    // Construtores
    public Usuario() {}

    public Usuario(String nome, String email, String senhaHash, Igreja igreja, Role role) {
        this.nome = nome;
        this.email = email;
        this.senhaHash = senhaHash;
        this.igreja = igreja;
        this.role = role;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenhaHash() {
        return senhaHash;
    }

    public void setSenhaHash(String senhaHash) {
        this.senhaHash = senhaHash;
    }

    public Igreja getIgreja() {
        return igreja;
    }

    public void setIgreja(Igreja igreja) {
        this.igreja = igreja;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataUltimoAcesso() {
        return dataUltimoAcesso;
    }

    public void setDataUltimoAcesso(LocalDateTime dataUltimoAcesso) {
        this.dataUltimoAcesso = dataUltimoAcesso;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", ativo=" + ativo +
                '}';
    }

    // Enum para Roles
    public enum Role {
        ADMIN,        // Acesso completo
        TESOUREIRO,   // Gerencia contribuições
        MEMBRO,       // Visualiza dados da Igreja
        OBSERVADOR    // Apenas leitura
    }
}
