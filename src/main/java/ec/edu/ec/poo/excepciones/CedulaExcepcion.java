package ec.edu.ec.poo.excepciones;

/**
 * Excepción personalizada que se lanza cuando una cédula es inválida.
 * <p>
 * Permite mostrar un mensaje personalizado indicando el motivo de la invalidación
 * de la cédula ingresada.
 * </p>
 */
public class CedulaExcepcion extends Exception {

    /**
     * Constructor que inicializa la excepción con un mensaje personalizado.
     * @param message mensaje descriptivo del error relacionado con la cédula.
     */
    public CedulaExcepcion(String message) {
        super(message);
    }
}
