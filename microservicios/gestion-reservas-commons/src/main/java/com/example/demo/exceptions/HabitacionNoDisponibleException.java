

package com.example.demo.exceptions;

//Esta excepción se usará cuando la habitación existe, pero está OCUPADA o en MANTENIMIENTO
public class HabitacionNoDisponibleException extends RuntimeException {
 public HabitacionNoDisponibleException(String message) {
     super(message);
 }
}