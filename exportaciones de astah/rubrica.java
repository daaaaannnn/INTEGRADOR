import Java.lang.String;
import java.util.Collection;

/**
 * Contiene las rúbricas de evaluación, que son las herramientas usadas para calificar trabajos según diferentes criterios.
 */
public class rubrica {

	private int id_rubrica;

	private String nombre;

	private String descripcion;

	private float ponderacion_total;

	private Collection<criterio_rubrica> criterio_rubrica;

	private evaluacion evaluacion;

	private evaluacion evaluacion;

	public int setid_rubrica(int pid_rubrica) {
		return 0;
	}

	public String setnombre(String pnombre) {
		return null;
	}

	public String setdescripcion(String pdescripcion) {
		return null;
	}

	public float setponderacion(float pponderacion_total) {
		return 0;
	}

	public int getid_rubrica() {
		return 0;
	}

	public String getnombre() {
		return null;
	}

	public String getdescripcion() {
		return null;
	}

	public float getponderacion_total() {
		return 0;
	}

}
