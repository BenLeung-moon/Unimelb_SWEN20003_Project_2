import bagel.*;

import java.util.ArrayList;

/**
 * Class represents a character controlled by player
 */
public class Sailor extends Character implements Collidable{
    private final static double INITIAL_MAX_HEALTH_POINT = 100;
    private final static int INITIAL_DAMAGE_POINT = 15;
    private final static int MOVE_SPEED = 1;
    private final static int HP_MESSAGE_SIZE = 30;
    private final static int HEALTH_BAR_X = 10;
    private final static int HEALTH_BAR_Y = 25;
    private final static int ATTACK_DURATION = 1000;
    private final static int ATTACK_COOL_DOWN_TIME = 2000;
    private final static Image SAILOR_LEFT = new Image("res/sailor/sailorLeft.png");
    private final static Image SAILOR_RIGHT = new Image("res/sailor/sailorRight.png");
    private final static Image SAILOR_HIT_LEFT = new Image("res/sailor/sailorHitLeft.png");
    private final static Image SAILOR_HIT_RIGHT = new Image("res/sailor/sailorHitRight.png");

    private boolean attackState = false;
    private boolean attackCool = false;
    private final CoolDownCounter durationCounter = new CoolDownCounter();
    private final ArrayList<Items> Inventory = new ArrayList<>();

    /**
     * Constructor used to initialize a sailor
     * @param xPos provides a x coordinate
     * @param yPos provides a y coordinate
     */
    public Sailor(double xPos, double yPos) {
        super(xPos,yPos,SAILOR_RIGHT,INITIAL_MAX_HEALTH_POINT, MOVE_SPEED,
                INITIAL_DAMAGE_POINT, ATTACK_COOL_DOWN_TIME, new HealthBar(HP_MESSAGE_SIZE),
                Direction.RIGHT, SAILOR_LEFT, SAILOR_RIGHT);
    }

    /**
     * method represents a behaviour that sailor attack target
     * @param target Character parameter that is the target of attacking
     */
    public void attack(Character target) {
        //cannot damage invincible enemy
        if(!((Enemies) target).isVincible()) {
            target.damage(this.getDamagePoint());
            //print log message
            System.out.println("Sailor inflicts " + this.getDamagePoint() +
                    " damage points on " + target.getClass().getName() + ". " + target.getClass().getName()
                    + "’s current health: "
                    + (int) target.getCurrentHealthPoint() + "/" + (int) target.getMaxHealthPoint());
        }
    }

    /**
     * method used to render sailor and change image to corresponding direction
     * count frames when sailor is in attack state and cool down state
     */
    @Override
    public void render() {
        super.render();
        super.renderHealthBar(HEALTH_BAR_X, HEALTH_BAR_Y);
        for(Items i: Inventory){
            i.render();
        }
        //counter for attack state
        if(this.attackState){
            if(this.durationCounter.isOverState(ATTACK_DURATION)){
                this.setAttackState(false);
                this.attackCool = true;
            }
        }

        //counter for attack cool down
        if(this.attackCool){
            if(this.attackCoolDown.isOverState(this.getAttackCoolDownTime())){
                this.attackCool = false;
            }
        }
    }

    /**
     * Gets the attack state
     * @return boolean that represents attack state
     */
    public boolean isAttackState() {
        return attackState;
    }

    /**
     * Sets the attack state
     * @param attackState boolean parameter represents the state want to change
     */
    public void setAttackState(boolean attackState) {
        if(!attackCool) {
            this.attackState = attackState;

            //set image from IDLE state to ATTACK state
            if (this.attackState) {
                super.imageRenew(SAILOR_HIT_LEFT, SAILOR_HIT_RIGHT,
                        SAILOR_LEFT, SAILOR_RIGHT);
            }
            //set image from ATTACK state to IDLE state
            else {
                super.imageRenew(SAILOR_LEFT, SAILOR_RIGHT,
                        SAILOR_HIT_LEFT, SAILOR_HIT_RIGHT);
            }
        }
    }

    /**
     * Gets the Inventory Arraylist
     * @return Arraylist of Inventory
     */
    public ArrayList<Items> getInventory() {
        return Inventory;
    }

    /**
     * method used to detect whether collide with other character
     * @param other represents character want to check whether collide
     */
    @Override
    public void collide(Character other) {
        this.attack(other);
    }
}
