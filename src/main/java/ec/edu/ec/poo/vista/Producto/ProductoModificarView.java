package ec.edu.ec.poo.vista.Producto;
import ec.edu.ec.poo.utils.MensajeInternacionalizacionHandler;
import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * Clase ProductoModificarView representa la ventana gráfica para modificar
 * un producto existente en el sistema. Permite buscar un producto por código
 * y actualizar sus datos (nombre y precio).
 */
public class ProductoModificarView extends JInternalFrame {
    /** Panel principal que contiene todos los componentes visuales */
    private JPanel panelPrincipal;

    /** Campo de texto para ingresar el código del producto a buscar */
    private JTextField txtCodigo;

    /** Campo de texto para mostrar o modificar el nombre del producto */
    private JTextField txtNombre;

    /** Campo de texto para mostrar o modificar el precio del producto */
    private JTextField txtPrecio;

    /** Botón para buscar un producto según su código */
    private JButton btnBuscar;

    /** Botón para modificar la información del producto seleccionado */
    private JButton btnModificar;

    /** Manejador de internacionalización para mostrar los textos según el idioma seleccionado */
    private MensajeInternacionalizacionHandler mensaje;

    /**
     * Constructor que inicializa la interfaz gráfica para modificar productos
     * con soporte de internacionalización y carga de íconos.
     * @param mensaje manejador de internacionalización
     */
    public ProductoModificarView(MensajeInternacionalizacionHandler mensaje) {
        this.mensaje = mensaje;
        initComponents();
        actualizarTextos();

        // Ícono para el botón Buscar
        URL buscarURL = ProductoModificarView.class.getClassLoader().getResource("imagenes/buscarproducto.png");
        if (buscarURL != null) {
            ImageIcon iconBuscar = new ImageIcon(new ImageIcon(buscarURL).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
            btnBuscar.setIcon(iconBuscar);
        } else {
            System.out.println("Error al cargar buscarproducto.png");
        }

         // Ícono para el botón Modificar
        URL modificarURL = ProductoModificarView.class.getClassLoader().getResource("imagenes/modificarproduc.png");
        if (modificarURL != null) {
            ImageIcon iconModificar = new ImageIcon(new ImageIcon(modificarURL).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
            btnModificar.setIcon(iconModificar);
        } else {
            System.out.println("Error al cargar modificarproduc.png");
        }

    }


    /**
     * Inicializa los componentes de la interfaz gráfica.
     */
    private void initComponents() {
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 200);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        //setLocationRelativeTo(null);
    }
    /**
     * Actualiza los textos de la ventana de acuerdo al idioma seleccionado
     * utilizando las claves definidas en los archivos .properties.
     */
    private void actualizarTextos() {
        setTitle(mensaje.get("producto.modificar.titulo"));
        txtCodigo.setText(mensaje.get("codigo"));
        txtNombre.setText(mensaje.get("nombre"));
        txtPrecio.setText(mensaje.get("precio"));

        btnBuscar.setText(mensaje.get("buscar")); // ✅ SOLO buscar
        btnModificar.setText(mensaje.get("actualizar"));
    }

    /**
     * Cambia dinámicamente el idioma de la interfaz gráfica.
     * @param lenguaje idioma (ejemplo: "es", "en")
     * @param pais país asociado al idioma (ejemplo: "EC", "US")
     */
    public void cambiarIdioma(String lenguaje, String pais) {
        mensaje.setLenguaje(lenguaje, pais);
        actualizarTextos();
    }

    /**
     * Obtiene el campo de texto donde se ingresa el código del producto.
     * @return JTextField para el código del producto
     */
    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    /**
     * Obtiene el campo de texto donde se ingresa o muestra el nombre del producto.
     * @return JTextField para el nombre del producto
     */
    public JTextField getTxtNombre() {
        return txtNombre;
    }

    /**
     * Obtiene el campo de texto donde se ingresa o muestra el precio del producto.
     * @return JTextField para el precio del producto
     */
    public JTextField getTxtPrecio() {
        return txtPrecio;
    }

    /**
     * Obtiene el botón encargado de buscar un producto por su código.
     * @return JButton para la acción de buscar
     */
    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    /**
     * Obtiene el botón encargado de modificar la información del producto.
     * @return JButton para la acción de modificar
     */
    public JButton getBtnModificar() {
        return btnModificar;
    }

    /**
     * Muestra un cuadro de diálogo con el mensaje recibido como parámetro.
     * @param mensaje texto a mostrar en el cuadro de diálogo
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    /**
     * Limpia todos los campos de texto del formulario, dejándolos en blanco.
     */
    public void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
    }

}
