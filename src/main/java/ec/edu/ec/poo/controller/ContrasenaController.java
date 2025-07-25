package ec.edu.ec.poo.controller;



import ec.edu.ec.poo.dao.UsuarioDAO;

import ec.edu.ec.poo.excepciones.CamposExcepcion;

import ec.edu.ec.poo.excepciones.ContraseniaExcepcion;

import ec.edu.ec.poo.modelo.RespuestaSeguridad;

import ec.edu.ec.poo.modelo.Usuario;

import ec.edu.ec.poo.utils.MensajeInternacionalizacionHandler;

import ec.edu.ec.poo.vista.Contraseña.NuevaContrasenaView;

import ec.edu.ec.poo.vista.Contraseña.RecuperarCuentaView;

import ec.edu.ec.poo.vista.Usuario.LoginView;



import javax.swing.*;

import java.util.List;

import java.util.Random;


/**
 * **Clase `ContrasenaController`**
 * <p>
 * Este controlador se encarga de toda la lógica de negocio para la recuperación y el cambio de contraseñas.
 * Actúa como intermediario entre las vistas de la interfaz de usuario ({@link RecuperarCuentaView},
 * {@link NuevaContrasenaView}) y la capa de acceso a datos ({@link UsuarioDAO}). Su función principal es
 * validar la identidad del usuario a través de una pregunta de seguridad y permitirle establecer una nueva
 * contraseña.
 * </p>
 */
public class ContrasenaController {

    /**
     * **Atributo `usuarioDAO`**: `private final UsuarioDAO usuarioDAO`
     * <p>
     * Referencia a la interfaz {@link UsuarioDAO}. Se usa para interactuar con la base de datos o el
     * almacenamiento de datos para buscar y actualizar la información de los usuarios. Es final porque se
     * inicializa en el constructor.
     * </p>
     */
    private final UsuarioDAO usuarioDAO;

    /**
     * **Atributo `mensaje`**: `private final MensajeInternacionalizacionHandler mensaje`
     * <p>
     * Instancia de {@link MensajeInternacionalizacionHandler}. Permite obtener mensajes de texto en el
     * idioma configurado actualmente, lo que facilita la internacionalización de la aplicación. Es final
     * porque se inicializa en el constructor.
     * </p>
     */
    private final MensajeInternacionalizacionHandler mensaje;



    /**
     * **Atributo `usuarioActual`**: `private Usuario usuarioActual`
     * <p>
     * Almacena el objeto {@link Usuario} del usuario que está en proceso de recuperación de contraseña.
     * Se asigna una vez que la validación inicial de ID y nombre es exitosa.
     * </p>
     */
    private Usuario usuarioActual;

    /**
     * **Atributo `preguntaActual`**: `private RespuestaSeguridad preguntaActual`
     * <p>
     * Almacena la pregunta de seguridad seleccionada aleatoriamente y su respuesta correcta, la cual
     * se presenta al usuario para la segunda fase de validación.
     * </p>
     */
    private RespuestaSeguridad preguntaActual;

    /**
     * **Atributo `recuperarCuentaView`**: `private RecuperarCuentaView recuperarCuentaView`
     * <p>
     * Referencia a la ventana de recuperación de cuenta. Se usa para gestionar la interfaz de usuario
     * durante las primeras etapas del proceso.
     * </p>
     */
    private RecuperarCuentaView recuperarCuentaView;

    /**
     * **Atributo `nuevaContrasenaView`**: `private NuevaContrasenaView nuevaContrasenaView`
     * <p>
     * Referencia a la ventana para establecer una nueva contraseña. Se muestra después de que el usuario
     * ha validado su identidad y la pregunta de seguridad.
     * </p>
     */
    private NuevaContrasenaView nuevaContrasenaView;



    /**
     * **Constructor de `ContrasenaController`**
     * <p>
     * Inicializa el controlador con las dependencias necesarias.
     * </p>
     * @param usuarioDAO Objeto {@link UsuarioDAO} para la persistencia de datos de usuarios.
     * @param mensaje    Objeto {@link MensajeInternacionalizacionHandler} para gestionar los mensajes de la interfaz.
     */
    public ContrasenaController(UsuarioDAO usuarioDAO, MensajeInternacionalizacionHandler mensaje) {

        this.usuarioDAO = usuarioDAO;

        this.mensaje = mensaje;

    }



    /**
     * **Método `iniciarRecuperacion`**
     * <p>
     * Inicia el flujo de recuperación de contraseña. Crea y muestra la vista de recuperación de cuenta
     * y configura los listeners de los botones para manejar las interacciones del usuario.
     * </p>
     */
    public void iniciarRecuperacion() {

        recuperarCuentaView = new RecuperarCuentaView(mensaje);



        recuperarCuentaView.getBtnValidarUsuario().addActionListener(e -> validarUsuario());

        recuperarCuentaView.getBtnValidarPregunta().addActionListener(e -> validarPregunta());

        recuperarCuentaView.getBtnCancelar().addActionListener(e -> recuperarCuentaView.dispose());



        recuperarCuentaView.setVisible(true);

    }



    /**
     * **Método `validarUsuario`**
     * <p>
     * Valida la identidad del usuario basándose en su ID (nombre de usuario) y nombre completo.
     * Si la validación es exitosa, selecciona una pregunta de seguridad aleatoria y la muestra en la vista.
     * </p>
     */
    private void validarUsuario() {

        String id = recuperarCuentaView.getTxtId().getText().trim();

        String nombre = recuperarCuentaView.getTxtNombre().getText().trim();



        Usuario usuario = usuarioDAO.buscarPorUsername(id);

        if (usuario == null || !usuario.getNombre().equalsIgnoreCase(nombre)) {

            recuperarCuentaView.mostrarMensaje("usuario.no.encontrado");

            return;

        }



        this.usuarioActual = usuario;

        List<RespuestaSeguridad> respuestas = usuario.getRespuestasSeguridad();

        if (respuestas == null || respuestas.size() < 3) {

            recuperarCuentaView.mostrarMensaje("error.intentos.superados");

            return;

        }



        this.preguntaActual = respuestas.get(new Random().nextInt(respuestas.size()));

        recuperarCuentaView.setPreguntaActual(preguntaActual);

        recuperarCuentaView.getLblPregunta().setText(preguntaActual.getPregunta().getTexto());

    }



    /**
     * **Método `validarPregunta`**
     * <p>
     * Compara la respuesta ingresada por el usuario con la respuesta correcta de la pregunta de seguridad.
     * Si la respuesta es correcta, procede a abrir la ventana para cambiar la contraseña.
     * </p>
     */
    private void validarPregunta() {

        String respuesta = recuperarCuentaView.getTxtRespuesta().getText().trim();

        if (preguntaActual.getRespuesta().equalsIgnoreCase(respuesta)) {

            recuperarCuentaView.mostrarMensaje("respuesta.correcta");

            abrirCambioContrasena();

        } else {

            recuperarCuentaView.mostrarMensaje("respuesta.incorrecta");

        }

    }



    /**
     * **Método `abrirCambioContrasena`**
     * <p>
     * Muestra la ventana para que el usuario ingrese la nueva contraseña. Configura los listeners
     * para manejar los botones de aceptar y cancelar.
     * </p>
     */
    private void abrirCambioContrasena() {

        nuevaContrasenaView = new NuevaContrasenaView(mensaje);

        nuevaContrasenaView.setVisible(true);



        nuevaContrasenaView.getBtnAceptar().addActionListener(e -> cambiarContrasena());

        nuevaContrasenaView.getBtnCancelar().addActionListener(e -> nuevaContrasenaView.dispose());

    }



    /**
     * **Método `cambiarContrasena`**
     * <p>
     * Procesa el cambio de contraseña. Valida que las contraseñas coincidan y no estén vacías.
     * Si todo es correcto, actualiza la contraseña del usuario en el sistema, muestra un mensaje de éxito,
     * y cierra las ventanas de recuperación, regresando a la vista de login.
     * </p>
     */
    private void cambiarContrasena() {

        String nueva = new String(nuevaContrasenaView.getTxtNueva().getPassword());

        String confirmar = new String(nuevaContrasenaView.getTxtConfirmar().getPassword());



        if (nueva.isEmpty() || confirmar.isEmpty()) {

            nuevaContrasenaView.mostrarMensaje("error.campos.vacios");

            return;

        }

        if (!nueva.equals(confirmar)) {

            nuevaContrasenaView.mostrarMensaje("error.contrasena.no.coincide");

            return;

        }



        int confirm = JOptionPane.showConfirmDialog(

                nuevaContrasenaView,

                mensaje.get("mensaje.confirmar.cambio"),

                mensaje.get("titulo.confirmacion"),

                JOptionPane.YES_NO_OPTION);



        if (confirm == JOptionPane.YES_OPTION) {

            try {

                usuarioActual.setContrasenia(nueva);

                usuarioDAO.actualizar(usuarioActual);

                nuevaContrasenaView.mostrarMensaje("mensaje.cambio.exito");

                nuevaContrasenaView.dispose();

                recuperarCuentaView.dispose();



// Mostrar login tras cambio de contraseña

                SwingUtilities.invokeLater(() -> {

                    LoginView loginView = new LoginView(mensaje);

                    loginView.setVisible(true);

                });

            } catch (CamposExcepcion | ContraseniaExcepcion ex) {

                nuevaContrasenaView.mostrarMensaje(ex.getMessage());

            }

        }

    }



    /**
     * **Método `cambiarIdiomaVistas`**
     * <p>
     * Permite cambiar dinámicamente el idioma de las vistas gestionadas por este controlador
     * ({@link RecuperarCuentaView} y {@link NuevaContrasenaView}).
     * </p>
     * @param lang    El nuevo código de idioma (ej. "es", "en").
     * @param country El nuevo código de país (ej. "EC", "US").
     */
    public void cambiarIdiomaVistas(String lang, String country) {

        if (recuperarCuentaView != null) {

            recuperarCuentaView.getMensaje().setLenguaje(lang, country);

            recuperarCuentaView.actualizarTextos();

        }

        if (nuevaContrasenaView != null) {

            nuevaContrasenaView.getMensaje().setLenguaje(lang, country);

            nuevaContrasenaView.actualizarTextos();

        }

    }

}