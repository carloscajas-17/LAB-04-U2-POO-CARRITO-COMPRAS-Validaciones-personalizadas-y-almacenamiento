package ec.edu.ec.poo.dao.imple.memoria;

import ec.edu.ec.poo.dao.ProductoDAO;
import ec.edu.ec.poo.modelo.Producto;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Clase que implementa {@link ProductoDAO} almacenando los productos
 * de manera no persistente utilizando una lista en memoria.
 */
public class ProductoDAOMemoria implements ProductoDAO {

    /**
     * Lista interna que almacena los productos.
     */
    private List<Producto> productos;

    /**
     * Constructor que inicializa la lista de productos vacía.
     */
    public ProductoDAOMemoria() {
        productos = new ArrayList<>();
    }

    /**
     * Agrega un producto a la lista.
     * @param producto producto a agregar.
     */
    @Override
    public void crear(Producto producto) {
        if (producto == null) {
            throw new IllegalArgumentException("No se puede agregar un producto nulo.");
        }
        if (buscarPorCodigo(producto.getCodigo()) != null) {
            throw new IllegalArgumentException("Ya existe un producto con el mismo código.");
        }
        productos.add(producto);
    }


    /**
     * Busca un producto según su código único.
     * @param codigo código del producto.
     * @return producto encontrado o null si no existe.
     */
    @Override
    public Producto buscarPorCodigo(int codigo) {
        for (Producto producto : productos) {
            if (producto.getCodigo() == codigo) {
                return producto;
            }
        }
        return null;
    }

    /**
     * Busca productos cuyo nombre comience con un prefijo específico.
     * @param nombre prefijo del nombre.
     * @return lista de productos encontrados.
     */
    /**
     * Busca un único producto que coincida exactamente con el nombre.
     * @param nombre nombre exacto del producto
     * @return producto encontrado o null si no existe coincidencia
     */
    @Override
    public Producto buscarPorNombre(String nombre) {
        for (Producto producto : productos) {
            if (producto.getNombre().equalsIgnoreCase(nombre)) {
                return producto;
            }
        }
        return null;
    }



    /**
     * Lista todos los productos almacenados en memoria.
     * @return lista completa de productos.
     */
    @Override
    public List<Producto> listar() {
        return new ArrayList<>(productos);
    }

    /**
     * Actualiza un producto existente dentro de la lista.
     * @param producto producto actualizado.
     * @return true si se actualizó correctamente, false si no se encontró el producto.
     */
    @Override
    public boolean actualizar(Producto producto) {
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getCodigo() == producto.getCodigo()) {
                productos.set(i, producto);
                return true;
            }
        }
        return false;
    }

    /**
     * Elimina un producto según su código.
     * @param codigo código del producto a eliminar.
     */
    @Override
    public void eliminar(int codigo) {
        productos.removeIf(producto -> producto.getCodigo() == codigo);
    }

    /**
     * Lista todos los productos almacenados.
     * @return lista de productos.
     */
    @Override
    public List<Producto> listarTodos() {
        return new ArrayList<>(productos);
    }
}
