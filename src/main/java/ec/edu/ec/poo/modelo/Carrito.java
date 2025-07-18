package ec.edu.ec.poo.modelo;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

/**
 * Clase que representa un carrito de compras.
 * Contiene productos agregados por el usuario junto con métodos para calcular totales, modificar, eliminar y listar productos.
 */
public class Carrito {
    /** Porcentaje de IVA aplicado a las compras (15%) */
    private final double IVA = 0.15;


    /** Contador estático para asignar un código único a cada carrito */
    private static int contador = 1;

    /** Código único del carrito */
    private int codigo;

    /** Usuario propietario del carrito */
    private Usuario usuario;


    /** Fecha de creación del carrito */
    private GregorianCalendar fechaCreacion;

    /** Lista de productos en el carrito */
    private List<ItemCarrito> items;

    /**
     * Constructor vacío, genera un carrito con código único y fecha actual.
     */
    public Carrito() {
        this.codigo = contador++;
        this.usuario = usuario;
        items = new ArrayList<>();
        fechaCreacion = new GregorianCalendar();
    }

    /**
     * Constructor con usuario asociado.
     * @param usuario el usuario propietario del carrito
     */
    public Carrito(Usuario usuario) {
        this();
        this.usuario = usuario;
    }


    /** @return IVA aplicado */
    public double getIVA() {
        return IVA;
    }


    /** @return código único del carrito */
    public int getCodigo() {
        return codigo;
    }

    /** @return contador actual */
    public static int getContador() {
        return contador;
    }

    /**
     * Establece el contador estático.
     * @param contador nuevo contador
     */
    public static void setContador(int contador) {
        Carrito.contador = contador;
    }

    /**
     * Modifica el código del carrito.
     * @param codigo nuevo código
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    /** @return fecha de creación */
    public GregorianCalendar getFechaCreacion() {
        return fechaCreacion;
    }

    /**
     * Modifica la fecha de creación.
     * @param fechaCreacion nueva fecha
     */
    public void setFechaCreacion(GregorianCalendar fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    /** @return usuario del carrito */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Asigna un usuario al carrito.
     * @param usuario nuevo usuario
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /** @return lista de ítems */
    public List<ItemCarrito> getItems() {
        return items;
    }

    /**
     * Asigna una lista de ítems al carrito.
     * @param items nueva lista de ítems
     */
    public void setItems(List<ItemCarrito> items) {
        this.items = items;
    }

    /**
     * Agrega un producto con una cantidad al carrito.
     * @param producto producto a agregar
     * @param cantidad cantidad del producto
     */
    public void agregarProducto(Producto producto, int cantidad) {
        items.add(new ItemCarrito(producto, cantidad));
    }

    /** @return fecha en formato Date */
    public java.util.Date getFecha() {
        return fechaCreacion.getTime();
    }

    /** @return subtotal calculado */
    public double getSubtotal() {
        return calcularSubtotal();
    }

    /** @return IVA calculado */
    public double getIva() {
        return calcularIVA();
    }

    /** @return total calculado */
    public double getTotal() {
        return calcularTotal();
    }

    /** @return lista de productos */
    public List<ItemCarrito> getProductos() {
        return items;
    }


    /**
     * Elimina un producto del carrito por su código.
     * @param codigoProducto código del producto a eliminar
     */
    public void eliminarProducto(int codigoProducto) {
        Iterator<ItemCarrito> it = items.iterator();
        while (it.hasNext()) {
            if (it.next().getProducto().getCodigo() == codigoProducto) {
                it.remove();
                break;
            }
        }
    }

    /** Vacía el carrito completamente */
    public void vaciarCarrito() {
        items.clear();
    }

    /** @return lista de ítems */
    public List<ItemCarrito> obtenerItems() {
        return items;
    }

    /** @return true si el carrito está vacío */
    public boolean estaVacio() {
        return items.isEmpty();
    }

    /** @return subtotal de los productos */
    public double calcularSubtotal() {
        double subtotal = 0;
        for (ItemCarrito item : items) {
            subtotal += item.getProducto().getPrecio() * item.getCantidad();
        }
        return subtotal;
    }

    /** @return IVA calculado */
    public double calcularIVA() {
        double subtotal = calcularSubtotal();
        return subtotal * IVA;
    }

    /** @return total (subtotal + IVA) */
    public double calcularTotal() {
        return calcularSubtotal() + calcularIVA();
    }

    /**
     * Modifica la cantidad de un producto existente.
     * @param codigoProducto código del producto
     * @param nuevaCantidad nueva cantidad
     */
    public void modificarCantidadProducto(int codigoProducto, int nuevaCantidad) {
        for (ItemCarrito item : items) {
            if (item.getProducto().getCodigo() == codigoProducto) {
                item.setCantidad(nuevaCantidad);
                return;
            }
        }
    }

    /**
     * Verifica si el carrito contiene un producto específico.
     * @param codigoProducto código del producto
     * @return true si lo contiene
     */
    public boolean contieneProducto(int codigoProducto) {
        return items.stream().anyMatch(item ->
                item.getProducto().getCodigo() == codigoProducto
        );
    }

    /**
     * Busca un ítem por código de producto.
     * @param codigo código del producto
     * @return ítem correspondiente o null si no existe
     */
    public ItemCarrito buscarItemPorCodigo(int codigo) {
        for (ItemCarrito item : items) {
            if (item.getProducto().getCodigo() == codigo) {
                return item; // Retorna el ítem si encuentra coincidencia
            }
        }
        return null;
    }

    /**
     * Muestra la información del carrito como texto.
     * @return representación del carrito
     */
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