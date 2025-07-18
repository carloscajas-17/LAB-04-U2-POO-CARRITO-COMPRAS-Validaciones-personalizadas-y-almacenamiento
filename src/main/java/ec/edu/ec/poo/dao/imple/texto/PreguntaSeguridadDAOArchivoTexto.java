package ec.edu.ec.poo.dao.imple.texto;

import ec.edu.ec.poo.dao.PreguntaSeguridadDAO;
import ec.edu.ec.poo.modelo.PreguntaSeguridad;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación de la interfaz {@link PreguntaSeguridadDAO} utilizando un archivo de texto
 * para almacenar y recuperar preguntas de seguridad.
 * <p>
 * Cada pregunta se almacena en una línea del archivo con el siguiente formato:
 * id|texto_pregunta
 * </p>
 */
public class PreguntaSeguridadDAOArchivoTexto implements PreguntaSeguridadDAO {

    /**
     * Lista de preguntas de seguridad cargadas desde el archivo.
     */
    private final List<PreguntaSeguridad> preguntas;

    /**
     * Ruta del archivo de texto donde se almacenan las preguntas.
     */
    private final String rutaArchivo;

    /**
     * Constructor que inicializa el DAO con la ruta del archivo y carga las preguntas existentes.
     *
     * @param rutaArchivo Ruta del archivo donde se encuentran las preguntas de seguridad.
     */
    public PreguntaSeguridadDAOArchivoTexto(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
        this.preguntas = new ArrayList<>();
        cargarDesdeArchivo();
    }

    /**
     * Carga las preguntas de seguridad desde el archivo de texto.
     * Cada línea debe estar separada por el símbolo "|".
     * Si el archivo no existe, la lista queda vacía.
     */
    private void cargarDesdeArchivo() {
        preguntas.clear();
        File archivo = new File(rutaArchivo);
        if (!archivo.exists()) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                PreguntaSeguridad pregunta = convertirLineaAPregunta(linea);
                if (pregunta != null) preguntas.add(pregunta);
            }
        } catch (IOException e) {
            System.out.println("Error al cargar preguntas: " + e.getMessage());
        }
    }

    /**
     * Convierte una línea del archivo en un objeto {@link PreguntaSeguridad}.
     *
     * @param linea Línea leída del archivo en formato id|texto.
     * @return Un objeto PreguntaSeguridad o null si ocurre un error de conversión.
     */
    private PreguntaSeguridad convertirLineaAPregunta(String linea) {
        try {
            String[] partes = linea.split("\\|");
            if (partes.length < 2) return null;
            int id = Integer.parseInt(partes[0]);
            String texto = partes[1];
            return new PreguntaSeguridad(id, texto);
        } catch (Exception e) {
            System.out.println("Error convirtiendo pregunta: " + e.getMessage());
            return null;
        }
    }

    /**
     * {@inheritDoc}
     * Obtiene la lista completa de preguntas de seguridad.
     *
     * @return Lista con todas las preguntas de seguridad almacenadas.
     */
    @Override
    public List<PreguntaSeguridad> obtenerTodas() {
        return new ArrayList<>(preguntas);
    }
}
