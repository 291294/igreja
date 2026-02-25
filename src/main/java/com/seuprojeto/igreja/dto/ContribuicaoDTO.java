package com.seuprojeto.igreja.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public class ContribuicaoDTO {
    
    private Long id;
    
    @NotNull(message = "Tipo de contribuição é obrigatório (DIZIMO ou OFERTA)")
    private String tipo;
    
    @NotNull(message = "Valor é obrigatório")
    @DecimalMin(value = "0.01", message = "Valor deve ser maior que 0")
    private BigDecimal valor;
    
    @NotNull(message = "Data é obrigatória")
    private LocalDate data;
    
    private String observacao;
    
    private Long membroId;
    
    @NotNull(message = "Igreja ID é obrigatório")
    private Long igrejaId;

    // Construtores
    public ContribuicaoDTO() {
    }

    public ContribuicaoDTO(String tipo, BigDecimal valor, Long igrejaId) {
        this.tipo = tipo;
        this.valor = valor;
        this.igrejaId = igrejaId;
        this.data = LocalDate.now();
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Long getMembroId() {
        return membroId;
    }

    public void setMembroId(Long membroId) {
        this.membroId = membroId;
    }

    public Long getIgrejaId() {
        return igrejaId;
    }

    public void setIgrejaId(Long igrejaId) {
        this.igrejaId = igrejaId;
    }
}
