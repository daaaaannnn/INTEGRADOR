package gestionpracticas;

import com.gestionpracticas.dao.*;
import com.gestionpracticas.util.Utilidades;
import gestionpracticas.dao.AsignacionDocenteDAO;
import gestionpracticas.dao.CursoDAO;
import gestionpracticas.dao.GrupoDAO;
import gestionpracticas.dao.MatriculaPracticaDAO;
import gestionpracticas.dao.RegistroActividadDAO;
import gestionpracticas.modelo.AsignacionDocente;
import gestionpracticas.modelo.Curso;
import gestionpracticas.modelo.Evaluacion;
import gestionpracticas.modelo.Grupo;
import gestionpracticas.modelo.MatriculaPractica;
import gestionpracticas.modelo.RegistroActividad;
import java.util.List;

// ── CursoLogica ────────────────────────────────────────────────────────────────
class CursoLogica {
    private final CursoDAO dao;
    public CursoLogica() { this.dao = new CursoDAO(); }

    public boolean crearCurso(Curso c) {
        if (Utilidades.esVacio(c.getNombre()))  throw new IllegalArgumentException("Nombre obligatorio.");
        if (Utilidades.esVacio(c.getCodigo()))  throw new IllegalArgumentException("Código obligatorio.");
        if (c.getPrograma() == null)            throw new IllegalArgumentException("Programa obligatorio.");
        c.setEstado("ACTIVO");
        return dao.insertar(c);
    }
    public List<Curso> obtenerTodos() { return dao.listarTodos(); }
    public Curso buscarPorId(int id)  { return dao.buscarPorId(id); }
    public boolean actualizar(Curso c){ return dao.actualizar(c); }
    public boolean desactivar(int id) { return dao.eliminar(id); }
}

// ── GrupoLogica ────────────────────────────────────────────────────────────────
class GrupoLogica {
    private GrupoDAO dao;
    public GrupoLogica() { this.dao = new GrupoDAO(); }

    public boolean crearGrupo(Grupo g) {
        if (Utilidades.esVacio(g.getNombre())) throw new IllegalArgumentException("Nombre obligatorio.");
        if (g.getCurso() == null)              throw new IllegalArgumentException("Curso obligatorio.");
        if (g.getDocente() == null)            throw new IllegalArgumentException("Docente obligatorio.");
        g.setEstado("ACTIVO");
        return dao.insertar(g);
    }
    public List<Grupo> obtenerTodos() { return dao.listarTodos(); }
    public Grupo buscarPorId(int id)  { return dao.buscarPorId(id); }
    public boolean actualizar(Grupo g){ return dao.actualizar(g); }
    public boolean desactivar(int id) { return dao.eliminar(id); }
}

// ── AsignacionDocenteLogica ────────────────────────────────────────────────────
class AsignacionDocenteLogica {
    private AsignacionDocenteDAO dao;
    public AsignacionDocenteLogica() { this.dao = new AsignacionDocenteDAO(); }

    public boolean asignar(AsignacionDocente a) {
        if (a.getPractica() == null)                throw new IllegalArgumentException("Práctica obligatoria.");
        if (a.getUsuarios() == null || a.getUsuarios().isEmpty()) throw new IllegalArgumentException("Docente obligatorio.");
        return dao.insertar(a);
    }
    public List<AsignacionDocente> listarPorPractica(int idPractica) { return dao.listarPorPractica(idPractica); }
    public boolean eliminar(int id) { return dao.eliminar(id); }
}

// ── MatriculaPracticaLogica ────────────────────────────────────────────────────
class MatriculaPracticaLogica {
    private final MatriculaPracticaDAO dao;
    public MatriculaPracticaLogica() { this.dao = new MatriculaPracticaDAO(); }

    public boolean matricular(MatriculaPractica m) {
        if (m.getEstudiante() == null) throw new IllegalArgumentException("Estudiante obligatorio.");
        if (m.getPractica() == null)   throw new IllegalArgumentException("Práctica obligatoria.");
        m.setEstado("ACTIVO");
        return dao.insertar(m);
    }
    public List<MatriculaPractica> listarPorEstudiante(int id) { return dao.listarPorEstudiante(id); }
    public boolean actualizarEstado(int id, String estado)     { return dao.actualizarEstado(id, estado); }
}

// ── EvaluacionLogica ───────────────────────────────────────────────────────────
class EvaluacionLogica {
    private final EvaluacionDAO dao;
    public EvaluacionLogica() { this.dao = new EvaluacionDAO(); }

    public boolean registrarEvaluacion(Evaluacion e) {
        if (e.getMatricula() == null)  throw new IllegalArgumentException("Matrícula obligatoria.");
        if (e.getRubrica() == null)    throw new IllegalArgumentException("Rúbrica obligatoria.");
        if (e.getEvaluador() == null)  throw new IllegalArgumentException("Evaluador obligatorio.");
        if (e.getPuntajeObtenido() < 0) throw new IllegalArgumentException("El puntaje no puede ser negativo.");
        e.setEstado("REALIZADA");
        return dao.insertar(e);
    }
    public List<Evaluacion> listarPorMatricula(int id) { return dao.listarPorMatricula(id); }
    public boolean actualizar(Evaluacion e)            { return dao.actualizar(e); }
}

// ── RegistroActividadLogica ────────────────────────────────────────────────────
class RegistroActividadLogica {
    private final RegistroActividadDAO dao;
    public RegistroActividadLogica() { this.dao = new RegistroActividadDAO(); }

    public boolean registrar(RegistroActividad r) {
        if (Utilidades.esVacio(r.getDescripcion())) throw new IllegalArgumentException("La descripción es obligatoria.");
        if (r.getHoras() <= 0)                      throw new IllegalArgumentException("Las horas deben ser mayores a 0.");
        if (r.getMatricula() == null)               throw new IllegalArgumentException("La matrícula es obligatoria.");
        r.setEstado("PENDIENTE");
        return dao.insertar(r);
    }
    public List<RegistroActividad> listarPorMatricula(int id)         { return dao.listarPorMatricula(id); }
    public boolean aprobar(int idRegistro)                            { return dao.actualizarEstado(idRegistro, "APROBADO"); }
    public boolean rechazar(int idRegistro)                           { return dao.actualizarEstado(idRegistro, "RECHAZADO"); }
}