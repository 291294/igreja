package com.seuprojeto.igreja.dto;

/**
 * DTO para Resposta de Autenticação (Login/Registro)
 */
public class AuthResponseDTO {

    private String token;
    private Long usuarioId;
    private Long igrejaId;
    private String nomeIgreja;
    private String plano;
    private String role;
    private String email;

    // Construtores
    public AuthResponseDTO() {}

    public AuthResponseDTO(String token, Long usuarioId, Long igrejaId, String nomeIgreja, 
                          String plano, String role, String email) {
        this.token = token;
        this.usuarioId = usuarioId;
        this.igrejaId = igrejaId;
        this.nomeIgreja = nomeIgreja;
        this.plano = plano;
        this.role = role;
        this.email = email;
    }

    // Getters e Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getIgrejaId() {
        return igrejaId;
    }

    public void setIgrejaId(Long igrejaId) {
        this.igrejaId = igrejaId;
    }

    public String getNomeIgreja() {
        return nomeIgreja;
    }

    public void setNomeIgreja(String nomeIgreja) {
        this.nomeIgreja = nomeIgreja;
    }

    public String getPlano() {
        return plano;
    }

    public void setPlano(String plano) {
        this.plano = plano;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
