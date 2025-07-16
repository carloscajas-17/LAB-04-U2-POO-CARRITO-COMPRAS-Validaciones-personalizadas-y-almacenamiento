package ec.edu.ec.poo.vista;


import ec.edu.ec.poo.controller.*;
import ec.edu.ec.poo.dao.imple.*;
import ec.edu.ec.poo.modelo.*;
import ec.edu.ec.poo.utils.MensajeInternacionalizacionHandler;
import ec.edu.ec.poo.vista.Carrito.*;
import ec.edu.ec.poo.vista.Producto.*;
import ec.edu.ec.poo.vista.Usuario.*;

import javax.swing.*;

public class Main {

    // Variables globales
    private static MensajeInternacionalizacionHandler mensaje;
    private static UsuarioDAOMemoria usuarioDAO;
    private static ProductoDAOMemoria productoDAO;
    private static CarritoDAOMemoria carritoDAO;
    private static PreguntaSeguridadDAOMemoria preguntaDAO;

    public static void main(String[] args) {
        iniciarAplicacion("es", "EC");
    }

    public static void iniciarAplicacion(String lang, String country) {
        mensaje = new MensajeInternacionalizacionHandler(lang, country);

        // DAOs compartidos (persisten)
        if (usuarioDAO == null) usuarioDAO = new UsuarioDAOMemoria();
        if (productoDAO == null) productoDAO = new ProductoDAOMemoria();
        if (carritoDAO == null) carritoDAO = new CarritoDAOMemoria();
        if (preguntaDAO == null) preguntaDAO = new PreguntaSeguridadDAOMemoria();

        // 1. Vistas de usuario
        LoginView loginView = new LoginView(mensaje);
        UsuarioRegistroView usuarioRegistroView = new UsuarioRegistroView(mensaje);
        UsuarioEliminarView usuarioEliminarView = new UsuarioEliminarView(mensaje);
        UsuarioListaView usuarioListaView = new UsuarioListaView(mensaje);
        UsuarioModificarView usuarioModificarView = new UsuarioModificarView(mensaje);

        // 2. Controlador
        UsuarioController usuarioController = new UsuarioController(
                usuarioDAO, carritoDAO, loginView, preguntaDAO,
                usuarioRegistroView, usuarioEliminarView, usuarioListaView, usuarioModificarView
        );

        loginView.setVisible(true);

        // 3. Esperar autenticación
        while (usuarioController.getUsuarioAutenticado() == null) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                return;
            }
        }

        Usuario usuarioAutenticado = usuarioController.getUsuarioAutenticado();

        // 4. Vistas de producto
        ProductoAnadirView productoAnadirView = new ProductoAnadirView(mensaje);
        ProductoListaView productoListaView = new ProductoListaView(mensaje);
        ProductoEliminarView productoEliminarView = new ProductoEliminarView(mensaje);
        ProductoModificarView productoModificarView = new ProductoModificarView(mensaje);

        // 5. Vistas de carrito
        CarritoAnadirView carritoAnadirView = new CarritoAnadirView(mensaje);
        CarritoBuscarView carritoBuscarView = new CarritoBuscarView(mensaje);
        CarritoEliminarView carritoEliminarView = new CarritoEliminarView(mensaje);
        CarritoModificarView carritoModificarView = new CarritoModificarView(mensaje);
        CarritoListaView carritoListaView = new CarritoListaView(mensaje);

        // 6. Controladores
        ProductoController productoController = new ProductoController(
                productoDAO, productoAnadirView, productoListaView,
                productoEliminarView, productoModificarView, carritoAnadirView);

        CarritoController carritoController = new CarritoController(
                carritoDAO, productoDAO,
                carritoAnadirView, carritoBuscarView, carritoEliminarView,
                carritoModificarView, carritoListaView,
                usuarioAutenticado);

        // 7. Ventana principal
        MenuPrincipalView menuPrincipal = new MenuPrincipalView(mensaje);
        menuPrincipal.setVisible(true);

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

        menuPrincipal.getMenuItemCrearCarrito().addActionListener(e -> mostrarVentanaInterna(carritoAnadirView, menuPrincipal));
        menuPrincipal.getMenuItemBuscarCarrito().addActionListener(e -> mostrarVentanaInterna(carritoBuscarView, menuPrincipal));
        menuPrincipal.getMenuItemEliminarCarrito().addActionListener(e -> mostrarVentanaInterna(carritoEliminarView, menuPrincipal));
        menuPrincipal.getMenuItemActualizarCarrito().addActionListener(e -> mostrarVentanaInterna(carritoModificarView, menuPrincipal));
        menuPrincipal.getMenuItemListarCarrito().addActionListener(e -> {
            carritoController.cargarCarritos();
            mostrarVentanaInterna(carritoListaView, menuPrincipal);
        });

        // Cambiar idioma dinámicamente
        menuPrincipal.getMenuItemEspaniol().addActionListener(e -> cambiarIdioma("es", "EC", usuarioController, productoController, carritoController, menuPrincipal));
        menuPrincipal.getMenuItemIngles().addActionListener(e -> cambiarIdioma("en", "US", usuarioController, productoController, carritoController, menuPrincipal));
        menuPrincipal.getMenuItemFrances().addActionListener(e -> cambiarIdioma("fr", "FR", usuarioController, productoController, carritoController, menuPrincipal));

        // Cerrar sesión
        menuPrincipal.getMenuItemCerrarSesion().addActionListener(e -> {
            menuPrincipal.dispose();
            JOptionPane.showMessageDialog(null, mensaje.get("mensaje.sesion.cerrada"),
                    mensaje.get("titulo.informacion"), JOptionPane.INFORMATION_MESSAGE);
            SwingUtilities.invokeLater(() -> iniciarAplicacion(lang, country)); // Reiniciar login con idioma actual
        });
    }

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

        // ✅ Cambiar idioma + bandera del menú
        String rutaIcono = switch (lang) {
            case "es" -> "imagenes/banderaes.png";
            case "en" -> "imagenes/banderauk.png";
            case "fr" -> "imagenes/banderafr.png";
            default -> "imagenes/banderauk.png";
        };

        principalView.cambiarIdioma(lang, country, rutaIcono);

        // ✅ Cambiar idioma en controladores
        usuarioController.cambiarIdiomaVistas(lang, country);
        productoController.cambiarIdiomaVistas(lang, country);
        carritoController.cambiarIdiomaVistas(lang, country);
    }

}
