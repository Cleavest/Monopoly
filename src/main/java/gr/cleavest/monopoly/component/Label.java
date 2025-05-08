package gr.cleavest.monopoly.component;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * @author Cleavest on 6/3/2025
 */
public class Label extends Component {

    private String text;
    private Color color;
    private boolean background;
    private Color backgroundColor;

    public Label(String text, int x, int y) {
        super(x,y,0,0);
        this.text = text;
        this.background = false;
        this.setColor(Color.BLACK);
    }

    public Label(int x, int y, int width, int height, String text, Color color, Color backgroundColor) {
        super(x, y, width, height);
        this.text = text;
        this.color = color;
        this.background = true;
        this.backgroundColor = backgroundColor;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void draw(Graphics2D graphics2D) {

        if (background) {
            graphics2D.setColor(backgroundColor);
            graphics2D.fillRect(x, y, width, height);

            FontMetrics fm = graphics2D.getFontMetrics();
            int textWidth = fm.stringWidth(text);
            int textHeight = fm.getHeight();

            graphics2D.setColor(color);
            graphics2D.drawString(text, x + (width - textWidth) / 2, y + (height - textHeight) / 2 + fm.getAscent());

            return;
        }

        graphics2D.setColor(color);
        graphics2D.drawString(text, x, y);
    }

    @Override
    public void onClick() {

    }

    @Override
    public void updateHoverState(MouseEvent e) {

    }

    public void setBackground(boolean background) {
        this.background = background;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
}
