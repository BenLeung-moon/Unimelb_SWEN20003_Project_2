import bagel.Image;

/**
 * Class represent an item could boost player health point and its maximum
 */
public class Elixir extends Items{
    private static final int HEALTH_POINT_BUFF = 35;
    private static final Image ELIXIR = new Image("res/items/elixir.png");
    private static final Image ELIXIR_ICON = new Image("res/items/elixirIcon.png");

    /**
     * Constructor used to initialize Elixir on the ground
     * @param xPos provides x coordinate
     * @param yPos provides y coordinate
     */
    public Elixir(int xPos, int yPos) {
        super(xPos,yPos,ELIXIR);
    }

    /**
     * Constructor used to initialize Elixir picked by player
     * @param sailor parameter that the player pick the Elixir
     */
    public Elixir(Sailor sailor){
        super(ELIXIR_ICON,sailor);
    }

    /**
     * Method used to boost the buff to player pick Elixir
     * @param sailor parameter of player picked Elixir
     */
    @Override
    public void boost(Sailor sailor) {
        sailor.setMaxHealthPoint(sailor.getMaxHealthPoint() + HEALTH_POINT_BUFF);
        sailor.heal((int) sailor.getMaxHealthPoint());

        //add item Elixir into inventory
        (sailor).getInventory().add(new Elixir(sailor));
        super.setRemovable(true);
    }
}
