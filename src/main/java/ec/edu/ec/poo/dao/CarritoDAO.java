package ec.edu.ec.poo.dao;





import ec.edu.ec.poo.modelo.Carrito;
import java.util.List;

public interface CarritoDAO {


    void crear(Carrito carrito);
    Carrito buscarPorCodigo(int codigo);
    List<Carrito> buscarPorUsuario(String username);
    void actualizar(Carrito carrito);
    void eliminar(int codigo);


    List<Carrito> listarTodos(); // ← si también lo usas en otro lado
    List<Carrito> listarPorUsuario(String idUsuario);







}

