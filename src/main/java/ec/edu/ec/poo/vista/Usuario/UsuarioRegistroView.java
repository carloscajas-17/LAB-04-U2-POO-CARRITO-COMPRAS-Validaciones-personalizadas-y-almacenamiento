package ec.edu.ec.poo.vista.Usuario;

import ec.edu.ec.poo.utils.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.net.URL;

/**
 * Vista gráfica para el registro de un nuevo usuario.
 * Permite ingresar los datos personales, credenciales y seleccionar preguntas de seguridad.
 * Implementa internacionalización mediante {@link MensajeInternacionalizacionHandler}.
 */
public class UsuarioRegistroView extends JDialog {
    /**
     * Panel principal que contiene todos los componentes gráficos
     */
    private JPanel panelPrincipal;

    /**
     * Campo de texto para ingresar el ID del usuario
     */
    private JTextField txtId;

    /**
     * Campo de texto para ingresar el nombre completo del usuario
     */
    private JTextField txtNombre;

    /**
     * Campo de texto para ingresar la fecha de nacimiento
     */
    private JTextField txtFechaNacimiento;

    /**
     * Campo de texto para ingresar el correo electrónico
     */
    private JTextField txtEmail;

    /**
     * Campo de texto para ingresar el número telefónico
     */
    private JTextField txtTelefono;

    /**
     * Campo de texto para ingresar la dirección del usuario
     */
    private JTextField txtDireccion;

    /**
     * Campo de contraseña para ingresar la contraseña
     */
    private JPasswordField txtContrasenia;

    /**
     * Campo de contraseña para confirmar la contraseña
     */
    private JPasswordField txtConfirmarContrasenia;

    /**
     * Botón para registrar al nuevo usuario
     */
    private JButton btnRegistrar;

    /**
     * Botón para cancelar el registro
     */
    private JButton btnCancelar;

    /**
     * Texto o título principal del formulario
     */
    private JFormattedTextField txtTitulo;

    /**
     * Tabla para mostrar las preguntas de seguridad y sus respuestas
     */
    private JTable tablaPreguntas;

    /**
     * Panel con scroll para visualizar las preguntas
     */
    private JScrollPane scrollPanePreguntas;

    /**
     * Etiqueta para el título del formulario
     */
    private JLabel lblTitulo;

    /**
     * Etiqueta para el ID del usuario
     */
    private JLabel lblId;

    /**
     * Etiqueta para el nombre del usuario
     */
    private JLabel lblNombre;

    /**
     * Etiqueta para la fecha de nacimiento
     */
    private JLabel lblFechaNacimiento;

    /**
     * Etiqueta para el correo electrónico
     */
    private JLabel lblEmail;

    /**
     * Etiqueta para el número telefónico
     */
    private JLabel lblTelefono;

    /**
     * Etiqueta para la dirección
     */
    private JLabel lblDireccion;

    /**
     * Etiqueta para la contraseña
     */
    private JLabel lblContrasenia;

    /**
     * Etiqueta para confirmar la contraseña
     */
    private JLabel lblConfirmarContrasenia;

    /**
     * Manejador de internacionalización para cambiar dinámicamente los textos
     */
    private MensajeInternacionalizacionHandler mensaje;

    /**
     * Constructor principal que inicializa la vista de registro con soporte de internacionalización.
     *
     * @param mensaje Manejador de mensajes para traducir textos.
     */
    public UsuarioRegistroView(MensajeInternacionalizacionHandler mensaje) {
        this.mensaje = mensaje;
        initComponents();
        actualizarTextos();

        // Ícono para botón Registrar
        URL urlRegistrar = UsuarioRegistroView.class.getClassLoader().getResource("imagenes/REGISTRARUSU.png");
        if (urlRegistrar != null) {
            ImageIcon icono = new ImageIcon(new ImageIcon(urlRegistrar).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
            btnRegistrar.setIcon(icono);
        } else {
            System.out.println("No se pudo cargar REGISTRARUSU.png");
        }

        // Ícono para botón Cancelar
        URL urlCancelar = UsuarioRegistroView.class.getClassLoader().getResource("imagenes/cancelarcontra.png");
        if (urlCancelar != null) {
            ImageIcon icono = new ImageIcon(new ImageIcon(urlCancelar).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
            btnCancelar.setIcon(icono);
        } else {
            System.out.println("No se pudo cargar cancelarcontra.png");
        }

    }


    /**
     * Inicializa y configura todos los componentes gráficos de la ventana de registro de usuario.
     * - Define el panel principal como el contenido principal del diálogo.
     * - Ajusta el tamaño, modalidad y comportamiento de cierre de la ventana.
     * - Configura la tabla de preguntas de seguridad con columnas: "Usar", "Pregunta", "Respuesta".
     * - La columna "Usar" permite seleccionar preguntas mediante un checkbox (booleano).
     * - La columna "Pregunta" es solo de visualización y no es editable.
     * - La columna "Respuesta" es editable para que el usuario pueda escribir su respuesta.
     */
    private void initComponents() {
        setContentPane(panelPrincipal);
        setTitle("Registro de Usuario");
        setModal(true);
        setSize(600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Configurar tabla
        tablaPreguntas.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Usar", "Pregunta", "Respuesta"}
        ) {
            /**
             * Indica si una celda es editable según la columna.
             * @param row número de fila
             * @param column número de columna
             * @return true si la celda es editable (columnas 0 y 2), false si no lo es (columna 1)
             */
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 1; // Solo editar columnas Usar y Respuesta
            }

            /**
             * Define el tipo de datos para cada columna.
             * La columna 0 es un checkbox (Boolean.class), las demás son texto (String.class).
             * @param columnIndex índice de la columna
             * @return clase correspondiente al tipo de dato de la columna
             */
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) return Boolean.class;
                return String.class;
            }
        });
    }

    /**
     * Actualiza todos los textos visibles en la interfaz gráfica según el idioma seleccionado.
     * Cambia el título, etiquetas, botones y cabeceras de la tabla.
     */
    private void actualizarTextos() {
        // Cambiar el título de la ventana
        setTitle(mensaje.get("usuario.registro.titulo"));

        // Cambiar el texto del encabezado si usas un JLabel o un JFormattedTextField
        if (lblTitulo != null) {
            lblTitulo.setText(mensaje.get("usuario.registro.titulo"));
        }
        if (txtTitulo != null) {
            txtTitulo.setText(mensaje.get("usuario.registro.titulo"));
        }

        // Cambiar etiquetas de los campos
        if (lblId != null) lblId.setText(mensaje.get("usuario"));
        if (lblNombre != null) lblNombre.setText(mensaje.get("nombre"));
        if (lblFechaNacimiento != null) lblFechaNacimiento.setText(mensaje.get("fecha.nacimiento"));
        if (lblEmail != null) lblEmail.setText(mensaje.get("email"));
        if (lblTelefono != null) lblTelefono.setText(mensaje.get("telefono"));
        if (lblDireccion != null) lblDireccion.setText(mensaje.get("direccion"));
        if (lblContrasenia != null) lblContrasenia.setText(mensaje.get("contrasenia"));
        if (lblConfirmarContrasenia != null) lblConfirmarContrasenia.setText(mensaje.get("confirmar.password"));

        // Cambiar botones
        if (btnRegistrar != null) btnRegistrar.setText(mensaje.get("registrar"));
        if (btnCancelar != null) btnCancelar.setText(mensaje.get("cancelar"));

        // Cambiar cabeceras de tabla
        if (tablaPreguntas != null && tablaPreguntas.getColumnCount() >= 3) {
            tablaPreguntas.getColumnModel().getColumn(0).setHeaderValue(mensaje.get("columna.usar"));
            tablaPreguntas.getColumnModel().getColumn(1).setHeaderValue(mensaje.get("columna.pregunta"));
            tablaPreguntas.getColumnModel().getColumn(2).setHeaderValue(mensaje.get("columna.respuesta"));
            tablaPreguntas.getTableHeader().repaint(); //  Refrescar cabecera
        }
    }

    /**
     * Cambia el idioma actual de la interfaz gráfica.
     *
     * @param lang    Código de lenguaje (ej: "es", "en", "fr").
     * @param country Código de país (ej: "EC", "US", "FR").
     */
    public void cambiarIdioma(String lang, String country) {
        mensaje.setLenguaje(lang, country);
        actualizarTextos();
    }


    /**
     * Muestra un cuadro de diálogo con un mensaje traducido.
     *
     * @param key Clave del mensaje a mostrar.
     */
    public void mostrarMensaje(String key) {
        JOptionPane.showMessageDialog(this, mensaje.get(key));
    }

    /**
     * Limpia todos los campos del formulario, dejando los inputs vacíos.
     */
    public void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        txtFechaNacimiento.setText("");
        txtEmail.setText("");
        txtTelefono.setText("");
        txtDireccion.setText("");
        txtContrasenia.setText("");
        txtConfirmarContrasenia.setText("");
    }

    // Getters

    /**
     * Obtiene el campo de texto para el ID del usuario.
     *
     * @return JTextField correspondiente al ID del usuario.
     */
    public JTextField getTxtId() {
        return txtId;
    }

    /**
     * Obtiene el campo de texto para el nombre del usuario.
     *
     * @return JTextField correspondiente al nombre del usuario.
     */
    public JTextField getTxtNombre() {
        return txtNombre;
    }

    /**
     * Obtiene el campo de texto para la fecha de nacimiento del usuario.
     *
     * @return JTextField correspondiente a la fecha de nacimiento.
     */
    public JTextField getTxtFechaNacimiento() {
        return txtFechaNacimiento;
    }

    /**
     * Obtiene el campo de texto para el correo electrónico del usuario.
     *
     * @return JTextField correspondiente al email del usuario.
     */
    public JTextField getTxtEmail() {
        return txtEmail;
    }

    /**
     * Obtiene el campo de texto para el número telefónico del usuario.
     *
     * @return JTextField correspondiente al teléfono del usuario.
     */
    public JTextField getTxtTelefono() {
        return txtTelefono;
    }

    /**
     * Obtiene el campo de texto para la dirección domiciliaria del usuario.
     *
     * @return JTextField correspondiente a la dirección del usuario.
     */
    public JTextField getTxtDireccion() {
        return txtDireccion;
    }

    /**
     * Obtiene el campo de texto tipo contraseña donde el usuario ingresa su clave.
     *
     * @return JPasswordField correspondiente a la contraseña del usuario.
     */
    public JPasswordField getTxtContrasenia() {
        return txtContrasenia;
    }

    /**
     * Obtiene el campo de contraseña para confirmar la clave del usuario.
     *
     * @return JPasswordField correspondiente a la confirmación de la contraseña.
     */
    public JPasswordField getTxtConfirmarContrasenia() {
        return txtConfirmarContrasenia;
    }

    /**
     * Obtiene el botón utilizado para registrar al usuario.
     *
     * @return JButton correspondiente al botón de registrar.
     */
    public JButton getBtnRegistrar() {
        return btnRegistrar;
    }

    /**
     * Obtiene el botón utilizado para cancelar el registro del usuario.
     *
     * @return JButton correspondiente al botón de cancelar.
     */
    public JButton getBtnCancelar() {
        return btnCancelar;
    }

    /**
     * Obtiene el campo de texto formateado que muestra el título principal del formulario.
     *
     * @return JFormattedTextField correspondiente al título del formulario.
     */
    public JFormattedTextField getTxtTitulo() {
        return txtTitulo;
    }

    /**
     * Obtiene la tabla donde se muestran las preguntas de seguridad disponibles.
     *
     * @return JTable correspondiente a la tabla de preguntas de seguridad.
     */
    public JTable getTablaPreguntas() {
        return tablaPreguntas;
    }

    /**
     * Obtiene el componente JScrollPane que contiene la tabla de preguntas.
     *
     * @return JScrollPane correspondiente al área de desplazamiento de la tabla de preguntas.
     */
    public JScrollPane getScrollPanePreguntas() {
        return scrollPanePreguntas;
    }

    /**
     * Obtiene el manejador de internacionalización para cambiar textos dinámicamente.
     *
     * @return MensajeInternacionalizacionHandler utilizado en la vista.
     */
    public MensajeInternacionalizacionHandler getMensaje() {
        return mensaje;
    }

}
