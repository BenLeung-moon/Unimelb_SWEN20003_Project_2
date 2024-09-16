import bagel.Image;

/**
 * Class represents an item could heal player
 */
public class Potion extends Items{
    private final static int HEALING_VALUE = 25;
    private final static Image POTION = new Image("res/items/potion.png");
    private final static Image POTION_ICON = new Image("res/items/potionIcon.png");

    /**
     * Constructor to initialize a Potion on the ground
     * @param xPos provides x coordinate
     * @param yPos provides y coordinate
     */
    public Potion(int xPos, int yPos) {
        super(xPos,yPos,POTION);
    }

    /**
     * Constructor to initialize a Potion picked by player
     * @param sailor Sailor parameter that is player pick the Potion
     */
    public Potion(Sailor sailor){
        super(POTION_ICON, sailor);
    }

    /**
     * Method used to heal player who pick Potion
     * @param sailor the player who benefit from the item
     */
    @Override
    public void boost(Sailor sailor) {
        sailor.heal(HEALING_VALUE);
        //add item potion into inventory
        sailor.getInventory().add(new Potion(sailor));
        super.setRemovable(true);
    }
}
