package gestionpracticas.logica;

import com.gestionpracticas.util.Utilidades;
import gestionpracticas.dao.GrupoDAO;
import gestionpracticas.modelo.Grupo;
import java.util.List;

public class GrupoLogica {

    private final GrupoDAO dao;

    public GrupoLogica() {
        this.dao = new GrupoDAO();
    }

    public boolean crearGrupo(Grupo g) {
        if (Utilidades.esVacio(g.getNombre()))
            throw new IllegalArgumentException("El nombre del grupo es obligatorio.");
        if (g.getCurso() == null)
            throw new IllegalArgumentException("El curso es obligatorio.");
        if (g.getDocente() == null)
            throw new IllegalArgumentException("El docente es obligatorio.");
        if (Utilidades.esVacio(g.getSemestre()))
            throw new IllegalArgumentException("El semestre es obligatorio.");
        if (g.getAnio() <= 0)
            throw new IllegalArgumentException("El año es obligatorio.");
        g.setEstado("ACTIVO");
        return dao.insertar(g);
    }

    public List<Grupo> obtenerTodos() {
        return dao.listarTodos();
    }

    public Grupo buscarPorId(int id) {
        return dao.buscarPorId(id);
    }

    public boolean actualizar(Grupo g) {
        if (Utilidades.esVacio(g.getNombre()))
            throw new IllegalArgumentException("El nombre del grupo es obligatorio.");
        if (g.getCurso() == null)
            throw new IllegalArgumentException("El curso es obligatorio.");
        return dao.actualizar(g);
    }

    public boolean desactivar(int id) {
        return dao.eliminar(id);
    }
}