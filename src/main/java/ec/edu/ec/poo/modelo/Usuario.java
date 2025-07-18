package ec.edu.ec.poo.modelo;


import java.util.List;

/**
 * Clase Usuario que representa a un usuario del sistema.
 * Incluye información personal, credenciales de acceso, rol,
 * y una lista de respuestas de seguridad asociadas.
 */
public class Usuario {

    /** Identificador único del usuario (ID o username) */
    private String id;


    /** Nombre completo del usuario */
    private String nombre;

    /** Contraseña de acceso del usuario */
    private String contrasenia;


    /** Rol que determina los permisos del usuario */
    private Rol rol;

    /** Fecha de nacimiento del usuario */
    private String fechaNacimiento;


    /** Correo electrónico del usuario */
    private String email;


    /** Número telefónico del usuario */
    private String telefono;


    /** Dirección domiciliaria del usuario */
    private String direccion;


    /** Lista de respuestas de seguridad asociadas al usuario */
    private List<RespuestaSeguridad> respuestasSeguridad;

    /**
     * Constructor vacío para inicializar la clase sin parámetros.
     */
    public Usuario() {}


    /**
     * Constructor utilizado para autenticación rápida con ID y contraseña.
     * @param id identificador único del usuario
     * @param contrasenia contraseña de acceso del usuario
     */
    public Usuario(String id, String contrasenia) {
        this.id = id;
        this.contrasenia = contrasenia;
    }


    /**
     * Constructor principal con todos los datos personales y credenciales.
     * @param id identificador único del usuario
     * @param nombre nombre completo del usuario
     * @param contrasenia contraseña del usuario
     * @param rol rol del usuario (ADMINISTRADOR o USUARIO)
     * @param fechaNacimiento fecha de nacimiento
     * @param email correo electrónico
     * @param telefono número telefónico
     * @param direccion dirección domiciliaria
     */
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


    /**
     * Constructor reducido con ID, nombre, contraseña y rol.
     * @param id identificador único del usuario
     * @param nombre nombre completo del usuario
     * @param contrasenia contraseña del usuario
     * @param rol rol del usuario
     */
    public Usuario(String id, String nombre, String contrasenia, Rol rol) {
        this.id = id;
        this.nombre = nombre;
        this.contrasenia = contrasenia;
        this.rol = rol;
    }


    /** @return identificador único del usuario */
    public String getId() { return id; }


    /** @param id establece un nuevo identificador para el usuario */
    public void setId(String id) { this.id = id; }

    /** @return nombre completo del usuario */
    public String getNombre() { return nombre; }


    /** @param nombre asigna el nombre completo del usuario */
    public void setNombre(String nombre) { this.nombre = nombre; }

    /** @return contraseña de acceso del usuario */
    public String getContrasenia() { return contrasenia; }


    /** @param contrasenia asigna una nueva contraseña al usuario */
    public void setContrasenia(String contrasenia) { this.contrasenia = contrasenia; }

    /** @return rol del usuario */
    public Rol getRol() { return rol; }


    /** @param rol asigna un nuevo rol al usuario */
    public void setRol(Rol rol) { this.rol = rol; }

    /** @return fecha de nacimiento del usuario */
    public String getFechaNacimiento() { return fechaNacimiento; }


    /** @param fechaNacimiento asigna una nueva fecha de nacimiento */
    public void setFechaNacimiento(String fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    /** @return correo electrónico del usuario */
    public String getEmail() { return email; }


    /** @param email asigna un nuevo correo electrónico */
    public void setEmail(String email) { this.email = email; }

    /** @return teléfono del usuario */
    public String getTelefono() { return telefono; }


    /** @param telefono asigna un nuevo número telefónico */
    public void setTelefono(String telefono) { this.telefono = telefono; }

    /** @return dirección domiciliaria del usuario */
    public String getDireccion() { return direccion; }


    /** @param direccion asigna una nueva dirección domiciliaria */
    public void setDireccion(String direccion) { this.direccion = direccion; }

    /** @return lista de respuestas de seguridad del usuario */
    public List<RespuestaSeguridad> getRespuestasSeguridad() {
        return respuestasSeguridad;
    }


    /** @param respuestasSeguridad asigna las respuestas de seguridad del usuario */
    public void setRespuestasSeguridad(List<RespuestaSeguridad> respuestasSeguridad) {
        this.respuestasSeguridad = respuestasSeguridad;
    }


    /**
     * Retorna la información completa del usuario como texto.
     * @return cadena con los detalles principales del usuario
     */
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
