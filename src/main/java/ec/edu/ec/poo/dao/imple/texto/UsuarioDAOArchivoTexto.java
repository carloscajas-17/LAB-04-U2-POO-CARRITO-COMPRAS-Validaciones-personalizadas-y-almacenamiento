package ec.edu.ec.poo.dao.imple.texto;

import ec.edu.ec.poo.dao.UsuarioDAO;
import ec.edu.ec.poo.modelo.PreguntaSeguridad;
import ec.edu.ec.poo.modelo.RespuestaSeguridad;
import ec.edu.ec.poo.modelo.Rol;
import ec.edu.ec.poo.modelo.Usuario;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación de la interfaz {@link UsuarioDAO} utilizando archivos de texto plano
 * para almacenar la información de los usuarios de forma persistente.
 * <p>
 * Cada usuario se guarda en una línea del archivo con el siguiente formato:
 * id|nombre|telefono|email|contrasenia|rol|respuestas_seguridad
 * </p>
 */
public class UsuarioDAOArchivoTexto implements UsuarioDAO {

    /**
     * Lista de usuarios cargados desde el archivo.
     */
    private final List<Usuario> usuarios;

    /**
     * Ruta del archivo donde se almacenan los usuarios.
     */
    private final String rutaArchivo;

    /**
     * Constructor que inicializa el DAO con la ruta del archivo y carga los usuarios existentes.
     *
     * @param rutaArchivo Ruta donde se almacena el archivo de texto.
     */
    public UsuarioDAOArchivoTexto(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
        this.usuarios = new ArrayList<>();
        cargarDesdeArchivo();
    }

    /**
     * Método privado para cargar la información de usuarios desde el archivo de texto.
     */
    private void cargarDesdeArchivo() {
        usuarios.clear();
        File archivo = new File(rutaArchivo);
        if (!archivo.exists()) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                Usuario usuario = convertirLineaAUsuario(linea);
                if (usuario != null) usuarios.add(usuario);
            }
        } catch (IOException e) {
            System.out.println("Error al cargar usuarios: " + e.getMessage());
        }
    }

    /**
     * Convierte una línea del archivo a un objeto {@link Usuario}.
     *
     * @param linea Línea del archivo.
     * @return Usuario correspondiente a la línea o null si ocurre un error.
     */
    private Usuario convertirLineaAUsuario(String linea) {
        try {
            String[] partes = linea.split("\\|");
            if (partes.length < 7) return null;
            String id = partes[0];
            String nombre = partes[1];
            String telefono = partes[2];
            String email = partes[3];
            String contrasenia = partes[4];
            Rol rol = Rol.valueOf(partes[5]);
            String respuestasStr = partes[6];

            Usuario usuario = new Usuario();
            usuario.setId(id);
            usuario.setNombre(nombre);
            usuario.setTelefono(telefono);
            usuario.setEmail(email);
            usuario.setContrasenia(contrasenia);
            usuario.setRol(rol);

            List<RespuestaSeguridad> respuestas = new ArrayList<>();
            if (!respuestasStr.isBlank()) {
                String[] respuestasSeparadas = respuestasStr.split(";");
                for (String r : respuestasSeparadas) {
                    String[] datos = r.split(",");
                    PreguntaSeguridad pregunta = new PreguntaSeguridad(Integer.parseInt(datos[0]), datos[1]);
                    String respuesta = datos[2];
                    respuestas.add(new RespuestaSeguridad(pregunta, respuesta, usuario));
                }
            }
            usuario.setRespuestasSeguridad(respuestas);
            return usuario;
        } catch (Exception e) {
            System.out.println("Error convirtiendo usuario: " + e.getMessage());
            return null;
        }
    }

    /**
     * Guarda toda la lista de usuarios en el archivo de texto.
     */
    private void guardarEnArchivo() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo))) {
            for (Usuario usuario : usuarios) {
                StringBuilder sb = new StringBuilder();
                sb.append(usuario.getId()).append("|")
                        .append(usuario.getNombre()).append("|")
                        .append(usuario.getTelefono()).append("|")
                        .append(usuario.getEmail()).append("|")
                        .append(usuario.getContrasenia()).append("|")
                        .append(usuario.getRol()).append("|");
                List<RespuestaSeguridad> respuestas = usuario.getRespuestasSeguridad();
                if (respuestas != null) {
                    for (RespuestaSeguridad r : respuestas) {
                        sb.append(r.getPregunta().getId()).append(",")
                                .append(r.getPregunta().getTexto()).append(",")
                                .append(r.getRespuesta()).append(";");
                    }
                }
                writer.write(sb.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar usuarios: " + e.getMessage());
        }
    }

    /**
     * Autentica a un usuario verificando su ID (username) y contraseña.
     *
     * @param username El ID del usuario a autenticar.
     * @param contrasenia La contraseña del usuario.
     * @return El usuario autenticado si las credenciales son correctas, o null si no se encuentra.
     */
    @Override
    public Usuario autenticar(String username, String contrasenia) {
        return usuarios.stream()
                .filter(u -> u.getId().equals(username) && u.getContrasenia().equals(contrasenia))
                .findFirst()
                .orElse(null);
    }

    /**
     * Crea un nuevo usuario y lo agrega a la lista de usuarios, guardando los cambios en el archivo.
     *
     * @param usuario El objeto Usuario que se desea crear.
     */
    @Override
    public void crear(Usuario usuario) {
        usuarios.add(usuario);
        guardarEnArchivo();
    }

    /**
     * Busca un usuario por su ID (username).
     *
     * @param username El ID del usuario a buscar.
     * @return El objeto Usuario si se encuentra, o null si no existe.
     */
    @Override
    public Usuario buscarPorUsername(String username) {
        return usuarios.stream()
                .filter(u -> u.getId().equals(username))
                .findFirst()
                .orElse(null);
    }

    /**
     * Elimina un usuario identificado por su ID (username) de la lista y actualiza el archivo.
     *
     * @param username El ID del usuario que se desea eliminar.
     */
    @Override
    public void eliminar(String username) {
        usuarios.removeIf(u -> u.getId().equals(username));
        guardarEnArchivo();
    }

    /**
     * Actualiza la información de un usuario existente identificado por su ID.
     *
     * @param usuario El objeto Usuario con los datos actualizados.
     * @return true si la actualización fue exitosa, false si no se encontró el usuario.
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
     * Lista todos los usuarios registrados.
     *
     * @return Una lista con todos los usuarios.
     */
    @Override
    public List<Usuario> listarTodos() {
        return new ArrayList<>(usuarios);
    }

    /**
     * Lista los usuarios que poseen un rol específico.
     *
     * @param rol El rol por el cual se filtrarán los usuarios.
     * @return Una lista de usuarios que tienen el rol especificado.
     */
    @Override
    public List<Usuario> listarPorRol(Rol rol) {
        return usuarios.stream()
                .filter(u -> u.getRol().equals(rol))
                .collect(Collectors.toList());
    }

    /**
     * Busca un usuario por su correo electrónico.
     *
     * @param email El correo electrónico del usuario a buscar.
     * @return El objeto Usuario si se encuentra, o null si no existe.
     */
    @Override
    public Usuario buscarPorEmail(String email) {
        return usuarios.stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    /**
     * Busca un usuario por su número de teléfono.
     *
     * @param telefono El número de teléfono del usuario a buscar.
     * @return El objeto Usuario si se encuentra, o null si no existe.
     */
    @Override
    public Usuario buscarPorTelefono(String telefono) {
        return usuarios.stream()
                .filter(u -> u.getTelefono().equals(telefono))
                .findFirst()
                .orElse(null);
    }

    /**
     * Busca un usuario por su ID.
     *
     * @param id El ID del usuario a buscar.
     * @return El objeto Usuario si se encuentra, o null si no existe.
     */
    @Override
    public Usuario buscarPorId(String id) {
        return usuarios.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElse(null);
    }



}
