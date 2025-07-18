package ec.edu.ec.poo.modelo;


/**
 * Clase Producto representa un producto dentro del sistema de carrito de compras.
 * Cada producto tiene un código único, un nombre y un precio.
 */
public class Producto {

    /** Código único del producto */
    private int codigo;


    /** Nombre o descripción del producto */
    private String nombre;


    /** Precio unitario del producto */
    private  double precio;

    /**
     * Constructor vacío por defecto.
     */
    public Producto() {
    }


    /**
     * Constructor con parámetros para inicializar un producto con datos específicos.
     *
     * @param codigo código único del producto
     * @param nombre nombre o descripción del producto
     * @param precio precio unitario del producto
     */
    public Producto(int codigo, String nombre, double precio) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
    }

    /**
     * Obtiene el código del producto.
     *
     * @return código numérico del producto
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * Establece un nuevo código para el producto.
     *
     * @param codigo nuevo código único para el producto
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    /**
     * Obtiene el nombre del producto.
     *
     * @return nombre o descripción del producto
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Asigna un nuevo nombre o descripción al producto.
     *
     * @param nombre nombre actualizado del producto
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    /**
     * Obtiene el precio del producto.
     *
     * @return precio unitario del producto
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * Establece un nuevo precio para el producto.
     *
     * @param precio precio actualizado del producto
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }


    /**
     * Sobrescribe el método toString para mostrar la información del producto
     * de forma legible, incluyendo su código, nombre y precio.
     *
     * @return representación textual del producto
     */
    @Override
    public String toString() {
        return "Producto{" +
                "codigo=" + codigo +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                '}';
    }


}
