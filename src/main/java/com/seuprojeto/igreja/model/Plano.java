package com.seuprojeto.igreja.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entidade Plano - Define os limites e recursos disponíveis para cada Igreja
 */
@Entity
@Table(name = "plano")
public class Plano {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String nome; // FREE, PREMIUM, ENTERPRISE

    @Column(nullable = false)
    private Integer limiteMembros;

    @Column(nullable = false)
    private Integer limiteUsuarios;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valorMensal;

    @Column(nullable = false)
    private Boolean ativo = true;

    @Column(length = 500)
    private String descricao;

    // Construtores
    public Plano() {}

    public Plano(String nome, Integer limiteMembros, Integer limiteUsuarios, BigDecimal valorMensal) {
        this.nome = nome;
        this.limiteMembros = limiteMembros;
        this.limiteUsuarios = limiteUsuarios;
        this.valorMensal = valorMensal;
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

    public Integer getLimiteMembros() {
        return limiteMembros;
    }

    public void setLimiteMembros(Integer limiteMembros) {
        this.limiteMembros = limiteMembros;
    }

    public Integer getLimiteUsuarios() {
        return limiteUsuarios;
    }

    public void setLimiteUsuarios(Integer limiteUsuarios) {
        this.limiteUsuarios = limiteUsuarios;
    }

    public BigDecimal getValorMensal() {
        return valorMensal;
    }

    public void setValorMensal(BigDecimal valorMensal) {
        this.valorMensal = valorMensal;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "Plano{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", limiteMembros=" + limiteMembros +
                ", limiteUsuarios=" + limiteUsuarios +
                ", valorMensal=" + valorMensal +
                ", ativo=" + ativo +
                '}';
    }
}
