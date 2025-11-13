package com.taller.proyecto.logica;

import java.util.*;

public class Pais {
    private int idPais;
    private String descripcion;
    private List <Escuderia> escuderias;
    private List <Persona> personas;
    private List <Circuito> circuitos;
    private List <Carrera> carreras;

    public Pais(){
        this.escuderias = new ArrayList <>();
        this.personas = new ArrayList <>();
        this.circuitos = new ArrayList<>();
        this.carreras = new ArrayList<>();
    }

    public Pais(int idPais, String descripcion){
        this.idPais = idPais;
        this.descripcion = descripcion;
    }

    public Pais(int idPais, String descripcion, List <Escuderia> escuderias, List <Persona> personas, List <Circuito> circuitos, List <Carrera> carreras) {
        this.idPais = idPais;
        this.descripcion = descripcion;
        this.escuderias = escuderias;
        this.personas = personas;
        this.circuitos = circuitos;
        this.carreras = carreras;
    }

    public int getIdPais() {
        return this.idPais;
    }
    public String getDescripcion() {
        return this.descripcion;
    }
    public List <Escuderia> getEscuderias(){
        return this.escuderias;
    }
    public List <Persona> getPersonas(){
        return this.personas;
    }
    public List <Circuito> getCircuitos(){
        return this.circuitos;
    }
    public List <Carrera> getCarreras(){
        return this.carreras;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public void setIdPais(int idPais) {
        this.idPais = idPais;
    }

    public void agregarEscuderia (Escuderia e){
        this.escuderias.add (e);
    }
    public void agregarPersona (Persona p){
        this.personas.add (p);
    }
    public void agregarCircuito (Circuito c){
        this.circuitos.add (c);
    }
    public void agregarCarrera (Carrera c){
        this.carreras.add (c);
    }

}






