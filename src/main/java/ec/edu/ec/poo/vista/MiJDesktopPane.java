package ec.edu.ec.poo.vista;

import javax.swing.*;
import java.awt.*;

/**
 * JDesktopPane personalizado con fondo degradado y figura de carrito con personaje y toldo.
 */
public class MiJDesktopPane extends JDesktopPane {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        int width = getWidth();
        int height = getHeight();
        int cx = width / 2;
        int cy = height / 2;

        // Fondo degradado
        GradientPaint gradiente = new GradientPaint(0, 0, new Color(0, 170, 255), 0, height, new Color(0, 255, 204));
        g2.setPaint(gradiente);
        g2.fillRect(0, 0, width, height);

        // Título principal
        g2.setColor(new Color(0, 51, 102));
        g2.setFont(new Font("SansSerif", Font.BOLD, 36));
        String texto = "TIENDA EL RINCÓN ORIGINAL";
        FontMetrics fm = g2.getFontMetrics();
        int textWidth = fm.stringWidth(texto);
        g2.drawString(texto, (width - textWidth) / 2, 50);

        // Celular con toldo
        int phoneW = 220;
        int phoneH = 400;
        int phoneX = cx - phoneW / 2;
        int phoneY = cy - 200;

        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRoundRect(phoneX, phoneY, phoneW, phoneH, 30, 30);
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(phoneX, phoneY, phoneW, phoneH, 30, 30);

        g2.setColor(Color.WHITE);
        g2.fillRoundRect(phoneX + 10, phoneY + 60, phoneW - 20, phoneH - 80, 20, 20);

        // Toldo tipo tienda
        int stripeWidth = 22;
        Color[] coloresToldo = {Color.BLUE, Color.WHITE};
        for (int i = 0; i < 7; i++) {
            g2.setColor(coloresToldo[i % 2]);
            g2.fillRect(phoneX + i * stripeWidth, phoneY - 30, stripeWidth, 40);
            g2.setColor(Color.BLACK);
            g2.drawRect(phoneX + i * stripeWidth, phoneY - 30, stripeWidth, 40);
        }

        g2.setColor(Color.BLUE);
        g2.fillRect(phoneX, phoneY + 10, phoneW, 10);

        // Texto dentro del celular
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("SansSerif", Font.BOLD, 18));
        g2.drawString("¡Bienvenido al", phoneX + 30, phoneY + 110);
        g2.drawString("Carrito de Compras!", phoneX + 15, phoneY + 140);

        // Carrito de compras con personaje
        int cartX = cx + 70;
        int cartY = cy + 50;
        int cartW = 100;
        int cartH = 60;

        g2.setStroke(new BasicStroke(3));
        g2.setColor(Color.BLACK);
        g2.drawRoundRect(cartX, cartY, cartW, cartH, 10, 10);
        g2.drawLine(cartX - 30, cartY - 20, cartX, cartY + 10);

        g2.fillOval(cartX + 10, cartY + cartH, 15, 15);
        g2.fillOval(cartX + cartW - 25, cartY + cartH, 15, 15);

        for (int i = 1; i < 4; i++) {
            int x = cartX + i * cartW / 4;
            g2.drawLine(x, cartY, x, cartY + cartH);
        }

        // Personaje empujando el carrito
        g2.setColor(Color.BLACK);
        g2.fillOval(cartX - 80, cartY - 50, 30, 30); // Cabeza
        g2.fillRoundRect(cartX - 75, cartY - 20, 20, 50, 10, 10); // Cuerpo
        g2.fillRoundRect(cartX - 60, cartY - 10, 30, 10, 10, 10); // Brazo
        g2.fillRoundRect(cartX - 75, cartY + 30, 15, 40, 10, 10); // Pierna izquierda
        g2.fillRoundRect(cartX - 50, cartY + 30, 15, 30, 10, 10); // Pierna derecha
    }
}
