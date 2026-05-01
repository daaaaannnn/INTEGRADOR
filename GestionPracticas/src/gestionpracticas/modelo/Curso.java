package gestionpracticas.modelo;

public class Curso {
    private int idCurso;
    private String nombre;
    private String codigo;
    private int creditos;
    private String programa;
    private String descripcion;
    private String estado;

    public Curso() {}

    public Curso(int idCurso, String nombre, String codigo, int creditos,
                 String programa, String descripcion, String estado) {
        this.idCurso = idCurso;
        this.nombre = nombre;
        this.codigo = codigo;
        this.creditos = creditos;
        this.programa = programa;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    public int getIdCurso() { return idCurso; }
    public void setIdCurso(int idCurso) { this.idCurso = idCurso; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public int getCreditos() { return creditos; }
    public void setCreditos(int creditos) { this.creditos = creditos; }

    public String getPrograma() { return programa; }
    public void setPrograma(String programa) { this.programa = programa; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    @Override
    public String toString() {
        return "Curso{id=" + idCurso + ", nombre=" + nombre + ", codigo=" + codigo + "}";
    }
}
