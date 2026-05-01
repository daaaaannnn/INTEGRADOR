package gestionpracticas.modelo;

public class Pregunta {
    private int idPregunta;
    private String textoPregunta;
    private String tipoPregunta; // ABIERTA, OPCION_MULTIPLE, ESCALA
    private Evaluacion evaluacion;
    private Practica practica;

    public Pregunta() {}

    public Pregunta(int idPregunta, String textoPregunta, String tipoPregunta,
                    Evaluacion evaluacion, Practica practica) {
        this.idPregunta = idPregunta;
        this.textoPregunta = textoPregunta;
        this.tipoPregunta = tipoPregunta;
        this.evaluacion = evaluacion;
        this.practica = practica;
    }

    public int getIdPregunta() { return idPregunta; }
    public void setIdPregunta(int idPregunta) { this.idPregunta = idPregunta; }

    public String getTextoPregunta() { return textoPregunta; }
    public void setTextoPregunta(String textoPregunta) { this.textoPregunta = textoPregunta; }

    public String getTipoPregunta() { return tipoPregunta; }
    public void setTipoPregunta(String tipoPregunta) { this.tipoPregunta = tipoPregunta; }

    public Evaluacion getEvaluacion() { return evaluacion; }
    public void setEvaluacion(Evaluacion evaluacion) { this.evaluacion = evaluacion; }

    public Practica getPractica() { return practica; }
    public void setPractica(Practica practica) { this.practica = practica; }

    @Override
    public String toString() {
        return "Pregunta{id=" + idPregunta + ", texto=" + textoPregunta + "}";
    }
}
