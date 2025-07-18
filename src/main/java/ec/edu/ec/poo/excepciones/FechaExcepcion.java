package ec.edu.ec.poo.excepciones;

public class FechaExcepcion extends Exception {
    public FechaExcepcion() {
        super("Formato de fecha incorrecto (dene ser dd/MM/yyyy)");
    }
}
