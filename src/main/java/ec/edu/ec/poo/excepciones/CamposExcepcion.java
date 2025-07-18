package ec.edu.ec.poo.excepciones;

/**
 * Excepción personalizada que se lanza cuando un campo obligatorio no ha sido llenado.
 * <p>
 * Esta excepción permite especificar qué campo es obligatorio y no ha sido proporcionado,
 * mostrando un mensaje personalizado de error.
 * </p>
 */
public class CamposExcepcion extends Exception {

    /**
     * Constructor que genera un mensaje indicando que el campo es obligatorio.
     * @param campo nombre del campo que no fue llenado correctamente.
     */
    public CamposExcepcion(String campo) {
        super("El campo " + campo + " es obligatorio");
    }
}
