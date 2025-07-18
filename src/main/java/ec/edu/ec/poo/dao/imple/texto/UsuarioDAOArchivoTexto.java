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

public class UsuarioDAOArchivoTexto implements UsuarioDAO {

    private final List<Usuario> usuarios;
    private final String rutaArchivo;

    public UsuarioDAOArchivoTexto(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
        this.usuarios = new ArrayList<>();
        cargarDesdeArchivo();
    }

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

    @Override
    public Usuario autenticar(String username, String contrasenia) {
        return usuarios.stream()
                .filter(u -> u.getId().equals(username) && u.getContrasenia().equals(contrasenia))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void crear(Usuario usuario) {
        usuarios.add(usuario);
        guardarEnArchivo();
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
}
