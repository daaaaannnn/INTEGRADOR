package gestionpracticas.logica;

import com.gestionpracticas.modelo.AsignacionDocente;
import gestionpracticas.dao.AsignacionDocenteDAO;
import java.util.List;

public class AsignacionDocenteLogica {

    private final AsignacionDocenteDAO dao;

    public AsignacionDocenteLogica() {
        this.dao = new AsignacionDocenteDAO();
    }

    public boolean asignar(AsignacionDocente a) {
        if (a.getPractica() == null)
            throw new IllegalArgumentException("La práctica es obligatoria.");
        if (!a.getUsuarios().isEmpty() && null != a.getUsuarios()) {
        } else {
            throw new IllegalArgumentException("Debe asignarse al menos un docente.");
        }
        if (a.getRolEnPractica() == null || a.getRolEnPractica().trim().isEmpty())
            throw new IllegalArgumentException("El rol en la práctica es obligatorio.");
        return dao.insertar(a);
    }

    public List<gestionpracticas.modelo.AsignacionDocente> listarPorPractica(int idPractica) {
        return dao.listarPorPractica(idPractica);
    }

    public boolean eliminar(int id) {
        return dao.eliminar(id);
    }
}