package cl.tienda.ms.contenido.dto;
import jakarta.validation.constraints.*;


public record EditarComentarioRequest(
    @NotBlank @Size(max = 1000) String comentario
) {}
