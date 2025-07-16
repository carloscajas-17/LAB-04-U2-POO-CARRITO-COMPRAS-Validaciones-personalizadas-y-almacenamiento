package ec.edu.ec.poo.modelo;


public class RespuestaSeguridad {
    private PreguntaSeguridad pregunta;
    private String respuesta;
    private Usuario usuario;




    public RespuestaSeguridad(PreguntaSeguridad pregunta, String respuesta, Usuario usuario) {
        this.pregunta = pregunta;
        this.respuesta = respuesta;
        this.usuario = usuario;
    }

    public PreguntaSeguridad getPregunta() {
        return pregunta;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setPregunta(PreguntaSeguridad pregunta) {
        this.pregunta = pregunta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}

