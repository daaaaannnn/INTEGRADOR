package gestionpracticas.logica;

import gestionpracticas.dao.RegistroActividadDAO;
import gestionpracticas.modelo.RegistroActividad;
import com.gestionpracticas.util.Utilidades;
import java.util.List;

public class RegistroActividadLogica {

    private final RegistroActividadDAO dao;

    public RegistroActividadLogica() {
        this.dao = new RegistroActividadDAO();
    }

    public boolean registrar(RegistroActividad r) {
        if (Utilidades.esVacio(r.getDescripcion()))
            throw new IllegalArgumentException("La descripción de la actividad es obligatoria.");
        if (r.getHoras() <= 0)
            throw new IllegalArgumentException("Las horas deben ser mayores a 0.");
        if (r.getHoras() > 24)
            throw new IllegalArgumentException("Las horas no pueden superar 24 por actividad.");
        if (r.getMatricula() == null)
            throw new IllegalArgumentException("La matrícula es obligatoria.");
        if (Utilidades.esVacio(r.getTipoActividad()))
            throw new IllegalArgumentException("El tipo de actividad es obligatorio.");
        r.setEstado("PENDIENTE");
        return dao.insertar(r);
    }

    public List<RegistroActividad> listarPorMatricula(int idMatricula) {
        return dao.listarPorMatricula(idMatricula);
    }

    public boolean aprobar(int idRegistro) {
        return dao.actualizarEstado(idRegistro, "APROBADO");
    }

    public boolean rechazar(int idRegistro, String motivo) {
        return dao.actualizarEstado(idRegistro, "RECHAZADO");
    }

    public List<RegistroActividad> listarTodos() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}