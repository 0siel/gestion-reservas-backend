package msv_reservas.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.demo.enums.EstadoRegistro; // Importante
import com.example.demo.enums.EstadoReserva;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "RESERVAS", schema = "RESERVAS_USER")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter @ToString
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID_RESERVA")
    private Long id;

    @NotNull(message = "El id del huésped es requerido")
    @Column(name="ID_HUESPED", nullable = false)
    private Long idHuesped;

    @NotNull(message = "El id de la habitación es requerido")
    @Column(name="ID_HABITACION", nullable = false)
    private Long idHabitacion;

    @NotNull(message = "La fecha de entrada es requerido")
    @FutureOrPresent
    @Column(name="FECHA_ENTRADA", nullable = false)
    private LocalDate fechaEntrada;

    @NotNull(message = "La fecha de salida es requerida")
    @FutureOrPresent
    @Column(name="FECHA_SALIDA", nullable = false)
    private LocalDate fechaSalida;

    @NotNull(message = "La cantidad de noches es requerida")
    @Positive
    @Column(name="CANT_NOCHES", nullable = false)
    private Integer cantNoches;

    @NotNull(message = "El monto total es requerido")
    @Column(name="MONTO_TOTAL", nullable = false)
    private BigDecimal montoTotal;

    // ESTADO DE NEGOCIO (Confirmada, Cancelada, etc.)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "El estado de la reserva es requerido")
    @Column(name="ESTADO", nullable = false, length = 20)
    private EstadoReserva estado;
    
    @Column(name="FECHA_CREACION", insertable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    // ESTADO DE SISTEMA (Borrado Lógico: ACTIVO / ELIMINADO)
    @Enumerated(EnumType.STRING)
    @Column(name="ESTADO_REGISTRO", nullable = false, length = 20)
    private EstadoRegistro estadoRegistro = EstadoRegistro.ACTIVO;
}
