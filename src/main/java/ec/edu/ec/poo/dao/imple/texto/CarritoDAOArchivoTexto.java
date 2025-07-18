package ec.edu.ec.poo.dao.imple.texto;

import ec.edu.ec.poo.dao.CarritoDAO;
import ec.edu.ec.poo.modelo.Carrito;
import ec.edu.ec.poo.modelo.ItemCarrito;
import ec.edu.ec.poo.modelo.Producto;
import ec.edu.ec.poo.modelo.Usuario;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class CarritoDAOArchivoTexto implements CarritoDAO {

    private final List<Carrito> carritos;
    private final String rutaArchivo;
    private final List<Usuario> usuarios;
    private final SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");

    public CarritoDAOArchivoTexto(String rutaArchivo, List<Usuario> usuarios) {
        this.rutaArchivo = rutaArchivo;
        this.usuarios = usuarios;
        this.carritos = new ArrayList<>();
        cargarDesdeArchivo();
    }

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
        return listarPorUsuario(username);
    }



    @Override
    public List<Carrito> listarTodos() {
        return new ArrayList<>(carritos);
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

    public boolean existeCarrito(int codigo) {
        return buscarPorCodigo(codigo) != null;
    }

    public int contarCarritosUsuario(String idUsuario) {
        return (int) carritos.stream()
                .filter(c -> c.getUsuario().getId().equals(idUsuario))
                .count();
    }
}
