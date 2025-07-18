package ec.edu.ec.poo.vista.Usuario;



import ec.edu.ec.poo.utils.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * Clase UsuarioEliminarView representa la interfaz gráfica para eliminar un usuario.
 * Permite buscar un usuario mediante su identificador y visualizar información asociada
 * antes de proceder a su eliminación.
 */
public class UsuarioEliminarView extends JInternalFrame {
    /** Panel principal que contiene todos los componentes gráficos */
    private JPanel pnlPrincipal;

    /** Etiqueta para el título principal de la ventana */
    private JLabel lblTitulo;

    /** Etiqueta para el campo asociado */
    private JLabel lblAsociado;

    /** Etiqueta para el campo usuario */
    private JLabel lblUsuario;

    /** Campo de texto para ingresar el identificador del usuario a eliminar */
    private JTextField txtUsuario;

    /** Campo de texto que muestra información del registro del usuario */
    private JTextField txtRegistro;

    /** Campo de texto que muestra el nivel de acceso o rol del usuario */
    private JTextField txtAcceso;

    /** Campo de texto para información adicional asociada al usuario */
    private JTextField txtAsociado;

    /** Botón para buscar el usuario */
    private JButton btnBuscar;

    /** Botón para eliminar el usuario */
    private JButton btnEliminar;

    /** Botón para cancelar la operación y cerrar la ventana */
    private JButton btnCancelar;

    /** Manejador de internacionalización para cambiar los textos dinámicamente */
    private MensajeInternacionalizacionHandler mensaje;

    /**
     * Constructor que inicializa la vista de eliminación de usuario con internacionalización.
     * @param mensaje manejador de internacionalización
     */
    public UsuarioEliminarView(MensajeInternacionalizacionHandler mensaje) {
        this.mensaje = mensaje;
        initComponents();
        actualizarTextos();

        // Ícono para Buscar Usuario
        URL urlBuscar = UsuarioEliminarView.class.getClassLoader().getResource("imagenes/buscarusuarioListar.png");
        if (urlBuscar != null) {
            btnBuscar.setIcon(new ImageIcon(new ImageIcon(urlBuscar).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        }

         // Ícono para Eliminar
        URL urlEliminar = UsuarioEliminarView.class.getClassLoader().getResource("imagenes/eliminarproducto.png");
        if (urlEliminar != null) {
            btnEliminar.setIcon(new ImageIcon(new ImageIcon(urlEliminar).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        }

        // Ícono para Cancelar
        URL urlCancelar = UsuarioEliminarView.class.getClassLoader().getResource("imagenes/cancelarcontra.png");
        if (urlCancelar != null) {
            btnCancelar.setIcon(new ImageIcon(new ImageIcon(urlCancelar).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        }

    }

    /**
     * Inicializa los componentes visuales y configuración básica del JInternalFrame.
     */
    private void initComponents() {
        setContentPane(pnlPrincipal);
        setClosable(true);
        setResizable(true);
        setSize(500, 500);
    }
    /**
     * Actualiza los textos de la interfaz según el idioma seleccionado.
     */
    private void actualizarTextos() {
        setTitle(mensaje.get("usuario.eliminar.titulo"));
        lblTitulo.setText(mensaje.get("usuario.eliminar.titulo"));
        lblUsuario.setText(mensaje.get("usuario"));
        lblAsociado.setText(mensaje.get("asociado"));

        // Agregamos textos traducidos para los botones
        btnBuscar.setText(mensaje.get("buscar"));
        btnEliminar.setText(mensaje.get("eliminar"));
        btnCancelar.setText(mensaje.get("cancelar"));
    }

    /**
     * Cambia el idioma de la vista según el código de lenguaje y país proporcionados.
     * @param lang código del idioma (ejemplo: "es", "en", "fr")
     * @param country código del país (ejemplo: "EC", "US", "FR")
     */
    public void cambiarIdioma(String lang, String country) {
        mensaje.setLenguaje(lang, country);
        actualizarTextos();
    }




    /**
     * Obtiene el panel principal que contiene todos los componentes visuales de la ventana.
     * @return el panel principal
     */
    public JPanel getPnlPrincipal() {
        return pnlPrincipal;
    }

    /**
     * Asigna un nuevo panel principal a la ventana.
     * @param pnlPrincipal el panel principal a establecer
     */
    public void setPnlPrincipal(JPanel pnlPrincipal) {
        this.pnlPrincipal = pnlPrincipal;
    }

    /**
     * Obtiene la etiqueta del título principal.
     * @return la etiqueta del título
     */
    public JLabel getLblTitulo() {
        return lblTitulo;
    }

    /**
     * Establece el valor de la etiqueta del título.
     * @param lblTitulo etiqueta con el nuevo título
     */
    public void setLblTitulo(JLabel lblTitulo) {
        this.lblTitulo = lblTitulo;
    }

    /**
     * Obtiene la etiqueta que muestra información del campo 'asociado'.
     * @return la etiqueta 'asociado'
     */
    public JLabel getLblAsociado() {
        return lblAsociado;
    }

    /**
     * Establece la etiqueta para el campo 'asociado'.
     * @param lblAsociado etiqueta a asignar para el campo asociado
     */
    public void setLblAsociado(JLabel lblAsociado) {
        this.lblAsociado = lblAsociado;
    }

    /**
     * Obtiene el campo de texto donde se ingresa el identificador del usuario.
     * @return campo de texto para ID del usuario
     */
    public JTextField getTxtUsuario() {
        return txtUsuario;
    }

    /**
     * Asigna un nuevo campo de texto para el ID del usuario.
     * @param txtUsuario campo de texto para ID de usuario
     */
    public void setTxtUsuario(JTextField txtUsuario) {
        this.txtUsuario = txtUsuario;
    }

    /**
     * Obtiene el campo de texto que muestra el registro asociado al usuario.
     * @return campo de texto con información del registro
     */
    public JTextField getTxtRegistro() {
        return txtRegistro;
    }


    /**
     * Establece el campo de texto para mostrar información del registro.
     * @param txtRegistro el campo de texto del registro
     */
    public void setTxtRegistro(JTextField txtRegistro) {
        this.txtRegistro = txtRegistro;
    }

    /**
     * Obtiene el campo de texto que muestra la información de acceso del usuario.
     * @return el campo de texto de acceso
     */
    public JTextField getTxtAcceso() {
        return txtAcceso;
    }

    /**
     * Establece el campo de texto para mostrar la información de acceso del usuario.
     * @param txtAcceso el campo de texto de acceso a establecer
     */
    public void setTxtAcceso(JTextField txtAcceso) {
        this.txtAcceso = txtAcceso;
    }

    /**
     * Obtiene el campo de texto que muestra información del usuario asociado.
     * @return el campo de texto del usuario asociado
     */
    public JTextField getTxtAsociado() {
        return txtAsociado;
    }

    /**
     * Establece el campo de texto del usuario asociado.
     * @param txtAsociado el campo de texto para asociado
     */
    public void setTxtAsociado(JTextField txtAsociado) {
        this.txtAsociado = txtAsociado;
    }

    /**
     * Obtiene el botón para buscar usuarios.
     * @return el botón de buscar
     */
    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    /**
     * Establece el botón para buscar usuarios.
     * @param btnBuscar el botón para buscar
     */
    public void setBtnBuscar(JButton btnBuscar) {
        this.btnBuscar = btnBuscar;
    }

    /**
     * Obtiene el botón para eliminar usuarios.
     * @return el botón de eliminar
     */
    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    /**
     * Establece el botón para eliminar usuarios.
     * @param btnEliminar el botón para eliminar
     */
    public void setBtnEliminar(JButton btnEliminar) {
        this.btnEliminar = btnEliminar;
    }

    /**
     * Obtiene el botón para cancelar la operación.
     * @return el botón de cancelar
     */
    public JButton getBtnCancelar() {
        return btnCancelar;
    }

    /**
     * Establece el botón para cancelar la operación.
     * @param btnCancelar el botón de cancelar a establecer
     */
    public void setBtnCancelar(JButton btnCancelar) {
        this.btnCancelar = btnCancelar;
    }

    /**
     * Obtiene el manejador de internacionalización utilizado en la vista.
     * @return el manejador de internacionalización
     */
    public MensajeInternacionalizacionHandler getMensaje() {
        return mensaje;
    }

    /**
     * Establece el manejador de internacionalización para la vista.
     * @param mensaje el manejador de internacionalización
     */
    public void setMensaje(MensajeInternacionalizacionHandler mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * Muestra un cuadro de diálogo con un mensaje obtenido desde el archivo de propiedades
     * según la clave proporcionada.
     * @param keyMensaje la clave del mensaje a mostrar
     */
    public void mostrarMensaje(String keyMensaje) {
        JOptionPane.showMessageDialog(this, mensaje.get(keyMensaje));
    }




}