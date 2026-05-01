package gestionpracticas.modelo;

import java.util.Date;

public class Practica {
    private int idPractica;
    private String titulo;
    private String descripcion;
    private Institucion institucion;
    private Grupo grupo;
    private Date fechaInicio;
    private Date fechaFin;
    private String tipoPractica; // EMPRESARIAL, SOCIAL, INVESTIGACION
    private String estado;

    public Practica() {}

    public Practica(int idPractica, String titulo, String descripcion,
                    Institucion institucion, Grupo grupo, Date fechaInicio,
                    Date fechaFin, String tipoPractica, String estado) {
        this.idPractica = idPractica;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.institucion = institucion;
        this.grupo = grupo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.tipoPractica = tipoPractica;
        this.estado = estado;
    }

    public int getIdPractica() { return idPractica; }
    public void setIdPractica(int idPractica) { this.idPractica = idPractica; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Institucion getInstitucion() { return institucion; }
    public void setInstitucion(Institucion institucion) { this.institucion = institucion; }

    public Grupo getGrupo() { return grupo; }
    public void setGrupo(Grupo grupo) { this.grupo = grupo; }

    public Date getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(Date fechaInicio) { this.fechaInicio = fechaInicio; }

    public Date getFechaFin() { return fechaFin; }
    public void setFechaFin(Date fechaFin) { this.fechaFin = fechaFin; }

    public String getTipoPractica() { return tipoPractica; }
    public void setTipoPractica(String tipoPractica) { this.tipoPractica = tipoPractica; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    @Override
    public String toString() {
        return "Practica{id=" + idPractica + ", titulo=" + titulo + ", estado=" + estado + "}";
    }

    public int getinstitucion() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
