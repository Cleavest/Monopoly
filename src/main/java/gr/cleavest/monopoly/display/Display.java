package gr.cleavest.monopoly.display;

import gr.cleavest.monopoly.gamestate.ContainerController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * @author Cleavest on 1/3/2025
 */
public class Display extends JPanel implements Runnable, MouseListener,MouseMotionListener {
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    private Thread thread;
    private boolean running;
    private int FPS = 60;
    private int currentFPS = 0;
    private final boolean debug = true;
    private ContainerController containerController;
    public Display() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        addMouseListener(this);
        addMouseMotionListener(this);

        thread = new Thread(this);
        thread.start();

    }
//    public void addNotify(){
//        super.addNotify();
//        if (thread == null){
//            thread = new Thread(this);
//            thread.start();
//        }
//    }


    public void init() {
        running = true;
        this.containerController = new ContainerController(this);
    }
    @Override
    public void run() {
        init();
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;
        while (running) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += currentTime - lastTime;
            lastTime = currentTime;
            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }
            if (timer >= 1000000000) {
                currentFPS = drawCount;
                drawCount = 0;
                timer = 0;
            }
        }
    }
    private void update() {
        containerController.update();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }
    @Override
    public void mousePressed(MouseEvent e) {
        containerController.click(e);
    }
    @Override
    public void mouseReleased(MouseEvent e) {
    }
    @Override
    public void mouseEntered(MouseEvent e) {
    }
    @Override
    public void mouseExited(MouseEvent e) {
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (!running) return;

        Graphics2D g2 = (Graphics2D) g;

        if (containerController == null) return;

        containerController.draw(g2);

        if (debug) {
            g2.drawString("FPS: " + currentFPS, 1280-50, 15);
        }
        g2.dispose();
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (containerController == null) return;

        containerController.updateHover(e);
    }
}