package gr.cleavest.monopoly.component;

import gr.cleavest.monopoly.utils.TextWrapper;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.List;

/**
 * @author Cleavest on 12/3/2025
 */
public class MultiLabel extends Component {

    private final String text;
    private Color color;
    private Font font;

    public MultiLabel(String text, int x, int y, int width) {
        super(x, y, width, 0);
        this.text = text;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        graphics2D.setColor(color);

        if (font != null) {
            graphics2D.setFont(font);
        }

        List<String> lines = TextWrapper.wrapText(text, graphics2D.getFontMetrics(), width - 10);
        int lineHeight = graphics2D.getFontMetrics().getHeight();
        int tempY = y;

        for (String line : lines) {
            graphics2D.drawString(line, x, tempY);
            tempY += lineHeight;
        }
    }

    @Override
    public void onClick() throws InterruptedException {

    }

    @Override
    public void updateHoverState(MouseEvent e) {

    }
}
