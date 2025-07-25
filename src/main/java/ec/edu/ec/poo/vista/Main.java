package ec.edu.ec.poo.vista;



// Importaciones necesarias para el funcionamiento del sistema

import ec.edu.ec.poo.controller.*;

import ec.edu.ec.poo.dao.CarritoDAO;

import ec.edu.ec.poo.dao.PreguntaSeguridadDAO;

import ec.edu.ec.poo.dao.ProductoDAO;

import ec.edu.ec.poo.dao.UsuarioDAO;

import ec.edu.ec.poo.dao.imple.memoria.*;

import ec.edu.ec.poo.dao.imple.texto.*;

import ec.edu.ec.poo.dao.imple.binario.*;

import ec.edu.ec.poo.modelo.Rol;

import ec.edu.ec.poo.modelo.Usuario;

import ec.edu.ec.poo.utils.MensajeInternacionalizacionHandler;

import ec.edu.ec.poo.vista.Carrito.*;

import ec.edu.ec.poo.vista.Producto.*;

import ec.edu.ec.poo.vista.Usuario.*;



import javax.swing.*;

import java.io.File;



/**
 * **Clase `Main`**
 * <p>
 * Clase principal de la aplicación. Es el punto de entrada que se encarga de la inicialización,
 * la configuración del tipo de almacenamiento de datos (memoria, texto o binario) y la orquestación
 * de las diferentes vistas y controladores del sistema.
 * </p>
 */
public class Main {


    /**
     * **Atributo `mensaje`**
     * <p>
     * Instancia del manejador para obtener mensajes de la interfaz en diferentes idiomas.
     * Esto permite que la aplicación sea internacionalizable.
     * </p>
     */
    private static MensajeInternacionalizacionHandler mensaje;

    /**
     * **Atributo `usuarioDAO`**
     * <p>
     * Objeto de acceso a datos para la gestión de usuarios.
     * Su implementación (en memoria, en archivo de texto o en archivo binario) se define en tiempo de ejecución.
     * </p>
     */
    private static UsuarioDAO usuarioDAO;

    /**
     * **Atributo `productoDAO`**
     * <p>
     * Objeto de acceso a datos para la gestión de productos.
     * Permite realizar operaciones CRUD (Crear, Leer, Actualizar, Borrar) sobre los productos.
     * </p>
     */
    private static ProductoDAO productoDAO;

    /**
     * **Atributo `carritoDAO`**
     * <p>
     * Objeto de acceso a datos para la gestión de carritos de compra.
     * Gestiona la persistencia de los carritos de los usuarios.
     * </p>
     */
    private static CarritoDAO carritoDAO;

    /**
     * **Atributo `preguntaDAO`**
     * <p>
     * Objeto de acceso a datos para las preguntas de seguridad.
     * Utilizado en el proceso de recuperación de contraseñas.
     * </p>
     */
    private static PreguntaSeguridadDAO preguntaDAO;




    // Rutas para el modo de almacenamiento en archivos de texto
    /**
     * **Constante `RUTA_USUARIOS_TEXTO`**
     * <p>
     * Ruta del archivo de texto donde se almacenan los datos de los usuarios.
     * </p>
     */
    private static final String RUTA_USUARIOS_TEXTO = "datos/usuarios.txt";

    /**
     * **Constante `RUTA_PRODUCTOS_TEXTO`**
     * <p>
     * Ruta del archivo de texto donde se almacenan los datos de los productos.
     * </p>
     */
    private static final String RUTA_PRODUCTOS_TEXTO = "datos/productos.txt";

    /**
     * **Constante `RUTA_CARRITOS_TEXTO`**
     * <p>
     * Ruta del archivo de texto donde se almacenan los datos de los carritos.
     * </p>
     */
    private static final String RUTA_CARRITOS_TEXTO = "datos/carritos.txt";

    /**
     * **Constante `RUTA_PREGUNTAS_TEXTO`**
     * <p>
     * Ruta del archivo de texto donde se almacenan las preguntas de seguridad.
     * </p>
     */
    private static final String RUTA_PREGUNTAS_TEXTO = "datos/preguntas.txt";



    // Rutas para el modo de almacenamiento en archivos binarios
    /**
     * **Constante `RUTA_USUARIOS_BIN`**
     * <p>
     * Ruta del archivo binario donde se serializan los objetos de usuario.
     * </p>
     */
    private static final String RUTA_USUARIOS_BIN = "datos/usuarios.bin";

    /**
     * **Constante `RUTA_PRODUCTOS_BIN`**
     * <p>
     * Ruta del archivo binario donde se serializan los objetos de producto.
     * </p>
     */
    private static final String RUTA_PRODUCTOS_BIN = "datos/productos.bin";

    /**
     * **Constante `RUTA_CARRITOS_BIN`**
     * <p>
     * Ruta del archivo binario donde se serializan los objetos de carrito.
     * </p>
     */
    private static final String RUTA_CARRITOS_BIN = "datos/carritos.bin";

    /**
     * **Constante `RUTA_PREGUNTAS_BIN`**
     * <p>
     * Ruta del archivo binario donde se serializan las preguntas de seguridad.
     * </p>
     */private static final String RUTA_PREGUNTAS_BIN = "datos/preguntas.bin";



    /**
     * **Método `main`**
     * <p>
     * Punto de entrada de la aplicación. Llama al método `iniciarAplicacion` para comenzar la ejecución.
     * </p>
     * @param args Argumentos de la línea de comandos (no utilizados).
     */
    public static void main(String[] args) {

        iniciarAplicacion("es", "EC");

    }



    /**
     * **Método `crearCarpetaDatos` (private)**
     * <p>
     * Crea el directorio "datos" si no existe. Este directorio es necesario para la persistencia
     * de datos en modo texto o binario.
     * </p>
     */
    private static void crearCarpetaDatos() {

        File carpeta = new File("datos");

        if (!carpeta.exists()) {

            carpeta.mkdirs();

            System.out.println("📁 Carpeta 'datos/' creada.");

        }

    }



    /**
     * **Método `crearAdminSiNoExiste` (private)**
     * <p>
     * Verifica si ya existe un usuario con el rol de {@link Rol#ADMINISTRADOR} en el sistema.
     * Si no se encuentra, crea un usuario administrador con datos por defecto.
     * </p>
     */
    private static void crearAdminSiNoExiste() {

        if (usuarioDAO.listarPorRol(Rol.ADMINISTRADOR).isEmpty()) {

            try {

                Usuario admin = new Usuario("016538085", "admin", "Adm_12", Rol.ADMINISTRADOR,

                        "01/01/1990", "admin@correo.com", "0999999999", "Quito");

                usuarioDAO.crear(admin);

                System.out.println(" Admin creado en archivo.");

            } catch (Exception e) {

                System.out.println(" Error creando admin: " + e.getMessage());

            }

        }

    }



    /**
     * **Método `iniciarAplicacion`**
     * <p>
     * Orquesta el flujo de inicio de la aplicación. Configura el idioma, muestra la ventana de login
     * para que el usuario elija el tipo de almacenamiento, inicializa los DAOs y controladores
     * correspondientes, y finalmente muestra el menú principal según el rol del usuario autenticado.
     * </p>
     * @param lang Código de idioma (ej. "es").
     * @param country Código de país (ej. "EC").
     */
    public static void iniciarAplicacion(String lang, String country) {

        mensaje = new MensajeInternacionalizacionHandler(lang, country);

        crearCarpetaDatos();



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

        switch (tipo.toUpperCase()) {

            case "MEMORIA" -> {

                usuarioDAO = new UsuarioDAOMemoria();

                carritoDAO = new CarritoDAOMemoria();

                preguntaDAO = new PreguntaSeguridadDAOMemoria();

                productoDAO = new ProductoDAOMemoria();

            }

            case "TEXTO" -> {

                usuarioDAO = new UsuarioDAOArchivoTexto(RUTA_USUARIOS_TEXTO);

                carritoDAO = new CarritoDAOArchivoTexto(RUTA_CARRITOS_TEXTO, usuarioDAO.listarTodos());

                preguntaDAO = new PreguntaSeguridadDAOArchivoTexto(RUTA_PREGUNTAS_TEXTO);

                productoDAO = new ProductoDAOArchivoTexto(RUTA_PRODUCTOS_TEXTO);

                crearAdminSiNoExiste();

            }

            case "BINARIO" -> {

                usuarioDAO = new UsuarioDAOArchivoBinario(RUTA_USUARIOS_BIN);

                carritoDAO = new CarritoDAOArchivoBinario(RUTA_CARRITOS_BIN);

                preguntaDAO = new PreguntaSeguridadDAOArchivoBinario(RUTA_PREGUNTAS_BIN);

                productoDAO = new ProductoDAOArchivoBinario(RUTA_PRODUCTOS_BIN);

                crearAdminSiNoExiste();

            }

        }



        UsuarioController usuarioController = new UsuarioController(

                usuarioDAO, carritoDAO, loginView, preguntaDAO,

                usuarioRegistroView, usuarioEliminarView, usuarioListaView, usuarioModificarView);



        while (usuarioController.getUsuarioAutenticado() == null) {

            try { Thread.sleep(200); } catch (InterruptedException e) { return; }

        }



        Usuario usuarioAutenticado = usuarioController.getUsuarioAutenticado();



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



        if (usuarioAutenticado.getRol() == Rol.ADMINISTRADOR) {

            menuPrincipal.getMenuItemCrear().addActionListener(e -> mostrarVentanaInterna(productoAnadirView, menuPrincipal));

            menuPrincipal.getMenuItemBuscar().addActionListener(e -> mostrarVentanaInterna(productoListaView, menuPrincipal));

            menuPrincipal.getMenuItemEliminar().addActionListener(e -> mostrarVentanaInterna(productoEliminarView, menuPrincipal));

            menuPrincipal.getMenuItemActualizar().addActionListener(e -> mostrarVentanaInterna(productoModificarView, menuPrincipal));



            menuPrincipal.getMenuItemCerrarSesion().addActionListener(e -> {

                menuPrincipal.dispose();

                JOptionPane.showMessageDialog(null, mensaje.get("mensaje.sesion.cerrada"), mensaje.get("titulo.informacion"), JOptionPane.INFORMATION_MESSAGE);



// Mostrar Login limpio

                SwingUtilities.invokeLater(() -> {

                    LoginView nuevoLogin = new LoginView(mensaje);

                    nuevoLogin.setVisible(true);

                });

            });

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



        menuPrincipal.getMenuItemEspaniol().addActionListener(e -> cambiarIdioma("es", "EC", usuarioController, productoController, carritoController, menuPrincipal));

        menuPrincipal.getMenuItemIngles().addActionListener(e -> cambiarIdioma("en", "US", usuarioController, productoController, carritoController, menuPrincipal));

        menuPrincipal.getMenuItemFrances().addActionListener(e -> cambiarIdioma("fr", "FR", usuarioController, productoController, carritoController, menuPrincipal));

    }



    /**
     * **Método `mostrarVentanaInterna` (private)**
     * <p>
     * Muestra una ventana interna ({@link JInternalFrame}) dentro del escritorio principal. Si la ventana
     * ya está abierta, simplemente la trae al frente.
     * </p>
     * @param frame     La ventana interna que se desea mostrar.
     * @param principal La ventana principal que contiene el JDesktopPane.
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



    /**
     * **Método `cambiarIdioma` (private)**
     * <p>
     * Actualiza el idioma de la aplicación. Cambia los textos y el icono de la bandera en la ventana principal
     * y en todas las vistas gestionadas por los controladores.
     * </p>
     * @param lang              El nuevo código de idioma (ej. "es").
     * @param country           El nuevo código de país (ej. "EC").
     * @param usuarioController Controlador de usuarios para actualizar sus vistas.
     * @param productoController Controlador de productos para actualizar sus vistas.
     * @param carritoController Controlador de carritos para actualizar sus vistas.
     * @param principalView     La ventana principal de la aplicación.
     */
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