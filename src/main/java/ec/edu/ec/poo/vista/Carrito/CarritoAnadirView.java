package ec.edu.ec.poo.vista.Carrito;

import ec.edu.ec.poo.utils.ButtonEditor;
import ec.edu.ec.poo.utils.ButtonRenderer;
import ec.edu.ec.poo.utils.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.net.URL;

/**
 * Clase CarritoAnadirView que representa la ventana gráfica para añadir productos al carrito de compras.
 * Esta vista incluye campos para ingresar el código, nombre, precio, cantidad del producto,
 * y una tabla para mostrar productos añadidos junto con totales calculados (subtotal, IVA, total).
 * <p>
 * Integra funcionalidades de internacionalización mediante MensajeInternacionalizacionHandler.

 */
public class CarritoAnadirView extends JInternalFrame {

    /** Botón para buscar un producto por código */
    private JButton btnBuscar;

    /** Campo de texto para ingresar el código del producto */
    private JTextField txtCodigo;

    /** Campo de texto para mostrar el nombre del producto encontrado */
    private JTextField txtNombre;

    /** Campo de texto para mostrar el precio del producto encontrado */
    private JTextField txtPrecio;

    /** Botón para añadir un producto al carrito */
    private JButton btnAnadir;

    /** Tabla donde se listan los productos añadidos al carrito */
    private JTable tblProductos;

    /** Campo de texto para mostrar el subtotal acumulado del carrito */
    private JTextField txtSubtotal;

    /** Campo de texto para mostrar el IVA calculado del carrito */
    private JTextField txtIva;

    /** Campo de texto para mostrar el total final (subtotal + IVA) */
    private JTextField txtTotal;

    /** Botón para guardar el carrito creado */
    private JButton btnGuardar;

    /** Botón para limpiar los campos de la vista */
    private JButton btnLimpiar;

    /** ComboBox para seleccionar la cantidad de productos a añadir */
    private JComboBox cbxCantidad;

    /** Panel principal que contiene todos los componentes gráficos de la vista */
    private JPanel panelPrincipal;

    /** Campo de texto para mostrar el código del carrito generado */
    private JTextField txtCodigoCarrito;

    /** Campo de texto para mostrar el usuario autenticado asociado al carrito */
    private JTextField txtUsuario;

    /** Manejador de internacionalización para cambiar dinámicamente el idioma de los textos */
    private MensajeInternacionalizacionHandler mensajeInternacionalizacion;



    /**
     * Constructor de la vista CarritoAnadirView.
     * <p>
     * Inicializa y configura la interfaz gráfica del carrito de compras,
     * incluyendo la tabla de productos con acciones de modificar y eliminar,
     * así como la carga de iconos para los botones y el soporte para internacionalización.
     * <p>
     * Características destacadas:
     * - Configuración de tabla con columnas: Código, Nombre, Precio, Cantidad, Subtotal y Acciones.
     * - Configuración de botones con imágenes escaladas para "Buscar", "Añadir", "Guardar" y "Limpiar".
     * - Integración de botón editor y renderizador para acciones en la tabla.
     * - Soporte para múltiples idiomas mediante {@link MensajeInternacionalizacionHandler}.
     *
     * @param mensajeInternacionalizacion Manejador para gestionar la internacionalización de los textos en la vista.
     */
    public CarritoAnadirView(MensajeInternacionalizacionHandler mensajeInternacionalizacion){
        super("Carrito de Compras", true, true, false, true);
        this.mensajeInternacionalizacion = mensajeInternacionalizacion;
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(700, 500); // ajustado por ancho de botones

        DefaultTableModel modelo = new DefaultTableModel();
        Object[] columnas = {"Codigo", "Nombre", "Precio", "Cantidad", "Subtotal", "Acciones"};
        modelo.setColumnIdentifiers(columnas);
        tblProductos.setModel(modelo);

        // ✅ Integrar botones "Modificar" y "Eliminar"
        tblProductos.getColumn("Acciones").setCellRenderer(new ButtonRenderer());

        ButtonEditor editor = new ButtonEditor();
        editor.addActionListener(e -> {
            String comando = e.getActionCommand(); // Ej: modify:1 o delete:2
            System.out.println("Acción recibida: " + comando);

            // Aquí puedes invocar al controlador para manejar las acciones:
            // String[] partes = comando.split(":");
            // if (partes[0].equals("modify")) { ... }
        });

        tblProductos.getColumn("Acciones").setCellEditor(editor);

        cargarDatos();
        URL buscarURL = CarritoAnadirView.class.getClassLoader().getResource("imagenes/buscar.png");
        if (buscarURL != null) {
            ImageIcon iconBtnBuscar = new ImageIcon(new ImageIcon(buscarURL).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
            btnBuscar.setIcon(iconBtnBuscar);
        } else {
            System.out.println("Error al cargar buscar.png");
        }

        URL anadirURL = CarritoAnadirView.class.getClassLoader().getResource("imagenes/añadircarr.png");
        if (anadirURL != null) {
            ImageIcon iconBtnAnadir = new ImageIcon(new ImageIcon(anadirURL).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
            btnAnadir.setIcon(iconBtnAnadir);
        } else {
            System.out.println("Error al cargar añadircarr.png");
        }

        URL guardarURL = CarritoAnadirView.class.getClassLoader().getResource("imagenes/guardarcarr.png");
        if (guardarURL != null) {
            ImageIcon iconBtnGuardar = new ImageIcon(new ImageIcon(guardarURL).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
            btnGuardar.setIcon(iconBtnGuardar);
        } else {
            System.out.println("Error al cargar guardarcarr.png");
        }

        URL limpiarURL = CarritoAnadirView.class.getClassLoader().getResource("imagenes/limpiarcarr.png");
        if (limpiarURL != null) {
            ImageIcon iconBtnLimpiar = new ImageIcon(new ImageIcon(limpiarURL).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
            btnLimpiar.setIcon(iconBtnLimpiar);
        } else {
            System.out.println("Error al cargar limpiarcarr.png");
        }

    }

    /**
     * Cambia dinámicamente el idioma de la interfaz gráfica de la vista CarritoAnadirView.
     * <p>
     * Este método actualiza el idioma configurado en el manejador de internacionalización
     * y recarga los textos visibles en la interfaz según el nuevo idioma seleccionado.
     * Es utilizado para soportar múltiples idiomas durante la ejecución de la aplicación.
     *
     * @param lang    Código de lenguaje (por ejemplo, "es" para español, "en" para inglés).
     * @param country Código de país (por ejemplo, "EC" para Ecuador, "US" para Estados Unidos).
     */
    public void cambiarIdioma(String lang, String country) {
        mensajeInternacionalizacion.setLenguaje(lang, country);
        actualizarTextos();
    }


    /**
     * Actualiza dinámicamente los textos de todos los componentes de la interfaz gráfica
     * según el idioma configurado en {@link MensajeInternacionalizacionHandler}.
     * <p>
     * Este método es utilizado para cambiar los textos visibles en botones, campos de texto,
     * etiquetas y título de la ventana, facilitando la internacionalización de la vista
     * {@link CarritoAnadirView}.
     * <p>
     * Cada texto se actualiza mediante las claves definidas en los archivos <code>.properties</code>.
     */
    private void actualizarTextos() {
        setTitle(mensajeInternacionalizacion.get("carrito.anadir.titulo"));
        txtCodigo.setText(mensajeInternacionalizacion.get("codigo"));
        txtNombre.setText(mensajeInternacionalizacion.get("nombre"));
        txtPrecio.setText(mensajeInternacionalizacion.get("precio"));
        cbxCantidad.setSelectedItem(mensajeInternacionalizacion.get("cantidad"));
        txtSubtotal.setText(mensajeInternacionalizacion.get("subtotal"));
        txtIva.setText(mensajeInternacionalizacion.get("iva"));
        txtTotal.setText(mensajeInternacionalizacion.get("total"));

        btnBuscar.setText(mensajeInternacionalizacion.get("buscar"));
        btnAnadir.setText(mensajeInternacionalizacion.get("anadir"));
        btnGuardar.setText(mensajeInternacionalizacion.get("guardar"));
        btnLimpiar.setText(mensajeInternacionalizacion.get("limpiar"));
    }



    /**
     * Carga dinámicamente los valores numéricos del 1 al 20 en el componente
     * {@link JComboBox} {@code cbxCantidad}, que permite al usuario seleccionar
     * la cantidad de productos a añadir al carrito.
     * <p>
     * Este método limpia cualquier dato previo del {@code cbxCantidad} antes de insertar
     * los nuevos valores, asegurando que siempre se reinicie correctamente.
     */
    private void cargarDatos(){
        cbxCantidad.removeAllItems();
        for(int i = 0; i < 20; i++){
            cbxCantidad.addItem(String.valueOf(i + 1));
        }
    }





    /**
     * Obtiene el editor de botones asociado a la columna "Acciones" de la tabla.
     * @return instancia de {@link ButtonEditor} para la edición de celdas con botones.
     */
    public ButtonEditor getButtonEditor() {
        return (ButtonEditor) tblProductos.getColumn("Acciones").getCellEditor();
    }



    /** @return botón para buscar productos. */
    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    /** @return campo de texto para ingresar el código del producto. */
    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    /** @return campo de texto para mostrar el nombre del producto. */
    public JTextField getTxtNombre() {
        return txtNombre;
    }

    /** @return campo de texto para mostrar el precio del producto. */
    public JTextField getTxtPrecio() {
        return txtPrecio;
    }

    /** @return botón para añadir productos a la tabla. */
    public JButton getBtnAnadir() {
        return btnAnadir;
    }

    /** @return tabla que muestra los productos añadidos al carrito. */
    public JTable getTblProductos() {
        return tblProductos;
    }

    /** @return campo de texto para mostrar el subtotal del carrito. */
    public JTextField getTxtSubtotal() {
        return txtSubtotal;
    }

    /** @return campo de texto para mostrar el IVA calculado. */
    public JTextField getTxtIva() {
        return txtIva;
    }

    /** @return campo de texto para mostrar el total a pagar. */
    public JTextField getTxtTotal() {
        return txtTotal;
    }

    /** @return botón para guardar el carrito. */
    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    /** @return botón para limpiar todos los datos del carrito. */
    public JButton getBtnLimpiar() {
        return btnLimpiar;
    }

    /** @return combo box que permite seleccionar la cantidad del producto. */
    public JComboBox getCbxCantidad() {
        return cbxCantidad;
    }

    /** @return panel principal de la interfaz gráfica. */
    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    /** @return campo de texto para mostrar el código del carrito generado. */
    public JTextField getTxtCodigoCarrito() {
        return txtCodigoCarrito;
    }

    /** @return campo de texto para mostrar el nombre del usuario actual. */
    public JTextField getTxtUsuario() {
        return txtUsuario;
    }

    /** @return manejador de internacionalización para traducción de textos. */
    public MensajeInternacionalizacionHandler getMensajeInternacionalizacion() {
        return mensajeInternacionalizacion;
    }

    /**
     * Muestra un mensaje emergente al usuario mediante un cuadro de diálogo.
     * @param mensaje el texto del mensaje que se mostrará.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}
