package ec.edu.ec.poo.controller;


import ec.edu.ec.poo.dao.UsuarioDAO;
import ec.edu.ec.poo.modelo.RespuestaSeguridad;
import ec.edu.ec.poo.modelo.Usuario;
import ec.edu.ec.poo.utils.MensajeInternacionalizacionHandler;
import ec.edu.ec.poo.vista.Contraseña.NuevaContrasenaView;
import ec.edu.ec.poo.vista.Contraseña.RecuperarCuentaView;

import javax.swing.*;
import java.util.List;
import java.util.Random;

public class ContrasenaController {

    private final UsuarioDAO usuarioDAO;
    private final MensajeInternacionalizacionHandler mensaje;

    private Usuario usuarioActual;
    private RespuestaSeguridad preguntaActual;
    private RecuperarCuentaView recuperarCuentaView;

    private NuevaContrasenaView nuevaContrasenaView;

    public ContrasenaController(UsuarioDAO usuarioDAO, MensajeInternacionalizacionHandler mensaje) {
        this.usuarioDAO = usuarioDAO;
        this.mensaje = mensaje;
    }

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

    public void iniciarRecuperacion() {
        recuperarCuentaView = new RecuperarCuentaView(mensaje);

        recuperarCuentaView.getBtnValidarUsuario().addActionListener(e -> {
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
        });

        recuperarCuentaView.getBtnValidarPregunta().addActionListener(e -> {
            String respuesta = recuperarCuentaView.getTxtRespuesta().getText().trim();
            if (preguntaActual.getRespuesta().equalsIgnoreCase(respuesta)) {
                recuperarCuentaView.mostrarMensaje("respuesta.correcta");

                nuevaContrasenaView = new NuevaContrasenaView(mensaje);
                nuevaContrasenaView.setVisible(true);

                nuevaContrasenaView.getBtnAceptar().addActionListener(a -> {
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
                            JOptionPane.YES_NO_OPTION
                    );

                    if (confirm == JOptionPane.YES_OPTION) {
                        usuarioActual.setContrasenia(nueva);
                        usuarioDAO.actualizar(usuarioActual);
                        nuevaContrasenaView.mostrarMensaje("mensaje.cambio.exito");
                        nuevaContrasenaView.dispose();
                        recuperarCuentaView.dispose();
                    }
                });

                nuevaContrasenaView.getBtnCancelar().addActionListener(a -> nuevaContrasenaView.dispose());

            } else {
                recuperarCuentaView.mostrarMensaje("respuesta.incorrecta");
            }
        });

        recuperarCuentaView.getBtnCancelar().addActionListener(e -> recuperarCuentaView.dispose());

        recuperarCuentaView.setVisible(true);
    }
}

