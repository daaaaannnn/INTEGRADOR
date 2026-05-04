package gestionpracticas.modelo;

import java.util.Date;

public class Evaluacion {
    private int idEvaluacion;
    private MatriculaPractica matricula;
    private Rubrica rubrica;
    private Usuario evaluador;
    private Date fechaEvaluacion;
    private double puntajeObtenido;
    private String observaciones;
    private String estado; // PENDIENTE, REALIZADA, APROBADA

    public Evaluacion() {}

    public Evaluacion(int idEvaluacion, MatriculaPractica matricula, Rubrica rubrica,
                      Usuario evaluador, Date fechaEvaluacion, double puntajeObtenido,
                      String observaciones, String estado) {
        this.idEvaluacion = idEvaluacion;
        this.matricula = matricula;
        this.rubrica = rubrica;
        this.evaluador = evaluador;
        this.fechaEvaluacion = fechaEvaluacion;
        this.puntajeObtenido = puntajeObtenido;
        this.observaciones = observaciones;
        this.estado = estado;
    }

    public int getIdEvaluacion() { return idEvaluacion; }
    public void setIdEvaluacion(int idEvaluacion) { this.idEvaluacion = idEvaluacion; }

    public MatriculaPractica getMatricula() { return matricula; }
    public void setMatricula(MatriculaPractica matricula) { this.matricula = matricula; }

    public Rubrica getRubrica() { return rubrica; }
    public void setRubrica(Rubrica rubrica) { this.rubrica = rubrica; }

    public Usuario getEvaluador() { return evaluador; }
    public void setEvaluador(Usuario evaluador) { this.evaluador = evaluador; }

    public Date getFechaEvaluacion() { return fechaEvaluacion; }
    public void setFechaEvaluacion(Date fechaEvaluacion) { this.fechaEvaluacion = fechaEvaluacion; }

    public double getPuntajeObtenido() { return puntajeObtenido; }
    public void setPuntajeObtenido(double puntajeObtenido) { this.puntajeObtenido = puntajeObtenido; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    @Override
    public String toString() {
        return "Evaluacion{id=" + idEvaluacion + ", puntaje=" + puntajeObtenido + ", estado=" + estado + "}";
    }
}
