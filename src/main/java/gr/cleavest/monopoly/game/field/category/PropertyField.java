package gr.cleavest.monopoly.game.field.category;

import gr.cleavest.monopoly.game.field.Field;
import gr.cleavest.monopoly.game.player.Player;
import gr.cleavest.monopoly.utils.GraphicsUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * @author Cleavest on 2/3/2025
 */
public class PropertyField extends Field {

    private final Color color;

    public PropertyField(int positionId, Color color) {
        super(positionId);
        this.color = color;
    }

    @Override
    public void stay(Player player) {

    }

    @Override
    public void draw(Graphics2D g2, int x, int y) {
        AffineTransform originalTransform = g2.getTransform();
        Stroke originalStroke = g2.getStroke();

        g2.setStroke(new BasicStroke(2));
        //GraphicsUtil.fillCorner(g2,x, y ,  getWidth(), 15, getColor());
//        g2.setColor(Color.BLACK);
//        g2.drawRect(x, y, getWidth(), getHeight());
//

        g2.setColor(color);
        g2.fillRect(x, y, getWidth(), 15);
        g2.setColor(Color.BLACK);
        g2.drawLine(x, 15, getWidth(), 15);
//        g2.setColor(Color.BLACK);
//        g2.drawRect(x, y, width, height);

        g2.setTransform(originalTransform);
        g2.setStroke(originalStroke);
    }

    public Color getColor() {
        return color;
    }
}
