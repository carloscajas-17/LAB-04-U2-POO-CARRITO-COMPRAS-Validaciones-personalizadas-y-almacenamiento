package ec.edu.ec.poo.utils;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.MissingResourceException;

/**
 * Clase utilitaria para la gestión de textos internacionalizados.
 * Proporciona acceso dinámico a los mensajes definidos en los archivos `mensajes.properties`
 * según el idioma y país seleccionados.
 */
public class MensajeInternacionalizacionHandler {

    /** Conjunto de recursos que contiene las claves y sus respectivos textos traducidos */
    private ResourceBundle bundle;

    /** Locale actual configurado (idioma y país) */
    private Locale locale;

    /**
     * Constructor que inicializa el handler con un idioma y país específico.
     *
     * @param lenguaje código del idioma (ejemplo: "es" para español, "en" para inglés)
     * @param pais código del país (ejemplo: "EC" para Ecuador, "US" para Estados Unidos)
     */
    public MensajeInternacionalizacionHandler(String lenguaje, String pais) {
        setLenguaje(lenguaje, pais);
    }

    /**
     * Obtiene el texto traducido correspondiente a una clave específica.
     * Si la clave no existe en el archivo de propiedades, devuelve un mensaje de error
     * indicando la clave no encontrada.
     *
     * @param key clave o identificador del mensaje buscado
     * @return texto traducido según el idioma actual o un mensaje de advertencia si no se encuentra la clave
     */
    public String get(String key) {
        try {
            return bundle.getString(key);
        } catch (MissingResourceException e) {
            return "¡Clave no encontrada!: " + key;
        }
    }

    /**
     * Cambia el idioma y país del sistema, recargando el archivo de propiedades correspondiente.
     * Este método permite cambiar dinámicamente el idioma en tiempo de ejecución.
     *
     * @param lenguaje código de idioma, por ejemplo: "es" (español), "en" (inglés), "fr" (francés)
     * @param pais código de país, por ejemplo: "EC", "US", "FR"
     */
    public void setLenguaje(String lenguaje, String pais) {
        this.locale = new Locale(lenguaje, pais);
        this.bundle = ResourceBundle.getBundle("mensajes", locale);
    }

    /**
     * Devuelve el locale (idioma y país) actualmente configurado en la aplicación.
     *
     * @return objeto {@link Locale} que representa la configuración regional actual
     */
    public Locale getLocale() {
        return locale;
    }
}
