package ec.edu.ec.poo.controller;

import ec.edu.ec.poo.dao.CarritoDAO;
import ec.edu.ec.poo.dao.PreguntaSeguridadDAO;
import ec.edu.ec.poo.dao.UsuarioDAO;
import ec.edu.ec.poo.dao.imple.PreguntaSeguridadDAOMemoria;
import ec.edu.ec.poo.modelo.*;
import ec.edu.ec.poo.utils.FormateadorUtils;
import ec.edu.ec.poo.utils.MensajeInternacionalizacionHandler;
//import ec.edu.ec.poo.vista.Contrasenia.NuevaContrasenaView;
import ec.edu.ec.poo.vista.Contraseña.RecuperarCuentaView;
import ec.edu.ec.poo.vista.Usuario.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class UsuarioController {

    private Usuario usuario;
    private final UsuarioDAO usuarioDAO;
    private final CarritoDAO carritoDAO;
    private final LoginView loginView;
    private final UsuarioRegistroView usuarioRegistroView;
    private final UsuarioEliminarView usuarioEliminarView;
    private final UsuarioListaView usuarioListaView;
    private final UsuarioModificarView usuarioModificarView;
    private final PreguntaSeguridadDAO preguntaDAO;
    private MensajeInternacionalizacionHandler mensaje;

    public UsuarioController(
            UsuarioDAO usuarioDAO,
            CarritoDAO carritoDAO,
            LoginView loginView,
            PreguntaSeguridadDAO preguntaDAO, // ✅ nuevo parámetro
            UsuarioRegistroView usuarioRegistroView,
            UsuarioEliminarView usuarioEliminarView,
            UsuarioListaView usuarioListaView,
            UsuarioModificarView usuarioModificarView
    ) {
        this.usuarioDAO = usuarioDAO;
        this.carritoDAO = carritoDAO;
        this.loginView = loginView;
        this.preguntaDAO = preguntaDAO; // ✅ guardar el nuevo DAO
        this.usuarioRegistroView = usuarioRegistroView;
        this.usuarioEliminarView = usuarioEliminarView;
        this.usuarioListaView = usuarioListaView;
        this.usuarioModificarView = usuarioModificarView;
        this.mensaje = loginView.getMensaje();
        configurarEventosEnVistas();
    }


    private void configurarEventosEnVistas() {
        loginView.getBtnIniciar().addActionListener(e -> autenticar());

        loginView.getBtnRegistrar().addActionListener(e -> {
            usuarioRegistroView.limpiarCampos();

            String lang = loginView.getMensaje().getLocale().getLanguage();
            String country = loginView.getMensaje().getLocale().getCountry();

            // Cambiar idioma en el handler
            mensaje.setLenguaje(lang, country);

            // Actualizar preguntas con el nuevo idioma
            if (preguntaDAO instanceof PreguntaSeguridadDAOMemoria) {
                ((PreguntaSeguridadDAOMemoria) preguntaDAO).actualizarPreguntasConIdioma(mensaje);
            }

            // Actualizar vista
            usuarioRegistroView.cambiarIdioma(lang, country);

            // Volver a cargar preguntas ya traducidas
            DefaultTableModel modelo = (DefaultTableModel) usuarioRegistroView.getTablaPreguntas().getModel();
            modelo.setRowCount(0);
            for (PreguntaSeguridad pregunta : preguntaDAO.obtenerTodas()) {
                modelo.addRow(new Object[]{false, pregunta.getTexto(), ""});
            }

            usuarioRegistroView.setVisible(true);
        });
        usuarioRegistroView.getBtnCancelar().addActionListener(e -> {
            usuarioRegistroView.dispose();      // Cierra la ventana de registro
            loginView.setVisible(true);         // Vuelve a mostrar el login
        });


        loginView.getBtnOlvidoContrasena().addActionListener(e -> {
            ContrasenaController contrasenaController = new ContrasenaController(usuarioDAO, loginView.getMensaje());
            contrasenaController.iniciarRecuperacion();
        });








        usuarioRegistroView.getBtnRegistrar().addActionListener(e -> registrarUsuario());
        usuarioEliminarView.getBtnBuscar().addActionListener(e -> buscarUsuarioEliminar());
        usuarioEliminarView.getBtnEliminar().addActionListener(e -> eliminarUsuario());
        usuarioListaView.getBtnBuscar().addActionListener(e -> buscarUsuarioLista());
        usuarioListaView.getBtnLimpiar().addActionListener(e -> limpiarCamposLista());
        usuarioModificarView.getBtnModificar().addActionListener(e -> modificarUsuario());
        usuarioModificarView.getBtnMostrar().addActionListener(e -> mostrarContrasenia());
    }

    // ✅ Login solo con ID y contraseña
    private void autenticar() {
        String id = loginView.getTxtUsuario().getText();
        String contrasenia = loginView.getTxtContrasenia().getText();

        usuario = usuarioDAO.autenticar(id, contrasenia);
        if (usuario == null) {
            loginView.mostrarMensaje("usuario.login.error");
        } else {
            loginView.dispose();
        }
        loginView.limpiarCampos();
    }

    public Usuario getUsuarioAutenticado() {
        return usuario;
    }

    // ✅ Registro con todos los campos ampliados
    private void registrarUsuario() {
        String id = usuarioRegistroView.getTxtId().getText().trim();
        String nombre = usuarioRegistroView.getTxtNombre().getText().trim();
        String fechaNacimiento = usuarioRegistroView.getTxtFechaNacimiento().getText().trim();
        String email = usuarioRegistroView.getTxtEmail().getText().trim();
        String telefono = usuarioRegistroView.getTxtTelefono().getText().trim();
        String direccion = usuarioRegistroView.getTxtDireccion().getText().trim();
        String password = new String(usuarioRegistroView.getTxtContrasenia().getPassword());
        String confirmar = new String(usuarioRegistroView.getTxtConfirmarContrasenia().getPassword());

        if (id.isEmpty() || nombre.isEmpty() || fechaNacimiento.isEmpty() || email.isEmpty() ||
                telefono.isEmpty() || direccion.isEmpty() || password.isEmpty() || confirmar.isEmpty()) {
            usuarioRegistroView.mostrarMensaje("mensaje.campos.vacios");
            return;
        }

        if (!password.equals(confirmar)) {
            usuarioRegistroView.mostrarMensaje("mensaje.contrasenia.no.coincide");
            return;
        }

        if (usuarioDAO.buscarPorUsername(id) != null) {
            usuarioRegistroView.mostrarMensaje("mensaje.usuario.ya.existe");
            return;
        }

        if (usuarioDAO.buscarPorEmail(email) != null) {
            usuarioRegistroView.mostrarMensaje("mensaje.email.ya.existe");
            return;
        }

        if (usuarioDAO.buscarPorTelefono(telefono) != null) {
            usuarioRegistroView.mostrarMensaje("mensaje.telefono.ya.existe");
            return;
        }

        // ✅ Crear el usuario primero
        Usuario nuevoUsuario = new Usuario(id, nombre, password, Rol.USUARIO,
                fechaNacimiento, email, telefono, direccion);

        // Obtener preguntas seleccionadas
        DefaultTableModel modelo = (DefaultTableModel) usuarioRegistroView.getTablaPreguntas().getModel();
        List<RespuestaSeguridad> respuestas = new ArrayList<>();

        for (int i = 0; i < modelo.getRowCount(); i++) {
            Boolean usar = (Boolean) modelo.getValueAt(i, 0);
            String preguntaTexto = (String) modelo.getValueAt(i, 1);
            String respuesta = (String) modelo.getValueAt(i, 2);

            if (usar != null && usar) {
                if (respuesta == null || respuesta.trim().isEmpty()) {
                    usuarioRegistroView.mostrarMensaje("error.respuesta.vacia");
                    return;
                }
                for (PreguntaSeguridad pregunta : preguntaDAO.obtenerTodas()) {
                    if (pregunta.getTexto().equals(preguntaTexto)) {
                        // ✅ Aquí se agrega también el usuario a la respuesta
                        respuestas.add(new RespuestaSeguridad(pregunta, respuesta.trim(), nuevoUsuario));
                        break;
                    }
                }
            }
        }

        if (respuestas.size() < 3) {
            usuarioRegistroView.mostrarMensaje("Debe seleccionar al menos 3 preguntas");
            return;
        }

        nuevoUsuario.setRespuestasSeguridad(respuestas);

        usuarioDAO.crear(nuevoUsuario);
        usuarioRegistroView.mostrarMensaje("mensaje.usuario.registrado");
        usuarioRegistroView.limpiarCampos();
        usuarioRegistroView.dispose();
    }


    // Reemplaza el método agregarListeners() con esto:
    private void agregarListeners() {
        loginView.getCbxIdioma().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String seleccionado = loginView.getCbxIdioma().getSelectedItem().toString();
                if (seleccionado.equals("Español")) {
                    cambiarIdiomaVistas("es", "EC");
                } else if (seleccionado.equals("Inglés")) {
                    cambiarIdiomaVistas("en", "US");
                } else if (seleccionado.equals("Francés")) {
                    cambiarIdiomaVistas("fr", "FR");
                }
            }
        });

        loginView.getBtnOlvidoContrasena().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RecuperarCuentaView recuperarView = new RecuperarCuentaView(loginView.getMensaje());
                recuperarView.setVisible(true);
            }
        });
    }



    private void buscarUsuarioEliminar() {
        String id = usuarioEliminarView.getTxtUsuario().getText().trim();
        if (id.isEmpty()) {
            usuarioEliminarView.mostrarMensaje("mensaje.campos.vacios");
            return;
        }

        Usuario user = usuarioDAO.buscarPorUsername(id);
        if (user != null) {
            int asociados = contarCarritosUsuario(user);
            usuarioEliminarView.getTxtAsociado().setText(String.valueOf(asociados));
            usuarioEliminarView.getBtnEliminar().setEnabled(asociados == 0);
        } else {
            usuarioEliminarView.mostrarMensaje("usuario.no.encontrado");
        }
    }

    private void eliminarUsuario() {
        String id = usuarioEliminarView.getTxtUsuario().getText().trim();
        Usuario user = usuarioDAO.buscarPorUsername(id);
        if (user != null) {
            usuarioDAO.eliminar(user.getId());
            usuarioEliminarView.mostrarMensaje("usuario.eliminado");
            usuarioEliminarView.getTxtUsuario().setText("");
            usuarioEliminarView.getTxtAsociado().setText("");
        }
    }

    private void buscarUsuarioLista() {
        String id = usuarioListaView.getTxtUsuario().getText().trim();
        List<Usuario> usuarios;

        if (id.isEmpty()) {
            usuarios = usuarioDAO.listarTodos();
        } else {
            usuarios = new ArrayList<>();
            Usuario user = usuarioDAO.buscarPorUsername(id);
            if (user != null) usuarios.add(user);
        }

        cargarUsuariosEnLaTabla(usuarios);
    }
    /*private void cargarPreguntas() {
        List<PreguntaSeguridad> preguntas = preguntaDAO.obtenerTodas();
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Usar", "Pregunta", "Respuesta"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 1; // Solo editar "Usar" y "Respuesta"
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 0 ? Boolean.class : String.class;
            }
        };

        for (PreguntaSeguridad p : preguntas) {
            model.addRow(new Object[]{false, p.getTexto(), ""});
        }

        // ✅ Corrección aquí
        usuarioRegistroView.getTablaPreguntas().setModel(model);
    }

     */



    private void cargarUsuariosEnLaTabla(List<Usuario> usuarios) {
        DefaultTableModel modelo = (DefaultTableModel) usuarioListaView.getTblDetalle().getModel();
        modelo.setRowCount(0);
        Locale locale = usuarioListaView.getMensaje().getLocale();

        for (Usuario user : usuarios) {
            List<Carrito> carritos = carritoDAO.listarPorUsuario(user.getId());
            int numCarritos = carritos.size();
            double total = calcularTotalCarritos(carritos);

            modelo.addRow(new Object[]{
                    user.getId(),
                    numCarritos,
                    FormateadorUtils.formatearMoneda(total, locale)
            });
        }
    }

    private void limpiarCamposLista() {
        usuarioListaView.getTxtUsuario().setText("");
        DefaultTableModel modelo = (DefaultTableModel) usuarioListaView.getTblDetalle().getModel();
        modelo.setRowCount(0);
    }

    private double calcularTotalCarritos(List<Carrito> carritos) {
        return carritos.stream().mapToDouble(Carrito::calcularTotal).sum();
    }

    private void modificarUsuario() {
        String id = usuarioModificarView.getTxtUsuario().getText().trim();
        String contrasenia = new String(usuarioModificarView.getTxtContrasenia().getPassword());
        String confirmacion = new String(usuarioModificarView.getTxtConfirmar().getPassword());

        if (id.isEmpty()) {
            usuarioModificarView.mostrarMensaje("usuario.vacio");
            return;
        }

        if (!contrasenia.equals(confirmacion)) {
            usuarioModificarView.mostrarMensaje("contrasenias.no.coinciden");
            return;
        }

        if (contrasenia.isEmpty()) {
            contrasenia = usuario.getContrasenia();
        } else if (!validarContrasenia(contrasenia)) {
            usuarioModificarView.mostrarMensaje("contrasenia.invalida");
            return;
        }

        usuario.setId(id);
        usuario.setContrasenia(contrasenia);

        if (usuarioDAO.actualizar(usuario)) {
            usuarioModificarView.mostrarMensaje("usuario.actualizado");
            usuarioModificarView.dispose();
        } else {
            usuarioModificarView.mostrarMensaje("error.actualizar");
        }
    }
    private void cargarPreguntasTraducidas() {
        List<PreguntaSeguridad> preguntas = preguntaDAO.obtenerTodas();

        DefaultTableModel modelo = new DefaultTableModel(
                new Object[]{"Usar", "Pregunta", "Respuesta"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0 || column == 2; // ✅ Solo checkbox y respuesta
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 0 ? Boolean.class : String.class;
            }
        };

        for (PreguntaSeguridad p : preguntas) {
            modelo.addRow(new Object[]{false, p.getTexto(), ""});
        }

        usuarioRegistroView.getTablaPreguntas().setModel(modelo);
    }


    private void mostrarContrasenia() {
        JPasswordField contrasenia = usuarioModificarView.getTxtContrasenia();
        JPasswordField confirmacion = usuarioModificarView.getTxtConfirmar();

        if (contrasenia.getEchoChar() == '*') {
            contrasenia.setEchoChar((char) 0);
            confirmacion.setEchoChar((char) 0);
            usuarioModificarView.getBtnMostrar().setText(usuarioModificarView.getMensaje().get("ocultar"));
        } else {
            contrasenia.setEchoChar('*');
            confirmacion.setEchoChar('*');
            usuarioModificarView.getBtnMostrar().setText(usuarioModificarView.getMensaje().get("mostrar"));
        }
    }

    private boolean validarContrasenia(String contrasenia) {
        return contrasenia.length() >= 5;
    }

    private int contarCarritosUsuario(Usuario usuario) {
        List<Carrito> carritos = carritoDAO.listarPorUsuario(usuario.getId());
        return carritos.size();
    }

    public void cambiarIdiomaVistas(String lang, String country) {
        mensaje.setLenguaje(lang, country);

        // ✅ Actualizar preguntas del DAO con el nuevo idioma
        if (preguntaDAO instanceof PreguntaSeguridadDAOMemoria) {
            ((PreguntaSeguridadDAOMemoria) preguntaDAO).actualizarPreguntasConIdioma(mensaje);
        }

        // ✅ Actualizar vistas
        if (usuarioRegistroView != null) {
            usuarioRegistroView.cambiarIdioma(lang, country);
            cargarPreguntasTraducidas(); // ✅ usa el método correcto
        }

        if (usuarioEliminarView != null) {
            usuarioEliminarView.cambiarIdioma(lang, country);
        }

        if (usuarioListaView != null) {
            usuarioListaView.getMensaje().setLenguaje(lang, country);
            usuarioListaView.actualizarTextos(); // este método debe existir
        }

        if (usuarioModificarView != null) {
            usuarioModificarView.cambiarIdioma(lang, country);
        }
    }











}
