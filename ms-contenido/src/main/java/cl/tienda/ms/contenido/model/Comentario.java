package cl.tienda.ms.contenido.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.time.OffsetDateTime;

@Entity
@Table(name = "blog_comentarios")
@Getter
@Setter
@NoArgsConstructor
public class Comentario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "blog_id", nullable = false)
    private Long blogId;
    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;
    @Column(nullable = false, length = 1000)
    private String comentario;
    @Column(nullable = false)
    private OffsetDateTime fecha;
    @Column(name = "comentario_padre_id")
    private Long comentarioPadreId;
}
