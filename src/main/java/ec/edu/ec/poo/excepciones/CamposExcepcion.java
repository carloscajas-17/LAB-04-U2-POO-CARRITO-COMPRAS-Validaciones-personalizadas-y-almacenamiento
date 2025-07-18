package ec.edu.ec.poo.excepciones;

public class CamposExcepcion extends Exception {
    public CamposExcepcion(String campo) {
        super("El campo " + campo + " es obligatorio");
    }
}
