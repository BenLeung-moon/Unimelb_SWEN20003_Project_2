import bagel.Image;

/**
 * Class represent an item that is a goal of the game
 */
public class Treasure extends StationaryEntities{
    private final static Image TREASURE = new Image("res/treasure.png");

    /**
     * Constructor used to initialize Treasure on ground
     * @param xPos provides x coordinate
     * @param yPos provides y coordinate
     */
    public Treasure(int xPos, int yPos) {
        super(xPos,yPos,TREASURE);
    }

    /**
     * method represents the colliding behaviour with character
     * @param other represents the object of colliding
     */
    @Override
    public void collide(Character other) {
        if(other instanceof Sailor){
            ShadowPirate.setGameWin(true);
            ShadowPirate.setGameOn(false);
        }
    }
}
