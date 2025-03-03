package gr.cleavest.monopoly.game.field.category;

import gr.cleavest.monopoly.game.field.Field;
import gr.cleavest.monopoly.game.player.Player;
import gr.cleavest.monopoly.utils.GraphicsUtil;
import gr.cleavest.monopoly.utils.ImageLoader;
import gr.cleavest.monopoly.utils.Reference;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Cleavest on 2/3/2025
 */
public class GoToJailField extends Field {

    private BufferedImage image;

    public GoToJailField(int positionId) {
        super(positionId);

        image = ImageLoader.loadImage(Reference.GO_TO_JAIL);
    }

    @Override
    public void stay(Player player) {

    }

    @Override
    public void draw(Graphics2D g2,int x, int y) {
//        g2.drawRect(x, y, Reference.CORNER_SIZE, Reference.CORNER_SIZE);
        g2.drawImage(image, x + 25, y + 25, 50, 50, null);
    }

    @Override
    public int getWidth() {
        return Reference.CORNER_SIZE;
    }

    @Override
    public int getHeight() {
        return Reference.CORNER_SIZE;
    }

}
