package ec.edu.ec.poo.vista.Carrito;

import ec.edu.ec.poo.utils.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.net.URL;

/**
 * Clase CarritoDetalleView representa una ventana modal de detalle de un carrito de compras,
 * mostrando información del usuario, fecha, subtotal, IVA, total y la lista de productos del carrito.
 * Además, permite cambiar idioma dinámicamente usando internacionalización.
 *
 */
public class CarritoDetalleView extends JDialog {
    /** Panel principal que contiene toda la estructura del detalle */
    private JPanel pnlPrincipal;

    /** Panel superior que contiene información básica como usuario, fecha */
    private JPanel pnlSuperior;

    /** Panel inferior que contiene botones y totales */
    private JPanel pnlInferior;

    /** Campo para mostrar el nombre del usuario asociado al carrito */
    private JTextField txtUsuario;

    /** Campo para mostrar la fecha de creación del carrito */
    private JTextField txtFecha;

    /** Campo para mostrar el subtotal del carrito */
    private JTextField txtSubtotal;

    /** Campo para mostrar el valor del IVA */
    private JTextField txtIva;

    /** Campo para mostrar el total final a pagar */
    private JTextField txtTotal;

    /** Tabla que muestra el detalle de productos del carrito */
    private JTable tblCarrito;

    /** Etiqueta para mostrar texto asociado al usuario */
    private JLabel lblUsuario;

    /** Etiqueta para mostrar texto asociado a la fecha */
    private JLabel lblFecha;

    /** Etiqueta para mostrar texto asociado al subtotal */
    private JLabel lblSubtotal;

    /** Etiqueta para mostrar texto asociado al IVA */
    private JLabel lblIva;

    /** Etiqueta para mostrar texto asociado al total */
    private JLabel lblTotal;

    /** Botón para cerrar la ventana de detalle y volver */
    private JButton btnVolver;

    /** Campo para mostrar el código del carrito */
    private JTextField txtCodigo;

    /** Etiqueta para mostrar el texto 'Código' */
    private JLabel lblCodigo;

    /** Modelo de tabla para gestionar dinámicamente los productos mostrados */
    private DefaultTableModel modelo;

    /** Manejador para controlar la internacionalización de la vista */
    private MensajeInternacionalizacionHandler mensaje;

    /**
     * Constructor de la vista CarritoDetalleView.
     * Inicializa la ventana modal para mostrar el detalle del carrito de compras.
     * Configura la internacionalización con el idioma actual, carga los textos,
     * y asigna el ícono correspondiente al botón "Volver".
     *
     * @param mensaje instancia de {@link MensajeInternacionalizacionHandler} utilizada para la traducción dinámica de textos.
     */
    public CarritoDetalleView(MensajeInternacionalizacionHandler mensaje) {
        this.mensaje = mensaje;
        initComponents();
        actualizarTextos();

        // Ícono del botón Volver
        URL volverURL = CarritoDetalleView.class.getClassLoader().getResource("imagenes/volverdetaller.png");
        if (volverURL != null) {
            ImageIcon iconVolver = new ImageIcon(new ImageIcon(volverURL).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
            btnVolver.setIcon(iconVolver);
        } else {
            System.out.println("Error al cargar volverdetaller.png");
        }
    }

    /**
     * Método que inicializa los componentes gráficos principales de la ventana CarritoDetalleView.
     *
     * Acciones realizadas:
     * <ul>
     *     <li>Establece el panel principal como contenido principal del JDialog.</li>
     *     <li>Configura el tamaño de la ventana a 600x400 píxeles.</li>
     *     <li>Define la ventana como modal, lo que bloquea otras ventanas hasta que se cierre.</li>
     *     <li>Inicializa y configura el modelo de la tabla llamando a {@code configurarTabla()}.</li>
     * </ul>
     */
    private void initComponents() {
        setContentPane(pnlPrincipal);
        setSize(600, 400);
        setModal(true);

        configurarTabla();
    }

    /**
     * Configura el modelo de la tabla {@code tblCarrito} con las columnas correspondientes,
     * utilizando los textos internacionalizados mediante {@code mensaje}.
     *
     * Acciones realizadas:
     * <ul>
     *     <li>Crea un nuevo {@code DefaultTableModel} vacío.</li>
     *     <li>Define los encabezados de las columnas con soporte para internacionalización.</li>
     *     <li>Asigna el modelo de datos a la tabla {@code tblCarrito} para mostrar la información correctamente.</li>
     * </ul>
     *
     * Las columnas incluyen:
     * <ul>
     *     <li>Código del producto</li>
     *     <li>Nombre</li>
     *     <li>Precio unitario</li>
     *     <li>Cantidad</li>
     *     <li>Subtotal</li>
     *     <li>IVA</li>
     *     <li>Total</li>
     * </ul>
     */
    private void configurarTabla() {
        modelo = new DefaultTableModel();
        Object[] columnas = {
                mensaje.get("columna.codigo"),
                mensaje.get("columna.nombre"),
                mensaje.get("columna.precio"),
                mensaje.get("columna.cantidad"),
                mensaje.get("columna.subtotal"),
                mensaje.get("columna.iva"),
                mensaje.get("columna.total")
        };
        modelo.setColumnIdentifiers(columnas);
        tblCarrito.setModel(modelo);
    }


    /**
     * Actualiza todos los textos visibles en la vista {@code CarritoDetalleView}
     * según el idioma seleccionado en el manejador de internacionalización {@code mensaje}.
     *
     * Acciones realizadas:
     * <ul>
     *     <li>Actualiza el título de la ventana con la clave {@code carrito.detalle.titulo}.</li>
     *     <li>Actualiza las etiquetas de los campos: Código, Usuario, Fecha, Subtotal, IVA y Total.</li>
     *     <li>Actualiza el texto del botón "Volver" si no es {@code null}.</li>
     * </ul>
     *
     * Este método es útil cuando se cambia el idioma durante la ejecución para reflejar inmediatamente los cambios en la interfaz gráfica.
     */
    private void actualizarTextos() {
        setTitle(mensaje.get("carrito.detalle.titulo"));
        lblCodigo.setText(mensaje.get("codigo"));
        lblUsuario.setText(mensaje.get("usuario"));
        lblFecha.setText(mensaje.get("fecha"));
        lblSubtotal.setText(mensaje.get("subtotal"));
        lblIva.setText(mensaje.get("iva"));
        lblTotal.setText(mensaje.get("total"));

        if (btnVolver != null) {
            btnVolver.setText(mensaje.get("carrito.detalle.volver"));
        }
    }


    /**
     * Cambia dinámicamente el idioma de la interfaz gráfica del {@code CarritoDetalleView}.
     *
     * <p>Este método realiza las siguientes acciones:</p>
     * <ul>
     *     <li>Actualiza el idioma del manejador de internacionalización {@code mensaje} según el lenguaje y país proporcionados.</li>
     *     <li>Refresca los textos visibles de la interfaz mediante {@code actualizarTextos()}.</li>
     *     <li>Vuelve a configurar las cabeceras de la tabla mediante {@code configurarTabla()} para reflejar el idioma seleccionado.</li>
     * </ul>
     *
     * @param lenguaje código del idioma (por ejemplo, "es" para español, "en" para inglés)
     * @param pais código del país (por ejemplo, "EC" para Ecuador, "US" para Estados Unidos)
     */
    public void cambiarIdioma(String lenguaje, String pais) {
        mensaje.setLenguaje(lenguaje, pais);
        actualizarTextos();
        configurarTabla();
    }


    /**
     * Obtiene el panel principal de la vista.
     * @return {@code JPanel} principal que contiene toda la interfaz.
     */
    public JPanel getPnlPrincipal() {
        return pnlPrincipal;
    }


    /**
     * Establece el panel principal de la vista.
     * @param pnlPrincipal nuevo panel principal
     */
    public void setPnlPrincipal(JPanel pnlPrincipal) {
        this.pnlPrincipal = pnlPrincipal;
    }

    /**
     * Obtiene el panel superior de la vista.
     * @return {@code JPanel} superior
     */
    public JPanel getPnlSuperior() {
        return pnlSuperior;
    }

    /**
     * Establece el panel superior de la vista.
     * @param pnlSuperior nuevo panel superior
     */
    public void setPnlSuperior(JPanel pnlSuperior) {
        this.pnlSuperior = pnlSuperior;
    }

    /**
     * Obtiene el panel inferior de la vista.
     * @return {@code JPanel} inferior
     */
    public JPanel getPnlInferior() {
        return pnlInferior;
    }

    /**
     * Obtiene el campo de texto para mostrar el usuario asociado al carrito.
     * @return campo de texto del usuario
     */
    public JTextField getTxtUsuario() {
        return txtUsuario;
    }

    /**
     * Obtiene el campo de texto que muestra la fecha del carrito.
     * @return campo de texto con la fecha
     */
    public JTextField getTxtFecha() {
        return txtFecha;
    }

    /**
     * Obtiene el campo de texto que muestra el subtotal del carrito.
     * @return campo de texto subtotal
     */
    public JTextField getTxtSubtotal() {
        return txtSubtotal;
    }

    /**
     * Obtiene el campo de texto que muestra el IVA del carrito.
     * @return campo de texto IVA
     */
    public JTextField getTxtIva() {
        return txtIva;
    }

    /**
     * Obtiene el campo de texto que muestra el total del carrito.
     * @return campo de texto total
     */
    public JTextField getTxtTotal() {
        return txtTotal;
    }

    /**
     * Obtiene la tabla que muestra los productos del carrito.
     * @return tabla de productos del carrito
     */
    public JTable getTblCarrito() {
        return tblCarrito;
    }


    /**
     * Obtiene la etiqueta que muestra el texto 'Usuario'.
     * @return {@code JLabel} de usuario
     */
    public JLabel getLblUsuario() {
        return lblUsuario;
    }

    /**
     * Obtiene la etiqueta de 'Fecha'.
     * @return {@code JLabel} fecha
     */
    public JLabel getLblFecha() {
        return lblFecha;
    }

    /**
     * Obtiene la etiqueta de 'Subtotal'.
     * @return {@code JLabel} subtotal
     */
    public JLabel getLblSubtotal() {
        return lblSubtotal;
    }

    /**
     * Obtiene la etiqueta de 'IVA'.
     * @return {@code JLabel} IVA
     */
    public JLabel getLblIva() {
        return lblIva;
    }

    /**
     * Obtiene la etiqueta de 'Total'.
     * @return {@code JLabel} total
     */
    public JLabel getLblTotal() {
        return lblTotal;
    }

    /**
     * Obtiene el campo de texto para el código del carrito.
     * @return campo de texto código
     */
    public JTextField getTxtCodigo() {
        return txtCodigo;
    }


    /**
     * Obtiene la etiqueta que muestra 'Código'.
     * @return etiqueta de código
     */
    public JLabel getLblCodigo() {
        return lblCodigo;
    }

    /**
     * Obtiene el modelo de tabla utilizado para listar los productos.
     * @return {@code DefaultTableModel} de la tabla
     */
    public DefaultTableModel getModelo() {
        return modelo;
    }


    /**
     * Obtiene el botón 'Volver'.
     * @return botón para regresar a la vista anterior
     */
    public JButton getBtnVolver() {
        return btnVolver;
    }


    /**
     * Obtiene el manejador de internacionalización.
     * @return objeto {@code MensajeInternacionalizacionHandler} actual
     */
    public MensajeInternacionalizacionHandler getMensaje() {
        return mensaje;
    }

    /**
     * Establece el manejador de internacionalización.
     * @param mensaje manejador {@code MensajeInternacionalizacionHandler}
     */
    public void setMensaje(MensajeInternacionalizacionHandler mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * Muestra un mensaje emergente traducido según la clave proporcionada.
     * @param keyMensaje clave del mensaje a mostrar
     */
    public void mostrarMensaje(String keyMensaje) {
        JOptionPane.showMessageDialog(this, mensaje.get(keyMensaje));
    }
}