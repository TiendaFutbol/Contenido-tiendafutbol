package cl.tienda.ms.contenido.dto;
import jakarta.validation.constraints.*;


public record ComentarioRequest(
    @NotNull @Positive Long blogId,
    @NotBlank @Size(max = 1000) String comentario,
    @Positive Long comentarioPadreId
) {}
