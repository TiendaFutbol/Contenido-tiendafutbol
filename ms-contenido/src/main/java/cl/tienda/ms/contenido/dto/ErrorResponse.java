package cl.tienda.ms.contenido.dto;
import java.util.Map;
public record ErrorResponse(int status, String mensaje, Map<String, String> errores) {
    public ErrorResponse(int status, String mensaje) { this(status, mensaje, Map.of()); }
}
