package gr.cleavest.monopoly.game.player;

import gr.cleavest.monopoly.game.field.Field;
import gr.cleavest.monopoly.game.field.FieldController;
import gr.cleavest.monopoly.game.field.Sellable;
import gr.cleavest.monopoly.game.field.category.PropertyField;
import gr.cleavest.monopoly.game.field.category.RailRoadField;
import gr.cleavest.monopoly.game.field.category.UtilityField;
import gr.cleavest.monopoly.gamestate.state.Game;
import gr.cleavest.monopoly.utils.Reference;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Cleavest on 2/3/2025
 */
public class Player {

    private final Color color;
    private int positionId;
    private int balance;
    private int jailCard;
    private boolean isJail;
    private int roundsInJail;
    private boolean isBankrupt;
    private final FieldController fieldController;

    public Player(Color color, FieldController fieldController) {
        this.positionId = 0;
        this.color = color;
        this.balance = 1000;
        this.jailCard = 0;
        this.isJail = false;
        this.roundsInJail = 0;
        this.fieldController = fieldController;
        this.isBankrupt = false;
    }

    public void draw(Graphics2D g2 , int x , int y, int width, int height) {
        Color originalColor = g2.getColor();
        Stroke originalStroke = g2.getStroke();
        Object originalAntiAlias = g2.getRenderingHint(RenderingHints.KEY_ANTIALIASING);

        // Ενεργοποίηση του anti-aliasing για λείες γραμμές και καμπύλες
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(color);
        g2.fillOval(x , y  , 20  , 20 );

        g2.setColor(Color.BLACK);
        g2.drawOval(x, y , 20, 20);

        g2.setColor(originalColor);
        g2.setStroke(originalStroke);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, originalAntiAlias);
    }

    public void move(int numSpaces) {

        positionId += numSpaces;
        if (positionId >= Reference.FIELD_SIZE) {
            positionId -= Reference.FIELD_SIZE;
            addBalance(200);
        }
    }

    public void moveTo(int value, Game game) {
        positionId = value;
        game.stay(this);
    }

    public int getPosition() {
        return positionId;
    }

    public int getBalance() {
        return balance;
    }

    public void changeJailCard(int value) {
        this.jailCard += value;
    }

    public void addBalance(int amount) {
        balance += amount;

        if (balance < 0) {
            handleNegativeBalance();
        }
    }

    private void handleNegativeBalance() {
        int totalAssets = calculateTotalAssets();
        
        if (totalAssets < Math.abs(balance)) {
            isBankrupt = true;
            return;
        }

        // Πρώτα πουλάνε τα σπίτια/ξενοδοχεία
        sellHouses();
        
        // Αν ακόμα χρειάζεται, πουλάνε τα ακίνητα
        if (balance < 0) {
            sellProperties();
        }
    }

    private void sellHouses() {
        Arrays.stream(fieldController.getFields())
                .filter(field -> field instanceof PropertyField)
                .map(field -> (PropertyField) field)
                .filter(field -> field.getOwner() == this)
                .forEach(property -> {
                    while (property.getPropertyCount() > 0 && balance < 0) {
                        int houseValue = property.getHousePriceSell() / 2; // 50% της αξίας για σπίτια
                        property.changePropertyCount(-1);
                        balance += houseValue;
                    }
                });
    }

    private void sellProperties() {
        // Ταξινόμηση περιουσίας με βάση την αξία
        List<Field> properties = Arrays.stream(fieldController.getFields())
                .filter(field -> (field instanceof PropertyField || 
                                field instanceof RailRoadField || 
                                field instanceof UtilityField) && 
                                getFieldOwner(field) == this)
                .sorted((f1, f2) -> {
                    int value1 = calculateFieldValue(f1);
                    int value2 = calculateFieldValue(f2);
                    return Integer.compare(value2, value1); // Φθίνουσα ταξινόμηση
                })
                .collect(Collectors.toList());

        // Πώληση περιουσίας μέχρι να καλύψουμε το αρνητικό υπόλοιπο
        for (Field field : properties) {
            if (balance >= 0) break;

            int sellValue = calculateFieldValue(field);
            setFieldOwner(field, null);
            balance += sellValue;
        }
    }

    private Player getFieldOwner(Field field) {
        if (field instanceof PropertyField) {
            return ((PropertyField) field).getOwner();
        } else if (field instanceof RailRoadField) {
            return ((RailRoadField) field).getOwner();
        } else if (field instanceof UtilityField) {
            return ((UtilityField) field).getOwner();
        }
        return null;
    }

    private void setFieldOwner(Field field, Player owner) {

        if (field instanceof Sellable) {
            ((Sellable) field).resetOwner();
        }
        // if (field instanceof PropertyField) {
        //     ((PropertyField) field).setOwner(owner);
        // } else if (field instanceof RailRoadField) {
        //     ((RailRoadField) field).setOwner(owner);
        // } else if (field instanceof UtilityField) {
        //     ((UtilityField) field).setOwner(owner);
        // }
    }

    private int calculateFieldValue(Field field) {
        if (field instanceof PropertyField property) {
            return (int) (property.getPrice() * 0.8);
        } else if (field instanceof RailRoadField railroad) {
            return (int) (railroad.getPrice() * 0.8);
        } else if (field instanceof UtilityField utility) {
            return (int) (utility.getPrice() * 0.8);
        }
        return 0;
    }

    private int calculateTotalAssets() {
        int total = 0;
        
        // Υπολογισμός αξίας ακινήτων
        for (Field field : fieldController.getFields()) {
            if (field instanceof PropertyField property && property.getOwner() == this) {
                total += (int) (property.getPrice() * 0.8);
                total += property.getPropertyCount() * property.getHousePriceSell() / 2;
            } else if (field instanceof RailRoadField railroad && railroad.getOwner() == this) {
                total += (int) (railroad.getPrice() * 0.8);
            } else if (field instanceof UtilityField utility && utility.getOwner() == this) {
                total += (int) (utility.getPrice() * 0.8);
            }
        }

        return total;
    }

    public Color getColor() {
        return color;
    }

    public void goToJail() {
        this.isJail = true;
        this.positionId = 10;
        this.roundsInJail = 0;
    }

    public void exitJail(){
        this.isJail = false;
        this.roundsInJail = 0;
    }

    public void addRoundInJail() {
        roundsInJail += 1;
    }

    public int getRoundsInJail() {
        return roundsInJail;
    }

    public int getJailCard() {
        return jailCard;
    }

    public boolean isJail() {
        return isJail;
    }

    public void setJail(boolean jail) {
        isJail = jail;
    }

    public boolean isBankrupt() {
        return isBankrupt;
    }
}
