package remitly.bank_identifier_code.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import remitly.bank_identifier_code.dto.ExceptionResponseDTO;
import remitly.bank_identifier_code.exception.BadRequestException;
import remitly.bank_identifier_code.exception.ResourceNotFoundException;
import java.security.Timestamp;
import java.time.Instant;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponseDTO> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ExceptionResponseDTO response = new ExceptionResponseDTO(Instant.now(),ex.getMessage(),404);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionResponseDTO> handleBadRequestException(BadRequestException ex) {
        ExceptionResponseDTO response = new ExceptionResponseDTO(Instant.now(),ex.getMessage(),400);
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponseDTO> handleGeneralException(Exception ex) {
        ExceptionResponseDTO response = new ExceptionResponseDTO(Instant.now(),ex.getMessage(),500);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
