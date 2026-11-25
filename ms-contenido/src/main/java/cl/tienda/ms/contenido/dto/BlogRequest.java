package cl.tienda.ms.contenido.dto;
import jakarta.validation.constraints.*;
import java.util.List;

public record BlogRequest(
    @NotBlank @Size(max = 200) String titulo,
    @Size(max = 400) String resumen,
    @NotBlank String contenido,
    @Size(max = 400) String img,
    @NotNull List<@NotNull @Positive Long> categoriaIds
) {}
