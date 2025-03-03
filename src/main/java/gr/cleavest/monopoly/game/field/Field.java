package gr.cleavest.monopoly.game.field;

import gr.cleavest.monopoly.game.player.Player;
import gr.cleavest.monopoly.utils.GraphicsUtil;
import gr.cleavest.monopoly.utils.Reference;

import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * @author Cleavest on 2/3/2025
 */
public abstract class Field {

    int positionId;

    public Field (int positionId) {
        this.positionId = positionId;
    }

    public abstract void stay(Player player);

    public abstract void draw(Graphics2D g2,int x, int y);

    public void drawBorder(Graphics2D g2, int x, int y){
        AffineTransform originalTransform = g2.getTransform();
        Stroke originalStroke = g2.getStroke();

        g2.setStroke(new BasicStroke(2));
        g2.setColor(Color.BLACK);
        g2.drawRect(x, y, getWidth(), getHeight());

        g2.setTransform(originalTransform);
        g2.setStroke(originalStroke);
    }


    public int getWidth() {
        return Reference.SQUARE_WIDTH;
    }

    public int getHeight() {
        return Reference.SQUARE_HEIGHT;
    }
}
