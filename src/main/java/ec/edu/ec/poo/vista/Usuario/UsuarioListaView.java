package ec.edu.ec.poo.vista.Usuario;



import ec.edu.ec.poo.utils.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.net.URL;

/**
 * Vista gráfica para listar usuarios con opción de buscar, limpiar y visualizar detalles.
 * Permite la internacionalización de textos usando MensajeInternacionalizacionHandler.
 */
public class UsuarioListaView extends JInternalFrame {
    /** Panel principal de la ventana */
    private JPanel pnlPrincipal;

    /** Panel superior donde se encuentran los campos de búsqueda */
    private JPanel pnlSuperior;

    /** Panel inferior donde se muestra la tabla */
    private JPanel pnlInferior;

    /** Campo de texto para ingresar el nombre o ID del usuario a buscar */
    private JTextField txtUsuario;

    /** Botón para realizar la búsqueda del usuario */
    private JButton btnBuscar;

    /** Botón para limpiar el campo de búsqueda */
    private JButton btnLimpiar;

    /** Etiqueta que muestra el título de la ventana */
    private JLabel lblTitulo;

    /** Etiqueta descriptiva para el campo de usuario */
    private JLabel lblUsuario;

    /** Tabla donde se listan los detalles del usuario */
    private JTable tblDetalle;

    /** Manejador de internacionalización para traducir los textos */
    private MensajeInternacionalizacionHandler mensaje;

    /**
     * Constructor que inicializa la vista de listado de usuarios con internacionalización.
     * @param mensaje manejador de internacionalización
     */
    public UsuarioListaView(MensajeInternacionalizacionHandler mensaje) {
        this.mensaje = mensaje;
        initComponents();
        actualizarTextos();

        // Ícono para Buscar Usuario
        URL urlBuscar = UsuarioListaView.class.getClassLoader().getResource("imagenes/buscarusuarioListar.png");
        if (urlBuscar != null) {
            btnBuscar.setIcon(new ImageIcon(new ImageIcon(urlBuscar).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        }

        // Ícono para Limpiar
        URL urlLimpiar = UsuarioListaView.class.getClassLoader().getResource("imagenes/limpiarcarr.png");
        if (urlLimpiar != null) {
            btnLimpiar.setIcon(new ImageIcon(new ImageIcon(urlLimpiar).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        }

    }

    /**
     * Inicializa los componentes gráficos de la ventana.
     */
    private void initComponents() {
        setContentPane(pnlPrincipal);
        setClosable(true);
        setResizable(true);
        setSize(500, 500);

        configurarTabla();
    }

    /**
     * Configura la tabla para mostrar las columnas asociadas a los usuarios.
     */
    private void configurarTabla() {
        DefaultTableModel modelo = new DefaultTableModel();
        Object[] columnas = {
                mensaje.get("columna.usuario"),
                mensaje.get("columna.asociado"),
                mensaje.get("columna.total")
        };
        modelo.setColumnIdentifiers(columnas);
        tblDetalle.setModel(modelo);
    }
    /**
     * Actualiza los textos de todos los componentes de la ventana según el idioma seleccionado.
     */
    public void actualizarTextos() {
        setTitle(mensaje.get("usuario.lista.view"));

        lblTitulo.setText(mensaje.get("usuario.lista.view"));
        lblUsuario.setText(mensaje.get("usuario"));

        btnBuscar.setText(mensaje.get("buscar"));
        btnLimpiar.setText(mensaje.get("limpiar"));

        configurarTabla(); //  También se actualizan los textos de las columnas
    }

    /**
     * Cambia el idioma de la ventana.
     * @param lang lenguaje (por ejemplo, "es", "en", "fr")
     * @param country país (por ejemplo, "EC", "US", "FR")
     */
    public void cambiarIdioma(String lang, String country) {
        mensaje.setLenguaje(lang, country);
        actualizarTextos();
    }

    /**
     * Obtiene el panel principal que contiene todos los componentes.
     * @return JPanel panel principal
     */
    public JPanel getPnlPrincipal() {
        return pnlPrincipal;
    }

    /**
     * Establece el panel principal.
     * @param pnlPrincipal nuevo panel principal
     */
    public void setPnlPrincipal(JPanel pnlPrincipal) {
        this.pnlPrincipal = pnlPrincipal;
    }

    /**
     * Obtiene el panel superior donde están los campos de búsqueda.
     * @return JPanel panel superior
     */
    public JPanel getPnlSuperior() {
        return pnlSuperior;
    }

    /**
     * Establece el panel superior.
     * @param pnlSuperior nuevo panel superior
     */
    public void setPnlSuperior(JPanel pnlSuperior) {
        this.pnlSuperior = pnlSuperior;
    }

    /**
     * Obtiene el panel inferior donde se ubica la tabla de usuarios.
     * @return JPanel panel inferior
     */
    public JPanel getPnlInferior() {
        return pnlInferior;
    }

    /**
     * Establece el panel inferior.
     * @param pnlInferior nuevo panel inferior
     */
    public void setPnlInferior(JPanel pnlInferior) {
        this.pnlInferior = pnlInferior;
    }

    /**
     * Obtiene el campo de texto donde se ingresa el nombre o ID del usuario.
     * @return JTextField campo de texto del usuario
     */
    public JTextField getTxtUsuario() {
        return txtUsuario;
    }


    /**
     * Establece el campo de texto donde se ingresa el usuario.
     * @param txtUsuario campo de texto del usuario
     */
    public void setTxtUsuario(JTextField txtUsuario) {
        this.txtUsuario = txtUsuario;
    }

    /**
     * Obtiene el botón utilizado para realizar la búsqueda de usuarios.
     * @return JButton botón de búsqueda
     */
    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    /**
     * Establece el botón utilizado para buscar usuarios.
     * @param btnBuscar nuevo botón de búsqueda
     */
    public void setBtnBuscar(JButton btnBuscar) {
        this.btnBuscar = btnBuscar;
    }

    /**
     * Obtiene el botón utilizado para limpiar los campos o resultados.
     * @return JButton botón de limpiar
     */
    public JButton getBtnLimpiar() {
        return btnLimpiar;
    }

    /**
     * Establece el botón utilizado para limpiar los campos o resultados.
     * @param btnLimpiar nuevo botón de limpiar
     */
    public void setBtnLimpiar(JButton btnLimpiar) {
        this.btnLimpiar = btnLimpiar;
    }

    /**
     * Obtiene la etiqueta que muestra el título principal de la ventana.
     * @return JLabel etiqueta del título
     */
    public JLabel getLblTitulo() {
        return lblTitulo;
    }

    /**
     * Establece la etiqueta del título principal de la ventana.
     * @param lblTitulo nueva etiqueta del título
     */
    public void setLblTitulo(JLabel lblTitulo) {
        this.lblTitulo = lblTitulo;
    }

    /**
     * Obtiene la etiqueta que describe el campo del usuario.
     * @return JLabel etiqueta del usuario
     */
    public JLabel getLblUsuario() {
        return lblUsuario;
    }


    /**
     * Establece la etiqueta asociada al campo de usuario.
     * @param lblUsuario nueva etiqueta del usuario
     */
    public void setLblUsuario(JLabel lblUsuario) {
        this.lblUsuario = lblUsuario;
    }

    /**
     * Obtiene la tabla donde se listan los detalles de los usuarios.
     * @return JTable tabla de detalles
     */
    public JTable getTblDetalle() {
        return tblDetalle;
    }

    /**
     * Establece la tabla donde se mostrarán los detalles de los usuarios.
     * @param tblDetalle nueva tabla de detalles
     */
    public void setTblDetalle(JTable tblDetalle) {
        this.tblDetalle = tblDetalle;
    }

    /**
     * Obtiene el manejador de mensajes para la internacionalización.
     * @return MensajeInternacionalizacionHandler manejador de mensajes
     */
    public MensajeInternacionalizacionHandler getMensaje() {
        return mensaje;
    }

    /**
     * Asigna un nuevo manejador de mensajes para la internacionalización.
     * @param mensaje nuevo manejador de mensajes
     */
    public void setMensaje(MensajeInternacionalizacionHandler mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * Muestra un mensaje emergente en la interfaz gráfica usando la clave proporcionada
     * del archivo de internacionalización.
     * @param keyMensaje clave del mensaje a mostrar
     */
    public void mostrarMensaje(String keyMensaje) {
        JOptionPane.showMessageDialog(this, mensaje.get(keyMensaje));
    }

}
