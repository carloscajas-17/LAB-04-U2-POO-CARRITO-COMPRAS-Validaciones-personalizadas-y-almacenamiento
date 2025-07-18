/**
 * Controladores principales del sistema que gestionan la lógica de negocio entre vistas y modelos.
 */
package ec.edu.ec.poo.controller;

import ec.edu.ec.poo.dao.UsuarioDAO;
import ec.edu.ec.poo.excepciones.CamposExcepcion;
import ec.edu.ec.poo.excepciones.ContraseniaExcepcion;
import ec.edu.ec.poo.modelo.RespuestaSeguridad;
import ec.edu.ec.poo.modelo.Usuario;
import ec.edu.ec.poo.utils.MensajeInternacionalizacionHandler;
import ec.edu.ec.poo.vista.Contraseña.NuevaContrasenaView;
import ec.edu.ec.poo.vista.Contraseña.RecuperarCuentaView;

import javax.swing.*;
import java.util.List;
import java.util.Random;

/**
 * Controlador encargado de gestionar la lógica relacionada con la recuperación de contraseñas.
 * <p>
 * Permite validar la identidad del usuario mediante preguntas de seguridad y gestionar el cambio de contraseña.
 * Facilita la comunicación entre las vistas de recuperación de cuenta y los modelos correspondientes.
 * También soporta la actualización dinámica de idioma para las interfaces gráficas.
 * </p>
 *
 * Este controlador sigue el patrón MVC, conectando la lógica de negocio del usuario con las vistas
 * {@link RecuperarCuentaView} y {@link NuevaContrasenaView}.

 */
public class ContrasenaController {

    /** DAO encargado de la gestión de usuarios */
    private final UsuarioDAO usuarioDAO;

    /** Manejador para cambiar los textos de la interfaz según el idioma */
    private final MensajeInternacionalizacionHandler mensaje;

    /** Usuario actualmente en proceso de recuperación de contraseña */
    private Usuario usuarioActual;

    /** Pregunta de seguridad actual que debe responder el usuario */
    private RespuestaSeguridad preguntaActual;

    /** Vista para recuperar cuenta mediante preguntas de seguridad */
    private RecuperarCuentaView recuperarCuentaView;

    /** Vista para establecer la nueva contraseña */
    private NuevaContrasenaView nuevaContrasenaView;

    /**
     * Constructor del controlador encargado de la lógica de recuperación de contraseña.
     * @param usuarioDAO DAO para acceso y manipulación de datos de usuarios.
     * @param mensaje Manejador para la internacionalización de mensajes.
     */
    public ContrasenaController(UsuarioDAO usuarioDAO, MensajeInternacionalizacionHandler mensaje) {
        this.usuarioDAO = usuarioDAO;
        this.mensaje = mensaje;
    }

    /**
     * Cambia el idioma actual de todas las vistas asociadas a este controlador.
     * @param lang Código de idioma (ejemplo: "es", "en")
     * @param country Código de país (ejemplo: "EC", "US")
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

    /**
     * Inicia el proceso completo de recuperación de contraseña:
     * 1. Verifica existencia del usuario mediante ID y nombre.
     * 2. Valida la respuesta a una pregunta de seguridad aleatoria.
     * 3. Permite establecer una nueva contraseña con confirmación.
     */
    public void iniciarRecuperacion() {
        recuperarCuentaView = new RecuperarCuentaView(mensaje);

        // Paso 1: Validar el usuario
        recuperarCuentaView.getBtnValidarUsuario().addActionListener(e -> {
            String id = recuperarCuentaView.getTxtId().getText().trim();
            String nombre = recuperarCuentaView.getTxtNombre().getText().trim();

            // Buscar usuario por ID
            Usuario usuario = usuarioDAO.buscarPorUsername(id);
            if (usuario == null || !usuario.getNombre().equalsIgnoreCase(nombre)) {
                recuperarCuentaView.mostrarMensaje("usuario.no.encontrado");
                return;
            }
            this.usuarioActual = usuario;

            // Validar que tenga mínimo 3 preguntas de seguridad
            List<RespuestaSeguridad> respuestas = usuario.getRespuestasSeguridad();
            if (respuestas == null || respuestas.size() < 3) {
                recuperarCuentaView.mostrarMensaje("error.intentos.superados");
                return;
            }

            // Elegir una pregunta de seguridad al azar
            this.preguntaActual = respuestas.get(new Random().nextInt(respuestas.size()));
            recuperarCuentaView.setPreguntaActual(preguntaActual);
            recuperarCuentaView.getLblPregunta().setText(preguntaActual.getPregunta().getTexto());
        });

        // Paso 2: Validar respuesta a la pregunta de seguridad
        recuperarCuentaView.getBtnValidarPregunta().addActionListener(e -> {
            String respuesta = recuperarCuentaView.getTxtRespuesta().getText().trim();
            if (preguntaActual.getRespuesta().equalsIgnoreCase(respuesta)) {
                recuperarCuentaView.mostrarMensaje("respuesta.correcta");

                // Mostrar ventana para nueva contraseña
                nuevaContrasenaView = new NuevaContrasenaView(mensaje);
                nuevaContrasenaView.setVisible(true);

                // Aceptar nueva contraseña
                nuevaContrasenaView.getBtnAceptar().addActionListener(a -> {
                    String nueva = new String(nuevaContrasenaView.getTxtNueva().getPassword());
                    String confirmar = new String(nuevaContrasenaView.getTxtConfirmar().getPassword());

                    // Validaciones de campos
                    if (nueva.isEmpty() || confirmar.isEmpty()) {
                        nuevaContrasenaView.mostrarMensaje("error.campos.vacios");
                        return;
                    }
                    if (!nueva.equals(confirmar)) {
                        nuevaContrasenaView.mostrarMensaje("error.contrasena.no.coincide");
                        return;
                    }

                    // Confirmación final
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
                        } catch (CamposExcepcion camposExcepcion) {
                            nuevaContrasenaView.mostrarMensaje(camposExcepcion.getMessage());
                        } catch (ContraseniaExcepcion contraseniaExcepcion) {
                            nuevaContrasenaView.mostrarMensaje(contraseniaExcepcion.getMessage());
                        }
                    }
                });

                // Cancelar acción de cambio de contraseña
                nuevaContrasenaView.getBtnCancelar().addActionListener(a -> nuevaContrasenaView.dispose());

            } else {
                recuperarCuentaView.mostrarMensaje("respuesta.incorrecta");
            }
        });

        // Cancelar todo el proceso de recuperación
        recuperarCuentaView.getBtnCancelar().addActionListener(e -> recuperarCuentaView.dispose());

        // Mostrar la ventana principal de recuperación
        recuperarCuentaView.setVisible(true);
    }
}
