package com.taller.proyecto.logica;

// REGISTRA PARTICIPACIÓN DE LOS PILOTOS EN UNA CARRERA CON EL AUTO QUE OCUPAN
public class AutoPiloto {
    private String fechaAsignacion;
    private Piloto piloto;
    private Auto auto;
    private Carrera carrera;
    //ATRIBUTOS PARA LOS RESULTADOS
    private int posicionFinal;
    private boolean vueltaRapida;

    public AutoPiloto(Piloto piloto, Auto auto, Carrera carrera, int posicionFinal, boolean vueltaRapida){
        this.piloto = piloto;
        this.auto = auto;
        this.carrera = carrera;
        this.posicionFinal = 0;
        this.vueltaRapida = false;
    }

    public AutoPiloto(Piloto piloto, Auto auto, Carrera carrera){
        this.piloto = piloto;
        this.auto = auto;
        this.carrera = carrera;
    }

    public AutoPiloto(String fechaAsignacion, Piloto piloto, Auto auto, Carrera carrera, int posicionFinal, boolean vueltaRapida){
        this.fechaAsignacion = fechaAsignacion;
        this.piloto = piloto;
        this.auto = auto;
        this.carrera = carrera;
        this.posicionFinal = posicionFinal;
        this.vueltaRapida = vueltaRapida;
    }

    public String getFechaAsignacion(){
        return this.fechaAsignacion;
    }

    public void setFechaAsignacion(String fechaAsignacion){
        this.fechaAsignacion = fechaAsignacion;
    }

    public Piloto getPiloto(){
        return piloto;
    }

    public void setPiloto(Piloto piloto){
        this.piloto = piloto;
    }

    public Auto getAuto(){
        return this.auto;
    }

    public void setAuto(Auto auto){
        this.auto = auto;
    }

    public Carrera getCarrera(){
        return this.carrera;
    }

    public void setCarrera(Carrera carrera){
        this.carrera = carrera;
    }
    public int getPosicionFinal() {
        return this.posicionFinal;
    }

    public void setPosicionFinal(int posicionFinal) {
        this.posicionFinal = posicionFinal;
    }

    public boolean getVueltaRapida() {
        return this.vueltaRapida;
    }

    public void setVueltaRapida(boolean vueltaRapida) {
        this.vueltaRapida = vueltaRapida;
}
}