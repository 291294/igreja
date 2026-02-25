package com.seuprojeto.igreja.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Utilitário para extrair informações de tenant do SecurityContext
 * Identifica qual Igreja o usuário autenticado pertence
 */
public class TenantContextHolder {

    /**
     * Extrai o igrejaId do token JWT armazenado no SecurityContext
     */
    public static Long getIgrejaId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        if (auth != null && auth.getDetails() instanceof JwtFilter.TenantInfo) {
            JwtFilter.TenantInfo tenantInfo = (JwtFilter.TenantInfo) auth.getDetails();
            return tenantInfo.getIgrejaId();
        }
        
        throw new SecurityException("Usuário não autenticado ou igrejaId não disponível");
    }

    /**
     * Extrai a role (ADMIN, MEMBRO, etc) do token JWT
     */
    public static String getRole() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        if (auth != null && auth.getDetails() instanceof JwtFilter.TenantInfo) {
            JwtFilter.TenantInfo tenantInfo = (JwtFilter.TenantInfo) auth.getDetails();
            return tenantInfo.getRole();
        }
        
        return null;
    }
}
