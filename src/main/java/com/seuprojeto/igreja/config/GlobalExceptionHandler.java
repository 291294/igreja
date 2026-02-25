package com.seuprojeto.igreja.config;

import java.net.URI;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(NoHandlerFoundException.class)
    public ProblemDetail handleNotFound(NoHandlerFoundException ex, HttpServletRequest request) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problem.setTitle("Endpoint não encontrado");
        problem.setDetail("O endpoint '" + request.getRequestURI() + "' não existe");
        problem.setProperty("timestamp", LocalDateTime.now());
        problem.setInstance(URI.create(request.getRequestURI()));

        log.warn("Endpoint não encontrado: {}", request.getRequestURI());

        return problem;
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ProblemDetail handleMethodNotAllowed(HttpRequestMethodNotSupportedException ex,
                                                HttpServletRequest request) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.METHOD_NOT_ALLOWED);
        problem.setTitle("Método HTTP não permitido");
        problem.setDetail("O método '" + ex.getMethod() + "' não é suportado para este endpoint");
        problem.setProperty("timestamp", LocalDateTime.now());
        problem.setInstance(URI.create(request.getRequestURI()));

        log.warn("Método inválido: {} em {}", ex.getMethod(), request.getRequestURI());

        return problem;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidation(MethodArgumentNotValidException ex,
                                          HttpServletRequest request) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle("Erro de validação");

        String errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .findFirst()
                .orElse("Dados inválidos");

        problem.setDetail(errorMessage);
        problem.setInstance(URI.create(request.getRequestURI()));
        problem.setProperty("timestamp", LocalDateTime.now());

        log.warn("Erro de validação em {}: {}", request.getRequestURI(), errorMessage);

        return problem;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail handleDataIntegrityViolation(DataIntegrityViolationException ex,
                                                      HttpServletRequest request) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        problem.setTitle("Conflito de dados");
        
        String detail = "Email já cadastrado no sistema";
        if (ex.getMessage().contains("email")) {
            detail = "Email já existe no sistema";
        } else if (ex.getMessage().contains("unique")) {
            detail = "Dados duplicados - este registro já existe";
        }
        
        problem.setDetail(detail);
        problem.setInstance(URI.create(request.getRequestURI()));
        problem.setProperty("timestamp", LocalDateTime.now());

        log.warn("Violação de integridade em {}: {}", request.getRequestURI(), ex.getMessage());

        return problem;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGenericException(Exception ex, HttpServletRequest request) {
        log.error("Erro interno na aplicação", ex);

        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problem.setTitle("Erro interno");
        problem.setDetail("Ocorreu um erro inesperado. Contate o suporte.");
        problem.setProperty("timestamp", LocalDateTime.now());
        problem.setInstance(URI.create(request.getRequestURI()));

        return problem;
    }
}
