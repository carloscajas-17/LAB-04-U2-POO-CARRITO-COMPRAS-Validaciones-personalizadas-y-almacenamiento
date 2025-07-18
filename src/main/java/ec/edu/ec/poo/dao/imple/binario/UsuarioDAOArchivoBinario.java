package ec.edu.ec.poo.dao.imple.binario;

import ec.edu.ec.poo.dao.UsuarioDAO;
import ec.edu.ec.poo.modelo.Usuario;
import ec.edu.ec.poo.modelo.Rol;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación completa de UsuarioDAO usando archivos binarios.
 * Gestiona almacenamiento de usuarios con preguntas y respuestas mediante serialización.
 */
public class UsuarioDAOArchivoBinario implements UsuarioDAO, Serializable {

    private final List<Usuario> usuarios;
    private final String rutaArchivo;

    public UsuarioDAOArchivoBinario(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
        this.usuarios = new ArrayList<>();
        cargarDesdeArchivo();
    }

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

    private void guardarEnArchivo() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(rutaArchivo))) {
            oos.writeObject(usuarios);
        } catch (IOException e) {
            System.out.println("Error al guardar usuarios binario: " + e.getMessage());
        }
    }

    @Override
    public Usuario autenticar(String username, String contrasenia) {
        return usuarios.stream()
                .filter(u -> u.getId().equals(username) && u.getContrasenia().equals(contrasenia))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void crear(Usuario usuario) {
        if (!existeUsuario(usuario.getId())) {
            usuarios.add(usuario);
            guardarEnArchivo();
        }
    }

    @Override
    public Usuario buscarPorUsername(String username) {
        return usuarios.stream()
                .filter(u -> u.getId().equals(username))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void eliminar(String username) {
        usuarios.removeIf(u -> u.getId().equals(username));
        guardarEnArchivo();
    }

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

    @Override
    public List<Usuario> listarTodos() {
        return new ArrayList<>(usuarios);
    }

    @Override
    public List<Usuario> listarPorRol(Rol rol) {
        return usuarios.stream()
                .filter(u -> u.getRol().equals(rol))
                .collect(Collectors.toList());
    }

    @Override
    public Usuario buscarPorEmail(String email) {
        return usuarios.stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Usuario buscarPorTelefono(String telefono) {
        return usuarios.stream()
                .filter(u -> u.getTelefono().equals(telefono))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Usuario buscarPorId(String id) {
        return usuarios.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public boolean existeUsuario(String id) {
        return buscarPorId(id) != null;
    }

    public int contarUsuarios() {
        return usuarios.size();
    }

    public List<Usuario> listar() {
        return new ArrayList<>(usuarios);
    }

    public double calcularPromedioUsuariosPorRol(Rol rol) {
        long total = usuarios.stream()
                .filter(u -> u.getRol().equals(rol))
                .count();
        return (double) total / usuarios.size();
    }
}
