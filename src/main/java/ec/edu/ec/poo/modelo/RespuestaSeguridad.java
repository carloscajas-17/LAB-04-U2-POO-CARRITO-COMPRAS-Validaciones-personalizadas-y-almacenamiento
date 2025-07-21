
package ec.edu.ec.poo.modelo;

/**
 * Clase RespuestaSeguridad representa una respuesta proporcionada por un usuario
 * para una pregunta de seguridad. Es utilizada durante procesos como recuperación de contraseña.
 */
public class RespuestaSeguridad {

    /** Pregunta de seguridad asociada */
    private PreguntaSeguridad pregunta;

    /** Respuesta proporcionada por el usuario a la pregunta de seguridad */
    private String respuesta;

    /** Usuario al que pertenece la respuesta de seguridad */
    private Usuario usuario;

    /**
     * Constructor vacío para inicialización simple.
     */
    public RespuestaSeguridad() {
    }
    /**
     * Constructor que inicializa la respuesta de seguridad con la pregunta asociada,
     * la respuesta del usuario y el usuario correspondiente.
     *
     * @param pregunta la pregunta de seguridad asociada
     * @param respuesta la respuesta proporcionada por el usuario
     * @param usuario el usuario propietario de la respuesta de seguridad
     */
    public RespuestaSeguridad(PreguntaSeguridad pregunta, String respuesta, Usuario usuario) {
        this.pregunta = pregunta;
        this.respuesta = respuesta;
        this.usuario = usuario;
    }

    /**
     * Obtiene la pregunta de seguridad asociada a esta respuesta.
     *
     * @return pregunta de seguridad
     */
    public PreguntaSeguridad getPregunta() {
        return pregunta;
    }

    /**
     * Establece o actualiza la pregunta de seguridad asociada.
     *
     * @param pregunta nueva pregunta de seguridad
     */
    public void setPregunta(PreguntaSeguridad pregunta) {
        this.pregunta = pregunta;
    }

    /**
     * Obtiene la respuesta proporcionada por el usuario.
     *
     * @return respuesta de seguridad
     */
    public String getRespuesta() {
        return respuesta;
    }

    /**
     * Establece o actualiza la respuesta proporcionada por el usuario.
     *
     * @param respuesta nueva respuesta de seguridad
     */
    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    /**
     * Obtiene el usuario asociado a esta respuesta de seguridad.
     *
     * @return usuario dueño de la respuesta
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Asigna o actualiza el usuario dueño de la respuesta de seguridad.
     *
     * @param usuario usuario al que pertenece esta respuesta
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
