package ec.edu.ec.poo.modelo;


import java.util.List;

public class Usuario {

    private String id;
    private String nombre;
    private String contrasenia;
    private Rol rol;

    private String fechaNacimiento;
    private String email;
    private String telefono;
    private String direccion;
    private List<RespuestaSeguridad> respuestasSeguridad;

    public Usuario() {}

    // Constructor para login
    public Usuario(String id, String contrasenia) {
        this.id = id;
        this.contrasenia = contrasenia;
    }

    // Constructor extendido con todos los datos
    public Usuario(String id, String nombre, String contrasenia, Rol rol,
                   String fechaNacimiento, String email, String telefono, String direccion) {
        this.id = id;
        this.nombre = nombre;
        this.contrasenia = contrasenia;
        this.rol = rol;
        this.fechaNacimiento = fechaNacimiento;
        this.email = email;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    // Constructor reducido (usado si aún no manejas preguntas)
    public Usuario(String id, String nombre, String contrasenia, Rol rol) {
        this.id = id;
        this.nombre = nombre;
        this.contrasenia = contrasenia;
        this.rol = rol;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getContrasenia() { return contrasenia; }
    public void setContrasenia(String contrasenia) { this.contrasenia = contrasenia; }

    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }

    public String getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(String fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public List<RespuestaSeguridad> getRespuestasSeguridad() {
        return respuestasSeguridad;
    }
    public void setRespuestasSeguridad(List<RespuestaSeguridad> respuestasSeguridad) {
        this.respuestasSeguridad = respuestasSeguridad;
    }
    @Override
    public String toString() {
        return "Usuario{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", contrasenia='" + contrasenia + '\'' +
                ", rol=" + rol +
                ", fechaNacimiento='" + fechaNacimiento + '\'' +
                ", email='" + email + '\'' +
                ", telefono='" + telefono + '\'' +
                ", direccion='" + direccion + '\'' +
                '}';
    }
}
