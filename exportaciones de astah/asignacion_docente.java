import Java.lang.date;
import Java.lang.String;
import java.util.Collection;

/**
 * Tabla que registra la asignación de docentes a cursos o grupos, indicando qué profesor está encargado de una materia o práctica específica.
 */
public class asignacion_docente {

	private int id_asignacion_docente;

	private date fecha_asignacion;

	private String rol_en_practica;

	private practica practica;

	private Collection<usuarios> usuarios;

	public int setid_asignacion_docente(int pid_asignacion_docente) {
		return 0;
	}

	public date setfecha_asignacion(date pfecha_asignacion) {
		return null;
	}

	public String setrol_en_practica(String prol_en_practica) {
		return null;
	}

	public int getid_asignacion_docente() {
		return 0;
	}

	public date getfecha_asignacion() {
		return null;
	}

	public String getrol_en_practica() {
		return null;
	}

}
