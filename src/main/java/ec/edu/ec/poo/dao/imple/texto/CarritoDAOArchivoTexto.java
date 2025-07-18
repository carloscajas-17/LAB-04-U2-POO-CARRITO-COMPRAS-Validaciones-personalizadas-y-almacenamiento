package ec.edu.ec.poo.dao.imple.texto;

import ec.edu.ec.poo.dao.CarritoDAO;
import ec.edu.ec.poo.modelo.Carrito;
import ec.edu.ec.poo.modelo.ItemCarrito;
import ec.edu.ec.poo.modelo.Producto;
import ec.edu.ec.poo.modelo.Usuario;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Implementación de la interfaz {@link CarritoDAO} utilizando archivos de texto
 * para almacenar carritos de compras asociados a usuarios y productos.
 * <p>
 * Cada carrito se almacena en una línea con el siguiente formato:
 * codigo|fecha|idUsuario|item1;item2;...
 * </p>
 * Donde cada item contiene: codigoProducto,nombre,precio,cantidad
 */
public class CarritoDAOArchivoTexto implements CarritoDAO {

    /**
     * Lista de carritos cargados desde el archivo.
     */
    private final List<Carrito> carritos;

    /**
     * Ruta del archivo de texto donde se almacenan los carritos.
     */
    private final String rutaArchivo;

    /**
     * Lista de usuarios existente para asociar cada carrito.
     */
    private final List<Usuario> usuarios;

    /**
     * Formateador de fecha en formato dd/MM/yyyy para guardar y leer fechas.
     */
    private final SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");

    /**
     * Constructor que inicializa la lista de carritos desde un archivo.
     *
     * @param rutaArchivo Ruta del archivo donde se almacenan los carritos.
     * @param usuarios Lista de usuarios previamente registrados.
     */
    public CarritoDAOArchivoTexto(String rutaArchivo, List<Usuario> usuarios) {
        this.rutaArchivo = rutaArchivo;
        this.usuarios = usuarios;
        this.carritos = new ArrayList<>();
        cargarDesdeArchivo();
    }

    /**
     * Carga la lista de carritos desde el archivo de texto.
     * Cada línea representa un carrito completo con sus productos.
     * Si el archivo no existe, no se carga nada.
     */
    private void cargarDesdeArchivo() {
        carritos.clear();
        File archivo = new File(rutaArchivo);
        if (!archivo.exists()) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                try {
                    Carrito carrito = convertirLineaACarrito(linea);
                    if (carrito != null) carritos.add(carrito);
                } catch (Exception ex) {
                    System.out.println("Línea inválida: " + linea + " Error: " + ex.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println("Error al cargar carritos: " + e.getMessage());
        }
    }

    /**
     * Convierte una línea del archivo a un objeto {@link Carrito}.
     *
     * @param linea Línea en formato código|fecha|usuario|items.
     * @return El carrito creado o null si ocurre un error.
     */
    private Carrito convertirLineaACarrito(String linea) {
        try {
            String[] partes = linea.split("\\|");
            if (partes.length < 4) return null;

            int codigo = Integer.parseInt(partes[0]);
            String fechaTexto = partes[1];
            String idUsuario = partes[2];

            Usuario usuario = usuarios.stream()
                    .filter(u -> u.getId().equals(idUsuario))
                    .findFirst().orElse(null);
            if (usuario == null) return null;

            Carrito carrito = new Carrito(usuario);
            carrito.setCodigo(codigo);
            Date fecha = formatoFecha.parse(fechaTexto);
            GregorianCalendar fechaCreacion = new GregorianCalendar();
            fechaCreacion.setTime(fecha);
            carrito.setFechaCreacion(fechaCreacion);

            List<ItemCarrito> items = new ArrayList<>();
            String[] itemsDatos = partes[3].split(";");
            for (String item : itemsDatos) {
                if (!item.isBlank()) {
                    String[] itemPartes = item.split(",");
                    int codigoProducto = Integer.parseInt(itemPartes[0]);
                    String nombre = itemPartes[1];
                    double precio = Double.parseDouble(itemPartes[2]);
                    int cantidad = Integer.parseInt(itemPartes[3]);
                    items.add(new ItemCarrito(new Producto(codigoProducto, nombre, precio), cantidad));
                }
            }
            carrito.setItems(items);
            return carrito;
        } catch (Exception e) {
            System.out.println("Error convertir carrito: " + e.getMessage());
            return null;
        }
    }

    /**
     * Guarda la lista actualizada de carritos en el archivo de texto.
     * Sobrescribe el archivo completo en cada llamada.
     */
    private void guardarEnArchivo() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo))) {
            for (Carrito carrito : carritos) {
                StringBuilder linea = new StringBuilder();
                linea.append(carrito.getCodigo()).append("|")
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
     * Crea un nuevo carrito si no existe previamente según su código único.
     *
     * @param carrito Objeto Carrito a registrar.
     */
    @Override
    public void crear(Carrito carrito) {
        if (!existeCarrito(carrito.getCodigo())) {
            carritos.add(carrito);
            guardarEnArchivo();
        }
    }

    /**
     * Busca un carrito por su código identificador.
     *
     * @param codigo Código del carrito a buscar.
     * @return El carrito encontrado o null si no existe.
     */
    @Override
    public Carrito buscarPorCodigo(int codigo) {
        return carritos.stream()
                .filter(c -> c.getCodigo() == codigo)
                .findFirst()
                .orElse(null);
    }

    /**
     * Busca todos los carritos asociados a un usuario usando su username.
     *
     * @param username ID del usuario.
     * @return Lista de carritos del usuario.
     */
    @Override
    public List<Carrito> buscarPorUsuario(String username) {
        return listarPorUsuario(username);
    }

    /**
     * Lista todos los carritos almacenados.
     *
     * @return Lista completa de carritos.
     */
    @Override
    public List<Carrito> listarTodos() {
        return new ArrayList<>(carritos);
    }

    /**
     * Actualiza un carrito existente buscando por su código.
     *
     * @param carrito Carrito con datos actualizados.
     * @return true si la actualización fue exitosa, false si no se encontró el carrito.
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
     * Elimina un carrito según su código y actualiza el archivo.
     *
     * @param codigo Código del carrito a eliminar.
     */
    @Override
    public void eliminar(int codigo) {
        carritos.removeIf(c -> c.getCodigo() == codigo);
        guardarEnArchivo();
    }

    /**
     * Lista todos los carritos de un usuario según su ID.
     *
     * @param idUsuario ID del usuario.
     * @return Lista de carritos asociados al usuario.
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
     * Verifica si un carrito ya existe en la lista mediante su código.
     *
     * @param codigo Código del carrito.
     * @return true si existe, false si no.
     */
    public boolean existeCarrito(int codigo) {
        return buscarPorCodigo(codigo) != null;
    }

    /**
     * Cuenta la cantidad de carritos asociados a un usuario.
     *
     * @param idUsuario ID del usuario.
     * @return Número total de carritos que tiene el usuario.
     */
    public int contarCarritosUsuario(String idUsuario) {
        return (int) carritos.stream()
                .filter(c -> c.getUsuario().getId().equals(idUsuario))
                .count();
    }




}
