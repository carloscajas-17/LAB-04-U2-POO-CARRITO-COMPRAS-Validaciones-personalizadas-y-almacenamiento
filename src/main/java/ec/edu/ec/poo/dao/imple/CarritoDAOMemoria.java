package ec.edu.ec.poo.dao.imple;

import ec.edu.ec.poo.dao.CarritoDAO;
import ec.edu.ec.poo.modelo.Carrito;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Clase que implementa la Interfaz{@link CarritoDAO} utilizando una lista
 * en memoria.
 */

public class CarritoDAOMemoria implements CarritoDAO {
    /**
     * Cosntructor que inicializa la lista de carritos vacios
     */
    private List<Carrito> listaCarritos;


    /**
     * Constructor que inicializa la lista de carritos vacio.
     */
    public CarritoDAOMemoria() {
        listaCarritos = new ArrayList<Carrito>();
    }

    /**
     * Agregga un nuevo carrito a la lista en memoria
     * @param carrito el carrito a registrar
     */
    @Override
    public void crear(Carrito carrito) {
        listaCarritos.add(carrito);
    }

    /**
     * Busca un carrito por su codigo unico
     * @param codigo codigo del carrito
     * @return carrrito ecnontrado o {@code null} si no existe
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
     * Busca y retorna todos los carritos asociados a un usuario especifico
     * @param username ID o nombre de usuario
     * @return lista de carritos vinculados al usuario
     */
    @Override
    public List<Carrito> buscarPorUsuario(String username) {
        List<Carrito> carritosUsuario = new ArrayList<>();
        for (Carrito carrito : listaCarritos) {
            if (carrito.getUsuario() != null &&
                    carrito.getUsuario().getId().equals(username)) {
                carritosUsuario.add(carrito);
            }
        }
        return carritosUsuario;
    }

    /**
     * Actualiza un carrito existente buscando por su codigo.
     * Reemplaza el carrito antiguo por el nuevo con la misma identificacion.
     * @param carrito carrito actualizado
     */
    @Override
    public void actualizar(Carrito carrito) {
        for(int i = 0; i < listaCarritos.size(); i++) {
            if(listaCarritos.get(i).getCodigo() == carrito.getCodigo()) {
                listaCarritos.set(i, carrito);
                break;
            }
        }
    }

    /**
     * Elimina un carrito de la lista en memoria segun su codigo
     * @param codigo codigo del carrito a eliminar
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
     * Lista todos los carritos pertenecientes a un usuario especifico
     * @param idUsuario ID del usuario
     * @return lista de carritos asocidos
     */
    @Override
    public List<Carrito> listarPorUsuario(String idUsuario) {
        List<Carrito> resultado = new ArrayList<>();
        for (Carrito carrito : listaCarritos) {
            if (carrito.getUsuario() != null &&
                    carrito.getUsuario().getId().equals(idUsuario)) {
                resultado.add(carrito);
            }
        }
        return resultado;
    }










    @Override
    public List<Carrito> listarTodos() {
        return listaCarritos;
    }
}

