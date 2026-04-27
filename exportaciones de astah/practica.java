import Java.lang.String;
import java.util.Collection;

/**
 * Almacena las actividades prácticas o trabajos asignados a los estudiantes dentro de un curso.
 */
public class practica {

	private int id_practica;

	private String nombre;

	private String descripcion;

	private String objetivos;

	private int horas_reglamentarias;

	private String tipo_practica;

	private programa programa;

	private Collection<curso> curso;

	private institucion institucion;

	private Collection<institucion> institucion;

	private Collection<grupo> grupo;

	private matricula_practica matricula_practica;

	private Collection<matricula_practica> matricula_practica;

	private Collection<pregunta> pregunta;

	private Collection<asignacion_docente> asignacion_docente;

	private Collection<usuarios> usuarios;

	/**
	 *  
	 */
	public practica() {

	}

	/**
	 *  
	 */
	public setid_practica(int pid_practica) {

	}

	/**
	 *  
	 */
	public setnombre(String pnombre) {

	}

	/**
	 *  
	 */
	public setdescripcion(String pdescripcion) {

	}

	/**
	 *  
	 */
	public setobjetivos(String pobjetivos) {

	}

	/**
	 *  
	 */
	public sethoras_reglamentarias(int phoras_reglamentarias) {

	}

	/**
	 *  
	 */
	public settipo_practica(String ptipo_practica) {

	}

	public int getid_practica() {
		return 0;
	}

	public String getnombre() {
		return null;
	}

	public String getdescripcion() {
		return null;
	}

	public String getobjetivos() {
		return null;
	}

	public int gethoras_reglamentarias() {
		return 0;
	}

	public String gettipo_practica() {
		return null;
	}

}
