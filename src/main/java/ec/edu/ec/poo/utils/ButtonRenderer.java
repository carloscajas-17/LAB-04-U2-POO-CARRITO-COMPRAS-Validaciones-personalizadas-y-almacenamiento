package ec.edu.ec.poo.utils;




import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class ButtonRenderer extends JPanel implements TableCellRenderer {
    private final JButton modifyButton;
    private final JButton deleteButton;

    public ButtonRenderer() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
        modifyButton = new JButton("Modificar");
        deleteButton = new JButton("Eliminar");
        add(modifyButton);
        add(deleteButton);
    }

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