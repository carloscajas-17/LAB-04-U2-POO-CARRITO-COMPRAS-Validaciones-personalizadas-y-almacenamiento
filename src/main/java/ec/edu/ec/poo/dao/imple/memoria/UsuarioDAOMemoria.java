package ec.edu.ec.poo.dao.imple.memoria;


import ec.edu.ec.poo.dao.UsuarioDAO;
import ec.edu.ec.poo.modelo.Rol;
import ec.edu.ec.poo.modelo.Usuario;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Clase que implementa {@link UsuarioDAO} para gestionar usuarios en memoria.
 */
public class UsuarioDAOMemoria implements UsuarioDAO {

    /**
     * Lista interna que almacena los usuarios.
     */
    private List<Usuario> usuarios;

    /**
     * Constructor que inicializa la lista de usuarios con datos de prueba.
     */
    public UsuarioDAOMemoria() {
        usuarios = new ArrayList<>();

        try {
            crear(new Usuario("1234567890", "Administrador General", "Admin@123", Rol.ADMINISTRADOR,
                    "01/01/1990", "admin@correo.com", "0987654321", "Quito"));

            crear(new Usuario("0923456789", "Telmo Cajas", "Telmo@123", Rol.USUARIO,
                    "10/03/1999", "telmo@correo.com", "099112233", "Cuenca"));

        } catch (Exception e) {
            System.out.println("Error creando usuario de prueba: " + e.getMessage());
        }
    }

    /**
     * Autentica un usuario verificando si existe un usario registrado con el ID  y contraseña proporcionados.
     * @param id ID o nombre del usuario registrado.
     * @param contrasenia Contraseña ingresada.
     * @return el objeto {@link Usuario} si las credenciales son correctas,o {@code null} si no encuntra coincidencia.
     */
    @Override
    public Usuario autenticar(String id, String contrasenia) {
        for (Usuario usuario : usuarios) {
            if (usuario.getId().equals(id) && usuario.getContrasenia().equals(contrasenia)) {
                return usuario;
            }
        }
        return null;
    }

    /**
     * Agrega un nuevo usuario a la lista de usuario en memoria.
     *
     * @param usuario el obejto {@link Usuario} que se desea registrar.
     */
    @Override
    public void crear(Usuario usuario) {
        usuarios.add(usuario);
        //  Solo para verificar en consola
        System.out.println("Usuario creado: " + usuario);
    }

    /**
     * Busca un usuario en la lista de usuarios utilizando su nombre de usuario(ID).
     * @param username el ID  o nombre  de usuario que se desea buscar.
     * @return el objeto {@link Usuario} correpondiente si se encuentra;
     *                   {@code null} si no existe ningún usuario con ese ID.
     */
    @Override
    public Usuario buscarPorUsername(String username) {
        for (Usuario usuario : usuarios) {
            if (usuario.getId().equals(username)) {
                return usuario;
            }
        }
        return null;
    }

    /**
     * Bysca un usuario en la lista de usuarios utilizando el identificador único (ID).
     * @param id el identificador único del usuario a buscar.
     * @return el objeto {@link Usuario} si se encuntra un usuario con el ID proporcionado;
     *              {@code null} si no se encuntra ningún usuario con dicho ID
     */
    @Override
    public Usuario buscarPorId(String id) {
        for (Usuario u : usuarios) {
            if (u.getId().equals(id)) {
                return u;
            }
        }
        return null;
    }


    /**
     * Elimina un usuraio del sistema según su identificador de usuario(username).
     * Recorre la lista de usuarios y elimina el primeor que coincida con el ID proporcionado.
     * Si no encuntra el usuario, no realiza ninguna acción.
     * @param username  el identificador único del usuario que se desea eliminar.
     */
    @Override
    public void eliminar(String username) {
        Iterator<Usuario> iterator = usuarios.iterator();
        while (iterator.hasNext()) {
            Usuario usuario = iterator.next();
            if (usuario.getId().equals(username)) {
                iterator.remove();
                break;
            }
        }
    }

    /**
     * Actualiza los daots de un usurio en la lista de usuarios.
     *
     * Busca un usuario en la lista por su identificador único (ID).
     * Si encuntra un usuario con el mismo ID, reemplaza sus datos por los del usuario Proporcionado
     * Retorna un vlaor booleano indicando si la operación fue exitosa.
     *
     * @param usuario el objeto {@link Usuario } con los nuevos datos a actualizar.
     * @return {@code true} si el usuario fue encontrado y actualizado correctamente, {@code false} si no encontró.
     */
    @Override
    public boolean actualizar(Usuario usuario) {
        for (int i = 0; i < usuarios.size(); i++) {
            Usuario usuarioAux = usuarios.get(i);
            if (usuarioAux.getId().equals(usuario.getId())) {
                usuarios.set(i, usuario);
                return true; // Sí lo encontró y actualizó
            }
        }
        return false; // No se encontró el usuario con ese ID
    }

    /**
     * Busca un suario en la lisya por su direccion de correo electrónico.
     *
     * Compara el email proporcionado con los usuarios registrados,
     * ignorando mayúsculas y minúsculas-
     *
     * @param email dirección de correo electrónico a buscar.
     * @return el {@link Usuario} correspondiente si se encuentra, o {@code null} si no existe.
     */
    @Override
    public Usuario buscarPorEmail(String email) {
        for (Usuario usuario : usuarios) {
            if (usuario.getEmail().equalsIgnoreCase(email)) {
                return usuario;
            }
        }
        return null;
    }

    /**
     * Buscar un usuario en la lista en memoria utilizando su númeri de teléfono.
     *
     * Recorre la lista de usuario y compara el número teléfonico exacto para encontrar coincidencias.
     * @param telefono número de teléfono asociado al usaurio que se desea buscar.
     * @return el {@link Usuario} correspondiente si se encuentra, o { @code null} si no existe un usuario con ese teléfono.
     */
    @Override
    public Usuario buscarPorTelefono(String telefono) {
        for (Usuario usuario : usuarios) {
            if (usuario.getTelefono().equals(telefono)) {
                return usuario;
            }
        }
        return null;
    }


    /**
     * Lista todos los usuarios almacenados en memoria
     * @return una lista {@link List} que contiene todos los usuarios registrados en el sistema.
     */
    @Override
    public List<Usuario> listarTodos() {
        return usuarios;
    }

    /**
     * Lista todos los usuarios que tiene un rol específico.
     *
     * Recorre la lista de usuarios almacenados en memoria y filtra aquellos
     * que coincidan con el rol especificado
     *
     * @param rol el {@link Rol} que se desea filtrar ya sea ( ADMIN o USUARIO).
     * @return una lista {@link List} de usuarios cuyo rol coincide con el parámetro recibido.
     */
    @Override
    public List<Usuario> listarPorRol(Rol rol) {
        List<Usuario> usuariosEncontrados = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            if (usuario.getRol().equals(rol)) {
                usuariosEncontrados.add(usuario);
            }
        }
        return usuariosEncontrados;
    }
}
