import Java.lang.String;
import java.util.Collection;

/**
 * Registra los programas académicos de la institución (por ejemplo: Ingeniería de Sistemas, Administración, etc.).
 */
public class programa {

	private int id_programa;

	private String nombre;

	private String codigo;

	private int facultad;

	private int estado;

	private String resolucion_men;

	private Collection<curso> curso;

	private practica practica;

	private Collection<usuarios> usuarios;

	/**
	 *  
	 *  
	 *  
	 */
	public programa() {

	}

	/**
	 *  
	 */
	public setid_programa:(int pid_programa) {

	}

	/**
	 *  
	 */
	public setnombre(String pnombre) {

	}

	/**
	 *  
	 */
	public setcodigo(String pcodigo) {

	}

	/**
	 *  
	 */
	public setfacultad(int pfacultad) {

	}

	/**
	 *  
	 */
	public setestado(int pestado) {

	}

	/**
	 *  
	 */
	public setresolucion_men(String presolucion_men) {

	}

	public int getid_programa() {
		return 0;
	}

	public String getnombre() {
		return null;
	}

	public String getcodigo() {
		return null;
	}

	public int getfacultad() {
		return 0;
	}

	public int getestado() {
		return 0;
	}

	public String getresolucion_men() {
		return null;
	}

}
