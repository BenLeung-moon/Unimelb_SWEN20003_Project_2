import bagel.Image;
import bagel.util.Rectangle;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class represents Enemies in game
 */
public abstract class Enemies extends Character{
    private final static int HEALTH_BAR_SIZE = 15;
    private final static int HEALTH_BAR_OFFSET = 6;

    private final double attackRange;
    private final double projectileSpeed;
    private final Image projectileImage;
    private final int invincibleTime;
    private final CoolDownCounter invincibleCounter = new CoolDownCounter();
    private boolean isAttackCoolDown;
    private boolean vincible;

    /**
     * Constructor used to initialize an enemy
     * @param xPos provides x coordinate
     * @param yPos provides y coordinate
     * @param image provides image
     * @param maxHealthPoint provide maximum of health point
     * @param minSpeed provide the lowest bound of moving speed
     * @param maxSpeed provide the highest bound of moving speed
     * @param damagePoint provide the enemy's damage point
     * @param attackCoolDownTime provide the time length between two times of attack
     * @param attackRange provides the enemy's attack range
     * @param invincibleTime provides the time length will not be damaged after a single attack
     * @param leftImage stores leftward image
     * @param rightImage store rightward image
     * @param projectileSpeed store projectile travelling speed
     * @param projectileImage store projectile image
     */
    public Enemies(double xPos, double yPos, Image image, double maxHealthPoint,
                   double minSpeed, double maxSpeed, int damagePoint,
                   int attackCoolDownTime, int attackRange, int invincibleTime,
                   Image leftImage, Image rightImage,
                   double projectileSpeed, Image projectileImage) {
        super(xPos, yPos, image, maxHealthPoint,
                randomSpeedGenerator(minSpeed, maxSpeed), damagePoint,
                attackCoolDownTime, new HealthBar(HEALTH_BAR_SIZE),
                randomDirection(), leftImage, rightImage);
        this.attackRange = attackRange;
        this.invincibleTime = invincibleTime;
        this.projectileSpeed = projectileSpeed;
        this.projectileImage = projectileImage;
        this.isAttackCoolDown = false;
        this.vincible = false;
    }

    /**
     * method represents the behaviour of enemy shoot projectile
     * @param target Character parameter represents the target want to shoot
     * @param arrayList arrayList used to store Projectile shot
     */
    public void shoot(Character target, ArrayList<Projectile> arrayList) {
        double xDistance = target.getCentrePoint().x - this.getCentrePoint().x;
        double yDistance = target.getCentrePoint().y - this.getCentrePoint().y;
        double rotation = Math.atan(yDistance/xDistance);

        //calculate x-coordinate and y-coordinate speed for projectile
        double xSpeed = this.projectileSpeed * Math.cos(rotation);
        double ySpeed = this.projectileSpeed * Math.sin(rotation);

        //if target on the left side, reserve the direction of projectile
        if(xDistance < 0){
            xSpeed = -xSpeed;
            ySpeed = -ySpeed;
        }

        //detect whether finishing cool down
        if(isAttackCoolDown){
            if (this.attackCoolDown.isOverState(this.getAttackCoolDownTime())){
                this.isAttackCoolDown = false;
            }
        }

        //shoot projectile
        if(inRange(target) && !isAttackCoolDown){
            this.isAttackCoolDown = true;
            arrayList.add(new Projectile(this.getXPos(), this.getYPos(), this.projectileImage,
                    xSpeed, ySpeed, this.getDamagePoint(),rotation, this.getClass().getName()));
        }
    }

    /**
     * detect whether target in attack range
     * @param target represent a target want to shoot
     * @return boolean a target is whether in attack range
     */
    public boolean inRange(Character target){
        return new Rectangle(this.getCentrePoint().x - (attackRange / 2),
                this.getCentrePoint().y - (attackRange / 2),
                attackRange, attackRange).
                intersects(target.getImage().getBoundingBoxAt(target.getCentrePoint()));
    }

    /**
     * Method represents the behaviour of being damaged and invincible temporarily
     * @param damage int value that character is damaged
     */
    @Override
    public void damage(int damage){
        //being invincible after damaging
        super.damage(damage);
        this.setVincible(true);
    }

    /**
     * Method used to render image and counter whether the invincible state is passed
     */
    @Override
    public void render(){
        //counter for vincible state
        if(this.isVincible()){
            if(invincibleCounter.isOverState(invincibleTime)){
                this.setVincible(false);
            }
        }
        super.render();
        this.renderHealthBar((int) super.getXPos(), (int) (super.getYPos() - HEALTH_BAR_OFFSET));

    }

    /**
     * method used to generate a double speed within the range of a type of enemy
     * @param minSpeed provides minimum speed of an enemy
     * @param maxSpeed provides maximum speed of an enemy
     * @return a random speed for an enemy
     */
    public static double randomSpeedGenerator(double minSpeed, double maxSpeed){
        Random rand = new Random();

        return (double)(rand.nextInt((int)(maxSpeed*10-minSpeed*10 + 1))) / 10 + minSpeed;
    }

    /**
     * Gets state of vincible
     * @return boolean that if it is vincible
     */
    public boolean isVincible() {
        return vincible;
    }

    /**
     * Sets states of vincible
     * @param vincible boolean parameter that want to set
     */
    public void setVincible(boolean vincible) {
        this.vincible = vincible;
    }
}
