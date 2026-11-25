package cl.tienda.ms.contenido.dto;
import jakarta.validation.constraints.*;


public record CategoriaBlogRequest(
    @NotBlank @Size(max = 50) String nombre
) {}
