package gr.cleavest.monopoly.utils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Cleavest on 12/3/2025
 */
public class TextWrapper {

    public static List<String> wrapText(String text, FontMetrics fontMetrics, int maxWidth) {
        List<String> lines = new ArrayList<>();
        String[] words = text.split(" ");
        StringBuilder currentLine = new StringBuilder();

        for (String word : words) {
            String testLine = currentLine.isEmpty() ? word : currentLine + " " + word;

            if (fontMetrics.stringWidth(testLine) > maxWidth) {
                lines.add(currentLine.toString());
                currentLine = new StringBuilder(word);
            } else {
                currentLine = new StringBuilder(testLine);
            }
        }

        if (!currentLine.isEmpty()) {
            lines.add(currentLine.toString());
        }

        return lines;
    }
}
