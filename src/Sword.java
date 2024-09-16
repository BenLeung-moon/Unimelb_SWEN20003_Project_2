import bagel.Image;

/**
 * Class represent an item that could improve player damage point
 */
public class Sword extends Items{
    private static final int ATTACK_BUFF = 15;
    private static final Image SWORD = new Image("res/items/sword.png");
    private static final Image SWORD_ICON = new Image("res/items/swordIcon.png");

    /**
     * Constructor to initialize Sword on the ground
     * @param xPos provides x coordinate
     * @param yPos provides y coordinate
     */
    public Sword(int xPos, int yPos) {
        super(xPos,yPos,SWORD);
    }

    /**
     * Constructor to initialize Sword picked by a player
     * @param sailor provides sailor who picked a sword
     */
    public Sword(Sailor sailor){
        super(SWORD_ICON,sailor);
    }

    /**
     * method used to boost player damage point
     * @param sailor the player who benefit from the item
     */
    @Override
    public void boost(Sailor sailor) {
        sailor.setDamagePoint(sailor.getDamagePoint() + ATTACK_BUFF);

        //add item Sword into inventory
        sailor.getInventory().add(new Sword(sailor));
        super.setRemovable(true);
    }
}
