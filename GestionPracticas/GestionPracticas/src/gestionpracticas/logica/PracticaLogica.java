package gestionpracticas.logica;

import gestionpracticas.modelo.Practica;
import com.gestionpracticas.util.Utilidades;
import gestionpracticas.dao.PracticaDAO;
import java.util.List;

public class PracticaLogica {

    private final PracticaDAO dao;

    public PracticaLogica() {
        this.dao = new PracticaDAO();
    }

    public boolean crearPractica(Practica p) {
        if (Utilidades.esVacio(p.getTitulo()))      throw new IllegalArgumentException("El título es obligatorio.");
        if (p.getInstitucion() == null)              throw new IllegalArgumentException("La institución es obligatoria.");
        if (p.getGrupo() == null)                   throw new IllegalArgumentException("El grupo es obligatorio.");
        if (p.getFechaInicio() == null)              throw new IllegalArgumentException("La fecha de inicio es obligatoria.");
        if (p.getFechaFin() == null)                 throw new IllegalArgumentException("La fecha de fin es obligatoria.");
        if (p.getFechaInicio().after(p.getFechaFin())) throw new IllegalArgumentException("La fecha de inicio no puede ser posterior a la fecha de fin.");
        p.setEstado("PENDIENTE");
        return dao.insertar(p);
    }

    public List<Practica> obtenerTodas() {
        return dao.listarTodos();
    }

    public Practica buscarPorId(int id) {
        return dao.buscarPorId(id);
    }

    public boolean actualizarPractica(Practica p) {
        if (Utilidades.esVacio(p.getTitulo())) throw new IllegalArgumentException("El título es obligatorio.");
        return dao.actualizar(p);
    }

    public boolean desactivarPractica(int id) {
        return dao.eliminar(id);
    }
}
