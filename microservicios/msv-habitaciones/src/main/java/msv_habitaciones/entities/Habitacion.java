package msv_habitaciones.entities;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

import com.example.demo.enums.EstadoHabitacion;
import com.example.demo.enums.EstadoRegistro;
import com.example.demo.enums.TipoHabitacion;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "HABITACIONES")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Habitacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_HABITACION")
    private Long id;

    @Column(name = "NUMERO", nullable = false, unique = true, length = 10)
    @NotBlank(message = "El número de habitación es requerido")
    @Size(max = 10, message = "El número puede tener máximo 10 caracteres")
    private String numero;

    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO", nullable = false, length = 20)
    @NotNull(message = "El tipo de habitación es requerido")
    private TipoHabitacion tipo;

    @Column(name = "PRECIO_NOCHE", nullable = false, precision = 10, scale = 2)
    @NotNull(message = "El precio por noche es requerido")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
    private BigDecimal precioNoche;

    @Column(name = "CAPACIDAD", nullable = false)
    @NotNull(message = "La capacidad es requerida")
    @Min(value = 1, message = "La capacidad mínima es 1 persona")
    private Integer capacidad;

    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO", nullable = false, length = 20)
    @NotNull(message = "El estado de la habitación es requerido")
    private EstadoHabitacion estado;

    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO_REGISTRO", nullable = false, length = 30)
    @NotNull(message = "El estado del registro es requerido")
    private EstadoRegistro estadoRegistro;
}
