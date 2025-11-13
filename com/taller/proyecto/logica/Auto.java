package com.taller.proyecto.logica;

import java.util.*;

public class Auto {
    private String modelo;
    private String motor;
    private List <AutoPiloto> historialCarreras;

    public Auto(){
        this.historialCarreras = new ArrayList <>();
    }

    public Auto(String modelo, String motor){
        this.modelo = modelo;
        this.motor = motor;
    }

    public Auto (String modelo, String motor, List <AutoPiloto> historialCarreras){
        this.modelo=modelo;
        this.motor=motor;
        this.historialCarreras = historialCarreras;
    }

    public String getModelo(){
        return modelo;
    }
    public void setModelo(String modelo){
        this.modelo=modelo;
    }
    public String getMotor(){
        return motor;
    }
    public void setMotor(String motor){
        this.motor = motor;
    }

    public List <AutoPiloto> getHistorialCarreras(){
        return this.historialCarreras;
    }

    public void agregarHistorialCarreras(AutoPiloto a){
        this.historialCarreras.add(a);
    }
    
}