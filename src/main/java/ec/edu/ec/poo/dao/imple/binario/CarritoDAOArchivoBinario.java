package ec.edu.ec.poo.dao.imple.binario;

import ec.edu.ec.poo.dao.CarritoDAO;
import ec.edu.ec.poo.modelo.Carrito;
import ec.edu.ec.poo.modelo.Usuario;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación de {@link CarritoDAO} usando archivos binarios.
 * <p>
 * Gestiona operaciones CRUD sobre carritos de compras almacenando
 * información completa de carrito, usuario y productos mediante serialización.
 * </p>
 */
public class CarritoDAOArchivoBinario implements CarritoDAO, Serializable {

    /**
     * Lista de carritos cargados desde el archivo binario.
     */
    private final List<Carrito> carritos;

    /**
     * Ruta del archivo binario donde se almacenan los carritos.
     */
    private final String rutaArchivo;

    /**
     * Constructor que inicializa la lista de carritos desde un archivo binario.
     *
     * @param rutaArchivo Ruta donde se guarda el archivo binario.
     */
    public CarritoDAOArchivoBinario(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
        this.carritos = new ArrayList<>();
        cargarDesdeArchivo();
    }

    /**
     * Carga los carritos desde el archivo binario usando deserialización.
     */
    private void cargarDesdeArchivo() {
        carritos.clear();
        File archivo = new File(rutaArchivo);
        if (!archivo.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            Object obj = ois.readObject();
            if (obj instanceof List<?>) {
                List<?> listaLeida = (List<?>) obj;
                for (Object elemento : listaLeida) {
                    if (elemento instanceof Carrito) {
                        carritos.add((Carrito) elemento);
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al cargar carritos binario: " + e.getMessage());
        }
    }

    /**
     * Guarda la lista actualizada de carritos en el archivo binario usando serialización.
     */
    private void guardarEnArchivo() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(rutaArchivo))) {
            oos.writeObject(carritos);
        } catch (IOException e) {
            System.out.println("Error al guardar carritos binario: " + e.getMessage());
        }
    }
    /**
     * {@inheritDoc}
     * Crea un nuevo carrito y lo guarda en el archivo binario, siempre y cuando no exista previamente.
     *
     * @param carrito Carrito a registrar.
     */
    @Override
    public void crear(Carrito carrito) {
        if (!existeCarrito(carrito.getCodigo())) {
            carritos.add(carrito);
            guardarEnArchivo();
        }
    }

    /**
     * {@inheritDoc}
     * Busca un carrito por su código único.
     *
     * @param codigo Código del carrito.
     * @return Carrito encontrado o null si no existe.
     */
    @Override
    public Carrito buscarPorCodigo(int codigo) {
        return carritos.stream()
                .filter(c -> c.getCodigo() == codigo)
                .findFirst()
                .orElse(null);
    }

    /**
     * {@inheritDoc}
     * Busca todos los carritos de un usuario según su ID.
     *
     * @param username ID del usuario.
     * @return Lista de carritos pertenecientes al usuario.
     */
    @Override
    public List<Carrito> buscarPorUsuario(String username) {
        List<Carrito> resultado = new ArrayList<>();
        for (Carrito carrito : carritos) {
            Usuario usuario = carrito.getUsuario();
            if (usuario != null && usuario.getId().equals(username)) {
                resultado.add(carrito);
            }
        }
        return resultado;
    }

    /**
     * {@inheritDoc}
     * Lista todos los carritos registrados.
     *
     * @return Lista completa de carritos.
     */
    @Override
    public List<Carrito> listarTodos() {
        return new ArrayList<>(carritos);
    }

    /**
     * {@inheritDoc}
     * Lista los carritos filtrados por un usuario específico.
     *
     * @param idUsuario ID del usuario.
     * @return Lista de carritos asociados al usuario.
     */
    @Override
    public List<Carrito> listarPorUsuario(String idUsuario) {
        List<Carrito> listaPorUsuario = new ArrayList<>();
        for (Carrito carrito : carritos) {
            if (carrito.getUsuario() != null && carrito.getUsuario().getId().equals(idUsuario)) {
                listaPorUsuario.add(carrito);
            }
        }
        return listaPorUsuario;
    }

    /**
     * {@inheritDoc}
     * Actualiza un carrito si ya existe mediante su código.
     *
     * @param carrito Carrito con datos actualizados.
     * @return true si se actualizó correctamente, false si no existe.
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
     * Elimina un carrito mediante su código único y actualiza el archivo.
     *
     * @param codigo Código del carrito a eliminar.
     */
    @Override
    public void eliminar(int codigo) {
        carritos.removeIf(c -> c.getCodigo() == codigo);
        guardarEnArchivo();
    }

    /**
     * Verifica si un carrito existe mediante su código.
     *
     * @param codigo Código del carrito.
     * @return true si existe, false si no.
     */
    public boolean existeCarrito(int codigo) {
        return buscarPorCodigo(codigo) != null;
    }

    /**
     * Cuenta el total de carritos registrados.
     *
     * @return Cantidad total de carritos.
     */
    public int contarCarritos() {
        return carritos.size();
    }

    /**
     * Suma el total de todos los carritos considerando sus totales calculados.
     *
     * @return Suma total en dinero de todos los carritos.
     */
    public double obtenerTotalCarritos() {
        return carritos.stream().mapToDouble(Carrito::calcularTotal).sum();
    }



}
