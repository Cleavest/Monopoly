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
public class JailField extends Field {

    private BufferedImage image;
    public JailField(int positionId) {
        super(positionId);

        this.image = ImageLoader.loadImage(Reference.PRISONER);
    }

    @Override
    public void stay(Player player, Container container, FieldController fieldController) {

    }

    @Override
    public void draw(Graphics2D g2,int x, int y) {
        int cellSize = 60;

        //drawCorner(g2,x,y);
        //GraphicsUtil.drawCorner(g2, x, y, Reference.CORNER_SIZE, Reference.CORNER_SIZE);
//        g2.drawRect(x,y,getWidth(),getHeight());

        g2.drawImage(image, x + 30, y + 30,cellSize,cellSize, null);
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
