package ec.edu.ec.poo.vista;
// Importaciones necesarias para el funcionamiento del sistema
import ec.edu.ec.poo.controller.*;
import ec.edu.ec.poo.dao.CarritoDAO;
import ec.edu.ec.poo.dao.PreguntaSeguridadDAO;
import ec.edu.ec.poo.dao.ProductoDAO;
import ec.edu.ec.poo.dao.UsuarioDAO;
import ec.edu.ec.poo.dao.imple.memoria.CarritoDAOMemoria;
import ec.edu.ec.poo.dao.imple.memoria.PreguntaSeguridadDAOMemoria;
import ec.edu.ec.poo.dao.imple.memoria.ProductoDAOMemoria;
import ec.edu.ec.poo.dao.imple.memoria.UsuarioDAOMemoria;

import ec.edu.ec.poo.utils.MensajeInternacionalizacionHandler;
import ec.edu.ec.poo.vista.Carrito.*;
import ec.edu.ec.poo.vista.Producto.*;
import ec.edu.ec.poo.vista.Usuario.*;

import ec.edu.ec.poo.dao.imple.binario.*;

import ec.edu.ec.poo.dao.imple.texto.*;
import ec.edu.ec.poo.modelo.Rol;
import ec.edu.ec.poo.modelo.Usuario;

import javax.swing.*;

/**
 * Clase principal que inicia la aplicación de carrito de compras.
 * Se encarga de inicializar el idioma, vistas, controladores y DAO según
 * el tipo de almacenamiento seleccionado por el usuario (Memoria, Texto o Binario).
 * Además, configura el menú principal con los permisos de usuario.
 */
public class Main {

    /**
     * Manejador de internacionalización para cambiar idioma del sistema.
     */
    private static MensajeInternacionalizacionHandler mensaje;

    /** DAO para operaciones sobre la entidad Usuario */
    private static UsuarioDAO usuarioDAO;

    /** DAO para operaciones sobre la entidad Producto */
    private static ProductoDAO productoDAO;

    /** DAO para operaciones sobre la entidad Carrito */
    private static CarritoDAO carritoDAO;

    /** DAO para operaciones sobre la entidad Pregunta de Seguridad */
    private static PreguntaSeguridadDAO preguntaDAO;

    /**
     * Método principal que ejecuta la aplicación por primera vez.
     * Se inicializa con idioma Español (Ecuador).
     * @param args argumentos de línea de comandos no utilizados.
     */
    public static void main(String[] args) {
        iniciarAplicacion("es", "EC");
    }

    /**
     * Inicializa la aplicación completa con idioma y país definidos.
     * Carga vistas, controladores y DAO según el tipo de almacenamiento elegido.
     * @param lang idioma seleccionado (ej. "es").
     * @param country país seleccionado (ej. "EC").
     */
    public static void iniciarAplicacion(String lang, String country) {
        mensaje = new MensajeInternacionalizacionHandler(lang, country);

        LoginView loginView = new LoginView(mensaje);

        UsuarioRegistroView usuarioRegistroView = new UsuarioRegistroView(mensaje);
        UsuarioEliminarView usuarioEliminarView = new UsuarioEliminarView(mensaje);
        UsuarioListaView usuarioListaView = new UsuarioListaView(mensaje);
        UsuarioModificarView usuarioModificarView = new UsuarioModificarView(mensaje);

        loginView.setVisible(true);

        while (loginView.getCbxAlmacenamiento().getSelectedIndex() == -1) {
            try { Thread.sleep(200); } catch (InterruptedException e) {}
        }

        String tipo = loginView.getCbxAlmacenamiento().getSelectedItem().toString();
        if (tipo.equalsIgnoreCase("MEMORIA")) {
            usuarioDAO = new UsuarioDAOMemoria();
            carritoDAO = new CarritoDAOMemoria();
            preguntaDAO = new PreguntaSeguridadDAOMemoria();
            productoDAO = new ProductoDAOMemoria();
        } else if (tipo.equalsIgnoreCase("TEXTO")) {
            usuarioDAO = new UsuarioDAOArchivoTexto("datos/usuarios.txt");
            carritoDAO = new CarritoDAOArchivoTexto("datos/carritos.txt", usuarioDAO.listarTodos());
            preguntaDAO = new PreguntaSeguridadDAOArchivoTexto("datos/preguntas.txt");
            productoDAO = new ProductoDAOArchivoTexto("datos/productos.txt");
        } else if (tipo.equalsIgnoreCase("BINARIO")) {
            usuarioDAO = new UsuarioDAOArchivoBinario("datos/usuarios.bin");
            carritoDAO = new CarritoDAOArchivoBinario("datos/carritos.bin");
            preguntaDAO = new PreguntaSeguridadDAOArchivoBinario("datos/preguntas.bin");
            productoDAO = new ProductoDAOArchivoBinario("datos/productos.bin");
        }

        UsuarioController usuarioController = new UsuarioController(
                usuarioDAO, carritoDAO, loginView, preguntaDAO,
                usuarioRegistroView, usuarioEliminarView, usuarioListaView, usuarioModificarView);

        while (usuarioController.getUsuarioAutenticado() == null) {
            try { Thread.sleep(200); } catch (InterruptedException e) { return; }
        }




        Usuario usuarioAutenticado = usuarioController.getUsuarioAutenticado();
        loginView.dispose();

        usuarioController.configurarDAOsDesdeLogin(loginView);

        usuarioDAO = usuarioController.getUsuarioDAO();
        productoDAO = usuarioController.getProductoDAO();
        carritoDAO = usuarioController.getCarritoDAO();
        preguntaDAO = usuarioController.getPreguntaDAO();

        ProductoAnadirView productoAnadirView = new ProductoAnadirView(mensaje);
        ProductoListaView productoListaView = new ProductoListaView(mensaje);
        ProductoEliminarView productoEliminarView = new ProductoEliminarView(mensaje);
        ProductoModificarView productoModificarView = new ProductoModificarView(mensaje);

        CarritoAnadirView carritoAnadirView = new CarritoAnadirView(mensaje);
        CarritoBuscarView carritoBuscarView = new CarritoBuscarView(mensaje);
        CarritoEliminarView carritoEliminarView = new CarritoEliminarView(mensaje);
        CarritoModificarView carritoModificarView = new CarritoModificarView(mensaje);
        CarritoListaView carritoListaView = new CarritoListaView(mensaje);

        ProductoController productoController = new ProductoController(
                productoDAO, productoAnadirView, productoListaView,
                productoEliminarView, productoModificarView, carritoAnadirView);

        CarritoController carritoController = new CarritoController(
                carritoDAO, productoDAO,
                carritoAnadirView, carritoBuscarView, carritoEliminarView,
                carritoModificarView, carritoListaView,
                usuarioAutenticado);

        MenuPrincipalView menuPrincipal = new MenuPrincipalView(mensaje);
        menuPrincipal.setVisible(true);

        menuPrincipal.getMiJDesktopPane().add(carritoAnadirView);
        menuPrincipal.getMiJDesktopPane().add(carritoBuscarView);
        menuPrincipal.getMiJDesktopPane().add(carritoEliminarView);
        menuPrincipal.getMiJDesktopPane().add(carritoModificarView);
        menuPrincipal.getMiJDesktopPane().add(carritoListaView);

        menuPrincipal.getMiJDesktopPane().add(productoAnadirView);
        menuPrincipal.getMiJDesktopPane().add(productoListaView);
        menuPrincipal.getMiJDesktopPane().add(productoEliminarView);
        menuPrincipal.getMiJDesktopPane().add(productoModificarView);

        if (usuarioAutenticado.getRol() == Rol.ADMINISTRADOR) {
            menuPrincipal.getMenuItemCrear().addActionListener(e -> mostrarVentanaInterna(productoAnadirView, menuPrincipal));
            menuPrincipal.getMenuItemBuscar().addActionListener(e -> mostrarVentanaInterna(productoListaView, menuPrincipal));
            menuPrincipal.getMenuItemEliminar().addActionListener(e -> mostrarVentanaInterna(productoEliminarView, menuPrincipal));
            menuPrincipal.getMenuItemActualizar().addActionListener(e -> mostrarVentanaInterna(productoModificarView, menuPrincipal));

            menuPrincipal.getMiJDesktopPane().add(usuarioEliminarView);
            menuPrincipal.getMiJDesktopPane().add(usuarioListaView);
            menuPrincipal.getMiJDesktopPane().add(usuarioModificarView);
        } else {
            menuPrincipal.deshabilitarMenusAdministrador();
        }

        // Listeners para menú Carrito
        menuPrincipal.getMenuItemCrearCarrito().addActionListener(e -> mostrarVentanaInterna(carritoAnadirView, menuPrincipal));
        menuPrincipal.getMenuItemBuscarCarrito().addActionListener(e -> mostrarVentanaInterna(carritoBuscarView, menuPrincipal));
        menuPrincipal.getMenuItemEliminarCarrito().addActionListener(e -> mostrarVentanaInterna(carritoEliminarView, menuPrincipal));
        menuPrincipal.getMenuItemActualizarCarrito().addActionListener(e -> mostrarVentanaInterna(carritoModificarView, menuPrincipal));
        menuPrincipal.getMenuItemListarCarrito().addActionListener(e -> {
            carritoController.cargarCarritos();
            mostrarVentanaInterna(carritoListaView, menuPrincipal);
        });

        // Listeners para cambio de idioma
        menuPrincipal.getMenuItemEspaniol().addActionListener(e -> cambiarIdioma("es", "EC", usuarioController, productoController, carritoController, menuPrincipal));
        menuPrincipal.getMenuItemIngles().addActionListener(e -> cambiarIdioma("en", "US", usuarioController, productoController, carritoController, menuPrincipal));
        menuPrincipal.getMenuItemFrances().addActionListener(e -> cambiarIdioma("fr", "FR", usuarioController, productoController, carritoController, menuPrincipal));

        // Listener para cerrar sesión y reiniciar aplicación
        menuPrincipal.getMenuItemCerrarSesion().addActionListener(e -> {
            menuPrincipal.dispose();
            JOptionPane.showMessageDialog(null, mensaje.get("mensaje.sesion.cerrada"), mensaje.get("titulo.informacion"), JOptionPane.INFORMATION_MESSAGE);
            usuarioDAO = null;
            productoDAO = null;
            carritoDAO = null;
            preguntaDAO = null;
            SwingUtilities.invokeLater(() -> iniciarAplicacion(lang, country));
        });
    }

    /**
     * Muestra una ventana interna dentro del JDesktopPane asegurando visibilidad.
     * @param frame JInternalFrame que se mostrará.
     * @param principal ventana principal contenedora.
     */
    private static void mostrarVentanaInterna(JInternalFrame frame, MenuPrincipalView principal) {
        if (!frame.isVisible()) {
            principal.getMiJDesktopPane().add(frame);
            frame.setVisible(true);
        } else {
            try {
                frame.setSelected(true);
                frame.toFront();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void cambiarIdioma(String lang, String country,
                                      UsuarioController usuarioController,
                                      ProductoController productoController,
                                      CarritoController carritoController,
                                      MenuPrincipalView principalView) {
        String rutaIcono = switch (lang) {
            case "es" -> "imagenes/banderaes.png";
            case "en" -> "imagenes/banderauk.png";
            case "fr" -> "imagenes/banderafr.png";
            default -> "imagenes/banderauk.png";
        };
        principalView.cambiarIdioma(lang, country, rutaIcono);
        usuarioController.cambiarIdiomaVistas(lang, country);
        productoController.cambiarIdiomaVistas(lang, country);
        carritoController.cambiarIdiomaVistas(lang, country);
    }
}
