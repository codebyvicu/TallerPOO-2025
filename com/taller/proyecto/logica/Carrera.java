package com.taller.proyecto.logica;

import java.util.*;

public class Carrera {
    private String fechaRealizacion;
    private int numeroVueltas;
    private String horaRealizacion;
    private Pais pais;
    private Circuito circuito;
    private List <AutoPiloto> participaciones;
    
    public Carrera (){
        this.participaciones = new ArrayList <>();
    }

    public Carrera(String fechaRealizacion, String horaRealizacion, Circuito circuito){
        this.fechaRealizacion = fechaRealizacion;
        this.horaRealizacion = horaRealizacion;
        this.circuito = circuito;
    }

    public Carrera (String fechaRealizacion, int numeroVueltas, String horaRealizacion, Pais pais, Circuito circuito, List <AutoPiloto> participantes){
        this.fechaRealizacion = fechaRealizacion;
        this.numeroVueltas=numeroVueltas;
        this.horaRealizacion=horaRealizacion;
        this.pais = pais;
        this.circuito = circuito;
        this.participaciones = participantes;
    }

    public String getFechaRealizacion(){
        return this.fechaRealizacion;
    }
    public void setFechaRealizacion(String fechaRealizacion){
        this.fechaRealizacion = fechaRealizacion;
    }

    public int getNumeroVueltas(){
        return this.numeroVueltas;
    }

    public Pais getPais(){
        return this.pais;
    }
    public Circuito getCircuito(){
        return this.circuito;
    }
    public List <AutoPiloto> getParticipaciones(){
        return participaciones;
    }

    public void setNumeroVueltas(int numeroVueltas){
        this.numeroVueltas = numeroVueltas;
    }

    public String getHoraRealizacion(){
        return horaRealizacion;
    }
    public void setHoraRealizacion(String horaRealizacion){
        this.horaRealizacion = horaRealizacion;
    }
    public void setPais(Pais pais){
        this.pais = pais;
    }
    public void setCircuito (Circuito circuito){
        this.circuito = circuito;
    }

    public void agregarParticipante (AutoPiloto par){
        this.participaciones.add (par);
    }

    public void planificarCarrera(String fechaRealizacion, String horaRealizacion, Circuito circuito){
        this.fechaRealizacion = fechaRealizacion;
        this.horaRealizacion = horaRealizacion;
        this.circuito = circuito;
    }
    
}
