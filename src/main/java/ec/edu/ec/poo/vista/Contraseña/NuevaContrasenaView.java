package ec.edu.ec.poo.vista.Contraseña;

import ec.edu.ec.poo.utils.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class NuevaContrasenaView extends JDialog {
    private JPanel panelPrincipal;
    private JLabel lblTitulo;
    private JLabel lblNueva;
    private JLabel lblConfirmar;
    private JPasswordField txtNueva;
    private JPasswordField txtConfirmar;
    private JButton btnAceptar;
    private JButton btnCancelar;

    private MensajeInternacionalizacionHandler mensaje;

    public NuevaContrasenaView(MensajeInternacionalizacionHandler mensaje) {
        this.mensaje = mensaje;
        initComponents();
        actualizarTextos();
        setModal(true);
    }
    private void initComponents() {
        setContentPane(panelPrincipal);
        setTitle("Recuperar Contraseña");
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        setSize(400, 250);
        setLocationRelativeTo(null);
    }
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
    public void actualizarTextos() {
        setTitle(mensaje.get("recuperar.nueva.titulo"));
        lblTitulo.setText(mensaje.get("recuperar.nueva.titulo"));
        lblNueva.setText(mensaje.get("recuperar.nueva.contrasena"));
        lblConfirmar.setText(mensaje.get("recuperar.nueva.confirmar"));
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

    public JLabel getLblNueva() {
        return lblNueva;
    }

    public void setLblNueva(JLabel lblNueva) {
        this.lblNueva = lblNueva;
    }

    public JLabel getLblConfirmar() {
        return lblConfirmar;
    }

    public void setLblConfirmar(JLabel lblConfirmar) {
        this.lblConfirmar = lblConfirmar;
    }

    public JPasswordField getTxtNueva() {
        return txtNueva;
    }

    public void setTxtNueva(JPasswordField txtNueva) {
        this.txtNueva = txtNueva;
    }

    public JPasswordField getTxtConfirmar() {
        return txtConfirmar;
    }

    public void setTxtConfirmar(JPasswordField txtConfirmar) {
        this.txtConfirmar = txtConfirmar;
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

    public void setMensaje(MensajeInternacionalizacionHandler mensaje) {
        this.mensaje = mensaje;
    }
    public void mostrarMensaje(String mensajeClave) {
        JOptionPane.showMessageDialog(this, mensaje.get(mensajeClave));
    }
    public void limpiarCampos() {
        txtNueva.setText("");
        txtConfirmar.setText("");
    }
}