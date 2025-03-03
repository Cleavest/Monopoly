package gr.cleavest.monopoly.gamestate;

import gr.cleavest.monopoly.gamestate.state.*;
import gr.cleavest.monopoly.gamestate.state.Menu;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Cleavest on 1/3/2025
 */
public class ContainerController {

    public static final int MENU = 0;
    public static final int GAME = 1;

    private final List<Container> states;
    private int currentState;

    public ContainerController() {
        states = new ArrayList<>();
        add(new MenuScreen(this));
        add(new Game(this));
        add(new Test(this));
        add(new Recor(this));
        setContainer(MENU);
    }

    public void add(Container container) {
        states.add(container);
    }

    public void setContainer(int state) {
        currentState = state;
    }

    public void update() {
        states.get(currentState).update();
    }

    public void draw(Graphics2D g) {
        states.get(currentState).draw(g);
        states.get(currentState).drawComponent(g);
    }

    public void click(MouseEvent e) {
        states.get(currentState).clickComponent(e);
    }

    public void updateHover(MouseEvent e) {
        states.get(currentState).updateHover(e);
    }
}
