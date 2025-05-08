package gr.cleavest.monopoly.game.field;

import gr.cleavest.monopoly.game.field.category.*;
import gr.cleavest.monopoly.game.player.Player;
import gr.cleavest.monopoly.utils.Reference;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Cleavest on 2/3/2025
 */
public class FieldController {

    private Field[] fields = new Field[40];

    public FieldController() {
        add(new GoField(0));
        add(new PropertyField(1,"Μικρό Πάρκο" ,50,ColorGroup.BROWN, Arrays.asList(2,10,30,90,160,250),50));
        add(new CommunityChestField(2));
        add(new PropertyField(3,"Καφετέρια" ,60,ColorGroup.BROWN, Arrays.asList(4,20,60,180,320,450),50));
        add(new TaxField(4));
        add(new RailRoadField(5,"Κεντρικός Σταθμός"));
        add(new PropertyField(6,"Βιβλιοπωλείο" ,70,ColorGroup.BLUE_LIGHT, Arrays.asList(6,30,90,270,400,550),50));
        add(new ChanceField(7));
        add(new PropertyField(8,"Μίνι Μάρκετ" ,80,ColorGroup.BLUE_LIGHT, Arrays.asList(6,30,90,270,400,550),50));
        add(new PropertyField(9,"Στάση Λεωφορείου",90 ,ColorGroup.BLUE_LIGHT, Arrays.asList(8,40,100,300,450,600),50));
        add(new JailField(10));
        add(new PropertyField(11,"Πρατήριο Βενζίνης" ,100,ColorGroup.ORANGE, Arrays.asList(10,50,150,450,625,750),100));
        add(new UtilityField(12));
        add(new PropertyField(13,"Σινεμά" ,150,ColorGroup.ORANGE, Arrays.asList(10,50,150,450,625,750),100));
        add(new PropertyField(14,"Εστιατόριο",170 ,ColorGroup.ORANGE, Arrays.asList(12,60,180,500,700,900),100));
        add(new RailRoadField(15, "Σταθμός Λιμανιού"));
        add(new PropertyField(16,"Γυμναστήριο" ,200,ColorGroup.PINK,Arrays.asList(14,70,200,550,750,950),100));
        add(new CommunityChestField(17));
        add(new PropertyField(18,"Σούπερ Μάρκετ",220 ,ColorGroup.PINK,Arrays.asList(14,70,200,550,750,950),100));
        add(new PropertyField(19,"Νοσοκομείο",250 ,ColorGroup.PINK,Arrays.asList(16,80,220,600,800,1000),100));
        add(new FreeParkingField(20));
        add(new PropertyField(21,"Σχολείο",280 ,ColorGroup.RED,Arrays.asList(18,90,250,700,875,1050),150));
        add(new ChanceField(22));
        add(new PropertyField(23,"Τράπεζα" ,300,ColorGroup.RED,Arrays.asList(18,90,250,700,875,1050),150));
        add(new PropertyField(24,"Εμπορικό Κέντρο",350 ,ColorGroup.RED,Arrays.asList(20,100,300,750,925,925),150));
        add(new RailRoadField(25,"Σταθμός Βουνού"));
        add(new PropertyField(26,"Ξενοδοχείο" ,400,ColorGroup.YELLOW,Arrays.asList(22,110,330,800,975,1150),150));
        add(new PropertyField(27,"Αεροδρόμιο",450 ,ColorGroup.YELLOW, Arrays.asList(22,110,330,800,975,1150),150));
        add(new UtilityField(28));
        add(new PropertyField(29,"Λιμάνι" ,500,ColorGroup.YELLOW, Arrays.asList(24,120,360,850,1025,1200),150));
        add(new GoToJailField(30));
        add(new PropertyField(31,"Πύργος Γραφείων" ,550,ColorGroup.GREEN, Arrays.asList(26,130,390,900,1100,1275),200));
        add(new PropertyField(32,"Πάρκο Τεχνολογίας" ,600,ColorGroup.GREEN,Arrays.asList(26,130,390,900,1100,1275),200));
        add(new CommunityChestField(33));
        add(new PropertyField(34,"Φόρος Περιουσίας" ,650,ColorGroup.GREEN, Arrays.asList(28,150,450,1000,1200,1400),200));
        add(new RailRoadField(35,"Σταθμός Περιφέρειας"));
        add(new ChanceField(36));
        add(new PropertyField(37,"Test" ,700,ColorGroup.BLUE,Arrays.asList(35,175,500,1100,1300,1500),200));
        add(new TaxField(38));
        add(new PropertyField(39,"Ακρόπολη", 800 ,ColorGroup.BLUE,Arrays.asList(50,200,600,1400,1700,2000),200));
    }

    public Field[] getFields() {
        return fields;
    }

    public void add(Field field) {
        fields[field.getPositionId()] = field;
    }

    public long getOwnerTrain(Player player) {
        return Arrays.stream(fields).filter(field -> field instanceof RailRoadField && ((RailRoadField) field).getOwner() == player).count();
    }

    public boolean hasAllGroup(PropertyField f) {
        return f.getGroup().getCount() == Arrays.stream(fields).filter(field -> field instanceof RailRoadField && ((RailRoadField) field).getOwner() == f.getOwner()).count();
    }

    public List<Field> getRailRoadFields() {
        return Arrays.stream(fields).filter(field -> field instanceof RailRoadField).toList();
    }

    public int[] getInfoProperties(Player player) {

        int houses = 0;
        int hotels = 0;

        for (Field value : fields) {
            if (value instanceof PropertyField field) {
                if (field.getOwner() == player) {
                    if (field.getPropertyCount() == 5) {
                        hotels++;
                    } else {
                        houses += field.getPropertyCount();
                    }
                }
            }
        }

        return new int[]{houses, hotels};
    }
}
