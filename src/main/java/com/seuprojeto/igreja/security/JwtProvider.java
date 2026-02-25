package com.seuprojeto.igreja.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

/**
 * JWT Provider - Responsável por gerar e validar tokens JWT
 * Token contém: userId, igrejaId, role, plano, exp
 */
@Component
public class JwtProvider {

    @Value("${jwt.secret:minha-chave-secreta-super-segura-min-32-caracteres}")
    private String jwtSecret;

    @Value("${jwt.expiration:86400000}")  // 24 horas em milissegundos
    private long jwtExpirationMs;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    /**
     * Gera um JWT token com os dados do usuário
     */
    public String gerarToken(Long usuarioId, Long igrejaId, String email, String role, String plano) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("igrejaId", igrejaId);
        claims.put("role", role);
        claims.put("plano", plano);
        claims.put("email", email);

        return criarToken(claims, usuarioId.toString());
    }

    /**
     * Cria o token JWT
     */
    private String criarToken(Map<String, Object> claims, String subject) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + jwtExpirationMs);

        return Jwts
                .builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(now)
                .expiration(expiration)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Extrai todas as claims do token
     */
    public Claims extrairClaims(String token) {
        try {
            return Jwts
                    .parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Extrai o userId (subject) do token
     */
    public Long extrairUsuarioId(String token) {
        try {
            Claims claims = extrairClaims(token);
            return claims != null ? Long.parseLong(claims.getSubject()) : null;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Extrai o igrejaId do token
     */
    public Long extrairIgrejaId(String token) {
        try {
            Claims claims = extrairClaims(token);
            return claims != null ? ((Number) claims.get("igrejaId")).longValue() : null;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Extrai a role do token
     */
    public String extrairRole(String token) {
        try {
            Claims claims = extrairClaims(token);
            return claims != null ? (String) claims.get("role") : null;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Extrai o plano do token
     */
    public String extrairPlano(String token) {
        try {
            Claims claims = extrairClaims(token);
            return claims != null ? (String) claims.get("plano") : null;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Extrai o email do token
     */
    public String extrairEmail(String token) {
        try {
            Claims claims = extrairClaims(token);
            return claims != null ? (String) claims.get("email") : null;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Valida se o token é válido
     */
    public boolean validarToken(String token) {
        return extrairClaims(token) != null;
    }

    /**
     * Verifica se o token está expirado
     */
    public boolean estaExpirado(String token) {
        try {
            Claims claims = extrairClaims(token);
            return claims == null || claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return true;
        }
    }
}
