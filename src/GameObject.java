import bagel.Image;
import bagel.util.Point;

/**
 * Abstract class represents a gameObject in this game
 */
public abstract class GameObject {
    private double xPos;
    private double yPos;
    private Image image;
    private boolean removable = false;

    /**
     * Constructor used to initialize a gameObject
     * @param xPos provides x coordinate
     * @param yPos provides y coordinate
     * @param image provide image
     */
    public GameObject(double xPos, double yPos, Image image) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.image = image;
    }

    /**
     * Sets the x coordinate
     * @param xPos x coordinate want to set
     */
    public void setXPos(double xPos) {
        this.xPos = xPos;
    }

    /**
     * Sets the y coordinate
     * @param yPos y coordinate want to set
     */
    public void setYPos(double yPos) {
        this.yPos = yPos;
    }

    /**
     * Sets the image
     * @param image image parameter want to set
     */
    public void setImage(Image image) {
        this.image = image;
    }

    /**
     * Gets the image
     * @return an Image of a gameObject
     */
    public Image getImage() {
        return image;
    }

    /**
     * Gets the x coordinate
     * @return double x coordinate
     */
    public double getXPos() {
        return xPos;
    }

    /**
     * Gets the y coordinate
     * @return double y coordinate
     */
    public double getYPos() {
        return yPos;
    }

    /**
     * Method used to get the center point of a gameObject
     * @return Point of a gameObject
     */
    public Point getCentrePoint(){
        return new Point(this.xPos + (this.image.getWidth() / 2.0),
                this.yPos + (this.image.getHeight() / 2.0));
    }

    /**
     * Sets the removable of a gameObject
     * @param removable boolean parameter want to set
     */
    public void setRemovable(boolean removable) {
        this.removable = removable;
    }

    /**
     * Gets state of removable
     * @return boolean state of a gameObject
     */
    public boolean isRemovable() {
        return removable;
    }

    /**
     * method used to render image
     */
    public void render(){
        image.drawFromTopLeft(xPos,yPos);
    }
}
