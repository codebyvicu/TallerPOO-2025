package com.taller.proyecto.logica;

public class Circuito {
    private String nombre;
    private int idCircuito;
    private int longitud; 
    private Pais pais;
    private Carrera carrera;

    public Circuito(String nombre, int longitud, Pais pais){
        this.nombre = nombre;
        this.longitud = longitud;
        this.pais = pais;
    }

    public Circuito(String nombre, int longitud, Pais pais, Carrera carrera) {
        this.nombre = nombre;
        this.longitud = longitud;
        this.pais = pais;
        this.carrera = carrera;
    }

    public String getNombre() {
        return this.nombre;
    }
    
    public int getLongitud() {
        return this.longitud;
    }

    public Pais getPais(){
        return this.pais;
    }

    public Carrera getCarrera(){
        return this.carrera;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setLongitud(int longitud) {
        this.longitud = longitud;
    }

    public void setPais (Pais pais){
        this.pais = pais;
    }

    public void setCarrera (Carrera carrera){
        this.carrera = carrera;
    }

    public int getIdCircuito(){
        return this.idCircuito;
    }

    public void setIdCircuito(int idCircuito){
        this.idCircuito = idCircuito;
    }

}
