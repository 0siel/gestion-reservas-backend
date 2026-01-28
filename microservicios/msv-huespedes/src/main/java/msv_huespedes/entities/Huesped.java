package msv_huespedes.entities;


import java.time.LocalDateTime;

import com.example.demo.enums.EstadoRegistro;
import com.example.demo.enums.Nacionalidad;

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
    @Size(min = 2, max = 100)
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")
    @Column(name="NOMBRE", nullable = false, length = 100)
    private String nombre;

    @NotBlank(message = "El apellido paterno es requerido")
    @Size(min = 2, max = 100)
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")
    @Column(name="APELLIDO_PATERNO", nullable = false, length = 100)
    private String apellidoPaterno;

    @NotBlank(message = "El apellido materno es requerido")
    @Size(min = 2, max = 100)
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")
    @Column(name="APELLIDO_MATERNO", nullable = false, length = 100)
    private String apellidoMaterno;

    @NotBlank(message = "El email es requerido")
    @Email
    @Column(name="EMAIL", nullable = false, unique = true, length = 150)
    private String email;

    @NotBlank(message = "El teléfono es requerido")
    @Pattern(regexp = "^\\d{10}$")
    @Column(name="TELEFONO", nullable = false, length = 20)
    private String telefono;

    @NotBlank(message = "El documento es requerido")
    @Column(name="DOCUMENTO", nullable = false, unique = true, length = 50)
    private String documento;

    @NotNull(message = "La nacionalidad es requerida") // Cambiamos NotBlank por NotNull para Enums
    @Enumerated(EnumType.STRING)
    @Column(name="NACIONALIDAD", nullable = false, length = 50)
    private Nacionalidad nacionalidad;

    @Column(name="FECHA_REGISTRO", insertable = false, updatable = false)
    private LocalDateTime fechaRegistro;

    @Enumerated(EnumType.STRING) // Guarda el valor como texto (ACTIVO/ELIMINADO)
    @Column(name="ESTADO_REGISTRO", nullable = false, length = 20)
    private EstadoRegistro estadoRegistro = EstadoRegistro.ACTIVO;
}