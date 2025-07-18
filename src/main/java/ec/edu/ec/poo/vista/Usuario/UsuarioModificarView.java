package ec.edu.ec.poo.vista.Usuario;

import ec.edu.ec.poo.utils.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * Clase que representa la ventana para modificar datos del usuario,
 * incluyendo contraseña, con soporte de internacionalización.
 */
public class UsuarioModificarView extends JInternalFrame {
    /** Panel principal de la ventana */
    private JPanel pnlPrincipal;

    /** Etiqueta del título de la ventana */
    private JLabel lblTitulo;

    /** Etiqueta para el campo de usuario */
    private JLabel lblUsuario;

    /** Etiqueta para el campo de contraseña */
    private JLabel lblContrasenia;

    /** Etiqueta para el campo de confirmar contraseña */
    private JLabel lblConfirmar;

    /** Campo de texto para ingresar el nombre de usuario */
    private JTextField txtUsuario;

    /** Campo para ingresar la nueva contraseña */
    private JPasswordField txtContrasenia;

    /** Campo para confirmar la nueva contraseña */
    private JPasswordField txtConfirmar;

    /** Botón para aplicar la modificación de datos */
    private JButton btnModificar;

    /** Botón para buscar el usuario antes de modificar */
    private JButton btnMostrar;

    /** Manejador para la internacionalización de mensajes */
    private MensajeInternacionalizacionHandler mensaje;

    /**
     * Constructor principal que inicializa la vista con internacionalización.
     * @param mensaje objeto manejador para los mensajes traducidos
     */
    public UsuarioModificarView(MensajeInternacionalizacionHandler mensaje) {
        this.mensaje = mensaje;
        initComponent();
        actualizarTextos();

        // Ícono para el botón Modificar
        URL urlModificar = UsuarioModificarView.class.getClassLoader().getResource("imagenes/modificarproduc.png");
        if (urlModificar != null) {
            ImageIcon icono = new ImageIcon(new ImageIcon(urlModificar).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
            btnModificar.setIcon(icono);
        }

         // Ícono para el botón Mostrar
        URL urlMostrar = UsuarioModificarView.class.getClassLoader().getResource("imagenes/buscar.png");
        if (urlMostrar != null) {
            ImageIcon icono = new ImageIcon(new ImageIcon(urlMostrar).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
            btnMostrar.setIcon(icono);
        }


    }

    /**
     * Configura las propiedades visuales iniciales de la ventana.
     */
    private void initComponent() {
        setContentPane(pnlPrincipal);
        setClosable(true);
        setResizable(true);
        setSize(500, 500);

    }

    /**
     * Actualiza los textos de la interfaz gráfica según el idioma seleccionado.
     */
    private void actualizarTextos() {
        setTitle(mensaje.get("usuario.modificar.titulo"));

        lblTitulo.setText(mensaje.get("usuario.modificar.titulo"));
        lblUsuario.setText(mensaje.get("usuario"));
        lblContrasenia.setText(mensaje.get("contrasenia"));
        lblConfirmar.setText(mensaje.get("confirmar"));

        btnModificar.setText(mensaje.get("modificar"));
        btnMostrar.setText(mensaje.get("mostrar"));
    }

    /**
     * Cambia el idioma de la interfaz gráfica.
     * @param lang idioma en formato ISO, ej. "es"
     * @param country país en formato ISO, ej. "EC"
     */
    public void cambiarIdioma(String lang, String country) {
        mensaje.setLenguaje(lang, country);
        actualizarTextos(); // Asegúrate de que este método existe y cambia TODO
    }


    /**
     * Obtiene el panel principal de la ventana.
     * @return JPanel principal que contiene los componentes gráficos.
     */
    public JPanel getPnlPrincipal() {
        return pnlPrincipal;
    }

    /**
     * Asigna un nuevo panel principal a la ventana.
     * @param pnlPrincipal nuevo JPanel principal.
     */
    public void setPnlPrincipal(JPanel pnlPrincipal) {
        this.pnlPrincipal = pnlPrincipal;
    }

    /**
     * Obtiene la etiqueta del título de la ventana.
     * @return JLabel que muestra el título.
     */
    public JLabel getLblTitulo() {
        return lblTitulo;
    }

    /**
     * Establece el valor de la etiqueta del título.
     * @param lblTitulo JLabel para el título.
     */
    public void setLblTitulo(JLabel lblTitulo) {
        this.lblTitulo = lblTitulo;
    }

    /**
     * Obtiene la etiqueta asociada al nombre de usuario.
     * @return JLabel que muestra el texto del usuario.
     */
    public JLabel getLblUsuario() {
        return lblUsuario;
    }

    /**
     * Establece la etiqueta que se muestra para el campo de usuario.
     * @param lblUsuario JLabel que se mostrará junto al campo usuario.
     */
    public void setLblUsuario(JLabel lblUsuario) {
        this.lblUsuario = lblUsuario;
    }

    /**
     * Obtiene la etiqueta para el campo contraseña.
     * @return JLabel correspondiente al campo de contraseña.
     */
    public JLabel getLblContrasenia() {
        return lblContrasenia;
    }


    /**
     * Establece el JLabel que se usará para mostrar el texto "Contraseña".
     * @param lblContrasenia JLabel para el campo de contraseña.
     */
    public void setLblContrasenia(JLabel lblContrasenia) {
        this.lblContrasenia = lblContrasenia;
    }

    /**
     * Obtiene el campo de texto donde se ingresa el nombre de usuario.
     * @return JTextField para ingresar el usuario.
     */
    public JTextField getTxtUsuario() {
        return txtUsuario;
    }

    /**
     * Establece el JTextField para ingresar el nombre de usuario.
     * @param txtUsuario campo de texto para el usuario.
     */
    public void setTxtUsuario(JTextField txtUsuario) {
        this.txtUsuario = txtUsuario;
    }

    /**
     * Obtiene el campo de contraseña.
     * @return JPasswordField para ingresar la contraseña.
     */
    public JPasswordField getTxtContrasenia() {
        return txtContrasenia;
    }

    /**
     * Establece el campo de contraseña.
     * @param txtContrasenia JPasswordField para la contraseña.
     */
    public void setTxtContrasenia(JPasswordField txtContrasenia) {
        this.txtContrasenia = txtContrasenia;
    }

    /**
     * Obtiene el campo para confirmar la contraseña.
     * @return JPasswordField para la confirmación de contraseña.
     */
    public JPasswordField getTxtConfirmar() {
        return txtConfirmar;
    }

    /**
     * Establece el campo para confirmar la contraseña.
     * @param txtConfirmar JPasswordField para confirmar la contraseña.
     */
    public void setTxtConfirmar(JPasswordField txtConfirmar) {
        this.txtConfirmar = txtConfirmar;
    }

    /**
     * Obtiene el botón encargado de modificar el usuario.
     * @return JButton para la acción de modificar.
     */
    public JButton getBtnModificar() {
        return btnModificar;
    }


    /**
     * Establece el botón utilizado para modificar la información del usuario.
     * @param btnModificar JButton que ejecuta la acción de modificar.
     */
    public void setBtnModificar(JButton btnModificar) {
        this.btnModificar = btnModificar;
    }

    /**
     * Obtiene el botón que muestra la información del usuario buscado.
     * @return JButton para mostrar datos del usuario.
     */
    public JButton getBtnMostrar() {
        return btnMostrar;
    }

    /**
     * Establece el botón para mostrar la información del usuario.
     * @param btnMostrar JButton que ejecuta la acción de mostrar usuario.
     */
    public void setBtnMostrar(JButton btnMostrar) {
        this.btnMostrar = btnMostrar;
    }

    /**
     * Obtiene el manejador de internacionalización para cambiar textos dinámicamente.
     * @return MensajeInternacionalizacionHandler para traducción de textos.
     */
    public MensajeInternacionalizacionHandler getMensaje() {
        return mensaje;
    }

    /**
     * Establece el manejador de internacionalización para traducción de textos.
     * @param mensaje objeto MensajeInternacionalizacionHandler con traducciones.
     */
    public void setMensaje(MensajeInternacionalizacionHandler mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * Muestra un cuadro de diálogo con el mensaje traducido correspondiente a la clave proporcionada.
     * @param keyMensaje clave del mensaje en el archivo de internacionalización.
     */
    public void mostrarMensaje(String keyMensaje) {
        JOptionPane.showMessageDialog(this, mensaje.get(keyMensaje));
    }

    /**
     * Limpia los campos de entrada del formulario, dejando vacío el usuario,
     * contraseña y confirmación de contraseña.
     */
    public void limpiarCampos() {
        txtUsuario.setText("");
        txtContrasenia.setText("");
        txtConfirmar.setText("");
    }

}
