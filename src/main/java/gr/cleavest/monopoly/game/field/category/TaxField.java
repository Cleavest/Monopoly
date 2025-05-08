package gr.cleavest.monopoly.game.field.category;

import gr.cleavest.monopoly.component.Background;
import gr.cleavest.monopoly.component.Label;
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
        int startX = Reference.CORNER_SIZE + 1;
        int startY = Reference.CORNER_SIZE + 1;
        Background background = new Background(startX, startY, Reference.BOARD_SIZE - Reference.CORNER_SIZE * 2 - 2, 200);
        container.addSecondComponent(background);

        Label label = new Label( "Πληρώσατε φόρο 200€", startX + 20, startY + 20);
        label.setColor(Color.WHITE);
        container.addSecondComponent(label);

        player.addBalance(-200);
    }

    @Override
    public void draw(Graphics2D g2,int x, int y) {
        GraphicsUtil.drawString(g2, "TAX", 0, 0, getWidth(), getHeight(), Reference.buttonFont);

    }
}
