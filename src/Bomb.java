import bagel.Image;

/**
 * class represents a bomb that can damage player
 */

public class Bomb extends StationaryEntities{
    private final static int DAMAGE_POINT = 10;
    private final static int EXPLOSION_TIME = 500;
    private final static Image BOMB = new Image("res/bomb.png");
    private final static Image EXPLOSION = new Image("res/explosion.png");
    private final CoolDownCounter explosionCounter = new CoolDownCounter();
    private boolean isExplode = false;

    /**
     * constructor to initialize a bomb
     * @param xPos int parameter provides x coordinate
     * @param yPos int parameter provides y coordinate
     */
    public Bomb(int xPos, int yPos) {
        super(xPos,yPos,BOMB);
    }

    /**
     * method represents behaviour after colliding with a character
     * damage player and change isExplode to true
     * @param other Character parameter is the object of behaviour
     */
    @Override
    public void collide(Character other) {
        if(other instanceof Sailor && !isExplode){
            super.setImage(EXPLOSION);
            other.damage(DAMAGE_POINT);

            //print log message
            System.out.println("Bomb inflicts " + DAMAGE_POINT
                    + " damage points on Sailor. Sailor’s current health: "
                    + (int) other.getCurrentHealthPoint() + "/" + (int) other.getMaxHealthPoint());

            this.isExplode = true;
        }
    }

    /**
     * method used to render image on game
     * change image after colliding
     * disappear after a period
     */
    @Override
    public void render(){
        if(!this.isExplode){
            super.render();
        }
        else {
            //disappear after explosion time
            if(explosionCounter.isOverState(EXPLOSION_TIME)){
                super.setRemovable(true);

            }
        }
        super.render();
    }
}
