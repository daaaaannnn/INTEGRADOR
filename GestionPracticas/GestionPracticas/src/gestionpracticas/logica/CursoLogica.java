package gestionpracticas.logica;

import gestionpracticas.dao.CursoDAO;
import gestionpracticas.modelo.Curso;
import com.gestionpracticas.util.Utilidades;
import java.util.List;

public class CursoLogica {

    private final CursoDAO dao;

    public CursoLogica() {
        this.dao = new CursoDAO();
    }

    public boolean crearCurso(Curso c) {
        if (Utilidades.esVacio(c.getNombre()))
            throw new IllegalArgumentException("El nombre del curso es obligatorio.");
        if (Utilidades.esVacio(c.getCodigo()))
            throw new IllegalArgumentException("El código del curso es obligatorio.");
        if (c.getPrograma() == null)
            throw new IllegalArgumentException("El programa es obligatorio.");
        if (c.getCreditos() <= 0)
            throw new IllegalArgumentException("Los créditos deben ser mayores a 0.");
        c.setEstado("ACTIVO");
        return dao.insertar(c);
    }

    public List<Curso> obtenerTodos() {
        return dao.listarTodos();
    }

    public Curso buscarPorId(int id) {
        return dao.buscarPorId(id);
    }

    public boolean actualizar(Curso c) {
        if (Utilidades.esVacio(c.getNombre()))
            throw new IllegalArgumentException("El nombre del curso es obligatorio.");
        if (Utilidades.esVacio(c.getCodigo()))
            throw new IllegalArgumentException("El código del curso es obligatorio.");
        return dao.actualizar(c);
    }

    public boolean desactivar(int id) {
        return dao.eliminar(id);
    }
}
