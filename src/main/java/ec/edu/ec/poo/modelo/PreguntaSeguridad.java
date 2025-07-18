package ec.edu.ec.poo.modelo;


/**
 * Clase PreguntaSeguridad representa una pregunta de seguridad que puede ser utilizada
 * para la recuperación de contraseñas. Cada pregunta tiene un identificador único y un texto.

 */
public class PreguntaSeguridad {
    /** Identificador único de la pregunta */
    private int id;


    /** Texto o contenido de la pregunta */
    private String texto;

    /**
     * Constructor que inicializa la pregunta con su identificador y texto.
     * @param id identificador único de la pregunta
     * @param texto contenido textual de la pregunta
     */
    public PreguntaSeguridad(int id, String texto) {
        this.id = id;
        this.texto = texto;
    }

    /**
     * Retorna el identificador de la pregunta.
     * @return identificador numérico de la pregunta
     */
    public int getId() {
        return id;
    }

    /**
     * Obtiene el texto de la pregunta.
     * @return contenido textual de la pregunta
     */
    public String getTexto() {
        return texto;
    }

    /**
     * Sobrescribe el método toString para retornar únicamente el texto de la pregunta,
     * útil para mostrarlo directamente en componentes gráficos como JComboBox.
     * @return texto de la pregunta
     */
    @Override
    public String toString() {
        return texto; // Para mostrar en el JComboBox
    }

    /**
     * Permite actualizar el texto de la pregunta, útil para internacionalización.
     * @param nuevaPregunta texto actualizado de la pregunta
     */
    public void setTexto(String nuevaPregunta) {
        this.texto = nuevaPregunta;
    }
}

