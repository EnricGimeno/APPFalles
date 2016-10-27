package com.gimeno.enric.falles_designv2;


// Creamos un objeto para las FALLAS
public class FallaObject {

    private String name;
    private String seccion;
    private String fallera;
    private String presidente;
    private String artista;
    private String lema;
    private String photoURL;
    private Double latitud;
    private Double longitud;

    public FallaObject(String name, String seccion, String fallera, String presidente, String artista, String lema, String photoURL, Double latitud, Double longitud) {
        this.name = name;
        this.seccion = seccion;
        this.fallera = fallera;
        this.presidente = presidente;
        this.artista = artista;
        this.lema = lema;
        this.photoURL = photoURL;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public String getName() {
        return name;
    }

    public String getSeccion() {
        return seccion;
    }

    public String getFallera() {
        return fallera;
    }

    public String getPresidente() {
        return presidente;
    }

    public String getArtista() {
        return artista;
    }

    public String getLema() {
        return lema;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public Double getLatitud() {
        return latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public void setFallera(String fallera) {
        this.fallera = fallera;
    }

    public void setPresidente(String presidente) {
        this.presidente = presidente;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public void setLema(String lema) {
        this.lema = lema;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }
}
