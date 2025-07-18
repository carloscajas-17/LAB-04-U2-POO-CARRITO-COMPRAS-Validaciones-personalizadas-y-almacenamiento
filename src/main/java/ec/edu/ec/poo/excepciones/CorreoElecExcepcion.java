package ec.edu.ec.poo.excepciones;

/**
 * Excepción personalizada que se lanza cuando un correo electrónico no cumple
 * con el formato válido establecido (ejemplo: nombre@dominio.com).
 * <p>
 * Esta excepción extiende {@link RuntimeException} porque se considera una
 * excepción no verificada durante la validación de campos de entrada.
 * </p>
 */
public class CorreoElecExcepcion extends RuntimeException {

    /**
     * Constructor que inicializa la excepción con un mensaje por defecto
     * indicando que el correo no tiene un formato válido.
     */
    public CorreoElecExcepcion() {
        super("El correo electrónico no tiene formato válido");
    }
}
