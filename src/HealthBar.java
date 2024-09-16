import bagel.DrawOptions;
import bagel.Font;
import bagel.util.Colour;

/**
 * Class represents a health bar which a character has
 */
public class HealthBar extends Font{
    private final static int MIDDLE_HP_INTERVAL = 65;
    private final static int LOW_HP_INTERVAL = 35;
    private final static String FONT_FILE = "res/wheaton.otf";
    private final static Colour GREEN = new Colour(0,0.8,0.2);
    private final static Colour ORANGE = new Colour(0.9,0.6,0);
    private final static Colour RED = new Colour(1,0,0);

    /**
     * Constructor used to initialize a health bar
     * @param fontSize int provides the size of a health bar
     */
    public HealthBar(int fontSize) {
        super(FONT_FILE, fontSize);
    }

    /**
     * method used to render health bar and Change to corresponding colour
     * @param xPos provides x coordinate
     * @param yPos provides y coordinate
     * @param maxHealthPoint provides maximum of health point used to calculate
     * @param currentHealthPoint provides current health point used to calculate
     */
    public void render(int xPos, int yPos,
                       double maxHealthPoint, double currentHealthPoint) {
        //calculate the percentage in integer
        long percentage = Math.round((currentHealthPoint/maxHealthPoint) * 100);

        //change message colour and render
        if(percentage <= MIDDLE_HP_INTERVAL && percentage > LOW_HP_INTERVAL){//Health Point coloured orange
            this.drawString(percentage + "%",
                    xPos,yPos,
                    new DrawOptions().setBlendColour(ORANGE));
        }
        else if (percentage <= LOW_HP_INTERVAL){//Health Point coloured red
            this.drawString(percentage + "%",
                    xPos,yPos,
                    new DrawOptions().setBlendColour(RED));
        }
        else{//Health Point coloured green
            this.drawString(percentage + "%",
                    xPos,yPos,
                    new DrawOptions().setBlendColour(GREEN));
        }

    }
}
