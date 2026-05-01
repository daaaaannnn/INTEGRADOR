package gestionpracticas.modelo;

public class NivelDesempeno {
    private int idNivel;
    private CriterioRubrica criterio;
    private String nombre; // EXCELENTE, BUENO, REGULAR, DEFICIENTE
    private String descripcion;
    private double puntaje;

    public NivelDesempeno() {}

    public NivelDesempeno(int idNivel, CriterioRubrica criterio, String nombre,
                           String descripcion, double puntaje) {
        this.idNivel = idNivel;
        this.criterio = criterio;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.puntaje = puntaje;
    }

    public int getIdNivel() { return idNivel; }
    public void setIdNivel(int idNivel) { this.idNivel = idNivel; }

    public CriterioRubrica getCriterio() { return criterio; }
    public void setCriterio(CriterioRubrica criterio) { this.criterio = criterio; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public double getPuntaje() { return puntaje; }
    public void setPuntaje(double puntaje) { this.puntaje = puntaje; }

    @Override
    public String toString() {
        return "NivelDesempeno{id=" + idNivel + ", nombre=" + nombre + ", puntaje=" + puntaje + "}";
    }
}
