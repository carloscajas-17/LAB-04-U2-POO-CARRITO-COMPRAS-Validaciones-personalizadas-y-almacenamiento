package ec.edu.ec.poo.dao.imple.binario;

import ec.edu.ec.poo.dao.PreguntaSeguridadDAO;
import ec.edu.ec.poo.modelo.PreguntaSeguridad;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación completa de PreguntaSeguridadDAO usando archivos binarios.
 * Gestiona almacenamiento de preguntas de seguridad mediante serialización.
 */
public class PreguntaSeguridadDAOArchivoBinario implements PreguntaSeguridadDAO, Serializable {

    private final List<PreguntaSeguridad> preguntas;
    private final String rutaArchivo;

    public PreguntaSeguridadDAOArchivoBinario(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
        this.preguntas = new ArrayList<>();
        cargarDesdeArchivo();
    }

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

    private void guardarEnArchivo() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(rutaArchivo))) {
            oos.writeObject(preguntas);
        } catch (IOException e) {
            System.out.println("Error al guardar preguntas binario: " + e.getMessage());
        }
    }

    @Override
    public List<PreguntaSeguridad> obtenerTodas() {
        return new ArrayList<>(preguntas);
    }

    public void agregarPregunta(PreguntaSeguridad pregunta) {
        if (!existePregunta(pregunta.getId())) {
            preguntas.add(pregunta);
            guardarEnArchivo();
        }
    }

    public PreguntaSeguridad buscarPorId(int id) {
        return preguntas.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public boolean eliminarPregunta(int id) {
        boolean eliminado = preguntas.removeIf(p -> p.getId() == id);
        if (eliminado) guardarEnArchivo();
        return eliminado;
    }

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

    public int contarPreguntas() {
        return preguntas.size();
    }

    public boolean existePregunta(int id) {
        return preguntas.stream().anyMatch(p -> p.getId() == id);
    }

    public List<PreguntaSeguridad> listar() {
        return new ArrayList<>(preguntas);
    }

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
