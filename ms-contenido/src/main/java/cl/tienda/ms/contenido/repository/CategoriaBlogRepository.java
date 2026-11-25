package cl.tienda.ms.contenido.repository;
import cl.tienda.ms.contenido.model.CategoriaBlog;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CategoriaBlogRepository extends JpaRepository<CategoriaBlog, Long> {

}
