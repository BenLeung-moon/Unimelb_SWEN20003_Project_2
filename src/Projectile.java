import bagel.DrawOptions;
import bagel.Image;

/**
 * Class represents the projectile shot by enemies
 */
public class Projectile extends GameObject implements Collidable{

    private final String shooter;
    private final double xSpeed;
    private final double ySpeed;
    private final int damage;
    private final DrawOptions rotation;

    /**
     * Constructor to initialize a projectile
     * @param xPos provides x coordinate
     * @param yPos provides y coordinate
     * @param image provides image
     * @param xSpeed provides x-coordinate speed
     * @param ySpeed provides y-coordinate speed
     * @param damage provides damage of a projectile
     * @param rotation provides the degree of rotation
     * @param shooter store shooter of a projectile
     */
    public Projectile(double xPos, double yPos, Image image,
                      double xSpeed, double ySpeed, int damage,
                      double rotation, String shooter) {
        super(xPos, yPos, image);
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.damage = damage;
        this.rotation = new DrawOptions().setRotation(rotation);
        this.shooter = shooter;
    }

    /**
     * method used to render projectile and renew its position
     */
    @Override
    public void render() {
        this.getImage().drawFromTopLeft(this.getXPos(),this.getYPos(),rotation);
        this.travel();
    }

    /**
     * method represents projectile collides with character
     * @param other character parameter represents the object of colliding
     */
    @Override
    public void collide(Character other) {
        other.damage(this.damage);
        this.setRemovable(true);
    }

    /**
     * method represents projectiles travel in a fixed speed
     */
    public void travel(){
        this.setXPos(this.getXPos()+xSpeed);
        this.setYPos(this.getYPos()+ySpeed);
    }

    /**
     * a method represents projectile hits target
     * @param target Character parameter represent the target of projectile
     */
    public void hitTarget(Character target){
        target.damage(damage);
        System.out.println(this.shooter + " inflicts " + this.damage +
                " damage points on " + target.getClass().getName() + ". " + target.getClass().getName()
                + "’s current health: "
                + (int) target.getCurrentHealthPoint() + "/" + (int) target.getMaxHealthPoint());

        this.setRemovable(true);
    }

    /**
     * method used to detect whether the projectile hit the target
     * @param target represents the target of projectile
     * @return boolean that whether the projectile hit target
     */
    public boolean isHit(Character target){
        double hitDistance = 20;
        return (this.getCentrePoint().distanceTo(target.getCentrePoint()) <= hitDistance);
    }
}
