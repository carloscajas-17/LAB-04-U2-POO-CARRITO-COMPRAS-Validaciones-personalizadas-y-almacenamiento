package ec.edu.ec.poo.vista.Producto;

import ec.edu.ec.poo.modelo.Producto;
import ec.edu.ec.poo.utils.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.net.URL;
import java.util.List;

public class ProductoListaView extends JInternalFrame {

    private JTextField txtBuscar;
    private JButton btnBuscar;
    private JTable tblProductos;
    private JPanel panelPrincipal;
    private JButton btnListar;
    private DefaultTableModel modelo;
    private MensajeInternacionalizacionHandler mensaje;

    public ProductoListaView(MensajeInternacionalizacionHandler mensaje) {
        this.mensaje = mensaje;
        initComponents();
        actualizarTextos();

        // Ícono para el botón Buscar
        URL buscarURL = ProductoListaView.class.getClassLoader().getResource("imagenes/buscarproducto.png");
        if (buscarURL != null) {
            ImageIcon iconBuscar = new ImageIcon(new ImageIcon(buscarURL).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
            btnBuscar.setIcon(iconBuscar);
        } else {
            System.out.println("Error al cargar buscarproducto.png");
        }

// Ícono para el botón Listar
        URL listarURL = ProductoListaView.class.getClassLoader().getResource("imagenes/listarproductos.png");
        if (listarURL != null) {
            ImageIcon iconListar = new ImageIcon(new ImageIcon(listarURL).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
            btnListar.setIcon(iconListar);
        } else {
            System.out.println("Error al cargar listarproductos.png");
        }

    }
    private void initComponents() {
        setContentPane(panelPrincipal);
        setSize(500, 500);
        //setLocationRelativeTo(null);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);

        configurarTabla();
    }


    private void configurarTabla() {
        modelo = new DefaultTableModel();
        Object[] columnas = {
                mensaje.get("codigo"),
                mensaje.get("nombre"),
                mensaje.get("precio")
        };
        modelo.setColumnIdentifiers(columnas);
        tblProductos.setModel(modelo);
    }
    private void actualizarTextos() {
        setTitle(mensaje.get("producto.lista.titulo"));
        btnBuscar.setText(mensaje.get("buscar"));
        btnListar.setText(mensaje.get("listar"));
        txtBuscar.setToolTipText(mensaje.get("buscar.tooltip")); // (opcional)
    }


    public void cambiarIdioma(String lenguaje, String pais) {
        mensaje.setLenguaje(lenguaje, pais);
        actualizarTextos();
        configurarTabla();
    }


    public JTextField getTxtBuscar() {
        return txtBuscar;
    }

    public void setTxtBuscar(JTextField txtBuscar) {
        this.txtBuscar = txtBuscar;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public void setBtnBuscar(JButton btnBuscar) {
        this.btnBuscar = btnBuscar;
    }

    public JTable getTblProductos() {
        return tblProductos;
    }

    public void setTblProductos(JTable tblProductos) {
        this.tblProductos = tblProductos;
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    public JButton getBtnListar() {
        return btnListar;
    }

    public void setBtnListar(JButton btnListar) {
        this.btnListar = btnListar;
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }

    public void setModelo(DefaultTableModel modelo) {
        this.modelo = modelo;
    }

    public void cargarDatos(List<Producto> listaProductos) {
        modelo.setNumRows(0);
        for (Producto producto : listaProductos) {
            Object[] fila = {
                    producto.getCodigo(),
                    producto.getNombre(),
                    producto.getPrecio()
            };
            modelo.addRow(fila);
        }
    }
}



