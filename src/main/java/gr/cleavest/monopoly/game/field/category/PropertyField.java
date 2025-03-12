package gr.cleavest.monopoly.game.field.category;

import gr.cleavest.monopoly.component.Background;
import gr.cleavest.monopoly.component.Button;
import gr.cleavest.monopoly.component.DrawComponent;
import gr.cleavest.monopoly.component.Label;
import gr.cleavest.monopoly.game.field.Field;
import gr.cleavest.monopoly.game.field.ColorGroup;
import gr.cleavest.monopoly.game.field.FieldController;
import gr.cleavest.monopoly.game.player.Player;
import gr.cleavest.monopoly.gamestate.Container;
import gr.cleavest.monopoly.utils.GraphicsUtil;
import gr.cleavest.monopoly.utils.Reference;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.List;

/**
 * @author Cleavest on 2/3/2025
 */
public class PropertyField extends Field {

    private Player owner;
    private final ColorGroup group;
    private final String name;
    private final int price;
    private int propertyCount;
    private final List<Integer> prices;
    private final int buildingCost;

    public PropertyField(int positionId, String name, int price, ColorGroup group, List<Integer> prices, int buildingCost) {
        super(positionId);
        this.group = group;
        this.name = name;
        this.price = price;
        this.owner = null;
        this.propertyCount = 0;
        this.prices = prices;
        this.buildingCost = buildingCost;
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
            Label priceLabel = new Label( "Τιμή " + price + "€", startX + splitX, y);
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
        }

        if (owner == player) {
            if (propertyCount == 5) {
                Label max = new Label( "Δεν μπορείτε να χτίσετε άλλο.", startX + splitX, y);
                max.setColor(Color.WHITE);
                container.addSecondComponent(max);
                y+= split;
                return;
            }

            Label building = new Label( "Χτίσιμο ", startX + splitX, y);
            building.setColor(Color.WHITE);
            y+= split;

            String text = propertyCount == 4 ? "Ξενοδοχείο" : "Σπίτι " + (propertyCount + 1);
            Label house = new Label(text + " Τιμή " +  buildingCost + "€", startX + splitX, y);
            house.setColor(Color.WHITE);
            y+= split;

            Button button = new Button("Buy", startX + splitX,y,100,30);
            button.addHandler(btn -> {
                player.addBalance(-buildingCost);
                propertyCount++;
                container.addUpdateQueue(() -> {
                    container.clearSecondComponents();
                    stay(player, container, fieldController);
                });
            });
            button.setToggled(player.getBalance() >= buildingCost);

            container.addSecondComponent(button);
            container.addSecondComponent(house);
            container.addSecondComponent(building);
            //upgrade
            return;
        }

        boolean hasOwnerAllColor = fieldController.hasAllGroup(this);
        int totalPrice = prices.get(propertyCount);

        if (hasOwnerAllColor) {
            totalPrice *= 2;
        }

        Label payed = new Label( "Πληρώσατε " + totalPrice, startX + splitX, y);
        payed.setColor(Color.WHITE);
        player.addBalance(-totalPrice);

        container.addSecondComponent(payed);

    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public Player getOwner() {
        return owner;
    }

    public int getPropertyCount() {
        return propertyCount;
    }

    public ColorGroup getGroup() {
        return group;
    }

    @Override
    public void draw(Graphics2D g2, int x, int y) {
        AffineTransform originalTransform = g2.getTransform();
        Stroke originalStroke = g2.getStroke();
        Font currentFont = g2.getFont();

        g2.setStroke(new BasicStroke(2));

        if (owner != null) {
            g2.setColor(owner.getColor());
            g2.fillRect(x, y, getWidth(), getHeight());
        }

        g2.setColor(getColor());
        g2.fillRect(x, y, getWidth(), 15);
        g2.setColor(Color.BLACK);
        g2.drawLine(x, 15, getWidth(), 15);

        g2.setTransform(originalTransform);
        g2.setStroke(originalStroke);

        GraphicsUtil.drawString(g2, name, 0,0,getWidth(),getHeight(), Reference.buttonFont);
        g2.setFont(currentFont);
    }

    public Color getColor() {
        return group.getColor();
    }
}
