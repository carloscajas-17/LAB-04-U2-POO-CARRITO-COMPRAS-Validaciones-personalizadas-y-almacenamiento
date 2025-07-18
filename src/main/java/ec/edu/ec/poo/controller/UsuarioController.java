/**
 * Controladores principales del sistema que gestionan la lógica de negocio entre vistas y modelos.
 */
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

/**
 * Controlador encargado de gestionar todas las operaciones relacionadas con usuarios,
 * incluyendo autenticación, registro, eliminación, modificación, listado de usuarios
 * y gestión de preguntas de seguridad.
 * <p>
 * Implementa el patrón MVC conectando las vistas gráficas del módulo Usuario con los DAOs y modelos,
 * controlando la lógica de negocio relacionada a usuarios y sus interacciones dentro del sistema.
 * También maneja la internacionalización de todas las vistas correspondientes.
 * </p>

 */
public class UsuarioController {

    /** Usuario autenticado actualmente */
    private Usuario usuario;

    /** DAO para usuarios */
    private final UsuarioDAO usuarioDAO;

    /** DAO para carritos asociados */
    private final CarritoDAO carritoDAO;

    /** Vista principal Login */
    private final LoginView loginView;


    /** Vista para registro de usuarios */
    private final UsuarioRegistroView usuarioRegistroView;


    /** Vista para eliminación de usuarios */
    private final UsuarioEliminarView usuarioEliminarView;

    /** Vista para listar usuarios */
    private final UsuarioListaView usuarioListaView;


    /** Vista para modificar usuarios */
    private final UsuarioModificarView usuarioModificarView;


    /** DAO para preguntas de seguridad */
    private final PreguntaSeguridadDAO preguntaDAO;


    /** Manejador para internacionalización */
    private MensajeInternacionalizacionHandler mensaje;

    /**
     * Constructor principal para inicializar las vistas y DAOs relacionados a Usuario.
     *
     * @param usuarioDAO DAO para usuarios
     * @param carritoDAO DAO para carritos
     * @param loginView vista login
     * @param preguntaDAO DAO preguntas seguridad
     * @param usuarioRegistroView vista registro
     * @param usuarioEliminarView vista eliminar
     * @param usuarioListaView vista lista
     * @param usuarioModificarView vista modificar
     */
    public UsuarioController(
            UsuarioDAO usuarioDAO,
            CarritoDAO carritoDAO,
            LoginView loginView,
            PreguntaSeguridadDAO preguntaDAO, //  nuevo parámetro
            UsuarioRegistroView usuarioRegistroView,
            UsuarioEliminarView usuarioEliminarView,
            UsuarioListaView usuarioListaView,
            UsuarioModificarView usuarioModificarView
    ) {
        this.usuarioDAO = usuarioDAO;
        this.carritoDAO = carritoDAO;
        this.loginView = loginView;
        this.preguntaDAO = preguntaDAO; //  guardar el nuevo DAO
        this.usuarioRegistroView = usuarioRegistroView;
        this.usuarioEliminarView = usuarioEliminarView;
        this.usuarioListaView = usuarioListaView;
        this.usuarioModificarView = usuarioModificarView;
        this.mensaje = loginView.getMensaje();
        configurarEventosEnVistas();
    }


    /**
     * Configura los listeners para las vistas del módulo usuario.
     * Gestiona registro, autenticación, eliminación, modificación, listado y recuperación de contraseña.
     */
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


    /**
     * Autentica al usuario ingresado con ID y contraseña.
     * Si es válido, cierra la ventana de login.
     */
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

    /**
     * Retorna el usuario autenticado.
     * @return usuario autenticado
     */
    public Usuario getUsuarioAutenticado() {
        return usuario;
    }


    /**
     * Registra un nuevo usuario en el sistema realizando validaciones de campos obligatorios,
     * validación de contraseñas, verificación de datos únicos (ID, email, teléfono)
     * y registro de respuestas de seguridad seleccionadas.
     *
     * Proceso del método:
     * - Valida que todos los campos obligatorios estén llenos.
     * - Verifica que la contraseña coincida con su confirmación.
     * - Comprueba que el ID, correo y teléfono no estén previamente registrados.
     * - Obtiene y valida al menos 3 respuestas de seguridad seleccionadas por el usuario.
     * - Registra el nuevo usuario junto a sus respuestas en la base de datos.
     * - Muestra mensajes correspondientes según validación o éxito.
     */
    private void registrarUsuario() {
        String id = usuarioRegistroView.getTxtId().getText().trim();
        String nombre = usuarioRegistroView.getTxtNombre().getText().trim();
        String fechaNacimiento = usuarioRegistroView.getTxtFechaNacimiento().getText().trim();
        String email = usuarioRegistroView.getTxtEmail().getText().trim();
        String telefono = usuarioRegistroView.getTxtTelefono().getText().trim();
        String direccion = usuarioRegistroView.getTxtDireccion().getText().trim();
        String password = new String(usuarioRegistroView.getTxtContrasenia().getPassword());
        String confirmar = new String(usuarioRegistroView.getTxtConfirmarContrasenia().getPassword());

        // Validar campos obligatorios
        if (id.isEmpty() || nombre.isEmpty() || fechaNacimiento.isEmpty() || email.isEmpty() ||
                telefono.isEmpty() || direccion.isEmpty() || password.isEmpty() || confirmar.isEmpty()) {
            usuarioRegistroView.mostrarMensaje("mensaje.campos.vacios");
            return;
        }

        // Validar coincidencia de contraseñas
        if (!password.equals(confirmar)) {
            usuarioRegistroView.mostrarMensaje("mensaje.contrasenia.no.coincide");
            return;
        }

        // Verificar si el usuario, email o teléfono ya existen
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

        // Creación de nuevo usuario con datos ingresados
        Usuario nuevoUsuario = new Usuario(id, nombre, password, Rol.USUARIO,
                fechaNacimiento, email, telefono, direccion);

        // Recopilar respuestas de seguridad seleccionadas
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

        // Validar que haya al menos 3 respuestas seleccionadas
        if (respuestas.size() < 3) {
            usuarioRegistroView.mostrarMensaje("Debe seleccionar al menos 3 preguntas");
            return;
        }


        // Asociar respuestas al nuevo usuario
        nuevoUsuario.setRespuestasSeguridad(respuestas);

        // Registrar usuario en el DAO
        usuarioDAO.crear(nuevoUsuario);
        usuarioRegistroView.mostrarMensaje("mensaje.usuario.registrado");

        // Limpiar campos y cerrar ventana de registro
        usuarioRegistroView.limpiarCampos();
        usuarioRegistroView.dispose();
    }



    /**
     * Agrega los listeners de eventos a la vista Login.
     *
     * Este método configura dos funcionalidades principales:
     *
     * 1. Cambio dinámico de idioma según la selección del combo box (`CbxIdioma`):
     *    - Español: establece idioma español Ecuador ("es", "EC").
     *    - Inglés: establece idioma inglés Estados Unidos ("en", "US").
     *    - Francés: establece idioma francés Francia ("fr", "FR").
     *
     * 2. Recuperación de contraseña:
     *    - Al presionar el botón de olvido de contraseña (`BtnOlvidoContrasena`), se abre la ventana
     *      `RecuperarCuentaView` utilizando el idioma actual del sistema.
     */
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



    /**
     * Busca un usuario en el sistema para la funcionalidad de eliminación.
     *
     * Funcionamiento:
     * - Obtiene el ID del usuario desde la vista `UsuarioEliminarView`.
     * - Si el campo está vacío, muestra un mensaje de advertencia.
     * - Busca el usuario mediante su ID usando el DAO correspondiente.
     * - Si el usuario existe, calcula la cantidad de carritos asociados.
     *   - Muestra la cantidad en la vista y habilita el botón de eliminar únicamente si no tiene carritos.
     * - Si el usuario no existe, muestra un mensaje indicando que no fue encontrado.
     */
    private void buscarUsuarioEliminar() {
        String id = usuarioEliminarView.getTxtUsuario().getText().trim();

        // Validación de campo vacío
        if (id.isEmpty()) {
            usuarioEliminarView.mostrarMensaje("mensaje.campos.vacios");
            return;
        }

        // Búsqueda del usuario en la base de datos
        Usuario user = usuarioDAO.buscarPorUsername(id);
        if (user != null) {
            // Contar carritos asociados y mostrar en la vista
            int asociados = contarCarritosUsuario(user);
            usuarioEliminarView.getTxtAsociado().setText(String.valueOf(asociados));

            // Habilitar botón eliminar solo si no tiene carritos
            usuarioEliminarView.getBtnEliminar().setEnabled(asociados == 0);
        } else {
            usuarioEliminarView.mostrarMensaje("usuario.no.encontrado");
        }
    }

    /**
     * Elimina un usuario del sistema.
     *
     * Funcionamiento:
     * - Obtiene el ID del usuario desde la vista `UsuarioEliminarView`.
     * - Busca al usuario en la base de datos usando su ID.
     * - Si el usuario existe, lo elimina mediante el DAO y muestra un mensaje de éxito.
     * - Luego limpia los campos de la vista eliminando los datos visualizados.
     *
     * Nota: este método no realiza validaciones previas de carritos asociados,
     * se asume que la validación fue gestionada previamente desde la interfaz.
     */
    private void eliminarUsuario() {
        String id = usuarioEliminarView.getTxtUsuario().getText().trim();
        Usuario user = usuarioDAO.buscarPorUsername(id);
        if (user != null) {
            usuarioDAO.eliminar(user.getId());
            usuarioEliminarView.mostrarMensaje("usuario.eliminado");
            // Limpiar campos tras eliminación
            usuarioEliminarView.getTxtUsuario().setText("");
            usuarioEliminarView.getTxtAsociado().setText("");
        }
    }

    /**
     * Busca usuarios para mostrar en la vista de lista de usuarios.
     *
     * Funcionamiento:
     * - Obtiene el ID ingresado en el campo de búsqueda de la vista `UsuarioListaView`.
     * - Si el campo está vacío, obtiene todos los usuarios registrados mediante `listarTodos()`.
     * - Si el campo tiene un valor, busca un usuario específico por ID:
     *    - Si existe, lo agrega a la lista de resultados.
     * - Llama al método `cargarUsuariosEnLaTabla()` para mostrar los resultados en la tabla.
     */
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



    /**
     * Carga en la tabla de la vista `UsuarioListaView` los datos de una lista de usuarios,
     * mostrando la cantidad de carritos asociados y el total monetario acumulado.
     *
     * Funcionamiento:
     * - Limpia previamente la tabla de datos.
     * - Para cada usuario, obtiene la lista de carritos que tiene asociados.
     * - Calcula el número total de carritos y el monto total de compras usando `calcularTotalCarritos`.
     * - Agrega una fila a la tabla con el ID del usuario, cantidad de carritos y total formateado según el idioma.
     *
     * @param usuarios lista de usuarios a mostrar en la tabla
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

    /**
     * Limpia los campos y la tabla de la vista `UsuarioListaView`.
     *
     * Funcionamiento:
     * - Limpia el campo de texto donde se ingresa el ID del usuario.
     * - Limpia el contenido de la tabla eliminando todas sus filas.
     * Este método se utiliza para reiniciar la vista de listado antes de mostrar nuevos resultados.
     */
    private void limpiarCamposLista() {
        usuarioListaView.getTxtUsuario().setText("");
        DefaultTableModel modelo = (DefaultTableModel) usuarioListaView.getTblDetalle().getModel();
        modelo.setRowCount(0);
    }

    /**
     * Calcula el total acumulado de los carritos asociados a un usuario.
     *
     * Funcionamiento:
     * - Recorre la lista de carritos recibida como parámetro.
     * - Suma el total monetario de cada carrito utilizando el método `calcularTotal()`.
     *
     * @param carritos lista de carritos asociados a un usuario
     * @return suma total del valor de todos los carritos
     */
    private double calcularTotalCarritos(List<Carrito> carritos) {
        return carritos.stream().mapToDouble(Carrito::calcularTotal).sum();
    }

    /**
     * Modifica los datos de un usuario autenticado, permitiendo actualizar el ID y la contraseña.
     *
     * Funcionamiento:
     * - Obtiene el nuevo ID y la nueva contraseña desde la vista `UsuarioModificarView`.
     * - Valida que el campo ID no esté vacío.
     * - Valida que la contraseña coincida con la confirmación ingresada.
     * - Si la contraseña está vacía, conserva la contraseña actual del usuario autenticado.
     * - Si la contraseña no cumple con el criterio mínimo, muestra un mensaje de error.
     * - Actualiza el ID y la contraseña del usuario.
     * - Si la actualización es exitosa, muestra un mensaje de éxito y cierra la vista.
     * - Si falla, muestra un mensaje de error.
     */
    private void modificarUsuario() {
        String id = usuarioModificarView.getTxtUsuario().getText().trim();
        String contrasenia = new String(usuarioModificarView.getTxtContrasenia().getPassword());
        String confirmacion = new String(usuarioModificarView.getTxtConfirmar().getPassword());

        // Validar ID no vacío
        if (id.isEmpty()) {
            usuarioModificarView.mostrarMensaje("usuario.vacio");
            return;
        }

        // Validar coincidencia de contraseñas
        if (!contrasenia.equals(confirmacion)) {
            usuarioModificarView.mostrarMensaje("contrasenias.no.coinciden");
            return;
        }

        // Si no se ingresa contraseña nueva, mantener la actual
        if (contrasenia.isEmpty()) {
            contrasenia = usuario.getContrasenia();
        } else if (!validarContrasenia(contrasenia)) {
            usuarioModificarView.mostrarMensaje("contrasenia.invalida");
            return;
        }

        // Actualizar usuario
        usuario.setId(id);
        usuario.setContrasenia(contrasenia);

        if (usuarioDAO.actualizar(usuario)) {
            usuarioModificarView.mostrarMensaje("usuario.actualizado");
            usuarioModificarView.dispose();
        } else {
            usuarioModificarView.mostrarMensaje("error.actualizar");
        }
    }

    /**
     * Carga dinámicamente las preguntas de seguridad traducidas según el idioma actual.
     * Se actualiza la tabla de la vista UsuarioRegistroView.
     */
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


    /**
     * Alterna la visibilidad de los campos de contraseña y confirmación de contraseña en la vista `UsuarioModificarView`.
     *
     * Funcionamiento:
     * - Si las contraseñas están ocultas (modo seguro), las muestra en texto claro.
     * - Si las contraseñas están visibles, las oculta usando el carácter `*`.
     * - También actualiza dinámicamente el texto del botón para mostrar u ocultar contraseña,
     *   según el idioma configurado mediante internacionalización.
     */
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

    /**
     * Valida si una contraseña cumple con el criterio mínimo de longitud.
     *
     * Reglas de validación:
     * - La contraseña es válida si tiene al menos 5 caracteres.
     *
     * @param contrasenia la contraseña a validar
     * @return true si la contraseña es válida (longitud mínima cumplida), false en caso contrario
     */
    private boolean validarContrasenia(String contrasenia) {
        return contrasenia.length() >= 5;
    }

    /**
     * Cuenta la cantidad de carritos asociados a un usuario.
     *
     * Funcionamiento:
     * - Obtiene la lista de carritos vinculados al usuario mediante su ID.
     * - Retorna el tamaño de la lista, es decir, la cantidad total de carritos encontrados.
     *
     * @param usuario el usuario del cual se quiere contar los carritos asociados
     * @return cantidad total de carritos que tiene el usuario
     */
    private int contarCarritosUsuario(Usuario usuario) {
        List<Carrito> carritos = carritoDAO.listarPorUsuario(usuario.getId());
        return carritos.size();
    }

    /**
     * Cambia el idioma en todas las vistas relacionadas con usuarios y preguntas de seguridad.
     * Actualiza las preguntas traducidas en la vista registro.
     * @param lang código idioma
     * @param country código país
     */
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
