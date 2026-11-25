package cl.tienda.ms.contenido.controller;
import cl.tienda.ms.contenido.model.*;
import cl.tienda.ms.contenido.dto.*;
import cl.tienda.ms.contenido.service.ContactoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/contactos")
@RequiredArgsConstructor
@Tag(name = "Contacto")
public class ContactoController {
    private final ContactoService service;
    @GetMapping
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Listar registros")
    public List<Contacto> listar() { return service.listar(); }
    @GetMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Buscar registro")
    public Contacto buscar(@PathVariable("id") Long id) { return service.buscar(id); }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear registro")
    public Contacto crear(@Valid @RequestBody ContactoRequest request) { return service.crear(request); }
    @PutMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Actualizar registro")
    public Contacto actualizar(@PathVariable("id") Long id, @Valid @RequestBody ContactoRequest request) {
        return service.actualizar(id, request);
    }
    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eliminar registro")
    public void eliminar(@PathVariable("id") Long id) { service.eliminar(id); }
}
