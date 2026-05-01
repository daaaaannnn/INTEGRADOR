package gestionpracticas.modelo;

import java.util.Date;

public class Rubrica {
    private int idRubrica;
    private String nombre;
    private String descripcion;
    private Usuario docente;
    private Practica practica;
    private double puntajeTotal;
    private Date fechaCreacion;
    private String estado;

    public Rubrica() {}

    public Rubrica(int idRubrica, String nombre, String descripcion, Usuario docente,
                   Practica practica, double puntajeTotal, Date fechaCreacion, String estado) {
        this.idRubrica = idRubrica;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.docente = docente;
        this.practica = practica;
        this.puntajeTotal = puntajeTotal;
        this.fechaCreacion = fechaCreacion;
        this.estado = estado;
    }

    public int getIdRubrica() { return idRubrica; }
    public void setIdRubrica(int idRubrica) { this.idRubrica = idRubrica; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Usuario getDocente() { return docente; }
    public void setDocente(Usuario docente) { this.docente = docente; }

    public Practica getPractica() { return practica; }
    public void setPractica(Practica practica) { this.practica = practica; }

    public double getPuntajeTotal() { return puntajeTotal; }
    public void setPuntajeTotal(double puntajeTotal) { this.puntajeTotal = puntajeTotal; }

    public Date getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(Date fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    @Override
    public String toString() {
        return "Rubrica{id=" + idRubrica + ", nombre=" + nombre + "}";
    }
}
