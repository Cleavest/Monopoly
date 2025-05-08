package gr.cleavest.monopoly.game.field.category;

import gr.cleavest.monopoly.component.Background;
import gr.cleavest.monopoly.component.Label;
import gr.cleavest.monopoly.component.MultiLabel;
import gr.cleavest.monopoly.game.field.Field;
import gr.cleavest.monopoly.game.field.FieldController;
import gr.cleavest.monopoly.game.player.Player;
import gr.cleavest.monopoly.gamestate.Container;
import gr.cleavest.monopoly.gamestate.state.Game;
import gr.cleavest.monopoly.utils.ImageLoader;
import gr.cleavest.monopoly.utils.Reference;
import gr.cleavest.monopoly.utils.TextWrapper;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Random;

/**
 * @author Cleavest on 2/3/2025
 */
public class CommunityChestField extends Field {

    private BufferedImage image;
    private final Random random;

    public CommunityChestField(int positionId) {
        super(positionId);
        this.image = ImageLoader.loadImage(Reference.CHEST);
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
        g2.drawImage(image, x, y,getWidth(), getHeight(), null);
    }

    @FunctionalInterface
    interface Action {
        void execute(Player player, Container container, FieldController fieldController);
    }

    enum Card {
        HANG_OUT_NEIGHBOR("You set aside time every week to hang out with your elderly neighbor – you’ve heard some amazing stories! COLLECT $100.", (player,container,fieldController) -> player.addBalance(100)),
        CLEAN_FOOTPATHS("You organize a group to clean up your town’s footpaths. COLLECT $50.", (player,container,fieldController) -> player.addBalance(50)),
        BLOOD_DONATION("You volunteered at a blood donation. There were free cookies! COLLECT $10.", (player,container,fieldController) -> player.addBalance(10)),
        BUY_COOKIES("You buy a few bags of cookies from that school bake sale. Yum! PAY $50.", (player,container,fieldController) -> player.addBalance(-50)),
        RESCUE_PUPPY("You rescue a puppy – and you feel rescued, too! GET OUT OF JAIL FREE.", (player,container,fieldController) -> player.changeJailCard(1)),
        STREET_PARTY("You organize a street party so people on your road can get to know each other. COLLECT $10 FROM EACH PLAYER.", (player,container,fieldController) -> {
            if (container instanceof Game game) {
                int money = game.getPlayers().stream()
                        .filter(gamePlayer -> player != gamePlayer)
                        .peek(gamePlayer -> gamePlayer.addBalance(-10))
                        .mapToInt(gamePlayer -> 10)
                        .sum();

                player.addBalance(money);
            }
        }),
        BLAST_MUSIC("Blasting music late at night? Your neighbors do not approve. GO TO JAIL.", (player,container,fieldController) -> {
            player.goToJail();
        }),
        HELP_NEIGHBOR("You help your neighbor bring in her groceries. She makes you lunch to say thanks! COLLECT $20.", (player,container,fieldController) -> player.addBalance(20)),
        BUILD_PLAYGROUND("You help build a new school playground – then you get to test the slide! COLLECT $100.", (player,container,fieldController) -> player.addBalance(100)),
        CHILDREN_HOSPITAL("You spend the day playing games with kids at a local children’s hospital. COLLECT $100.", (player,container,fieldController) -> player.addBalance(100)),
        CAR_WASH("You go to the local school’s car wash fundraiser – but you forget to close your windows! PAY $100.", (player,container,fieldController) -> player.addBalance(-100)),
        FOOT_RACE("Just when you think you can’t go another step, you finish that foot race – and raise money for your local hospital! ADVANCE TO GO. COLLECT $200.", (player,container,fieldController) -> player.addBalance(200)),
        STORM_CLEANUP("You help your neighbors clean up their Gardens after a big storm. COLLECT $200.", (player,container,fieldController) -> player.addBalance(200)),
        ANIMAL_SHELTER("Your fuzzy friends at the animal shelter will be thankful for your donation. PAY $50.", (player,container,fieldController) -> player.addBalance(-50)),
        HOME_IMPROVEMENT("You should have volunteered for that home improvement project – you would have learned valuable skills! FOR EACH HOUSE YOU OWN, PAY $40. FOR EACH HOTEL YOU OWN, PAY $115.", (player,container,fieldController) -> {
            int[] info = fieldController.getInfoProperties(player);
            int pay = 40 * info[0] + 115 * info[1];
            player.addBalance(-pay);
        }),
        BAKE_SALE("You organize a bake sale for your local school. COLLECT $25.", (player,container,fieldController) -> player.addBalance(25));

        private final String message;
        private final Action action;

        Card(String message, Action action) {
            this.message = message;
            this.action = action;
        }

        public void execute(Player player, Container container, FieldController fieldController) {
            System.out.println("Community Chest: " + message);
            action.execute(player, container, fieldController);
        }
    }
}
