/**
 * Implementación corregida de la clase CarritoDAOMemoria con método actualizar completo y documentado.
 */
package ec.edu.ec.poo.dao.imple.memoria;

import ec.edu.ec.poo.dao.CarritoDAO;
import ec.edu.ec.poo.modelo.Carrito;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Clase que implementa la Interfaz {@link CarritoDAO} utilizando una lista
 * en memoria para gestionar los carritos de compras.
 */
public class CarritoDAOMemoria implements CarritoDAO {

    /**
     * Lista que almacena todos los carritos registrados en memoria.
     */
    private List<Carrito> listaCarritos;

    /**
     * Constructor que inicializa la lista de carritos vacía.
     */
    public CarritoDAOMemoria() {
        listaCarritos = new ArrayList<>();
    }

    /**
     * Agrega un nuevo carrito a la lista en memoria.
     * @param carrito el carrito a registrar.
     */
    @Override
    public void crear(Carrito carrito) {
        if (carrito == null) {
            throw new IllegalArgumentException("No se puede crear un carrito nulo.");
        }
        if (buscarPorCodigo(carrito.getCodigo()) != null) {
            throw new IllegalArgumentException("Ya existe un carrito con ese código.");
        }
        listaCarritos.add(carrito);
    }


    /**
     * Busca un carrito por su código único.
     * @param codigo el código del carrito.
     * @return carrito encontrado o {@code null} si no existe.
     */
    @Override
    public Carrito buscarPorCodigo(int codigo) {
        for(Carrito carrito : listaCarritos) {
            if(carrito.getCodigo() == codigo) {
                return carrito;
            }
        }
        return null;
    }

    /**
     * Busca todos los carritos asociados a un usuario específico.
     * @param username ID o nombre de usuario.
     * @return lista de carritos vinculados al usuario.
     */
    @Override
    public List<Carrito> buscarPorUsuario(String username) {
        List<Carrito> carritosUsuario = new ArrayList<>();
        for (Carrito carrito : listaCarritos) {
            if (carrito.getUsuario() != null && carrito.getUsuario().getId().equals(username)) {
                carritosUsuario.add(carrito);
            }
        }
        return carritosUsuario;
    }

    /**
     * Actualiza un carrito existente buscando por su código.
     * Reemplaza el carrito antiguo por el nuevo con la misma identificación.
     * @param carrito carrito actualizado.
     * @return {@code true} si el carrito fue actualizado, {@code false} si no se encontró.
     */
    @Override
    public boolean actualizar(Carrito carrito) {
        for(int i = 0; i < listaCarritos.size(); i++) {
            if(listaCarritos.get(i).getCodigo() == carrito.getCodigo()) {
                listaCarritos.set(i, carrito);
                return true; // carrito actualizado correctamente
            }
        }
        return false; // carrito no encontrado
    }

    /**
     * Elimina un carrito de la lista en memoria según su código.
     * @param codigo código del carrito a eliminar.
     */
    @Override
    public void eliminar(int codigo) {
        Iterator<Carrito> iterator = listaCarritos.iterator();
        while(iterator.hasNext()) {
            Carrito carrito = iterator.next();
            if(carrito.getCodigo() == codigo) {
                iterator.remove();
            }
        }
    }

    /**
     * Lista todos los carritos pertenecientes a un usuario específico.
     * @param idUsuario ID del usuario.
     * @return lista de carritos asociados.
     */
    @Override
    public List<Carrito> listarPorUsuario(String idUsuario) {
        List<Carrito> resultado = new ArrayList<>();
        for (Carrito carrito : listaCarritos) {
            if (carrito.getUsuario() != null && carrito.getUsuario().getId().equals(idUsuario)) {
                resultado.add(carrito);
            }
        }
        return resultado;
    }

    /**
     * Retorna la lista completa de carritos registrados en memoria.
     * @return lista de todos los carritos.
     */
    @Override
    public List<Carrito> listarTodos() {
        return  new ArrayList<>(listaCarritos);
    }
}
