package gr.cleavest.monopoly.gamestate;
import gr.cleavest.monopoly.component.Component;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Cleavest on 1/3/2025
 */

public abstract class Container {
    protected ContainerController containerController;
    public abstract void init();
    public abstract void update();
    public abstract void draw(Graphics2D graphics2D);
    private final List<Component> components;
    private final List<Component> secondComponents;
    private BlockingQueue<Runnable> updateQueue = new LinkedBlockingQueue();
    public Container(ContainerController containerController){
        this.containerController = containerController;
        this.components = new ArrayList<>();
        this.secondComponents = new ArrayList<>();
    }
    public void drawComponent(Graphics2D graphics2D){
        components.forEach(c -> {
            if (!c.isHidden()) {
                c.draw(graphics2D);
            }
        });
        secondComponents.forEach(c -> {
            if (!c.isHidden()) {
                c.draw(graphics2D);
            }
        });
    }
    public void addComponent(Component component){
        components.add(component);
    }

    public void addSecondComponent(Component component){
        secondComponents.add(component);
    }

    public void clearSecondComponents(){
        secondComponents.clear();
    }

    public void clickComponent(MouseEvent e){
        components.forEach(component -> {
            try {
                component.click(e);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });
        secondComponents.forEach(component -> {
            try {
                component.click(e);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
    public void release(){
        components.clear();
    }

    public void updateHover(MouseEvent e) {
        components.forEach(component -> {
            component.updateHoverState(e);
        });

        secondComponents.forEach(component -> component.updateHoverState(e));
    }

    public void addUpdateQueue(Runnable r){
        updateQueue.add(r);
    }

    public void updateQueue() throws InterruptedException {
        while (!updateQueue.isEmpty()) {
            Runnable task = updateQueue.take();
            task.run();
        }
    }


}