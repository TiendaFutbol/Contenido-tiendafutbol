package cl.tienda.ms.contenido.repository;
import cl.tienda.ms.contenido.model.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    List<Comentario> findByBlogIdOrderByFechaAsc(Long blogId);
}
