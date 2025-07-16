package ec.edu.ec.poo.controller;

import ec.edu.ec.poo.dao.CarritoDAO;
import ec.edu.ec.poo.dao.ProductoDAO;
import ec.edu.ec.poo.modelo.*;
import ec.edu.ec.poo.utils.FormateadorUtils;
import ec.edu.ec.poo.vista.Carrito.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Locale;

public class CarritoController {

    private final CarritoDAO carritoDAO;
    private final ProductoDAO productoDAO;
    private final CarritoAnadirView carritoAnadirView;
    private final CarritoBuscarView carritoBuscarView;
    private final CarritoEliminarView carritoEliminarView;
    private final CarritoModificarView carritoActualizarView;
    private final CarritoListaView carritoListaView;
    private Carrito carrito;
    private Usuario usuarioAutenticado;
    private int productoSeleccionado = -1;

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

        // Mostrar código y usuario
        carritoAnadirView.getTxtCodigoCarrito().setText(String.valueOf(carrito.getCodigo()));
        carritoAnadirView.getTxtUsuario().setText(usuarioAutenticado.getId());

        configurarEventosEnVistas();
    }

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

// VER DETALLE DE CARRITO SELECCIONADO
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

    private void anadirProducto() {
        int codigo = Integer.parseInt(carritoAnadirView.getTxtCodigo().getText());
        Producto producto = productoDAO.buscarPorCodigo(codigo);
        int cantidad = Integer.parseInt(carritoAnadirView.getCbxCantidad().getSelectedItem().toString());
        carrito.agregarProducto(producto, cantidad);
        cargarProductos();
        mostrarTotales();
    }

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

    private void mostrarTotales() {
        Locale locale = carritoAnadirView.getMensajeInternacionalizacion().getLocale();
        String subtotal = FormateadorUtils.formatearMoneda(carrito.calcularSubtotal(), locale);
        String iva = String.valueOf(carrito.calcularIVA());
        String total = String.valueOf(carrito.calcularTotal());

        carritoAnadirView.getTxtSubtotal().setText(subtotal);
        carritoAnadirView.getTxtIva().setText(iva);
        carritoAnadirView.getTxtTotal().setText(total);
    }

    private void limpiarDatos() {
        carritoAnadirView.getTxtCodigo().setText("");
        carritoAnadirView.getTxtNombre().setText("");
        carritoAnadirView.getTxtPrecio().setText("$0.00");
        carritoAnadirView.getCbxCantidad().setSelectedIndex(0);
        carritoAnadirView.getTxtSubtotal().setText("$0.00");
        carritoAnadirView.getTxtIva().setText("$0.00");
        carritoAnadirView.getTxtTotal().setText("$0.00");
    }

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

    private void eliminarProductoDesdeAnadir(int fila) {
        int codigo = (int) carritoAnadirView.getTblProductos().getValueAt(fila, 0);
        carrito.eliminarProducto(codigo);
        cargarProductos();
        mostrarTotales();
    }

    // Métodos para la ventana "CarritoBuscarView"
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

    private void limpiarCampos() {
        DefaultTableModel modelo = (DefaultTableModel) carritoBuscarView.getTblProducto().getModel();
        modelo.setRowCount(0);
        carritoBuscarView.getTxtCodigo().setText("");
        carritoBuscarView.getTxtSubtotal().setText("");
        carritoBuscarView.getTxtIva().setText("");
        carritoBuscarView.getTxtTotal().setText("");
    }

    // Métodos para la ventana "CarritoEliminarView"
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

    private void limpiarCamposEliminar() {
        DefaultTableModel modelo = (DefaultTableModel) carritoEliminarView.getTblProducto().getModel();
        modelo.setRowCount(0);
        carritoEliminarView.getTxtCodigo().setText("");
        carritoEliminarView.getTxtSubtotal().setText("");
        carritoEliminarView.getTxtIva().setText("");
        carritoEliminarView.getTxtTotal().setText("");
        carritoEliminarView.getBtnEliminar().setEnabled(false);
    }

    // Métodos para la ventana "CarritoActualizarView"
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

    private void buscarProductoModificar() {
        int codigo = Integer.parseInt(carritoActualizarView.getTxtCodigoP().getText());
        Producto producto = productoDAO.buscarPorCodigo(codigo);

        if (producto == null) {
            carritoActualizarView.mostrarMensaje("Producto no encontrado");
        } else {
            productoSeleccionado = codigo;
        }
    }

    private void agregarProductoAlCarrito() {
        Producto producto = productoDAO.buscarPorCodigo(productoSeleccionado);
        carrito.agregarProducto(producto, 1);
        cargarProductosEnTabla();
        actualizarTotales();
        productoSeleccionado = -1;
        carritoActualizarView.getTxtCodigoP().setText("");
    }

    private void modificarProductoEnCarrito(int fila) {
        int nuevaCantidad = carritoActualizarView.getCantidadEnFila(fila);
        int codigo = carritoActualizarView.getCodigoProductoEnFila(fila);
        carrito.modificarCantidadProducto(codigo, nuevaCantidad);
        actualizarTotales();
    }

    private void eliminarProductoDelCarrito(int fila) {
        int codigo = carritoActualizarView.getCodigoProductoEnFila(fila);
        carrito.eliminarProducto(codigo);
        cargarProductosEnTabla();
        actualizarTotales();
    }

    private void guardarCambios() {
        carritoDAO.actualizar(carrito);
        carritoActualizarView.mostrarMensaje("Carrito actualizado correctamente");
        carritoActualizarView.limpiarTabla();
        carritoActualizarView.getTxtCodigoC().setText("");
        carritoActualizarView.getTxtCodigoP().setText("");
        productoSeleccionado = -1;
    }

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

    private void actualizarTotales() {
        double subtotal = carrito.calcularSubtotal();
        double iva = carrito.calcularIVA();
        double total = carrito.calcularTotal();

        carritoActualizarView.actualizarTotales(subtotal, iva, total);
    }

    // Métodos para la ventana "CarritoListaView"
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

    private void mostrarVentanaDetalle(Carrito carrito) {
        CarritoDetalleView detalleView = new CarritoDetalleView(carritoListaView.getMensaje());
        configurarCamposDetalle(detalleView, carrito);
        configurarTablaProductos(detalleView, carrito);
        configurarTotalesDetalle(detalleView, carrito);

        detalleView.setLocationRelativeTo(carritoListaView);
        detalleView.setVisible(true);
    }

    private void configurarCamposDetalle(CarritoDetalleView detalleView, Carrito carrito) {
        Locale locale = detalleView.getMensaje().getLocale();

        detalleView.getTxtCodigo().setText(String.valueOf(carrito.getCodigo()));
        detalleView.getTxtUsuario().setText(carrito.getUsuario().getId());
        detalleView.getTxtFecha().setText(
                FormateadorUtils.formatearFecha(carrito.getFechaCreacion().getTime(), locale)
        );
    }

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

    public void cambiarIdiomaVistas(String lang, String country) {
        carritoAnadirView.cambiarIdioma(lang, country);
        carritoBuscarView.cambiarIdioma(lang, country);
        carritoEliminarView.cambiarIdioma(lang, country);
        carritoActualizarView.cambiarIdioma(lang, country);
        carritoListaView.cambiarIdioma(lang, country);
    }


}
