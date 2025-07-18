package ec.edu.ec.poo.vista.Producto;

import ec.edu.ec.poo.modelo.Producto;
import ec.edu.ec.poo.utils.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.net.URL;
import java.util.List;

/**
 * Vista gráfica para listar y buscar productos registrados en el sistema.
 * Permite visualizar los productos mediante una tabla y realizar búsquedas por nombre o listar todos.
 * Soporta internacionalización dinámica.
 */
public class ProductoListaView extends JInternalFrame {

    /** Campo de texto para ingresar el nombre o código de producto a buscar */
    private JTextField txtBuscar;

    /** Botón para realizar la búsqueda de productos */
    private JButton btnBuscar;

    /** Tabla que muestra los productos encontrados */
    private JTable tblProductos;

    /** Panel principal que contiene todos los componentes gráficos */
    private JPanel panelPrincipal;

    /** Botón para listar todos los productos registrados */
    private JButton btnListar;

    /** Modelo de tabla encargado de manejar los datos mostrados */
    private DefaultTableModel modelo;

    /** Manejador para internacionalización (traducción de textos) */
    private MensajeInternacionalizacionHandler mensaje;

    /**
     * Constructor que inicializa la vista con el manejador de internacionalización.
     * @param mensaje objeto para gestionar traducciones dinámicas.
     */
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


    /**
     * Inicializa los componentes visuales y configura la tabla de productos.
     */
    private void initComponents() {
        setContentPane(panelPrincipal);
        setSize(500, 500);
        //setLocationRelativeTo(null);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);

        configurarTabla();
    }


    /**
     * Configura las columnas y cabecera de la tabla según el idioma seleccionado.
     * Define las columnas: Código, Nombre y Precio.
     */
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


    /**
     * Actualiza los textos visuales (botones, título y tooltips) según el idioma actual.
     */
    private void actualizarTextos() {
        setTitle(mensaje.get("producto.lista.titulo"));
        btnBuscar.setText(mensaje.get("buscar"));
        btnListar.setText(mensaje.get("listar"));
        txtBuscar.setToolTipText(mensaje.get("buscar.tooltip")); // (opcional)
    }


    /**
     * Cambia el idioma de la vista y actualiza todos los textos y cabecera de tabla.
     * @param lenguaje código de idioma (ej: "es")
     * @param pais código de país (ej: "EC")
     */
    public void cambiarIdioma(String lenguaje, String pais) {
        mensaje.setLenguaje(lenguaje, pais);
        actualizarTextos();
        configurarTabla();
    }


    /**
     * Obtiene el campo de texto para ingresar el nombre o código del producto a buscar.
     * @return JTextField para búsqueda de productos.
     */
    public JTextField getTxtBuscar() {
        return txtBuscar;
    }

    /**
     * Asigna un nuevo campo de texto para ingresar el nombre o código del producto a buscar.
     * @param txtBuscar JTextField para búsqueda de productos.
     */
    public void setTxtBuscar(JTextField txtBuscar) {
        this.txtBuscar = txtBuscar;
    }

    /**
     * Obtiene el botón que permite realizar la búsqueda de productos.
     * @return JButton para búsqueda.
     */
    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    /**
     * Asigna el botón encargado de realizar la búsqueda de productos.
     * @param btnBuscar JButton para búsqueda.
     */
    public void setBtnBuscar(JButton btnBuscar) {
        this.btnBuscar = btnBuscar;
    }

    /**
     * Obtiene la tabla donde se listan los productos encontrados.
     * @return JTable que muestra los productos.
     */
    public JTable getTblProductos() {
        return tblProductos;
    }

    /**
     * Establece una nueva tabla para mostrar los productos.
     * @param tblProductos JTable que muestra los productos.
     */
    public void setTblProductos(JTable tblProductos) {
        this.tblProductos = tblProductos;
    }

    /**
     * Obtiene el panel principal que contiene todos los componentes gráficos de la vista.
     * @return JPanel principal.
     */
    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    /**
     * Asigna un nuevo panel principal para la vista.
     * @param panelPrincipal JPanel principal.
     */
    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    /**
     * Obtiene el botón encargado de listar todos los productos registrados.
     * @return JButton para listar productos.
     */
    public JButton getBtnListar() {
        return btnListar;
    }


    /**
     * Asigna el botón que permite listar todos los productos registrados.
     * @param btnListar JButton para listar productos.
     */
    public void setBtnListar(JButton btnListar) {
        this.btnListar = btnListar;
    }

    /**
     * Obtiene el modelo de la tabla que gestiona las filas y columnas de productos.
     * @return DefaultTableModel asociado a la tabla de productos.
     */
    public DefaultTableModel getModelo() {
        return modelo;
    }

    /**
     * Asigna un nuevo modelo de tabla para gestionar los datos de productos mostrados.
     * @param modelo DefaultTableModel a establecer.
     */
    public void setModelo(DefaultTableModel modelo) {
        this.modelo = modelo;
    }

    /**
     * Carga los datos de una lista de productos en la tabla de la interfaz gráfica.
     *
     * Este método limpia primero el contenido actual de la tabla y luego
     * agrega cada producto como una nueva fila mostrando código, nombre y precio.
     *
     * @param listaProductos lista de productos a mostrar en la tabla.
     */
    /**
     * Muestra un mensaje emergente con la información deseada.
     * @param mensaje texto que se mostrará en el diálogo
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void cargarDatos(List<Producto> listaProductos) {
        modelo.setNumRows(0); // Limpia la tabla
        for (Producto producto : listaProductos) {
            Object[] fila = {
                    producto.getCodigo(),
                    producto.getNombre(),
                    producto.getPrecio()
            };
            modelo.addRow(fila); // Agrega la fila a la tabla
        }
    }


}



