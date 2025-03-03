package gr.cleavest.monopoly.game.field;

import gr.cleavest.monopoly.game.field.category.*;
import gr.cleavest.monopoly.utils.Reference;

/**
 * @author Cleavest on 2/3/2025
 */
public class FieldController {

    private Field[] fields = new Field[40];

    public FieldController() {
        add(new GoField(0));
        add(new PropertyField(1, Reference.BROWN));
        add(new CommunityChestField(2));
        add(new PropertyField(3,Reference.BROWN));
        add(new TaxField(4));
        add(new RailRoadField(5));
        add(new PropertyField(6,Reference.BLUE_LIGHT));
        add(new ChanceField(7));
        add(new PropertyField(8,Reference.BLUE_LIGHT));
        add(new PropertyField(9,Reference.BLUE_LIGHT));
        add(new JailField(10));
        add(new PropertyField(11,Reference.ORANGE));
        add(new UtilityField(12));
        add(new PropertyField(13,Reference.ORANGE));
        add(new PropertyField(14,Reference.ORANGE));
        add(new RailRoadField(15));
        add(new PropertyField(16,Reference.PINK));
        add(new CommunityChestField(17));
        add(new PropertyField(18,Reference.PINK));
        add(new PropertyField(19,Reference.PINK));
        add(new FreeParkingField(20));
        add(new PropertyField(21,Reference.RED));
        add(new ChanceField(22));
        add(new PropertyField(23,Reference.RED));
        add(new PropertyField(24,Reference.RED));
        add(new RailRoadField(25));
        add(new PropertyField(26,Reference.YELLOW));
        add(new PropertyField(27,Reference.YELLOW));
        add(new UtilityField(28));
        add(new PropertyField(29,Reference.YELLOW));
        add(new GoToJailField(30));
        add(new PropertyField(31,Reference.GREEN));
        add(new PropertyField(32,Reference.GREEN));
        add(new CommunityChestField(33));
        add(new PropertyField(34,Reference.GREEN));
        add(new RailRoadField(35));
        add(new ChanceField(36));
        add(new PropertyField(37,Reference.BLUE));
        add(new TaxField(38));
        add(new PropertyField(39,Reference.BLUE));
    }

    public Field[] getFields() {
        return fields;
    }

    public void add(Field field) {
        fields[field.positionId] = field;
    }
}
