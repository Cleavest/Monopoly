package gr.cleavest.monopoly.gamestate.state;

import gr.cleavest.monopoly.component.Button;
import gr.cleavest.monopoly.game.field.Field;
import gr.cleavest.monopoly.game.field.FieldController;
import gr.cleavest.monopoly.game.field.category.*;
import gr.cleavest.monopoly.game.player.Player;
import gr.cleavest.monopoly.gamestate.Container;
import gr.cleavest.monopoly.gamestate.ContainerController;
import gr.cleavest.monopoly.utils.GraphicsUtil;
import gr.cleavest.monopoly.utils.Reference;
import gr.cleavest.monopoly.utils.Square;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.function.Consumer;

/**
 * @author Cleavest on 1/3/2025
 */
public class Game extends Container {

    private final FieldController fieldController;

    private final int SQUARE_HEIGHT = 100;
    private final int SQUARE_WIDTH = 55;

    private final int CORNER_SIZE = SQUARE_HEIGHT;

    private final int startDrawingX = 0;
    private final int startDrawingY = 0;

    private List<Player> players = new ArrayList<>();
    private Square[] squares;
    private List<Runnable> updateQueue = new ArrayList<>();

    int[][] playerOffsets = {
            {5, 5},   // Παίκτης 1: (x + 5, y + 5)
            {5, 25},  // Παίκτης 2: (x + 25, y + 5)
            {25, 5},  // Παίκτης 3: (x + 5, y + 25)
            {25, 25}  // Παίκτης 4: (x + 25, y + 25)
    };

    private int firstDice = -1;
    private int secondDice = -1;

    private int nextPlayer;


    public Game(ContainerController containerController) {
        super(containerController);
        this.fieldController = new FieldController();
        this.nextPlayer = 0;

        squares = new Square[fieldController.getFields().length];

        calcSquares();

        addComponent(new Button("Back", 1280-100,720-60 , 80,40).addHandler(button -> {
            containerController.setContainer(ContainerController.MENU);
        }));

        Player first = new Player(1, Color.YELLOW);
        Player second = new Player(2, Color.GREEN);
        Player second2 = new Player(4, Color.RED);
        players.add(first);
        players.add(second);
        players.add(second2);

        addComponent(new Button("Dice", 200,200 , 80,40).addHandler(button -> {
            firstDice = throwDie();
            secondDice = throwDie();

            updateQueue.add(() -> {

                Player player = players.get(nextPlayer);

                player.move(firstDice + secondDice);

                Field field = fieldController.getFields()[nextPlayer];
                field.stay(player);

                nextPlayer = (nextPlayer + 1) % players.size();

            });
        }));
    }

    private int throwDie()
    {
        return (int)(Math.random() * 6) + 1;
    }

    private int getDrawingX(int value) {
        return startDrawingX + value;
    }
    private int getDrawingY(int value) {
        return startDrawingY + value;
    }

    private void calcSquares(){
        int dx = Reference.BOARD_SIZE - CORNER_SIZE;
        int dy = Reference.BOARD_SIZE - CORNER_SIZE;

        for (int i = 0; i < fieldController.getFields().length; i++){

            Field field = fieldController.getFields()[i];

            squares[i] = new Square(dx,dy,field.getWidth(),field.getHeight());

            if (i == 39) continue;

            int orientation = (i / 10) % 4;

            if (orientation == 0){
                dx -= fieldController.getFields()[i + 1].getWidth();
                dy = Reference.BOARD_SIZE - CORNER_SIZE;
            } else if (orientation == 1){
                dx = 0;
                dy -= fieldController.getFields()[i + 1].getWidth();
            } else if (orientation == 2){
                dx += fieldController.getFields()[i].getWidth();
                dy = 0;
            } else {
                dx = Reference.BOARD_SIZE - CORNER_SIZE;
                dy += fieldController.getFields()[i].getWidth();
            }
        }
    }

    @Override
    public void init() {

    }

    @Override
    public void update() {
        updateQueue.forEach(Runnable::run);

        updateQueue.clear();
    }

    @Override
    public void draw(Graphics2D g2) {

        g2.setColor(new Color(223, 237, 225)); // Ανοιχτό πράσινο
        g2.fillRect(0, 0, Reference.BOARD_SIZE, Reference.BOARD_SIZE);

        for (int i = 0; i < squares.length; i++){

            Square square = squares[i];
            int dx = square.getX();
            int dy = square.getY();

            int orientation = (i / 10) % 4;

            Field field = fieldController.getFields()[i];

            int finalI = i;
            rotate(g2, dx, dy, square, orientation, () -> {
                field.draw(g2,0,0);
                field.drawBorder(g2,0,0);

                int[] playersNumber = new int[squares.length];

                for (Player player : players) {
                    if (player.getPosition() == finalI) {
                        player.draw(g2,playerOffsets[playersNumber[player.getPosition()]][0],playerOffsets[ playersNumber[player.getPosition()]][1],square.getWidth(),square.getHeight());
                    }
                    playersNumber[player.getPosition()]++;
                }
            });

            drawDice(g2,400,400);
            drawAvatarPlayer(g2, Reference.BOARD_SIZE + 20, 20);
        }
    }

    private void rotate(Graphics2D g2,int dx, int dy,Square square, int orientation, Runnable runnable) {
        AffineTransform oldTransform = g2.getTransform();

        switch (orientation) {
            case 0:
                g2.translate(dx, dy);
                g2.rotate(Math.toRadians(0));
                runnable.run();
                break;
            case 1:
                g2.translate(dx + square.getHeight(), dy);
                g2.rotate(Math.toRadians(90));
                runnable.run();
                break;
            case 2:
                g2.translate(dx + square.getWidth(), dy + square.getHeight());
                g2.rotate(Math.toRadians(180));
                runnable.run();
                break;
            case 3:
                g2.translate(dx, dy + square.getWidth());
                g2.rotate(Math.toRadians(270));
                runnable.run();
                break;
            default:
                throw new IllegalArgumentException("Η γωνία πρέπει να είναι 0, 90, 180 ή 270 μοίρες");
        }

        g2.setTransform(oldTransform);
    }

    private void drawDice(Graphics2D g2, int x, int y) {
        int diceSize = 30;
        int split = 10;

        int width = diceSize * 2 + split * 3;
        int height = diceSize + split * 2;

        g2.drawRect(x,y,width,height);

        g2.drawRect(x + split, y + split, diceSize, diceSize);
        g2.drawString(firstDice+"",x + split + diceSize / 2,y + split + diceSize / 2);
        g2.drawRect(x + split * 2 + diceSize, y + split, diceSize, diceSize);
        g2.drawString(secondDice+"",x + split * 2 + diceSize + diceSize/2,y + split + diceSize / 2);
    }

    private void drawAvatarPlayer(Graphics2D g2, int x, int y) {
        int space = 20;

        int width = 75;
        int height = 75;

        AffineTransform originalTransform = g2.getTransform();
        Stroke originalStroke = g2.getStroke();

        g2.setStroke(new BasicStroke(2));

        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);


            if (nextPlayer == i) {
                g2.setColor(player.getColor());
                g2.fillRect(x ,y,width,height);
            }
            g2.setColor(player.getColor());
            g2.drawRect(x ,y,width,height);

            g2.setColor(Color.BLACK);
            g2.drawString( player.getBalance() + "$", x + space, y + space);

            x += space + width;
        }

        g2.setTransform(originalTransform);
        g2.setStroke(originalStroke);
    }
}
