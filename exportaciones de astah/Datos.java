import persistencia.RegistroCsv;
import persistencia.ArchivoCsv;

/**
 * Clase especializada en la persistencia de los datos del problema.
 * @version 1.20240314
 * @author Ricardo Vicente Jaime Vivas - Programa de Ingeniería de Sistemas - Universidad de Investigación y Desarrollo UDI - Grupo de Investigación en Ingeniería de Sistemas GIDSAW
 */
public class Datos extends ArchivoCsv {

	/**
	 * Nombre, sin extensión, del archivo para almacenar los registros de objetos de la clase Municipio.
	 */
	public int archivousuarios;

	/**
	 * Nombre, sin extensión, del archivo para almacenar los registros de objetos de la clase CertificadoPoblacion.
	 */
	public int archivoprograma;

	/**
	 * Nombre, sin extensión, del archivo para almacenar los registros de objetos de la clase Departamento.
	 */
	public int archivopractica;

	public int archivoinstitucion;

	public int archivocurso;

	public int archivoasignacion_docente;

	public int archivomatricula_practica;

	public int archivogrupo;

	public int archivoevaluacion;

	public int archivopregunta;

	public int archivoregistro_actividad;

	public int archivorubrica;

	public int archivorespuesta;

	public int archivonivel_desempeno;

	public int archivocriterio_rubrica;

	/**
	 * Método constructor de la clase. Inicializa el atributo archivoMunicipios con un nombre apropiado.
	 * 
	 *  
	 */
	public Datos() {

	}

	/**
	 * Método que graba en el archivo correspondiente un registro con valores de los atributos del objeto de la clase Municipio dado como parámetro.
	 * @pMunicipio : Parámetro con el objeto de la clase Municipio a ser grabado.
	 * 
	 *  
	 */
	public void grabarusuarios(usuarios pusuarios) {

	}

	/**
	 *  
	 */
	public void grabarprograma(programa pprograma) {

	}

	/**
	 *  
	 */
	public void grabarpractica(practica ppractica) {

	}

	/**
	 *  
	 */
	public void grabarinstitucion(institucion pinstitucion) {

	}

	/**
	 *  
	 */
	public void grabarcurso(curso pcurso) {

	}

	/**
	 *  
	 */
	public void grabarasignacion_docente(asignacion_docente pasignacion_docente) {

	}

	/**
	 *  
	 */
	public void grabarmatricula_practica(matricula_practica pmatricula_practica) {

	}

	/**
	 *  
	 */
	public void grabargrupo(grupo pgrupo) {

	}

	/**
	 *  
	 */
	public void grabarevaluacion(evaluacion pevaluacion) {

	}

	/**
	 *  
	 */
	public void grabarpregunta(pregunta ppregunta) {

	}

	/**
	 *  
	 */
	public void grabarregistro_actividad(registro_actividad pregistro_actividad) {

	}

	/**
	 *  
	 */
	public void grabarrubrica(rubrica prubrica) {

	}

	/**
	 *  
	 */
	public void grabarrespuesta(int prespuesta) {

	}

	/**
	 *  
	 */
	public void grabarnivel_desempeno(nivel_desempeno pnivel_desempeno) {

	}

	/**
	 *  
	 */
	public void grabarcriterio_rubrica(criterio_rubrica pcriterio_rubrica) {

	}

	/**
	 * Método que reemplaza en el archivo correspondiente un registro con valores de los atributos del objeto de la clase Municipio dado como parámetro.
	 * @pMunicipio : Parámetro con el objeto de la clase Municipio a ser grabado.
	 * 
	 *  
	 */
	public void actualizarusuarios(usuarios pusuarios) {

	}

	/**
	 *  
	 */
	public void actualizarprograma(programa pprograma) {

	}

	/**
	 *  
	 */
	public void actualizarpractica(practica ppractica) {

	}

	/**
	 *  
	 */
	public void actualizarinstitucion(institucion pinstitucion) {

	}

	/**
	 *  
	 */
	public void actualizarcurso(curso pcurso) {

	}

	/**
	 *  
	 */
	public void actualizarasignacion_docente(asignacion_docente pasignacion_docente) {

	}

	/**
	 *  
	 */
	public void actualizarmatricula_practica(matricula_practica pmatricula_practica) {

	}

	/**
	 *  
	 */
	public void actualizargrupo(grupo pgrupo) {

	}

	/**
	 *  
	 */
	public void actualizarevaluacion(evaluacion pevaluacion) {

	}

	/**
	 *  
	 */
	public void actualizarpregunta(pregunta ppregunta) {

	}

	/**
	 *  
	 */
	public void actualizarregistro_actividad(registro_actividad pregistro_actividad) {

	}

	/**
	 *  
	 */
	public void actualizarrubrica(rubrica prubrica) {

	}

	/**
	 *  
	 */
	public void actualizarrespuesta(int prespuesta) {

	}

	/**
	 *  
	 */
	public void actualizarnivel_desempeno(nivel_desempeno pnivel_desempeno) {

	}

	/**
	 *  
	 */
	public void actualizarcriterio_rubrica(criterio_rubrica pcriterio_rubrica) {

	}

	/**
	 * Método que construye un nuevo objeto de la clase Municipio con los valores de los atributos de su registro almacenado en los archivos correspondientes, de donde los recupera utilizando un código DANE como parámetro de búsqueda.
	 * @pId : Id dado como parámetro de búsqueda del certificado.
	 */
	public usuarios recuperarusuarios(int pId_usuarios) {
		return null;
	}

	/**
	 *  
	 */
	public programa recuperarprograma(int pid_programa) {
		return null;
	}

	public practica recuperarpractica(int id_practica) {
		return null;
	}

	public institucion recuperarinstitucion(int id_institucion) {
		return null;
	}

	public curso recuperarcurso(int id_curso) {
		return null;
	}

	public asignacion_docente recuperarasignacion_docente(int id_asignacion_docente) {
		return null;
	}

	public matricula_practica recuperarmatricula_practica(int id_matricula_practica) {
		return null;
	}

	public grupo recuperargrupo(int id_grupo) {
		return null;
	}

	public evaluacion recuperarevaluacion(int id_evaluacion) {
		return null;
	}

	public pregunta recuperarpregunta(int id_pregunta) {
		return null;
	}

	public registro_actividad recuperarregistro_actividad(int id_actividad) {
		return null;
	}

	public rubrica recuperarrubrica(int id_rubrica) {
		return null;
	}

	public int recuperarrespuesta(int id_respuesta) {
		return 0;
	}

	/**
	 *  
	 */
	public nivel_desempeno recuperarnivel_desempeno(int id_nivel) {
		return null;
	}

	public criterio_rubrica recuperarcriterio_rubrica(int id_criterio) {
		return null;
	}

}
