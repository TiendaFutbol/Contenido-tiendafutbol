package cl.tienda.ms.contenido.exception;

import cl.tienda.ms.contenido.dto.ErrorResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.http.converter.HttpMessageNotReadableException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> error(ResponseStatusException e) {
        return ResponseEntity.status(e.getStatusCode())
                .body(new ErrorResponse(e.getStatusCode().value(), e.getReason()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> conflicto() {
        return ResponseEntity.status(409).body(new ErrorResponse(409, "Registro duplicado o relacionado"));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<ErrorResponse> datosInvalidos() {
        return ResponseEntity.badRequest().body(new ErrorResponse(400, "Datos inválidos"));
    }
}
