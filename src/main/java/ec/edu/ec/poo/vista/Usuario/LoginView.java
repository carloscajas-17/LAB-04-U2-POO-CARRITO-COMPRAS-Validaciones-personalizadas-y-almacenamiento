package ec.edu.ec.poo.vista.Usuario;
import ec.edu.ec.poo.utils.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

/**
 * Clase LoginView que representa la interfaz gráfica para el inicio de sesión de usuarios.
 * Permite seleccionar idioma, iniciar sesión, registrar un nuevo usuario o recuperar contraseña.
 * Maneja internacionalización mediante {@link MensajeInternacionalizacionHandler}.
 */
public class LoginView extends JFrame {
    /** Panel principal de la ventana de inicio de sesión */
    private JPanel pnlPrincipal;

    /** Panel superior donde se muestra el título */
    private JPanel pnlSuperior;

    /** Panel central que contiene los campos de usuario y contraseña */
    private JPanel pnlCentral;

    /** Campo de texto para ingresar el nombre de usuario */
    private JTextField txtUsuario;

    /** Campo de texto para ingresar la contraseña */
    private JPasswordField txtContrasenia;

    /** Botón para iniciar sesión */
    private JButton btnIniciar;

    /** Botón para registrar un nuevo usuario */
    private JButton btnRegistrar;

    /** ComboBox para cambiar el idioma */
    private JComboBox<String> cbxIdioma;

    /** Etiqueta para "Usuario" */
    private JLabel lblUser;

    /** Etiqueta para "Contraseña" */
    private JLabel lblContrasenia;

    /** Etiqueta del título de la ventana */
    private JLabel lblTitulo;

    /** Botón para recuperación de contraseña */
    private JButton btnOlvidoContrasena;

    /** Panel para mostrar el título */
    private JPanel txtTitulo;
    /**
     * ComboBox para seleccionar el tipo de almacenamiento del sistema
     * (por ejemplo: Memoria, Archivo Texto, Archivo Binario).
     */
    private JComboBox<String> cbxAlmacenamiento;


    /** Manejador de internacionalización */
    private MensajeInternacionalizacionHandler mensaje;

    /** Códigos de idioma disponibles */
    private String[] codigosIdioma = {"es", "en", "fr"};

    /** Idioma seleccionado, por defecto español */
    private String idiomaSeleccionado = "es";

    /** País seleccionado, por defecto Ecuador */
    private String paisSeleccionado = "EC";

    /** Listener para notificar cuando se cambia el idioma */
    private ActionListener listenerCambioIdioma;

    /**
     * Constructor principal de la vista Login, recibe el manejador de mensajes para internacionalización.
     * @param mensaje manejador de textos internacionalizados
     */
    public LoginView(MensajeInternacionalizacionHandler mensaje) {
        this.mensaje = mensaje;
        initComponents();
        cargarDatos();
        agregarListeners();
        actualizarTextos();

        // Ícono para Iniciar Sesión
        URL urlIniciar = LoginView.class.getClassLoader().getResource("imagenes/INICIARCESION.jpg");
        if (urlIniciar != null) {
            ImageIcon icono = new ImageIcon(new ImageIcon(urlIniciar).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
            btnIniciar.setIcon(icono);
        } else {
            System.out.println("Error al cargar INICIARCESION.jpg");
        }

         // Ícono para Registrar
        URL urlRegistrar = LoginView.class.getClassLoader().getResource("imagenes/REGISTRARUSU.png");
        if (urlRegistrar != null) {
            ImageIcon icono = new ImageIcon(new ImageIcon(urlRegistrar).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
            btnRegistrar.setIcon(icono);
        } else {
            System.out.println("Error al cargar REGISTRARUSU.png");
        }

         // Ícono para ¿Olvidó su contraseña?
        URL urlOlvido = LoginView.class.getClassLoader().getResource("imagenes/olvidemicontra.png");
        if (urlOlvido != null) {
            ImageIcon icono = new ImageIcon(new ImageIcon(urlOlvido).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
            btnOlvidoContrasena.setIcon(icono);
        } else {
            System.out.println("Error al cargar olvidemicontra.png");
        }

    }



    /**
     * Método privado para añadir listeners al comboBox para cambiar idioma.
     */
    private void agregarListeners() {
        cbxIdioma.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cambiarIdioma();
            }
        });
    }



    /**
     * Método privado para inicializar los componentes visuales.
     */
    private void initComponents() {
        setContentPane(pnlPrincipal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 450);
        setLocationRelativeTo(null);
    }

    /**
     * Método privado para cargar los idiomas en el comboBox.
     */
    private void cargarDatos() {
        cbxIdioma.removeAllItems();
        cbxIdioma.addItem(mensaje.get("menu.idioma.es"));
        cbxIdioma.addItem(mensaje.get("menu.idioma.en"));
        cbxIdioma.addItem(mensaje.get("menu.idioma.fr"));

        if (idiomaSeleccionado.equals("es")) cbxIdioma.setSelectedIndex(0);
        else if (idiomaSeleccionado.equals("en")) cbxIdioma.setSelectedIndex(1);
        else if (idiomaSeleccionado.equals("fr")) cbxIdioma.setSelectedIndex(2);
        // Cargar opciones de almacenamiento
        cbxAlmacenamiento.removeAllItems();
        cbxAlmacenamiento.addItem("MEMORIA");
        cbxAlmacenamiento.addItem("TEXTO");
        cbxAlmacenamiento.addItem("BINARIO");

    }

    /**
     * Método privado para asignar textos según el idioma seleccionado.
     */
    private void actualizarTextos() {
        setTitle(mensaje.get("login.titulo"));

        lblTitulo.setText(mensaje.get("login.titulo"));
        lblUser.setText(mensaje.get("usuario"));
        lblContrasenia.setText((mensaje.get("contrasenia")));

        btnIniciar.setText(mensaje.get("iniciar"));
        btnRegistrar.setText(mensaje.get("registrar"));
        btnOlvidoContrasena.setText(mensaje.get("login.olvido_contrasena"));

        cargarDatos();

    }


    /**
     * Permite establecer un ActionListener externo para ser notificado cuando cambia el idioma.
     * @param listenerCambioIdioma listener externo que reacciona a cambio de idioma
     */
    public void setListenerCambioIdioma(ActionListener listenerCambioIdioma) {
        this.listenerCambioIdioma = listenerCambioIdioma;
    }


    /**
     * Cambia el idioma de la vista y actualiza los textos.
     */
    private void cambiarIdioma() {
        int seleccion = cbxIdioma.getSelectedIndex();

        if (seleccion != -1 && seleccion < codigosIdioma.length) {
            idiomaSeleccionado = codigosIdioma[seleccion];
            paisSeleccionado = idiomaSeleccionado.equals("es") ? "EC" :
                    idiomaSeleccionado.equals("fr") ? "FR" : "US";
            mensaje.setLenguaje(idiomaSeleccionado, paisSeleccionado);
            actualizarTextos();

            if (listenerCambioIdioma != null) {
                listenerCambioIdioma.actionPerformed(
                        new ActionEvent(this, ActionEvent.ACTION_PERFORMED, idiomaSeleccionado + "_" + paisSeleccionado)
                );
            }
        }
    }




    /**
     * Obtiene el panel principal que contiene toda la interfaz gráfica del login.
     * @return JPanel principal del formulario de inicio de sesión.
     */
    public JPanel getPnlPrincipal() {
        return pnlPrincipal;
    }

    /**
     * Establece el panel principal de la interfaz.
     * @param pnlPrincipal JPanel principal a asignar.
     */
    public void setPnlPrincipal(JPanel pnlPrincipal) {
        this.pnlPrincipal = pnlPrincipal;
    }

    /**
     * Obtiene el panel superior donde se encuentra el título.
     * @return JPanel superior de la vista.
     */
    public JPanel getPnlSuperior() {
        return pnlSuperior;
    }

    /**
     * Asigna un nuevo panel superior.
     * @param pnlSuperior JPanel superior a establecer.
     */
    public void setPnlSuperior(JPanel pnlSuperior) {
        this.pnlSuperior = pnlSuperior;
    }

    /**
     * Obtiene el panel central donde están los campos de usuario y contraseña.
     * @return JPanel central de la vista.
     */
    public JPanel getPnlCentral() {
        return pnlCentral;
    }

    /**
     * Establece el panel central de la vista.
     * @param pnlCentral JPanel central a asignar.
     */
    public void setPnlCentral(JPanel pnlCentral) {
        this.pnlCentral = pnlCentral;
    }

    /**
     * Obtiene el campo de texto donde se ingresa el usuario.
     * @return JTextField para el nombre de usuario.
     */
    public JTextField getTxtUsuario() {
        return txtUsuario;
    }


    /**
     * Establece el campo de texto para ingresar el nombre de usuario.
     * @param txtUsuario JTextField que permite capturar el usuario.
     */
    public void setTxtUsuario(JTextField txtUsuario) {
        this.txtUsuario = txtUsuario;
    }

    /**
     * Obtiene el campo de contraseña donde el usuario ingresa su clave.
     * @return JPasswordField para la contraseña.
     */
    public JPasswordField getTxtContrasenia() {
        return txtContrasenia;
    }

    /**
     * Establece el campo de contraseña del formulario.
     * @param txtContrasenia JPasswordField a asignar para la contraseña.
     */
    public void setTxtContrasenia(JPasswordField txtContrasenia) {
        this.txtContrasenia = txtContrasenia;
    }

    /**
     * Obtiene el botón para iniciar sesión.
     * @return JButton de inicio de sesión.
     */
    public JButton getBtnIniciar() {
        return btnIniciar;
    }

    /**
     * Asigna el botón para iniciar sesión.
     * @param btnIniciar JButton que permite iniciar sesión.
     */
    public void setBtnIniciar(JButton btnIniciar) {
        this.btnIniciar = btnIniciar;
    }

    /**
     * Obtiene el botón para registrar un nuevo usuario.
     * @return JButton para registro de usuario.
     */
    public JButton getBtnRegistrar() {
        return btnRegistrar;
    }

    /**
     * Establece el botón para registrar un nuevo usuario.
     * @param btnRegistrar JButton para registrar un usuario nuevo.
     */
    public void setBtnRegistrar(JButton btnRegistrar) {
        this.btnRegistrar = btnRegistrar;
    }

    /**
     * Obtiene el combo box de idiomas disponibles.
     * @return JComboBox que permite seleccionar el idioma.
     */
    public JComboBox<String> getCbxIdioma() {
        return cbxIdioma;
    }

    /**
     * Establece el combo box para seleccionar idioma.
     * @param cbxIdioma JComboBox con las opciones de idioma.
     */
    public void setCbxIdioma(JComboBox<String> cbxIdioma) {
        this.cbxIdioma = cbxIdioma;
    }

    /**
     * Obtiene la etiqueta que describe el campo de usuario.
     * @return JLabel para el campo usuario.
     */
    public JLabel getLblUser() {
        return lblUser;
    }


    /**
     * Establece la etiqueta del campo usuario.
     * @param lblUser JLabel que representa el texto descriptivo del campo usuario.
     */
    public void setLblUser(JLabel lblUser) {
        this.lblUser = lblUser;
    }

    /**
     * Obtiene la etiqueta del campo contraseña.
     * @return JLabel que representa el texto descriptivo del campo contraseña.
     */
    public JLabel getLblContrasenia() {
        return lblContrasenia;
    }

    /**
     * Establece la etiqueta del campo contraseña.
     * @param lblContrasenia JLabel que representa el texto descriptivo del campo contraseña.
     */
    public void setLblContrasenia(JLabel lblContrasenia) {
        this.lblContrasenia = lblContrasenia;
    }

    /**
     * Obtiene la etiqueta del título principal de la vista.
     * @return JLabel correspondiente al título principal.
     */
    public JLabel getLblTitulo() {
        return lblTitulo;
    }

    /**
     * Establece la etiqueta del título principal.
     * @param lblTitulo JLabel que contiene el título principal de la interfaz.
     */
    public void setLblTitulo(JLabel lblTitulo) {
        this.lblTitulo = lblTitulo;
    }

    /**
     * Obtiene el manejador de internacionalización utilizado para gestionar textos multilingües.
     * @return MensajeInternacionalizacionHandler encargado de la localización de textos.
     */
    public MensajeInternacionalizacionHandler getMensaje() {
        return mensaje;
    }

    /**
     * Establece el manejador de internacionalización para actualizar textos según el idioma seleccionado.
     * @param mensaje objeto MensajeInternacionalizacionHandler a asignar.
     */
    public void setMensaje(MensajeInternacionalizacionHandler mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * Obtiene el arreglo de códigos de idioma utilizados en el combo de selección.
     * @return Arreglo de Strings que representa los códigos de idioma (ej. "es", "en", "fr").
     */
    public String[] getCodigosIdioma() {
        return codigosIdioma;
    }


    /**
     * Establece el arreglo de códigos de idioma disponibles para el selector de idioma.
     * @param codigosIdioma Arreglo de Strings con los códigos de idioma (por ejemplo: "es", "en", "fr").
     */
    public void setCodigosIdioma(String[] codigosIdioma) {
        this.codigosIdioma = codigosIdioma;
    }

    /**
     * Obtiene el idioma actualmente seleccionado.
     * @return String que representa el código del idioma seleccionado (por ejemplo, "es").
     */
    public String getIdiomaSeleccionado() {
        return idiomaSeleccionado;
    }

    /**
     * Establece el idioma seleccionado para la interfaz gráfica.
     * @param idiomaSeleccionado Código del idioma a establecer (por ejemplo, "es", "en").
     */
    public void setIdiomaSeleccionado(String idiomaSeleccionado) {
        this.idiomaSeleccionado = idiomaSeleccionado;
    }

    /**
     * Obtiene el país asociado al idioma seleccionado.
     * @return String que representa el país del idioma seleccionado (por ejemplo, "EC").
     */
    public String getPaisSeleccionado() {
        return paisSeleccionado;
    }

    /**
     * Establece el país seleccionado para la configuración regional.
     * @param paisSeleccionado Código del país (por ejemplo, "EC", "US", "FR").
     */
    public void setPaisSeleccionado(String paisSeleccionado) {
        this.paisSeleccionado = paisSeleccionado;
    }

    /**
     * Obtiene el botón utilizado para recuperar contraseña.
     * @return JButton correspondiente a la opción "Olvidé mi contraseña".
     */
    public JButton getBtnOlvidoContrasena() {
        return btnOlvidoContrasena;
    }

    /**
     * Establece el botón que ejecuta la funcionalidad de recuperación de contraseña.
     * @param btnOlvidoContrasena JButton para la opción "Olvidé mi contraseña".
     */
    public void setBtnOlvidoContrasena(JButton btnOlvidoContrasena) {
        this.btnOlvidoContrasena = btnOlvidoContrasena;
    }

    /**
     * Obtiene el panel gráfico utilizado para mostrar el título principal.
     * @return JPanel que contiene el título visual de la interfaz.
     */
    public JPanel getTxtTitulo() {
        return txtTitulo;
    }

    /**
     * Establece el panel que contiene el título principal de la interfaz gráfica.
     * @param txtTitulo JPanel que se mostrará como título en la parte superior.
     */
    public void setTxtTitulo(JPanel txtTitulo) {
        this.txtTitulo = txtTitulo;
    }

    /**
     * Muestra un mensaje emergente utilizando la clave de internacionalización proporcionada.
     * @param mensajeKey Clave que corresponde al mensaje en los archivos de internacionalización (.properties).
     */
    public void mostrarMensaje(String mensajeKey) {
        JOptionPane.showMessageDialog(this, mensaje.get(mensajeKey));
    }

    /**
     * Obtiene el comboBox de opciones de almacenamiento para seleccionar entre memoria, texto o binario.
     * @return JComboBox con opciones de almacenamiento.
     */
    public JComboBox<String> getCbxAlmacenamiento() { return cbxAlmacenamiento; }


    /**
     * Limpia los campos de entrada del formulario de inicio de sesión, es decir,
     * borra el contenido de los campos de usuario y contraseña.
     */
    public void limpiarCampos() {
        txtUsuario.setText("");
        txtContrasenia.setText("");
    }




}
