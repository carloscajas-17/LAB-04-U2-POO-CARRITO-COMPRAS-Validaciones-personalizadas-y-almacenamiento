package ec.edu.ec.poo.dao.imple.texto;

import ec.edu.ec.poo.dao.PreguntaSeguridadDAO;
import ec.edu.ec.poo.modelo.PreguntaSeguridad;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación completa de PreguntaSeguridadDAO usando archivos de texto.
 * Lee todas las preguntas registradas desde archivo, compatible con la lógica actual.
 */
public class PreguntaSeguridadDAOArchivoTexto implements PreguntaSeguridadDAO {

    private final List<PreguntaSeguridad> preguntas;
    private final String rutaArchivo;

    public PreguntaSeguridadDAOArchivoTexto(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
        this.preguntas = new ArrayList<>();
        cargarDesdeArchivo();
    }

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

    @Override
    public List<PreguntaSeguridad> obtenerTodas() {
        return new ArrayList<>(preguntas);
    }
}
