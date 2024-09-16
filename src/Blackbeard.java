import bagel.Image;

/**
 *Class of Blackbeard, a type of Enemy
 * @author Haohong Liang
 * @version 1.0
 */

public class Blackbeard extends Enemies{
    private final static double MIN_MOVE_SPEED = 0.2;
    private final static double MAX_MOVE_SPEED = 0.7;
    private final static int DAMAGE_POINT = 20;
    private final static int ATTACK_RANGE = 200;
    private final static int ATTACK_COOL_DOWN_TIME = 1500;
    private final static int INVINCIBLE_TIME = 1500;
    private final static double MAX_HEALTH_POINT = 90;
    private final static double PROJECTILE_SPEED = 0.8;
    private final static Image BLACKBEARD_PROJECTILE = new Image("res/blackbeard/blackbeardProjectile.png");
    private final static Image BLACKBEARD_LEFT = new Image("res/blackbeard/blackbeardLeft.png");
    private final static Image BLACKBEARD_RIGHT = new Image("res/blackbeard/blackbeardRight.png");
    private final static Image BLACKBEARD_INVINCIBLE_LEFT = new Image("res/blackbeard/blackbeardHitLeft.png");
    private final static Image BLACKBEARD_INVINCIBLE_RIGHT = new Image("res/blackbeard/blackbeardHitRight.png");

    /**
     * Constructor to initialize blackbeard
     * @param xPos double parameter provides x coordinate
     * @param yPos double parameter provides y coordinate
     */
    public Blackbeard(double xPos, double yPos) {
        super(xPos, yPos, BLACKBEARD_RIGHT, MAX_HEALTH_POINT,
                MIN_MOVE_SPEED, MAX_MOVE_SPEED, DAMAGE_POINT,
                ATTACK_COOL_DOWN_TIME, ATTACK_RANGE, INVINCIBLE_TIME,
                BLACKBEARD_LEFT, BLACKBEARD_RIGHT, PROJECTILE_SPEED, BLACKBEARD_PROJECTILE);
    }

    /**
     * Sets the vincible and change to corresponding images
     * @param vincible boolean that the vincible want to set
     */
    @Override
    public void setVincible(boolean vincible) {
        super.setVincible(vincible);

        //set image from IDLE state to INVINCIBLE state
        if(this.isVincible()){
            super.imageRenew(BLACKBEARD_INVINCIBLE_LEFT, BLACKBEARD_INVINCIBLE_RIGHT,
                    BLACKBEARD_LEFT,BLACKBEARD_RIGHT);
        }
        //set image from INVINCIBLE state to IDLE state
        else{
            super.imageRenew(BLACKBEARD_LEFT, BLACKBEARD_RIGHT,
                    BLACKBEARD_INVINCIBLE_LEFT,BLACKBEARD_INVINCIBLE_RIGHT);
        }
    }
}
