package gr.cleavest.monopoly.component;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

/**
 * @author Cleavest on 12/3/2025
 */
public class DrawComponent extends Component {

    private final Consumer<Graphics2D> consumer;

    public DrawComponent(int x, int y, int width, int height, Consumer<Graphics2D> consumer) {
        super(x, y, width, height);
        this.consumer = consumer;
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        consumer.accept(graphics2D);
    }

    @Override
    public void onClick() throws InterruptedException {

    }

    @Override
    public void updateHoverState(MouseEvent e) {

    }
}
