import Java.lang.date;
import Java.lang.String;
import java.util.Collection;

/**
 * Guarda los datos de las evaluaciones realizadas, como exámenes, prácticas o actividades calificadas dentro de un curso.
 */
public class evaluacion {

	private int id_evaluacion;

	private date fecha_evaluacion;

	private float calificacion_cuantitativa;

	private String observacion_general;

	private matricula_practica matricula_practica;

	private rubrica rubrica;

	private rubrica rubrica;

	private Collection<pregunta> pregunta;

	private Collection<usuarios> usuarios;

	/**
	 *  
	 */
	public evalucion() {

	}

	public int setid_evaluacion(int pid_evaluacion) {
		return 0;
	}

	public date setfecha_evaluacion(date pfecha_evaluacion) {
		return null;
	}

	public float setcalificacion_cuantitativa(float pcalificacion_cuantitativa) {
		return 0;
	}

	public String setobservacion_general(String pobservacion_general) {
		return null;
	}

	public int getid_evaluacion() {
		return 0;
	}

	public date getfecha_evaluacion() {
		return null;
	}

	public float getcalificacion_cuantitativa() {
		return 0;
	}

	public String getobservacion_general() {
		return null;
	}

}
