package ec.edu.ec.poo.vista.Usuario;

import ec.edu.ec.poo.utils.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.net.URL;

public class UsuarioRegistroView extends JDialog {
    private JPanel panelPrincipal;
    private JTextField txtId;
    private JTextField txtNombre;
    private JTextField txtFechaNacimiento;
    private JTextField txtEmail;
    private JTextField txtTelefono;
    private JTextField txtDireccion;
    private JPasswordField txtContrasenia;
    private JPasswordField txtConfirmarContrasenia;
    private JButton btnRegistrar;
    private JButton btnCancelar;
    private JFormattedTextField txtTitulo;
    private JTable tablaPreguntas;
    private JScrollPane scrollPanePreguntas;

    // Etiquetas (deben ser creadas y enlazadas desde el .form si no están)
    private JLabel lblTitulo;
    private JLabel lblId;
    private JLabel lblNombre;
    private JLabel lblFechaNacimiento;
    private JLabel lblEmail;
    private JLabel lblTelefono;
    private JLabel lblDireccion;
    private JLabel lblContrasenia;
    private JLabel lblConfirmarContrasenia;

    private MensajeInternacionalizacionHandler mensaje;

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
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 1; // Solo editar columnas Usar y Respuesta
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) return Boolean.class;
                return String.class;
            }
        });
    }

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
            tablaPreguntas.getTableHeader().repaint(); // ✅ Refrescar cabecera
        }
    }

    public void cambiarIdioma(String lang, String country) {
        mensaje.setLenguaje(lang, country);
        actualizarTextos();
    }


    public void mostrarMensaje(String key) {
        JOptionPane.showMessageDialog(this, mensaje.get(key));
    }

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
    public JTextField getTxtId() { return txtId; }
    public JTextField getTxtNombre() { return txtNombre; }
    public JTextField getTxtFechaNacimiento() { return txtFechaNacimiento; }
    public JTextField getTxtEmail() { return txtEmail; }
    public JTextField getTxtTelefono() { return txtTelefono; }
    public JTextField getTxtDireccion() { return txtDireccion; }
    public JPasswordField getTxtContrasenia() { return txtContrasenia; }
    public JPasswordField getTxtConfirmarContrasenia() { return txtConfirmarContrasenia; }
    public JButton getBtnRegistrar() { return btnRegistrar; }
    public JButton getBtnCancelar() { return btnCancelar; }
    public JFormattedTextField getTxtTitulo() { return txtTitulo; }
    public JTable getTablaPreguntas() { return tablaPreguntas; }
    public JScrollPane getScrollPanePreguntas() { return scrollPanePreguntas; }
    public MensajeInternacionalizacionHandler getMensaje() { return mensaje; }
}
