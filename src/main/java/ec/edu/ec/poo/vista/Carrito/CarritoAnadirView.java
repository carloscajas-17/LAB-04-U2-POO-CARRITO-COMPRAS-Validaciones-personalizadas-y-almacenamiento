package ec.edu.ec.poo.vista.Carrito;

import ec.edu.ec.poo.utils.ButtonEditor;
import ec.edu.ec.poo.utils.ButtonRenderer;
import ec.edu.ec.poo.utils.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.net.URL;

public class CarritoAnadirView extends JInternalFrame {
    private JButton btnBuscar;
    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JButton btnAnadir;
    private JTable tblProductos;
    private JTextField txtSubtotal;
    private JTextField txtIva;
    private JTextField txtTotal;
    private JButton btnGuardar;
    private JButton btnLimpiar;
    private JComboBox cbxCantidad;
    private JPanel panelPrincipal;
    private JTextField txtCodigoCarrito;
    private JTextField txtUsuario;

    private MensajeInternacionalizacionHandler mensajeInternacionalizacion;

    public CarritoAnadirView(MensajeInternacionalizacionHandler mensajeInternacionalizacion){
        super("Carrito de Compras", true, true, false, true);
        this.mensajeInternacionalizacion = mensajeInternacionalizacion;
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(700, 500); // ajustado por ancho de botones

        DefaultTableModel modelo = new DefaultTableModel();
        Object[] columnas = {"Codigo", "Nombre", "Precio", "Cantidad", "Subtotal", "Acciones"};
        modelo.setColumnIdentifiers(columnas);
        tblProductos.setModel(modelo);

        // ✅ Integrar botones "Modificar" y "Eliminar"
        tblProductos.getColumn("Acciones").setCellRenderer(new ButtonRenderer());

        ButtonEditor editor = new ButtonEditor();
        editor.addActionListener(e -> {
            String comando = e.getActionCommand(); // Ej: modify:1 o delete:2
            System.out.println("Acción recibida: " + comando);

            // Aquí puedes invocar al controlador para manejar las acciones:
            // String[] partes = comando.split(":");
            // if (partes[0].equals("modify")) { ... }
        });

        tblProductos.getColumn("Acciones").setCellEditor(editor);

        cargarDatos();
        URL buscarURL = CarritoAnadirView.class.getClassLoader().getResource("imagenes/buscar.png");
        if (buscarURL != null) {
            ImageIcon iconBtnBuscar = new ImageIcon(new ImageIcon(buscarURL).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
            btnBuscar.setIcon(iconBtnBuscar);
        } else {
            System.out.println("Error al cargar buscar.png");
        }

        URL anadirURL = CarritoAnadirView.class.getClassLoader().getResource("imagenes/añadircarr.png");
        if (anadirURL != null) {
            ImageIcon iconBtnAnadir = new ImageIcon(new ImageIcon(anadirURL).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
            btnAnadir.setIcon(iconBtnAnadir);
        } else {
            System.out.println("Error al cargar añadircarr.png");
        }

        URL guardarURL = CarritoAnadirView.class.getClassLoader().getResource("imagenes/guardarcarr.png");
        if (guardarURL != null) {
            ImageIcon iconBtnGuardar = new ImageIcon(new ImageIcon(guardarURL).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
            btnGuardar.setIcon(iconBtnGuardar);
        } else {
            System.out.println("Error al cargar guardarcarr.png");
        }

        URL limpiarURL = CarritoAnadirView.class.getClassLoader().getResource("imagenes/limpiarcarr.png");
        if (limpiarURL != null) {
            ImageIcon iconBtnLimpiar = new ImageIcon(new ImageIcon(limpiarURL).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
            btnLimpiar.setIcon(iconBtnLimpiar);
        } else {
            System.out.println("Error al cargar limpiarcarr.png");
        }

    }
    public void cambiarIdioma(String lang, String country) {
        mensajeInternacionalizacion.setLenguaje(lang, country);
        actualizarTextos();
    }


    private void actualizarTextos() {
        setTitle(mensajeInternacionalizacion.get("carrito.anadir.titulo"));
        txtCodigo.setText(mensajeInternacionalizacion.get("codigo"));
        txtNombre.setText(mensajeInternacionalizacion.get("nombre"));
        txtPrecio.setText(mensajeInternacionalizacion.get("precio"));
        cbxCantidad.setSelectedItem(mensajeInternacionalizacion.get("cantidad"));
        txtSubtotal.setText(mensajeInternacionalizacion.get("subtotal"));
        txtIva.setText(mensajeInternacionalizacion.get("iva"));
        txtTotal.setText(mensajeInternacionalizacion.get("total"));

        btnBuscar.setText(mensajeInternacionalizacion.get("buscar"));
        btnAnadir.setText(mensajeInternacionalizacion.get("anadir"));
        btnGuardar.setText(mensajeInternacionalizacion.get("guardar"));
        btnLimpiar.setText(mensajeInternacionalizacion.get("limpiar"));
    }



    private void cargarDatos(){
        cbxCantidad.removeAllItems();
        for(int i = 0; i < 20; i++){
            cbxCantidad.addItem(String.valueOf(i + 1));
        }
    }





    public ButtonEditor getButtonEditor() {
        return (ButtonEditor) tblProductos.getColumn("Acciones").getCellEditor();
    }



    public JButton getBtnBuscar() {
        return btnBuscar;
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

    public JButton getBtnAnadir() {
        return btnAnadir;
    }

    public JTable getTblProductos() {
        return tblProductos;
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

    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    public JButton getBtnLimpiar() {
        return btnLimpiar;
    }

    public JComboBox getCbxCantidad() {
        return cbxCantidad;
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public JTextField getTxtCodigoCarrito() {
        return txtCodigoCarrito;
    }

    public JTextField getTxtUsuario() {
        return txtUsuario;
    }

    public MensajeInternacionalizacionHandler getMensajeInternacionalizacion() {
        return mensajeInternacionalizacion;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}
