package gr.cleavest.monopoly.component;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * @author Cleavest on 6/3/2025
 */
public class Label extends Component {

    private final String text;
    private Color color;

    public Label(String text, int x, int y) {
        super(x, y, 0, 0);
        this.text = text;
        this.setColor(Color.BLACK);
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        graphics2D.setColor(color);
        graphics2D.drawString(text, x, y);
    }

    @Override
    public void onClick() {

    }

    @Override
    public void updateHoverState(MouseEvent e) {

    }
}
