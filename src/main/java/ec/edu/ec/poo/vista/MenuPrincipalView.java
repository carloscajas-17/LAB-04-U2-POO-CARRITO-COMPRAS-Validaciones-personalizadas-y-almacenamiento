package ec.edu.ec.poo.vista;


import ec.edu.ec.poo.utils.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class MenuPrincipalView extends JFrame {

    private MensajeInternacionalizacionHandler mensaje;

    private JMenuBar menuBar;
    private JMenu menuProducto, menuCarrito, menuIdioma, menuSalir;
    private JMenuItem menuItemCrear, menuItemEliminar, menuItemActualizar, menuItemBuscar;
    private JMenuItem menuItemCrearCarrito, menuItemBuscarCarrito, menuItemEliminarCarrito, menuItemActualizarCarrito, menuItemListarCarrito;
    private JMenuItem menuItemEspaniol, menuItemIngles, menuItemFrances;
    private JMenuItem menuItemSalir, menuItemCerrarSesion;

    private MiJDesktopPane miJDesktopPane;

    public MenuPrincipalView(MensajeInternacionalizacionHandler mensaje) {
        this.mensaje = mensaje;
        initComponents();
        agregarListeners();
    }

    private ImageIcon cargarIcono(String ruta) {
        URL url = getClass().getClassLoader().getResource(ruta);
        if (url != null) {
            return new ImageIcon(new ImageIcon(url).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        } else {
            System.out.println("No se pudo cargar el ícono: " + ruta);
            return null;
        }
    }

    private void initComponents() {
        setTitle(mensaje.get("app.titulo"));
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        miJDesktopPane = new MiJDesktopPane();
        setContentPane(miJDesktopPane);

        // Menú
        menuBar = new JMenuBar();

        menuProducto = new JMenu(mensaje.get("menu.producto"));
        menuProducto.setIcon(cargarIcono("imagenes/listarproductos.png"));

        menuCarrito = new JMenu(mensaje.get("menu.carrito"));
        menuCarrito.setIcon(cargarIcono("imagenes/listarcarritos.png"));

        menuIdioma = new JMenu(mensaje.get("menu.idioma"));
        menuIdioma.setIcon(cargarIcono("imagenes/banderauk.png")); // idioma predeterminado inglés

        menuSalir = new JMenu(mensaje.get("menu.salir"));
        menuSalir.setIcon(cargarIcono("imagenes/cancelarcontra.png"));

        // Opciones de producto
        menuItemCrear = new JMenuItem(mensaje.get("menu.producto.crear"));
        menuItemBuscar = new JMenuItem(mensaje.get("menu.producto.buscar"));
        menuItemEliminar = new JMenuItem(mensaje.get("menu.producto.eliminar"));
        menuItemActualizar = new JMenuItem(mensaje.get("menu.producto.actualizar"));

        menuProducto.add(menuItemCrear);
        menuProducto.add(menuItemBuscar);
        menuProducto.add(menuItemEliminar);
        menuProducto.add(menuItemActualizar);

        // Opciones de carrito
        menuItemCrearCarrito = new JMenuItem(mensaje.get("menu.carrito.crear"));
        menuItemBuscarCarrito = new JMenuItem(mensaje.get("menu.carrito.buscar"));
        menuItemEliminarCarrito = new JMenuItem(mensaje.get("menu.carrito.eliminar"));
        menuItemActualizarCarrito = new JMenuItem(mensaje.get("menu.carrito.actualizar"));
        menuItemListarCarrito = new JMenuItem(mensaje.get("menu.carrito.listar"));

        menuCarrito.add(menuItemCrearCarrito);
        menuCarrito.add(menuItemBuscarCarrito);
        menuCarrito.add(menuItemEliminarCarrito);
        menuCarrito.add(menuItemActualizarCarrito);
        menuCarrito.add(menuItemListarCarrito);

        // Idiomas
        menuItemEspaniol = new JMenuItem(mensaje.get("menu.idioma.es"));
        menuItemIngles = new JMenuItem(mensaje.get("menu.idioma.en"));
        menuItemFrances = new JMenuItem(mensaje.get("menu.idioma.fr"));

        menuIdioma.add(menuItemEspaniol);
        menuIdioma.add(menuItemIngles);
        menuIdioma.add(menuItemFrances);

        // Salir
        menuItemCerrarSesion = new JMenuItem(mensaje.get("menu.salir.cerrar"));
        menuItemSalir = new JMenuItem(mensaje.get("menu.salir.salir"));

        menuSalir.add(menuItemCerrarSesion);
        menuSalir.add(menuItemSalir);

        // Añadir menús a la barra
        menuBar.add(menuProducto);
        menuBar.add(menuCarrito);
        menuBar.add(menuIdioma);
        menuBar.add(menuSalir);

        setJMenuBar(menuBar);
    }


    private void agregarListeners() {
        // Cambiar idioma desde el menú
        menuItemEspaniol.addActionListener(e -> cambiarIdioma("es", "EC", "imagenes/banderaes.png"));
        menuItemIngles.addActionListener(e -> cambiarIdioma("en", "US", "imagenes/banderauk.png"));
        menuItemFrances.addActionListener(e -> cambiarIdioma("fr", "FR", "imagenes/banderafr.png"));

        // Cerrar sesión
        menuItemCerrarSesion.addActionListener(e -> {
            int respuesta = JOptionPane.showConfirmDialog(
                    this,
                    mensaje.get("mensaje.confirmar.cerrar.sesion"),
                    mensaje.get("titulo.confirmacion"),
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );
            if (respuesta == JOptionPane.YES_OPTION) {
                dispose();
                SwingUtilities.invokeLater(() -> {
                    var nuevoMensaje = new MensajeInternacionalizacionHandler(mensaje.getLocale().getLanguage(), mensaje.getLocale().getCountry());

                    var loginView = new ec.edu.ec.poo.vista.Usuario.LoginView(nuevoMensaje);
                    var usuarioDAO = new ec.edu.ec.poo.dao.imple.UsuarioDAOMemoria();
                    var productoDAO = new ec.edu.ec.poo.dao.imple.ProductoDAOMemoria();
                    var carritoDAO = new ec.edu.ec.poo.dao.imple.CarritoDAOMemoria();
                    var preguntaDAO = new ec.edu.ec.poo.dao.imple.PreguntaSeguridadDAOMemoria();

                    var registro = new ec.edu.ec.poo.vista.Usuario.UsuarioRegistroView(nuevoMensaje);
                    var eliminar = new ec.edu.ec.poo.vista.Usuario.UsuarioEliminarView(nuevoMensaje);
                    var lista = new ec.edu.ec.poo.vista.Usuario.UsuarioListaView(nuevoMensaje);
                    var modificar = new ec.edu.ec.poo.vista.Usuario.UsuarioModificarView(nuevoMensaje);

                    new ec.edu.ec.poo.controller.UsuarioController(usuarioDAO, carritoDAO, loginView, preguntaDAO, registro, eliminar, lista, modificar);

                    loginView.setVisible(true);
                });
                JOptionPane.showMessageDialog(null, mensaje.get("mensaje.sesion.cerrada"), mensaje.get("titulo.informacion"), JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Salir del sistema
        menuItemSalir.addActionListener(e -> {
            int respuesta = JOptionPane.showConfirmDialog(this,
                    mensaje.get("mensaje.confirmar.salir.sistema"),
                    mensaje.get("titulo.confirmacion"),
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);
            if (respuesta == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
    }

    public void cambiarIdioma(String lenguaje, String pais) {
        this.mensaje.setLenguaje(lenguaje, pais);
        setTitle(mensaje.get("app.titulo"));

        // Actualizar textos del menú principal
        menuProducto.setText(mensaje.get("menu.producto"));
        menuCarrito.setText(mensaje.get("menu.carrito"));
        menuIdioma.setText(mensaje.get("menu.idioma"));
        menuSalir.setText(mensaje.get("menu.salir"));

        menuItemCrear.setText(mensaje.get("menu.producto.crear"));
        menuItemBuscar.setText(mensaje.get("menu.producto.buscar"));
        menuItemEliminar.setText(mensaje.get("menu.producto.eliminar"));
        menuItemActualizar.setText(mensaje.get("menu.producto.actualizar"));

        menuItemCrearCarrito.setText(mensaje.get("menu.carrito.crear"));
        menuItemBuscarCarrito.setText(mensaje.get("menu.carrito.buscar"));
        menuItemEliminarCarrito.setText(mensaje.get("menu.carrito.eliminar"));
        menuItemActualizarCarrito.setText(mensaje.get("menu.carrito.actualizar"));
        menuItemListarCarrito.setText(mensaje.get("menu.carrito.listar"));

        menuItemEspaniol.setText(mensaje.get("menu.idioma.es"));
        menuItemIngles.setText(mensaje.get("menu.idioma.en"));
        menuItemFrances.setText(mensaje.get("menu.idioma.fr"));

        menuItemCerrarSesion.setText(mensaje.get("menu.salir.cerrar"));
        menuItemSalir.setText(mensaje.get("menu.salir.salir"));
    }


    // Métodos de acceso
    public JMenuItem getMenuItemCrear() { return menuItemCrear; }
    public JMenuItem getMenuItemBuscar() { return menuItemBuscar; }
    public JMenuItem getMenuItemEliminar() { return menuItemEliminar; }
    public JMenuItem getMenuItemActualizar() { return menuItemActualizar; }

    public JMenuItem getMenuItemCrearCarrito() { return menuItemCrearCarrito; }
    public JMenuItem getMenuItemBuscarCarrito() { return menuItemBuscarCarrito; }
    public JMenuItem getMenuItemEliminarCarrito() { return menuItemEliminarCarrito; }
    public JMenuItem getMenuItemActualizarCarrito() { return menuItemActualizarCarrito; }
    public JMenuItem getMenuItemListarCarrito() { return menuItemListarCarrito; }

    public JMenuItem getMenuItemEspaniol() { return menuItemEspaniol; }
    public JMenuItem getMenuItemIngles() { return menuItemIngles; }
    public JMenuItem getMenuItemFrances() { return menuItemFrances; }
    public JMenuItem getMenuItemCerrarSesion() { return menuItemCerrarSesion; }

    public MiJDesktopPane getMiJDesktopPane() { return miJDesktopPane; }
    public void cambiarIdioma(String lenguaje, String pais, String rutaIcono) {
        // Cambiar idioma (llama a la versión que solo cambia textos)
        cambiarIdioma(lenguaje, pais);

        // Cambiar ícono del menú "Idioma"
        menuIdioma.setIcon(cargarIcono(rutaIcono));
    }


    public void deshabilitarMenusAdministrador() {
        menuProducto.setVisible(false);
    }

    public void mostrarMensaje(String mensajeClave) {
        JOptionPane.showMessageDialog(this, mensaje.get(mensajeClave));
    }
}
