package ec.edu.ec.poo.excepciones;

public class CorreoElecExcepcion extends RuntimeException {
    public CorreoElecExcepcion() {
        super("El correo electrónico no tiene formato válido");
    }
}
