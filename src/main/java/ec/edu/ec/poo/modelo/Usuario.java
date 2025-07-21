package ec.edu.ec.poo.modelo;

import ec.edu.ec.poo.excepciones.*;
import java.util.List;

/**
 * Clase que representa a un usuario dentro del sistema.
 * Incluye atributos básicos de identificación, contacto y autenticación,
 * además de lógica de validación de datos como cédula, contraseña, correo electrónico y fecha.
 */
public class Usuario {

    /** Identificador único del usuario, validado con algoritmo de cédula ecuatoriana */
    private String id;

    /** Nombre completo del usuario */
    private String nombre;

    /** Contraseña segura del usuario */
    private String contrasenia;

    /** Rol asignado al usuario (ejemplo: ADMIN, CLIENTE) */
    private Rol rol;

    /** Fecha de nacimiento del usuario en formato dd/MM/yyyy */
    private String fechaNacimiento;

    /** Correo electrónico del usuario */
    private String email;

    /** Teléfono de contacto del usuario */
    private String telefono;

    /** Dirección domiciliaria del usuario */
    private String direccion;

    /** Lista de respuestas de seguridad asociadas al usuario */
    private List<RespuestaSeguridad> respuestasSeguridad;

    /**
     * Constructor vacío para inicialización simple del objeto Usuario.
     */
    public Usuario() {}

    /**
     * Constructor utilizado para login rápido.
     * @param id Identificador único del usuario
     * @param contrasenia Contraseña del usuario
     */
    public Usuario(String id, String contrasenia) {
        this.id = id;
        this.contrasenia = contrasenia;
    }

    /**
     * Constructor completo con validaciones personalizadas para los campos obligatorios.
     * @param id Cédula del usuario (valida formato ecuatoriano)
     * @param nombre Nombre del usuario
     * @param contrasenia Contraseña con políticas de seguridad
     * @param rol Rol del usuario
     * @param fechaNacimiento Fecha de nacimiento en formato dd/MM/yyyy
     * @param email Correo electrónico válido
     * @param telefono Número telefónico
     * @param direccion Dirección del usuario
     * @throws CamposExcepcion si algún campo obligatorio está vacío
     * @throws CedulaExcepcion si la cédula es inválida
     * @throws ContraseniaExcepcion si la contraseña no cumple con las reglas de seguridad
     * @throws CorreoElecExcepcion si el correo es inválido
     * @throws FechaExcepcion si la fecha no tiene formato válido
     */
    public Usuario(String id, String nombre, String contrasenia, Rol rol,
                   String fechaNacimiento, String email, String telefono, String direccion)
            throws CamposExcepcion, CedulaExcepcion, ContraseniaExcepcion, CorreoElecExcepcion, FechaExcepcion {
        setId(id);
        setNombre(nombre);
        setContrasenia(contrasenia);
        setEmail(email);
        setFechaNacimiento(fechaNacimiento);
        this.rol = rol;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    // ======= GETTERS =======

    /**
     * Obtiene el identificador único del usuario (cédula).
     * @return ID del usuario
     */
    public String getId() { return id; }

    /**
     * Obtiene el nombre completo del usuario.
     * @return Nombre del usuario
     */
    public String getNombre() { return nombre; }

    /**
     * Obtiene la contraseña del usuario.
     * @return Contraseña del usuario
     */
    public String getContrasenia() { return contrasenia; }

    /**
     * Obtiene el rol asignado al usuario.
     * @return Rol del usuario (ejemplo: ADMIN, CLIENTE)
     */
    public Rol getRol() { return rol; }

    /**
     * Obtiene la fecha de nacimiento del usuario.
     * @return Fecha de nacimiento en formato dd/MM/yyyy
     */
    public String getFechaNacimiento() { return fechaNacimiento; }

    /**
     * Obtiene el correo electrónico del usuario.
     * @return Correo electrónico
     */
    public String getEmail() { return email; }

    /**
     * Obtiene el número telefónico del usuario.
     * @return Teléfono del usuario
     */
    public String getTelefono() { return telefono; }

    /**
     * Obtiene la dirección domiciliaria del usuario.
     * @return Dirección del usuario
     */
    public String getDireccion() { return direccion; }

    /**
     * Obtiene la lista de respuestas de seguridad del usuario.
     * @return Lista de respuestas de seguridad asociadas al usuario
     */
    public List<RespuestaSeguridad> getRespuestasSeguridad() { return respuestasSeguridad; }


    // ======= SETTERS CON VALIDACIONES =======

    /**
     * Asigna y valida la cédula.
     * @param id Cédula del usuario
     * @throws CedulaExcepcion si la cédula no es válida
     * @throws CamposExcepcion si el campo está vacío
     */
    public void setId(String id) throws CedulaExcepcion, CamposExcepcion {
        if (id == null || id.isEmpty()) throw new CamposExcepcion("ID");
        if (!validarCedula(id)) throw new CedulaExcepcion("La cédula es inválida");
        this.id = id;
    }

    /**
     * Asigna y valida el nombre del usuario.
     * @param nombre Nombre del usuario
     * @throws CamposExcepcion si el campo está vacío
     */
    public void setNombre(String nombre) throws CamposExcepcion {
        if (nombre == null || nombre.isEmpty()) throw new CamposExcepcion("Nombre");
        this.nombre = nombre;
    }

    /**
     * Asigna y valida la contraseña del usuario.
     * @param contrasenia Contraseña a asignar
     * @throws ContraseniaExcepcion si la contraseña no es segura
     * @throws CamposExcepcion si el campo está vacío
     */
    public void setContrasenia(String contrasenia) throws ContraseniaExcepcion, CamposExcepcion {
        if (contrasenia == null || contrasenia.isEmpty()) throw new CamposExcepcion("Contraseña");
        if (!validarContrasenia(contrasenia)) throw new ContraseniaExcepcion("Contraseña insegura");
        this.contrasenia = contrasenia;
    }

    /**
     * Asigna y valida el correo electrónico del usuario.
     * @param email Correo electrónico
     * @throws CorreoElecExcepcion si el correo tiene formato inválido
     * @throws CamposExcepcion si el campo está vacío
     */
    public void setEmail(String email) throws CorreoElecExcepcion, CamposExcepcion {
        if (email == null || email.isEmpty()) throw new CamposExcepcion("Correo electrónico");
        if (!validarCorreo(email)) throw new CorreoElecExcepcion();
        this.email = email;
    }

    /**
     * Asigna y valida la fecha de nacimiento.
     * @param fechaNacimiento Fecha en formato dd/MM/yyyy
     * @throws FechaExcepcion si la fecha no cumple con el formato
     * @throws CamposExcepcion si el campo está vacío
     */
    public void setFechaNacimiento(String fechaNacimiento) throws FechaExcepcion, CamposExcepcion {
        if (fechaNacimiento == null || fechaNacimiento.isEmpty()) throw new CamposExcepcion("Fecha de nacimiento");
        if (!validarFecha(fechaNacimiento)) throw new FechaExcepcion();
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * Establece el rol del usuario.
     *
     * @param rol El objeto de tipo Rol que representa el rol a asignar.
     */
    public void setRol(Rol rol) {
        this.rol = rol;
    }
    /**
     * Asigna y valida el teléfono del usuario.
     * Debe ser exactamente 10 dígitos numéricos.
     * @param telefono número telefónico
     * @throws CamposExcepcion si el campo está vacío
     * @throws IllegalArgumentException si el formato es inválido o no tiene 10 dígitos
     */
    public void setTelefono(String telefono) throws CamposExcepcion {
        if (telefono == null || telefono.isEmpty()) throw new CamposExcepcion("Teléfono");
        if (!telefono.matches("\\d{10}")) throw new IllegalArgumentException("El teléfono debe tener exactamente 10 dígitos numéricos");
        this.telefono = telefono;
    }

    /**
     * Establece la dirección del usuario.
     *
     * @param direccion La cadena de texto que representa la dirección a asignar.
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Establece la lista de respuestas de seguridad del usuario.
     *
     * @param respuestasSeguridad La lista de objetos de tipo RespuestaSeguridad.
     */
    public void setRespuestasSeguridad(List<RespuestaSeguridad> respuestasSeguridad) {
        this.respuestasSeguridad = respuestasSeguridad;
    }
    // ======= VALIDACIONES INTERNAS =======

    /**
     * Valida la cédula ecuatoriana según el algoritmo de verificación.
     * @param cedula Cédula a validar
     * @return true si la cédula es válida, false en caso contrario
     */
    private boolean validarCedula(String cedula) {
        if (cedula.length() != 10) return false;
        int suma = 0;
        int verificador = Character.getNumericValue(cedula.charAt(9));
        for (int i = 0; i < 9; i++) {
            int num = Character.getNumericValue(cedula.charAt(i));
            suma += (i % 2 == 0) ? ((num * 2 > 9) ? num * 2 - 9 : num * 2) : num;
        }
        int resultado = (10 - (suma % 10)) % 10;
        return resultado == verificador;
    }

    /**
     * Valida la seguridad de la contraseña.
     * Requisitos: mínimo 6 caracteres, una minúscula, una mayúscula y un carácter especial (@, _ o -).
     * @param contrasenia Contraseña a validar
     * @return true si cumple con las políticas, false caso contrario
     */
    private boolean validarContrasenia(String contrasenia) {
        return contrasenia.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[@_-]).{6,}$");
    }

    /**
     * Valida el formato del correo electrónico.
     * @param correo Correo a validar
     * @return true si tiene formato válido, false en caso contrario
     */
    private boolean validarCorreo(String correo) {
        return correo.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }

    /**
     * Valida el formato de la fecha (dd/MM/yyyy).
     * @param fecha Fecha a validar
     * @return true si cumple el formato, false caso contrario
     */
    private boolean validarFecha(String fecha) {
        return fecha.matches("^\\d{2}/\\d{2}/\\d{4}$");
    }

    /**
     * Representación en texto del objeto Usuario.
     * @return Cadena con los valores de los atributos del usuario
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
