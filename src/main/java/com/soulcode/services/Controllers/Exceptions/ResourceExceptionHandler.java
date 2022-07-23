package com.soulcode.services.Controllers.Exceptions;

import com.soulcode.services.Services.Exceptions.DataIntegrityViolationException;
import com.soulcode.services.Services.Exceptions.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

// @ControllerAdvice - para tratamento de exceções
@ControllerAdvice
public class ResourceExceptionHandler {

    // criando o método
    // classe: StandardError e o objeto instanciado: error
    // regra:  @ExceptionHandler(EntityNotFoundException.class) - para tdo funcionar
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<StandardError> entityNotFound(EntityNotFoundException e, HttpServletRequest request) {
        StandardError error = new StandardError();
        error.setTimestamp(Instant.now());
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setError("Registro não Encontrado");
        error.setMessage(e.getMessage());
        error.setPath(request.getRequestURI());
        error.setTrace("EntityNotFoundException");

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    // nome do método - dataIntegrityViolationException
    public ResponseEntity<StandardError> dataIntegrityViolationException(DataIntegrityViolationException e, HttpServletRequest request) {

        StandardError error = new StandardError();
        error.setTimestamp(Instant.now());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setError("Atributo não pode ser Duplicado");
        error.setMessage(e.getMessage());
        error.setPath(request.getRequestURI());
        error.setTrace("DataIntegrityViolationException");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

}
