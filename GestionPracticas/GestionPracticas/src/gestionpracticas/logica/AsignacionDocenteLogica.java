package gestionpracticas.logica;

import gestionpracticas.modelo.AsignacionDocente;  // Cambiado de com.gestionpracticas a gestionpracticas
import gestionpracticas.dao.AsignacionDocenteDAO;
import java.util.List;

public class AsignacionDocenteLogica {

    private final AsignacionDocenteDAO dao;

    public AsignacionDocenteLogica() {
        this.dao = new AsignacionDocenteDAO();
    }

    public boolean asignar(AsignacionDocente a) {
        // Validaciones
        if (a.getPractica() == null) {
            throw new IllegalArgumentException("La práctica es obligatoria.");
        }
        
        // Corregida la condición: primero verificar null, luego si está vacío
        if (a.getUsuarios() == null || a.getUsuarios().isEmpty()) {
            throw new IllegalArgumentException("Debe asignarse al menos un docente.");
        }
        
        if (a.getRolEnPractica() == null || a.getRolEnPractica().trim().isEmpty()) {
            throw new IllegalArgumentException("El rol en la práctica es obligatorio.");
        }
        
        return dao.insertar(a);
    }

    // Corregido: usar la misma clase importada
    public List<AsignacionDocente> listarPorPractica(int idPractica) {
        return dao.listarPorPractica(idPractica);
    }

    public boolean eliminar(int id) {
        return dao.eliminar(id);
    }
}