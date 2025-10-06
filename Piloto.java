public class Piloto extends Persona {
    private int numeroCompetencia;
    private int victorias;
    private int polePosition;
    private int vueltasRapidas;
    private int podios;

    public Piloto(String dni, String nombre, String apellido, int numeroCompetencia, int victorias, int polePosition, int vueltasRapidas, int podios){
        super(dni, nombre, apellido);
        this.numeroCompetencia = numeroCompetencia;
        this.victorias = victorias;
        this.polePosition = polePosition;
        this.vueltasRapidas = vueltasRapidas;
        this.podios = podios;
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
}
