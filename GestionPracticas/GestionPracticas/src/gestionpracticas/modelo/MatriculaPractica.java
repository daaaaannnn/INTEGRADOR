package gestionpracticas.modelo;

import gestionpracticas.modelo.Usuario;
import java.util.Date;

public class MatriculaPractica {
    private int idMatricula;
    private Usuario estudiante;
    private Practica practica;
    private Date fechaMatricula;
    private String estado;
    private String observaciones;

    public MatriculaPractica() {}

    public MatriculaPractica(int idMatricula, Usuario estudiante, Practica practica,
                              Date fechaMatricula, String estado, String observaciones) {
        this.idMatricula = idMatricula;
        this.estudiante = estudiante;
        this.practica = practica;
        this.fechaMatricula = fechaMatricula;
        this.estado = estado;
        this.observaciones = observaciones;
    }

    public int getIdMatricula() { return idMatricula; }
    public void setIdMatricula(int idMatricula) { this.idMatricula = idMatricula; }

    public Usuario getEstudiante() { return estudiante; }
    public void setEstudiante(Usuario estudiante) { this.estudiante = estudiante; }

    public Practica getPractica() { return practica; }
    public void setPractica(Practica practica) { this.practica = practica; }

    public Date getFechaMatricula() { return fechaMatricula; }
    public void setFechaMatricula(Date fechaMatricula) { this.fechaMatricula = fechaMatricula; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    @Override
    public String toString() {
        return "MatriculaPractica{id=" + idMatricula + ", estado=" + estado + "}";
    }
}
