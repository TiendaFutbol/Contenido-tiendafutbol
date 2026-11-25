package cl.tienda.ms.contenido.service;
import cl.tienda.ms.contenido.model.*;
import cl.tienda.ms.contenido.dto.*;
import cl.tienda.ms.contenido.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;
import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class ComentarioService {
    private final ComentarioRepository repository;
    private final BlogRepository blogs;
    public List<Comentario> listar() { return repository.findAll(); }
    public List<Comentario> porBlog(Long blogId) {
        if (!blogs.existsById(blogId)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Blog no encontrado");
        return repository.findByBlogIdOrderByFechaAsc(blogId);
    }
    public Comentario buscar(Long id) {
        return repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Comentario no encontrado"));
    }
    public Comentario crear(ComentarioRequest request, Long usuarioId) {
        if (!blogs.existsById(request.blogId())) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Blog no encontrado");
        if (request.comentarioPadreId() != null) {
            Comentario padre = buscar(request.comentarioPadreId());
            if (!padre.getBlogId().equals(request.blogId())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El comentario padre pertenece a otro blog");
            }
        }
        Comentario comentario = new Comentario();
        comentario.setBlogId(request.blogId());
        comentario.setUsuarioId(usuarioId);
        comentario.setComentario(request.comentario().trim());
        comentario.setComentarioPadreId(request.comentarioPadreId());
        comentario.setFecha(OffsetDateTime.now());
        return repository.save(comentario);
    }
    public Comentario actualizar(Long id, EditarComentarioRequest request, Long usuarioId, boolean admin) {
        Comentario comentario = buscar(id);
        validarDueño(comentario, usuarioId, admin);
        comentario.setComentario(request.comentario().trim());
        return repository.save(comentario);
    }
    public void eliminar(Long id, Long usuarioId, boolean admin) {
        Comentario comentario = buscar(id);
        validarDueño(comentario, usuarioId, admin);
        repository.delete(comentario);
    }
    private void validarDueño(Comentario comentario, Long usuarioId, boolean admin) {
        if (!admin && !comentario.getUsuarioId().equals(usuarioId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No puedes modificar este comentario");
        }
    }
}
