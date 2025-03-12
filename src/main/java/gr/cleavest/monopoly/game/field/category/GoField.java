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
public class GoField extends Field {

    private BufferedImage image;

    public GoField(int positionId) {
        super(positionId);


        this.image = ImageLoader.loadImage(Reference.GO);
    }

    @Override
    public void stay(Player player, Container container, FieldController fieldController) {

    }

    @Override
    public void draw(Graphics2D g2,int x, int y) {

//        g2.drawRect(x,y,getWidth(),getHeight());
        g2.drawImage(image, x + 20, y + 20, 50, 50, null);
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
