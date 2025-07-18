package ec.edu.ec.poo.excepciones;

/**
 * Excepción personalizada que se lanza cuando una contraseña no cumple
 * con los requisitos de seguridad definidos.
 * <p>
 * Esta excepción es utilizada para validar contraseñas seguras según reglas
 * preestablecidas como longitud mínima, presencia de mayúsculas, minúsculas,
 * caracteres especiales, etc.
 * </p>
 */
public class ContraseniaExcepcion extends Exception {

    /**
     * Constructor que inicializa la excepción con un mensaje descriptivo.
     * @param message mensaje que detalla el motivo por el cual la contraseña es inválida.
     */
    public ContraseniaExcepcion(String message) {
        super(message);
    }
}
