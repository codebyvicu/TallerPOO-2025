public class Pais {
 /**
 * Representa un país donde puede residir una escudería o realizarse una carrera.
 */
    private int idPais;
    private String descripcion;

    // Constructor
    public Pais(int idPais, String descripcion) {
        this.idPais = idPais;
        this.descripcion = descripcion;
    }

    // Getters y Setters
    public int getIdPais() {
        return idPais;
    }

    public void setIdPais(int idPais) {
        this.idPais = idPais;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    // toString
    @Override
    public String toString() {
        return "Pais [idPais=" + idPais + ", descripcion=" + descripcion + "]";
    }
}


