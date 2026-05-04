package gestionpracticas.logica;

import com.gestionpracticas.dao.EvaluacionDAO;
import gestionpracticas.modelo.Evaluacion;
import java.util.List;

public class EvaluacionLogica {

    private final EvaluacionDAO dao;

    public EvaluacionLogica() {
        this.dao = new EvaluacionDAO();
    }

    public boolean registrarEvaluacion(Evaluacion e) {
        if (e.getMatricula() == null)
            throw new IllegalArgumentException("La matrícula es obligatoria.");
        if (e.getRubrica() == null)
            throw new IllegalArgumentException("La rúbrica es obligatoria.");
        if (e.getEvaluador() == null)
            throw new IllegalArgumentException("El evaluador es obligatorio.");
        if (e.getPuntajeObtenido() < 0)
            throw new IllegalArgumentException("El puntaje no puede ser negativo.");
        if (e.getPuntajeObtenido() > 100)
            throw new IllegalArgumentException("El puntaje no puede superar 100.");
        e.setEstado("REALIZADA");
        return dao.insertar(e);
    }

    public List<Evaluacion> listarPorMatricula(int idMatricula) {
        return dao.listarPorMatricula(idMatricula);
    }

    public boolean actualizar(Evaluacion e) {
        if (e.getPuntajeObtenido() < 0 || e.getPuntajeObtenido() > 100)
            throw new IllegalArgumentException("El puntaje debe estar entre 0 y 100.");
        return dao.actualizar(e);
    }
}