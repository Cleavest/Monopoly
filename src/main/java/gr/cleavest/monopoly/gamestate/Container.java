package gr.cleavest.monopoly.gamestate;
import gr.cleavest.monopoly.component.Component;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
/**
 * @author Cleavest on 1/3/2025
 */

public abstract class Container {
    protected ContainerController containerController;
    public abstract void init();
    public abstract void update();
    public abstract void draw(Graphics2D graphics2D);
    private final List<Component> components;
    public Container(ContainerController containerController){
        this.containerController = containerController;
        this.components = new ArrayList<>();
    }
    public void drawComponent(Graphics2D graphics2D){
        components.forEach(c -> c.draw(graphics2D));
    }
    public void addComponent(Component component){
        components.add(component);
    }
    public void clickComponent(MouseEvent e){
        components.forEach(component -> component.click(e));
    }
    public void release(){
        components.clear();
    }

    public void updateHover(MouseEvent e) {
        components.forEach(component -> {
            component.updateHoverState(e);
        });
    }


}