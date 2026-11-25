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
public class BlogService {
    private final BlogRepository repository;
    private final CategoriaBlogRepository categorias;

    public List<Blog> listar() { return repository.findAll(); }
    public Blog buscar(Long id) {
        return repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Blog no encontrado"));
    }
    public Blog crear(BlogRequest request, Long usuarioId) {
        Blog blog = new Blog();
        blog.setUsuarioId(usuarioId);
        blog.setFecha(OffsetDateTime.now());
        cargarDatos(blog, request);
        return repository.save(blog);
    }
    public Blog actualizar(Long id, BlogRequest request) {
        Blog blog = buscar(id);
        cargarDatos(blog, request);
        return repository.save(blog);
    }
    public void eliminar(Long id) { repository.delete(buscar(id)); }
    private void cargarDatos(Blog blog, BlogRequest request) {
        blog.setTitulo(request.titulo().trim());
        blog.setResumen(request.resumen());
        blog.setContenido(request.contenido());
        blog.setImg(request.img());
        Set<CategoriaBlog> lista = new LinkedHashSet<>();
        for (Long id : request.categoriaIds()) {
            lista.add(categorias.findById(id).orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoría no encontrada")));
        }
        blog.getCategorias().clear();
        blog.getCategorias().addAll(lista);
    }
}
