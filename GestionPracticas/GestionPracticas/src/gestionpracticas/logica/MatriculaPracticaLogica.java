/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package gestionpracticas.logica;

import gestionpracticas.dao.MatriculaPracticaDAO;
import gestionpracticas.modelo.MatriculaPractica;
import java.util.List;

public class MatriculaPracticaLogica {

    private final MatriculaPracticaDAO dao;

    public MatriculaPracticaLogica() {
        this.dao = new MatriculaPracticaDAO();
    }

    public boolean matricular(MatriculaPractica m) {
        if (m.getEstudiante() == null)
            throw new IllegalArgumentException("El estudiante es obligatorio.");
        if (m.getPractica() == null)
            throw new IllegalArgumentException("La práctica es obligatoria.");
        m.setEstado("ACTIVO");
        return dao.insertar(m);
    }

    public List<MatriculaPractica> listarPorEstudiante(int idEstudiante) {
        return dao.listarPorEstudiante(idEstudiante);
    }

    public boolean aprobar(int idMatricula) {
        return dao.actualizarEstado(idMatricula, "APROBADO");
    }

    public boolean rechazar(int idMatricula) {
        return dao.actualizarEstado(idMatricula, "RECHAZADO");
    }

    public boolean desactivar(int idMatricula) {
        return dao.actualizarEstado(idMatricula, "INACTIVO");
    }
}
