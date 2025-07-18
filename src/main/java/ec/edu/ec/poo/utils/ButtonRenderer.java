package ec.edu.ec.poo.utils;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * Clase ButtonRenderer es un renderizador personalizado para celdas de una JTable,
 * mostrando botones de "Modificar" y "Eliminar" en cada celda. Se utiliza para renderizar
 * visualmente las celdas antes de cualquier edición.
 *
 * Renderiza botones personalizados para acciones dentro de una celda de JTable.
 *
 */
public class ButtonRenderer extends JPanel implements TableCellRenderer {


    /** Botón de acción para modificar un registro */
    private final JButton modifyButton;


    /** Botón de acción para eliminar un registro */
    private final JButton deleteButton;

    /**
     * Constructor que inicializa el panel con los botones "Modificar" y "Eliminar".
     * Los botones son visuales únicamente y no manejan eventos desde este renderizador.
     */
    public ButtonRenderer() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
        modifyButton = new JButton("Modificar");
        deleteButton = new JButton("Eliminar");
        add(modifyButton);
        add(deleteButton);
    }

    /**
     * Método sobrescrito de TableCellRenderer para renderizar el componente de celda.
     * Ajusta el color de fondo según si la celda está seleccionada.
     *
     * @param table la tabla que solicita el renderizado
     * @param value el valor de la celda (no se usa aquí)
     * @param isSelected indica si la celda está seleccionada
     * @param hasFocus indica si la celda tiene el foco (no usado)
     * @param row índice de fila
     * @param column índice de columna
     * @return el componente JPanel con los botones renderizados
     */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (isSelected) {
            setBackground(table.getSelectionBackground());
        } else {
            setBackground(table.getBackground());
        }
        return this;
    }
}