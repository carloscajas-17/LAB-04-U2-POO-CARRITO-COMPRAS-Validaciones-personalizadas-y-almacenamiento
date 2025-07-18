package ec.edu.ec.poo.dao.imple.binario;

import ec.edu.ec.poo.dao.PreguntaSeguridadDAO;
import ec.edu.ec.poo.modelo.PreguntaSeguridad;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación de la interfaz {@link PreguntaSeguridadDAO} utilizando archivos binarios.
 * <p>
 * Permite almacenar, listar, buscar, actualizar y eliminar preguntas de seguridad mediante
 * serialización en archivos binarios.
 * </p>
 */
public class PreguntaSeguridadDAOArchivoBinario implements PreguntaSeguridadDAO, Serializable {

    /**
     * Lista de preguntas cargadas desde el archivo binario.
     */
    private final List<PreguntaSeguridad> preguntas;

    /**
     * Ruta del archivo binario donde se almacenan las preguntas.
     */
    private final String rutaArchivo;

    /**
     * Constructor que inicializa el DAO con la ruta del archivo y carga las preguntas desde el archivo binario.
     *
     * @param rutaArchivo Ruta del archivo binario.
     */
    public PreguntaSeguridadDAOArchivoBinario(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
        this.preguntas = new ArrayList<>();
        cargarDesdeArchivo();
    }

    /**
     * Carga las preguntas de seguridad desde el archivo binario utilizando deserialización.
     * Si el archivo no existe, la lista se inicializa vacía.
     */
    private void cargarDesdeArchivo() {
        preguntas.clear();
        File archivo = new File(rutaArchivo);
        if (!archivo.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            Object obj = ois.readObject();
            if (obj instanceof List<?>) {
                List<?> lista = (List<?>) obj;
                for (Object p : lista) {
                    if (p instanceof PreguntaSeguridad) {
                        preguntas.add((PreguntaSeguridad) p);
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al cargar preguntas binario: " + e.getMessage());
        }
    }

    /**
     * Guarda la lista actualizada de preguntas en el archivo binario mediante serialización.
     */
    private void guardarEnArchivo() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(rutaArchivo))) {
            oos.writeObject(preguntas);
        } catch (IOException e) {
            System.out.println("Error al guardar preguntas binario: " + e.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     * Retorna una lista de todas las preguntas almacenadas.
     *
     * @return Lista completa de preguntas.
     */
    @Override
    public List<PreguntaSeguridad> obtenerTodas() {
        return new ArrayList<>(preguntas);
    }

    /**
     * Agrega una nueva pregunta de seguridad si no existe previamente.
     *
     * @param pregunta Pregunta de seguridad a agregar.
     */
    public void agregarPregunta(PreguntaSeguridad pregunta) {
        if (!existePregunta(pregunta.getId())) {
            preguntas.add(pregunta);
            guardarEnArchivo();
        }
    }

    /**
     * Busca una pregunta por su ID.
     *
     * @param id ID de la pregunta a buscar.
     * @return Pregunta encontrada o null si no existe.
     */
    public PreguntaSeguridad buscarPorId(int id) {
        return preguntas.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    /**
     * Elimina una pregunta mediante su ID.
     *
     * @param id ID de la pregunta a eliminar.
     * @return true si se eliminó exitosamente, false si no existía.
     */
    public boolean eliminarPregunta(int id) {
        boolean eliminado = preguntas.removeIf(p -> p.getId() == id);
        if (eliminado) guardarEnArchivo();
        return eliminado;
    }

    /**
     * Actualiza una pregunta existente buscando por su ID.
     *
     * @param pregunta Pregunta actualizada.
     * @return true si se actualizó, false si no se encontró.
     */
    public boolean actualizarPregunta(PreguntaSeguridad pregunta) {
        for (int i = 0; i < preguntas.size(); i++) {
            if (preguntas.get(i).getId() == pregunta.getId()) {
                preguntas.set(i, pregunta);
                guardarEnArchivo();
                return true;
            }
        }
        return false;
    }

    /**
     * Devuelve la cantidad total de preguntas almacenadas.
     *
     * @return Número total de preguntas.
     */
    public int contarPreguntas() {
        return preguntas.size();
    }

    /**
     * Verifica si una pregunta existe en la lista mediante su ID.
     *
     * @param id ID de la pregunta.
     * @return true si existe, false si no.
     */
    public boolean existePregunta(int id) {
        return preguntas.stream().anyMatch(p -> p.getId() == id);
    }

    /**
     * Lista todas las preguntas almacenadas.
     *
     * @return Lista de preguntas.
     */
    public List<PreguntaSeguridad> listar() {
        return new ArrayList<>(preguntas);
    }

    /**
     * Busca preguntas que contengan un texto específico (ignorando mayúsculas y minúsculas).
     *
     * @param texto Texto a buscar dentro del contenido de las preguntas.
     * @return Lista de preguntas que contienen el texto indicado.
     */
    public List<PreguntaSeguridad> buscarPorTexto(String texto) {
        List<PreguntaSeguridad> resultado = new ArrayList<>();
        for (PreguntaSeguridad pregunta : preguntas) {
            if (pregunta.getTexto().toLowerCase().contains(texto.toLowerCase())) {
                resultado.add(pregunta);
            }
        }
        return resultado;
    }
}
