package ec.edu.ec.poo.modelo;


import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

public class Carrito {
    private final double IVA = 0.15;
    private static int contador = 1;
    private int codigo;
    private Usuario usuario;
    private GregorianCalendar fechaCreacion;
    private List<ItemCarrito> items;

    public Carrito() {
        this.codigo = contador++;
        this.usuario = usuario;
        items = new ArrayList<>();
        fechaCreacion = new GregorianCalendar();
    }

    public Carrito(Usuario usuario) {
        this();
        this.usuario = usuario;
    }

    public double getIVA() {
        return IVA;
    }

    public int getCodigo() {
        return codigo;
    }

    public static int getContador() {
        return contador;
    }

    public static void setContador(int contador) {
        Carrito.contador = contador;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public GregorianCalendar getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(GregorianCalendar fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<ItemCarrito> getItems() {
        return items;
    }

    public void setItems(List<ItemCarrito> items) {
        this.items = items;
    }

    public void agregarProducto(Producto producto, int cantidad) {
        items.add(new ItemCarrito(producto, cantidad));
    }

    public java.util.Date getFecha() {
        return fechaCreacion.getTime();
    }

    public double getSubtotal() {
        return calcularSubtotal();
    }

    public double getIva() {
        return calcularIVA();
    }

    public double getTotal() {
        return calcularTotal();
    }

    public List<ItemCarrito> getProductos() {
        return items;
    }


    public void eliminarProducto(int codigoProducto) {
        Iterator<ItemCarrito> it = items.iterator();
        while (it.hasNext()) {
            if (it.next().getProducto().getCodigo() == codigoProducto) {
                it.remove();
                break;
            }
        }
    }

    public void vaciarCarrito() {
        items.clear();
    }

    public List<ItemCarrito> obtenerItems() {
        return items;
    }

    public boolean estaVacio() {
        return items.isEmpty();
    }

    public double calcularSubtotal() {
        double subtotal = 0;
        for (ItemCarrito item : items) {
            subtotal += item.getProducto().getPrecio() * item.getCantidad();
        }
        return subtotal;
    }

    public double calcularIVA() {
        double subtotal = calcularSubtotal();
        return subtotal * IVA;
    }

    public double calcularTotal() {
        return calcularSubtotal() + calcularIVA();
    }

    public void modificarCantidadProducto(int codigoProducto, int nuevaCantidad) {
        for (ItemCarrito item : items) {
            if (item.getProducto().getCodigo() == codigoProducto) {
                item.setCantidad(nuevaCantidad);
                return;
            }
        }
    }

    public boolean contieneProducto(int codigoProducto) {
        return items.stream().anyMatch(item ->
                item.getProducto().getCodigo() == codigoProducto
        );
    }

    public ItemCarrito buscarItemPorCodigo(int codigo) {
        for (ItemCarrito item : items) {
            if (item.getProducto().getCodigo() == codigo) {
                return item; // Retorna el ítem si encuentra coincidencia
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Carrito{" +
                "IVA=" + IVA +
                ", Código: " + codigo +
                ", Usuario: " + usuario +
                ", Fecha de Creación: " + fechaCreacion +
                ", Items: " + items +
                '}';
    }
}