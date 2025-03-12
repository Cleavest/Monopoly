package gr.cleavest.monopoly.game.player;

import gr.cleavest.monopoly.utils.Reference;

import java.awt.*;

/**
 * @author Cleavest on 2/3/2025
 */
public class Player {

    private final Color color;
    private int positionId;
    private int balance;
    private int jailCard;

    public Player(Color color) {
        this.positionId = 0;
        this.color = color;
        this.balance = 1000;
        this.jailCard = 0;
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

//        if (position == TO_JAIL) {
//            position = IN_JAIL;
//            toJail();
//        }
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
    }

    public Color getColor() {
        return color;
    }
}
