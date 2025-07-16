package ec.edu.ec.poo.vista.Usuario;

import ec.edu.ec.poo.utils.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class UsuarioModificarView extends JInternalFrame {
    private JPanel pnlPrincipal;
    private JLabel lblTitulo;
    private JLabel lblUsuario;
    private JLabel lblContrasenia;
    private JLabel lblConfirmar;
    private JTextField txtUsuario;
    private JPasswordField txtContrasenia;
    private JPasswordField txtConfirmar;
    private JButton btnModificar;
    private JButton btnMostrar;
    private MensajeInternacionalizacionHandler mensaje;

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

    private void initComponent() {
        setContentPane(pnlPrincipal);
        setClosable(true);
        setResizable(true);
        setSize(500, 500);

    }

    private void actualizarTextos() {
        setTitle(mensaje.get("usuario.modificar.titulo"));

        lblTitulo.setText(mensaje.get("usuario.modificar.titulo"));
        lblUsuario.setText(mensaje.get("usuario"));
        lblContrasenia.setText(mensaje.get("contrasenia"));
        lblConfirmar.setText(mensaje.get("confirmar"));

        btnModificar.setText(mensaje.get("modificar"));
        btnMostrar.setText(mensaje.get("mostrar"));
    }
    public void cambiarIdioma(String lang, String country) {
        mensaje.setLenguaje(lang, country);
        actualizarTextos(); // Asegúrate de que este método existe y cambia TODO
    }


    public JPanel getPnlPrincipal() {
        return pnlPrincipal;
    }

    public void setPnlPrincipal(JPanel pnlPrincipal) {
        this.pnlPrincipal = pnlPrincipal;
    }

    public JLabel getLblTitulo() {
        return lblTitulo;
    }

    public void setLblTitulo(JLabel lblTitulo) {
        this.lblTitulo = lblTitulo;
    }

    public JLabel getLblUsuario() {
        return lblUsuario;
    }

    public void setLblUsuario(JLabel lblUsuario) {
        this.lblUsuario = lblUsuario;
    }

    public JLabel getLblContrasenia() {
        return lblContrasenia;
    }

    public void setLblContrasenia(JLabel lblContrasenia) {
        this.lblContrasenia = lblContrasenia;
    }

    public JTextField getTxtUsuario() {
        return txtUsuario;
    }

    public void setTxtUsuario(JTextField txtUsuario) {
        this.txtUsuario = txtUsuario;
    }

    public JPasswordField getTxtContrasenia() {
        return txtContrasenia;
    }

    public void setTxtContrasenia(JPasswordField txtContrasenia) {
        this.txtContrasenia = txtContrasenia;
    }

    public JPasswordField getTxtConfirmar() {
        return txtConfirmar;
    }

    public void setTxtConfirmar(JPasswordField txtConfirmar) {
        this.txtConfirmar = txtConfirmar;
    }

    public JButton getBtnModificar() {
        return btnModificar;
    }

    public void setBtnModificar(JButton btnModificar) {
        this.btnModificar = btnModificar;
    }

    public JButton getBtnMostrar() {
        return btnMostrar;
    }

    public void setBtnMostrar(JButton btnMostrar) {
        this.btnMostrar = btnMostrar;
    }

    public MensajeInternacionalizacionHandler getMensaje() {
        return mensaje;
    }

    public void setMensaje(MensajeInternacionalizacionHandler mensaje) {
        this.mensaje = mensaje;
    }

    public void mostrarMensaje(String keyMensaje) {
        JOptionPane.showMessageDialog(this,mensaje.get(keyMensaje));
    }

    public void limpiarCampos() {
        txtUsuario.setText("");
        txtContrasenia.setText("");
        txtConfirmar.setText("");
    }
}
