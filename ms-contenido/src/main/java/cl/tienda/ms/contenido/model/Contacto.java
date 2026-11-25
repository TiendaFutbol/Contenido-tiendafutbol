package cl.tienda.ms.contenido.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.time.OffsetDateTime;

@Entity
@Table(name = "contactos")
@Getter
@Setter
@NoArgsConstructor
public class Contacto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String nombre;
    @Column(nullable = false, length = 150)
    private String email;
    @Column(length = 200)
    private String asunto;
    @Column(nullable = false, length = 1000)
    private String mensaje;
    @Column(nullable = false)
    private OffsetDateTime fecha;
}
