package cl.tienda.ms.contenido.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "categorias_blog")
@Getter
@Setter
@NoArgsConstructor
public class CategoriaBlog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, length = 50)
    private String nombre;
}
