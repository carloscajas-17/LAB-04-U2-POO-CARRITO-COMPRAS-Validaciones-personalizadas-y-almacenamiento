package ec.edu.ec.poo.vista.Contraseña;

import ec.edu.ec.poo.utils.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

/**
 * Vista gráfica que permite ingresar una nueva contraseña durante el proceso de recuperación.
 * Incluye campos para ingresar y confirmar la contraseña, junto con botones para aceptar o cancelar.
 * Admite internacionalización dinámica mediante {@link MensajeInternacionalizacionHandler}.
 */
public class NuevaContrasenaView extends JDialog {
    /** Panel principal que contiene todos los componentes de la vista */
    private JPanel panelPrincipal;

    /** Etiqueta para el título de la ventana */
    private JLabel lblTitulo;

    /** Etiqueta para el campo de nueva contraseña */
    private JLabel lblNueva;

    /** Etiqueta para el campo de confirmar contraseña */
    private JLabel lblConfirmar;

    /** Campo de contraseña para ingresar la nueva contraseña */
    private JPasswordField txtNueva;

    /** Campo de contraseña para confirmar la nueva contraseña */
    private JPasswordField txtConfirmar;

    /** Botón para aceptar y confirmar el cambio de contraseña */
    private JButton btnAceptar;

    /** Botón para cancelar el proceso */
    private JButton btnCancelar;

    /** Manejador de internacionalización para cambiar dinámicamente los textos */
    private MensajeInternacionalizacionHandler mensaje;



    /**
     * Constructor principal que inicializa la vista con el manejador de internacionalización.
     * @param mensaje objeto que gestiona los textos traducidos según el idioma seleccionado
     */
    public NuevaContrasenaView(MensajeInternacionalizacionHandler mensaje) {
        this.mensaje = mensaje;
        initComponents();
        actualizarTextos();
        setModal(true);
    }

    /**
     * Inicializa los componentes gráficos de la ventana, estableciendo el panel principal,
     * el tamaño, la posición y el comportamiento al cerrar.
     */
    private void initComponents() {
        setContentPane(panelPrincipal);
        setTitle("Recuperar Contraseña");
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        setSize(400, 250);
        setLocationRelativeTo(null);
    }

    /**
     * Cambia el idioma de toda la vista al idioma especificado, actualizando los textos y los íconos.
     * @param lang código del lenguaje (ejemplo: "es", "en", "fr")
     * @param country código del país (ejemplo: "EC", "US", "FR")
     */
    public void cambiarIdioma(String lang, String country) {
        mensaje.setLenguaje(lang, country);
        actualizarTextos();

        URL aceptarURL = NuevaContrasenaView.class.getClassLoader().getResource("imagenes/aceptarcontra.png");
        if (aceptarURL != null) {
            ImageIcon iconAceptar = new ImageIcon(new ImageIcon(aceptarURL).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
            btnAceptar.setIcon(iconAceptar);
        } else {
            System.out.println("Error al cargar aceptarcontra.png");
        }

        // Ícono btnCancelar
        URL cancelarURL = NuevaContrasenaView.class.getClassLoader().getResource("imagenes/cancelarcontra.png");
        if (cancelarURL != null) {
            ImageIcon iconCancelar = new ImageIcon(new ImageIcon(cancelarURL).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
            btnCancelar.setIcon(iconCancelar);
        } else {
            System.out.println("Error al cargar cancelarcontra.png");
        }
    }

    /**
     * Actualiza todos los textos visibles en la interfaz gráfica según el idioma actual.
     * Se actualizan: título, etiquetas y textos de los botones.
     */
    public void actualizarTextos() {
        setTitle(mensaje.get("recuperar.nueva.titulo"));
        lblTitulo.setText(mensaje.get("recuperar.nueva.titulo"));
        lblNueva.setText(mensaje.get("recuperar.nueva.contrasena"));
        lblConfirmar.setText(mensaje.get("recuperar.nueva.confirmar"));
        btnAceptar.setText(mensaje.get("aceptar"));
        btnCancelar.setText(mensaje.get("cancelar"));
    }


    /**
     * Retorna el panel principal que contiene todos los componentes visuales.
     * @return el panel principal
     */
    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    /**
     * Asigna un nuevo panel principal a la vista.
     * @param panelPrincipal nuevo panel principal
     */
    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    /**
     * Obtiene la etiqueta del título principal.
     * @return etiqueta del título
     */
    public JLabel getLblTitulo() {
        return lblTitulo;
    }

    /**
     * Establece la etiqueta del título principal.
     * @param lblTitulo nueva etiqueta del título
     */
    public void setLblTitulo(JLabel lblTitulo) {
        this.lblTitulo = lblTitulo;
    }

    /**
     * Retorna la etiqueta que indica el campo para nueva contraseña.
     * @return etiqueta "nueva contraseña"
     */
    public JLabel getLblNueva() {
        return lblNueva;
    }

    /**
     * Asigna un nuevo texto o componente a la etiqueta de nueva contraseña.
     * @param lblNueva etiqueta de nueva contraseña
     */
    public void setLblNueva(JLabel lblNueva) {
        this.lblNueva = lblNueva;
    }

    /**
     * Obtiene la etiqueta para confirmar la contraseña.
     * @return etiqueta de confirmar contraseña
     */
    public JLabel getLblConfirmar() {
        return lblConfirmar;
    }

    /**
     * Asigna una nueva etiqueta para confirmar la contraseña.
     * @param lblConfirmar etiqueta para campo de confirmación
     */
    public void setLblConfirmar(JLabel lblConfirmar) {
        this.lblConfirmar = lblConfirmar;
    }

    /**
     * Obtiene el campo de texto para ingresar la nueva contraseña.
     * @return campo de nueva contraseña
     */
    public JPasswordField getTxtNueva() {
        return txtNueva;
    }

    /**
     * Establece un nuevo campo para la nueva contraseña.
     * @param txtNueva campo de nueva contraseña
     */
    public void setTxtNueva(JPasswordField txtNueva) {
        this.txtNueva = txtNueva;
    }

    /**
     * Obtiene el campo para confirmar la nueva contraseña.
     * @return campo de confirmación de contraseña
     */
    public JPasswordField getTxtConfirmar() {
        return txtConfirmar;
    }

    /**
     * Asigna un nuevo campo para la confirmación de contraseña.
     * @param txtConfirmar campo para confirmar contraseña
     */
    public void setTxtConfirmar(JPasswordField txtConfirmar) {
        this.txtConfirmar = txtConfirmar;
    }

    /**
     * Devuelve el botón para aceptar y guardar la nueva contraseña.
     * @return botón Aceptar
     */
    public JButton getBtnAceptar() {
        return btnAceptar;
    }

    /**
     * Asigna un nuevo botón para aceptar el cambio de contraseña.
     * @param btnAceptar botón Aceptar
     */
    public void setBtnAceptar(JButton btnAceptar) {
        this.btnAceptar = btnAceptar;
    }

    /**
     * Devuelve el botón para cancelar el proceso.
     * @return botón Cancelar
     */
    public JButton getBtnCancelar() {
        return btnCancelar;
    }

    /**
     * Asigna un nuevo botón para cancelar el cambio de contraseña.
     * @param btnCancelar botón Cancelar
     */
    public void setBtnCancelar(JButton btnCancelar) {
        this.btnCancelar = btnCancelar;
    }

    /**
     * Retorna el manejador de internacionalización utilizado por la vista.
     * @return objeto {@link MensajeInternacionalizacionHandler}
     */
    public MensajeInternacionalizacionHandler getMensaje() {
        return mensaje;
    }

    /**
     * Asigna un nuevo manejador de internacionalización.
     * @param mensaje manejador de internacionalización
     */
    public void setMensaje(MensajeInternacionalizacionHandler mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * Muestra un mensaje traducido según la clave recibida.
     * @param mensajeClave clave para obtener el texto traducido
     */
    public void mostrarMensaje(String mensajeClave) {
        JOptionPane.showMessageDialog(this, mensaje.get(mensajeClave));
    }

    /**
     * Limpia los campos de texto para nueva contraseña y confirmación.
     * Útil para reiniciar el formulario después de un registro exitoso.
     */
    public void limpiarCampos() {
        txtNueva.setText("");
        txtConfirmar.setText("");
    }

}