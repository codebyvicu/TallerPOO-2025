package com.taller.proyecto.logica;

public class Persona{
    private String dni;
    private String nombre;
    private String apellido;
    private Pais pais;

    public Persona(){
        dni = "";
        nombre = "";
        apellido = "";
        pais = null;
    }

    public Persona(String dni, String nombre, String apellido, Pais pais){
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;  
        this.pais = pais;      
    }

    public String getDni(){
        return this.dni;
    }

    public String getNombre(){
        return this.nombre;
    }

    public String getApellido(){
        return this.apellido;
    }

    public Pais getPais(){
        return this.pais;
    }

    public void setDni(String dni){
        this.dni = dni;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public void setApellido(String apellido){
        this.apellido = apellido;
    }

    public void setPais(Pais pais){
        this.pais = pais;
    }
}