package gr.cleavest.monopoly.game.field.category;

import gr.cleavest.monopoly.game.field.Field;
import gr.cleavest.monopoly.game.field.FieldController;
import gr.cleavest.monopoly.game.player.Player;
import gr.cleavest.monopoly.gamestate.Container;
import gr.cleavest.monopoly.utils.GraphicsUtil;
import gr.cleavest.monopoly.utils.Reference;

import java.awt.*;

/**
 * @author Cleavest on 2/3/2025
 */
public class TaxField extends Field {

    public TaxField(int positionId) {
        super(positionId);
    }

    @Override
    public void stay(Player player, Container container, FieldController fieldController) {

    }

    @Override
    public void draw(Graphics2D g2,int x, int y) {
//        g2.drawRect(x, y, getWidth(), getHeight());
        //g2.drawString("TAX", x + getWidth() / 2, y + getHeight() / 2);
        GraphicsUtil.drawString(g2, "TAX", 0, 0, getWidth(), getHeight(), Reference.buttonFont);

    }
}
