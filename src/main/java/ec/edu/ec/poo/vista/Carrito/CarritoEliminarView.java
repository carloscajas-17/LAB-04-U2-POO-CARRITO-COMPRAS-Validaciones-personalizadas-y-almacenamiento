package ec.edu.ec.poo.vista.Carrito;


import ec.edu.ec.poo.utils.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.net.URL;

/**
 * Clase {@code CarritoEliminarView} representa la ventana gráfica para eliminar un carrito.
 * Incluye campos para mostrar los productos del carrito, subtotal, IVA y total, así como botones
 * para buscar y eliminar carritos. Integra internacionalización dinámica.
 */
public class CarritoEliminarView extends JInternalFrame {
    /** Panel principal que contiene todos los elementos gráficos */
    private JPanel pnlPrincipal;

    /** Panel superior de la ventana */
    private JPanel pnlSuperior;

    /** Panel central donde se muestra la tabla de productos */
    private JPanel pnlCentral;

    /** Campo de texto para ingresar el código del carrito a buscar o eliminar */
    private JTextField txtCodigo;

    /** Botón para eliminar el carrito seleccionado */
    private JButton btnEliminar;

    /** Botón para buscar un carrito mediante su código */
    private JButton btnBuscar;

    /** Tabla para mostrar los productos contenidos en el carrito */
    private JTable tblProducto;

    /** Campo de texto para mostrar el subtotal del carrito */
    private JTextField txtSubtotal;

    /** Campo de texto para mostrar el IVA del carrito */
    private JTextField txtIva;

    /** Campo de texto para mostrar el total del carrito */
    private JTextField txtTotal;

    /** Etiqueta de título principal de la ventana */
    private JLabel lblTitulo;

    /** Etiqueta para el campo de código */
    private JLabel lblCodigo;

    /** Etiqueta para el subtotal */
    private JLabel lblSubtotal;

    /** Etiqueta para el IVA */
    private JLabel lblIva;

    /** Etiqueta para el total */
    private JLabel lblTotal;

    /** Manejador de internacionalización que permite cambiar dinámicamente los textos */
    private MensajeInternacionalizacionHandler mensaje;

    /**
     * Constructor de la vista {@code CarritoEliminarView}.
     * Inicializa los componentes gráficos, establece la internacionalización y configura los íconos
     * para los botones de buscar y eliminar carritos.
     *
     * @param mensaje manejador de internacionalización que permite cambiar los textos de la interfaz
     */
    public CarritoEliminarView(MensajeInternacionalizacionHandler mensaje) {
        this.mensaje = mensaje;
        initComponents();
        actualizarTextos();

        // Ícono del botón Buscar
        URL buscarURL = CarritoEliminarView.class.getClassLoader().getResource("imagenes/buscar.png");
        if (buscarURL != null) {
            ImageIcon iconBuscar = new ImageIcon(new ImageIcon(buscarURL).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
            btnBuscar.setIcon(iconBuscar);
        } else {
            System.out.println("Error al cargar buscar.png");
        }

        // Ícono del botón Eliminar
        URL eliminarURL = CarritoEliminarView.class.getClassLoader().getResource("imagenes/eliminarcarr.png");
        if (eliminarURL != null) {
            ImageIcon iconEliminar = new ImageIcon(new ImageIcon(eliminarURL).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
            btnEliminar.setIcon(iconEliminar);
        } else {
            System.out.println("Error al cargar eliminarcarr.png");
        }
    }

    /**
     * Actualiza dinámicamente los textos de la interfaz gráfica {@code CarritoEliminarView}
     * utilizando las claves definidas en el archivo de internacionalización.
     * <p>
     * Este método es llamado cuando se cambia el idioma o cuando se inicializa la vista,
     * garantizando que todos los componentes muestren los textos correctos según el idioma seleccionado.
     * <ul>
     *     <li>Actualiza el título de la ventana y el label principal.</li>
     *     <li>Actualiza los labels para el código, subtotal, IVA y total.</li>
     *     <li>Actualiza los textos de los botones Buscar y Eliminar.</li>
     * </ul>
     */
    private void actualizarTextos() {
        setTitle(mensaje.get("carrito.eliminar.titulo"));
        lblTitulo.setText(mensaje.get("carrito.eliminar.titulo"));

        lblCodigo.setText(mensaje.get("codigo"));
        lblSubtotal.setText(mensaje.get("subtotal"));
        lblIva.setText(mensaje.get("iva"));
        lblTotal.setText(mensaje.get("total"));

        btnBuscar.setText(mensaje.get("buscar"));
        btnEliminar.setText(mensaje.get("eliminar"));
    }

    /**
     * Inicializa y configura los componentes principales de la interfaz gráfica {@code CarritoEliminarView}.
     * <p>
     * Este método establece el panel principal como contenido de la ventana,
     * permite que la ventana sea cerrable y redimensionable, define un tamaño predeterminado
     * y realiza la configuración inicial de la tabla que mostrará los productos.
     * </p>
     */
    private void initComponents() {
        setContentPane(pnlPrincipal);
        setClosable(true);
        setResizable(true);
        setSize(500, 500);

        configurarTabla();
    }

    /**
     * Configura la tabla {@code tblProducto} con las columnas necesarias para visualizar los productos
     * dentro del carrito que será eliminado. Las columnas incluyen código, nombre, precio, cantidad y subtotal.
     * <p>
     * Los nombres de las columnas se obtienen dinámicamente desde el archivo de internacionalización
     * mediante la clase {@code MensajeInternacionalizacionHandler}.
     * </p>
     */
    private void configurarTabla() {
        DefaultTableModel modelo = new DefaultTableModel();
        Object[] columnas = {
                mensaje.get("columna.codigo"),
                mensaje.get("columna.nombre"),
                mensaje.get("columna.precio"),
                mensaje.get("columna.cantidad"),
                mensaje.get("columna.subtotal")
        };
        modelo.setColumnIdentifiers(columnas);
        tblProducto.setModel(modelo);
    }

    /**
     * Cambia dinámicamente el idioma de la interfaz gráfica {@code CarritoEliminarView} según los parámetros proporcionados.
     * <p>
     * Este método actualiza los textos visibles en la ventana, incluidas las etiquetas, botones y columnas de la tabla,
     * utilizando las traducciones definidas en los archivos de internacionalización.
     * </p>
     *
     * @param lenguaje código del idioma (por ejemplo: "es" para español, "en" para inglés).
     * @param pais     código del país o región (por ejemplo: "EC" para Ecuador, "US" para Estados Unidos).
     */
    public void cambiarIdioma(String lenguaje, String pais) {
        mensaje.setLenguaje(lenguaje, pais);
        actualizarTextos();
        configurarTabla();
    }

    /**
     * Obtiene el panel principal de la vista.
     * @return panel principal.
     */
    public JPanel getPnlPrincipal() { return pnlPrincipal; }

    /**
     * Establece el panel principal de la vista.
     * @param pnlPrincipal panel principal a asignar.
     */
    public void setPnlPrincipal(JPanel pnlPrincipal) { this.pnlPrincipal = pnlPrincipal; }

    /**
     * Obtiene el panel superior de la vista.
     * @return panel superior.
     */
    public JPanel getPnlSuperior() { return pnlSuperior; }

    /**
     * Establece el panel superior de la vista.
     * @param pnlSuperior panel superior a asignar.
     */
    public void setPnlSuperior(JPanel pnlSuperior) { this.pnlSuperior = pnlSuperior; }

    /**
     * Obtiene el panel central de la vista.
     * @return panel central.
     */
    public JPanel getPnlCentral() { return pnlCentral; }

    /**
     * Establece el panel central de la vista.
     * @param pnlCentral panel central a asignar.
     */
    public void setPnlCentral(JPanel pnlCentral) { this.pnlCentral = pnlCentral; }

    /**
     * Obtiene la etiqueta del título.
     * @return etiqueta del título.
     */
    public JLabel getLblTitulo() { return lblTitulo; }

    /**
     * Establece la etiqueta del título.
     * @param lblTitulo etiqueta a asignar.
     */
    public void setLblTitulo(JLabel lblTitulo) { this.lblTitulo = lblTitulo; }

    /**
     * Obtiene la etiqueta para el código.
     * @return etiqueta del código.
     */
    public JLabel getLblCodigo() { return lblCodigo; }

    /**
     * Establece la etiqueta para el código.
     * @param lblCodigo etiqueta a asignar.
     */
    public void setLblCodigo(JLabel lblCodigo) { this.lblCodigo = lblCodigo; }

    /**
     * Obtiene la etiqueta para el subtotal.
     * @return etiqueta del subtotal.
     */
    public JLabel getLblSubtotal() { return lblSubtotal; }

    /**
     * Establece la etiqueta para el subtotal.
     * @param lblSubtotal etiqueta a asignar.
     */
    public void setLblSubtotal(JLabel lblSubtotal) { this.lblSubtotal = lblSubtotal; }

    /**
     * Obtiene la etiqueta para el IVA.
     * @return etiqueta del IVA.
     */
    public JLabel getLblIva() { return lblIva; }

    /**
     * Establece la etiqueta para el IVA.
     * @param lblIva etiqueta a asignar.
     */
    public void setLblIva(JLabel lblIva) { this.lblIva = lblIva; }

    /**
     * Obtiene la etiqueta para el total.
     * @return etiqueta del total.
     */
    public JLabel getLblTotal() { return lblTotal; }

    /**
     * Establece la etiqueta para el total.
     * @param lblTotal etiqueta a asignar.
     */
    public void setLblTotal(JLabel lblTotal) { this.lblTotal = lblTotal; }

    /**
     * Obtiene el campo de texto para el código.
     * @return campo de texto del código.
     */
    public JTextField getTxtCodigo() { return txtCodigo; }

    /**
     * Establece el campo de texto para el código.
     * @param txtCodigo campo de texto a asignar.
     */
    public void setTxtCodigo(JTextField txtCodigo) { this.txtCodigo = txtCodigo; }

    /**
     * Obtiene el campo de texto para el subtotal.
     * @return campo de texto del subtotal.
     */
    public JTextField getTxtSubtotal() { return txtSubtotal; }

    /**
     * Establece el campo de texto para el subtotal.
     * @param txtSubtotal campo de texto a asignar.
     */
    public void setTxtSubtotal(JTextField txtSubtotal) { this.txtSubtotal = txtSubtotal; }

    /**
     * Obtiene el campo de texto para el IVA.
     * @return campo de texto del IVA.
     */
    public JTextField getTxtIva() { return txtIva; }

    /**
     * Establece el campo de texto para el IVA.
     * @param txtIva campo de texto a asignar.
     */
    public void setTxtIva(JTextField txtIva) { this.txtIva = txtIva; }

    /**
     * Obtiene el campo de texto para el total.
     * @return campo de texto del total.
     */
    public JTextField getTxtTotal() { return txtTotal; }

    /**
     * Establece el campo de texto para el total.
     * @param txtTotal campo de texto a asignar.
     */
    public void setTxtTotal(JTextField txtTotal) { this.txtTotal = txtTotal; }

    /**
     * Obtiene el botón para eliminar.
     * @return botón eliminar.
     */
    public JButton getBtnEliminar() { return btnEliminar; }

    /**
     * Establece el botón para eliminar.
     * @param btnEliminar botón a asignar.
     */
    public void setBtnEliminar(JButton btnEliminar) { this.btnEliminar = btnEliminar; }

    /**
     * Obtiene el botón para buscar.
     * @return botón buscar.
     */
    public JButton getBtnBuscar() { return btnBuscar; }

    /**
     * Establece el botón para buscar.
     * @param btnBuscar botón a asignar.
     */
    public void setBtnBuscar(JButton btnBuscar) { this.btnBuscar = btnBuscar; }

    /**
     * Obtiene la tabla donde se muestran los productos.
     * @return tabla de productos.
     */
    public JTable getTblProducto() { return tblProducto; }

    /**
     * Establece la tabla de productos.
     * @param tblProducto tabla a asignar.
     */
    public void setTblProducto(JTable tblProducto) { this.tblProducto = tblProducto; }

    /**
     * Muestra un mensaje mediante un cuadro de diálogo utilizando la clave internacionalizada.
     * @param keyMensaje clave del mensaje internacionalizado.
     */
    public void mostrarMensaje(String keyMensaje) {
        JOptionPane.showMessageDialog(this, mensaje.get(keyMensaje));
    }
}
