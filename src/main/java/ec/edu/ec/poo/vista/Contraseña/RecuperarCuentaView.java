package ec.edu.ec.poo.vista.Contraseña;
import ec.edu.ec.poo.modelo.RespuestaSeguridad;
import ec.edu.ec.poo.utils.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class RecuperarCuentaView extends JDialog {
    private JPanel panelPrincipal;
    private JLabel lblTitulo;
    private JLabel lblId;
    private JLabel lblNombre;
    private JLabel lblPregunta;
    private JTextField txtId;
    private JTextField txtNombre;
    private JTextField txtRespuesta;
    private JButton btnValidarUsuario;
    private JButton btnValidarPregunta;
    private JButton btnAceptar;
    private JButton btnCancelar;
    private RespuestaSeguridad preguntaActual;
    private MensajeInternacionalizacionHandler mensaje;


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

    private void initComponents() {
        setContentPane(panelPrincipal);
        setModal(true);
        setSize(450, 300);
        setLocationRelativeTo(null);
        btnAceptar.setEnabled(false);
    }
    public void cambiarIdioma(String lang, String country) {
        mensaje.setLenguaje(lang, country);
        actualizarTextos();
    }
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





    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    public JLabel getLblTitulo() {
        return lblTitulo;
    }

    public void setLblTitulo(JLabel lblTitulo) {
        this.lblTitulo = lblTitulo;
    }

    public JLabel getLblId() {
        return lblId;
    }

    public void setLblId(JLabel lblId) {
        this.lblId = lblId;
    }

    public JLabel getLblNombre() {
        return lblNombre;
    }

    public void setLblNombre(JLabel lblNombre) {
        this.lblNombre = lblNombre;
    }

    public JLabel getLblPregunta() {
        return lblPregunta;
    }

    public void setLblPregunta(JLabel lblPregunta) {
        this.lblPregunta = lblPregunta;
    }

    public JTextField getTxtId() {
        return txtId;
    }

    public void setTxtId(JTextField txtId) {
        this.txtId = txtId;
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public void setTxtNombre(JTextField txtNombre) {
        this.txtNombre = txtNombre;
    }

    public JTextField getTxtRespuesta() {
        return txtRespuesta;
    }

    public void setTxtRespuesta(JTextField txtRespuesta) {
        this.txtRespuesta = txtRespuesta;
    }

    public JButton getBtnValidarUsuario() {
        return btnValidarUsuario;
    }

    public void setBtnValidarUsuario(JButton btnValidarUsuario) {
        this.btnValidarUsuario = btnValidarUsuario;
    }

    public JButton getBtnValidarPregunta() {
        return btnValidarPregunta;
    }

    public void setBtnValidarPregunta(JButton btnValidarPregunta) {
        this.btnValidarPregunta = btnValidarPregunta;
    }

    public JButton getBtnAceptar() {
        return btnAceptar;
    }

    public void setBtnAceptar(JButton btnAceptar) {
        this.btnAceptar = btnAceptar;
    }

    public JButton getBtnCancelar() {
        return btnCancelar;
    }

    public void setBtnCancelar(JButton btnCancelar) {
        this.btnCancelar = btnCancelar;
    }

    public MensajeInternacionalizacionHandler getMensaje() {
        return mensaje;
    }
    public RespuestaSeguridad getPreguntaActual() {
        return preguntaActual;
    }

    public void setPreguntaActual(RespuestaSeguridad preguntaActual) {
        this.preguntaActual = preguntaActual;
    }


    public void setMensaje(MensajeInternacionalizacionHandler mensaje) {
        this.mensaje = mensaje;
    }

    public void mostrarMensaje(String texto) {
        JOptionPane.showMessageDialog(this, texto);
    }



}
