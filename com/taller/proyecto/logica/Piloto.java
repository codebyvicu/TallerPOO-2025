package com.taller.proyecto.logica;

import java.util.*;

public class Piloto extends Persona {
    private int numeroCompetencia;
    private int victorias;
    private int polePosition;
    private int vueltasRapidas;
    private int podios;
    private int puntosAcumulados;
    //Historial de las escuderías a las que perteneció
    private List <PilotoEscuderia> historialEscuderia;
    //Historial de las carreras en las que compitió
    private List <AutoPiloto> historialCarreras;

    public Piloto(){
        super("", "", "", null);
        this.historialEscuderia = new ArrayList <> ();
        this.historialCarreras = new ArrayList <> ();
        this.puntosAcumulados = 0;
    }

    public Piloto(String dni, String nombre, String apellido, int numeroCompetencia, int victorias, int polePosition, int vueltasRapidas, int podios, Pais pais){
        super(dni, nombre, apellido, pais);
        this.numeroCompetencia = numeroCompetencia;
        this.victorias = victorias;
        this.polePosition = polePosition;
        this.vueltasRapidas = vueltasRapidas;
        this.podios = podios;
        this.puntosAcumulados = 0;
    }

    public Piloto(String dni, String nombre, String apellido, int numeroCompetencia, int victorias, int polePosition, int vueltasRapidas, int podios, Pais pais, List <PilotoEscuderia> historialEscuderia, List <AutoPiloto> historialCarreras){
        super(dni, nombre, apellido, pais);
        this.numeroCompetencia = numeroCompetencia;
        this.victorias = victorias;
        this.polePosition = polePosition;
        this.vueltasRapidas = vueltasRapidas;
        this.podios = podios;
        this.historialEscuderia = historialEscuderia;
        this.historialCarreras = historialCarreras;
        this.puntosAcumulados = 0;
    }

    public int getNumeroCompetencia(){
        return this.numeroCompetencia;
    }

    public int getVictorias(){
        return this.victorias;
    }

    public int getPolePosition(){
        return this.polePosition;
    }

    public int getVueltasRapidas(){
        return this.vueltasRapidas;
    }

    public int getPodios(){
        return this.podios;
    }

    public List <PilotoEscuderia> getHistorialEscuderia(){
        return this.historialEscuderia;
    }

     public List <AutoPiloto> getHistorialCarreras(){
        return this.historialCarreras;
    }

    public void setNumeroCompetencia(int numeroCompetencia){
        this.numeroCompetencia = numeroCompetencia;
    }

    public void setVictorias(int victorias){
        this.victorias = victorias;
    }

    public void setPolePosition(int polePosition){
        this.polePosition = polePosition;
    }

    public void setVueltasRapidas(int vueltasRapidas){
        this.vueltasRapidas = vueltasRapidas;
    }

    public void setPodios(int podios){
        this.podios = podios;
    }

    public void agregarPilotoEscuderia(PilotoEscuderia p){
        this.historialEscuderia.add(p);
    }

    public void agregarHistorialCarreras(AutoPiloto a){
        this.historialCarreras.add(a);
    }
    public int getPuntosAcumulados(){
        return this.puntosAcumulados;
    }

    public void sumarPuntos(int puntos){
        this.puntosAcumulados += puntos;
    }
}
