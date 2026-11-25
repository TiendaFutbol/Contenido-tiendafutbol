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
public class ContactoService {
    private final ContactoRepository repository;
    public List<Contacto> listar() { return repository.findAll(); }
    public Contacto buscar(Long id) {
        return repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Registro no encontrado"));
    }
    public Contacto crear(ContactoRequest request) {
        Contacto entidad = new Contacto();
        cargarDatos(entidad, request);
        entidad.setFecha(OffsetDateTime.now());
        return repository.save(entidad);
    }
    public Contacto actualizar(Long id, ContactoRequest request) {
        Contacto entidad = buscar(id);
        cargarDatos(entidad, request);
        return repository.save(entidad);
    }
    public void eliminar(Long id) { repository.delete(buscar(id)); }
    private void cargarDatos(Contacto entidad, ContactoRequest request) {
        entidad.setNombre(request.nombre().trim());
        entidad.setEmail(request.email().trim());
        entidad.setAsunto(request.asunto());
        entidad.setMensaje(request.mensaje().trim());
    }
}
