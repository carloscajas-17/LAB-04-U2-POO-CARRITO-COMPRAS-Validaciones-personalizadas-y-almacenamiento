package ec.edu.ec.poo.vista.Producto;

import ec.edu.ec.poo.modelo.Producto;
import ec.edu.ec.poo.utils.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.List;


/**
 * Vista gráfica para añadir productos dentro del sistema.
 * Permite ingresar código, nombre y precio de un producto.
 * Integra internacionalización para cambiar el idioma dinámicamente.
 */
public class ProductoAnadirView extends JInternalFrame {

    /** Panel principal de la ventana */
    private JPanel panelPrincipal;

    /** Campo de texto para ingresar el precio del producto */
    private JTextField txtPrecio;

    /** Campo de texto para ingresar el nombre del producto */
    private JTextField txtNombre;

    /** Campo de texto para ingresar el código del producto */
    private JTextField txtCodigo;

    /** Botón para aceptar o registrar un nuevo producto */
    private JButton btnAceptar;

    /** Botón para limpiar los campos del formulario */
    private JButton btnLimpiar;

    /** Manejador para internacionalización */
    private MensajeInternacionalizacionHandler mensaje;

    /**
     * Constructor de la vista para añadir productos.
     * @param mensaje instancia del manejador de internacionalización
     */
    public ProductoAnadirView(MensajeInternacionalizacionHandler mensaje) {
        this.mensaje = mensaje;
        initComponents();
        configurarListeners();
        actualizarTextos();

        // Ícono para el botón Aceptar
        URL aceptarURL = ProductoAnadirView.class.getClassLoader().getResource("imagenes/aceptarcontra.png");
        if (aceptarURL != null) {
            ImageIcon iconAceptar = new ImageIcon(new ImageIcon(aceptarURL).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
            btnAceptar.setIcon(iconAceptar);
        } else {
            System.out.println("Error al cargar aceptarcontra.png");
        }

         // Ícono para el botón Limpiar
        URL limpiarURL = ProductoAnadirView.class.getClassLoader().getResource("imagenes/limpiarcarr.png");
        if (limpiarURL != null) {
            ImageIcon iconLimpiar = new ImageIcon(new ImageIcon(limpiarURL).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
            btnLimpiar.setIcon(iconLimpiar);
        } else {
            System.out.println("Error al cargar limpiarcarr.png");
        }

    }


    /**
     * Inicializa los componentes gráficos de la vista.
     */
    private void initComponents() {
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);

        //setResizable(false);
        //setLocationRelativeTo(null);
        //pack();
    }

    /**
     * Actualiza los textos de la vista usando el idioma seleccionado.
     */
    private void actualizarTextos() {
        setTitle(mensaje.get("producto.anadir.titulo"));
        btnAceptar.setText(mensaje.get("aceptar"));
        btnLimpiar.setText(mensaje.get("limpiar"));
    }


    /**
     * Cambia el idioma de la vista actualizando los textos dinámicamente.
     * @param lenguaje código del idioma (ejemplo: es)
     * @param pais código del país (ejemplo: EC)
     */
    public void cambiarIdioma(String lenguaje, String pais) {
        mensaje.setLenguaje(lenguaje, pais);
        actualizarTextos();
    }


    /**
     * Configura los listeners para los botones, especialmente para limpiar campos.
     */
    private void configurarListeners() {
        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
            }
        });
    }

    /**
     * Obtiene el panel principal que contiene todos los componentes de la vista.
     * @return panel principal del formulario.
     */
    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    /**
     * Establece el panel principal del formulario.
     * @param panelPrincipal panel que contiene todos los componentes gráficos.
     */
    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    /**
     * Obtiene el campo de texto para ingresar el precio del producto.
     * @return campo de texto para el precio.
     */
    public JTextField getTxtPrecio() {
        return txtPrecio;
    }

    /**
     * Asigna un nuevo campo de texto para el precio del producto.
     * @param txtPrecio campo de texto del precio.
     */
    public void setTxtPrecio(JTextField txtPrecio) {
        this.txtPrecio = txtPrecio;
    }

    /**
     * Obtiene el campo de texto para ingresar el nombre del producto.
     * @return campo de texto para el nombre.
     */
    public JTextField getTxtNombre() {
        return txtNombre;
    }

    /**
     * Asigna un nuevo campo de texto para el nombre del producto.
     * @param txtNombre campo de texto del nombre.
     */
    public void setTxtNombre(JTextField txtNombre) {
        this.txtNombre = txtNombre;
    }

    /**
     * Obtiene el campo de texto para ingresar el código del producto.
     * @return campo de texto para el código.
     */
    public JTextField getTxtCodigo() {
        return txtCodigo;
    }


    /**
     * Asigna el campo de texto para el código del producto.
     * @param txtCodigo componente JTextField que permite ingresar el código del producto.
     */
    public void setTxtCodigo(JTextField txtCodigo) {
        this.txtCodigo = txtCodigo;
    }

    /**
     * Obtiene el botón para aceptar la operación (guardar producto).
     * @return JButton correspondiente al botón Aceptar.
     */
    public JButton getBtnAceptar() {
        return btnAceptar;
    }

    /**
     * Asigna el botón para aceptar la operación (guardar producto).
     * @param btnAceptar JButton para confirmar la acción.
     */
    public void setBtnAceptar(JButton btnAceptar) {
        this.btnAceptar = btnAceptar;
    }

    /**
     * Obtiene el botón para limpiar los campos del formulario.
     * @return JButton correspondiente al botón Limpiar.
     */
    public JButton getBtnLimpiar() {
        return btnLimpiar;
    }

    /**
     * Asigna el botón para limpiar los campos del formulario.
     * @param btnLimpiar JButton para limpiar la información ingresada.
     */
    public void setBtnLimpiar(JButton btnLimpiar) {
        this.btnLimpiar = btnLimpiar;
    }

    /**
     * Muestra un mensaje emergente (diálogo) con el texto proporcionado.
     * @param mensaje texto que se mostrará en la ventana de diálogo.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    /**
     * Limpia los campos de entrada del formulario, reiniciando el código, nombre y precio.
     */
    public void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
    }

    /**
     * Muestra en consola los productos contenidos en la lista proporcionada.
     * @param productos lista de productos a mostrar mediante System.out.println.
     */
    public void mostrarProductos(List<Producto> productos) {
        for (Producto producto : productos) {
            System.out.println(producto);
        }
    }

}
