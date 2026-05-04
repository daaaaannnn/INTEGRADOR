package gestionpracticas.logica;

import gestionpracticas.dao.RubricaDAO;
import gestionpracticas.modelo.Rubrica;
import com.gestionpracticas.util.Utilidades;
import java.util.List;

public class RubricaLogica {

    private final RubricaDAO dao;

    public RubricaLogica() {
        this.dao = new RubricaDAO();
    }

    public boolean crearRubrica(Rubrica r) {
        if (Utilidades.esVacio(r.getNombre()))
            throw new IllegalArgumentException("El nombre de la rúbrica es obligatorio.");
        if (r.getDocente() == null)
            throw new IllegalArgumentException("El docente es obligatorio.");
        if (r.getPractica() == null)
            throw new IllegalArgumentException("La práctica es obligatoria.");
        if (r.getPuntajeTotal() <= 0)
            throw new IllegalArgumentException("El puntaje total debe ser mayor a 0.");
        r.setEstado("ACTIVO");
        return dao.insertar(r);
    }

    public List<Rubrica> listarPorDocente(int idDocente) {
        return dao.listarPorDocente(idDocente);
    }

    public boolean desactivar(int id) {
        return dao.eliminar(id);
    }
}