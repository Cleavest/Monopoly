package gr.cleavest.monopoly.game.field.category;

import gr.cleavest.monopoly.game.field.Field;
import gr.cleavest.monopoly.game.field.FieldController;
import gr.cleavest.monopoly.game.player.Player;
import gr.cleavest.monopoly.gamestate.Container;
import gr.cleavest.monopoly.utils.GraphicsUtil;
import gr.cleavest.monopoly.utils.ImageLoader;
import gr.cleavest.monopoly.utils.Reference;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Cleavest on 2/3/2025
 */
public class FreeParkingField extends Field {

    private BufferedImage image;

    public FreeParkingField(int positionId) {
        super(positionId);
        image = ImageLoader.loadImage(Reference.PARKING_IMAGE);
    }

    @Override
    public void stay(Player player, Container container, FieldController fieldController) {

    }

    @Override
    public void draw(Graphics2D g2,int x, int y) {

        //drawCorner(g2, x, y);
//        g2.setColor(Color.black);
//        g2.drawRect(x, y, getWidth(), getHeight());
        //GraphicsUtil.drawCorner(g2, x, y, Reference.CORNER_SIZE, Reference.CORNER_SIZE);
        g2.drawImage(image, x + 25, y + 25, 50,50,null);
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
