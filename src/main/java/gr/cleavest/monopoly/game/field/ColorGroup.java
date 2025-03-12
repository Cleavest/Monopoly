package gr.cleavest.monopoly.game.field;

import gr.cleavest.monopoly.utils.Reference;

import java.awt.*;

/**
 * @author Cleavest on 6/3/2025
 */
public enum ColorGroup {

    BROWN(Reference.BROWN,2),
    BLUE_LIGHT(Reference.BLUE_LIGHT,2),
    ORANGE(Reference.ORANGE,2),
    PINK(Reference.PINK,2),
    RED(Reference.RED,2),
    YELLOW(Reference.YELLOW,2),
    GREEN(Reference.GREEN,2),
    BLUE(Reference.BLUE,2);

    private final Color color;
    private final int count;

    ColorGroup(Color color, int count) {
        this.color = color;
        this.count = count;
    }

    public Color getColor() {
        return color;
    }

    public int getCount() {
        return count;
    }
}
