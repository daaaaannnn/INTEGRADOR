package gestionpracticas.logica;

import com.gestionpracticas.modelo.Programa;
import com.gestionpracticas.util.Utilidades;
import gestionpracticas.dao.ProgramaDAO;
import java.util.List;

public class ProgramaLogica {
    private final ProgramaDAO dao;
    public ProgramaLogica() { this.dao = new ProgramaDAO(); }

    public boolean crearPrograma(Programa p) {
        if (Utilidades.esVacio(p.getNombre()))  throw new IllegalArgumentException("El nombre es obligatorio.");
        if (Utilidades.esVacio(p.getCodigo()))  throw new IllegalArgumentException("El código es obligatorio.");
        p.setEstado("ACTIVO");
        return dao.insertar(p);
    }

    public List<Programa> obtenerTodos() { return dao.listarTodos(); }

    public boolean actualizar(Programa p) {
        if (Utilidades.esVacio(p.getNombre())) throw new IllegalArgumentException("El nombre es obligatorio.");
        return dao.actualizar(p);
    }

    public boolean desactivar(int id) { return dao.eliminar(id); }
}