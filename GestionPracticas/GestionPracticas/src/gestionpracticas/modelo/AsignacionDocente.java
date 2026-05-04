package gestionpracticas.modelo;

import java.util.Date;
import java.util.List;

public class AsignacionDocente {
    private int idAsignacionDocente;
    private Date fechaAsignacion;
    private String rolEnPractica;
    private Practica practica;
    private List<Usuario> usuarios;

    public AsignacionDocente() {}

    public AsignacionDocente(int idAsignacionDocente, Date fechaAsignacion,
                              String rolEnPractica, Practica practica, List<Usuario> usuarios) {
        this.idAsignacionDocente = idAsignacionDocente;
        this.fechaAsignacion = fechaAsignacion;
        this.rolEnPractica = rolEnPractica;
        this.practica = practica;
        this.usuarios = usuarios;
    }

    public int getIdAsignacionDocente() { return idAsignacionDocente; }
    public void setIdAsignacionDocente(int idAsignacionDocente) { this.idAsignacionDocente = idAsignacionDocente; }

    public Date getFechaAsignacion() { return fechaAsignacion; }
    public void setFechaAsignacion(Date fechaAsignacion) { this.fechaAsignacion = fechaAsignacion; }

    public String getRolEnPractica() { return rolEnPractica; }
    public void setRolEnPractica(String rolEnPractica) { this.rolEnPractica = rolEnPractica; }

    public Practica getPractica() { return practica; }
    public void setPractica(Practica practica) { this.practica = practica; }

    public List<Usuario> getUsuarios() { return usuarios; }
    public void setUsuarios(List<Usuario> usuarios) { this.usuarios = usuarios; }

    @Override
    public String toString() {
        return "AsignacionDocente{id=" + idAsignacionDocente + ", rol=" + rolEnPractica + "}";
    }
}
