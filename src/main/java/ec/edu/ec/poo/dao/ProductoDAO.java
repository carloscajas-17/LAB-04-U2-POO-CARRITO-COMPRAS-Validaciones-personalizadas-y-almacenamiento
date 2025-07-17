package ec.edu.ec.poo.dao;
import ec.edu.ec.poo.modelo.Producto;

import java.util.List;


/**
 * Interfaz DAO para la gestión de productos.
 * Define las operaciones CRUD básicas sobre los objetos {@link Producto}.
 *
 */
public interface ProductoDAO {

    /**
     * Crea un nuevo producto y lo guarda en la base de datos o lista en memoria.
     *
     * @param producto el objeto {@link Producto} a registrar
     */
    void crear(Producto producto);

    /**
     * Busca un producto mediante su código único.
     *
     * @param codigo código del producto a buscar
     * @return el producto encontrado o null si no existe
     */
    Producto buscarPorCodigo(int codigo);

    /**
     * Busca productos que coincidan con un nombre o parte del nombre.
     *
     * @param nombre nombre o término a buscar
     * @return lista de productos cuyo nombre coincida con el criterio
     */
    List<Producto> buscarPorNombre(String nombre);

    /**
     * Actualiza un producto previamente registrado.
     *
     * @param producto producto con los nuevos datos a actualizar
     */
    void actualizar(Producto producto);

    /**
     * Elimina un producto del sistema mediante su código.
     *
     * @param codigo código del producto a eliminar
     */
    void eliminar(int codigo);

    /**
     * Lista todos los productos registrados en el sistema.
     *
     * @return lista completa de productos
     */
    List<Producto> listarTodos();

}