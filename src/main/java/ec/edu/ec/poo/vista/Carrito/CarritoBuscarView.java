package ec.edu.ec.poo.vista.Carrito;


import ec.edu.ec.poo.utils.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.net.URL;

/**
 * Vista gráfica para la búsqueda de carritos en el sistema.
 * Permite consultar el carrito mediante su código y visualizar el subtotal, IVA y total.
 * Además, permite mostrar los productos asociados dentro de una tabla.
 *
 * La clase gestiona la internacionalización mediante un manejador de mensajes
 * y personaliza la interfaz según el idioma seleccionado.
 */
public class CarritoBuscarView extends JInternalFrame {
    /** Panel principal que contiene toda la interfaz gráfica */
    private JPanel pnlPrincipal;

    /** Panel superior donde se encuentran campos de búsqueda */
    private JPanel pnlSuperior;

    /** Panel central donde se muestra la tabla de productos */
    private JPanel pnlCentral;

    /** Campo de texto para ingresar el código del carrito */
    private JTextField txtCodigo;

    /** Botón para ejecutar la búsqueda del carrito */
    private JButton btnBuscar;

    /** Campo de texto para mostrar el subtotal del carrito */
    private JTextField txtSubtotal;

    /** Campo de texto para mostrar el IVA del carrito */
    private JTextField txtIva;

    /** Campo de texto para mostrar el total del carrito */
    private JTextField txtTotal;

    /** Botón para limpiar los campos de la vista */
    private JButton btnLimpiar;

    /** Tabla para listar los productos asociados al carrito */
    private JTable tblProducto;

    /** Etiqueta para el título de la ventana */
    private JLabel lblTitulo;

    /** Etiqueta para el campo código */
    private JLabel lblCodigo;

    /** Etiqueta para mostrar el subtotal */
    private JLabel lblSubtotal;

    /** Etiqueta para mostrar el IVA */
    private JLabel lblIva;

    /** Etiqueta para mostrar el total */
    private JLabel lblTotal;

    /** Manejador para la internacionalización de textos */
    private MensajeInternacionalizacionHandler mensaje;

    /**
     * Constructor principal para inicializar la vista CarritoBuscarView.
     *
     * Funcionalidades del constructor:
     * - Inicializa los componentes gráficos.
     * - Configura los textos de la interfaz usando internacionalización.
     * - Carga íconos gráficos para los botones buscar y limpiar.
     *
     * @param mensaje manejador de internacionalización utilizado para adaptar textos según idioma.
     */
    public CarritoBuscarView(MensajeInternacionalizacionHandler mensaje) {
        this.mensaje = mensaje;
        initComponents();
        actualizarTextos();
        // ÍCONOS de los botones
        URL buscarURL = CarritoBuscarView.class.getClassLoader().getResource("imagenes/buscar.png");
        if (buscarURL != null) {
            ImageIcon iconBuscar = new ImageIcon(new ImageIcon(buscarURL).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
            btnBuscar.setIcon(iconBuscar);
        } else {
            System.out.println("Error al cargar buscar.png");
        }

        URL limpiarURL = CarritoBuscarView.class.getClassLoader().getResource("imagenes/limpiarcarr.png");
        if (limpiarURL != null) {
            ImageIcon iconLimpiar = new ImageIcon(new ImageIcon(limpiarURL).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
            btnLimpiar.setIcon(iconLimpiar);
        } else {
            System.out.println("Error al cargar limpiarcarr.png");
        }

    }

    /**
     * Inicializa los componentes básicos de la vista CarritoBuscarView.
     *
     * Configura:
     * - El panel principal de contenido.
     * - Permite que la ventana sea cerrable y redimensionable.
     * - Define el tamaño inicial de la ventana.
     * - Configura la tabla de productos mediante {@link #configurarTabla()}.
     */
    private void initComponents() {
        setContentPane(pnlPrincipal);
        setClosable(true);
        setResizable(true);
        setSize(500, 500);

        configurarTabla();
    }


    /**
     * Configura la tabla {@link #tblProducto} estableciendo sus columnas principales
     * con títulos obtenidos desde el sistema de internacionalización.
     *
     * Las columnas configuradas incluyen:
     * - Código del producto.
     * - Nombre del producto.
     * - Precio unitario.
     * - Cantidad seleccionada.
     * - Subtotal por ítem.
     * - IVA calculado.
     * - Total por ítem.
     *
     * Este método inicializa un {@link DefaultTableModel} vacío y lo asigna a la tabla
     * para permitir futuras operaciones de carga y visualización de datos.
     */
    private void configurarTabla() {
        DefaultTableModel modelo = new DefaultTableModel();
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
        tblProducto.setModel(modelo);
    }


    /**
     * Cambia dinámicamente el idioma de la interfaz gráfica de la vista {@link CarritoBuscarView}.
     * <p>
     * Este método actualiza los textos visibles, incluyendo títulos, etiquetas, botones y
     * las cabeceras de la tabla, según el idioma y país especificados.
     * También actualiza las preguntas de seguridad y recursos internacionalizados.
     *
     * @param lenguaje el código de lenguaje ISO 639 (por ejemplo, "es" para español, "en" para inglés)
     * @param pais el código de país ISO 3166 (por ejemplo, "EC" para Ecuador, "US" para Estados Unidos)
     */
    public void cambiarIdioma(String lenguaje, String pais) {
        mensaje.setLenguaje(lenguaje, pais);
        actualizarTextos();
        configurarTabla(); // ¡Excelente! Esto también cambia las columnas.
    }


    /**
     * Actualiza los textos de todos los componentes de la interfaz gráfica
     * utilizando las claves de internacionalización definidas en el archivo de recursos.
     * <p>
     * Este método se encarga de actualizar:
     * <ul>
     *   <li>El título de la ventana.</li>
     *   <li>Las etiquetas asociadas a los campos de datos (código, subtotal, IVA, total).</li>
     *   <li>Los textos de los botones (Buscar, Limpiar).</li>
     * </ul>
     * Se debe invocar este método siempre que se cambie el idioma mediante {@link #cambiarIdioma(String, String)}
     * para garantizar que todos los textos reflejen correctamente el idioma actual.
     */
    private void actualizarTextos() {
        setTitle(mensaje.get("carrito.buscar.titulo"));
        lblTitulo.setText(mensaje.get("carrito.buscar.titulo"));
        lblCodigo.setText(mensaje.get("codigo"));
        lblSubtotal.setText(mensaje.get("subtotal"));
        lblIva.setText(mensaje.get("iva"));
        lblTotal.setText(mensaje.get("total"));

        btnBuscar.setText(mensaje.get("buscar"));
        btnLimpiar.setText(mensaje.get("limpiar"));
    }



    /**
     * Retorna el panel principal de la vista.
     * @return el panel principal.
     */
    public JPanel getPnlPrincipal() {
        return pnlPrincipal;
    }

    /**
     * Establece el panel principal de la vista.
     * @param pnlPrincipal el nuevo panel principal.
     */
    public void setPnlPrincipal(JPanel pnlPrincipal) {
        this.pnlPrincipal = pnlPrincipal;
    }

    /**
     * Retorna el panel superior de la vista.
     * @return el panel superior.
     */
    public JPanel getPnlSuperior() {
        return pnlSuperior;
    }

    /**
     * Establece el panel superior.
     * @param pnlSuperior el nuevo panel superior.
     */
    public void setPnlSuperior(JPanel pnlSuperior) {
        this.pnlSuperior = pnlSuperior;
    }

    /**
     * Retorna el panel central de la vista.
     * @return el panel central.
     */
    public JPanel getPnlCentral() {
        return pnlCentral;
    }

    /**
     * Establece el panel central.
     * @param pnlCentral el nuevo panel central.
     */
    public void setPnlCentral(JPanel pnlCentral) {
        this.pnlCentral = pnlCentral;
    }

    /**
     * Obtiene la etiqueta del título.
     * @return la etiqueta del título.
     */
    public JLabel getLblTitulo() {
        return lblTitulo;
    }

    /**
     * Establece la etiqueta del título.
     * @param lblTitulo la nueva etiqueta del título.
     */
    public void setLblTitulo(JLabel lblTitulo) {
        this.lblTitulo = lblTitulo;
    }

    /**
     * Obtiene la etiqueta del código.
     * @return la etiqueta del código.
     */
    public JLabel getLblCodigo() {
        return lblCodigo;
    }

    /**
     * Establece la etiqueta del código.
     * @param lblCodigo la nueva etiqueta del código.
     */
    public void setLblCodigo(JLabel lblCodigo) {
        this.lblCodigo = lblCodigo;
    }

    /**
     * Obtiene la etiqueta del subtotal.
     * @return la etiqueta del subtotal.
     */
    public JLabel getLblSubtotal() {
        return lblSubtotal;
    }

    /**
     * Establece la etiqueta del subtotal.
     * @param lblSubtotal la nueva etiqueta del subtotal.
     */
    public void setLblSubtotal(JLabel lblSubtotal) {
        this.lblSubtotal = lblSubtotal;
    }

    /**
     * Obtiene la etiqueta del IVA.
     * @return la etiqueta del IVA.
     */
    public JLabel getLblIva() {
        return lblIva;
    }

    /**
     * Establece la etiqueta del IVA.
     * @param lblIva la nueva etiqueta del IVA.
     */
    public void setLblIva(JLabel lblIva) {
        this.lblIva = lblIva;
    }

    /**
     * Obtiene la etiqueta del total.
     * @return la etiqueta del total.
     */
    public JLabel getLblTotal() {
        return lblTotal;
    }

    /**
     * Establece la etiqueta del total.
     * @param lblTotal la nueva etiqueta del total.
     */
    public void setLblTotal(JLabel lblTotal) {
        this.lblTotal = lblTotal;
    }

    /**
     * Obtiene el campo de texto del código.
     * @return el campo de texto del código.
     */
    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    /**
     * Establece el campo de texto del código.
     * @param txtCodigo el nuevo campo de texto del código.
     */
    public void setTxtCodigo(JTextField txtCodigo) {
        this.txtCodigo = txtCodigo;
    }

    /**
     * Obtiene el botón de buscar.
     * @return el botón de buscar.
     */
    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    /**
     * Establece el botón de buscar.
     * @param btnBuscar el nuevo botón de buscar.
     */
    public void setBtnBuscar(JButton btnBuscar) {
        this.btnBuscar = btnBuscar;
    }

    /**
     * Obtiene el campo de texto del subtotal.
     * @return el campo de texto del subtotal.
     */
    public JTextField getTxtSubtotal() {
        return txtSubtotal;
    }

    /**
     * Establece el campo de texto del subtotal.
     * @param txtSubtotal el nuevo campo de texto del subtotal.
     */
    public void setTxtSubtotal(JTextField txtSubtotal) {
        this.txtSubtotal = txtSubtotal;
    }

    /**
     * Obtiene el campo de texto del IVA.
     * @return el campo de texto del IVA.
     */
    public JTextField getTxtIva() {
        return txtIva;
    }

    /**
     * Establece el campo de texto del IVA.
     * @param txtIva el nuevo campo de texto del IVA.
     */
    public void setTxtIva(JTextField txtIva) {
        this.txtIva = txtIva;
    }

    /**
     * Obtiene el campo de texto del total.
     * @return el campo de texto del total.
     */
    public JTextField getTxtTotal() {
        return txtTotal;
    }

    /**
     * Establece el campo de texto del total.
     * @param txtTotal el nuevo campo de texto del total.
     */
    public void setTxtTotal(JTextField txtTotal) {
        this.txtTotal = txtTotal;
    }

    /**
     * Obtiene el botón para limpiar datos.
     * @return el botón de limpiar.
     */
    public JButton getBtnLimpiar() {
        return btnLimpiar;
    }

    /**
     * Establece el botón de limpiar.
     * @param btnLimpiar el nuevo botón de limpiar.
     */
    public void setBtnLimpiar(JButton btnLimpiar) {
        this.btnLimpiar = btnLimpiar;
    }

    /**
     * Obtiene la tabla de productos.
     * @return la tabla de productos.
     */
    public JTable getTblProducto() {
        return tblProducto;
    }

    /**
     * Establece la tabla de productos.
     * @param tblProducto la nueva tabla de productos.
     */
    public void setTblProducto(JTable tblProducto) {
        this.tblProducto = tblProducto;
    }

    /**
     * Obtiene el manejador de internacionalización.
     * @return el manejador de internacionalización.
     */
    public MensajeInternacionalizacionHandler getMensaje() {
        return mensaje;
    }

    /**
     * Establece el manejador de internacionalización.
     * @param mensaje el nuevo manejador de internacionalización.
     */
    public void setMensaje(MensajeInternacionalizacionHandler mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * Muestra un mensaje internacionalizado usando una clave.
     * @param keyMensaje clave del mensaje a mostrar.
     */
    public void mostrarMensaje(String keyMensaje) {
        JOptionPane.showMessageDialog(this, mensaje.get(keyMensaje));
    }

}

