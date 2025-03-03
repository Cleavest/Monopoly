package gr.cleavest.monopoly.game.field.category;

import gr.cleavest.monopoly.game.field.Field;
import gr.cleavest.monopoly.game.player.Player;
import gr.cleavest.monopoly.utils.GraphicsUtil;
import gr.cleavest.monopoly.utils.ImageLoader;
import gr.cleavest.monopoly.utils.Reference;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

/**
 * @author Cleavest on 2/3/2025
 */
public class RailRoadField extends Field {

    private BufferedImage image;

    public RailRoadField(int positionId) {
        super(positionId);
        this.image = ImageLoader.loadImage(Reference.TRAIN);
    }

    @Override
    public void stay(Player player) {

    }

    @Override
    public void draw(Graphics2D g2,int x, int y) {
//        g2.setColor(Color.black);
//        g2.drawRect(0, 0, getWidth(), getHeight());
        g2.drawImage(image, x + 2, y + getHeight() / 2,getWidth() - 4,getHeight() / 2 -2, null);
    }
}
