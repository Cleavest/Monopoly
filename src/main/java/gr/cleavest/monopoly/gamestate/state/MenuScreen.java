package gr.cleavest.monopoly.gamestate.state;

/**
 * @author Cleavest on 2/3/2025
 */
import gr.cleavest.monopoly.component.Button;
import gr.cleavest.monopoly.display.Display;
import gr.cleavest.monopoly.gamestate.Container;
import gr.cleavest.monopoly.gamestate.ContainerController;

import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * Παράδειγμα μιας οθόνης μενού με όμορφα κουμπιά
 */
public class MenuScreen extends Container {

    public MenuScreen(ContainerController containerController) {
        super(containerController);

        // Δημιουργία κουμπιών με διαφορετικά στυλ

        // Κουμπί παιχνιδιού με πράσινο χρώμα
        Button playButton = new Button("Νέο Παιχνίδι", 100, 200, 250, 50);
        playButton.addHandler(button -> {
            containerController.setContainer(ContainerController.GAME);
        });

        // Κουμπί επιλογών με μπλε χρώμα
        Button optionsButton = new Button("Επιλογές", 100, 270, 250, 50);
        optionsButton.addHandler(button -> {
            containerController.setContainer(2);
        });

        // Κουμπί ρεκόρ με στυλ Monopoly
        Button recordsButton = new Button("Ρεκόρ", 100, 340, 250, 50);
        recordsButton.addHandler(button -> {
            containerController.setContainer(3);
        });

        // Κουμπί εξόδου με κόκκινο χρώμα
        Button exitButton = new Button("Έξοδος", 100, 410, 250, 50);
        exitButton.addHandler(button -> {
            System.out.println("Έξοδος από το παιχνίδι!");
            System.exit(0);
        });

        // Προσθήκη των κουμπιών στο container
        addComponent(playButton);
        addComponent(optionsButton);
        addComponent(recordsButton);
        addComponent(exitButton);
    }

    @Override
    public void init() {

    }

    @Override
    public void update() {
        // Οποιαδήποτε λογική ενημέρωσης
    }

    @Override
    public void draw(Graphics2D g) {

        // Σχεδιασμός του φόντου
        g.setColor(new Color(240, 240, 240));
        g.fillRect(0, 0, Display.WIDTH, Display.HEIGHT);

        g.setColor(Color.GRAY);
        g.fillRect(90, 190, 270, 280);

        // Σχεδιασμός του τίτλου
        g.setColor(new Color(213, 39, 53)); // Χρώμα Monopoly
        g.setFont(new Font("Impact", Font.BOLD, 48));
        FontMetrics fm = g.getFontMetrics();
        String title = "Monopoly";
        int titleX = (Display.WIDTH - fm.stringWidth(title)) / 2;
        g.drawString(title, titleX, 120);

        // Σχεδιασμός μιας διακοσμητικής γραμμής
        g.setColor(new Color(50, 50, 50));
        g.setStroke(new BasicStroke(3));
        g.drawLine(100, 150, Display.WIDTH - 100, 150);
        g.setColor(Color.black);
    }
}