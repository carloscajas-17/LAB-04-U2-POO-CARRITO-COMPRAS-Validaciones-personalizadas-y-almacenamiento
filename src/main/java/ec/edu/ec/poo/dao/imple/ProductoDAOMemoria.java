package ec.edu.ec.poo.dao.imple;





import ec.edu.ec.poo.dao.ProductoDAO;
import ec.edu.ec.poo.modelo.Producto;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Clase que implementa {@link ProductoDAO} almacenando los productos
 * de mandera que persistencia utilizando una lista en memoria
 *
 */
public class ProductoDAOMemoria implements ProductoDAO {

    /**
     * Lista interna que alamacena los productos
     */
    private List<Producto> productos;

    /**
     * Constructor que inicializa la lista de productos vacía
     */
    public ProductoDAOMemoria() {
        productos = new ArrayList<Producto>();
    }

    /**
     * Agrega un prodcuto a la lista
     * @param producto producto a agregar
     */
    @Override
    public void crear(Producto producto) {
        productos.add(producto);
    }

    /**
     * Busca un producto según su código único
     * @param codigo código del producto
     * @return producto encontrado o null si no existe
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
     * Busca productos que comiencen con un nombre específico
     * @param nombre prefijo del nombre
     * @return lista de productos encontrados
     */
    @Override
    public List<Producto> buscarPorNombre(String nombre) {
        List<Producto> productosEncontrados = new ArrayList<>();
        for (Producto producto : productos) {
            if (producto.getNombre().startsWith(nombre)) {
                productosEncontrados.add(producto);
            }
        }
        return productosEncontrados;
    }

    /**
     * Actualiza un producto existente dentro de la lista
     * @param producto producto actualizado
     */
    @Override
    public void actualizar(Producto producto) {
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getCodigo() == producto.getCodigo()) {
                productos.set(i, producto);
            }
        }
    }

    /**
     * Elimina un producto según su código
     * @param codigo código del producto a eliminar
     */
    @Override
    public void eliminar(int codigo) {
        Iterator<Producto> iterator = productos.iterator();
        while (iterator.hasNext()) {
            Producto producto = iterator.next();
            if (producto.getCodigo() == codigo) {
                iterator.remove();
            }
        }
    }

    /**
     * Lista todos los productos almacenados en memoria
     * @return lista completa de productos
     */
    @Override
    public List<Producto> listarTodos() {
        return productos;
    }
}