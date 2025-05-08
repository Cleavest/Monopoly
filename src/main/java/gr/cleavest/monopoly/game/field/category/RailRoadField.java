package gr.cleavest.monopoly.game.field.category;

import gr.cleavest.monopoly.component.Background;
import gr.cleavest.monopoly.component.Button;
import gr.cleavest.monopoly.component.Label;
import gr.cleavest.monopoly.game.field.Field;
import gr.cleavest.monopoly.game.field.FieldController;
import gr.cleavest.monopoly.game.field.Sellable;
import gr.cleavest.monopoly.game.player.Player;
import gr.cleavest.monopoly.gamestate.Container;
import gr.cleavest.monopoly.utils.ImageLoader;
import gr.cleavest.monopoly.utils.Reference;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Cleavest on 2/3/2025
 */
public class RailRoadField extends Field implements Sellable {

    private final BufferedImage image;
    private Player owner;
    private final String name;
    private final int price = 200;
    private final int[] pricing = {0, 25,50,100,200};

    public RailRoadField(int positionId, String name) {
        super(positionId);
        this.image = ImageLoader.loadImage(Reference.TRAIN);
        this.name = name;
        this.owner = null;
    }

    public int getPrice() {
        return price;
    }

    public Player getOwner() {
        return owner;
    }
    

    @Override
    public void resetOwner() {
        this.owner = null;
    }
    

    public void setOwner(Player owner) {
        this.owner = owner;
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
        if (owner == null) {
            Label text = new Label(name, startX + 10, y);
            text.setColor(Color.WHITE);
            y+= split;
            gr.cleavest.monopoly.component.Label priceLabel = new Label( "Τιμή " + price + "€", startX + splitX, y);
            priceLabel.setColor(Color.WHITE);
            y+= split;
            Button button = new Button("Buy", startX + splitX,y,100,30);
            button.addHandler(event -> {
                        player.addBalance(-price);
                        this.owner = player;
                        container.addUpdateQueue(() -> {
                            container.clearSecondComponents();
                            stay(player, container, fieldController);
                        });
                    })
                    .setToggled(player.getBalance() >= price);

            container.addSecondComponent(text);
            container.addSecondComponent(priceLabel);
            container.addSecondComponent(button);
            return;

//            int split = 10;
//
//            int y = 20 * 2 + Reference.AVATAR_HEIGHT;
//
//            gr.cleavest.monopoly.component.Label text = new gr.cleavest.monopoly.component.Label(name + " " + price, Reference.startFieldInteract, y);
//            y+= split;
//            gr.cleavest.monopoly.component.Button button = new Button("Buy", Reference.startFieldInteract,y,50,20);
//            button.addHandler(event -> {
//                        this.owner = player;
//                        container.addUpdateQueue(() -> {
//                            container.clearSecondComponents();
//                            stay(player, container, fieldController);
//                        });
//                    })
//                    .setToggled(player.getBalance() >= price);
//
//            container.addSecondComponent(text);
//            container.addSecondComponent(button);

            //return;
        }

        if (owner == player) return;

        long count = fieldController.getOwnerTrain(owner);
        int totalPrice = pricing[(int) count];

        Label payed = new Label( "Πληρώσατε " + totalPrice, startX + splitX, y);
        payed.setColor(Color.WHITE);
        player.addBalance(-totalPrice);

        container.addSecondComponent(payed);
    }

    @Override
    public void draw(Graphics2D g2,int x, int y) {

        if (owner != null) {
            g2.setColor(owner.getColor());
            g2.fillRect(x, y, getWidth(), getHeight());
        }

        g2.drawImage(image, x + 2, y + getHeight() / 2,getWidth() - 4,getHeight() / 2 -2, null);
    }
}
