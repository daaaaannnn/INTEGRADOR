import Java.lang.String;
import java.util.Collection;

/**
 * Contiene los criterios de evaluación que forman parte de una rúbrica, como por ejemplo: claridad, presentación, contenido, análisis, etc.
 */
public class criterio_rubrica {

	private int id_criterio;

	private String nombre;

	private String descripcion;

	private float ponderacion;

	private Collection<nivel_desempeno> nivel_desempeno;

	private rubrica rubrica;

	public int setid_criterio(int pid_criterio) {
		return 0;
	}

	public String setnombre(String pnombre) {
		return null;
	}

	public String setdescripcion(String pdescripcion) {
		return null;
	}

	public float setponderacion(float pponderacion) {
		return 0;
	}

	public int getid_criterio() {
		return 0;
	}

	public String getnombre() {
		return null;
	}

	public String getdescripcion() {
		return null;
	}

	public float getponderacion() {
		return 0;
	}

}
