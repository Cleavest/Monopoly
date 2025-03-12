package gr.cleavest.monopoly.utils;

import java.awt.*;

/**
 * @author Cleavest on 2/3/2025
 */
public class Reference {

    public static final Color BROWN =  new Color(150, 83, 54);
    public static final Color BLUE_LIGHT = new Color(170, 224, 250);
    public static final Color PINK = new Color(214, 145, 190);
    public static final Color ORANGE = new Color(238, 173, 14);
    public static final Color RED = new Color(221, 13, 18);
    public static final Color YELLOW = new Color(252, 199, 12);
    public static final Color GREEN = new Color(0, 146, 70);
    public static final Color BLUE = new Color(0, 102, 168);

    public static final String PARKING_IMAGE = "corner/parking.png";
    public static final String GO_TO_JAIL = "corner/gotojail.png";
    public static final String GO = "corner/go.png";
    public static final String TRAIN = "corner/train.png";
    public static final String PRISONER = "corner/prisoner.png";
    public static final String CHANCE = "corner/chance.png";
    public static final String CHEST = "corner/chest.png";
    public static final String LUXURY = "corner/luxury.png";

    public static final int CORNER_SIZE = 100;
    public static final int SQUARE_HEIGHT = 100;
    public static final int SQUARE_WIDTH = 55;
    public static final int BOARD_SIZE = 2 * SQUARE_HEIGHT + SQUARE_WIDTH * 9;
    public static final int FIELD_SIZE = 40;

    public static final int startFieldInteract = BOARD_SIZE + 40;
    public static final int AVATAR_HEIGHT = 75;
    public static final Font buttonFont = new Font("Arial", Font.BOLD, 10);
}
