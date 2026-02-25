package com.seuprojeto.igreja.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seuprojeto.igreja.dto.AuthResponseDTO;
import com.seuprojeto.igreja.dto.RegistroDTO;
import com.seuprojeto.igreja.model.Assinatura;
import com.seuprojeto.igreja.model.Igreja;
import com.seuprojeto.igreja.model.Plano;
import com.seuprojeto.igreja.model.Usuario;
import com.seuprojeto.igreja.repository.AssinaturaRepository;
import com.seuprojeto.igreja.repository.IgrejaRepository;
import com.seuprojeto.igreja.repository.PlanoRepository;
import com.seuprojeto.igreja.repository.UsuarioRepository;
import com.seuprojeto.igreja.security.JwtProvider;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/**
 * RegistroController - Endpoints públicos para registro de nova Igreja (SaaS)
 * Não requer autenticação
 */
@RestController
@RequestMapping("/public")
@Tag(name = "Autenticação", description = "Endpoints públicos de registro e autenticação")
public class RegistroController {

    @Autowired
    private PlanoRepository planoRepository;

    @Autowired
    private IgrejaRepository igrejaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AssinaturaRepository assinaturaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    /**
     * Registra uma nova Igreja com Plano FREE automático
     * Fluxo SaaS completo:
     * 1. Cria Plano FREE (se não existir)
     * 2. Cria Igreja
     * 3. Cria Assinatura ATIVA
     * 4. Cria Usuário ADMIN
     * 5. Retorna JWT para autenticação imediata
     */
    @PostMapping("/registro")
    @Operation(summary = "Registrar nova Igreja", 
               description = "Cria uma nova Igreja com Plano FREE e retorna JWT de autenticação")
    @ApiResponse(responseCode = "200", description = "Igreja registrada com sucesso, JWT enviado")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    @ApiResponse(responseCode = "409", description = "Email já cadastrado")
    public ResponseEntity<AuthResponseDTO> registrarIgreja(@Valid @RequestBody RegistroDTO dto) {
        
        // 1. Verificar se emails já existem
        if (igrejaRepository.findByEmail(dto.getEmailIgreja()).isPresent()) {
            return ResponseEntity.status(409).body(new AuthResponseDTO());
        }

        try {
            // 2. Obter ou criar Plano FREE
            Plano planoFree = planoRepository.findByNome("FREE")
                .orElseGet(() -> criarPlanoFree());

            // 3. Criar Igreja
            Igreja igreja = new Igreja(
                dto.getNomeIgreja(),
                dto.getEmailIgreja(),
                passwordEncoder.encode(dto.getSenhaIgreja()),
                planoFree
            );
            Igreja igrejaGravada = igrejaRepository.save(igreja);

            // 4. Criar Assinatura ATIVA
            Assinatura assinatura = new Assinatura(igrejaGravada, planoFree);
            assinatura.setMetodoPagamento("GRATUITO");
            assinatura.setStatus(Assinatura.StatusAssinatura.ATIVA);
            assinaturaRepository.save(assinatura);

            // 5. Criar Usuário ADMIN
            Usuario usuarioAdmin = new Usuario(
                dto.getNomeAdmin(),
                dto.getEmailAdmin(),
                passwordEncoder.encode(dto.getSenhaAdmin()),
                igrejaGravada,
                Usuario.Role.ADMIN
            );
            Usuario usuarioGravado = usuarioRepository.save(usuarioAdmin);

            // 6. Gerar JWT
            String token = jwtProvider.gerarToken(
                usuarioGravado.getId(),
                igrejaGravada.getId(),
                usuarioGravado.getEmail(),
                usuarioGravado.getRole().toString(),
                planoFree.getNome()
            );

            // 7. Retornar resposta
            AuthResponseDTO response = new AuthResponseDTO(
                token,
                usuarioGravado.getId(),
                igrejaGravada.getId(),
                igrejaGravada.getNome(),
                planoFree.getNome(),
                usuarioGravado.getRole().toString(),
                usuarioGravado.getEmail()
            );

            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            throw new RuntimeException("Erro ao registrar Igreja: " + e.getMessage());
        }
    }

    /**
     * Cria o Plano FREE padrão
     */
    private Plano criarPlanoFree() {
        Plano plano = new Plano(
            "FREE",
            50,           // limiteMembros
            1,            // limiteUsuarios
            BigDecimal.ZERO  // valorMensal
        );
        plano.setDescricao("Plano gratuito - até 50 membros, 1 usuário");
        return planoRepository.save(plano);
    }

    /**
     * Health check do endpoint público
     */
    @GetMapping("/health")
    @Operation(summary = "Health Check Público", description = "Verifica se o serviço de registro está disponível")
    public ResponseEntity<?> health() {
        return ResponseEntity.ok(new Object() {
            public String status = "UP";
            public String message = "API de Registro disponível";
        });
    }
}
