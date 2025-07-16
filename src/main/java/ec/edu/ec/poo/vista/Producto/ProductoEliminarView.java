package ec.edu.ec.poo.vista.Producto;
import ec.edu.ec.poo.utils.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class ProductoEliminarView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JButton btnBuscar;
    private JButton btnEliminar;

    private MensajeInternacionalizacionHandler mensaje;

    public ProductoEliminarView(MensajeInternacionalizacionHandler mensaje) {
        this.mensaje = mensaje;
        initComponents();
        actualizarTextos();

        // Ícono para el botón Buscar
        URL buscarURL = ProductoEliminarView.class.getClassLoader().getResource("imagenes/buscarproducto.png");
        if (buscarURL != null) {
            ImageIcon iconBuscar = new ImageIcon(new ImageIcon(buscarURL).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
            btnBuscar.setIcon(iconBuscar);
        } else {
            System.out.println("Error al cargar buscarproductoactu.png");
        }

// Ícono para el botón Eliminar
        URL eliminarURL = ProductoEliminarView.class.getClassLoader().getResource("imagenes/eliminarproducto.png");
        if (eliminarURL != null) {
            ImageIcon iconEliminar = new ImageIcon(new ImageIcon(eliminarURL).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
            btnEliminar.setIcon(iconEliminar);
        } else {
            System.out.println("Error al cargar eliminarcarr.png");
        }

    }

    private void initComponents() {
        setContentPane(panelPrincipal);
        setSize(500, 200);
        //setLocationRelativeTo(null);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);
    }
    private void actualizarTextos() {
        setTitle(mensaje.get("producto.eliminar.titulo"));

        // No pongas setText en campos de entrada
        // Ejemplo incorrecto (que debes eliminar):
        // txtCodigo.setText(mensaje.get("codigo"));

        btnBuscar.setText(mensaje.get("buscar"));
        btnEliminar.setText(mensaje.get("eliminar"));
    }

    public void cambiarIdioma(String lenguaje, String pais) {
        mensaje.setLenguaje(lenguaje, pais);
        actualizarTextos();
    }


    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public JTextField getTxtPrecio() {
        return txtPrecio;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    public MensajeInternacionalizacionHandler getMensaje() {
        return mensaje;
    }

    public void setMensaje(MensajeInternacionalizacionHandler mensaje) {
        this.mensaje = mensaje;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public boolean confirmarEliminacion() {
        int opcion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de que desea eliminar este producto?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);
        return opcion == JOptionPane.YES_OPTION;
    }

    public void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
    }
}


