package cl.tienda.ms.contenido.repository;
import cl.tienda.ms.contenido.model.Contacto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ContactoRepository extends JpaRepository<Contacto, Long> {

}
