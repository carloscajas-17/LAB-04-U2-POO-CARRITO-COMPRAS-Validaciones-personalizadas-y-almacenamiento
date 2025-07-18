package ec.edu.ec.poo.modelo;

/**
 * Clase ItemCarrito representa un ítem dentro de un carrito de compras.
 * Contiene un producto y su cantidad correspondiente.

 */
public class ItemCarrito {
    /** Producto asociado al ítem */
    private Producto producto;


    /** Cantidad del producto en el carrito */
    private int cantidad;

    /**
     * Constructor vacío.
     */
    public ItemCarrito() {
    }

    /**
     * Constructor que inicializa el ítem con un producto y cantidad.
     * @param producto producto agregado al ítem
     * @param cantidad cantidad del producto
     */
    public ItemCarrito(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    /**
     * Asigna un nuevo producto al ítem.
     * @param producto producto a asignar
     */
    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    /**
     * Modifica la cantidad del producto.
     * @param cantidad nueva cantidad
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Obtiene el producto del ítem.
     * @return producto
     */
    public Producto getProducto() {
        return producto;
    }

    /**
     * Obtiene la cantidad del producto.
     * @return cantidad
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * Calcula el subtotal correspondiente al producto multiplicado por su cantidad.
     * @return subtotal monetario del ítem
     */
    public double getSubtotal() {
        return producto.getPrecio() * cantidad;
    }

    /**
     * Retorna una representación en texto del ítem, incluyendo producto, cantidad y subtotal.
     * @return cadena de texto representativa del ítem
     */
    @Override
    public String toString() {
        return producto.toString() + " x " + cantidad + " = $" + getSubtotal();
    }

}
