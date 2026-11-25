package cl.tienda.ms.contenido.repository;
import cl.tienda.ms.contenido.model.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Long> {

}
