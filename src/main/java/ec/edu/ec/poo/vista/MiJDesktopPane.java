package ec.edu.ec.poo.vista;

import javax.swing.*;
import java.awt.*;

/**
 * Clase personalizada de {@link JDesktopPane} que dibuja un fondo gráfico temático
 * con texto personalizado, una ilustración de celular, carrito y personaje.
 * También incluye un toldo con franjas y un mensaje de bienvenida.
 *
 * Esta clase se usa como fondo principal del sistema "Tienda El Rincón Original".
 */
public class MiJDesktopPane extends JDesktopPane {
    /**
     * Método sobrescrito para pintar el fondo del panel con gráficos personalizados.
     *
     * @param g el contexto gráfico utilizado para pintar el componente.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int cx = getWidth() / 2;
        int cy = getHeight() / 2;

        // Fondo celeste claro
        g2.setColor(new Color(200, 230, 255));
        g2.fillRect(0, 0, getWidth(), getHeight());

        // ===== TEXTO DE FONDO =====
        g2.setColor(new Color(0, 51, 102)); // Azul oscuro
        g2.setFont(new Font("SansSerif", Font.BOLD, 36));
        String textoFondo = "TIENDA EL RINCÓN ORIGINAL";
        FontMetrics fm = g2.getFontMetrics();
        int textoWidth = fm.stringWidth(textoFondo);
        g2.drawString(textoFondo, (getWidth() - textoWidth) / 2, 50);

        // ===== CELULAR =====
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

        g2.setColor(Color.GRAY);
        g2.fillOval(cx - 10, phoneY + phoneH - 15, 20, 20);

        // ===== TOLDO =====
        int stripeWidth = 22;
        Color[] colores = {Color.BLUE, Color.WHITE};
        for (int i = 0; i < 7; i++) {
            g2.setColor(colores[i % 2]);
            g2.fillRect(phoneX + i * stripeWidth, phoneY - 30, stripeWidth, 40);
            g2.setColor(Color.BLACK);
            g2.drawRect(phoneX + i * stripeWidth, phoneY - 30, stripeWidth, 40);
        }

        g2.setColor(Color.BLUE);
        g2.fillRect(phoneX, phoneY + 10, phoneW, 10);

        // ===== TEXTO EN PANTALLA =====
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("SansSerif", Font.BOLD, 18));
        g2.drawString("¡Bienvenido al", phoneX + 30, phoneY + 110);
        g2.drawString("Carrito de Compras!", phoneX + 15, phoneY + 140);

        // ===== CARRITO + PERSONAJE =====
        int cartX = cx + 70;
        int cartY = cy + 50;
        int cartW = 100;
        int cartH = 60;

        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(cartX, cartY, cartW, cartH, 10, 10);
        g2.drawLine(cartX - 30, cartY - 20, cartX, cartY + 10);

        g2.fillOval(cartX + 10, cartY + cartH, 15, 15);
        g2.fillOval(cartX + cartW - 25, cartY + cartH, 15, 15);

        for (int i = 1; i < 4; i++) {
            int x = cartX + i * cartW / 4;
            g2.drawLine(x, cartY, x, cartY + cartH);
        }

        g2.setColor(Color.BLACK);
        g2.fillOval(cartX - 80, cartY - 50, 30, 30); // Cabeza
        g2.fillRoundRect(cartX - 75, cartY - 20, 20, 50, 10, 10); // Cuerpo
        g2.fillRoundRect(cartX - 60, cartY - 10, 30, 10, 10, 10); // Brazo
        g2.fillRoundRect(cartX - 75, cartY + 30, 15, 40, 10, 10); // Pierna
        g2.fillRoundRect(cartX - 50, cartY + 30, 15, 30, 10, 10); // Pierna
    }
}

