package ec.edu.ec.poo.dao;
import ec.edu.ec.poo.modelo.Carrito;
import java.util.List;

/**
 * Interfaz DAO para la gestión de carritos.
 * Define las operaciones CRUD básicas y consultas relacionadas con carritos.
 *

 */
public interface CarritoDAO {


    /**
     * Crea y almacena un nuevo carrito.
     *
     * @param carrito el carrito que se desea registrar
     */
    void crear(Carrito carrito);


    /**
     * Busca un carrito en la base de datos mediante su código único.
     *
     * @param codigo el código identificador del carrito
     * @return el carrito encontrado o null si no existe
     */
    Carrito buscarPorCodigo(int codigo);


    /**
     * Busca todos los carritos asociados a un usuario mediante su ID o nombre de usuario.
     *
     * @param username el identificador del usuario
     * @return lista de carritos relacionados al usuario
     */
    List<Carrito> buscarPorUsuario(String username);


    /**
     * Actualiza la información de un carrito existente.
     *
     * @param carrito el carrito actualizado que reemplazará al anterior
     */
    boolean actualizar(Carrito carrito);



    /**
     * Elimina un carrito según su código.
     *
     * @param codigo el código del carrito que se desea eliminar
     */
    void eliminar(int codigo);


    /**
     * Lista todos los carritos almacenados en el sistema.
     *
     * @return lista completa de carritos
     */
    List<Carrito> listarTodos(); // ← si también lo usas en otro lado


    /**
     * Lista todos los carritos vinculados a un usuario específico.
     *
     * @param idUsuario el ID del usuario
     * @return lista de carritos correspondientes a ese usuario
     */
    List<Carrito> listarPorUsuario(String idUsuario);







}

