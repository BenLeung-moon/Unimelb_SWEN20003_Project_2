/**
 * interface represents an object could be collided
 */

public interface Collidable {
    void collide(Character other);

    /**
     * Method used to detect whether two object collide
     * @param gameObject represents a parameter want to check if it collides
     * @param other represents a parameter want to check if it collided with gameObject
     * @return boolean that whether two gameObject are collided
     */
    static boolean isCollide(GameObject gameObject, GameObject other){
        return gameObject.getImage().getBoundingBoxAt(gameObject.getCentrePoint()).intersects
                (other.getImage().getBoundingBoxAt(other.getCentrePoint()));
    }
}
