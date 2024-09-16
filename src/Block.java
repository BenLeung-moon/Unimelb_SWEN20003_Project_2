import bagel.Image;

/**
 * class represents a block that block character from moving
 */

public class Block extends StationaryEntities{
    private static final Image BLOCK = new Image("res/block.png");

    /**
     * Constructor used to initialize a block
     * @param xPos int parameter provides x coordinate
     * @param yPos int parameter provides y coordinate
     */
    public Block(int xPos, int yPos) {
        super(xPos, yPos, BLOCK);
    }

    /**
     * method represents the behaviour after colliding character
     * stop a character moving through
     * @param other Character class is blocked by block
     */
    @Override
    public void collide(Character other) {
            other.boundOff();
    }
}
