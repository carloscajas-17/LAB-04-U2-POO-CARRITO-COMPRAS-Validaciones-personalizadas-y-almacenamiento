package ec.edu.ec.poo.utils;


// Asegúrate de que este sea el paquete donde lo guardarás

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;

public class ButtonEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {
    private final JPanel panel;
    private final JButton modifyButton;
    private final JButton deleteButton;
    private JTable table;
    private int row; // Guarda la fila actual para saber qué elemento se está editando

    public static final String ACTION_MODIFY = "modify"; // Comando para la acción de modificar
    public static final String ACTION_DELETE = "delete"; // Comando para la acción de eliminar

    private ActionListener customActionListener; // Listener para notificar al controlador

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
     * Permite al controlador registrar un ActionListener para ser notificado
     * cuando se haga clic en un botón dentro de la celda.
     */
    public void addActionListener(ActionListener listener) {
        this.customActionListener = listener;
    }

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

    @Override
    public Object getCellEditorValue() {
        return null; // Este editor no devuelve un valor de celda específico
    }

    @Override
    public boolean isCellEditable(EventObject anEvent) {
        return true; // Indica que la celda es editable (para activar el editor)
    }

    @Override
    public boolean shouldSelectCell(EventObject anEvent) {
        return true; // Permite que la celda se seleccione al activar el editor
    }

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
