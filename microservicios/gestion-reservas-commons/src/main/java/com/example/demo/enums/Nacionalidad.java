package com.example.demo.enums;

public enum Nacionalidad {

    MEXICO("México"),
    ESTADOS_UNIDOS("Estados Unidos"),
    CANADA("Canadá"),
    ARGENTINA("Argentina"),
    BRASIL("Brasil"),
    COLOMBIA("Colombia"),
    CHILE("Chile"),
    PERU("Perú"),
    ESPAÑA("España"),
    FRANCIA("Francia"),
    ALEMANIA("Alemania"),
    ITALIA("Italia"),
    REINO_UNIDO("Reino Unido"),
    JAPON("Japón"),
    CHINA("China"),
    COREA_DEL_SUR("Corea del Sur"),
    AUSTRALIA("Australia"),
    OTRA("Otro");

    private final String nombre;

    Nacionalidad(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}