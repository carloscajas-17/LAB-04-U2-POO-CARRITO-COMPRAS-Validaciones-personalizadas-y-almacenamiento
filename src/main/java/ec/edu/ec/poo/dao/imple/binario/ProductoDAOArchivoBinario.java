package ec.edu.ec.poo.dao.imple.binario;

import ec.edu.ec.poo.dao.ProductoDAO;
import ec.edu.ec.poo.modelo.Producto;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación completa de ProductoDAO usando archivos binarios.
 * Gestiona almacenamiento completo de productos mediante serialización, siguiendo la lógica académica.
 */
public class ProductoDAOArchivoBinario implements ProductoDAO, Serializable {

    private final List<Producto> productos;
    private final String rutaArchivo;

    public ProductoDAOArchivoBinario(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
        this.productos = new ArrayList<>();
        cargarDesdeArchivo();
    }

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

    private void guardarEnArchivo() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(rutaArchivo))) {
            oos.writeObject(productos);
        } catch (IOException e) {
            System.out.println("Error al guardar productos binario: " + e.getMessage());
        }
    }

    @Override
    public void crear(Producto producto) {
        if (!existeProducto(producto.getCodigo())) {
            productos.add(producto);
            guardarEnArchivo();
        }
    }

    @Override
    public Producto buscarPorCodigo(int codigo) {
        return productos.stream()
                .filter(p -> p.getCodigo() == codigo)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Producto buscarPorNombre(String nombre) {
        return productos.stream()
                .filter(p -> p.getNombre().equalsIgnoreCase(nombre))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Producto> listarTodos() {
        return new ArrayList<>(productos);
    }

    @Override
    public List<Producto> listar() {
        return new ArrayList<>(productos);
    }

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

    @Override
    public void eliminar(int codigo) {
        productos.removeIf(p -> p.getCodigo() == codigo);
        guardarEnArchivo();
    }

    public boolean existeProducto(int codigo) {
        return buscarPorCodigo(codigo) != null;
    }

    public int contarProductos() {
        return productos.size();
    }

    public double calcularTotalInventario() {
        return productos.stream().mapToDouble(Producto::getPrecio).sum();
    }

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