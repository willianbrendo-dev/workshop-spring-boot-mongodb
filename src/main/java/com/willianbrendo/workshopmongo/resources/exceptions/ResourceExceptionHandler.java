package com.willianbrendo.workshopmongo.resources.exceptions;

import java.time.Instant;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.willianbrendo.workshopmongo.services.exceptions.ObjectNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {

	/**
     * Manipula a exceção ResourceNotFoundException.
     * @param e A exceção lançada (ResourceNotFoundException).
     * @param request O objeto da requisição HTTP.
     * @return ResponseEntity com status 404 Not Found e o corpo do erro.
     */
    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> resourceNotFound(ObjectNotFoundException e, HttpServletRequest request) {
        
        // Define o código de status HTTP 404 (Not Found)
        HttpStatus status = HttpStatus.NOT_FOUND; 
        
        // 1. Cria o objeto de erro padrão (StandardError)
        StandardError err = new StandardError(
            Instant.now(),                       // Horário do erro
            status.value(),                      // Código 404
            "Resource Not Found",                // Tipo de erro
            e.getMessage(),                      // Mensagem detalhada da exceção
            request.getRequestURI()              // URI que causou o erro
        );
        
        // 2. Retorna a resposta com o status 404 e o corpo de erro
        return ResponseEntity.status(status).body(err);
    }
    
    /**
     * Manipulador para a exceção DataIntegrityViolationException.
     * Mapeia para o status HTTP 400 Bad Request (Violação de FK/Chave Única).
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<StandardError> database(DataIntegrityViolationException e, HttpServletRequest request) {
        
        // Define o código de status HTTP 400
        HttpStatus status = HttpStatus.BAD_REQUEST; 
        
        StandardError err = new StandardError(
            Instant.now(),                       
            status.value(),                      // 400
            "Database Integrity Violation",      
            e.getMessage(),                      
            request.getRequestURI()              
        );
        
        return ResponseEntity.status(status).body(err);
    }
}
