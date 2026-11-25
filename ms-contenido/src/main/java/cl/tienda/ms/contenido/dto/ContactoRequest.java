package cl.tienda.ms.contenido.dto;
import jakarta.validation.constraints.*;


public record ContactoRequest(
    @NotBlank @Size(max = 100) String nombre,
    @NotBlank @Email @Size(max = 150) String email,
    @Size(max = 200) String asunto,
    @NotBlank @Size(max = 1000) String mensaje
) {}
