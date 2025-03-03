package gr.cleavest.monopoly.utils;

import java.awt.*;

/**
 * @author Cleavest on 2/3/2025
 */
public class GraphicsUtil {

    public static void drawCorner(Graphics2D g2, int x, int y, int width, int height) {
        g2.setColor(Color.BLACK);
        g2.drawRect(x, y, width, height);

        g2.setColor(Color.WHITE);
        g2.fillRect(x + 1, y + 1, width - 1, height - 1);
    }

    public static void fillCorner(Graphics2D g2, int x, int y, int width, int height, Color color) {
        g2.setColor(color);
        g2.fillRect(x, y, width, height);
        g2.setColor(Color.BLACK);
        g2.drawRect(x, y, width, height);
    }
}
