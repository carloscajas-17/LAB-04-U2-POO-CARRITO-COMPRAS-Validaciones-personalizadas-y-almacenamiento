package ec.edu.ec.poo.utils;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.MissingResourceException;

public class MensajeInternacionalizacionHandler {

    private ResourceBundle bundle;
    private Locale locale;

    public MensajeInternacionalizacionHandler(String lenguaje, String pais) {
        setLenguaje(lenguaje, pais);
    }

    public String get(String key) {
        try {
            return bundle.getString(key);
        } catch (MissingResourceException e) {
            return "¡Clave no encontrada!: " + key;
        }
    }

    public void setLenguaje(String lenguaje, String pais) {
        this.locale = new Locale(lenguaje, pais);
        this.bundle = ResourceBundle.getBundle("mensajes", locale);
    }

    public Locale getLocale() {
        return locale;
    }
}
