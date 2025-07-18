package ec.edu.ec.poo.dao.imple.texto;

import ec.edu.ec.poo.dao.ProductoDAO;
import ec.edu.ec.poo.modelo.Producto;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación de ProductoDAO usando archivos de texto.
 * Incluye operaciones CRUD completas y utilidades adicionales.
 */
public class ProductoDAOArchivoTexto implements ProductoDAO {

    private final List<Producto> productos;
    private final String rutaArchivo;

    public ProductoDAOArchivoTexto(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
        this.productos = new ArrayList<>();
        cargarDesdeArchivo();
    }

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
    public List<Producto> listar() {
        return new ArrayList<>(productos);
    }

    @Override
    public List<Producto> listarTodos() {
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
}
