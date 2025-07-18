package ec.edu.ec.poo.excepciones;

/**
 * Excepción personalizada que se lanza cuando la fecha proporcionada no cumple
 * con el formato requerido.
 * <p>
 * El formato válido aceptado es <b>dd/MM/yyyy</b>.
 * Esta excepción permite controlar errores de validación de fechas
 * durante el registro o modificación de datos.
 * </p>
 */
public class FechaExcepcion extends Exception {

    /**
     * Constructor que inicializa la excepción con un mensaje por defecto
     * indicando que el formato de fecha es incorrecto.
     */
    public FechaExcepcion() {
        super("Formato de fecha incorrecto (debe ser dd/MM/yyyy)");
    }
}
