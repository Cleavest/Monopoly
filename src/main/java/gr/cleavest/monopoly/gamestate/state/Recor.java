package gr.cleavest.monopoly.gamestate.state;

import gr.cleavest.monopoly.gamestate.Container;
import gr.cleavest.monopoly.gamestate.ContainerController;
import gr.cleavest.monopoly.utils.GraphicsUtil;

import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * @author Cleavest on 2/3/2025
 */
public class Recor extends Container {

    public Recor(ContainerController containerController) {
        super(containerController);
    }

    @Override
    public void init() {

    }

    @Override
    public void update() {

    }

    private  int bandSize = 15;;
    @Override
    public void draw(Graphics2D g2d) {
// Παράμετροι ορθογωνίου
        int x = 50;
        int y = 50;
        int width = 55;
        int height = 100;

        // Σχεδιάζουμε ένα ορθογώνιο αναφοράς (χωρίς περιστροφή) για σύγκριση
        g2d.setColor(Color.BLUE);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRect(x, y, width, height);
        GraphicsUtil.fillCorner(g2d,x, y , width, bandSize, Color.GREEN);

        // Σχεδιάζουμε το περιστραμμένο ορθογώνιο με το x,y να ευθυγραμμίζεται ως η αριστερή πλευρά
        g2d.setColor(Color.RED);
        drawRotatedRect(g2d, x, y, width, height, 270);
    }

    private void drawRotatedRect(Graphics2D g2d, int x, int y, int width, int height, int angle) {
        // Αποθηκεύουμε το αρχικό transform ώστε να το επαναφέρουμε στο τέλος
        AffineTransform oldTransform = g2d.getTransform();

        switch (angle) {
            case 0:
                // Καμία περιστροφή: σχεδιάζουμε κανονικά
                g2d.drawRect(x, y, width, height);
                break;
            case 90:
                //ΑΡΙΣΤΕΡΑ
                // Περιστροφή 90° (αντιστροφή: counterclockwise)
                // Η περιστροφή κατά 90° μετατρέπει το bounding box του ορθογωνίου
                // σε τέτοιο που το κάτω αριστερό σημείο (0, height) γίνεται το νέο (0,0).
                // Για να έχουμε το αριστερό (νέο) όριο στο (x, y) μετατοπίζουμε κατά (x + height, y).
                g2d.translate(x + height, y);
                g2d.rotate(Math.toRadians(90));
                g2d.drawRect(0, 0, width, height);
                GraphicsUtil.fillCorner(g2d,0, 0 , width, bandSize, Color.YELLOW);
                break;
            case 180:
                //PANW
                // Περιστροφή 180°:
                // Η περιστροφή 180° μετατρέπει το (0,0) σε (-width, -height).
                // Για να ευθυγραμμίσουμε το νέο (0,0) στο (x, y), μετατοπίζουμε κατά (x + width, y + height).
                g2d.translate(x + width, y + height);
                g2d.rotate(Math.toRadians(180));
                g2d.drawRect(0, 0, width, height);
                GraphicsUtil.fillCorner(g2d,0, 0 , width, bandSize, Color.YELLOW);
                break;
            case 270:
                //DEKSIA
                g2d.translate(x, y + width);
                g2d.rotate(Math.toRadians(270));
                g2d.drawRect(0, 0, width, height);
                GraphicsUtil.fillCorner(g2d,0, 0 , width, bandSize, Color.YELLOW);
                break;
            default:
                throw new IllegalArgumentException("Η γωνία πρέπει να είναι 0, 90, 180 ή 270 μοίρες");
        }

        // Επαναφέρουμε το αρχικό transform
        g2d.setTransform(oldTransform);
    }
}
