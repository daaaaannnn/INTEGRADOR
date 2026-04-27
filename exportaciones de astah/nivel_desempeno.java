import Java.lang.String;
import java.util.Collection;

/**
 * Define los niveles de desempeño en una rúbrica, por ejemplo: Excelente, Bueno, Aceptable, Insuficiente, junto con su puntaje o descripción.
 */
public class nivel_desempeno {

	private int id_nivel;

	private String nombre;

	private float valor_minimo;

	private float valor_maximo;

	private String descripcion;

	private Collection<criterio_rubrica> criterio_rubrica;

	public int setid_nivel(int pid_nivel) {
		return 0;
	}

	public String setnombre(String pnombre) {
		return null;
	}

	public float setvalor_minimo(float pvalor_minimo) {
		return 0;
	}

	public float setvalor_maximo(float pvalor_maximo) {
		return 0;
	}

	public String setdescripcion(String pdescripcion) {
		return null;
	}

	public int getid_nivel() {
		return 0;
	}

	public String getnombre() {
		return null;
	}

	public float getvalor_minimo() {
		return 0;
	}

	public float getvalor_maximo() {
		return 0;
	}

	public String getdescripcion() {
		return null;
	}

}
