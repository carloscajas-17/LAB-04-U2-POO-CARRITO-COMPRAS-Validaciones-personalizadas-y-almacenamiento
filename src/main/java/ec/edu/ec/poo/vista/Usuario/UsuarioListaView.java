package ec.edu.ec.poo.vista.Usuario;



import ec.edu.ec.poo.utils.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.net.URL;

public class UsuarioListaView extends JInternalFrame {
    private JPanel pnlPrincipal;
    private JPanel pnlSuperior;
    private JPanel pnlInferior;
    private JTextField txtUsuario;
    private JButton btnBuscar;
    private JButton btnLimpiar;
    private JLabel lblTitulo;
    private JLabel lblUsuario;
    private JTable tblDetalle;
    private MensajeInternacionalizacionHandler mensaje;

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

    private void initComponents() {
        setContentPane(pnlPrincipal);
        setClosable(true);
        setResizable(true);
        setSize(500, 500);

        configurarTabla();
    }

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
    public void actualizarTextos() {
        setTitle(mensaje.get("usuario.lista.view"));

        lblTitulo.setText(mensaje.get("usuario.lista.view"));
        lblUsuario.setText(mensaje.get("usuario"));

        btnBuscar.setText(mensaje.get("buscar"));
        btnLimpiar.setText(mensaje.get("limpiar"));

        configurarTabla(); // 👉 También se actualizan los textos de las columnas
    }

    public void cambiarIdioma(String lang, String country) {
        mensaje.setLenguaje(lang, country);
        actualizarTextos();
    }

    public JPanel getPnlPrincipal() {
        return pnlPrincipal;
    }

    public void setPnlPrincipal(JPanel pnlPrincipal) {
        this.pnlPrincipal = pnlPrincipal;
    }

    public JPanel getPnlSuperior() {
        return pnlSuperior;
    }

    public void setPnlSuperior(JPanel pnlSuperior) {
        this.pnlSuperior = pnlSuperior;
    }

    public JPanel getPnlInferior() {
        return pnlInferior;
    }

    public void setPnlInferior(JPanel pnlInferior) {
        this.pnlInferior = pnlInferior;
    }

    public JTextField getTxtUsuario() {
        return txtUsuario;
    }

    public void setTxtUsuario(JTextField txtUsuario) {
        this.txtUsuario = txtUsuario;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public void setBtnBuscar(JButton btnBuscar) {
        this.btnBuscar = btnBuscar;
    }

    public JButton getBtnLimpiar() {
        return btnLimpiar;
    }

    public void setBtnLimpiar(JButton btnLimpiar) {
        this.btnLimpiar = btnLimpiar;
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

    public JTable getTblDetalle() {
        return tblDetalle;
    }

    public void setTblDetalle(JTable tblDetalle) {
        this.tblDetalle = tblDetalle;
    }

    public MensajeInternacionalizacionHandler getMensaje() {
        return mensaje;
    }

    public void setMensaje(MensajeInternacionalizacionHandler mensaje) {
        this.mensaje = mensaje;
    }

    public void mostrarMensaje(String keyMensaje) {
        JOptionPane.showMessageDialog(this, mensaje.get(keyMensaje));
    }
}
