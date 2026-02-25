package com.seuprojeto.igreja.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * Entidade Assinatura - Rastreia e controla a assinatura de cada Igreja
 */
@Entity
@Table(name = "assinatura")
public class Assinatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "igreja_id", nullable = false, unique = true)
    private Igreja igreja;

    @ManyToOne(optional = false)
    @JoinColumn(name = "plano_id", nullable = false)
    private Plano plano;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private StatusAssinatura status; // ATIVA, CANCELADA, SUSPENSA

    @Column(nullable = false)
    private LocalDateTime dataInicio;

    @Column(nullable = true)
    private LocalDateTime dataFim;

    @Column(nullable = true)
    private LocalDateTime dataCancelamento;

    @Column(nullable = false)
    private LocalDateTime dataCriacao;

    @Column(nullable = false, length = 50)
    private String metodoPagamento; // CARTAO, BOLETO, PIX, etc

    @Column(nullable = true, length = 255)
    private String referenciaExterna; // ID de pagamento do gateway (Stripe, etc)

    // Construtores
    public Assinatura() {}

    public Assinatura(Igreja igreja, Plano plano) {
        this.igreja = igreja;
        this.plano = plano;
        this.status = StatusAssinatura.ATIVA;
        this.dataInicio = LocalDateTime.now();
        this.dataCriacao = LocalDateTime.now();
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Igreja getIgreja() {
        return igreja;
    }

    public void setIgreja(Igreja igreja) {
        this.igreja = igreja;
    }

    public Plano getPlano() {
        return plano;
    }

    public void setPlano(Plano plano) {
        this.plano = plano;
    }

    public StatusAssinatura getStatus() {
        return status;
    }

    public void setStatus(StatusAssinatura status) {
        this.status = status;
    }

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDateTime getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDateTime dataFim) {
        this.dataFim = dataFim;
    }

    public LocalDateTime getDataCancelamento() {
        return dataCancelamento;
    }

    public void setDataCancelamento(LocalDateTime dataCancelamento) {
        this.dataCancelamento = dataCancelamento;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(String metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    public String getReferenciaExterna() {
        return referenciaExterna;
    }

    public void setReferenciaExterna(String referenciaExterna) {
        this.referenciaExterna = referenciaExterna;
    }

    @Override
    public String toString() {
        return "Assinatura{" +
                "id=" + id +
                ", status=" + status +
                ", dataInicio=" + dataInicio +
                ", dataFim=" + dataFim +
                '}';
    }

    // Enum para Status
    public enum StatusAssinatura {
        ATIVA, CANCELADA, SUSPENSA, EXPIRADA
    }
}
