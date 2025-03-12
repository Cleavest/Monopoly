package gr.cleavest.monopoly.component;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * @author Cleavest on 12/3/2025
 */
public class Background extends Component{

    public Background(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        graphics2D.setColor(new Color(128,128,128));
        graphics2D.fillRect(x, y, width, height);
    }

    @Override
    public void onClick() throws InterruptedException {

    }

    @Override
    public void updateHoverState(MouseEvent e) {

    }
}
