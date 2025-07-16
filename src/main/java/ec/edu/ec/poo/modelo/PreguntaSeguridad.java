package ec.edu.ec.poo.modelo;


public class PreguntaSeguridad {
    private int id;
    private String texto;

    public PreguntaSeguridad(int id, String texto) {
        this.id = id;
        this.texto = texto;
    }

    public int getId() {
        return id;
    }

    public String getTexto() {
        return texto;
    }

    @Override
    public String toString() {
        return texto; // Para mostrar en el JComboBox
    }

    public void setTexto(String nuevaPregunta) {
        this.texto = nuevaPregunta;
    }
}

