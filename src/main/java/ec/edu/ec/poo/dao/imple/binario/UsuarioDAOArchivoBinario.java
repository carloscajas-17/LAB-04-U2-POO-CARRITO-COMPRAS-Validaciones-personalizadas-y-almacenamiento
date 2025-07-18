package ec.edu.ec.poo.dao.imple.binario;

import ec.edu.ec.poo.dao.UsuarioDAO;
import ec.edu.ec.poo.modelo.Usuario;
import ec.edu.ec.poo.modelo.Rol;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación completa de {@link UsuarioDAO} usando archivos binarios.
 * <p>
 * Permite operaciones CRUD completas para usuarios, incluyendo autenticación, búsqueda,
 * listado por rol, validaciones de existencia y operaciones estadísticas.
 * <p>
 * La persistencia se maneja mediante la serialización de objetos en archivos binarios.
 */
public class UsuarioDAOArchivoBinario implements UsuarioDAO, Serializable {

    /**
     * Lista de usuarios almacenada en memoria.
     */
    private final List<Usuario> usuarios;

    /**
     * Ruta del archivo binario donde se almacenan los usuarios.
     */
    private final String rutaArchivo;

    /**
     * Constructor que inicializa la lista de usuarios leyendo desde un archivo binario.
     *
     * @param rutaArchivo Ruta del archivo donde se almacenan los usuarios.
     */
    public UsuarioDAOArchivoBinario(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
        this.usuarios = new ArrayList<>();
        cargarDesdeArchivo();
    }

    /**
     * Método privado para cargar los usuarios desde el archivo binario usando deserialización.
     * Si el archivo no existe, la lista queda vacía.
     */
    private void cargarDesdeArchivo() {
        usuarios.clear();
        File archivo = new File(rutaArchivo);
        if (!archivo.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            Object obj = ois.readObject();
            if (obj instanceof List<?>) {
                List<?> lista = (List<?>) obj;
                for (Object u : lista) {
                    if (u instanceof Usuario) {
                        usuarios.add((Usuario) u);
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al cargar usuarios binario: " + e.getMessage());
        }
    }

    /**
     * Método privado para guardar la lista actualizada de usuarios en el archivo binario.
     */
    private void guardarEnArchivo() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(rutaArchivo))) {
            oos.writeObject(usuarios);
        } catch (IOException e) {
            System.out.println("Error al guardar usuarios binario: " + e.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     * Autentica a un usuario comparando ID y contraseña.
     *
     * @param username    ID del usuario.
     * @param contrasenia Contraseña del usuario.
     * @return El usuario autenticado o null si no coincide.
     */
    @Override
    public Usuario autenticar(String username, String contrasenia) {
        return usuarios.stream()
                .filter(u -> u.getId().equals(username) && u.getContrasenia().equals(contrasenia))
                .findFirst()
                .orElse(null);
    }

    /**
     * {@inheritDoc}
     * Crea un nuevo usuario si no existe previamente.
     *
     * @param usuario Objeto Usuario a registrar.
     */
    @Override
    public void crear(Usuario usuario) {
        if (!existeUsuario(usuario.getId())) {
            usuarios.add(usuario);
            guardarEnArchivo();
        }
    }

    /**
     * {@inheritDoc}
     * Busca un usuario por su ID.
     *
     * @param username ID del usuario.
     * @return Usuario encontrado o null si no existe.
     */
    @Override
    public Usuario buscarPorUsername(String username) {
        return usuarios.stream()
                .filter(u -> u.getId().equals(username))
                .findFirst()
                .orElse(null);
    }

    /**
     * {@inheritDoc}
     * Elimina un usuario por su ID.
     *
     * @param username ID del usuario a eliminar.
     */
    @Override
    public void eliminar(String username) {
        usuarios.removeIf(u -> u.getId().equals(username));
        guardarEnArchivo();
    }

    /**
     * {@inheritDoc}
     * Actualiza los datos de un usuario identificado por su ID.
     *
     * @param usuario Usuario actualizado.
     * @return true si se actualizó, false si no se encontró.
     */
    @Override
    public boolean actualizar(Usuario usuario) {
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getId().equals(usuario.getId())) {
                usuarios.set(i, usuario);
                guardarEnArchivo();
                return true;
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     * Lista todos los usuarios registrados.
     *
     * @return Lista completa de usuarios.
     */
    @Override
    public List<Usuario> listarTodos() {
        return new ArrayList<>(usuarios);
    }

    /**
     * {@inheritDoc}
     * Lista los usuarios que pertenecen a un rol específico.
     *
     * @param rol Rol a filtrar.
     * @return Lista de usuarios con el rol especificado.
     */
    @Override
    public List<Usuario> listarPorRol(Rol rol) {
        return usuarios.stream()
                .filter(u -> u.getRol().equals(rol))
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     * Busca un usuario por su correo electrónico.
     *
     * @param email Correo electrónico.
     * @return Usuario encontrado o null si no existe.
     */
    @Override
    public Usuario buscarPorEmail(String email) {
        return usuarios.stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    /**
     * {@inheritDoc}
     * Busca un usuario por su número de teléfono.
     *
     * @param telefono Número de teléfono.
     * @return Usuario encontrado o null si no existe.
     */
    @Override
    public Usuario buscarPorTelefono(String telefono) {
        return usuarios.stream()
                .filter(u -> u.getTelefono().equals(telefono))
                .findFirst()
                .orElse(null);
    }

    /**
     * {@inheritDoc}
     * Busca un usuario por su ID.
     *
     * @param id ID del usuario.
     * @return Usuario encontrado o null si no existe.
     */
    @Override
    public Usuario buscarPorId(String id) {
        return usuarios.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    /**
     * Verifica si un usuario existe en la lista por su ID.
     *
     * @param id ID del usuario.
     * @return true si existe, false si no.
     */
    public boolean existeUsuario(String id) {
        return buscarPorId(id) != null;
    }

    /**
     * Cuenta la cantidad total de usuarios almacenados.
     *
     * @return Cantidad de usuarios.
     */
    public int contarUsuarios() {
        return usuarios.size();
    }

    /**
     * Lista todos los usuarios (alias de listarTodos()).
     *
     * @return Lista de usuarios.
     */
    public List<Usuario> listar() {
        return new ArrayList<>(usuarios);
    }

    /**
     * Calcula el promedio de usuarios que tienen un rol específico respecto al total.
     *
     * @param rol Rol a calcular el promedio.
     * @return Promedio como valor decimal (ejemplo: 0.3).
     */
    public double calcularPromedioUsuariosPorRol(Rol rol) {
        long total = usuarios.stream()
                .filter(u -> u.getRol().equals(rol))
                .count();
        return usuarios.isEmpty() ? 0.0 : (double) total / usuarios.size();
    }
}
