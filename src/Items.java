import bagel.Image;

/**
 * Abstract Class represent a kind of items could be picked by player
 */
public abstract class Items extends StationaryEntities{
    private final static int INVENTORY_X = 10;
    private final static int INVENTORY_Y = 50;
    private final static int INVENTORY_OFFSET = 40;

    /**
     * used to initialize an item
     * @param xPos provides x coordinate
     * @param yPos provides y coordinate
     * @param image provides image
     */
    public Items(int xPos, int yPos, Image image) {
        super(xPos, yPos, image);
    }

    /**
     * constructor when items are picked
     * @param image provides image
     * @param sailor provides player who pick the item
     */
    public Items(Image image, Sailor sailor){
        super(INVENTORY_X,
                INVENTORY_Y + (sailor.getInventory().size()*INVENTORY_OFFSET),
                image);
    }

    /**
     * Method represents behaviour of an item colliding with character
     * @param other the object of colliding
     */
    public void collide(Character other){
        if(other instanceof Sailor){
            boost((Sailor) other);
            logMessage((Sailor) other);
        }
    }

    /**
     * Method represents behaviour of an item boosting buff on a player
     * @param sailor the player who benefit from the item
     */
    public abstract void boost(Sailor sailor);

    /**
     * method used to print log message
     * @param sailor provides attribute from sailor who picks the item
     */
    public void logMessage(Sailor sailor){
        if(this instanceof Potion || this instanceof Elixir){
            System.out.println("Sailor finds " + this.getClass().getName() +". Sailor’s current health: " +
                    (int) sailor.getCurrentHealthPoint() + "/" + (int) sailor.getMaxHealthPoint());
        }
        if(this instanceof Sword){
            System.out.println("Sailor finds " + this.getClass().getName()
                    +". Sailor’s damage points increased to " + sailor.getDamagePoint());
        }
    }
}
