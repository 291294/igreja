package com.seuprojeto.igreja.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * JWT Filter - Intercepta requisições e valida JWT
 * Extrai igrejaId do token e insere no SecurityContext para uso em Controllers/Services
 */
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    HttpServletResponse response, 
                                    FilterChain filterChain) throws ServletException, IOException {
        
        try {
            String token = extrairToken(request);

            if (token != null && jwtProvider.validarToken(token)) {
                Long usuarioId = jwtProvider.extrairUsuarioId(token);
                Long igrejaId = jwtProvider.extrairIgrejaId(token);
                String role = jwtProvider.extrairRole(token);

                // Criar authorities a partir da role do token
                List<SimpleGrantedAuthority> authorities = new ArrayList<>();
                if (role != null && !role.isBlank()) {
                    authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role));
                }

                // Criar authentication com o usuário extraído do token
                UsernamePasswordAuthenticationToken auth = 
                    new UsernamePasswordAuthenticationToken(
                        usuarioId,
                        null,
                        authorities.stream().collect(Collectors.toList())
                    );

                // Armazenar igrejaId e role nos detalhes da authentication
                auth.setDetails(new TenantInfo(igrejaId, role));

                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (Exception e) {
            logger.error("Erro ao processar JWT: " + e.getMessage());
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Extrai o token do header Authorization
     * Header format: "Bearer <token>"
     */
    private String extrairToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }

    /**
     * Classe interna para armazenar informações de tenant
     */
    public static class TenantInfo {
        private Long igrejaId;
        private String role;

        public TenantInfo(Long igrejaId, String role) {
            this.igrejaId = igrejaId;
            this.role = role;
        }

        public Long getIgrejaId() {
            return igrejaId;
        }

        public String getRole() {
            return role;
        }
    }
}
