package ec.edu.ec.poo.dao.imple;


import ec.edu.ec.poo.dao.PreguntaSeguridadDAO;
import ec.edu.ec.poo.modelo.PreguntaSeguridad;
import ec.edu.ec.poo.utils.MensajeInternacionalizacionHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que implementat {@link PreguntaSeguridadDAO} para almacenar preguntas de seguridad
 * de forma estatica en memoria,sim persistencia externa.
 * <p>
 *O
 * Soporta actualizacion dinamica de idioma mediante un {@link MensajeInternacionalizacionHandler}
 * </p>
 */
public class PreguntaSeguridadDAOMemoria implements PreguntaSeguridadDAO {

    /**
     * Lista de preguntas de seguridad cargadas en memopria
     */
    private final List<PreguntaSeguridad> bancoPreguntas;

    /**
     * Constructor que inicializa en banco de preguntas en idioma pr defecto (español).
     * Contiene 10 peguntas de sguridad predefinidas,
     */
    public PreguntaSeguridadDAOMemoria() {
        bancoPreguntas = new ArrayList<>();
        // Inicialmente en español o el idioma por defecto (esto cambiará luego)
        bancoPreguntas.add(new PreguntaSeguridad(1, "¿Nombre de tu primera mascota?"));
        bancoPreguntas.add(new PreguntaSeguridad(2, "¿Ciudad donde naciste?"));
        bancoPreguntas.add(new PreguntaSeguridad(3, "¿Nombre de tu mejor amigo de infancia?"));
        bancoPreguntas.add(new PreguntaSeguridad(4, "¿Cuál es tu color favorito?"));
        bancoPreguntas.add(new PreguntaSeguridad(5, "¿Nombre de tu primera escuela?"));
        bancoPreguntas.add(new PreguntaSeguridad(6, "¿Nombre de tu personaje favorito?"));
        bancoPreguntas.add(new PreguntaSeguridad(7, "¿Cuál fue tu primer celular?"));
        bancoPreguntas.add(new PreguntaSeguridad(8, "¿Nombre del libro que más te gusta?"));
        bancoPreguntas.add(new PreguntaSeguridad(9, "¿Comida favorita?"));
        bancoPreguntas.add(new PreguntaSeguridad(10, "¿Nombre de tu madre?"));
    }

    /**
     * Devuelve todas las preguntas de seguridad almacenadas en memoria.
     * @return lista con las 10 preguntas de seguridad actuales
     */
    @Override
    public List<PreguntaSeguridad> obtenerTodas() {
        return bancoPreguntas;
    }


    /**
     * Actualiza los textos de las preguntas de seguirdad segun el idoama seleccionado.
     * Usando el manejador de internaciolnalizacion para obtener las preguntas desde los archivos.
     * <p>
     *  Ejemplos de clave usada:{@code preguntas.1},{@code preguntas.2},...,{@code preguntas.10}.

     * @param mensaje instancia de {@link MensajeInternacionalizacionHandler} con idiomas configurado
     */
    public void actualizarPreguntasConIdioma(MensajeInternacionalizacionHandler mensaje) {
        for (PreguntaSeguridad p : bancoPreguntas) {
            int id = p.getId();
            String nuevaPregunta = mensaje.get("pregunta." + id);
            p.setTexto(nuevaPregunta);
        }
    }

}
