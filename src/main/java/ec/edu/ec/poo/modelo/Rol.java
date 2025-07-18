package ec.edu.ec.poo.modelo;

/**
 * Enumeración Rol que representa los distintos tipos de roles que puede tener un usuario
 * dentro del sistema.
 *
 * Los roles determinan los permisos y accesos del usuario a diferentes funcionalidades.
 *
 * <ul>
 *     <li>ADMINISTRADOR: Usuario con acceso total al sistema (gestión de usuarios, productos, carritos).</li>
 *     <li>USUARIO: Usuario con acceso básico para realizar compras y gestionar su propia cuenta.</li>
 * </ul>
 */
public enum Rol {

    /**
     * Rol de administrador del sistema con acceso completo.
     */
    ADMINISTRADOR,


    /**
     * Rol de usuario básico del sistema con acceso limitado.
     */
    USUARIO,
}
