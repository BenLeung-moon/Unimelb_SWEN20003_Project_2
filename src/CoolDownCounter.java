/**
 * Class represents a counter to record how many frames pass after entering a state
 */

public class CoolDownCounter {
    private int frameCounter = 0;

    /**
     * Method used to initialize a CoolDownCounter
     */
    public CoolDownCounter() {}

    /**
     * method used to detect how long time the state kept, if over the time expected to keep, return true
     * @param time represent the time period want to check whether it has passed
     * @return boolean that whether it passed the time length
     */
    public boolean isOverState(int time){
        if(this.frameCounter > time * ShadowPirate.REFRESH_RATE_PER_MILLISECOND){
            this.frameCounter = 0;
            return true;
        }
        this.frameCounter++;
        return false;
    }
}
