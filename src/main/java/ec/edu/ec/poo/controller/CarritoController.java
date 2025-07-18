/**
 * Controladores principales del sistema que gestionan la lógica de negocio entre vistas y modelos.
 */
package ec.edu.ec.poo.controller;


import ec.edu.ec.poo.dao.CarritoDAO;
import ec.edu.ec.poo.dao.ProductoDAO;
import ec.edu.ec.poo.modelo.*;
import ec.edu.ec.poo.utils.FormateadorUtils;
import ec.edu.ec.poo.vista.Carrito.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.Locale;

/**
 * Controlador principal encargado de gestionar la lógica relacionada con los carritos de compras,
 * incluyendo operaciones CRUD (crear, buscar, actualizar y eliminar), así como el manejo de vistas gráficas.
 * Implementa la comunicación entre las vistas y los modelos {@link Carrito} y {@link Producto}.
 * Además, permite la gestión dinámica del idioma para todas las vistas relacionadas con carritos.
 *
 * <p>Este controlador sigue el patrón MVC conectando las vistas del paquete {@code vista.Carrito}
 * con los DAOs y modelos del sistema.</p>
 */
public class CarritoController {

    /** DAO encargado de las operaciones CRUD del carrito */
    private final CarritoDAO carritoDAO;

    /** DAO encargado de las operaciones CRUD de productos */
    private final ProductoDAO productoDAO;

    /** Vista para añadir productos al carrito */
    private final CarritoAnadirView carritoAnadirView;

    /** Vista para buscar carritos existentes */
    private final CarritoBuscarView carritoBuscarView;

    /** Vista para eliminar carritos existentes */
    private final CarritoEliminarView carritoEliminarView;

    /** Vista para modificar productos dentro de un carrito */
    private final CarritoModificarView carritoActualizarView;

    /** Vista para listar todos los carritos según usuario o administrador */
    private final CarritoListaView carritoListaView;

    /** Carrito actual en memoria para operaciones */
    private Carrito carrito;

    /** Usuario actualmente autenticado que está realizando las operaciones */
    private Usuario usuarioAutenticado;

    /** Código del producto seleccionado para modificación o eliminación */
    private int productoSeleccionado = -1;


    /**
     * Constructor principal del controlador CarritoController.
     * Inicializa todas las vistas y DAO necesarios para la gestión del carrito.
     * También inicializa el carrito vacío y asigna el usuario autenticado.
     *
     * @param carritoDAO DAO encargado de gestionar los carritos
     * @param productoDAO DAO encargado de gestionar los productos
     * @param carritoAnadirView Vista para añadir carritos
     * @param carritoBuscarView Vista para buscar carritos
     * @param carritoEliminarView Vista para eliminar carritos
     * @param carritoActualizarView Vista para modificar carritos
     * @param carritoListaView Vista para listar carritos
     * @param usuarioAutenticado Usuario autenticado en sesión
     */
    public CarritoController(CarritoDAO carritoDAO,
                             ProductoDAO productoDAO,
                             CarritoAnadirView carritoAnadirView,
                             CarritoBuscarView carritoBuscarView,
                             CarritoEliminarView carritoEliminarView,
                             CarritoModificarView carritoActualizarView,
                             CarritoListaView carritoListaView,
                             Usuario usuarioAutenticado) {
        this.carritoDAO = carritoDAO;
        this.productoDAO = productoDAO;
        this.carritoAnadirView = carritoAnadirView;
        this.carritoBuscarView = carritoBuscarView;
        this.carritoEliminarView = carritoEliminarView;
        this.carritoActualizarView = carritoActualizarView;
        this.carritoListaView = carritoListaView;
        this.usuarioAutenticado = usuarioAutenticado;
        this.carrito = new Carrito(usuarioAutenticado);

        // Mostrar el código y el usuario directamente en la vista de añadir carrito
        carritoAnadirView.getTxtCodigoCarrito().setText(String.valueOf(carrito.getCodigo()));
        carritoAnadirView.getTxtUsuario().setText(usuarioAutenticado.getId());

        // Configurar los eventos de las vistas
        configurarEventosEnVistas();
    }

    /**
     * Configura todos los listeners y eventos para las vistas del carrito.
     * Asocia cada botón de cada vista con su respectivo método controlador.
     */
    private void configurarEventosEnVistas() {
        carritoAnadirView.getBtnAnadir().addActionListener(e -> anadirProducto());
        carritoAnadirView.getBtnGuardar().addActionListener(e -> guardarCarrito());
        carritoAnadirView.getBtnLimpiar().addActionListener(e -> limpiarDatos());

        carritoAnadirView.getButtonEditor().addActionListener(e -> {
            String comando = e.getActionCommand();
            String[] partes = comando.split(":");

            if (partes[0].equals("modify")) {
                int fila = Integer.parseInt(partes[1]);
                modificarProductoDesdeAnadir(fila);
            } else if (partes[0].equals("delete")) {
                int fila = Integer.parseInt(partes[1]);
                int confirm = JOptionPane.showConfirmDialog(
                        carritoAnadirView,
                        "¿Está seguro de eliminar este producto del carrito?",
                        "Confirmar eliminación",
                        JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    eliminarProductoDesdeAnadir(fila);
                }
            }
        });



        carritoBuscarView.getBtnBuscar().addActionListener(e -> buscarCarritoPorCodigo());
        carritoBuscarView.getBtnLimpiar().addActionListener(e -> limpiarCampos());

        carritoEliminarView.getBtnBuscar().addActionListener(e -> buscarCarritoParaEliminar());
        carritoEliminarView.getBtnEliminar().addActionListener(e -> eliminarCarrito());

        carritoActualizarView.getBtnBuscarC().addActionListener(e -> buscarCarritoModificar());
        carritoActualizarView.getBtnBuscarP().addActionListener(e -> buscarProductoModificar());
        carritoActualizarView.getBtnAnadir().addActionListener(e -> agregarProductoAlCarrito());
        carritoActualizarView.getBtnGuardar().addActionListener(e -> guardarCambios());

        // BUSCAR CARRITOS EN LISTA (por usuario o todos)
        carritoListaView.getBtnBuscar().addActionListener(e -> {
            String id = carritoListaView.getTxtUsuario().getText().trim();
            DefaultTableModel modelo = carritoListaView.getModelo();
            modelo.setRowCount(0);

            List<Carrito> carritos;

            if (id.isEmpty()) {
                // Si está vacío, mostrar todos según el rol
                if (usuarioAutenticado.getRol() == Rol.ADMINISTRADOR) {
                    carritos = carritoDAO.listarTodos();
                } else {
                    carritos = carritoDAO.buscarPorUsuario(usuarioAutenticado.getId());
                }
            } else {
                // Buscar solo por ese usuario
                carritos = carritoDAO.buscarPorUsuario(id);
            }

            for (Carrito carrito : carritos) {
                modelo.addRow(new Object[]{
                        carrito.getFechaCreacion().getTime(),
                        carrito.getCodigo(),
                        carrito.getUsuario().getId(),
                        carrito.obtenerItems().size(),
                        FormateadorUtils.formatearMoneda(carrito.calcularTotal(), carritoListaView.getMensaje().getLocale())
                });
            }
        });


        carritoListaView.getBtnDetalle().addActionListener(e -> mostrarDetalleCarrito());


        carritoActualizarView.setModificarListener(e -> {
            int fila = Integer.parseInt(e.getActionCommand());
            modificarProductoEnCarrito(fila);
        });
        carritoActualizarView.setEliminarListener(e -> {
            int fila = Integer.parseInt(e.getActionCommand());
            eliminarProductoDelCarrito(fila);
        });
    }
    /**
     * Guarda el carrito en la base de datos.
     */
    private void guardarCarrito() {
        if (this.carrito.getUsuario() == null) {
            this.carrito.setUsuario(this.usuarioAutenticado);
        }
        carritoDAO.crear(carrito);
        carritoAnadirView.mostrarMensaje("Carrito creado correctamente");
        System.out.println(carritoDAO.listarTodos());

        // Crear un nuevo carrito
        this.carrito = new Carrito(usuarioAutenticado);
        carritoAnadirView.getTxtCodigoCarrito().setText(String.valueOf(carrito.getCodigo()));

        // Limpiar la tabla y totales
        DefaultTableModel modelo = (DefaultTableModel) carritoAnadirView.getTblProductos().getModel();
        modelo.setRowCount(0);
        mostrarTotales();
    }

    /**
     * Agrega un producto al carrito actual desde la vista de añadir carrito.
     * Obtiene el código del producto y la cantidad desde la vista,
     * agrega el producto al carrito y actualiza la tabla con los productos actuales.
     */
    private void anadirProducto() {
        // Obtener el código del producto ingresado por el usuario
        int codigo = Integer.parseInt(carritoAnadirView.getTxtCodigo().getText());

        // Buscar el producto en el DAO usando el código
        Producto producto = productoDAO.buscarPorCodigo(codigo);

        // Obtener la cantidad seleccionada en el combo box
        int cantidad = Integer.parseInt(carritoAnadirView.getCbxCantidad().getSelectedItem().toString());

        // Agregar el producto y cantidad al carrito actual
        carrito.agregarProducto(producto, cantidad);

        // Actualizar la tabla con los productos agregados al carrito
        cargarProductos();

        // Mostrar el total actualizado (subtotal, IVA y total)
        mostrarTotales();
    }

    /**
     * Carga los productos actuales del carrito en la tabla de la vista CarritoAnadirView.
     * Recorre todos los ítems agregados al carrito, formatea precios y totales
     * según el idioma seleccionado, y actualiza la tabla visualmente.
     */
    private void cargarProductos() {
        Locale locale = carritoAnadirView.getMensajeInternacionalizacion().getLocale();
        List<ItemCarrito> items = carrito.obtenerItems();
        DefaultTableModel modelo = (DefaultTableModel) carritoAnadirView.getTblProductos().getModel();
        modelo.setNumRows(0);
        for (ItemCarrito item : items) {
            modelo.addRow(new Object[]{
                    item.getProducto().getCodigo(),
                    item.getProducto().getNombre(),
                    item.getProducto().getPrecio(),
                    item.getCantidad(),
                    FormateadorUtils.formatearMoneda(item.getProducto().getPrecio() * item.getCantidad(), locale),
                    "Modificar | Eliminar"
            });
        }
    }
    /**
     * Muestra los totales del carrito (subtotal, IVA y total) en la vista.
     */
    private void mostrarTotales() {
        Locale locale = carritoAnadirView.getMensajeInternacionalizacion().getLocale();
        String subtotal = FormateadorUtils.formatearMoneda(carrito.calcularSubtotal(), locale);
        String iva = String.valueOf(carrito.calcularIVA());
        String total = String.valueOf(carrito.calcularTotal());

        carritoAnadirView.getTxtSubtotal().setText(subtotal);
        carritoAnadirView.getTxtIva().setText(iva);
        carritoAnadirView.getTxtTotal().setText(total);
    }
    /**
     * Limpia los campos del formulario de añadir productos al carrito.
     */
    private void limpiarDatos() {
        carritoAnadirView.getTxtCodigo().setText("");
        carritoAnadirView.getTxtNombre().setText("");
        carritoAnadirView.getTxtPrecio().setText("$0.00");
        carritoAnadirView.getCbxCantidad().setSelectedIndex(0);
        carritoAnadirView.getTxtSubtotal().setText("$0.00");
        carritoAnadirView.getTxtIva().setText("$0.00");
        carritoAnadirView.getTxtTotal().setText("$0.00");
    }
    /**
     * Modifica la cantidad de un producto desde la vista de añadir carrito.
     * @param fila posición del producto en la tabla
     */
    private void modificarProductoDesdeAnadir(int fila) {
        int codigo = (int) carritoAnadirView.getTblProductos().getValueAt(fila, 0);
        String input = JOptionPane.showInputDialog(carritoAnadirView, "Ingrese nueva cantidad:");
        try {
            int nuevaCantidad = Integer.parseInt(input);
            carrito.modificarCantidadProducto(codigo, nuevaCantidad);
            cargarProductos();
            mostrarTotales();
        } catch (NumberFormatException ex) {
            carritoAnadirView.mostrarMensaje("Cantidad inválida");
        }
    }
    /**
     * Elimina un producto del carrito desde la vista añadir carrito.
     * @param fila posición del producto en la tabla
     */
    private void eliminarProductoDesdeAnadir(int fila) {
        int codigo = (int) carritoAnadirView.getTblProductos().getValueAt(fila, 0);
        carrito.eliminarProducto(codigo);
        cargarProductos();
        mostrarTotales();
    }
    /**
     * Busca un carrito por código desde la vista buscar carrito y muestra sus productos.
     */
    private void buscarCarritoPorCodigo() {
        int codigo = Integer.parseInt(carritoBuscarView.getTxtCodigo().getText());
        carrito = carritoDAO.buscarPorCodigo(codigo);
        if (carrito == null) {
            carritoBuscarView.mostrarMensaje("No se encontró el carrito");
            limpiarCampos();
        } else {
            mostrarProductosEnCarrito(carrito);
            carritoBuscarView.getTxtSubtotal().setText(String.format("$%,.2f", carrito.calcularSubtotal()));
            carritoBuscarView.getTxtIva().setText(String.format("$%,.2f", carrito.calcularIVA()));
            carritoBuscarView.getTxtTotal().setText(String.format("$%,.2f", carrito.calcularTotal()));
        }
    }
    /**
     * Muestra los productos de un carrito en la vista buscar carrito.
     * @param carrito carrito buscado
     */
    private void mostrarProductosEnCarrito(Carrito carrito) {
        DefaultTableModel modelo = (DefaultTableModel) carritoBuscarView.getTblProducto().getModel();
        modelo.setRowCount(0);

        for (ItemCarrito item : carrito.obtenerItems()) {
            Producto producto = item.getProducto();
            double subtotal = producto.getPrecio() * item.getCantidad();
            double iva = subtotal * carrito.calcularIVA();
            double total = subtotal + iva;

            modelo.addRow(new Object[]{
                    producto.getCodigo(),
                    producto.getNombre(),
                    String.format("$%,.2f", producto.getPrecio()),
                    item.getCantidad(),
                    String.format("$%,.2f", subtotal),
                    String.format("$%,.2f", iva),
                    String.format("$%,.2f", total)
            });
        }
    }
    /**
     * Limpia los campos y tabla de la vista buscar carrito.
     */
    private void limpiarCampos() {
        DefaultTableModel modelo = (DefaultTableModel) carritoBuscarView.getTblProducto().getModel();
        modelo.setRowCount(0);
        carritoBuscarView.getTxtCodigo().setText("");
        carritoBuscarView.getTxtSubtotal().setText("");
        carritoBuscarView.getTxtIva().setText("");
        carritoBuscarView.getTxtTotal().setText("");
    }
    /**
     * Busca un carrito para eliminarlo y muestra sus datos.
     */
    private void buscarCarritoParaEliminar() {
        int codigo = Integer.parseInt(carritoEliminarView.getTxtCodigo().getText());
        carrito = carritoDAO.buscarPorCodigo(codigo);
        if (carrito == null) {
            carritoEliminarView.mostrarMensaje("Carrito no encontrado");
            limpiarCamposEliminar();
        } else {
            mostrarDetallesCarrito(carrito);
            carritoEliminarView.getBtnEliminar().setEnabled(true);
        }
    }
    /**
     * Elimina un carrito luego de confirmación.
     */
    private void eliminarCarrito() {
        int codigo = Integer.parseInt(carritoEliminarView.getTxtCodigo().getText());
        carrito = carritoDAO.buscarPorCodigo(codigo);
        if (carrito == null) {
            carritoEliminarView.mostrarMensaje("El carrito ya fue eliminado o no existe");
            return;
        }
        int confirmacion = JOptionPane.showConfirmDialog(carritoEliminarView,
                "¿Está seguro de eliminar el carrito #" + codigo + "?",
                "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            carritoDAO.eliminar(codigo);
            carritoEliminarView.mostrarMensaje("Carrito eliminado correctamente");
            limpiarCamposEliminar();
        }
    }
    /**
     * Muestra los detalles del carrito en la vista eliminar carrito.
     * @param carrito carrito encontrado
     */
    private void mostrarDetallesCarrito(Carrito carrito) {
        DefaultTableModel modelo = (DefaultTableModel) carritoEliminarView.getTblProducto().getModel();
        modelo.setRowCount(0);
        for (ItemCarrito item : carrito.obtenerItems()) {
            modelo.addRow(new Object[]{
                    item.getProducto().getCodigo(),
                    item.getProducto().getNombre(),
                    String.format("$%,.2f", item.getProducto().getPrecio()),
                    item.getCantidad(),
                    String.format("$%,.2f", item.getProducto().getPrecio() * item.getCantidad())
            });
        }
        carritoEliminarView.getTxtSubtotal().setText(String.format("$%,.2f", carrito.calcularSubtotal()));
        carritoEliminarView.getTxtIva().setText(String.format("$%,.2f", carrito.calcularIVA()));
        carritoEliminarView.getTxtTotal().setText(String.format("$%,.2f", carrito.calcularTotal()));
    }
    /**
     * Limpia los campos de la vista eliminar carrito.
     */
    private void limpiarCamposEliminar() {
        DefaultTableModel modelo = (DefaultTableModel) carritoEliminarView.getTblProducto().getModel();
        modelo.setRowCount(0);
        carritoEliminarView.getTxtCodigo().setText("");
        carritoEliminarView.getTxtSubtotal().setText("");
        carritoEliminarView.getTxtIva().setText("");
        carritoEliminarView.getTxtTotal().setText("");
        carritoEliminarView.getBtnEliminar().setEnabled(false);
    }
    /**
     * Busca un carrito para modificarlo desde la vista actualizar carrito.
     */
    private void buscarCarritoModificar() {
        int codigo = Integer.parseInt(carritoActualizarView.getTxtCodigoC().getText());
        carrito = carritoDAO.buscarPorCodigo(codigo);

        if (carrito == null) {
            carritoActualizarView.mostrarMensaje("Carrito no encontrado");
            carritoActualizarView.limpiarTabla();
        } else {
            cargarProductosEnTabla();
            actualizarTotales();
        }
    }
    /**
     * Busca un producto para agregarlo al carrito desde la vista actualizar carrito.
     */

    private void buscarProductoModificar() {
        int codigo = Integer.parseInt(carritoActualizarView.getTxtCodigoP().getText());
        Producto producto = productoDAO.buscarPorCodigo(codigo);

        if (producto == null) {
            carritoActualizarView.mostrarMensaje("Producto no encontrado");
        } else {
            productoSeleccionado = codigo;
        }
    }
    /**
     * Agrega un producto al carrito desde la vista actualizar carrito.
     */
    private void agregarProductoAlCarrito() {
        Producto producto = productoDAO.buscarPorCodigo(productoSeleccionado);
        carrito.agregarProducto(producto, 1);
        cargarProductosEnTabla();
        actualizarTotales();
        productoSeleccionado = -1;
        carritoActualizarView.getTxtCodigoP().setText("");
    }
    /**
     * Modifica la cantidad de un producto en el carrito desde la vista actualizar carrito.
     * @param fila fila seleccionada
     */
    private void modificarProductoEnCarrito(int fila) {
        int nuevaCantidad = carritoActualizarView.getCantidadEnFila(fila);
        int codigo = carritoActualizarView.getCodigoProductoEnFila(fila);
        carrito.modificarCantidadProducto(codigo, nuevaCantidad);
        actualizarTotales();
    }
    /**
     * Elimina un producto del carrito desde la vista actualizar carrito.
     * @param fila fila seleccionada
     */
    private void eliminarProductoDelCarrito(int fila) {
        int codigo = carritoActualizarView.getCodigoProductoEnFila(fila);
        carrito.eliminarProducto(codigo);
        cargarProductosEnTabla();
        actualizarTotales();
    }
    /**
     * Guarda los cambios del carrito luego de ser modificado.
     */
    private void guardarCambios() {
        carritoDAO.actualizar(carrito);
        carritoActualizarView.mostrarMensaje("Carrito actualizado correctamente");
        carritoActualizarView.limpiarTabla();
        carritoActualizarView.getTxtCodigoC().setText("");
        carritoActualizarView.getTxtCodigoP().setText("");
        productoSeleccionado = -1;
    }
    /**
     * Carga los productos del carrito en la vista actualizar carrito.
     */
    private void cargarProductosEnTabla() {
        DefaultTableModel modelo = (DefaultTableModel) carritoActualizarView.getTblProducto().getModel();
        modelo.setRowCount(0);

        for (ItemCarrito item : carrito.obtenerItems()) {
            modelo.addRow(new Object[]{
                    item.getProducto().getCodigo(),
                    item.getProducto().getNombre(),
                    item.getProducto().getPrecio(),
                    item.getCantidad(),
                    item.getProducto().getPrecio() * item.getCantidad(),
                    ""
            });
        }
    }
    /**
     * Actualiza los totales del carrito mostrado en la vista actualizar carrito.
     */
    private void actualizarTotales() {
        double subtotal = carrito.calcularSubtotal();
        double iva = carrito.calcularIVA();
        double total = carrito.calcularTotal();

        carritoActualizarView.actualizarTotales(subtotal, iva, total);
    }

    /**
     * Carga todos los carritos en la vista lista carrito según el rol del usuario autenticado.
     */
    public void cargarCarritos() {
        DefaultTableModel modelo = carritoListaView.getModelo();
        modelo.setRowCount(0);

        List<Carrito> carritos;
        if (usuarioAutenticado.getRol() == Rol.ADMINISTRADOR) {
            carritos = carritoDAO.listarTodos();
        } else {
            carritos = carritoDAO.buscarPorUsuario(usuarioAutenticado.getId());
        }

        for (Carrito carrito : carritos) {
            modelo.addRow(new Object[]{
                    carrito.getFechaCreacion().getTime(),
                    carrito.getCodigo(),
                    carrito.getUsuario().getId(),
                    carrito.obtenerItems().size(),
                    FormateadorUtils.formatearMoneda(carrito.calcularTotal(), carritoListaView.getMensaje().getLocale())
            });
        }
    }

    /**
     * Muestra el detalle del carrito seleccionado desde la lista.
     */
    private void mostrarDetalleCarrito() {
        int filaSeleccionada = carritoListaView.getTblCarrito().getSelectedRow();

        if (filaSeleccionada == -1) {
            carritoListaView.mostrarMensaje("seleccionar.fila");
            return;
        }

        int codigo = (int) carritoListaView.getModelo().getValueAt(filaSeleccionada, 1);
        Carrito carrito = carritoDAO.buscarPorCodigo(codigo);

        if (carrito == null) {
            carritoListaView.mostrarMensaje("carrito.no.encontrado");
            return;
        }

        mostrarVentanaDetalle(carrito);
    }
    /**
     * Muestra una ventana modal con los detalles del carrito.
     * @param carrito carrito seleccionado
     */

    private void mostrarVentanaDetalle(Carrito carrito) {
        CarritoDetalleView detalleView = new CarritoDetalleView(carritoListaView.getMensaje());
        configurarCamposDetalle(detalleView, carrito);
        configurarTablaProductos(detalleView, carrito);
        configurarTotalesDetalle(detalleView, carrito);

        detalleView.setLocationRelativeTo(carritoListaView);
        detalleView.setVisible(true);
    }

    /**
     * Configura los campos generales (código, usuario, fecha) en la vista detalle carrito.
     * @param detalleView vista de detalle
     * @param carrito carrito mostrado
     */

    private void configurarCamposDetalle(CarritoDetalleView detalleView, Carrito carrito) {
        Locale locale = detalleView.getMensaje().getLocale();

        detalleView.getTxtCodigo().setText(String.valueOf(carrito.getCodigo()));
        detalleView.getTxtUsuario().setText(carrito.getUsuario().getId());
        detalleView.getTxtFecha().setText(
                FormateadorUtils.formatearFecha(carrito.getFechaCreacion().getTime(), locale)
        );
    }
    /**
     * Configura la tabla de productos en la vista detalle carrito.
     * @param detalleView vista de detalle
     * @param carrito carrito mostrado
     */

    private void configurarTablaProductos(CarritoDetalleView detalleView, Carrito carrito) {
        DefaultTableModel modelo = detalleView.getModelo();
        modelo.setRowCount(0);

        Locale locale = detalleView.getMensaje().getLocale();

        for (ItemCarrito item : carrito.obtenerItems()) {
            Producto producto = item.getProducto();
            double subtotal = producto.getPrecio() * item.getCantidad();
            double iva = subtotal * carrito.getIVA();
            double total = subtotal + iva;

            modelo.addRow(new Object[]{
                    producto.getCodigo(),
                    producto.getNombre(),
                    FormateadorUtils.formatearMoneda(producto.getPrecio(), locale),
                    item.getCantidad(),
                    FormateadorUtils.formatearMoneda(subtotal, locale),
                    FormateadorUtils.formatearMoneda(iva, locale),
                    FormateadorUtils.formatearMoneda(total, locale)
            });
        }
    }
    /**
     * Configura los totales de carrito en la vista detalle carrito.
     * @param detalleView vista de detalle
     * @param carrito carrito mostrado
     */

    private void configurarTotalesDetalle(CarritoDetalleView detalleView, Carrito carrito) {
        Locale locale = detalleView.getMensaje().getLocale();

        detalleView.getTxtSubtotal().setText(
                FormateadorUtils.formatearMoneda(carrito.calcularSubtotal(), locale)
        );
        detalleView.getTxtIva().setText(
                FormateadorUtils.formatearMoneda(carrito.calcularIVA(), locale)
        );
        detalleView.getTxtTotal().setText(
                FormateadorUtils.formatearMoneda(carrito.calcularTotal(), locale)
        );
    }
    /**
     * Cambia el idioma de todas las vistas relacionadas al carrito.
     * @param lang código de idioma
     * @param country código de país
     */
    public void cambiarIdiomaVistas(String lang, String country) {
        carritoAnadirView.cambiarIdioma(lang, country);
        carritoBuscarView.cambiarIdioma(lang, country);
        carritoEliminarView.cambiarIdioma(lang, country);
        carritoActualizarView.cambiarIdioma(lang, country);
        carritoListaView.cambiarIdioma(lang, country);
    }


}