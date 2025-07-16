package ec.edu.ec.poo.vista.Producto;
import ec.edu.ec.poo.utils.MensajeInternacionalizacionHandler;
import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class ProductoModificarView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JButton btnBuscar;
    private JButton btnModificar;

    private MensajeInternacionalizacionHandler mensaje;

    public ProductoModificarView(MensajeInternacionalizacionHandler mensaje) {
        this.mensaje = mensaje;
        initComponents();
        actualizarTextos();

        // Ícono para el botón Buscar
        URL buscarURL = ProductoModificarView.class.getClassLoader().getResource("imagenes/buscarproducto.png");
        if (buscarURL != null) {
            ImageIcon iconBuscar = new ImageIcon(new ImageIcon(buscarURL).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
            btnBuscar.setIcon(iconBuscar);
        } else {
            System.out.println("Error al cargar buscarproducto.png");
        }

// Ícono para el botón Modificar
        URL modificarURL = ProductoModificarView.class.getClassLoader().getResource("imagenes/modificarproduc.png");
        if (modificarURL != null) {
            ImageIcon iconModificar = new ImageIcon(new ImageIcon(modificarURL).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
            btnModificar.setIcon(iconModificar);
        } else {
            System.out.println("Error al cargar modificarproduc.png");
        }

    }
    private void initComponents() {
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 200);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        //setLocationRelativeTo(null);
    }
    private void actualizarTextos() {
        setTitle(mensaje.get("producto.modificar.titulo"));
        txtCodigo.setText(mensaje.get("codigo"));
        txtNombre.setText(mensaje.get("nombre"));
        txtPrecio.setText(mensaje.get("precio"));

        btnBuscar.setText(mensaje.get("buscar")); // ✅ SOLO buscar
        btnModificar.setText(mensaje.get("actualizar"));
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

    public JButton getBtnModificar() {
        return btnModificar;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
    public void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
    }
}
