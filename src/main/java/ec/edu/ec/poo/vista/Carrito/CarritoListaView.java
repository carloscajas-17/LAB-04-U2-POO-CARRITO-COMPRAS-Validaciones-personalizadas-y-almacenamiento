package ec.edu.ec.poo.vista.Carrito;

import ec.edu.ec.poo.utils.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.net.URL;

/**
 * Vista gráfica para listar carritos de compras.
 * <p>
 * Esta ventana muestra los carritos registrados filtrados por usuario,
 * permite cambiar de idioma dinámicamente y visualizar detalles de un carrito específico.
 * Incluye botones con íconos para buscar y ver detalles.
 * </p>

 */
public class CarritoListaView extends JInternalFrame {
    /** Panel principal del formulario */
    private JPanel pnlPrincipal;
    /** Panel superior que contiene el título y campo de búsqueda */
    private JPanel pnlSuperior;
    /** Panel inferior que contiene la tabla y botón detalle */
    private JPanel pnlInferior;
    /** Campo de texto para ingresar el nombre de usuario a buscar */
    private JTextField txtUsuario;
    /** Botón para buscar carritos por usuario */
    private JButton btnBuscar;
    /** Tabla que muestra la lista de carritos */
    private JTable tblCarrito;
    /** Botón para ver detalle del carrito seleccionado */
    private JButton btnDetalle;
    /** Etiqueta de título de la ventana */
    private JLabel lblTitulo;
    /** Etiqueta descriptiva del campo usuario */
    private JLabel lblUsuario;
    /** Modelo de datos para la tabla */
    private DefaultTableModel modelo;
    /** Manejador de internacionalización */
    private MensajeInternacionalizacionHandler mensaje;

    /**
     * Constructor de la vista CarritoListaView.
     * Inicializa los componentes gráficos, carga los íconos de los botones
     * y configura los textos de la interfaz según el idioma.
     *
     * @param mensaje Manejador de internacionalización para traducción de textos.
     */
    public CarritoListaView(MensajeInternacionalizacionHandler mensaje) {
        //super("Listar Carritos", true, true, false, true);
        this.mensaje = mensaje;
        initComponents();
        actualizarTextos();

        // Ícono del botón Buscar
        URL buscarURL = CarritoListaView.class.getClassLoader().getResource("imagenes/buscarusuariolistar.png");
        if (buscarURL != null) {
            ImageIcon iconBuscar = new ImageIcon(new ImageIcon(buscarURL).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
            btnBuscar.setIcon(iconBuscar);
        } else {
            System.out.println("Error al cargar buscarusuariolistar.png");
        }

        // Ícono del botón Detalle
        URL detalleURL = CarritoListaView.class.getClassLoader().getResource("imagenes/detallecarrito.png");
        if (detalleURL != null) {
            ImageIcon iconDetalle = new ImageIcon(new ImageIcon(detalleURL).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
            btnDetalle.setIcon(iconDetalle);
        } else {
            System.out.println("Error al cargar detallecarrito.png");
        }
    }

    /**
     * Inicializa los componentes básicos de la ventana
     * y configura la tabla principal.
     */
    private void initComponents() {
        setContentPane(pnlPrincipal);
        setClosable(true);
        setResizable(true);
        setSize(500, 500);

        configurarTabla();
    }

    /**
     * Configura las columnas de la tabla de carritos
     * utilizando los textos del archivo de internacionalización.
     */
    private void configurarTabla() {
        modelo = new DefaultTableModel();
        Object[] columnas = {
                mensaje.get("columna.fecha"),
                mensaje.get("columna.codigo"),
                mensaje.get("columna.usuario"),
                mensaje.get("columna.cantidad"),
                mensaje.get("columna.total")
        };
        modelo.setColumnIdentifiers(columnas);
        tblCarrito.setModel(modelo);
    }


    /**
     * Actualiza todos los textos visibles de la interfaz gráfica
     * de acuerdo al idioma seleccionado.
     */
    private void actualizarTextos() {
        setTitle(mensaje.get("carrito.lista.titulo"));
        lblTitulo.setText(mensaje.get("carrito.lista.titulo"));
        lblUsuario.setText(mensaje.get("usuario"));

        btnBuscar.setText(mensaje.get("buscar"));
        btnDetalle.setText(mensaje.get("carrito.lista.detalle")); // ✅ Esta clave SÍ está en tu .properties

    }

    /**
     * Cambia el idioma del sistema, actualiza los textos y la tabla.
     *
     * @param lenguaje Código del idioma, por ejemplo "es", "en", "fr".
     * @param pais     Código del país, por ejemplo "EC", "US", "FR".
     */
    public void cambiarIdioma(String lenguaje, String pais) {
        mensaje.setLenguaje(lenguaje, pais);
        actualizarTextos();
        configurarTabla();
    }

    /**
     * Obtiene el panel principal de la vista CarritoListaView.
     *
     * @return panel principal
     */
    public JPanel getPnlPrincipal() {
        return pnlPrincipal;
    }

    /**
     * Establece el panel principal de la vista CarritoListaView.
     *
     * @param pnlPrincipal nuevo panel principal
     */
    public void setPnlPrincipal(JPanel pnlPrincipal) {
        this.pnlPrincipal = pnlPrincipal;
    }

    /**
     * Obtiene el panel superior de la vista.
     *
     * @return panel superior
     */
    public JPanel getPnlSuperior() {
        return pnlSuperior;
    }

    /**
     * Establece el panel superior de la vista.
     *
     * @param pnlSuperior nuevo panel superior
     */
    public void setPnlSuperior(JPanel pnlSuperior) {
        this.pnlSuperior = pnlSuperior;
    }

    /**
     * Obtiene el panel inferior de la vista.
     *
     * @return panel inferior
     */
    public JPanel getPnlInferior() {
        return pnlInferior;
    }

    /**
     * Establece el panel inferior de la vista.
     *
     * @param pnlInferior nuevo panel inferior
     */
    public void setPnlInferior(JPanel pnlInferior) {
        this.pnlInferior = pnlInferior;
    }

    /**
     * Obtiene el campo de texto para ingresar el nombre de usuario.
     *
     * @return campo de texto del usuario
     */
    public JTextField getTxtUsuario() {
        return txtUsuario;
    }

    /**
     * Establece el campo de texto del usuario.
     *
     * @param txtUsuario campo de texto a asignar
     */
    public void setTxtUsuario(JTextField txtUsuario) {
        this.txtUsuario = txtUsuario;
    }

    /**
     * Obtiene el botón de búsqueda.
     *
     * @return botón buscar
     */
    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    /**
     * Establece el botón de búsqueda.
     *
     * @param btnBuscar botón buscar a asignar
     */
    public void setBtnBuscar(JButton btnBuscar) {
        this.btnBuscar = btnBuscar;
    }

    /**
     * Obtiene la tabla que muestra los carritos.
     *
     * @return tabla carrito
     */
    public JTable getTblCarrito() {
        return tblCarrito;
    }

    /**
     * Establece la tabla carrito.
     *
     * @param tblCarrito nueva tabla carrito
     */
    public void setTblCarrito(JTable tblCarrito) {
        this.tblCarrito = tblCarrito;
    }

    /**
     * Obtiene el botón para ver el detalle de un carrito.
     *
     * @return botón detalle
     */
    public JButton getBtnDetalle() {
        return btnDetalle;
    }

    /**
     * Establece el botón para ver el detalle.
     *
     * @param btnDetalle botón detalle a asignar
     */
    public void setBtnDetalle(JButton btnDetalle) {
        this.btnDetalle = btnDetalle;
    }

    /**
     * Obtiene la etiqueta del título.
     *
     * @return etiqueta título
     */
    public JLabel getLblTitulo() {
        return lblTitulo;
    }

    /**
     * Establece la etiqueta del título.
     *
     * @param lblTitulo etiqueta título
     */
    public void setLblTitulo(JLabel lblTitulo) {
        this.lblTitulo = lblTitulo;
    }

    /**
     * Obtiene la etiqueta para el usuario.
     *
     * @return etiqueta usuario
     */
    public JLabel getLblUsuario() {
        return lblUsuario;
    }

    /**
     * Establece la etiqueta del usuario.
     *
     * @param lblUsuario etiqueta usuario
     */
    public void setLblUsuario(JLabel lblUsuario) {
        this.lblUsuario = lblUsuario;
    }

    /**
     * Obtiene el manejador de mensajes para la internacionalización.
     *
     * @return manejador de mensajes
     */
    public MensajeInternacionalizacionHandler getMensaje() {
        return mensaje;
    }

    /**
     * Establece el manejador de mensajes.
     *
     * @param mensaje manejador de internacionalización
     */
    public void setMensaje(MensajeInternacionalizacionHandler mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * Obtiene el modelo de datos utilizado por la tabla carrito.
     *
     * @return modelo de la tabla
     */
    public DefaultTableModel getModelo() {
        return modelo;
    }

    /**
     * Establece el modelo de la tabla carrito.
     *
     * @param modelo modelo de tabla
     */
    public void setModelo(DefaultTableModel modelo) {
        this.modelo = modelo;
    }

    /**
     * Muestra un mensaje emergente al usuario con una clave del archivo de internacionalización.
     *
     * @param keyMensaje clave del mensaje en el archivo .properties
     */
    public void mostrarMensaje(String keyMensaje) {
        JOptionPane.showMessageDialog(this, mensaje.get(keyMensaje));
    }
}





