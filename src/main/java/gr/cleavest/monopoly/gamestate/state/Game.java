package gr.cleavest.monopoly.gamestate.state;

import gr.cleavest.monopoly.component.Button;
import gr.cleavest.monopoly.component.Component;
import gr.cleavest.monopoly.component.TestButton;
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
import java.util.List;
import java.util.stream.Collectors;

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

    private boolean isGameOver;

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
        this.isGameOver = false;

        squares = new Square[fieldController.getFields().length];

        calcSquares();

        addComponent(new Button("Back", 1280-100,720-60 , 80,40).addHandler(button -> {
            containerController.setContainer(ContainerController.MENU);
        }).setToggled(true));

        // Player first = new Player( Color.YELLOW);
        // Player second = new Player( Color.GREEN);
        // Player second2 = new Player( Color.RED);
        // players.add(first);
        // players.add(second);
        // players.add(second2);

        addComponent(new Button("Dice", 250,450 , 95,40).addHandler(button -> {
            firstDice = throwDie();
            secondDice = throwDie();

            addUpdateQueue(() -> {

                Player player = players.get(nextPlayer);

                if (player.isJail()) {
                    if (player.getRoundsInJail() >= 3) {
                        // Αυτόματος έξοδος μετά από 3 γύρους
                        player.exitJail();
                        player.move(firstDice + secondDice);
                    } else {
                        // Αν ρίξει διπλό, βγαίνει από τη φυλακή
                        if (firstDice == secondDice) {
                            player.exitJail();
                            player.move(firstDice + secondDice);
                        } else {
                            player.addRoundInJail();
                        }
                    }
                } else {
                    player.move(firstDice + secondDice);
                }

                stay(player);


                if (getActivePlayers().size() == 1 && !isGameOver) {
                    isGameOver = true;
                    button.setHidden(true);
                }

                nextPlayer = (nextPlayer + 1) % getActivePlayers().size();

            });
        }).setToggled(!isGameOver));

        System.out.println(isGameOver + " is game over");


        addComponent(new Button("Next", 250,250 , 95,40).addHandler(button -> {
            containerController.setContainer(ContainerController.MENU);
        }).setToggled(isGameOver));

//        addComponent(new TestButton("test" ,250, 460, 120, 40).addHandler(button -> {
//            System.out.println("test");
//        }));
    }

    public List<Player> getActivePlayers() {
        return players.stream().filter(player -> !player.isBankrupt()).collect(Collectors.toList());
    }

    public void setupPlayers(int value) {
        players.clear();
        Color[] colors = {Color.YELLOW, Color.GREEN, Color.RED, Color.BLUE};
        for (int i = 0; i < value; i++) {
            players.add(new Player(colors[i], fieldController));
        }
    }

    public void stay(Player player) {
        Field field = fieldController.getFields()[player.getPosition()];
        this.clearSecondComponents();
        field.stay(player, this, fieldController);
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

            //drawDice(g2,400,400);

            GraphicsUtil.drawSmoothDice(g2, 250, 400, 40, firstDice);
            GraphicsUtil.drawSmoothDice(g2, 300, 400, 40, secondDice);
            drawAvatarPlayer(g2, Reference.startFieldInteract, 20, 250, 80);


            //drawPlayerCard(g2, players.get(0), Reference.startFieldInteract, 400, true);
            //drawPlayerCard(g2, players.get(1), Reference.startFieldInteract, 400 + 150, false);

//            int lastPlayerPosition = nextPlayer - 1;
//            if (lastPlayerPosition == -1) {
//                lastPlayerPosition = 0;
//            }
//
//            Player player = players.get(lastPlayerPosition);
//            int playerPosition = player.getPosition();

            //Field proper = fieldController.getFields()[playerPosition];
//            if (proper instanceof PropertyField){
//                ((PropertyField) proper).getComponents().forEach(component -> {
//                    component.draw(g2);
//                });
//            }
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

//    private void drawAvatarPlayer(Graphics2D g2, int x, int y) {
//        int space = 20;
//
//        int width = 75;
//        //int height = 75;
//
//        AffineTransform originalTransform = g2.getTransform();
//        Stroke originalStroke = g2.getStroke();
//
//        g2.setStroke(new BasicStroke(2));
//
//        for (int i = 0; i < players.size(); i++) {
//            Player player = players.get(i);
//
//
//            if (nextPlayer == i) {
//                g2.setColor(player.getColor());
//                g2.fillRect(x ,y,width,Reference.AVATAR_HEIGHT);
//            }
//            g2.setColor(player.getColor());
//            g2.drawRect(x ,y,width,Reference.AVATAR_HEIGHT);
//
//            g2.setColor(Color.BLACK);
//            g2.drawString( player.getBalance() + "$", x + space, y + space);
//
//            x += space + width;
//        }
//
//        g2.setTransform(originalTransform);
//        g2.setStroke(originalStroke);
//    }

    private void drawAvatarPlayer(Graphics2D g2, int x, int y, int width, int height) {
        int space = 20;

        //g2.setStroke(new BasicStroke(2));

        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);

            GraphicsUtil.drawPlayerCard(g2, player, x, y,width,height ,nextPlayer == i);

            y += 80 + space;
        }

        g2.setColor(Color.BLACK);
    }

    public List<Player> getPlayers() {
        return players;
    }

    public int getLastDiceRoll() {
        return firstDice + secondDice;
    }
}
