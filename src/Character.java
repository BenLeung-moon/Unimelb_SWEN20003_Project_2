import bagel.Image;
import bagel.Keys;

import java.util.Random;

/**
 * Abstract class represents a Character in a game
 */

public abstract class Character extends GameObject{
    private double maxHealthPoint;
    private double currentHealthPoint;
    private int damagePoint;
    private final double moveSpeed;
    private final int attackCoolDownTime;
    private final HealthBar healthBar;
    protected CoolDownCounter attackCoolDown = new CoolDownCounter();
    private Direction direction;
    private Image leftImage;
    private Image rightImage;

    /**
     * Constructor to initialize a Character
     * @param xPos double parameter provides x coordinate
     * @param yPos double parameter provides y coordinate
     * @param image Image parameter provides image
     * @param maxHealthPoint double parameter set max health point
     * @param moveSpeed double parameter set moving speed
     * @param damagePoint int parameter set damage per attack
     * @param attackCoolDownTime int parameter set time period between two attack
     * @param healthBar HealthBar parameter provides health bar
     * @param direction Direction parameter provides initial direction
     * @param leftImage Image parameter used to store leftward image
     * @param rightImage Image parameter used to store rightward image
     */
    public Character(double xPos, double yPos, Image image, double maxHealthPoint,
                     double moveSpeed, int damagePoint, int attackCoolDownTime, HealthBar healthBar,
                     Direction direction, Image leftImage, Image rightImage) {
        super(xPos, yPos, image);
        this.maxHealthPoint = maxHealthPoint;
        this.currentHealthPoint = maxHealthPoint;
        this.moveSpeed = moveSpeed;
        this.damagePoint = damagePoint;
        this.attackCoolDownTime = attackCoolDownTime;
        this.healthBar = healthBar;
        this.direction = direction;
        this.leftImage = leftImage;
        this.rightImage =rightImage;
    }


    /**
     * Enum represents direction a character is facing
     */
    enum Direction
    {
        UP, DOWN, LEFT, RIGHT;

        /**
         * Method used to get reverse direction
         * @param direction Direction want to get its reverse direction
         * @return Direction output of reverse direction of input
         */
        public static Direction getReverseDirection(Direction direction){
            switch (direction){
                case UP:
                    return DOWN;

                case DOWN:
                    return UP;

                case LEFT:
                    return RIGHT;

                case RIGHT:
                    return LEFT;
            }
            return null;
        }
    }
    private static final Direction[] DIRECTIONS = Direction.class.getEnumConstants();
    private static final int SIZE = DIRECTIONS.length;
    private static final Random RANDOM = new Random();


    /**
     * method used to generate random Direction
     * @return Direction which is random
     */
    public static Direction randomDirection(){
        return DIRECTIONS[RANDOM.nextInt(SIZE)];
    }

    /**
     * Sets the maxHealthPoint
     *
     * @param maxHealthPoint maxHealthPoint
     */
    public void setMaxHealthPoint(double maxHealthPoint) {
        this.maxHealthPoint = maxHealthPoint;
    }

    /**
     * Sets the damagePoint
     *
     * @param damagePoint single damagePoint of Character
     */
    public void setDamagePoint(int damagePoint) {
        this.damagePoint = damagePoint;
    }

    /**
     * Gets the AttackCoolDownTime
     * @return int attackCoolDownTime
     */
    public int getAttackCoolDownTime() {
        return attackCoolDownTime;
    }

    /**
     * Gets the DamagePoint
     * @return int the DamagePoint
     */
    public int getDamagePoint() {
        return damagePoint;
    }

    /**
     * Gets the CurrentHealthPoint
     * @return double the Current Health Point
     */
    public double getCurrentHealthPoint() {
        return currentHealthPoint;
    }

    /**
     * Gets the MaxHealthPoint
     * @return double the Max Health Point
     */
    public double getMaxHealthPoint() {
        return maxHealthPoint;
    }

    /**
     * Gets the MoveSpeed
     * @return double move speed of character
     */
    public double getMoveSpeed() {
        return moveSpeed;
    }

    /**
     * method used to render character's health bar
     * @param xPos provides x coordinate to render health bar
     * @param yPos provides y coordinate to render health bar
     */
    public void renderHealthBar(int xPos, int yPos){
        healthBar.render(xPos, yPos,
                this.maxHealthPoint, this.currentHealthPoint);
    }

    /**method used to damage character and detect whether is dead
     * @param damage int value that character is damaged
     */
    public void damage(int damage){
        this.currentHealthPoint -= damage;

        //if health point below than 0, character die
        if(currentHealthPoint <= 0){
            super.setRemovable(true);
        }
    }

    /**
     * Gets Direction of the character
     * @return Direction of the character
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Sets the direction
     * @param direction Direction parameter want to set
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**method used to heal character
     *
     * @param healingPower int parameter value that the character is healed
     */
    public void heal(int healingPower){
        //healing effect can't over max health point
        if(this.currentHealthPoint + healingPower >= this.maxHealthPoint){
            this.currentHealthPoint = this.maxHealthPoint;
        }
        else{
            this.currentHealthPoint += healingPower;
        }
    }

    /**
     * method used to move character and change to corresponding image then set direction
     * @param input Direction parameter that character move
     */
    public void move(Keys input){
        if(input == Keys.LEFT){
            super.setXPos(super.getXPos() - this.getMoveSpeed());
            this.setImage(leftImage);
            setDirection(Direction.LEFT);
        }
        else if(input == Keys.RIGHT){
            super.setXPos(super.getXPos() + this.getMoveSpeed());
            this.setImage(rightImage);
            setDirection(Direction.RIGHT);
        }
        else if(input == Keys.UP){
            super.setYPos(super.getYPos() - this.getMoveSpeed());
            setDirection(Direction.UP);
        }
        else if(input == Keys.DOWN){
            super.setYPos(super.getYPos() + this.getMoveSpeed());
            setDirection(Direction.DOWN);
        }
    }

    /**
     * method used to renew character left and right images
     * @param leftImage left image want to change
     * @param rightImage right image want to change
     * @param oldLeftImage old image used to check current image
     * @param oldRightImage old image used to check current image
     */
    public void imageRenew(Image leftImage, Image rightImage,
                           Image oldLeftImage, Image oldRightImage){

        //change current image
        if(this.getImage().equals(oldLeftImage)){
            this.setImage(leftImage);
        }
        else if(this.getImage().equals(oldRightImage)){
            this.setImage(rightImage);
        }
        //change stored images
        this.leftImage = leftImage;
        this.rightImage = rightImage;
    }

    /**
     * method represents behaviour colliding with block
     * move by reverse direction
     */
    public void boundOff(){
        this.setDirection(Direction.getReverseDirection(this.direction));
        move(Keys.valueOf(this.getDirection().name()));

        if(this.getClass().equals(Sailor.class)){
            if(this.getDirection().equals(Direction.LEFT)){
                this.setImage(this.rightImage);
            }
            else if(this.getDirection().equals(Direction.RIGHT)){
                this.setImage(this.leftImage);
            }
        }
    }
}
