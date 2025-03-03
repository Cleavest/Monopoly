package gr.cleavest.monopoly.gamestate.state;

import gr.cleavest.monopoly.game.field.Field;
import gr.cleavest.monopoly.game.field.FieldController;
import gr.cleavest.monopoly.game.field.category.*;
import gr.cleavest.monopoly.gamestate.Container;
import gr.cleavest.monopoly.gamestate.ContainerController;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Map;

/**
 * @author Cleavest on 2/3/2025
 */
public class Test extends Container {

    private final FieldController fieldController;

    public Test(ContainerController containerController) {
        super(containerController);
        this.fieldController = new FieldController();
    }

    @Override
    public void init() {

    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics2D graphics2D) {
        // Σταθερές για το ταμπλό
        final int CORNER_SIZE = 100;
        final int SQUARE_HEIGHT = CORNER_SIZE;
//        final int SQUARE_WIDTH = (BOARD_SIZE - 2 * CORNER_SIZE) / 9;
        final int SQUARE_WIDTH = 55;
        final int BOARD_SIZE = 2 * CORNER_SIZE + 9 * SQUARE_WIDTH;

        // Αποθήκευση του αρχικού transform και άλλων ιδιοτήτων
        AffineTransform originalTransform = graphics2D.getTransform();
        Font originalFont = graphics2D.getFont();
        Stroke originalStroke = graphics2D.getStroke();

        // Ενεργοποίηση anti-aliasing για καλύτερη ποιότητα
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Φόντο ταμπλό
        graphics2D.setColor(new Color(223, 237, 225)); // Ανοιχτό πράσινο
        graphics2D.fillRect(0, 0, BOARD_SIZE, BOARD_SIZE);

        // Πλαίσιο ταμπλό
        graphics2D.setColor(Color.BLACK);
        graphics2D.setStroke(new BasicStroke(2));
        graphics2D.drawRect(0, 0, BOARD_SIZE - 1, BOARD_SIZE - 1);

        // ----- Γωνιακά τετράγωνα -----
        // GO (Κάτω δεξιά)
        drawCornerSquare(graphics2D, BOARD_SIZE - CORNER_SIZE, BOARD_SIZE - CORNER_SIZE, "GO", 0);
        // JAIL (Κάτω αριστερά)
        drawCornerSquare(graphics2D, 0, BOARD_SIZE - CORNER_SIZE, "JAIL", 10);
        // FREE PARKING (Πάνω αριστερά)
        drawCornerSquare(graphics2D, 0, 0, "FREE PARKING", 20);
        // GO TO JAIL (Πάνω δεξιά)
        drawCornerSquare(graphics2D, BOARD_SIZE - CORNER_SIZE, 0, "GO TO JAIL", 30);

        // ----- Κανονικά τετράγωνα -----

        // Κάτω σειρά (1-9)
        for (int i = 1; i <= 9; i++) {
            int x = BOARD_SIZE - CORNER_SIZE - (i * SQUARE_WIDTH);
            int y = BOARD_SIZE - SQUARE_HEIGHT;
            drawPropertySquare(graphics2D, x, y, i, 0, SQUARE_WIDTH, SQUARE_HEIGHT);
        }

        // Αριστερή στήλη (11-19)
        for (int i = 1; i <= 9; i++) {
            int x = 0;
            int y = BOARD_SIZE - CORNER_SIZE - (i * SQUARE_WIDTH);
            drawPropertySquare(graphics2D, x, y, i + 10, 1, SQUARE_HEIGHT, SQUARE_WIDTH);
        }

        // Πάνω σειρά (21-29)
        for (int i = 1; i <= 9; i++) {
            int x = CORNER_SIZE + ((i - 1) * SQUARE_WIDTH);
            int y = 0;
            drawPropertySquare(graphics2D, x, y, i + 20, 2, SQUARE_WIDTH, SQUARE_HEIGHT);
        }

        // Δεξιά στήλη (31-39)
        for (int i = 1; i <= 9; i++) {
            int x = BOARD_SIZE - SQUARE_HEIGHT;
            int y = CORNER_SIZE + ((i - 1) * SQUARE_WIDTH);
            drawPropertySquare(graphics2D, x, y, i + 30, 3, SQUARE_HEIGHT, SQUARE_WIDTH);
        }

        // ----- Κέντρο ταμπλό -----
        drawCentralArea(graphics2D, CORNER_SIZE, CORNER_SIZE, BOARD_SIZE - 2 * CORNER_SIZE, BOARD_SIZE - 2 * CORNER_SIZE);

        // Επαναφορά του αρχικού transform και άλλων ιδιοτήτων
        graphics2D.setTransform(originalTransform);
        graphics2D.setFont(originalFont);
        graphics2D.setStroke(originalStroke);
    }

    // Μέθοδος για σχεδίαση γωνιακού τετραγώνου
    private void drawCornerSquare(Graphics2D g2d, int x, int y, String name, int position) {
        final int CORNER_SIZE = 100;

        // Περίγραμμα
        g2d.setColor(Color.BLACK);
        g2d.drawRect(x, y, CORNER_SIZE, CORNER_SIZE);

        // Γέμισμα
        g2d.setColor(Color.WHITE);
        g2d.fillRect(x + 1, y + 1, CORNER_SIZE - 2, CORNER_SIZE - 2);

        // Κείμενο
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 14));

        // Ειδικά σχήματα ανάλογα με τη γωνία
        if (position == 0) { // GO
            drawGoSymbol(g2d, x, y, CORNER_SIZE);
        } else if (position == 10) { // JAIL
            drawJailSymbol(g2d, x, y, CORNER_SIZE);
        } else if (position == 20) { // FREE PARKING
            drawParkingSymbol(g2d, x, y, CORNER_SIZE);
        } else if (position == 30) { // GO TO JAIL
            drawGoToJailSymbol(g2d, x, y, CORNER_SIZE);
        }

        // Τίτλος γωνίας
        FontMetrics metrics = g2d.getFontMetrics();
        int textX = x + (CORNER_SIZE - metrics.stringWidth(name)) / 2;
        int textY = y + (CORNER_SIZE - metrics.getHeight()) / 2 + metrics.getAscent();
        g2d.drawString(name, textX, textY);
    }

    // Μέθοδος για σχεδίαση κανονικού τετραγώνου
    private void drawPropertySquare(Graphics2D g2d, int x, int y, int position, int orientation,
                                    int width, int height) {
        Field field = fieldController.getFields()[position];

        // Περίγραμμα
        g2d.setColor(Color.BLACK);
        g2d.drawRect(x, y, width, height);

        // Γέμισμα
        g2d.setColor(Color.WHITE);
        g2d.fillRect(x + 1, y + 1, width - 2, height - 2);

        // Χρωματιστή λωρίδα για ιδιοκτησίες
        if (field instanceof PropertyField) {
            drawColorBand(g2d, x, y, width, height, orientation, ((PropertyField) field).getColor());
        } else if (field instanceof RailRoadField) {
            drawColorBand(g2d, x, y, width, height, orientation, Color.LIGHT_GRAY);
        } else if (field instanceof UtilityField) {
            drawColorBand(g2d, x, y, width, height, orientation, new Color(193, 229, 196));
        }

        // Όνομα του τετραγώνου
        String name = getPropertyName(position);
        drawRotatedText(g2d, name, x, y, width, height, orientation);

        // Ειδικά σύμβολα για ειδικούς τύπους τετραγώνων
        if (field instanceof ChanceField) {
            drawRotatedSymbol(g2d, "?", x, y, width, height, orientation);
        } else if (field instanceof CommunityChestField) {
            drawRotatedSymbol(g2d, "CC", x, y, width, height, orientation);
        } else if (field instanceof TaxField) {
            drawRotatedSymbol(g2d, "$", x, y, width, height, orientation);
        } else if (field instanceof RailRoadField) {
            drawRotatedSymbol(g2d, "RR", x, y, width, height, orientation);
        } else if (field instanceof UtilityField) {
            String symbol = (position == 12) ? "⚡" : "💧";
            drawRotatedSymbol(g2d, symbol, x, y, width, height, orientation);
        }
    }

    // Μέθοδος για σχεδίαση χρωματιστής λωρίδας
    private void drawColorBand(Graphics2D g2d, int x, int y, int width, int height, int orientation, Color color) {
        int bandSize = 15;
        g2d.setColor(color);

        switch (orientation) {
            case 0: // Κάτω σειρά
                g2d.fillRect(x, y, width, bandSize);
                g2d.setColor(Color.BLACK);
                g2d.drawRect(x, y, width, bandSize);
                break;
            case 1: // Αριστερή στήλη
                g2d.fillRect(x + width - bandSize, y, bandSize, height);
                g2d.setColor(Color.BLACK);
                g2d.drawRect(x + width - bandSize, y, bandSize, height);
                break;
            case 2: // Πάνω σειρά
                g2d.fillRect(x, y + height - bandSize, width, bandSize);
                g2d.setColor(Color.BLACK);
                g2d.drawRect(x, y + height - bandSize, width, bandSize);
                break;
            case 3: // Δεξιά στήλη
                g2d.fillRect(x, y, bandSize, height);
                g2d.setColor(Color.BLACK);
                g2d.drawRect(x, y, bandSize, height);
                break;
        }
    }

    // Μέθοδος για περιστρεφόμενο κείμενο
    private void drawRotatedText(Graphics2D g2d, String text, int x, int y, int width, int height, int orientation) {
        AffineTransform originalTransform = g2d.getTransform();
        g2d.setFont(new Font("Arial", Font.PLAIN, 9));
        FontMetrics metrics = g2d.getFontMetrics();

        switch (orientation) {
            case 0: // Κάτω σειρά
                g2d.drawString(text, x + (width - metrics.stringWidth(text)) / 2, y + 25);
                break;
            case 1: // Αριστερή στήλη
                g2d.translate(x + width, y);
                g2d.rotate(Math.PI / 2);
                g2d.drawString(text, (height - metrics.stringWidth(text)) / 2, -8);
                break;
            case 2: // Πάνω σειρά
                g2d.translate(x + width, y + height);
                g2d.rotate(Math.PI);
                g2d.drawString(text, (width - metrics.stringWidth(text)) / 2, -height + 25);
                break;
            case 3: // Δεξιά στήλη
                g2d.translate(x, y + height);
                g2d.rotate(-Math.PI / 2);
                g2d.drawString(text, (height - metrics.stringWidth(text)) / 2, -8);
                break;
        }

        g2d.setTransform(originalTransform);
    }

    // Μέθοδος για περιστρεφόμενα σύμβολα
    private void drawRotatedSymbol(Graphics2D g2d, String symbol, int x, int y, int width, int height, int orientation) {
        AffineTransform originalTransform = g2d.getTransform();
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        FontMetrics metrics = g2d.getFontMetrics();

        switch (orientation) {
            case 0: // Κάτω σειρά
                g2d.drawString(symbol, x + (width - metrics.stringWidth(symbol)) / 2, y + height - 10);
                break;
            case 1: // Αριστερή στήλη
                g2d.translate(x + width, y);
                g2d.rotate(Math.PI / 2);
                g2d.drawString(symbol, (height - metrics.stringWidth(symbol)) / 2, -width + 20);
                break;
            case 2: // Πάνω σειρά
                g2d.translate(x + width, y + height);
                g2d.rotate(Math.PI);
                g2d.drawString(symbol, (width - metrics.stringWidth(symbol)) / 2, -10);
                break;
            case 3: // Δεξιά στήλη
                g2d.translate(x, y + height);
                g2d.rotate(-Math.PI / 2);
                g2d.drawString(symbol, (height - metrics.stringWidth(symbol)) / 2, 20);
                break;
        }

        g2d.setTransform(originalTransform);
    }

    // Μέθοδος για σχεδίαση του συμβόλου GO
    private void drawGoSymbol(Graphics2D g2d, int x, int y, int size) {
        int arrowSize = 40;
        int centerX = x + size / 2;
        int centerY = y + size / 2;

        g2d.setColor(Color.RED);
        g2d.setStroke(new BasicStroke(3));

        // Βέλος
        g2d.drawLine(centerX - 20, centerY, centerX + 20, centerY);
        g2d.drawLine(centerX + 20, centerY, centerX + 10, centerY - 10);
        g2d.drawLine(centerX + 20, centerY, centerX + 10, centerY + 10);

        // Collect $200
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 10));
        g2d.drawString("COLLECT $200", x + 15, y + size - 15);
    }

    // Μέθοδος για σχεδίαση του συμβόλου JAIL
    private void drawJailSymbol(Graphics2D g2d, int x, int y, int size) {
        int cellSize = 60;
        int cellX = x + (size - cellSize) / 2;
        int cellY = y + (size - cellSize) / 2;

        // Κελί φυλακής
        g2d.setColor(new Color(240, 240, 240));
        g2d.fillRect(cellX, cellY, cellSize, cellSize);

        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRect(cellX, cellY, cellSize, cellSize);

        // Κάγκελα
        g2d.setColor(Color.DARK_GRAY);
        int spacing = 10;
        for (int i = 0; i <= cellSize; i += spacing) {
            g2d.drawLine(cellX + i, cellY, cellX + i, cellY + cellSize);
        }
    }

    // Μέθοδος για σχεδίαση του συμβόλου FREE PARKING
    private void drawParkingSymbol(Graphics2D g2d, int x, int y, int size) {
        int symbolSize = 50;
        int symbolX = x + (size - symbolSize) / 2;
        int symbolY = y + (size - symbolSize) / 2;

        // Κύκλος parking
        g2d.setColor(Color.RED);
        g2d.fillOval(symbolX, symbolY, symbolSize, symbolSize);

        g2d.setColor(Color.BLACK);
        g2d.drawOval(symbolX, symbolY, symbolSize, symbolSize);

        // Γράμμα P
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 30));
        FontMetrics metrics = g2d.getFontMetrics();
        g2d.drawString("P", symbolX + (symbolSize - metrics.stringWidth("P")) / 2,
                symbolY + (symbolSize + metrics.getAscent() - metrics.getDescent()) / 2);
    }

    // Μέθοδος για σχεδίαση του συμβόλου GO TO JAIL
    private void drawGoToJailSymbol(Graphics2D g2d, int x, int y, int size) {
        int symbolSize = 40;
        int symbolX = x + (size - symbolSize) / 2;
        int symbolY = y + (size - symbolSize) / 2;

        // Αστυνομικό καπέλο
        g2d.setColor(Color.BLUE);
        g2d.fillOval(symbolX, symbolY, symbolSize, symbolSize);

        g2d.setColor(Color.BLUE.darker());
        g2d.fillRect(symbolX - 5, symbolY + 15, symbolSize + 10, 10);

        // Κείμενο
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 10));
        g2d.drawString("GO TO", x + 30, y + size - 25);
        g2d.drawString("JAIL", x + 35, y + size - 15);
    }

    // Μέθοδος για σχεδίαση του κέντρου του ταμπλό
    private void drawCentralArea(Graphics2D g2d, int x, int y, int width, int height) {
        // Λογότυπο MONOPOLY
        g2d.setColor(Color.RED);
        g2d.setFont(new Font("Arial", Font.BOLD, 36));
        FontMetrics metrics = g2d.getFontMetrics();
        String logo = "MONOPOLY";
        int logoX = x + (width - metrics.stringWidth(logo)) / 2;
        int logoY = y + height / 2;
        g2d.drawString(logo, logoX, logoY);

        // Διακοσμητικές γραμμές
        g2d.setStroke(new BasicStroke(3));
        g2d.drawLine(x + 50, logoY + 20, x + width - 50, logoY + 20);
        g2d.drawLine(x + 50, logoY - 30, x + width - 50, logoY - 30);
    }

    // Μέθοδος για λήψη ονόματος ιδιοκτησίας
    private String getPropertyName(int position) {
        switch (position) {
            case 1: return "Mediterranean";
            case 2: return "Community Chest";
            case 3: return "Baltic";
            case 4: return "Income Tax";
            case 5: return "Reading RR";
            case 6: return "Oriental";
            case 7: return "Chance";
            case 8: return "Vermont";
            case 9: return "Connecticut";
            case 11: return "St. Charles";
            case 12: return "Electric Co.";
            case 13: return "States";
            case 14: return "Virginia";
            case 15: return "Pennsylvania RR";
            case 16: return "St. James";
            case 17: return "Community Chest";
            case 18: return "Tennessee";
            case 19: return "New York";
            case 21: return "Kentucky";
            case 22: return "Chance";
            case 23: return "Indiana";
            case 24: return "Illinois";
            case 25: return "B&O RR";
            case 26: return "Atlantic";
            case 27: return "Ventnor";
            case 28: return "Water Works";
            case 29: return "Marvin Gardens";
            case 31: return "Pacific";
            case 32: return "North Carolina";
            case 33: return "Community Chest";
            case 34: return "Pennsylvania";
            case 35: return "Short Line RR";
            case 36: return "Chance";
            case 37: return "Park Place";
            case 38: return "Luxury Tax";
            case 39: return "Boardwalk";
            default: return "";
        }
    }
}
