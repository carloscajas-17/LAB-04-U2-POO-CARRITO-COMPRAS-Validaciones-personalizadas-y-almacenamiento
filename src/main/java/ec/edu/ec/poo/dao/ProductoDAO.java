package ec.edu.ec.poo.dao;

import ec.edu.ec.poo.modelo.Producto;

import java.util.List;

/**
 * Interfaz DAO para la gestión de productos.
 * Define las operaciones CRUD básicas sobre los objetos {@link Producto}.
 * Adaptada para trabajar con implementación compatible a archivos de texto o binario.
 * Incluye retorno booleano para operaciones que modifican y búsqueda directa por nombre.
 */
public interface ProductoDAO {

    /**
     * Crea un nuevo producto y lo guarda en el sistema.
     * @param producto el objeto {@link Producto} a registrar
     */
    void crear(Producto producto);

    /**
     * Busca un producto mediante su código único.
     * @param codigo código del producto a buscar
     * @return el producto encontrado o null si no existe
     */
    Producto buscarPorCodigo(int codigo);

    /**
     * Busca un único producto que coincida exactamente con el nombre.
     * @param nombre nombre exacto del producto
     * @return producto encontrado o null si no existe coincidencia
     */
    Producto buscarPorNombre(String nombre);

    /**
     * Retorna una lista con todos los productos registrados.
     * @return lista completa de productos
     */
    List<Producto> listar();

    /**
     * Retorna todos los productos registrados, alias para listar().
     * @return lista completa de productos
     */
    List<Producto> listarTodos();

    /**
     * Actualiza la información de un producto existente.
     * @param producto producto con la información actualizada
     * @return true si se actualizó correctamente, false si no se encontró
     */
    boolean actualizar(Producto producto);

    /**
     * Elimina un producto mediante su código único.
     * @param codigo código del producto a eliminar
     */
    void eliminar(int codigo);
}
