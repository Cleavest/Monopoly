package gr.cleavest.monopoly.gamestate.state;

import gr.cleavest.monopoly.component.Button;
import gr.cleavest.monopoly.gamestate.Container;
import gr.cleavest.monopoly.gamestate.ContainerController;

import java.awt.*;

/**
 * @author Cleavest on 1/3/2025
 */
public class Menu extends Container {
    public Menu(ContainerController gsm) {
        super(gsm);
        addComponent(new Button("Next",40,40,120,40).addHandler(button -> {
            containerController.setContainer(2);
        }));
    }

    @Override
    public void init() {

    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics2D g) {
        g.drawString("test ", 20 , 20);
    }
}
