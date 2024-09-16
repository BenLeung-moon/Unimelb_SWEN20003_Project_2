import bagel.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import static bagel.Keys.*;

/**
 * Skeleton Code for SWEN20003 Project 2, Semester 1, 2022
 *
 * Please fill your name below
 * @HAOHONG LIANG
 */
public class ShadowPirate extends AbstractGame {
    /**
     * this public attribute is stored for transfer millisecond to frames
     */
    public final static double REFRESH_RATE_PER_MILLISECOND = 0.06;
    private final static int WINDOW_WIDTH = 1024;
    private final static int WINDOW_HEIGHT = 768;
    private final static int LADDER_X = 990;
    private final static int LADDER_Y = 630;
    private final static int MESSAGE_SIZE = 55;
    private final static int MESSAGE_Y = 402;
    private final static int GAME_INSTRUCTION_Y_DISTANCE = 55;
    private final static int GAME_INTERVAL_TIME_LENGTH = 3000;
    private final static String GAME_TITLE = "ShadowPirate";
    private final static String START_INSTRUCTION = "PRESS SPACE TO START";
    private final static String ATTACK_INSTRUCTION = "PRESS S TO ATTACK";
    private final static String LEVEL0_INSTRUCTION = "USE ARROW KEYS TO FIND LADDER";
    private final static String LEVEL1_INSTRUCTION = "FIND THE TREASURE";
    private final static String LEVEL_WIN_MESSAGE = "LEVEL COMPLETE!";
    private final static String GAME_WIN_MESSAGE = "CONGRATULATIONS!";
    private final static String GAME_LOSE_MESSAGE = "GAME OVER";
    private final static String LEVEL0_WORLD_FILE = "res/level0.csv";
    private final static String LEVEL1_WORLD_FILE = "res/level1.csv";

    private final static ArrayList<StationaryEntities>  ALL_STATIONARY = new ArrayList<>();
    private final static ArrayList<Character> ALL_CHARACTERS = new ArrayList<>();
    private final static ArrayList<Projectile> ALL_PROJECTILES = new ArrayList<>();
    private final static int ENTRIES_NAME = 0;
    private final static int ENTRIES_X = 1;
    private final static int ENTRIES_Y = 2;

    private final Image LEVEL0_BACKGROUND_IMAGE = new Image("res/background0.png");
    private final Image LEVEL1_BACKGROUND_IMAGE = new Image("res/background1.png");
    private final Font MESSAGE = new Font("res/wheaton.otf", MESSAGE_SIZE);
    private static boolean gameOn;
    private static boolean gameEnd;
    private static boolean gameWin;
    private static boolean gameInterval;
    private Image BackGroundImage;
    private Sailor player;
    private int levelNum = 0;
    private int topEdge;
    private int bottomEdge;
    private int leftEdge;
    private int rightEdge;
    private int frameCounter = 0;

    /**
     * Constructor for the program.
     * the first parameter provides width of the window
     * the second parameter provides height of the window
     * the third parameter provides the name of game title
     */
    public ShadowPirate() {
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);
    }

    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        ShadowPirate game = new ShadowPirate();
        game.run();
        gameOn = false;
        gameEnd = false;
        gameWin = false;
        gameInterval = false;
    }

    /**
     * Method used to read file and create objects according level num
     * @param fileName String parameter provides file name of data file
     * @param levelNum int parameter provides which level is in
     */
    private void readCSV(String fileName, int levelNum){
        //reset the arraylist
        ALL_STATIONARY.clear();
        ALL_CHARACTERS.clear();
        ALL_PROJECTILES.clear();

        String input;
        try(BufferedReader br =
                    new BufferedReader(new FileReader(fileName))){
            while((input = br.readLine()) != null){
                //split data into [name, x, y]
                String[] cells = input.split(",");

                //new player
                switch (cells[ENTRIES_NAME]) {
                    case "Sailor":
                        player = new Sailor(Integer.parseInt(cells[ENTRIES_X]),
                                Integer.parseInt(cells[ENTRIES_Y]));
                        ALL_CHARACTERS.add(player);
                        break;
                    //new block or Bomb and store them
                    case "Block":
                        //in level0 blocks are blocks
                        if(levelNum == 0){
                            ALL_STATIONARY.add(new Block(Integer.parseInt(cells[ENTRIES_X]),
                                    Integer.parseInt(cells[ENTRIES_Y])));
                        }
                        //in level1 blocks read are bombs
                        else{
                            ALL_STATIONARY.add(new Bomb(Integer.parseInt(cells[ENTRIES_X]),
                                    Integer.parseInt(cells[ENTRIES_Y])));
                        }
                        break;
                    //new pirates and store them
                    case "Pirate":
                        ALL_CHARACTERS.add(new Pirate(Integer.parseInt(cells[ENTRIES_X]),
                                Integer.parseInt(cells[ENTRIES_Y])));
                        break;
                    //new Blackbeard and store
                    case "Blackbeard":
                        ALL_CHARACTERS.add(new Blackbeard(Integer.parseInt(cells[ENTRIES_X]),
                                Integer.parseInt(cells[ENTRIES_Y])));
                        break;
                    //new Elixir and store
                    case "Elixir":
                        ALL_STATIONARY.add(new Elixir(Integer.parseInt(cells[ENTRIES_X]),
                                Integer.parseInt(cells[ENTRIES_Y])));
                        break;
                    //new Potion and store
                    case "Potion":
                        ALL_STATIONARY.add(new Potion(Integer.parseInt(cells[ENTRIES_X]),
                                Integer.parseInt(cells[ENTRIES_Y])));
                        break;
                    //new Sword and store
                    case "Sword":
                        ALL_STATIONARY.add(new Sword(Integer.parseInt(cells[ENTRIES_X]),
                                Integer.parseInt(cells[ENTRIES_Y])));
                        break;
                    //new Treasure and store
                    case "Treasure":
                        ALL_STATIONARY.add(new Treasure(Integer.parseInt(cells[ENTRIES_X]),
                                Integer.parseInt(cells[ENTRIES_Y])));
                        break;
                    //new level bound and store
                    case "TopLeft":
                        leftEdge = Integer.parseInt(cells[ENTRIES_X]);
                        topEdge = Integer.parseInt(cells[ENTRIES_Y]);
                        break;
                    case"BottomRight":
                        rightEdge = Integer.parseInt(cells[ENTRIES_X]);
                        bottomEdge = Integer.parseInt(cells[ENTRIES_Y]);
                        break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Performs a state update. allows the game to exit when the escape key is pressed.
     * @param input this is the parameter from user input
     */
    @Override
    public void update(Input input) {

        if (input.wasPressed(Keys.ESCAPE)){
            Window.close();

        }else {
            //demonstrate instruction of level0
            if (!gameOn && !gameEnd && !gameWin && !gameInterval) {
                instructionRender(levelNum);
            }

            //when level complete
            if(gameInterval){
                intervalRender();

                //loop used to counter time pass.
                if (frameCounter < (GAME_INTERVAL_TIME_LENGTH * REFRESH_RATE_PER_MILLISECOND)) {
                    frameCounter++;
                } else {
                    //enter level1 instruction after interval time length
                    frameCounter = 0;
                    levelNum = 1;
                    gameInterval = false;
                }
            }

            //start game and read data
            if (input.wasPressed(Keys.SPACE) && !gameWin && !gameEnd && !gameInterval) {
                gameOn = true;
                //render level0 instruction
                if (levelNum == 0) {
                    readCSV(LEVEL0_WORLD_FILE, levelNum);
                    BackGroundImage = LEVEL0_BACKGROUND_IMAGE;
                }

                //render level1 instruction
                else if (levelNum == 1) {
                    readCSV(LEVEL1_WORLD_FILE, levelNum);
                    BackGroundImage = LEVEL1_BACKGROUND_IMAGE;
                }
            }

            //run after the game start
            if (gameOn && !gameEnd && !gameWin) {
                //render background
                BackGroundImage.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);

                //render gameObjects
                for (Character i : ALL_CHARACTERS) {
                    if (i != null) {
                        i.render();
                    }
                }
                for (GameObject i : ALL_STATIONARY) {
                    if (i != null) {
                        i.render();
                    }
                }
                for (GameObject i : ALL_PROJECTILES){
                    if (i != null) {
                        i.render();
                    }
                }

                //level0 completion detector
                if (isArriveLadder(player)) {
                    gameOn = false;
                    gameInterval = true;
                }

                //receive player command
                if (input.isDown(Keys.LEFT)) {
                    player.move(LEFT);
                }
                else if(input.isDown(Keys.RIGHT)) {
                    player.move(RIGHT);
                }
                else if (input.isDown(Keys.UP)) {
                    player.move(UP);
                }
                else if (input.isDown(Keys.DOWN)) {
                    player.move(DOWN);
                }
                if(isOutOfBound(player)){
                    player.boundOff();
                }
                if(input.wasPressed(Keys.S)){
                    player.setAttackState(true);
                }

                //Lose Condition
                if(player.getCurrentHealthPoint() <= 0){
                    gameEnd = true;
                }

                //Sailor Attack Success detector
                if(player.isAttackState()) {
                    for (Character i : ALL_CHARACTERS) {
                        if (i instanceof Enemies) {
                            if (Collidable.isCollide(player, i)) {
                                player.attack(i);
                            }
                        }
                    }
                }

                //Enemies auto action
                for(Character i: ALL_CHARACTERS){
                    if(i instanceof Enemies){
                        //moving
                        if(isOutOfBound(i)){
                            i.boundOff();
                        }
                        i.move(Keys.valueOf((i).getDirection().name()));

                        //shoot projectile
                        ((Enemies) i).shoot(player, ALL_PROJECTILES);
                    }
                }

                //Projectile Hit detection and outOfBound detection
                if(!ALL_PROJECTILES.isEmpty()) {
                    for (Projectile i : ALL_PROJECTILES) {
                        if (i != null) {
                            //hit detection
                            if (i.isHit(player)) {
                                i.hitTarget(player);
                            }

                            //outOfBound detection
                            if (isOutOfBound(i)) {
                                i.setRemovable(true);
                            }
                        }
                    }
                }

                //Detect each stationary entities whether are collided
                for(Character i: ALL_CHARACTERS){
                    for(StationaryEntities j: ALL_STATIONARY){
                        if(Collidable.isCollide(i, j)){
                            j.collide(i);
                        }
                    }
                }

                //remove character or stationary entities
                ALL_PROJECTILES.removeIf(GameObject::isRemovable);
                ALL_CHARACTERS.removeIf(GameObject::isRemovable);
                ALL_STATIONARY.removeIf(GameObject::isRemovable);
                ALL_CHARACTERS.trimToSize();
                ALL_STATIONARY.trimToSize();
                ALL_PROJECTILES.trimToSize();
            }

            //win game and render message
            if(gameWin){
                winMessageRender();
            }

            //lose game and render message
            if(gameEnd){
                loseMessageRender();
            }
        }
    }


    /**method used to render instruction
     *
     * @param levelNum this is the parameter to detect which level player is in
     */
    public void instructionRender(int levelNum){
        MESSAGE.drawString(START_INSTRUCTION,
                Window.getWidth() / 2.0 - MESSAGE.getWidth(START_INSTRUCTION) / 2,
                MESSAGE_Y);
        MESSAGE.drawString(ATTACK_INSTRUCTION,
                Window.getWidth() / 2.0 - MESSAGE.getWidth(ATTACK_INSTRUCTION) / 2,
                MESSAGE_Y + GAME_INSTRUCTION_Y_DISTANCE);

        if(levelNum == 0){
            MESSAGE.drawString(LEVEL0_INSTRUCTION,
                    Window.getWidth() / 2.0 - MESSAGE.getWidth(LEVEL0_INSTRUCTION) / 2,
                    MESSAGE_Y + 2 * GAME_INSTRUCTION_Y_DISTANCE);
        }
        else if (levelNum == 1){
            MESSAGE.drawString(LEVEL1_INSTRUCTION,
                    Window.getWidth() / 2.0 - MESSAGE.getWidth(LEVEL1_INSTRUCTION) / 2,
                    MESSAGE_Y + 2 * GAME_INSTRUCTION_Y_DISTANCE);
        }
    }

    /**method used to render interval and level win message
     *
     */
    public void intervalRender(){
        MESSAGE.drawString(LEVEL_WIN_MESSAGE,
                Window.getWidth() / 2.0 - MESSAGE.getWidth(LEVEL_WIN_MESSAGE) / 2,
                MESSAGE_Y);
    }

    /**method used to render win message
     *
     */
    public void winMessageRender(){
        MESSAGE.drawString(GAME_WIN_MESSAGE,
                Window.getWidth() / 2.0 - MESSAGE.getWidth(GAME_WIN_MESSAGE) / 2,
                MESSAGE_Y);
    }

    /**method used to render lose message
     *
     */
    public void loseMessageRender(){
        MESSAGE.drawString(GAME_LOSE_MESSAGE,
                Window.getWidth() / 2.0 - MESSAGE.getWidth(GAME_LOSE_MESSAGE) / 2,
                MESSAGE_Y);
    }

    /**
     * method used to detect player whether arrive at ladder and win the level0
     * @param player this is the parameter to provide player position
     * @return return boolean whether player win level0
     */
    public boolean isArriveLadder(GameObject player){
        return player.getXPos() >= LADDER_X && player.getYPos() > LADDER_Y;
    }

    /**method used to detect player is whether out of bound
     *
     * @param gameObject the game object to check whether it is out of game boundary
     * @return return boolean whether game object is out of game boundary
     */
    public boolean isOutOfBound(GameObject gameObject){
        return gameObject.getXPos() < leftEdge
                || gameObject.getXPos() > rightEdge
                || gameObject.getYPos() < topEdge
                || gameObject.getYPos() > bottomEdge;
    }

    /**
     * Sets the gameWin
     * @param gameWin boolean that want to set gameWin
     */
    public static void setGameWin(boolean gameWin) {
        ShadowPirate.gameWin = gameWin;
    }

    /**
     * Sets the GameOn
     * @param gameOn boolean want to set GameOn
     */
    public static void setGameOn(boolean gameOn) {
        ShadowPirate.gameOn = gameOn;
    }
}
