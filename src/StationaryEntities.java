import bagel.Image;

/**
 * Abstract class represents stationary entity in game
 */
public abstract class StationaryEntities extends GameObject implements Collidable{

    /**
     * Constructor used to initialize stationaryEntity
     * @param xPos provides x coordinate
     * @param yPos provides y coordinate
     * @param image provides image
     */
    public StationaryEntities(int xPos, int yPos, Image image) {
        super(xPos, yPos, image);
    }

}
