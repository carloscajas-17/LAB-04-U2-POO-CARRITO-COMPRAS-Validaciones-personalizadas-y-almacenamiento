package ec.edu.ec.poo.vista.Carrito;

import ec.edu.ec.poo.utils.MensajeInternacionalizacionHandler;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.text.NumberFormat;

/**
 * Vista gráfica interna para modificar un carrito de compras dentro del sistema.
 * Implementa funcionalidades para buscar carritos, buscar productos, añadir productos al carrito
 * y actualizar el carrito seleccionado, mostrando detalles como subtotal, IVA y total.
 * Integra internacionalización dinámica mediante {@link MensajeInternacionalizacionHandler}.
 */
public class CarritoModificarView extends JInternalFrame {
    /** Panel principal que contiene todos los componentes visuales */
    private JPanel pnlPrincipal;

    /** Panel superior para la búsqueda y selección */
    private JPanel pnlSuperior;

    /** Panel inferior para resumen y acciones */
    private JPanel pnlInferior;

    /** Campo de texto para el código del carrito */
    private JTextField txtCodigoC;

    /** Campo de texto para el código del producto */
    private JTextField txtCodigoP;

    /** Campo de texto para mostrar el subtotal del carrito */
    private JTextField txtSubtotal;

    /** Campo de texto para mostrar el IVA calculado */
    private JTextField txtIva;

    /** Campo de texto para mostrar el total final */
    private JTextField txtTotal;

    /** Botón para buscar un carrito */
    private JButton btnBuscarC;

    /** Botón para buscar un producto */
    private JButton btnBuscarP;

    /** Botón para añadir productos al carrito */
    private JButton btnAnadir;

    /** Botón para guardar las modificaciones realizadas al carrito */
    private JButton btnGuardar;

    /** Tabla para listar productos dentro del carrito */
    private JTable tblProducto;

    /** Etiqueta para el título principal de la vista */
    private JLabel lblTitulo;

    /** Etiqueta descriptiva para el código del carrito */
    private JLabel lblCodigoCarrito;

    /** Etiqueta descriptiva para el código del producto */
    private JLabel lblCodigoProducto;

    /** Etiqueta para subtotal */
    private JLabel lblSubtotal;

    /** Etiqueta para IVA */
    private JLabel lblIva;

    /** Etiqueta para total */
    private JLabel lblTotal;

    /** Listener externo para manejar la acción de modificar un producto dentro del carrito */
    private ActionListener modificarListener;

    /** Listener externo para manejar la acción de eliminar un producto del carrito */
    private ActionListener eliminarListener;

    /** Manejador de internacionalización que permite cambiar dinámicamente los textos */
    private MensajeInternacionalizacionHandler mensaje;


    /**
     * Constructor principal de la vista CarritoModificarView.
     * Se encarga de inicializar los componentes gráficos, establecer los textos
     * según el idioma actual y configurar los íconos de los botones.
     *
     * @param mensaje manejador de internacionalización para el cambio dinámico de idioma.
     */
    public CarritoModificarView(MensajeInternacionalizacionHandler mensaje) {
        this.mensaje = mensaje;
        initComponents();  // Inicializa los componentes del formulario
        actualizarTextos(); // Actualiza los textos de la interfaz según el idioma

        // Configuración del ícono para el botón Buscar Carrito
        URL buscarCarritoURL = CarritoModificarView.class.getClassLoader()
                .getResource("imagenes/añadiractualizacarrito.png");
        if (buscarCarritoURL != null) {
            ImageIcon iconBuscarC = new ImageIcon(
                    new ImageIcon(buscarCarritoURL).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)
            );
            btnBuscarC.setIcon(iconBuscarC);
        } else {
            System.out.println("Error al cargar añadiractualizacarrito.png");
        }

        // Configuración del ícono para el botón Buscar Producto
        URL buscarProductoURL = CarritoModificarView.class.getClassLoader()
                .getResource("imagenes/buscarproductoactu.png");
        if (buscarProductoURL != null) {
            ImageIcon iconBuscarP = new ImageIcon(
                    new ImageIcon(buscarProductoURL).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)
            );
            btnBuscarP.setIcon(iconBuscarP);
        } else {
            System.out.println("Error al cargar buscarproductoactu.png");
        }

        // Configuración del ícono para el botón Añadir Producto
        URL anadirURL = CarritoModificarView.class.getClassLoader()
                .getResource("imagenes/anadircarritmodificar.png");
        if (anadirURL != null) {
            ImageIcon iconAnadir = new ImageIcon(
                    new ImageIcon(anadirURL).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)
            );
            btnAnadir.setIcon(iconAnadir);
        } else {
            System.out.println("Error al cargar anadircarritmodificar.png");
        }

        // Configuración del ícono para el botón Guardar Cambios
        URL guardarURL = CarritoModificarView.class.getClassLoader()
                .getResource("imagenes/guardarcarr.png");
        if (guardarURL != null) {
            ImageIcon iconGuardar = new ImageIcon(
                    new ImageIcon(guardarURL).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)
            );
            btnGuardar.setIcon(iconGuardar);
        } else {
            System.out.println("Error al cargar guardarcarr.png");
        }
    }


    /**
     * Inicializa los componentes visuales de la vista CarritoModificarView.
     * Configura el panel principal como contenido de la ventana, permite que sea
     * cerrable y redimensionable, establece el tamaño inicial de la ventana
     * y configura la tabla de productos mediante el método {@code configurarTabla()}.
     */
    private void initComponents() {
        // Establece el panel principal como contenido de la ventana
        setContentPane(pnlPrincipal);

        // Permite que la ventana pueda cerrarse de forma independiente
        setClosable(true);

        // Permite que la ventana pueda redimensionarse
        setResizable(true);

        // Define el tamaño inicial de la ventana
        setSize(1000, 500);

        // Configura la tabla con las columnas correspondientes según idioma
        configurarTabla();
    }


    /**
     * Configura la tabla {@code tblProducto} para mostrar los productos del carrito con sus respectivas columnas.
     * Las columnas incluyen: código, nombre, precio, cantidad, subtotal y acciones (modificar/eliminar).
     * <p>
     * - La columna 'cantidad' (índice 3) es editable para permitir modificar la cantidad del producto.
     * - La columna 'acciones' (índice 5) es editable para incluir botones de modificar y eliminar.
     * <p>
     * También se configuran renderizadores y editores personalizados para la columna 'acciones',
     * utilizando {@link ButtonRenderer} y {@link ButtonEditor} respectivamente, para mostrar botones interactivos.
     */
    private void configurarTabla() {
        DefaultTableModel modelo = new DefaultTableModel(
                new Object[]{
                        mensaje.get("columna.codigo"),
                        mensaje.get("columna.nombre"),
                        mensaje.get("columna.precio"),
                        mensaje.get("columna.cantidad"),
                        mensaje.get("columna.subtotal"),
                        mensaje.get("columna.acciones")
                }, 0) {
            /**
             * Define qué celdas de la tabla son editables.
             * Solo la columna cantidad (índice 3) y la columna acciones (índice 5) serán editables.
             */
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3 || column == 5;
            }
        };

        tblProducto.setModel(modelo);

        tblProducto.getColumn(mensaje.get("columna.acciones"))
                .setCellRenderer(new ButtonRenderer());

        tblProducto.getColumn(mensaje.get("columna.acciones"))
                .setCellEditor(new ButtonEditor(new JCheckBox()));
    }

    /**
     * Actualiza dinámicamente todos los textos visibles en la interfaz gráfica según el idioma seleccionado.
     * <p>
     * Esta función se encarga de actualizar:
     * <ul>
     *     <li>El título de la ventana.</li>
     *     <li>Las etiquetas de campos como código de carrito, código de producto, subtotal, IVA y total.</li>
     *     <li>Los textos de los botones como buscar carrito, buscar producto, añadir y guardar.</li>
     * </ul>
     * <p>
     * Utiliza el {@link MensajeInternacionalizacionHandler} para obtener las traducciones correspondientes
     * desde los archivos de recursos `.properties`.
     */
    private void actualizarTextos() {
        setTitle(mensaje.get("carrito.actualizar.titulo"));
        lblTitulo.setText(mensaje.get("carrito.actualizar.titulo"));

        lblCodigoCarrito.setText(mensaje.get("codigo.carrito"));
        lblCodigoProducto.setText(mensaje.get("codigo.producto"));
        lblSubtotal.setText(mensaje.get("subtotal"));
        lblIva.setText(mensaje.get("iva"));
        lblTotal.setText(mensaje.get("total"));

        btnBuscarC.setText(mensaje.get("buscar.carrito"));
        btnBuscarP.setText(mensaje.get("buscar.producto"));
        btnAnadir.setText(mensaje.get("anadir"));
        btnGuardar.setText(mensaje.get("guardar"));
    }

    /**
     * Cambia el idioma de toda la interfaz gráfica del formulario CarritoModificarView.
     * <p>
     * Este método actualiza el {@link MensajeInternacionalizacionHandler} con el nuevo idioma y país,
     * luego actualiza dinámicamente todos los textos visibles (etiquetas, botones y título de ventana)
     * y reconfigura las columnas de la tabla para reflejar los cambios de idioma.
     * </p>
     *
     * @param lenguaje código del idioma, por ejemplo "es" para español o "en" para inglés.
     * @param pais código del país, por ejemplo "EC" para Ecuador o "US" para Estados Unidos.
     */
    public void cambiarIdioma(String lenguaje, String pais) {
        mensaje.setLenguaje(lenguaje, pais);
        actualizarTextos();
        configurarTabla();
    }

    /**
     * Clase {@code ButtonRenderer} es un renderizador personalizado para mostrar botones
     * dentro de una celda de una tabla {@link JTable}. Muestra dos botones por celda:
     * "Modificar" y "Eliminar".
     * <p>
     * Esta clase implementa {@link TableCellRenderer} y extiende {@link JPanel},
     * permitiendo renderizar ambos botones en una misma celda.
     * </p>
     */
    class ButtonRenderer extends JPanel implements TableCellRenderer {
        /** Botón para la acción de modificar el elemento de la fila */
        private JButton btnModificar;

        /** Botón para la acción de eliminar el elemento de la fila */
        private JButton btnEliminar;

        /**
         * Constructor por defecto que inicializa el renderizador con dos botones,
         * usando el layout {@link FlowLayout} para mostrarlos alineados horizontalmente.
         * Los textos de los botones se cargan dinámicamente desde el {@link MensajeInternacionalizacionHandler}.
         */
        public ButtonRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
            btnModificar = new JButton(mensaje.get("modificar"));
            btnEliminar = new JButton(mensaje.get("eliminar"));
            add(btnModificar);
            add(btnEliminar);
        }

        /**
         * Retorna el componente gráfico para ser renderizado dentro de la celda.
         *
         * @param table la tabla donde se renderiza
         * @param value el valor de la celda (no utilizado en este caso)
         * @param isSelected si la celda está seleccionada
         * @param hasFocus si la celda tiene foco
         * @param row la fila actual
         * @param column la columna actual
         * @return el {@link JPanel} con los botones como componente de renderizado
         */
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }


    /**
     * Editor de celdas personalizado para tablas que contiene dos botones: "Modificar" y "Eliminar".
     * Este editor permite capturar eventos de ambos botones dentro de una celda de la tabla,
     * y propagar la acción al controlador asociado mediante listeners externos.
     */
    class ButtonEditor extends DefaultCellEditor {
        /** Panel principal que contiene los botones */
        private JPanel panel;

        /** Botón para modificar el registro correspondiente a la fila */
        private JButton btnModificar;

        /** Botón para eliminar el registro correspondiente a la fila */
        private JButton btnEliminar;

        /** Fila actual que se está editando, utilizada para identificar el registro seleccionado */
        private int currentRow;

        /**
         * Constructor del editor de celda con dos botones.
         * Configura los botones con su respectivo texto y gestiona los eventos internos.
         *
         * @param checkBox componente base requerido por DefaultCellEditor (no se utiliza directamente)
         */
        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
            btnModificar = new JButton(mensaje.get("modificar"));
            btnEliminar = new JButton(mensaje.get("eliminar"));

            panel.add(btnModificar);
            panel.add(btnEliminar);

            btnModificar.addActionListener(e -> {
                fireEditingStopped();
                if (modificarListener != null) {
                    modificarListener.actionPerformed(
                            new ActionEvent(this, ActionEvent.ACTION_PERFORMED, String.valueOf(currentRow)));
                }
            });

            btnEliminar.addActionListener(e -> {
                fireEditingStopped();
                if (eliminarListener != null) {
                    eliminarListener.actionPerformed(
                            new ActionEvent(this, ActionEvent.ACTION_PERFORMED, String.valueOf(currentRow)));
                }
            });
        }

        /**
         * Devuelve el componente gráfico que será renderizado dentro de la celda al entrar en modo edición.
         * También actualiza la fila actual para saber desde qué fila se generó el evento.
         *
         * @param table tabla donde se utiliza el editor
         * @param value valor actual de la celda (no utilizado)
         * @param isSelected si la celda está seleccionada
         * @param row fila actual de la tabla
         * @param column columna actual de la tabla
         * @return panel con los botones de acción para ser mostrado en la celda
         */
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            currentRow = row;
            return panel;
        }

        /**
         * Retorna el valor de la celda tras la edición. En este caso retorna un valor vacío,
         * ya que el editor se utiliza exclusivamente para generar eventos de acción.
         *
         * @return cadena vacía ya que no se edita un valor específico
         */
        @Override
        public Object getCellEditorValue() {
            return "";
        }
    }

    /**
     * Obtiene la cantidad de productos desde una fila específica de la tabla {@code tblProducto}.
     *
     * <p>La cantidad se asume que se encuentra en la columna 3 de la tabla (índice 3).</p>
     *
     * @param fila índice de la fila de la cual se desea obtener la cantidad.
     * @return cantidad de productos (entero) correspondiente a la fila indicada.
     * @throws IllegalArgumentException si el índice de la fila es inválido (fuera de rango).
     * @throws RuntimeException si ocurre un error al convertir el valor de la celda a entero.
     */
    public int getCantidadEnFila(int fila) {
        if(fila < 0 || fila >= tblProducto.getRowCount()) {
            throw new IllegalArgumentException("Fila inválida");
        }
        try {
            return Integer.parseInt(tblProducto.getValueAt(fila, 3).toString());
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener la cantidad");
        }
    }

    /**
     * Obtiene el código del producto desde una fila específica de la tabla {@code tblProducto}.
     *
     * <p>La información del código del producto se obtiene de la primera columna (índice 0)
     * de la tabla.</p>
     *
     * @param fila índice de la fila de la cual se desea obtener el código del producto.
     * @return el código del producto como número entero; si ocurre un error al obtener o convertir
     *         el valor, retorna {@code -1} como valor indicativo de error.
     */
    public int getCodigoProductoEnFila(int fila) {
        try {
            return Integer.parseInt(tblProducto.getValueAt(fila, 0).toString());
        } catch (Exception e) {
            return -1;
        }
    }


    /**
     * Limpia completamente el contenido de la tabla de productos {@code tblProducto}.
     *
     * <p>Este método elimina todas las filas de la tabla, dejándola vacía,
     * y adicionalmente restablece los campos de subtotal, IVA y total a valores {@code 0.0}.</p>
     *
     * <p>Es útil para reiniciar la vista del carrito antes de cargar nuevos datos.</p>
     */
    public void limpiarTabla() {
        ((DefaultTableModel)tblProducto.getModel()).setRowCount(0);
        actualizarTotales(0.0, 0.0, 0.0);
    }

    /**
     * Actualiza los campos de subtotal, IVA y total del carrito con valores formateados según la localización actual.
     * <p>
     * Este método toma los valores numéricos calculados de subtotal, IVA y total,
     * los convierte al formato de moneda correspondiente al idioma y país definidos
     * en {@link MensajeInternacionalizacionHandler}, y los muestra en los respectivos
     * campos de texto de la vista.
     * </p>
     *
     * @param subtotal el valor del subtotal a mostrar
     * @param iva el valor del IVA a mostrar
     * @param total el valor total a mostrar (subtotal + IVA)
     */
    public void actualizarTotales(double subtotal, double iva, double total) {
        NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(mensaje.getLocale());
        txtSubtotal.setText(formatoMoneda.format(subtotal));
        txtIva.setText(formatoMoneda.format(iva));
        txtTotal.setText(formatoMoneda.format(total));
    }

    /**
     * Muestra un mensaje en pantalla utilizando el sistema de internacionalización.
     * El mensaje se obtiene a partir de una clave definida en los archivos .properties.
     *
     * @param claveMensaje clave de la cadena traducida a mostrar en el mensaje.
     */
    public void mostrarMensaje(String claveMensaje) {
        JOptionPane.showMessageDialog(this, mensaje.get(claveMensaje));
    }

    /**
     * Obtiene el panel principal de la vista.
     *
     * @return componente {@link JPanel} principal.
     */
    public JPanel getPnlPrincipal() {
        return pnlPrincipal;
    }

    /**
     * Asigna un nuevo panel principal a la vista.
     *
     * @param pnlPrincipal panel principal que se desea establecer.
     */
    public void setPnlPrincipal(JPanel pnlPrincipal) {
        this.pnlPrincipal = pnlPrincipal;
    }

    /**
     * Obtiene el campo de texto para el código del carrito.
     *
     * @return componente {@link JTextField} del código del carrito.
     */
    public JTextField getTxtCodigoC() {
        return txtCodigoC;
    }

    /**
     * Asigna el campo de texto del código del carrito.
     *
     * @param txtCodigoC componente {@link JTextField} que se desea establecer.
     */
    public void setTxtCodigoC(JTextField txtCodigoC) {
        this.txtCodigoC = txtCodigoC;
    }

    /**
     * Obtiene el campo de texto para el código del producto.
     *
     * @return componente {@link JTextField} del código del producto.
     */
    public JTextField getTxtCodigoP() {
        return txtCodigoP;
    }

    /**
     * Asigna el campo de texto del código del producto.
     *
     * @param txtCodigoP componente {@link JTextField} a establecer.
     */
    public void setTxtCodigoP(JTextField txtCodigoP) {
        this.txtCodigoP = txtCodigoP;
    }

    /**
     * Obtiene el botón para buscar un carrito.
     *
     * @return componente {@link JButton} de búsqueda de carrito.
     */
    public JButton getBtnBuscarC() {
        return btnBuscarC;
    }


    /**
     * Establece el botón encargado de la búsqueda del carrito.
     *
     * @param btnBuscarC el botón {@link JButton} para buscar carrito.
     */
    public void setBtnBuscarC(JButton btnBuscarC) {
        this.btnBuscarC = btnBuscarC;
    }

    /**
     * Obtiene el botón para buscar productos.
     *
     * @return el botón {@link JButton} utilizado para buscar productos.
     */
    public JButton getBtnBuscarP() {
        return btnBuscarP;
    }

    /**
     * Establece el botón utilizado para buscar productos.
     *
     * @param btnBuscarP el botón {@link JButton} para buscar productos.
     */
    public void setBtnBuscarP(JButton btnBuscarP) {
        this.btnBuscarP = btnBuscarP;
    }

    /**
     * Obtiene el botón para añadir un producto al carrito.
     *
     * @return el botón {@link JButton} de añadir producto.
     */
    public JButton getBtnAnadir() {
        return btnAnadir;
    }

    /**
     * Establece el botón que permite añadir productos al carrito.
     *
     * @param btnAnadir el botón {@link JButton} que se usará para añadir productos.
     */
    public void setBtnAnadir(JButton btnAnadir) {
        this.btnAnadir = btnAnadir;
    }

    /**
     * Obtiene el botón para guardar las modificaciones del carrito.
     *
     * @return el botón {@link JButton} encargado de guardar.
     */
    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    /**
     * Establece el botón encargado de guardar las modificaciones.
     *
     * @param btnGuardar el botón {@link JButton} de guardar.
     */
    public void setBtnGuardar(JButton btnGuardar) {
        this.btnGuardar = btnGuardar;
    }


    /**
     * Obtiene la tabla que lista los productos del carrito.
     *
     * @return la tabla {@link JTable} de productos.
     */
    public JTable getTblProducto() {
        return tblProducto;
    }

    /**
     * Establece la tabla que muestra los productos del carrito.
     *
     * @param tblProducto la tabla {@link JTable} de productos.
     */
    public void setTblProducto(JTable tblProducto) {
        this.tblProducto = tblProducto;
    }

    /**
     * Obtiene el campo de texto donde se muestra el subtotal del carrito.
     *
     * @return el campo {@link JTextField} del subtotal.
     */
    public JTextField getTxtSubtotal() {
        return txtSubtotal;
    }

    /**
     * Establece el campo de texto para mostrar el subtotal del carrito.
     *
     * @param txtSubtotal el campo {@link JTextField} de subtotal.
     */
    public void setTxtSubtotal(JTextField txtSubtotal) {
        this.txtSubtotal = txtSubtotal;
    }

    /**
     * Obtiene el campo de texto donde se muestra el IVA calculado.
     *
     * @return el campo {@link JTextField} del IVA.
     */
    public JTextField getTxtIva() {
        return txtIva;
    }

    /**
     * Establece el campo de texto para mostrar el IVA calculado.
     *
     * @param txtIva el campo {@link JTextField} del IVA.
     */
    public void setTxtIva(JTextField txtIva) {
        this.txtIva = txtIva;
    }

    /**
     * Obtiene el campo de texto donde se muestra el total a pagar.
     *
     * @return el campo {@link JTextField} del total.
     */
    public JTextField getTxtTotal() {
        return txtTotal;
    }

    /**
     * Establece el campo de texto para mostrar el total a pagar.
     *
     * @param txtTotal el campo {@link JTextField} del total.
     */
    public void setTxtTotal(JTextField txtTotal) {
        this.txtTotal = txtTotal;
    }

    /**
     * Obtiene el panel superior de la vista, que contiene los campos de búsqueda.
     *
     * @return el panel {@link JPanel} superior.
     */
    public JPanel getPnlSuperior() {
        return pnlSuperior;
    }

    /**
     * Establece el panel superior de la vista.
     *
     * @param pnlSuperior el {@link JPanel} superior.
     */
    public void setPnlSuperior(JPanel pnlSuperior) {
        this.pnlSuperior = pnlSuperior;
    }


    /**
     * Obtiene el panel inferior de la vista donde se muestran los totales y botones.
     *
     * @return el panel {@link JPanel} inferior.
     */
    public JPanel getPnlInferior() {
        return pnlInferior;
    }

    /**
     * Establece el panel inferior de la vista.
     *
     * @param pnlInferior el {@link JPanel} a establecer como inferior.
     */
    public void setPnlInferior(JPanel pnlInferior) {
        this.pnlInferior = pnlInferior;
    }

    /**
     * Obtiene la etiqueta del título principal de la vista.
     *
     * @return la etiqueta {@link JLabel} del título.
     */
    public JLabel getLblTitulo() {
        return lblTitulo;
    }

    /**
     * Establece la etiqueta del título principal de la vista.
     *
     * @param lblTitulo la etiqueta {@link JLabel} del título.
     */
    public void setLblTitulo(JLabel lblTitulo) {
        this.lblTitulo = lblTitulo;
    }

    /**
     * Obtiene la etiqueta correspondiente al código del carrito.
     *
     * @return la etiqueta {@link JLabel} para el código del carrito.
     */
    public JLabel getLblCodigoCarrito() {
        return lblCodigoCarrito;
    }

    /**
     * Establece la etiqueta para el código del carrito.
     *
     * @param lblCodigoCarrito la etiqueta {@link JLabel} para el código del carrito.
     */
    public void setLblCodigoCarrito(JLabel lblCodigoCarrito) {
        this.lblCodigoCarrito = lblCodigoCarrito;
    }

    /**
     * Obtiene la etiqueta correspondiente al código del producto.
     *
     * @return la etiqueta {@link JLabel} para el código del producto.
     */
    public JLabel getLblCodigoProducto() {
        return lblCodigoProducto;
    }

    /**
     * Establece la etiqueta para el código del producto.
     *
     * @param lblCodigoProducto la etiqueta {@link JLabel} del producto.
     */
    public void setLblCodigoProducto(JLabel lblCodigoProducto) {
        this.lblCodigoProducto = lblCodigoProducto;
    }

    /**
     * Obtiene la etiqueta del subtotal.
     *
     * @return la etiqueta {@link JLabel} del subtotal.
     */
    public JLabel getLblSubtotal() {
        return lblSubtotal;
    }


    /**
     * Establece la etiqueta para el subtotal mostrado en la interfaz.
     *
     * @param lblSubtotal la etiqueta {@link JLabel} para el subtotal.
     */
    public void setLblSubtotal(JLabel lblSubtotal) {
        this.lblSubtotal = lblSubtotal;
    }

    /**
     * Obtiene la etiqueta correspondiente al IVA mostrado en la vista.
     *
     * @return la etiqueta {@link JLabel} del IVA.
     */
    public JLabel getLblIva() {
        return lblIva;
    }

    /**
     * Establece la etiqueta correspondiente al IVA.
     *
     * @param lblIva la etiqueta {@link JLabel} para el IVA.
     */
    public void setLblIva(JLabel lblIva) {
        this.lblIva = lblIva;
    }

    /**
     * Obtiene la etiqueta correspondiente al total mostrado en la vista.
     *
     * @return la etiqueta {@link JLabel} del total.
     */
    public JLabel getLblTotal() {
        return lblTotal;
    }

    /**
     * Establece la etiqueta para el total.
     *
     * @param lblTotal la etiqueta {@link JLabel} para el total.
     */
    public void setLblTotal(JLabel lblTotal) {
        this.lblTotal = lblTotal;
    }

    /**
     * Retorna el listener que maneja la acción de modificar productos en el carrito.
     *
     * @return el {@link ActionListener} encargado de modificar.
     */
    public ActionListener getModificarListener() {
        return modificarListener;
    }

    /**
     * Retorna el listener encargado de eliminar productos del carrito.
     *
     * @return el {@link ActionListener} encargado de eliminar.
     */
    public ActionListener getEliminarListener() {
        return eliminarListener;
    }

    /**
     * Obtiene el manejador de internacionalización asociado a la vista.
     *
     * @return el {@link MensajeInternacionalizacionHandler} actual.
     */
    public MensajeInternacionalizacionHandler getMensaje() {
        return mensaje;
    }

    /**
     * Establece un nuevo manejador de internacionalización para cambiar los textos dinámicamente.
     *
     * @param mensaje el {@link MensajeInternacionalizacionHandler} a utilizar.
     */
    public void setMensaje(MensajeInternacionalizacionHandler mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * Asigna un {@link ActionListener} para gestionar la acción de modificar productos en la tabla.
     * El listener será ejecutado cuando se presione el botón de modificar en la tabla.
     *
     * @param listener el {@link ActionListener} encargado de modificar productos.
     */
    public void setModificarListener(ActionListener listener) {
        this.modificarListener = listener;
    }

    /**
     * Asigna un {@link ActionListener} para gestionar la acción de eliminar productos en la tabla.
     * El listener será ejecutado cuando se presione el botón de eliminar en la tabla.
     *
     * @param listener el {@link ActionListener} encargado de eliminar productos.
     */
    public void setEliminarListener(ActionListener listener) {
        this.eliminarListener = listener;
    }




}