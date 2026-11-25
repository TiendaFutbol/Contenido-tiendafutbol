package cl.tienda.ms.contenido.controller;
import cl.tienda.ms.contenido.model.*;
import cl.tienda.ms.contenido.dto.*;
import cl.tienda.ms.contenido.service.BlogService;
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
@RequestMapping("/api/blogs")
@RequiredArgsConstructor
@Tag(name = "Blog")
public class BlogController {
    private final BlogService service;
    @GetMapping
    @Operation(summary = "Listar blogs")
    public List<Blog> listar() { return service.listar(); }
    @GetMapping("/{id}")
    @Operation(summary = "Buscar blog")
    public Blog buscar(@PathVariable("id") Long id) { return service.buscar(id); }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Crear blog")
    public Blog crear(@Valid @RequestBody BlogRequest request, @AuthenticationPrincipal Long usuarioId) {
        return service.crear(request, usuarioId);
    }
    @PutMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Actualizar blog")
    public Blog actualizar(@PathVariable("id") Long id, @Valid @RequestBody BlogRequest request) {
        return service.actualizar(id, request);
    }
    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eliminar blog")
    public void eliminar(@PathVariable("id") Long id) { service.eliminar(id); }
}
