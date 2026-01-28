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
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$", message = "El nombre solo puede contener letras")
    @Column(name="NOMBRE", nullable = false, length = 100)
    private String nombre;

    @NotBlank(message = "El apellido paterno es requerido")
    @Size(min = 2, max = 100, message = "El apellido paterno debe tener entre 2 y 100 caracteres")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$", message = "El apellido paterno solo puede contener letras")
    @Column(name="APELLIDO_PATERNO", nullable = false, length = 100)
    private String apellidoPaterno;

    @NotBlank(message = "El apellido materno es requerido")
    @Size(min = 2, max = 100, message = "El apellido materno debe tener entre 2 y 100 caracteres")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$", message = "El apellido materno solo puede contener letras")
    @Column(name="APELLIDO_MATERNO", nullable = false, length = 100)
    private String apellidoMaterno;

    @NotBlank(message = "El email es requerido")
    @Email(message = "Debe ser un formato de email válido")
    @Size(max = 150)
    @Column(name="EMAIL", nullable = false, unique = true, length = 150)
    private String email;

    @NotBlank(message = "El teléfono es requerido")
    @Pattern(regexp = "^\\d{10}$", message = "El teléfono debe contener exactamente 10 dígitos numéricos")
    @Column(name="TELEFONO", nullable = false, length = 20)
    private String telefono;

    @NotBlank(message = "El documento es requerido")
    @Size(min = 5, max = 50, message = "El documento debe tener entre 5 y 50 caracteres")
    @Column(name="DOCUMENTO", nullable = false, unique = true, length = 50)
    private String documento;

    @NotBlank(message = "La nacionalidad es requerida")
    @Column(name="NACIONALIDAD", nullable = false, length = 50)
    private String nacionalidad;

    @Column(name="FECHA_REGISTRO", insertable = false, updatable = false)
    private LocalDateTime fechaRegistro;
}