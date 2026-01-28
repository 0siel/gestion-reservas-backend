package msv_huespedes.entities;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "HUESPEDES", schema = "HUESPEDES_USER")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Huesped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID_HUESPED")
    private Long id;

    @NotBlank(message = "El nombre es requerido")
    @Size(max = 100, message = "El nombre no puede exceder los 100 caracteres")
    @Column(name="NOMBRE", nullable = false, length = 100)
    private String nombre;

    @NotBlank(message = "El apellido es requerido")
    @Size(max = 100, message = "El apellido no puede exceder los 100 caracteres")
    @Column(name="APELLIDO", nullable = false, length = 100)
    private String apellido;

    @NotBlank(message = "El email es requerido")
    @Email(message = "Debe ser un email válido")
    @Column(name="EMAIL", nullable = false, unique = true, length = 150)
    private String email;

    @NotBlank(message = "El documento es requerido")
    @Column(name="DOCUMENTO", nullable = false, unique = true, length = 50)
    private String documento;

    @NotBlank(message = "La nacionalidad es requerida")
    @Column(name="NACIONALIDAD", nullable = false, length = 50)
    private String nacionalidad;

    @Column(name="FECHA_REGISTRO", insertable = false, updatable = false)
    private LocalDateTime fechaRegistro;
}