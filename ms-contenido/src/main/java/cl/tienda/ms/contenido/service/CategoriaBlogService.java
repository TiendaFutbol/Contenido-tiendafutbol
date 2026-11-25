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
public class CategoriaBlogService {
    private final CategoriaBlogRepository repository;
    public List<CategoriaBlog> listar() { return repository.findAll(); }
    public CategoriaBlog buscar(Long id) {
        return repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Registro no encontrado"));
    }
    public CategoriaBlog crear(CategoriaBlogRequest request) {
        CategoriaBlog entidad = new CategoriaBlog();
        cargarDatos(entidad, request);
        return repository.save(entidad);
    }
    public CategoriaBlog actualizar(Long id, CategoriaBlogRequest request) {
        CategoriaBlog entidad = buscar(id);
        cargarDatos(entidad, request);
        return repository.save(entidad);
    }
    public void eliminar(Long id) { repository.delete(buscar(id)); }
    private void cargarDatos(CategoriaBlog entidad, CategoriaBlogRequest request) {
        entidad.setNombre(request.nombre().trim());
    }
}
