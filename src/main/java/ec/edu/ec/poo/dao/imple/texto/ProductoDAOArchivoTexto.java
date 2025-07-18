package ec.edu.ec.poo.dao.imple.texto;

import ec.edu.ec.poo.dao.ProductoDAO;
import ec.edu.ec.poo.modelo.Producto;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación de la interfaz {@link ProductoDAO} utilizando un archivo de texto
 * como medio de persistencia para los productos.
 * <p>
 * Cada producto se almacena en una línea del archivo con el siguiente formato:
 * codigo|nombre|precio
 * </p>
 */
public class ProductoDAOArchivoTexto implements ProductoDAO {

    /**
     * Lista de productos cargados desde el archivo.
     */
    private final List<Producto> productos;

    /**
     * Ruta del archivo de texto donde se almacenan los productos.
     */
    private final String rutaArchivo;

    /**
     * Constructor que inicializa el DAO con la ruta del archivo y carga los productos existentes.
     *
     * @param rutaArchivo Ruta del archivo donde se almacenan los productos.
     */
    public ProductoDAOArchivoTexto(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
        this.productos = new ArrayList<>();
        cargarDesdeArchivo();
    }

    /**
     * Carga la lista de productos desde el archivo de texto.
     * Cada línea del archivo debe tener el formato: codigo|nombre|precio
     * Si el archivo no existe, la lista queda vacía.
     */
    private void cargarDesdeArchivo() {
        productos.clear();
        File archivo = new File(rutaArchivo);
        if (!archivo.exists()) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                try {
                    Producto producto = convertirLineaAProducto(linea);
                    if (producto != null) productos.add(producto);
                } catch (Exception ex) {
                    System.out.println("Línea inválida: " + linea + " Error: " + ex.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Error al cargar productos: " + e.getMessage());
        }
    }

    /**
     * Convierte una línea del archivo en un objeto {@link Producto}.
     *
     * @param linea La línea leída del archivo de texto.
     * @return Un objeto Producto o null si ocurre un error.
     */
    private Producto convertirLineaAProducto(String linea) {
        try {
            String[] partes = linea.split("\\|");
            if (partes.length < 3) return null;
            int codigo = Integer.parseInt(partes[0]);
            String nombre = partes[1];
            double precio = Double.parseDouble(partes[2]);
            return new Producto(codigo, nombre, precio);
        } catch (Exception e) {
            System.out.println("Error convirtiendo producto: " + e.getMessage());
            return null;
        }
    }

    /**
     * Guarda toda la lista de productos actualizada en el archivo de texto.
     * Cada producto se guarda como una línea en formato: codigo|nombre|precio
     */
    private void guardarEnArchivo() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo))) {
            for (Producto producto : productos) {
                String linea = producto.getCodigo() + "|" + producto.getNombre() + "|" + producto.getPrecio();
                writer.write(linea);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar productos: " + e.getMessage());
        }
    }

    /**
     * Crea un nuevo producto si no existe previamente y guarda los cambios en el archivo.
     *
     * @param producto El objeto Producto que se desea crear.
     */
    @Override
    public void crear(Producto producto) {
        if (!existeProducto(producto.getCodigo())) {
            productos.add(producto);
            guardarEnArchivo();
        }
    }

    /**
     * Busca un producto en la lista por su código único.
     *
     * @param codigo Código identificador del producto.
     * @return El producto encontrado o null si no existe.
     */
    @Override
    public Producto buscarPorCodigo(int codigo) {
        return productos.stream()
                .filter(p -> p.getCodigo() == codigo)
                .findFirst()
                .orElse(null);
    }

    /**
     * Busca un producto en la lista por su nombre, ignorando mayúsculas y minúsculas.
     *
     * @param nombre Nombre del producto.
     * @return El producto encontrado o null si no existe.
     */
    @Override
    public Producto buscarPorNombre(String nombre) {
        return productos.stream()
                .filter(p -> p.getNombre().equalsIgnoreCase(nombre))
                .findFirst()
                .orElse(null);
    }

    /**
     * Lista todos los productos almacenados.
     *
     * @return Una lista de todos los productos.
     */
    @Override
    public List<Producto> listar() {
        return new ArrayList<>(productos);
    }

    /**
     * Lista todos los productos registrados (sin filtros adicionales).
     *
     * @return Lista de productos completa.
     */
    @Override
    public List<Producto> listarTodos() {
        return new ArrayList<>(productos);
    }

    /**
     * Actualiza la información de un producto existente basado en su código.
     *
     * @param producto Producto con la información actualizada.
     * @return true si la actualización fue exitosa, false si no se encontró el producto.
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
     * Elimina un producto de la lista por su código.
     *
     * @param codigo Código del producto a eliminar.
     */
    @Override
    public void eliminar(int codigo) {
        productos.removeIf(p -> p.getCodigo() == codigo);
        guardarEnArchivo();
    }

    /**
     * Verifica si un producto ya existe mediante su código.
     *
     * @param codigo Código del producto.
     * @return true si el producto existe, false si no.
     */
    public boolean existeProducto(int codigo) {
        return buscarPorCodigo(codigo) != null;
    }

    /**
     * Devuelve la cantidad total de productos almacenados.
     *
     * @return Número total de productos.
     */
    public int contarProductos() {
        return productos.size();
    }
}