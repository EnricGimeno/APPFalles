package com.gimeno.enric.falles_designv2;


// Creamos una clase para los eventos del calendario.
public class EventCalendario {

    private String nombre;
    private String lugar;
    private String hora;

    public EventCalendario(String nombre, String lugar, String hora) {
        this.nombre = nombre;
        this.lugar = lugar;
        this.hora = hora;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getNombre() {
        return nombre;
    }

    public String getLugar() {
        return lugar;
    }

    public String getHora() {
        return hora;
    }
}
