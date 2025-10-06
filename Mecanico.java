public class Mecanico extends Persona{
    private Especialidad especialidad;
    private int añosExperiencia;
    
    public Mecanico(String dni, String nombre, String apellido, Especialidad especialidad, int añosExperiencia){
        super(dni, nombre, apellido);
        this.especialidad = especialidad;
        this.añosExperiencia = añosExperiencia;
    }

    public Especialidad getEspecialidad(){
        return this.especialidad;
    }

    public int añosExperiencia(){
        return this.añosExperiencia;
    }

    public void setEspecialidad(Especialidad especialidad){
        this.especialidad = especialidad;
    }

    public void setAñosExperiencia(int añosExperiencia){
        this.añosExperiencia = añosExperiencia;
    }
}
