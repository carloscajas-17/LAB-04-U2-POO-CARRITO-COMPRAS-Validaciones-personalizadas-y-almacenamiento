package ec.edu.ec.poo.vista.Carrito;

import ec.edu.ec.poo.utils.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.net.URL;

public class CarritoDetalleView extends JDialog {
    private JPanel pnlPrincipal;
    private JPanel pnlSuperior;
    private JPanel pnlInferior;
    private JTextField txtUsuario;
    private JTextField txtFecha;
    private JTextField txtSubtotal;
    private JTextField txtIva;
    private JTextField txtTotal;
    private JTable tblCarrito;
    private JLabel lblUsuario;
    private JLabel lblFecha;
    private JLabel lblSubtotal;
    private JLabel lblIva;
    private JLabel lblTotal;
    private JButton btnVolver;

    private JTextField txtCodigo;
    private JLabel lblCodigo;
    private DefaultTableModel modelo;
    private MensajeInternacionalizacionHandler mensaje;

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

    private void initComponents() {
        setContentPane(pnlPrincipal);
        setSize(600, 400);
        setModal(true);

        configurarTabla();
    }

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


    public void cambiarIdioma(String lenguaje, String pais) {
        mensaje.setLenguaje(lenguaje, pais);
        actualizarTextos();
        configurarTabla();
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

    public JTextField getTxtUsuario() {
        return txtUsuario;
    }

    public JTextField getTxtFecha() {
        return txtFecha;
    }

    public JTextField getTxtSubtotal() {
        return txtSubtotal;
    }

    public JTextField getTxtIva() {
        return txtIva;
    }

    public JTextField getTxtTotal() {
        return txtTotal;
    }

    public JTable getTblCarrito() {
        return tblCarrito;
    }

    public JLabel getLblUsuario() {
        return lblUsuario;
    }

    public JLabel getLblFecha() {
        return lblFecha;
    }

    public JLabel getLblSubtotal() {
        return lblSubtotal;
    }

    public JLabel getLblIva() {
        return lblIva;
    }

    public JLabel getLblTotal() {
        return lblTotal;
    }

    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    public JLabel getLblCodigo() {
        return lblCodigo;
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }


    public JButton getBtnVolver() {
        return btnVolver;
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