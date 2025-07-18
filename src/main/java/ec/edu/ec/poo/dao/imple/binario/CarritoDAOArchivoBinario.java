package ec.edu.ec.poo.dao.imple.binario;

import ec.edu.ec.poo.dao.CarritoDAO;
import ec.edu.ec.poo.modelo.Carrito;
import ec.edu.ec.poo.modelo.Usuario;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación completa de CarritoDAO usando archivos binarios.
 * Almacena carritos con información completa de usuario y productos.
 */
public class CarritoDAOArchivoBinario implements CarritoDAO, Serializable {

    private final List<Carrito> carritos;
    private final String rutaArchivo;

    public CarritoDAOArchivoBinario(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
        this.carritos = new ArrayList<>();
        cargarDesdeArchivo();
    }

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

    private void guardarEnArchivo() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(rutaArchivo))) {
            oos.writeObject(carritos);
        } catch (IOException e) {
            System.out.println("Error al guardar carritos binario: " + e.getMessage());
        }
    }

    @Override
    public void crear(Carrito carrito) {
        if (!existeCarrito(carrito.getCodigo())) {
            carritos.add(carrito);
            guardarEnArchivo();
        }
    }

    @Override
    public Carrito buscarPorCodigo(int codigo) {
        return carritos.stream()
                .filter(c -> c.getCodigo() == codigo)
                .findFirst()
                .orElse(null);
    }

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

    @Override
    public List<Carrito> listarTodos() {
        return new ArrayList<>(carritos);
    }

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

    @Override
    public void eliminar(int codigo) {
        carritos.removeIf(c -> c.getCodigo() == codigo);
        guardarEnArchivo();
    }

    public boolean existeCarrito(int codigo) {
        return buscarPorCodigo(codigo) != null;
    }

    public int contarCarritos() {
        return carritos.size();
    }

    public double obtenerTotalCarritos() {
        return carritos.stream().mapToDouble(Carrito::calcularTotal).sum();
    }
}
