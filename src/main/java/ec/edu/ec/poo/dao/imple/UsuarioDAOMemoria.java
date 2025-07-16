package ec.edu.ec.poo.dao.imple;


import ec.edu.ec.poo.dao.UsuarioDAO;
import ec.edu.ec.poo.modelo.Rol;
import ec.edu.ec.poo.modelo.Usuario;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UsuarioDAOMemoria implements UsuarioDAO {

    private List<Usuario> usuarios;

    public UsuarioDAOMemoria() {
        usuarios = new ArrayList<>();

        // 👇 Usuarios de prueba con constructor extendido
        crear(new Usuario("12345", "Administrador General", "12345", Rol.ADMINISTRADOR,
                "01/01/1990", "admin@correo.com", "0987654321", "Quito"));

        crear(new Usuario("telmo99", "123", "clave999", Rol.USUARIO,
                "10/03/1999", "telmo@correo.com", "099112233", "Cuenca"));
    }

    @Override
    public Usuario autenticar(String id, String contrasenia) {
        for (Usuario usuario : usuarios) {
            if (usuario.getId().equals(id) && usuario.getContrasenia().equals(contrasenia)) {
                return usuario;
            }
        }
        return null;
    }

    @Override
    public void crear(Usuario usuario) {
        usuarios.add(usuario);
        // 👇 Solo para verificar en consola (puedes eliminar esta línea si quieres)
        System.out.println("Usuario creado: " + usuario);
    }

    @Override
    public Usuario buscarPorUsername(String username) {
        for (Usuario usuario : usuarios) {
            if (usuario.getId().equals(username)) {
                return usuario;
            }
        }
        return null;
    }
    @Override
    public Usuario buscarPorId(String id) {
        for (Usuario u : usuarios) {
            if (u.getId().equals(id)) {
                return u;
            }
        }
        return null;
    }



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

    @Override
    public boolean actualizar(Usuario usuario) {
        for (int i = 0; i < usuarios.size(); i++) {
            Usuario usuarioAux = usuarios.get(i);
            if (usuarioAux.getId().equals(usuario.getId())) {
                usuarios.set(i, usuario);
                return true; // ✅ Sí lo encontró y actualizó
            }
        }
        return false; // ❌ No se encontró el usuario con ese ID
    }
    @Override
    public Usuario buscarPorEmail(String email) {
        for (Usuario usuario : usuarios) {
            if (usuario.getEmail().equalsIgnoreCase(email)) {
                return usuario;
            }
        }
        return null;
    }

    @Override
    public Usuario buscarPorTelefono(String telefono) {
        for (Usuario usuario : usuarios) {
            if (usuario.getTelefono().equals(telefono)) {
                return usuario;
            }
        }
        return null;
    }


    @Override
    public List<Usuario> listarTodos() {
        return usuarios;
    }

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
