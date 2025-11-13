package com.taller.proyecto.logica;

import java.util.*;

public class Mecanico extends Persona{
    private Especialidad especialidad;
    private int añosExperiencia;
    private List <Escuderia> escuderias;

    public Mecanico(){
        super("", "", "", null);
        this.escuderias = new ArrayList<>();
    }

    public Mecanico(String dni, String nombre, String apellido, Pais pais, Especialidad especialidad, int añosExperiencia){
        super(dni, nombre, apellido, pais);
        this.especialidad = especialidad;
        this.añosExperiencia = añosExperiencia;
    }
    
    public Mecanico(String dni, String nombre, String apellido, Especialidad especialidad, int añosExperiencia, Pais pais, List <Escuderia> escuderias){
        super(dni, nombre, apellido, pais);
        this.especialidad = especialidad;
        this.añosExperiencia = añosExperiencia;
        this.escuderias = escuderias;
    }

    public Especialidad getEspecialidad(){
        return this.especialidad;
    }

    public int añosExperiencia(){
        return this.añosExperiencia;
    }

    public List <Escuderia> getEscuderias(){
        return this.escuderias;
    }

    public void setEspecialidad(Especialidad especialidad){
        this.especialidad = especialidad;
    }

    public void setAñosExperiencia(int añosExperiencia){
        this.añosExperiencia = añosExperiencia;
    }

    public void agregarEscuderia(Escuderia e){
        this.escuderias.add(e);
    }
}