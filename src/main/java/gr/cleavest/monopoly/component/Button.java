package gr.cleavest.monopoly.component;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Button extends Component {
    private String text;
    private ButtonClickHandler buttonClickHandler;
    private boolean isHovered = false;

    public Button(String text, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.text = text;
    }

    public Button addHandler(ButtonClickHandler buttonClickHandler) {
        this.buttonClickHandler = buttonClickHandler;
        return this;
    }

    @Override
    public void updateHoverState(MouseEvent e) {
        boolean wasHovered = isHovered;
        isHovered = isHovered(e);
    }

    @Override
    public void draw(Graphics2D g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


        // Χρώματα για το κουμπί - #4285f4 κανονικό, #5a95f5 ανοιχτότερο για hover
        Color normalColor = new Color(66, 133, 244);    // #4285f4
        Color lighterColor = new Color(90, 149, 245);   // #5a95f5
        Color hoverColor = new Color(114, 165, 247);    // #72a5f7
        Color hoverLighterColor = new Color(138, 181, 249); // #8ab5f9

        // Επιλογή χρωμάτων με βάση την κατάσταση hover
        Color baseColor = isHovered ? hoverColor : normalColor;
        Color topColor = isHovered ? hoverLighterColor : lighterColor;

        // Δημιουργία gradient
        GradientPaint gradient = new GradientPaint(
                0, 0, topColor,
                0, height, baseColor
        );

        // Σχεδίαση του κύριου σώματος του κουμπιού
        g2d.setPaint(gradient);
        g2d.fillRoundRect(x, y, width, height, 10, 10);

        // Προσθήκη ελαφριάς σκιάς στο κάτω μέρος
        if (!isHovered) {
            g2d.setColor(new Color(0, 0, 0, 30));
            g2d.fillRoundRect(x, y + height-5, width, 5, 5, 5);
        }

        // Προσθήκη highlight στο πάνω μέρος για τρισδιάστατο εφέ
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        g2d.setColor(Color.WHITE);
        g2d.fillRoundRect(x + 2, y + 2, width-4, height/3, 8, 8);

        // Σχεδίαση κειμένου
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 16));

        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getHeight();

        g2d.drawString(text, x + (width - textWidth) / 2, y + (height - textHeight) / 2 + fm.getAscent());

        g2d.dispose();
    }

    @Override
    public void onClick() {
        if (buttonClickHandler != null) {
            buttonClickHandler.onClick(this);
        }
    }

    @FunctionalInterface
    public interface ButtonClickHandler {
        void onClick(Button button);
    }

    public void setText(String value) {
        this.text = value;
    }

}