package ec.edu.ec.poo.dao;
import ec.edu.ec.poo.modelo.Rol;
import ec.edu.ec.poo.modelo.Usuario;
import java.util.List;

/**
 * Interfaz DAO para la gestión de usuarios.
 * Define las operaciones CRUD, autenticación y consultas adicionales relacionadas con la entidad {@link Usuario}.
 *
 */
public interface UsuarioDAO {

    /**
     * Autentica un usuario validando su nombre de usuario y contraseña.
     *
     * @param username identificador único del usuario
     * @param contrasenia contraseña del usuario
     * @return el usuario autenticado o null si las credenciales son incorrectas
     */
    Usuario autenticar(String username, String contrasenia);

    /**
     * Crea un nuevo usuario en la base de datos o lista en memoria.
     *
     * @param usuario el objeto {@link Usuario} a registrar
     */
    void crear(Usuario usuario);

    /**
     * Busca un usuario mediante su nombre de usuario.
     *
     * @param username nombre de usuario a buscar
     * @return el usuario encontrado o null si no existe
     */
    Usuario buscarPorUsername(String username);

    /**
     * Elimina un usuario del sistema mediante su nombre de usuario.
     *
     * @param username nombre de usuario a eliminar
     */
    void eliminar(String username);

    /**
     * Actualiza los datos de un usuario existente.
     *
     * @param usuario usuario con los nuevos datos
     * @return true si la actualización fue exitosa, false si el usuario no fue encontrado
     */
    boolean actualizar(Usuario usuario);

    /**
     * Lista todos los usuarios registrados en el sistema.
     *
     * @return lista completa de usuarios
     */
    List<Usuario> listarTodos();

    /**
     * Lista los usuarios filtrados por su rol.
     *
     * @param rol rol de los usuarios a buscar
     * @return lista de usuarios con el rol especificado
     */
    List<Usuario> listarPorRol(Rol rol);

    /**
     * Busca un usuario por su dirección de correo electrónico.
     *
     * @param email email del usuario a buscar
     * @return el usuario encontrado o null si no existe
     */
    Usuario buscarPorEmail(String email);

    /**
     * Busca un usuario por su número de teléfono.
     *
     * @param telefono número telefónico a buscar
     * @return el usuario encontrado o null si no existe
     */
    Usuario buscarPorTelefono(String telefono);

    /**
     * Busca un usuario por su ID único.
     *
     * @param id ID único del usuario
     * @return el usuario encontrado o null si no existe
     */
    Usuario buscarPorId(String id);

}