package gr.cleavest.monopoly.component;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

/**
 * @author Cleavest on 1/3/2025
 * Βελτιωμένη έκδοση του Button με καλύτερη εμφάνιση
 */
public class TestButton extends Component {
    private String text;
    private ButtonClickHandler buttonClickHandler;

    // Κύρια χρώματα
    private Color normalColor = new Color(59, 89, 182);
    private Color hoverColor = new Color(48, 79, 172);
    private Color pressedColor = new Color(39, 69, 160);
    private Color textColor = Color.WHITE;
    private Color borderColor = new Color(39, 69, 160);

    // Στοιχεία στυλ
    private int cornerRadius = 10;
    private Font buttonFont = new Font("Arial", Font.BOLD, 14);
    private boolean hasShadow = true;
    private int shadowOffsetX = 3;
    private int shadowOffsetY = 3;

    // Καταστάσεις
    public boolean isPressed = false;
    public boolean isHovered = false;

    public TestButton(String text, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.text = text;
    }

    public TestButton addHandler(ButtonClickHandler buttonClickHandler) {
        this.buttonClickHandler = buttonClickHandler;
        return this;
    }

    public void setHovered(boolean hovered) {
        isHovered = hovered;
    }

    public void setPressed(boolean pressed) {
        isPressed = pressed;
    }

    @Override
    public void draw(Graphics2D g) {
        // Αποθηκεύουμε τις αρχικές ρυθμίσεις
        Composite originalComposite = g.getComposite();
        RenderingHints originalHints = g.getRenderingHints();

        // Ενεργοποιούμε το anti-aliasing για ομαλά άκρα
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // Επιλέγουμε το χρώμα ανάλογα με την κατάσταση του κουμπιού
        Color currentColor;
        if (isPressed) {
            currentColor = pressedColor;
        } else if (isHovered) {
            currentColor = hoverColor;
        } else {
            currentColor = normalColor;
        }

        // Σχεδιάζουμε τη σκιά αν χρειάζεται
        if (hasShadow && !isPressed) {
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
            g.setColor(Color.DARK_GRAY);
            g.fill(new RoundRectangle2D.Float(
                    x + shadowOffsetX,
                    y + shadowOffsetY,
                    width, height,
                    cornerRadius, cornerRadius));
            g.setComposite(originalComposite);
        }

        // Σχεδιάζουμε το κύριο σώμα του κουμπιού
        g.setColor(currentColor);
        RoundRectangle2D.Float roundRect = new RoundRectangle2D.Float(
                x, y, width, height, cornerRadius, cornerRadius);
        g.fill(roundRect);

        // Προσθέτουμε περίγραμμα
        g.setColor(borderColor);
        g.setStroke(new BasicStroke(2f));
        g.draw(roundRect);

        // Προσθέτουμε εφέ φωτισμού στο πάνω μέρος του κουμπιού (αν δεν είναι πατημένο)
        if (!isPressed) {
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
            g.setColor(Color.WHITE);
            g.fillRect(x + 2, y + 2, width - 4, height / 3);
            g.setComposite(originalComposite);
        }

        // Σχεδιάζουμε το κείμενο
        FontMetrics metrics = g.getFontMetrics(buttonFont);
        int textX = x + (width - metrics.stringWidth(text)) / 2;
        int textY = y + ((height - metrics.getHeight()) / 2) + metrics.getAscent();

        // Εάν το κουμπί είναι πατημένο, μετακινούμε ελαφρώς το κείμενο
        if (isPressed) {
            textX += 1;
            textY += 1;
        }

        g.setFont(buttonFont);
        g.setColor(textColor);
        g.drawString(text, textX, textY);

        // Επαναφέρουμε τις αρχικές ρυθμίσεις
        g.setComposite(originalComposite);
        g.setRenderingHints(originalHints);
    }

    @Override
    public boolean isHovered(MouseEvent e) {
        boolean wasHovered = isHovered;
        isHovered = super.isHovered(e);

        // Επιστρέφουμε αν άλλαξε η κατάσταση για να μπορούμε να επαναζωγραφίσουμε
        return wasHovered != isHovered;
    }

    @Override
    public void updateHoverState(MouseEvent e) {
        isHovered = super.isHovered(e);
    }

    @Override
    public void onClick() {
        isPressed = true;
        if (buttonClickHandler != null) {
            buttonClickHandler.onClick(this);
        }
        // Σε πραγματική εφαρμογή, θα πρέπει να ρυθμίσετε ένα timer για να επαναφέρετε
        // το isPressed σε false μετά από λίγο
    }

    @FunctionalInterface
    public interface ButtonClickHandler {
        void onClick(TestButton button);
    }

    public void setText(String value){
        this.text = value;
    }

    // Getters και setters για την προσαρμογή του κουμπιού

    public void setNormalColor(Color normalColor) {
        this.normalColor = normalColor;
    }

    public void setHoverColor(Color hoverColor) {
        this.hoverColor = hoverColor;
    }

    public void setPressedColor(Color pressedColor) {
        this.pressedColor = pressedColor;
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    public void setCornerRadius(int cornerRadius) {
        this.cornerRadius = cornerRadius;
    }

    public void setButtonFont(Font buttonFont) {
        this.buttonFont = buttonFont;
    }

    public void setHasShadow(boolean hasShadow) {
        this.hasShadow = hasShadow;
    }

    // Μέθοδοι για διαφορετικά προκαθορισμένα στυλ

    public void setPrimaryStyle() {
        this.normalColor = new Color(59, 89, 182);  // Μπλε
        this.hoverColor = new Color(48, 79, 172);
        this.pressedColor = new Color(39, 69, 160);
        this.borderColor = new Color(39, 69, 160);
        this.textColor = Color.WHITE;
    }

    public void setSuccessStyle() {
        this.normalColor = new Color(46, 204, 113);  // Πράσινο
        this.hoverColor = new Color(39, 174, 96);
        this.pressedColor = new Color(33, 148, 83);
        this.borderColor = new Color(33, 148, 83);
        this.textColor = Color.WHITE;
    }

    public void setDangerStyle() {
        this.normalColor = new Color(231, 76, 60);  // Κόκκινο
        this.hoverColor = new Color(192, 57, 43);
        this.pressedColor = new Color(169, 50, 38);
        this.borderColor = new Color(169, 50, 38);
        this.textColor = Color.WHITE;
    }

    public void setWarningStyle() {
        this.normalColor = new Color(241, 196, 15);  // Κίτρινο
        this.hoverColor = new Color(243, 156, 18);
        this.pressedColor = new Color(230, 126, 34);
        this.borderColor = new Color(230, 126, 34);
        this.textColor = Color.WHITE;
    }

    public void setMonopolyStyle() {
        this.normalColor = new Color(213, 39, 53);  // Monopoly κόκκινο
        this.hoverColor = new Color(180, 30, 45);
        this.pressedColor = new Color(150, 25, 35);
        this.borderColor = new Color(150, 25, 35);
        this.textColor = Color.WHITE;
        this.buttonFont = new Font("Impact", Font.PLAIN, 16);
    }
}