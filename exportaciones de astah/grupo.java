import Java.lang.String;
import Java.lang.date;
import java.util.Collection;

/**
 * Registra los grupos de estudiantes dentro de un curso o programa académico (por ejemplo: Grupo A, Grupo B).
 */
public class grupo {

	private int id_grupo;

	private String nombre_grupo;

	private String periodo_academico;

	private date fecha_inicio;

	private date fecha_fin;

	private practica practica;

	private matricula_practica matricula_practica;

	private Collection<usuarios> usuarios;

	/**
	 *  
	 */
	public void setid_grupo(int pid_grupo) {

	}

	/**
	 *  
	 */
	public void setnombre_grupo(String pnombre_grupo) {

	}

	/**
	 *  
	 */
	public void setperiodo_academico(String pperiodo_academico) {

	}

	/**
	 *  
	 */
	public void setfecha_inicio(date pfecha_inicio) {

	}

	/**
	 *  
	 */
	public void setfecha_fin(date pfecha_fin) {

	}

	/**
	 *  
	 */
	public int getid_grupo() {
		return 0;
	}

	/**
	 *  
	 */
	public String getnombre_grupo() {
		return null;
	}

	public String getperiodoacademico() {
		return null;
	}

	public date getfecha_inicio() {
		return null;
	}

	public date getfecha_fin() {
		return null;
	}

}
