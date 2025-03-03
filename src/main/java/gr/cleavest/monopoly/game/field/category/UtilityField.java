package gr.cleavest.monopoly.game.field.category;

import gr.cleavest.monopoly.game.field.Field;
import gr.cleavest.monopoly.game.player.Player;
import gr.cleavest.monopoly.utils.ImageLoader;
import gr.cleavest.monopoly.utils.Reference;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Cleavest on 2/3/2025
 */
public class UtilityField extends Field {

    private BufferedImage image;
    public UtilityField(int positionId) {
        super(positionId);
        this.image = ImageLoader.loadImage(Reference.LUXURY);
    }

    @Override
    public void stay(Player player) {

    }

    @Override
    public void draw(Graphics2D g2,int x, int y) {
//        g2.drawRect(x,y,getWidth(),getHeight());
        g2.drawImage(image, x, y, getWidth(), getHeight(), null);
    }
}
