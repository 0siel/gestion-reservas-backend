# Sistema de Gestión de Reservas para Hoteles

Sistema basado en **arquitectura de microservicios** para la gestión integral de reservas hoteleras.  
El proyecto utiliza **Spring Boot** para el backend y **Angular 16+** para el frontend, integrando **Eureka Server**, **API Gateway** y **JWT** para seguridad y comunicación entre servicios.

---

## Objetivo

Desarrollar un sistema escalable y seguro que permita administrar huéspedes, habitaciones y reservas hoteleras, controlando accesos por roles y centralizando las peticiones mediante un API Gateway.

---

## 🧩 Arquitectura del Sistema
## Arquitectura del Sistema

Frontend (Angular)
        |
        v
API Gateway (Spring Boot)
        |
        v
Eureka Server
        |
        +-------------------------------+
        |              |                |
  Huéspedes      Habitaciones        Reservas
        |
        v
    Oracle DB


**Authorization Server**  
Servidor independiente encargado de la autenticación y generación de tokens JWT (no registrado en Eureka).

---

## Tecnologías Utilizadas

### Backend
- Java 17+
- Spring Boot
- Spring Data JPA
- Spring Security + JWT
- OpenFeign
- Eureka Server
- API Gateway

### Frontend
- Angular 16+
- HttpClient
- RouterModule
- Guards & Interceptors

### Base de Datos
- Oracle Database

---

## Tipos de Usuarios

### Recepcionista (USER)
- Registrar y consultar huéspedes
- Crear y modificar reservas
- Check-in y check-out
- Consultar disponibilidad  
 No puede crear usuarios  
 No puede modificar precios

### Gerente (ADMIN)
- Todas las funciones del recepcionista
- Gestión de habitaciones
- Modificación de precios
- Gestión de usuarios
- Reportes de ocupación e ingresos

---

## Microservicios

### Authorization Server
- Autenticación de usuarios
- Emisión de tokens JWT
- Roles: `ADMIN`, `USER`

### Huéspedes
- Registro y gestión de huéspedes
- Validaciones de email y teléfono únicos

### Habitaciones
- Inventario de habitaciones
- Estados: Disponible, Ocupada, Limpieza, Mantenimiento

### Reservas
- Creación, modificación y cancelación de reservas
- Cálculo automático de noches y total
- Estados: Confirmada, En curso, Finalizada, Cancelada

---

## Seguridad

- Contraseñas encriptadas
- JWT con expiración
- Control de accesos por rol
- API Gateway valida y propaga tokens
- Frontend con guards y manejo de expiración

---

## Flujos Principales

- **Check-in**: Registrar huésped → Ver disponibilidad → Crear reserva → Confirmar
- **Check-out**: Seleccionar reserva → Calcular total → Liberar habitación
- **Modificar reserva**: Verificar disponibilidad → Recalcular costo

---

## Estados de Reservas

| Estado       | Color | Descripción |
|--------------|-------|------------|
| Confirmada   | Azul  | Pendiente de check-in |
| En curso     | Amarillo | Huésped hospedado |
| Finalizada   | Verde | Estadía completada |
| Cancelada    | Rojo  | Reserva cancelada |

---

## Validaciones Importantes

- Fechas de entrada < fechas de salida
- Habitaciones disponibles
- Precios positivos
- Campos obligatorios no vacíos
- Restricciones según estado de reserva
