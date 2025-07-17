package ec.edu.ec.poo.dao;

import ec.edu.ec.poo.modelo.PreguntaSeguridad;
import java.util.List;

/**
 * Interfaz DAO para la gestión de preguntas de seguridad.
 * Define la operación principal para obtener las preguntas registradas.
 *
 *
 */
public interface PreguntaSeguridadDAO {

    /**
     * Obtiene la lista completa de preguntas de seguridad disponibles en el sistema.
     *
     * @return lista de objetos {@link PreguntaSeguridad} registrados
     */
    List<PreguntaSeguridad> obtenerTodas();
}
