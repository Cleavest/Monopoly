package gr.cleavest.monopoly.game.field.category;

import gr.cleavest.monopoly.game.field.Field;
import gr.cleavest.monopoly.game.field.FieldController;
import gr.cleavest.monopoly.game.player.Player;
import gr.cleavest.monopoly.gamestate.Container;
import gr.cleavest.monopoly.gamestate.state.Recor;
import gr.cleavest.monopoly.utils.ImageLoader;
import gr.cleavest.monopoly.utils.Reference;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Cleavest on 2/3/2025
 */
public class ChanceField extends Field {

    private BufferedImage image;

    public ChanceField(int positionId) {
        super(positionId);
        this.image = ImageLoader.loadImage(Reference.CHANCE);
    }

    @Override
    public void stay(Player player, Container container, FieldController fieldController) {

    }

    @Override
    public void draw(Graphics2D g2,int x, int y) {
        g2.drawImage(image, x, y, getWidth(), getHeight(), null);
    }
}
