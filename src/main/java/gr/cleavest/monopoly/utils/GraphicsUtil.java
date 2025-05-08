package gr.cleavest.monopoly.utils;

import gr.cleavest.monopoly.game.player.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Cleavest on 2/3/2025
 */
public class GraphicsUtil {

    public static void drawCorner(Graphics2D g2, int x, int y, int width, int height) {
        g2.setColor(Color.BLACK);
        g2.drawRect(x, y, width, height);

        g2.setColor(Color.WHITE);
        g2.fillRect(x + 1, y + 1, width - 1, height - 1);
    }

    public static void fillCorner(Graphics2D g2, int x, int y, int width, int height, Color color) {
        g2.setColor(color);
        g2.fillRect(x, y, width, height);
        g2.setColor(Color.BLACK);
        g2.drawRect(x, y, width, height);
    }

    /**
     * Ζωγραφίζει ένα κείμενο στο κέντρο ενός τετραγώνου με συγκεκριμένες διαστάσεις.
     * Εάν το κείμενο δεν χωράει στο καθορισμένο πλάτος, διαχωρίζεται με '-'.
     * Εάν υπάρχουν πολλές λέξεις, κάθε λέξη τοποθετείται σε νέα γραμμή με y += 20.
     *
     * @param g2 Το αντικείμενο Graphics2D για το σχεδιασμό
     * @param text Το κείμενο που θα σχεδιαστεί
     * @param x Η συντεταγμένη x της αρχής του τετραγώνου
     * @param y Η συντεταγμένη y της αρχής του τετραγώνου
     * @param width Το πλάτος του τετραγώνου για το κείμενο
     * @param height Το ύψος του τετραγώνου
     * @param font Η γραμματοσειρά που θα χρησιμοποιηθεί
     */
    public static void drawString(Graphics2D g2, String text, int x, int y, int width, int height, Font font) {
        Font currentFont = g2.getFont();
        // Ορισμός της γραμματοσειράς
        g2.setFont(font);

        // Δημιουργία FontMetrics για να υπολογίσουμε τα πλάτη των κειμένων
        FontMetrics fontMetrics = g2.getFontMetrics(font);

        // Διαχωρισμός του κειμένου σε λέξεις
        String[] words = text.split("\\s+");

        // Υπολογισμός του συνολικού ύψους του κειμένου
        int totalTextHeight = words.length * (fontMetrics.getHeight() + 2);

        // Υπολογισμός της αρχικής τιμής του y ώστε το κείμενο να είναι κεντραρισμένο κάθετα
        int currentY = y + (height - totalTextHeight) / 2 + fontMetrics.getAscent();

        if (words.length == 1) {
            // Περίπτωση μιας λέξης
            String word = words[0];
            processAndDrawWordCentered(g2, word, x, currentY, width, fontMetrics);
        } else {
            // Περίπτωση πολλαπλών λέξεων
            for (String word : words) {
                processAndDrawWordCentered(g2, word, x, currentY, width, fontMetrics);
                // Αύξηση του y για την επόμενη λέξη
                currentY += fontMetrics.getHeight() + 2;
            }
        }

        g2.setFont(currentFont);
    }

    /**
     * Βοηθητική μέθοδος που επεξεργάζεται και σχεδιάζει μια λέξη στο κέντρο οριζόντια
     *
     * @param g2 Το αντικείμενο Graphics2D
     * @param word Η λέξη που θα σχεδιαστεί
     * @param x Η συντεταγμένη x του τετραγώνου
     * @param y Η συντεταγμένη y για το κείμενο
     * @param width Το πλάτος του τετραγώνου
     * @param fontMetrics Τα FontMetrics για τους υπολογισμούς πλάτους
     */
    private static void processAndDrawWordCentered(Graphics2D g2, String word, int x, int y, int width, FontMetrics fontMetrics) {
        // Έλεγχος αν η λέξη χωράει στο καθορισμένο πλάτος
        int wordWidth = fontMetrics.stringWidth(word);

        if (wordWidth <= width - 2) {
            // Η λέξη χωράει, υπολογίζουμε το κεντραρισμένο x
            int centeredX = x + (width - wordWidth) / 2;
            g2.drawString(word, centeredX, y);
        } else {
            // Η λέξη δεν χωράει, προσπαθούμε να την χωρίσουμε
            StringBuilder result = new StringBuilder();
            int currentY = y;

            for (int i = 0; i < word.length(); i++) {
                // Προσθέτουμε τον χαρακτήρα στο προσωρινό αποτέλεσμα
                result.append(word.charAt(i));

                // Έλεγχος αν το προσωρινό αποτέλεσμα χωράει ακόμα
                if (fontMetrics.stringWidth(result.toString()) > width) {
                    // Αφαιρούμε τον τελευταίο χαρακτήρα που προσθέσαμε
                    result.deleteCharAt(result.length() - 1);

                    // Προσθέτουμε το '-'
                    result.append('-');

                    // Υπολογίζουμε το κεντραρισμένο x για αυτό το τμήμα
                    int partWidth = fontMetrics.stringWidth(result.toString());
                    int centeredX = x + (width - partWidth) / 2;

                    // Ζωγραφίζουμε το τμήμα κεντραρισμένο
                    g2.drawString(result.toString(), centeredX, currentY);

                    // Δημιουργούμε ένα νέο StringBuilder με τον χαρακτήρα που αφαιρέσαμε
                    result = new StringBuilder();
                    result.append(word.charAt(i));

                    // Αυξάνουμε το y για το επόμενο τμήμα
                    currentY += fontMetrics.getHeight() + 2;
                }
            }

            // Ζωγραφίζουμε το υπόλοιπο της λέξης (αν υπάρχει) κεντραρισμένο
            if (!result.isEmpty()) {
                int partWidth = fontMetrics.stringWidth(result.toString());
                int centeredX = x + (width - partWidth) / 2;
                g2.drawString(result.toString(), centeredX, currentY);
            }
        }
    }

    /**
     * Ζωγραφίζει ένα ζάρι με λείες, anti-aliased κουκίδες
     *
     * @param g2 Το αντικείμενο Graphics2D για το σχεδιασμό
     * @param x Η συντεταγμένη x της αρχής του ζαριού
     * @param y Η συντεταγμένη y της αρχής του ζαριού
     * @param size Το μέγεθος του ζαριού (τετράγωνο)
     * @param value Η τιμή του ζαριού (1-6)
     */
    public static void drawSmoothDice(Graphics2D g2, int x, int y, int size, int value) {
        // Αποθήκευση των αρχικών ρυθμίσεων
        Color originalColor = g2.getColor();
        Stroke originalStroke = g2.getStroke();
        Object originalAntiAlias = g2.getRenderingHint(RenderingHints.KEY_ANTIALIASING);

        // Ενεργοποίηση του anti-aliasing για λείες γραμμές και καμπύλες
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Ζωγραφίζουμε το λευκό τετράγωνο του ζαριού με ελαφρώς στρογγυλεμένες γωνίες
        int cornerRadius = size / 10; // Μικρή στρογγυλοποίηση για πιο ρεαλιστική εμφάνιση
        g2.setColor(Color.WHITE);
        g2.fillRoundRect(x, y, size, size, cornerRadius, cornerRadius);

        // Ζωγραφίζουμε το μαύρο περίγραμμα, ελαφρώς λεπτότερο για καλύτερη εμφάνιση
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(1.5f));
        g2.drawRoundRect(x, y, size, size, cornerRadius, cornerRadius);

        // Υπολογισμός του μεγέθους των κουκίδων (βελτιωμένο μέγεθος)
        int dotSize = (int)(size * 0.16); // Ελαφρώς μικρότερες κουκίδες

        // Υπολογισμός των θέσεων των κουκίδων
        int spacing = size / 4;
        int center = x + size / 2 - dotSize / 2;  // Κέντρο
        int left = x + spacing;                   // Αριστερά
        int right = x + size - spacing - dotSize; // Δεξιά
        int top = y + spacing;                    // Πάνω
        int bottom = y + size - spacing - dotSize; // Κάτω

        // Ζωγραφίζουμε τις κουκίδες με μαύρο χρώμα
        g2.setColor(Color.BLACK);

        // Ζωγραφίζουμε τις κουκίδες ανάλογα με την τιμή
        switch (value) {
            case 1:
                // Μια κουκίδα στο κέντρο
                g2.fillOval(center, y + size / 2 - dotSize / 2, dotSize, dotSize);
                break;

            case 2:
                // Δύο κουκίδες διαγώνια
                g2.fillOval(left, top, dotSize, dotSize);
                g2.fillOval(right, bottom, dotSize, dotSize);
                break;

            case 3:
                // Τρεις κουκίδες διαγώνια + κέντρο
                g2.fillOval(left, top, dotSize, dotSize);
                g2.fillOval(center, y + size / 2 - dotSize / 2, dotSize, dotSize);
                g2.fillOval(right, bottom, dotSize, dotSize);
                break;

            case 4:
                // Τέσσερις κουκίδες στις γωνίες
                g2.fillOval(left, top, dotSize, dotSize);
                g2.fillOval(right, top, dotSize, dotSize);
                g2.fillOval(left, bottom, dotSize, dotSize);
                g2.fillOval(right, bottom, dotSize, dotSize);
                break;

            case 5:
                // Πέντε κουκίδες (τέσσερις στις γωνίες + κέντρο)
                g2.fillOval(left, top, dotSize, dotSize);
                g2.fillOval(right, top, dotSize, dotSize);
                g2.fillOval(center, y + size / 2 - dotSize / 2, dotSize, dotSize);
                g2.fillOval(left, bottom, dotSize, dotSize);
                g2.fillOval(right, bottom, dotSize, dotSize);
                break;

            case 6:
                // Έξι κουκίδες (δύο στήλες από τρεις)
                g2.fillOval(left, top, dotSize, dotSize);
                g2.fillOval(left, y + size / 2 - dotSize / 2, dotSize, dotSize);
                g2.fillOval(left, bottom, dotSize, dotSize);
                g2.fillOval(right, top, dotSize, dotSize);
                g2.fillOval(right, y + size / 2 - dotSize / 2, dotSize, dotSize);
                g2.fillOval(right, bottom, dotSize, dotSize);
                break;
        }

        // Επαναφορά των αρχικών ρυθμίσεων
        g2.setColor(originalColor);
        g2.setStroke(originalStroke);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, originalAntiAlias);
    }

    public static void drawPlayerCard(Graphics2D g2, Player player, int x, int y,int width, int height, boolean isActive) {

        Color originalColor = g2.getColor();
        Stroke originalStroke = g2.getStroke();
        Object originalAntiAlias = g2.getRenderingHint(RenderingHints.KEY_ANTIALIASING);

        if (player.isBankrupt()) {
            g2.setColor(Color.GRAY);
            g2.fillRoundRect(x - 2, y - 2, width + 4, height + 4, 4, 4);
        }

        // Ενεργοποίηση του anti-aliasing για λείες γραμμές και καμπύλες
        //g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(Color.BLACK);

        int arcSize = 20;
        // Αν είναι ο τρέχων παίκτης, αλλάζουμε το περίγραμμα σε χρυσό
        if (isActive) {
            g2.setColor(new Color(255, 215, 0)); // Χρυσό χρώμα
            g2.fillRoundRect(x - 2, y - 2, width + 4, height + 4, arcSize, arcSize);
        }
        g2.drawRoundRect(x, y, width, height, arcSize, arcSize);

        Font greekFont = new Font("Arial", Font.BOLD, 16);
        Font emojiFont = new Font("Segoe UI Emoji", Font.PLAIN, 16);

// Μαύρο κείμενο για το όνομα του παίκτη
        g2.setFont(greekFont);
        g2.setColor(Color.BLACK);
        g2.drawString("Παίκτης: ", x + 15, y + 25);

// Χρώμα του παίκτη (μεγαλύτερος κύκλος με περίγραμμα)
        g2.setFont(greekFont);
        g2.setColor(Color.BLACK);
        g2.drawString("Χρώμα: ", x + 35, y + 47);

// Ρύθμιση χρώματος του παίκτη
        g2.setColor(player.getColor());
        int circleDiameter = 20; // Αυξήσαμε το μέγεθος του κύκλου
        int circleX = x + 95;
        int circleY = y + 30;
        g2.fillOval(circleX, circleY, circleDiameter, circleDiameter); // Γέμισμα του κύκλου

// Προσθήκη περιγράμματος στον κύκλο για καλύτερη ορατότητα
        g2.setColor(Color.BLACK); // Μαύρο περίγραμμα
        g2.drawOval(circleX, circleY, circleDiameter, circleDiameter); // Περίγραμμα του κύκλου

        g2.setFont(greekFont);
        g2.setColor(Color.BLACK);
        g2.drawString("Χρήματα: " + player.getBalance() + "€", x + 15, y + 70);

// Παίζει τώρα (φωτιά emoji)
        if (isActive) {
            g2.setFont(emojiFont);
            g2.setColor(Color.RED);
            g2.drawString("\uD83D\uDD25", x + 130, y + 20); // 🔥

            g2.setFont(greekFont);
            g2.setColor(Color.BLACK);
            g2.drawString("Παίζει τώρα!", x + 150, y + 20);
        }

        g2.setColor(originalColor);
        g2.setStroke(originalStroke);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, originalAntiAlias);
    }
}
