package gr.cleavest.monopoly.game.field.category;

import gr.cleavest.monopoly.component.Background;
import gr.cleavest.monopoly.component.Button;
import gr.cleavest.monopoly.component.Label;
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
        int startX = Reference.CORNER_SIZE + 1;
        int startY = Reference.CORNER_SIZE + 1;
        Background background = new Background(startX, startY, Reference.BOARD_SIZE - Reference.CORNER_SIZE * 2 - 2, 200);
        container.addSecondComponent(background);

        int split = 20;
        int splitX = 10;
        int y = startY + split;

        Label text = new Label("Prison", startX + 10, y);
        y+= split;

        Button pay = new Button("pay 50 $", startX + splitX,y,100,30)
                .addHandler(event -> {
                    player.addBalance(-50);
                    player.exitJail();
                }).setToggled(player.getBalance() > 50);
        y+= split;

        Button useCard = new Button("use card (" +  player.getJailCard() +")", startX + splitX,y,100,30)
                .addHandler(event -> {
                    player.changeJailCard(-1);
                    player.exitJail();
                })
                .setToggled(player.getJailCard() > 0);

        container.addSecondComponent(text);
        container.addSecondComponent(pay);
        container.addSecondComponent(useCard);
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
