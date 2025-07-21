// CarritoDAOArchivoTexto.java
package ec.edu.ec.poo.dao.imple.texto;

import ec.edu.ec.poo.dao.CarritoDAO;
import ec.edu.ec.poo.modelo.*;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Implementación de {@link CarritoDAO} que permite gestionar los carritos mediante archivos de texto.
 * <p>
 * Cada carrito se guarda en el archivo de manera estructurada con los datos del carrito,
 * productos, cantidades, y relación con el usuario. Los datos se cargan al iniciar y se
 * actualizan automáticamente al realizar operaciones CRUD.
 * </p>
 */
public class CarritoDAOArchivoTexto implements CarritoDAO {

    /** Lista de carritos cargados desde archivo */
    private final List<Carrito> carritos;
    /** Lista de usuarios para asociación de carritos */
    private final List<Usuario> usuarios;
    /** Ruta del archivo de almacenamiento */
    private final String rutaArchivo;
    /** Formato para lectura y escritura de fechas */
    private final SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");


    /**
     * Constructor principal.
     * @param rutaArchivo ruta donde se almacenarán los carritos.
     * @param usuarios lista de usuarios registrados para asociar a los carritos.
     */
    public CarritoDAOArchivoTexto(String rutaArchivo, List<Usuario> usuarios) {
        this.rutaArchivo = rutaArchivo;
        this.usuarios = usuarios;
        this.carritos = new ArrayList<>();
        cargarDesdeArchivo();
    }

    /**
     * Carga los carritos desde el archivo de texto.
     * Si el archivo no existe, la lista de carritos permanece vacía.
     */
    private void cargarDesdeArchivo() {
        carritos.clear();
        File archivo = new File(rutaArchivo);
        if (!archivo.exists()) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                Carrito carrito = convertirLineaACarrito(linea);
                if (carrito != null) carritos.add(carrito);
            }
        } catch (IOException e) {
            System.out.println("Error al cargar carritos: " + e.getMessage());
        }
    }

    /**
     * Convierte una línea del archivo a un objeto {@link Carrito}.
     * @param linea línea con los datos serializados de un carrito.
     * @return carrito reconstruido o null si hay error de lectura.
     */
    private Carrito convertirLineaACarrito(String linea) {
        try {
            String[] partes = linea.split("\\|");
            if (partes.length < 7) return null;

            int codigo = Integer.parseInt(partes[0]);
            double subtotal = Double.parseDouble(partes[1]);
            double iva = Double.parseDouble(partes[2]);
            double total = Double.parseDouble(partes[3]);
            Date fecha = formatoFecha.parse(partes[4]);
            String idUsuario = partes[5];

            Usuario usuario = usuarios.stream()
                    .filter(u -> u.getId().equals(idUsuario))
                    .findFirst().orElse(null);
            if (usuario == null) return null;

            List<ItemCarrito> items = new ArrayList<>();
            String[] itemsDatos = partes[6].split(";");
            for (String item : itemsDatos) {
                if (!item.isBlank()) {
                    String[] datos = item.split(",");
                    Producto p = new Producto(
                            Integer.parseInt(datos[0]), datos[1], Double.parseDouble(datos[2]));
                    int cantidad = Integer.parseInt(datos[3]);
                    items.add(new ItemCarrito(p, cantidad));
                }
            }

            Carrito carrito = new Carrito(usuario);
            carrito.setCodigo(codigo);
            carrito.setFechaCreacion(new GregorianCalendar());
            carrito.getItems().addAll(items);
            return carrito;
        } catch (Exception e) {
            System.out.println("Error al convertir carrito: " + e.getMessage());
            return null;
        }
    }

    /**
     * Guarda todos los carritos en el archivo actualizando su contenido.
     */
    private void guardarEnArchivo() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo))) {
            for (Carrito carrito : carritos) {
                StringBuilder linea = new StringBuilder();
                linea.append(carrito.getCodigo()).append("|")
                        .append(carrito.calcularSubtotal()).append("|")
                        .append(carrito.calcularIVA()).append("|")
                        .append(carrito.calcularTotal()).append("|")
                        .append(formatoFecha.format(carrito.getFecha())).append("|")
                        .append(carrito.getUsuario().getId()).append("|");
                for (ItemCarrito item : carrito.getItems()) {
                    Producto p = item.getProducto();
                    linea.append(p.getCodigo()).append(",")
                            .append(p.getNombre()).append(",")
                            .append(p.getPrecio()).append(",")
                            .append(item.getCantidad()).append(";");
                }
                writer.write(linea.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar carritos: " + e.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     * Agrega un nuevo carrito al sistema.
     * Si ya existe un carrito con el mismo código, se lanza una excepción.
     * Al agregar un carrito se actualiza automáticamente el archivo.
     *
     * @param carrito objeto carrito a registrar.
     * @throws IllegalArgumentException si el código del carrito ya existe.
     */
    @Override
    public void crear(Carrito carrito) {
        if (buscarPorCodigo(carrito.getCodigo()) != null) {
            throw new IllegalArgumentException("Ya existe un carrito con ese código.");
        }
        carritos.add(carrito);
        guardarEnArchivo();
    }

    /**
     * {@inheritDoc}
     * Busca un carrito por su código único.
     *
     * @param codigo código identificador del carrito.
     * @return objeto {@link Carrito} encontrado o null si no existe.
     */
    @Override
    public Carrito buscarPorCodigo(int codigo) {
        return carritos.stream()
                .filter(c -> c.getCodigo() == codigo)
                .findFirst().orElse(null);
    }

    /**
     * {@inheritDoc}
     * Lista todos los carritos actualmente registrados.
     *
     * @return lista con todos los carritos.
     */
    @Override
    public List<Carrito> listarTodos() {
        return new ArrayList<>(carritos);
    }

    /**
     * {@inheritDoc}
     * Lista los carritos que pertenecen a un usuario específico.
     *
     * @param idUsuario ID del usuario cuyos carritos se desean listar.
     * @return lista de carritos asociados al usuario.
     */
    @Override
    public List<Carrito> listarPorUsuario(String idUsuario) {
        List<Carrito> resultado = new ArrayList<>();
        for (Carrito carrito : carritos) {
            if (carrito.getUsuario().getId().equals(idUsuario)) {
                resultado.add(carrito);
            }
        }
        return resultado;
    }

    /**
     * {@inheritDoc}
     * Busca carritos según el identificador de usuario.
     * Es funcionalmente similar a {@link #listarPorUsuario(String)}.
     *
     * @param idUsuario ID del usuario.
     * @return lista de carritos que pertenecen al usuario indicado.
     */
    @Override
    public List<Carrito> buscarPorUsuario(String idUsuario) {
        List<Carrito> resultado = new ArrayList<>();
        for (Carrito c : carritos) {
            if (c.getUsuario().getId().equals(idUsuario)) resultado.add(c);
        }
        return resultado;
    }

    /**
     * {@inheritDoc}
     * Actualiza un carrito existente en el sistema.
     * Si se encuentra un carrito con el mismo código, se reemplaza su información.
     *
     * @param carrito carrito actualizado a almacenar.
     * @return true si se actualizó exitosamente, false si no se encontró.
     */
    @Override
    public boolean actualizar(Carrito carrito) {
        for (int i = 0; i < carritos.size(); i++) {
            if (carritos.get(i).getCodigo() == carrito.getCodigo()) {
                carritos.set(i, carrito);
                guardarEnArchivo();
                return true;
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     * Elimina un carrito según su código.
     * Al eliminarlo se actualiza automáticamente el archivo de almacenamiento.
     *
     * @param codigo código del carrito a eliminar.
     */
    @Override
    public void eliminar(int codigo) {
        carritos.removeIf(c -> c.getCodigo() == codigo);
        guardarEnArchivo();
    }

}

