package com.seuprojeto.igreja.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

/**
 * Configuração do Swagger/OpenAPI para documentação da API.
 * Acesse em: http://localhost:8080/swagger-ui.html
 * JSON em: http://localhost:8080/v3/api-docs
 * YAML em: http://localhost:8080/v3/api-docs.yaml
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Igreja v1.0.0")
                        .description("Sistema de gerenciamento de Igreja com módulos de Membros, Contribuições e Configurações")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Suporte")
                                .email("suporte@igreja.local")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Servidor de Desenvolvimento"),
                        new Server()
                                .url("https://api.igreja.com")
                                .description("Servidor de Produção")
                ));
    }
}
