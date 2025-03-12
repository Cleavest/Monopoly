package gr.cleavest.monopoly.component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * @author Cleavest on 1/3/2025
 */

public abstract class Component {
    public final int x;
    public final int y;
    public final int width;
    public final int height;
    public Component(int x, int y, final int width, final int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    public abstract void draw(Graphics2D graphics2D);
    public abstract void onClick() throws InterruptedException;
    public boolean isHovered(MouseEvent e) {
        int cursorX = e.getX();
        int cursorY = e.getY();
        return (cursorX >= x && cursorX <= (x + width) && cursorY >= y && cursorY <= (y + height));
    }
    public void click(MouseEvent e) throws InterruptedException {
        if (isHovered(e)) {
            onClick();
        }
    }

    public abstract void updateHoverState(MouseEvent e);
}
