package ec.edu.ec.poo.dao.imple;


import ec.edu.ec.poo.dao.PreguntaSeguridadDAO;
import ec.edu.ec.poo.modelo.PreguntaSeguridad;
import ec.edu.ec.poo.utils.MensajeInternacionalizacionHandler;

import java.util.ArrayList;
import java.util.List;

public class PreguntaSeguridadDAOMemoria implements PreguntaSeguridadDAO {

    private final List<PreguntaSeguridad> bancoPreguntas;

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

    @Override
    public List<PreguntaSeguridad> obtenerTodas() {
        return bancoPreguntas;
    }


    public void actualizarPreguntasConIdioma(MensajeInternacionalizacionHandler mensaje) {
        for (PreguntaSeguridad p : bancoPreguntas) {
            int id = p.getId();
            String nuevaPregunta = mensaje.get("pregunta." + id);
            p.setTexto(nuevaPregunta);
        }
    }
}
