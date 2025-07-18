package ec.edu.ec.poo.utils;


// Asegúrate de que este sea el paquete donde lo guardarás

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;

/**
 * Clase ButtonEditor permite incluir botones de acción (Modificar y Eliminar)
 * dentro de celdas de una tabla JTable. Facilita la comunicación con el controlador
 * mediante un ActionListener personalizado.
 */
public class ButtonEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {


    /** Panel contenedor de los botones */
    private final JPanel panel;


    /** Botón para modificar el elemento */
    private final JButton modifyButton;


    /** Botón para eliminar el elemento */
    private final JButton deleteButton;


    /** Tabla asociada al editor */
    private JTable table;


    /** Fila actual que se está editando */
    private int row;

    /** Comando utilizado para identificar la acción de modificar */
    public static final String ACTION_MODIFY = "modify"; // Comando para la acción de modificar


    /** Comando utilizado para identificar la acción de eliminar */
    public static final String ACTION_DELETE = "delete"; // Comando para la acción de eliminar

    /** ActionListener externo que será notificado desde el controlador */
    private ActionListener customActionListener;

    /**
     * Constructor por defecto que inicializa el editor con los botones
     * "Modificar" y "Eliminar" dentro del panel.
     */
    public ButtonEditor() {
        panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        modifyButton = new JButton("Modificar");
        deleteButton = new JButton("Eliminar");

        // Asigna comandos únicos a los botones para que el controlador los identifique
        modifyButton.setActionCommand(ACTION_MODIFY);
        deleteButton.setActionCommand(ACTION_DELETE);

        // El editor se suscribe a los clics de sus propios botones
        modifyButton.addActionListener(this);
        deleteButton.addActionListener(this);

        panel.add(modifyButton);
        panel.add(deleteButton);
    }

    /**
     * Permite asignar un ActionListener externo, generalmente desde un controlador,
     * para recibir los eventos generados por los botones dentro de la celda.
     *
     * @param listener listener externo que recibirá los eventos de los botones
     */
    public void addActionListener(ActionListener listener) {
        this.customActionListener = listener;
    }

    /**
     * Devuelve el componente de la celda cuando entra en modo edición,
     * configurando el color de fondo según la selección.
     *
     * @param table la tabla que contiene la celda
     * @param value valor actual de la celda
     * @param isSelected true si la celda está seleccionada
     * @param row fila de la celda
     * @param column columna de la celda
     * @return componente visual (panel con botones)
     */
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.table = table;
        this.row = row; // Almacena la fila actual
        // Ajusta el color de fondo del panel para que coincida con la selección
        if (isSelected) {
            panel.setBackground(table.getSelectionBackground());
        } else {
            panel.setBackground(table.getBackground());
        }
        return panel;
    }

    /**
     * Retorna el valor que será almacenado en la celda.
     * En este caso no se almacena ningún valor, por lo que retorna null.
     *
     * @return null, ya que el editor solo gestiona acciones.
     */
    @Override
    public Object getCellEditorValue() {
        return null; // Este editor no devuelve un valor de celda específico
    }

    /**
     * Indica que la celda es editable.
     *
     * @param anEvent evento que desencadena la edición
     * @return siempre true, indicando que siempre es editable
     */
    @Override
    public boolean isCellEditable(EventObject anEvent) {
        return true; // Indica que la celda es editable (para activar el editor)
    }

    /**
     * Indica si la celda debe seleccionarse cuando se activa el editor.
     *
     * @param anEvent evento que activa el editor
     * @return true para permitir la selección
     */
    @Override
    public boolean shouldSelectCell(EventObject anEvent) {
        return true; // Permite que la celda se seleccione al activar el editor
    }

    /**
     * Método que se ejecuta cuando se hace clic en cualquiera de los botones.
     * Genera un nuevo ActionEvent que notifica al listener externo,
     * concatenando el tipo de acción y la fila.
     *
     * @param e evento de acción generado por los botones
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Concatena el comando del botón con la fila para enviar información completa al controlador
        String actionCommand = e.getActionCommand() + ":" + row;

        // Si hay un ActionListener externo (nuestro controlador), le pasamos el evento
        if (customActionListener != null) {
            customActionListener.actionPerformed(new ActionEvent(table, ActionEvent.ACTION_PERFORMED, actionCommand));
        }
        fireEditingStopped(); // Detiene la edición de la celda después del clic
    }
}
