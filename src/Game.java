import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Game extends JPanel {

    Dino dino = new Dino(this);
    Obstacle obstacle = new Obstacle(this);
    Background background = new Background(this);

    /**
     * Game ended flag.
     * If true, the game is over.
     */
    public static boolean gameEnded = false;

    /**
     * Lose life flag.
     * If true, the game should show an alert dialog to the user.
     */
    public static boolean loseLife = false;

    /**
     * Number of lives the user has.
     * If 0, the game is over.
     */
    public static int lives = 3;

    /**
     * Points the user has.
     * Incremented each time the user passes an obstacle.
     */
    public static int points = 0;

    /**
     * Level of the game.
     * Incremented each time the user passes three obstacles.
     */
    public static int level = 1;

    /**
     * Game paused flag.
     * If true, the game is paused.
     */
    public static boolean isGamePaused = false;

    /**
     *  List of keys with actions.
     *  These keys are used to trigger the corresponding actions in the Dino class.
     */
    private final int[] keysWithAction = {KeyEvent.VK_SPACE, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_DOWN, KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT};

    /**
     *  Constructor for the Game class.
     */
    public Game(){

       addKeyListener(new KeyListener() {
           @Override
           public void keyTyped(KeyEvent e) {

           }

           @Override
           public void keyPressed(KeyEvent e) {

               Integer[] numbersInteger = Arrays.stream(keysWithAction).boxed().toArray(Integer[]::new);
               List<Integer> keyList = Arrays.asList(numbersInteger);

                if(keyList.contains(e.getKeyCode())){
                    dino.keyPressed(e);
                }
           }

           @Override
           public void keyReleased(KeyEvent e) {
               Integer[] numbersInteger = Arrays.stream(keysWithAction).boxed().toArray(Integer[]::new);
               List<Integer> keyList = Arrays.asList(numbersInteger);

               if(keyList.contains(e.getKeyCode())){
                   dino.keyReleased(e);
               }
           }
       });
       setFocusable(true);
    }


    /**
     * Moves the game components.
     * This method moves the obstacle, the dino and the background.
     */
    public void move (){
        obstacle.move();
        dino.move();
        background.move();
    }

    /**
     * {@inheritDoc}
     *
     * Paints the game in the game window and paints the score.
     *
     * @param g The Graphics object used to paint the game.
     */
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        paint(g2);
        paintScore(g2);
    }

    /**
     * Paints the game components in the game window.
     * This method paints the dino, the obstacle and the background in the game window.
     * It also moves the game components.
     *
     * @param g The Graphics2D object used to paint the game components.
     */
    public void paint(Graphics2D g){
        dino.paint(g);
        obstacle.paint(g);
        background.paint(g);
        move();
    }

    /**
     * Paints the score in the game window.
     * Paints the score in the top right corner, the lives in the top left corner, and the level in the top center of the screen.
     * If the game is over, it also paints a message in the center of the screen.
     *
     * @param g The Graphics2D object used to paint the score.
     */
    public void paintScore(Graphics2D g){
        Font score = new Font("Arial", Font.BOLD, 25);
        g.setFont(score);
        g.setColor((Color.blue));
        g.drawString("Score: " + points,1300, 30);
        g.drawString("Lives: "+ lives,20,30);
        g.drawString("Level: "+ level,670,30);

        if(gameEnded){
            this.paintGameOver(g);
            this.paintRestartButton(g);
        }
    }

    /**
     * Sets the game over flag to true.
     * This method is used when the game is over, and it sets the gameEnded flag to true.
     * This flag is used in the paintScore method to paint the lost message in the center of the screen.
     */
    public void gameEnd(){
        gameEnded = true;
    }

    /**
     * Sets the loseLife flag to true.
     * This method is used when the game ends, and it sets the loseLife flag to true.
     * This flag is used in the main method to check if the game is over and to show an alert dialog.
     */
    public void loseLife(){
        loseLife = true;
    }


    /**
     * Paints the game over sprite in the center of the screen.
     * This method is called when the game is over, and it paints the game over sprite in the center of the screen.
     * The sprite is obtained from the sprite sheet by cropping the region from (952,28) to (952+widthGameOverSprite, 28+heightGameOverSprite).
     * The sprite is then drawn in the center of the screen by calling the drawImage method of the Graphics2D object.
     * @param g The Graphics2D object used to paint the game over sprite.
     */
    private void paintGameOver(Graphics2D g){
        int widthGameOverSprite = 382;
        int heightGameOverSprite = 60;
        Image gameOver = new ImageIcon(Objects.requireNonNull(getClass().getResource("/multimedia/sprite.png"))).getImage();
        BufferedImage gameOverBuffered = new BufferedImage(gameOver.getWidth(null),gameOver.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        gameOverBuffered.getGraphics().drawImage(gameOver, 0, 0, null);
        Image gameOverRemastered = gameOverBuffered.getSubimage(952,28,widthGameOverSprite,heightGameOverSprite);
        g.drawImage(gameOverRemastered, (int) getBounds().getCenterX()-widthGameOverSprite/2, this.getHeight()/2, null);
    }

    /**
     * Paints the restart button in the center of the screen.
     * This method is called when the game is over, and it paints the restart button in the center of the screen.
     * The sprite is obtained from the sprite sheet by cropping the region from (0,0) to (75,67).
     * The sprite is then drawn in the center of the screen by calling the drawImage method of the Graphics2D object.
     * Finally, it calls the handlerResetButton method to add a mouse listener to the restart button.
     * @param g The Graphics2D object used to paint the restart button.
     */
    private void paintRestartButton(Graphics2D g){
        Image restartButton = new ImageIcon(Objects.requireNonNull(getClass().getResource("/multimedia/sprite.png"))).getImage();
        BufferedImage restartButtonBuffered = new BufferedImage(restartButton.getWidth(null),restartButton.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        restartButtonBuffered.getGraphics().drawImage(restartButton, 0, 0, null);
        Image restartButtonRemastered = restartButtonBuffered.getSubimage(0,0,75,67);
        g.drawImage(restartButtonRemastered, (int) getBounds().getCenterX()-38, this.getHeight()/2+55, 75,67, null);

        this.handlerResetButton();
    }


    /**
     * Handles the restart button click event.
     * This method adds a mouse listener to the Game panel and waits for a mouse click event.
     * When the event is triggered, it checks if the mouse click is within the bounds of the restart button.
     * If the click is within the bounds, it notifies the Main.lock object to resume the game loop.
     * It also calls the restartGameValues() method to reset the game values.
     */
    private void handlerResetButton(){
        isGamePaused = true;
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                boolean checkX = e.getX() >= (int) getBounds().getCenterX()-38 && e.getX() <= (int) getBounds().getCenterX()+37;
                boolean checkY= e.getY() >= getHeight()/2+55 && e.getY() <= getHeight()/2+122;
                if( checkX && checkY){
                    isGamePaused = false;
                    synchronized (Main.lock) {
                        Main.lock.notify();
                    }
                    restartGameValues();
                }
            }
        });
    }


    /**
     * Resets the values of the game to their initial state, so that the game can be restarted.
     * This method is called when the user chooses to restart the game after losing.
     * It resets the values of the game to their initial state.
     */
    private void restartGameValues() {
        Game.gameEnded = false;
        Game.points = 0;
        Game.level = 1;
        Game.lives = 3;
        Dino.X_initial = 50;
        Obstacle.X_aux = -4;
        Obstacle.X_initial=1600;
        Background.X_aux = 0;
        Background.X_initial = 1600;
    }
}
