package cl.tienda.ms.contenido.controller;
import cl.tienda.ms.contenido.model.*;
import cl.tienda.ms.contenido.dto.*;
import cl.tienda.ms.contenido.service.ComentarioService;
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
@RequestMapping("/api/comentarios")
@RequiredArgsConstructor
@Tag(name = "Comentario")
public class ComentarioController {
    private final ComentarioService service;
    @GetMapping
    @Operation(summary = "Listar comentarios")
    public List<Comentario> listar() { return service.listar(); }
    @GetMapping("/{id}")
    @Operation(summary = "Buscar comentario")
    public Comentario buscar(@PathVariable("id") Long id) { return service.buscar(id); }
    @GetMapping("/blog/{blogId}")
    @Operation(summary = "Comentarios de un blog")
    public List<Comentario> porBlog(@PathVariable("blogId") Long blogId) { return service.porBlog(blogId); }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Crear comentario o respuesta")
    public Comentario crear(@Valid @RequestBody ComentarioRequest request, @AuthenticationPrincipal Long usuarioId) {
        return service.crear(request, usuarioId);
    }
    @PutMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Editar comentario propio")
    public Comentario actualizar(@PathVariable("id") Long id, @Valid @RequestBody EditarComentarioRequest request,
            @AuthenticationPrincipal Long usuarioId, Authentication auth) {
        return service.actualizar(id, request, usuarioId, esAdmin(auth));
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Eliminar comentario propio")
    public void eliminar(@PathVariable("id") Long id, @AuthenticationPrincipal Long usuarioId, Authentication auth) {
        service.eliminar(id, usuarioId, esAdmin(auth));
    }
    private boolean esAdmin(Authentication auth) {
        return auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }
}
