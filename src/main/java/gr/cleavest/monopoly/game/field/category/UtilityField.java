package gr.cleavest.monopoly.game.field.category;

import gr.cleavest.monopoly.component.Background;
import gr.cleavest.monopoly.component.Button;
import gr.cleavest.monopoly.component.Label;
import gr.cleavest.monopoly.game.field.Field;
import gr.cleavest.monopoly.game.field.FieldController;
import gr.cleavest.monopoly.game.field.Sellable;
import gr.cleavest.monopoly.game.player.Player;
import gr.cleavest.monopoly.gamestate.Container;
import gr.cleavest.monopoly.gamestate.state.Game;
import gr.cleavest.monopoly.utils.ImageLoader;
import gr.cleavest.monopoly.utils.Reference;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Cleavest on 2/3/2025
 */
public class UtilityField extends Field implements Sellable {

    private final BufferedImage image;
    private Player owner;
    private final int price = 150;
    private final int mortgageValue = 75;

    public UtilityField(int positionId) {
        super(positionId);
        this.image = ImageLoader.loadImage(Reference.LUXURY);
        this.owner = null;
    }

    @Override
    public void stay(Player player, Container container, FieldController fieldController) {
        if (owner == null) {
            showBuyOption(player, container, fieldController);
        } else if (!owner.equals(player)) {
            payRent(player, container, fieldController);
        }
    }

    private void showBuyOption(Player player, Container container, FieldController fieldController) {
        int startX = Reference.CORNER_SIZE + 1;
        int startY = Reference.CORNER_SIZE + 1;
        Background background = new Background(startX, startY, Reference.BOARD_SIZE - Reference.CORNER_SIZE * 2 - 2, 200);
        container.addSecondComponent(background);

        int split = 20;
        int splitX = 10;
        int y = startY + split;

        Label title = new Label("Utility - " + (getPositionId() == 12 ? "Electric Company" : "Water Works"), startX + 10, y);
        y += split;

        Label priceLabel = new Label("Price: $" + price, startX + 10, y);
        y += split;

        Button buy = new Button("Buy for $" + price, startX + splitX, y, 100, 30)
                .addHandler(event -> {
                    if (player.getBalance() >= price) {
                        player.addBalance(-price);
                        owner = player;
                        container.addUpdateQueue(() -> {
                            container.clearSecondComponents();
                            stay(player, container, fieldController);
                        });
                    }
                })
                .setToggled(player.getBalance() >= price);
        y += split;

        container.addSecondComponent(title);
        container.addSecondComponent(priceLabel);
        container.addSecondComponent(buy);
    }

    @Override
    public void resetOwner() {
        this.owner = null;
    }

    private void payRent(Player player, Container container, FieldController fieldController) {
        int startX = Reference.CORNER_SIZE + 1;
        int startY = Reference.CORNER_SIZE + 1;
        Background background = new Background(startX, startY, Reference.BOARD_SIZE - Reference.CORNER_SIZE * 2 - 2, 200);
        container.addSecondComponent(background);

        int split = 20;
        int splitX = 10;
        int y = startY + split;

        // Υπολογισμός ενοικίου (4x ή 10x το αποτέλεσμα των ζαριών)
        int diceRoll = ((Game) container).getLastDiceRoll();
        int multiplier = fieldController.getUtilityCount(owner) == 2 ? 10 : 4;
        int rent = diceRoll * multiplier;

        Label title = new Label("Pay Rent", startX + 10, y);
        y += split;

        Label rentLabel = new Label("Rent: $" + rent + " (Dice: " + diceRoll + " x " + multiplier + ")", startX + 10, y);
        y += split;

        Button pay = new Button("Pay $" + rent, startX + splitX, y, 100, 30)
                .addHandler(event -> {
                    if (player.getBalance() >= rent) {
                        player.addBalance(-rent);
                        owner.addBalance(rent);
                        container.clearSecondComponents();
                    }
                })
                .setToggled(player.getBalance() >= rent);

        container.addSecondComponent(title);
        container.addSecondComponent(rentLabel);
        container.addSecondComponent(pay);
    }

    @Override
    public void draw(Graphics2D g2, int x, int y) {
        g2.drawImage(image, x, y, getWidth(), getHeight(), null);
        if (owner != null) {
            g2.setColor(owner.getColor());
            g2.fillRect(x, y, 5, getHeight());
        }
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public int getPrice() {
        return price;
    }

    public int getMortgageValue() {
        return mortgageValue;
    }
}
