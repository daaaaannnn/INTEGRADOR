import Java.lang.String;
import java.util.Collection;

/**
 * Tabla que almacena la información de los usuarios del sistema, como estudiantes, docentes o administradores.
 */
public class usuarios {

	private int id_usuario;

	private String tipo_documento;

	private String numero_documento;

	private String nombres;

	private String apellidos;

	private String email;

	private String telefono;

	private String rol;

	private programa programa;

	private practica practica;

	private Collection<asignacion_docente> asignacion_docente;

	private Collection<institucion> institucion;

	private Collection<grupo> grupo;

	private evaluacion evaluacion;

	public int setid_usuario(int pid_usuario) {
		return 0;
	}

	public String settipo_documento(String ptipo_documento) {
		return null;
	}

	public String setnumero_documento(String pnumero_documento) {
		return null;
	}

	public String setnombres(String nombres) {
		return null;
	}

	public String setapellidos(String papellidos) {
		return null;
	}

	public String setemail(String pemail) {
		return null;
	}

	public String settelefono(String ptelefono) {
		return null;
	}

	public String setrol(String prol) {
		return null;
	}

	public int getid_usuario() {
		return 0;
	}

	public String gettipo_documento() {
		return null;
	}

	public String getnumero_documento() {
		return null;
	}

	public String getnombres() {
		return null;
	}

	public String getapellidos() {
		return null;
	}

	public String gettelefono():String() {
		return null;
	}

	public String getrol() {
		return null;
	}

}
