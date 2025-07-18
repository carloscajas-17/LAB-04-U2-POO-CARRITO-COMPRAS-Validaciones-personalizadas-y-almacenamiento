package ec.edu.ec.poo.vista.Contraseña;
import ec.edu.ec.poo.modelo.RespuestaSeguridad;
import ec.edu.ec.poo.utils.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * Clase gráfica que representa la ventana de recuperación de cuenta mediante preguntas de seguridad.
 * Permite validar usuario, responder preguntas y acceder al cambio de contraseña.
 * <p>Soporta cambio dinámico de idioma mediante {@link MensajeInternacionalizacionHandler}.</p>
 * <p>Incluye íconos personalizados para cada botón de acción.</p>
 */
public class RecuperarCuentaView extends JDialog {
    /** Panel principal que contiene todos los componentes */
    private JPanel panelPrincipal;
    /** Etiqueta del título principal */
    private JLabel lblTitulo;
    /** Etiqueta para el ID del usuario */
    private JLabel lblId;
    /** Etiqueta para el nombre del usuario */
    private JLabel lblNombre;
    /** Etiqueta para mostrar la pregunta de seguridad */
    private JLabel lblPregunta;
    /** Campo de texto para ingresar ID */
    private JTextField txtId;
    /** Campo de texto para ingresar nombre */
    private JTextField txtNombre;
    /** Campo de texto para ingresar respuesta */
    private JTextField txtRespuesta;
    /** Botón para validar si el usuario existe */
    private JButton btnValidarUsuario;
    /** Botón para validar la respuesta de seguridad */
    private JButton btnValidarPregunta;
    /** Botón para aceptar y continuar */
    private JButton btnAceptar;
    /** Botón para cancelar la recuperación */
    private JButton btnCancelar;
    /** Pregunta actual mostrada */
    private RespuestaSeguridad preguntaActual;
    /** Manejador de internacionalización */
    private MensajeInternacionalizacionHandler mensaje;



    /**
     * Constructor principal. Inicializa la vista con la configuración de internacionalización.
     * Carga los íconos de los botones y ajusta el idioma.
     *
     * @param mensaje manejador de internacionalización
     */
    public RecuperarCuentaView(MensajeInternacionalizacionHandler mensaje) {
        this.mensaje = mensaje;
        initComponents();
        actualizarTextos();

        // Ícono btnValidarUsuario
        URL validarUsuarioURL = RecuperarCuentaView.class.getClassLoader().getResource("imagenes/validarusuario.png");
        if (validarUsuarioURL != null) {
            ImageIcon iconValidarUsuario = new ImageIcon(new ImageIcon(validarUsuarioURL).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
            btnValidarUsuario.setIcon(iconValidarUsuario);
        } else {
            System.out.println("Error al cargar validarusuario.png");
        }

         // Ícono btnValidarPregunta
        URL validarPreguntaURL = RecuperarCuentaView.class.getClassLoader().getResource("imagenes/validarpreguntas.png");
        if (validarPreguntaURL != null) {
            ImageIcon iconValidarPregunta = new ImageIcon(new ImageIcon(validarPreguntaURL).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
            btnValidarPregunta.setIcon(iconValidarPregunta);
        } else {
            System.out.println("Error al cargar validarpreguntas.png");
        }

         // Ícono btnAceptar
        URL aceptarURL = RecuperarCuentaView.class.getClassLoader().getResource("imagenes/aceptarcontra.png");
        if (aceptarURL != null) {
            ImageIcon iconAceptar = new ImageIcon(new ImageIcon(aceptarURL).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
            btnAceptar.setIcon(iconAceptar);
        } else {
            System.out.println("Error al cargar aceptarcontra.png");
        }

        // Ícono btnCancelar
        URL cancelarURL = RecuperarCuentaView.class.getClassLoader().getResource("imagenes/cancelarrecuperar.png");
        if (cancelarURL != null) {
            ImageIcon iconCancelar = new ImageIcon(new ImageIcon(cancelarURL).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
            btnCancelar.setIcon(iconCancelar);
        } else {
            System.out.println("Error al cargar cancelarrecuperar.png");
        }

    }

    /**
     * Inicializa componentes básicos de la ventana.
     */
    private void initComponents() {
        setContentPane(panelPrincipal);
        setModal(true);
        setSize(450, 300);
        setLocationRelativeTo(null);
        btnAceptar.setEnabled(false);
    }

    /**
     * Cambia dinámicamente el idioma de la interfaz.
     *
     * @param lang código de idioma (por ejemplo: "es", "en", "fr")
     * @param country código de país (por ejemplo: "EC", "US", "FR")
     */
    public void cambiarIdioma(String lang, String country) {
        mensaje.setLenguaje(lang, country);
        actualizarTextos();
    }


    /**
     * Actualiza los textos de la interfaz según el idioma seleccionado.
     */
    public void actualizarTextos() {
        setTitle(mensaje.get("recuperar.titulo"));
        lblTitulo.setText(mensaje.get("recuperar.titulo"));
        lblId.setText(mensaje.get("usuario.id"));
        lblNombre.setText(mensaje.get("usuario.nombre"));
        lblPregunta.setText("");
        btnValidarUsuario.setText(mensaje.get("validar.usuario"));
        btnValidarPregunta.setText(mensaje.get("validar"));
        btnAceptar.setText(mensaje.get("aceptar"));
        btnCancelar.setText(mensaje.get("cancelar"));
    }





    /**
     * Obtiene el panel principal de la vista.
     *
     * @return el {@link JPanel} principal.
     */
    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    /**
     * Establece el panel principal de la vista.
     *
     * @param panelPrincipal el panel principal a asignar.
     */
    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    /**
     * Obtiene la etiqueta del título.
     *
     * @return la etiqueta {@link JLabel} del título.
     */
    public JLabel getLblTitulo() {
        return lblTitulo;
    }

    /**
     * Establece la etiqueta del título.
     *
     * @param lblTitulo la etiqueta de título a asignar.
     */
    public void setLblTitulo(JLabel lblTitulo) {
        this.lblTitulo = lblTitulo;
    }

    /**
     * Obtiene la etiqueta asociada al campo ID.
     * @return JLabel correspondiente a la etiqueta del ID del usuario.
     */
    public JLabel getLblId() {
        return lblId;
    }

    /**
     * Establece la etiqueta asociada al campo ID.
     * @param lblId la etiqueta {@link JLabel} para el ID.
     */
    public void setLblId(JLabel lblId) {
        this.lblId = lblId;
    }

    /**
     * Obtiene la etiqueta asociada al campo Nombre.
     * @return JLabel correspondiente a la etiqueta del nombre del usuario.
     */
    public JLabel getLblNombre() {
        return lblNombre;
    }

    /**
     * Establece la etiqueta asociada al campo Nombre.
     * @param lblNombre la etiqueta {@link JLabel} para el nombre.
     */
    public void setLblNombre(JLabel lblNombre) {
        this.lblNombre = lblNombre;
    }

    /**
     * Obtiene la etiqueta donde se mostrará la pregunta de seguridad.
     * @return JLabel correspondiente a la pregunta de seguridad.
     */
    public JLabel getLblPregunta() {
        return lblPregunta;
    }

    /**
     * Establece la etiqueta para mostrar la pregunta de seguridad.
     * @param lblPregunta etiqueta {@link JLabel} que mostrará la pregunta.
     */
    public void setLblPregunta(JLabel lblPregunta) {
        this.lblPregunta = lblPregunta;
    }

    /**
     * Obtiene el campo de texto donde el usuario ingresa su ID.
     * @return {@link JTextField} para ingreso del ID del usuario.
     */
    public JTextField getTxtId() {
        return txtId;
    }


    /**
     * Establece el campo de texto para el ID del usuario.
     * @param txtId {@link JTextField} que captura el ID del usuario.
     */
    public void setTxtId(JTextField txtId) {
        this.txtId = txtId;
    }

    /**
     * Obtiene el campo de texto para ingresar el nombre del usuario.
     * @return {@link JTextField} correspondiente al nombre del usuario.
     */
    public JTextField getTxtNombre() {
        return txtNombre;
    }

    /**
     * Establece el campo de texto para el nombre del usuario.
     * @param txtNombre {@link JTextField} que captura el nombre del usuario.
     */
    public void setTxtNombre(JTextField txtNombre) {
        this.txtNombre = txtNombre;
    }

    /**
     * Obtiene el campo de texto donde el usuario ingresa su respuesta a la pregunta de seguridad.
     * @return {@link JTextField} correspondiente a la respuesta de seguridad.
     */
    public JTextField getTxtRespuesta() {
        return txtRespuesta;
    }

    /**
     * Establece el campo de texto para la respuesta de seguridad.
     * @param txtRespuesta {@link JTextField} que almacena la respuesta a la pregunta de seguridad.
     */
    public void setTxtRespuesta(JTextField txtRespuesta) {
        this.txtRespuesta = txtRespuesta;
    }

    /**
     * Obtiene el botón para validar los datos del usuario (ID y nombre).
     * @return {@link JButton} que permite validar el usuario.
     */
    public JButton getBtnValidarUsuario() {
        return btnValidarUsuario;
    }

    /**
     * Establece el botón que permite validar el ID y nombre del usuario.
     * @param btnValidarUsuario {@link JButton} para validar el usuario.
     */
    public void setBtnValidarUsuario(JButton btnValidarUsuario) {
        this.btnValidarUsuario = btnValidarUsuario;
    }

    /**
     * Obtiene el botón para validar la respuesta de seguridad.
     * @return {@link JButton} que permite validar la respuesta ingresada.
     */
    public JButton getBtnValidarPregunta() {
        return btnValidarPregunta;
    }


    /**
     * Asigna el botón para validar la respuesta de la pregunta de seguridad.
     * @param btnValidarPregunta {@link JButton} que permite validar la respuesta ingresada.
     */
    public void setBtnValidarPregunta(JButton btnValidarPregunta) {
        this.btnValidarPregunta = btnValidarPregunta;
    }

    /**
     * Obtiene el botón que permite aceptar el flujo de recuperación de contraseña.
     * @return {@link JButton} correspondiente al botón aceptar.
     */
    public JButton getBtnAceptar() {
        return btnAceptar;
    }

    /**
     * Establece el botón que permite confirmar el proceso de recuperación de contraseña.
     * @param btnAceptar {@link JButton} que acepta y confirma la operación.
     */
    public void setBtnAceptar(JButton btnAceptar) {
        this.btnAceptar = btnAceptar;
    }

    /**
     * Obtiene el botón que permite cancelar el proceso de recuperación de contraseña.
     * @return {@link JButton} correspondiente al botón cancelar.
     */
    public JButton getBtnCancelar() {
        return btnCancelar;
    }

    /**
     * Establece el botón para cancelar el proceso de recuperación de contraseña.
     * @param btnCancelar {@link JButton} que cancela la operación.
     */
    public void setBtnCancelar(JButton btnCancelar) {
        this.btnCancelar = btnCancelar;
    }

    /**
     * Obtiene el manejador de internacionalización para acceder a los textos traducidos.
     * @return {@link MensajeInternacionalizacionHandler} encargado de la localización.
     */
    public MensajeInternacionalizacionHandler getMensaje() {
        return mensaje;
    }

    /**
     * Obtiene la pregunta de seguridad actualmente mostrada al usuario.
     * @return {@link RespuestaSeguridad} con la pregunta activa.
     */
    public RespuestaSeguridad getPreguntaActual() {
        return preguntaActual;
    }

    /**
     * Asigna la pregunta de seguridad que se mostrará al usuario.
     * @param preguntaActual {@link RespuestaSeguridad} con la pregunta seleccionada.
     */
    public void setPreguntaActual(RespuestaSeguridad preguntaActual) {
        this.preguntaActual = preguntaActual;
    }

    /**
     * Establece el manejador de internacionalización para cambiar dinámicamente los textos.
     * @param mensaje {@link MensajeInternacionalizacionHandler} que administra los textos internacionalizados.
     */
    public void setMensaje(MensajeInternacionalizacionHandler mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * Muestra un mensaje emergente (diálogo) al usuario con el contenido recibido.
     * @param texto Mensaje textual que será mostrado en el cuadro de diálogo.
     */
    public void mostrarMensaje(String texto) {
        JOptionPane.showMessageDialog(this, texto);
    }



}
