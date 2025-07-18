package ec.edu.ec.poo.utils;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Clase utilitaria FormateadorUtils provee métodos estáticos para formatear
 * valores numéricos y fechas según la configuración regional (Locale).
 *
 * Clase de utilidades para formatear datos financieros y de fechas
 * respetando las convenciones de internacionalización del sistema.
 */
public class FormateadorUtils {

    /**
     * Formatea una cantidad numérica como valor monetario según el idioma y país definidos.
     *
     *
     *
     * @param cantidad monto o cantidad numérica a formatear
     * @param locale configuración regional para formatear la moneda (idioma y país)
     * @return una cadena formateada como valor monetario
     */
    public static String formatearMoneda(double cantidad, Locale locale) {
        NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(locale);
        return  formatoMoneda.format(cantidad);
    }

    /**
     * Formatea una fecha según el formato de fecha de la región especificada.
     *
     * @param fecha objeto Date que representa la fecha a formatear
     * @param locale configuración regional para formatear la fecha (idioma y país)
     * @return una cadena representando la fecha en el formato regional adecuado
     */
    public static String formatearFecha(Date fecha, Locale locale) {
        DateFormat formato = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
        return formato.format(fecha);
    }
}

