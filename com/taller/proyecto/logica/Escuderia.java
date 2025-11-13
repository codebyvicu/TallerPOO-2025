package com.taller.proyecto.logica;

import java.util.*;

public class Escuderia {
    private String nombre;
    private int idEscuderia;
    private List<Auto> autos;
    private Pais pais;
    private List<Mecanico> mecanicos;
    private List <PilotoEscuderia> historialPilotos;

    public Escuderia(){
        this.nombre= "";
        this.autos= new ArrayList <> ();
        this.mecanicos= new ArrayList <>();
        this.historialPilotos= new ArrayList<>();
    }

    public Escuderia(String nombre, Pais pais, int idEscuderia){
    this.idEscuderia = idEscuderia;
    this.nombre = nombre;
    this.pais = pais;
    // --- INICIO DEL ARREGLO ---
    this.autos = new ArrayList<>();
    this.mecanicos = new ArrayList<>();
    this.historialPilotos = new ArrayList<>();
    // --- FIN DEL ARREGLO ---
}

    public Escuderia (String nombre, List<Auto> autos, List<Mecanico> mecanicos, List<PilotoEscuderia> historialPilotos){
       this.nombre=nombre;
       this.autos= autos;
       this.mecanicos= mecanicos;
       this.historialPilotos= historialPilotos;
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre){
        this.nombre=nombre;
    }

    public Pais getPais(){
        return pais;
    }

    public void setPais(Pais pais){
        this.pais= pais;
    }

    public List<Auto> getAuto(){
        return autos;
    }

    public void agregarAuto(Auto a){
        this.autos.add(a);
    }

    public List<Mecanico> getMecanicos(){
        return mecanicos;
    }

    public void agregarMecanico(Mecanico m){
        this.mecanicos.add(m);
    }

    public List<PilotoEscuderia> getHistoriaPilotos(){
        return historialPilotos;
    }

    public void agregarHistorialPiloto (PilotoEscuderia hp){
        this.historialPilotos.add(hp);

    }

    public int getIdEscuderia(){
        return idEscuderia;
    }

    public void setIdEscuderia(int idEscuderia){
        this.idEscuderia = idEscuderia;
    }
}
