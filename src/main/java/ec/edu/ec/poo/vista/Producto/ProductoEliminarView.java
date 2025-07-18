package ec.edu.ec.poo.vista.Producto;
import ec.edu.ec.poo.utils.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * Vista gráfica para eliminar productos del sistema.
 * Permite buscar un producto por su código y eliminarlo del sistema.
 * Integra soporte para internacionalización.
 */
public class ProductoEliminarView extends JInternalFrame {
    /** Panel principal que contiene todos los componentes visuales */
    private JPanel panelPrincipal;

    /** Campo de texto para ingresar el código del producto */
    private JTextField txtCodigo;

    /** Campo de texto para mostrar el nombre del producto */
    private JTextField txtNombre;

    /** Campo de texto para mostrar el precio del producto */
    private JTextField txtPrecio;

    /** Botón para buscar el producto por código */
    private JButton btnBuscar;

    /** Botón para eliminar el producto encontrado */
    private JButton btnEliminar;

    /** Manejador de internacionalización para cambiar dinámicamente los textos */
    private MensajeInternacionalizacionHandler mensaje;


    /**
     * Constructor que inicializa la vista para eliminar productos.
     * Configura los íconos de los botones y los textos según el idioma actual.
     * @param mensaje manejador de internacionalización que define el idioma.
     */
    public ProductoEliminarView(MensajeInternacionalizacionHandler mensaje) {
        this.mensaje = mensaje;
        initComponents();
        actualizarTextos();

        // Ícono para el botón Buscar
        URL buscarURL = ProductoEliminarView.class.getClassLoader().getResource("imagenes/buscarproducto.png");
        if (buscarURL != null) {
            ImageIcon iconBuscar = new ImageIcon(new ImageIcon(buscarURL).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
            btnBuscar.setIcon(iconBuscar);
        } else {
            System.out.println("Error al cargar buscarproductoactu.png");
        }

        // Ícono para el botón Eliminar
        URL eliminarURL = ProductoEliminarView.class.getClassLoader().getResource("imagenes/eliminarproducto.png");
        if (eliminarURL != null) {
            ImageIcon iconEliminar = new ImageIcon(new ImageIcon(eliminarURL).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
            btnEliminar.setIcon(iconEliminar);
        } else {
            System.out.println("Error al cargar eliminarcarr.png");
        }

    }

    /**
     * Inicializa los componentes gráficos básicos de la ventana.
     */
    private void initComponents() {
        setContentPane(panelPrincipal);
        setSize(500, 200);
        //setLocationRelativeTo(null);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);
    }

    /**
     * Actualiza los textos de los botones y la ventana según el idioma seleccionado.
     */
    private void actualizarTextos() {
        setTitle(mensaje.get("producto.eliminar.titulo"));

        // No pongas setText en campos de entrada
        // Ejemplo incorrecto (que debes eliminar):
        // txtCodigo.setText(mensaje.get("codigo"));

        btnBuscar.setText(mensaje.get("buscar"));
        btnEliminar.setText(mensaje.get("eliminar"));
    }

    /**
     * Cambia el idioma de la ventana y actualiza los textos mostrados.
     * @param lenguaje código de idioma (ejemplo: "es").
     * @param pais código de país (ejemplo: "EC").
     */
    public void cambiarIdioma(String lenguaje, String pais) {
        mensaje.setLenguaje(lenguaje, pais);
        actualizarTextos();
    }


    /**
     * Obtiene el campo de texto para ingresar el código del producto.
     * @return JTextField correspondiente al código.
     */
    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    /**
     * Obtiene el campo de texto para visualizar el nombre del producto.
     * @return JTextField correspondiente al nombre del producto.
     */
    public JTextField getTxtNombre() {
        return txtNombre;
    }

    /**
     * Obtiene el campo de texto para visualizar el precio del producto.
     * @return JTextField correspondiente al precio del producto.
     */
    public JTextField getTxtPrecio() {
        return txtPrecio;
    }

    /**
     * Obtiene el botón para buscar el producto por código.
     * @return JButton para la acción de buscar.
     */
    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    /**
     * Obtiene el botón para eliminar el producto.
     * @return JButton para la acción de eliminar.
     */
    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    /**
     * Obtiene el manejador de internacionalización asociado a la vista.
     * @return instancia de MensajeInternacionalizacionHandler que gestiona los textos según idioma.
     */
    public MensajeInternacionalizacionHandler getMensaje() {
        return mensaje;
    }

    /**
     * Establece el manejador de internacionalización para actualizar los textos según el idioma seleccionado.
     * @param mensaje instancia de MensajeInternacionalizacionHandler para gestionar los textos.
     */
    public void setMensaje(MensajeInternacionalizacionHandler mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * Muestra un mensaje emergente (JOptionPane) con el texto proporcionado.
     * @param mensaje el texto a mostrar en el cuadro de diálogo.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    /**
     * Muestra una ventana de confirmación para eliminar un producto.
     * @return true si el usuario confirma la eliminación; false en caso contrario.
     */
    public boolean confirmarEliminacion() {
        int opcion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de que desea eliminar este producto?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);
        return opcion == JOptionPane.YES_OPTION;
    }

    /**
     * Limpia los campos de texto del formulario, eliminando cualquier dato previo ingresado.
     * Deja en blanco el código, nombre y precio del producto.
     */
    public void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
    }

}


