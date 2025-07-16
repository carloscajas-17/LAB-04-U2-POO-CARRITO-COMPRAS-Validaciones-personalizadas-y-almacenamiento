package ec.edu.ec.poo.dao.imple;







import ec.edu.ec.poo.dao.CarritoDAO;
import ec.edu.ec.poo.modelo.Carrito;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class CarritoDAOMemoria implements CarritoDAO {

    private List<Carrito> listaCarritos;



    public CarritoDAOMemoria() {
        listaCarritos = new ArrayList<Carrito>();
    }

    @Override
    public void crear(Carrito carrito) {
        listaCarritos.add(carrito);
    }

    @Override
    public Carrito buscarPorCodigo(int codigo) {
        for(Carrito carrito : listaCarritos) {
            if(carrito.getCodigo() == codigo) {
                return carrito;
            }
        }
        return null;
    }

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

    @Override
    public void actualizar(Carrito carrito) {
        for(int i = 0; i < listaCarritos.size(); i++) {
            if(listaCarritos.get(i).getCodigo() == carrito.getCodigo()) {
                listaCarritos.set(i, carrito);
                break;
            }
        }
    }

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

