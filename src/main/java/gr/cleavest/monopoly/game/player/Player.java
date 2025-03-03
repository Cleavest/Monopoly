package gr.cleavest.monopoly.game.player;

import gr.cleavest.monopoly.utils.Reference;

import java.awt.*;

/**
 * @author Cleavest on 2/3/2025
 */
public class Player {

    private final Color color;
    private int[] inventory = new int[40];
    private int positionId;
    private int balance;

    public Player(int positionId,Color color) {
        this.positionId = 0;
        this.color = color;
        this.balance = 0;
    }

    public void draw(Graphics2D g , int x , int y, int width, int height) {

        g.setColor(color);
        g.fillOval(x , y , 20 , 20);
        g.setColor(Color.BLACK);
        //g.fillRect(x + 1, y + 1, width - 1, height - 1);
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

    public void addBalance(int amount) {
        balance += amount;
    }

    public Color getColor() {
        return color;
    }
}
