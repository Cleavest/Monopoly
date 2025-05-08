package gr.cleavest.monopoly.gamestate.state;

import gr.cleavest.monopoly.component.Background;
import gr.cleavest.monopoly.component.Button;
import gr.cleavest.monopoly.component.Label;
import gr.cleavest.monopoly.gamestate.Container;
import gr.cleavest.monopoly.gamestate.ContainerController;
import gr.cleavest.monopoly.utils.Reference;

import java.awt.*;

/**
 * @author Cleavest on 5/7/2025
 */
public class CreateGame extends Container {

    private int playerCount;

    private final int menuWidth = 240;
    private final int menuHeight = 400;
    private int centreX;
    private int centreY;

    public CreateGame(ContainerController containerController) {
        super(containerController);
        this.playerCount = 2;

        int spaceX = 20;
        int spaceY = 40;
        int buttonHeight = 30;

        calcPosition();

        int y = centreY - menuHeight / 2 + 20;
        System.out.println(y);

        Label text = new Label(centreX - (menuWidth / 2) + spaceX, y,menuWidth - spaceX * 2, buttonHeight,"Player count " + playerCount, Color.WHITE, Color.RED);
        addComponent(text);

        y += spaceY;

        Button addBtn = new Button("+1",centreX - (menuWidth / 2) + spaceX, y, menuWidth - spaceX * 2,buttonHeight)
                .addHandler(event -> {
                    if (playerCount < Reference.MAX_PLAYERS) {
                        playerCount++;
                        text.setText("Player count " + playerCount);
                    }
                });
        y += spaceY;
        Button subBtn = new Button("-1",centreX - (menuWidth / 2) + spaceX, y, menuWidth - spaceX * 2,buttonHeight)
                .addHandler(event -> {
                    if (playerCount > 2) {
                        playerCount--;
                        text.setText("Player count " + playerCount);
                    }
                });
        y += spaceY;
        Button startGame = new Button("start Game",centreX - (menuWidth / 2) + spaceX, y, menuWidth - spaceX * 2,buttonHeight);
        startGame.addHandler(event -> {
            ((Game)containerController.getContainer(ContainerController.GAME)).setupPlayers(playerCount);
            containerController.setContainer(ContainerController.GAME);
        });
        addComponent(addBtn);
        addComponent(subBtn);
        addComponent(startGame);
    }

    @Override
    public void init() {

    }

    @Override
    public void update() {

    }

    private void calcPosition() {

        centreX = containerController.getWidth() / 2;
        centreY = containerController.getHeight() / 2;



        System.out.println("startX: " + centreX + " startY: " + centreY);
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.GRAY);

        g.fillRect(centreX - menuWidth / 2, centreY - menuHeight / 2, menuWidth, menuHeight);
    }
}
