import bagel.Image;

/**
 * Class represents a type of enemy, with relatively lower ability
 */
public class Pirate extends Enemies{
    private final static double MIN_MOVE_SPEED = 0.2;
    private final static double MAX_MOVE_SPEED = 0.7;
    private final static int DAMAGE_POINT = 10;
    private final static int ATTACK_RANGE = 100;
    private final static int ATTACK_COOL_DOWN_TIME = 3000;
    private final static int INVINCIBLE_TIME = 1500;
    private final static double MAX_HEALTH_POINT = 45;
    private final static double PROJECTILE_SPEED = 0.4;
    private final static Image PIRATE_PROJECTILE = new Image("res/pirate/pirateProjectile.png");
    private final static Image PIRATE_LEFT = new Image("res/pirate/pirateLeft.png");
    private final static Image PIRATE_RIGHT = new Image("res/pirate/pirateRight.png");
    private final static Image PIRATE_INVINCIBLE_LEFT = new Image("res/pirate/pirateHitLeft.png");
    private final static Image PIRATE_INVINCIBLE_RIGHT = new Image("res/pirate/pirateHitRight.png");

    /**
     * Constructor to initialize a pirate
     * @param xPos provides x coordinate
     * @param yPos provides y coordinate
     */
    public Pirate(double xPos, double yPos) {
        super(xPos, yPos, PIRATE_RIGHT, MAX_HEALTH_POINT,
                MIN_MOVE_SPEED, MAX_MOVE_SPEED, DAMAGE_POINT,
                ATTACK_COOL_DOWN_TIME, ATTACK_RANGE, INVINCIBLE_TIME,
                PIRATE_LEFT, PIRATE_RIGHT, PROJECTILE_SPEED, PIRATE_PROJECTILE);
    }

    /**
     * method used to set vincible state and change to corresponding images
     * @param vincible boolean parameter that want to set
     */
    @Override
    public void setVincible(boolean vincible) {
        super.setVincible(vincible);

        //set image from IDLE state to INVINCIBLE state
        if(this.isVincible()){
            super.imageRenew(PIRATE_INVINCIBLE_LEFT, PIRATE_INVINCIBLE_RIGHT,
                    PIRATE_LEFT,PIRATE_RIGHT);
        }
        //set image from INVINCIBLE state to IDLE state
        else{
            super.imageRenew(PIRATE_LEFT, PIRATE_RIGHT,
                    PIRATE_INVINCIBLE_LEFT,PIRATE_INVINCIBLE_RIGHT);
        }
    }
}
