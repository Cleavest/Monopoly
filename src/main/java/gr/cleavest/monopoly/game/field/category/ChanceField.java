package gr.cleavest.monopoly.game.field.category;

import gr.cleavest.monopoly.game.field.Field;
import gr.cleavest.monopoly.game.field.FieldController;
import gr.cleavest.monopoly.game.player.Player;
import gr.cleavest.monopoly.gamestate.Container;
import gr.cleavest.monopoly.gamestate.state.Game;
import gr.cleavest.monopoly.gamestate.state.Recor;
import gr.cleavest.monopoly.utils.ImageLoader;
import gr.cleavest.monopoly.utils.Reference;
import gr.cleavest.monopoly.component.Background;
import gr.cleavest.monopoly.component.MultiLabel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * @author Cleavest on 2/3/2025
 */
public class ChanceField extends Field {

    private BufferedImage image;
    private final Random random;

    public ChanceField(int positionId) {
        
        super(positionId);
        this.image = ImageLoader.loadImage(Reference.CHANCE);
        this.random = new Random();
    }

    @Override
    public void stay(Player player, Container container, FieldController fieldController) {
        Card drawnCard = Card.values()[random.nextInt(Card.values().length)];
        drawnCard.execute(player, container, fieldController);

        int startX = Reference.CORNER_SIZE + 1;
        int startY = Reference.CORNER_SIZE + 1;
        int width = Reference.BOARD_SIZE - Reference.CORNER_SIZE * 2 - 2;
        Background background = new Background(startX, startY, width, 200);
        container.addSecondComponent(background);


        Font greekFont = new Font("Arial", Font.BOLD, 16);
        MultiLabel multiLabel = new MultiLabel(drawnCard.message, startX + 20, startY + 20, width - 10);
        multiLabel.setColor(Color.WHITE);
        multiLabel.setFont(greekFont);
        container.addSecondComponent(multiLabel);
    }

    @Override
    public void draw(Graphics2D g2,int x, int y) {
        g2.drawImage(image, x, y, getWidth(), getHeight(), null);
    }

    @FunctionalInterface
    interface Action {
        void execute(Player player, Container container, FieldController fieldController);
    }

    enum Card {
        ADVANCE_GO("Advance to 'Go'. Collect $200", (player, container, fieldController) -> {
            player.moveTo(0, (Game) container);
            player.addBalance(200);
        }),
        ADVANCE_ILLINOIS("Advance to Illinois Ave. If you pass Go, collect $200", (player, container, fieldController) -> {
            if (player.getPosition() > 24) player.addBalance(200);
            player.moveTo(24, (Game) container);
        }),
        ADVANCE_ST_CHARLES("Advance to St. Charles Place. If you pass Go, collect $200", (player, container, fieldController) -> {
            if (player.getPosition() > 11) player.addBalance(200);
            player.moveTo(11, (Game) container);
        }),
        NEAREST_UTILITY("Advance token to the nearest Utility. If unowned, buy it. Otherwise, pay 10x dice roll", (player, container, fieldController) -> {
            int utility1 = 12;
            int utility2 = 28;
            int target = (player.getPosition() < utility1 || player.getPosition() > utility2) ? utility1 : utility2;
            player.moveTo(target, (Game) container);
            // Handle buy/pay logic
        }),
        NEAREST_RAILROAD("Advance to the nearest Railroad. Pay double rent if owned", (player, container, fieldController) -> {
            fieldController.getRailRoadFields().stream()
                    .map(Field::getPositionId)
                    .filter(r -> player.getPosition() < r)
                    .findFirst()
                    .ifPresent(r -> player.moveTo(r, (Game) container));
        }),
        BANK_DIVIDEND("Bank pays you dividend of $50", (player, container, fieldController) -> player.addBalance(50)),
        GET_OUT_OF_JAIL("Get out of Jail Free", (player, container, fieldController) -> player.changeJailCard(1)),
        GO_BACK_THREE("Go Back Three Spaces", (player, container, fieldController) -> {
            player.moveTo(player.getPosition() - 3, (Game) container);
        }),
        GO_TO_JAIL("Go to Jail. Go directly to Jail. Do not pass GO, do not collect $200", (player, container, fieldController) -> player.goToJail()),
        REPAIRS("Make general repairs on all your property", (player, container, fieldController) -> {
            int[] info = fieldController.getInfoProperties(player);
            int pay = 25 * info[0] + 100 * info[1];
            player.addBalance(-pay);
        }),
        READING_RAILROAD("Take a trip to Reading Railroad. If you pass Go, collect $200", (player, container, fieldController) -> {
            if (player.getPosition() > 5) player.addBalance(200);
            player.moveTo(5, (Game) container);
        }),
        PAY_TAX("Pay Poor Tax of $15", (player, container, fieldController) -> player.addBalance(-15)),
        BOARDWALK("Take a walk on the Boardwalk", (player, container, fieldController) -> player.moveTo(39, (Game) container)),
        CHAIRMAN("You have been elected Chairman of the Board. Pay each player $50", (player, container, fieldController) -> {
            ((Game) container).getPlayers().stream()
                    .filter(p -> !p.equals(player))
                    .forEach(p -> {
                        player.addBalance(-50);
                        p.addBalance(50);
                    });
        }),
        LOAN_MATURES("Your building and loan matures. Receive $150", (player, container, fieldController) -> player.addBalance(150));
        private final String message;
        private final CommunityChestField.Action action;

        Card(String message, CommunityChestField.Action action) {
            this.message = message;
            this.action = action;
        }

        public void execute(Player player, Container container, FieldController fieldController) {
            System.out.println("Community Chest: " + message);
            action.execute(player, container, fieldController);
        }
    }
}
