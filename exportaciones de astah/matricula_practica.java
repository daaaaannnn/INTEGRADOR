import Java.lang.date;
import Java.lang.String;
import java.util.Collection;

/**
 * Tabla que registra la inscripción de estudiantes en prácticas o actividades evaluativas.
 */
public class matricula_practica {

	private int id_matricula_practica;

	private date fecha_matricula;

	private String estado;

	private int horas_cumplidas;

	private Collection<registro_actividad> registro_actividad;

	private Collection<grupo> grupo;

	private practica practica;

	private practica practica;

	private Collection<evaluacion> evaluacion;

	/**
	 *  
	 */
	public matricula_practica() {

	}

	/**
	 *  
	 */
	public int setid_matricula_practica(int pid_matricula_practica) {
		return 0;
	}

	/**
	 *  
	 */
	public date setfecha_matricula(date pfecha_matricula) {
		return null;
	}

	/**
	 *  
	 */
	public void setestado(String pestado) {

	}

	/**
	 *  
	 */
	public void sethoras_cumplidas(int phoras_cumplidas) {

	}

	public int getid_matricula_practica() {
		return 0;
	}

	public date getfecha_matricula() {
		return null;
	}

	public String getestado() {
		return null;
	}

	public int gethoras_cumplidas() {
		return 0;
	}

}
