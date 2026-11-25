package cl.tienda.ms.contenido.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.time.OffsetDateTime;
import java.util.Set;
import java.util.LinkedHashSet;

@Entity
@Table(name = "blogs")
@Getter
@Setter
@NoArgsConstructor
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;
    @Column(nullable = false, length = 200)
    private String titulo;
    @Column(length = 400)
    private String resumen;
    @Lob
    private String contenido;
    @Column(length = 400)
    private String img;
    @Column(nullable = false)
    private OffsetDateTime fecha;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "blog_categoria",
        joinColumns = @JoinColumn(name = "blog_id"),
        inverseJoinColumns = @JoinColumn(name = "categoria_id"))
    private Set<CategoriaBlog> categorias = new LinkedHashSet<>();
}
