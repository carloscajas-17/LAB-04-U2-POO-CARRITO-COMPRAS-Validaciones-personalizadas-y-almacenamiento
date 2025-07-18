package ec.edu.ec.poo.dao.imple.binario;

import ec.edu.ec.poo.dao.ProductoDAO;
import ec.edu.ec.poo.modelo.Producto;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase {@code ProductoDAOArchivoBinario} que implementa la interfaz {@link ProductoDAO}
 * para gestionar el almacenamiento y manipulación de productos utilizando archivos binarios.
 * <p>
 * Utiliza la serialización para almacenar una lista de productos de forma persistente,
 * permitiendo operaciones CRUD completas (crear, buscar, actualizar, eliminar).
 * </p>
 * <p>Incluye métodos auxiliares para conteo, verificación de existencia y filtrado por precio.</p>
 * <p><b>Nota:</b> Esta implementación es adecuada para entornos académicos con persistencia básica.</p>
 */
public class ProductoDAOArchivoBinario implements ProductoDAO, Serializable {

    /**
     * Lista de productos que se mantiene en memoria.
     */
    private final List<Producto> productos;

    /**
     * Ruta del archivo binario donde se almacenan los productos.
     */
    private final String rutaArchivo;

    /**
     * Constructor que inicializa el DAO con la ruta especificada y carga los datos desde el archivo binario.
     *
     * @param rutaArchivo Ruta del archivo binario donde se almacenan los productos.
     */
    public ProductoDAOArchivoBinario(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
        this.productos = new ArrayList<>();
        cargarDesdeArchivo();
    }

    /**
     * Carga la lista de productos desde el archivo binario mediante deserialización.
     * <p>Si el archivo no existe, la lista se inicializa vacía.</p>
     */
    private void cargarDesdeArchivo() {
        productos.clear();
        File archivo = new File(rutaArchivo);
        if (!archivo.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            Object obj = ois.readObject();
            if (obj instanceof List<?>) {
                List<?> lista = (List<?>) obj;
                for (Object p : lista) {
                    if (p instanceof Producto) {
                        productos.add((Producto) p);
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al cargar productos binario: " + e.getMessage());
        }
    }

    /**
     * Guarda la lista actualizada de productos en el archivo binario mediante serialización.
     */
    private void guardarEnArchivo() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(rutaArchivo))) {
            oos.writeObject(productos);
        } catch (IOException e) {
            System.out.println("Error al guardar productos binario: " + e.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     * Crea un nuevo producto y lo almacena si no existe previamente.
     *
     * @param producto Producto a registrar.
     */
    @Override
    public void crear(Producto producto) {
        if (!existeProducto(producto.getCodigo())) {
            productos.add(producto);
            guardarEnArchivo();
        }
    }

    /**
     * {@inheritDoc}
     * Busca un producto según su código único.
     *
     * @param codigo Código identificador del producto.
     * @return Producto encontrado o null si no existe.
     */
    @Override
    public Producto buscarPorCodigo(int codigo) {
        return productos.stream()
                .filter(p -> p.getCodigo() == codigo)
                .findFirst()
                .orElse(null);
    }

    /**
     * {@inheritDoc}
     * Busca un producto mediante su nombre (sin distinguir mayúsculas).
     *
     * @param nombre Nombre del producto.
     * @return Producto encontrado o null si no existe.
     */
    @Override
    public Producto buscarPorNombre(String nombre) {
        return productos.stream()
                .filter(p -> p.getNombre().equalsIgnoreCase(nombre))
                .findFirst()
                .orElse(null);
    }

    /**
     * {@inheritDoc}
     * Lista todos los productos registrados en el sistema.
     *
     * @return Lista completa de productos.
     */
    @Override
    public List<Producto> listarTodos() {
        return new ArrayList<>(productos);
    }

    /**
     * {@inheritDoc}
     * Lista todos los productos registrados (alias de listarTodos()).
     *
     * @return Lista de productos.
     */
    @Override
    public List<Producto> listar() {
        return new ArrayList<>(productos);
    }

    /**
     * {@inheritDoc}
     * Actualiza los datos de un producto existente mediante su código.
     *
     * @param producto Producto con los nuevos datos actualizados.
     * @return true si se actualizó correctamente, false si no se encontró.
     */
    @Override
    public boolean actualizar(Producto producto) {
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getCodigo() == producto.getCodigo()) {
                productos.set(i, producto);
                guardarEnArchivo();
                return true;
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     * Elimina un producto según su código.
     *
     * @param codigo Código del producto a eliminar.
     */
    @Override
    public void eliminar(int codigo) {
        productos.removeIf(p -> p.getCodigo() == codigo);
        guardarEnArchivo();
    }

    /**
     * Verifica si un producto ya existe en el sistema mediante su código.
     *
     * @param codigo Código identificador del producto.
     * @return true si el producto existe, false si no.
     */
    public boolean existeProducto(int codigo) {
        return buscarPorCodigo(codigo) != null;
    }

    /**
     * Cuenta la cantidad total de productos registrados.
     *
     * @return Número total de productos.
     */
    public int contarProductos() {
        return productos.size();
    }

    /**
     * Calcula el total del inventario sumando el precio de todos los productos.
     *
     * @return Suma total de precios de todos los productos.
     */
    public double calcularTotalInventario() {
        return productos.stream().mapToDouble(Producto::getPrecio).sum();
    }

    /**
     * Lista los productos cuyo precio es superior a un valor mínimo especificado.
     *
     * @param precioMinimo Precio mínimo para filtrar los productos.
     * @return Lista de productos con precio superior al mínimo.
     */
    public List<Producto> listarPorPrecioMayorA(double precioMinimo) {
        List<Producto> resultado = new ArrayList<>();
        for (Producto producto : productos) {
            if (producto.getPrecio() > precioMinimo) {
                resultado.add(producto);
            }
        }
        return resultado;
    }
}
