import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.List;

public class Game extends JPanel {

    Dino dino = new Dino(this);
    Obstacle obstacle = new Obstacle(this);

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
     * Calls the move method for the obstacle and the dino.
     */
    public void move (){
        obstacle.move();
        dino.move();
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
     * Paints the game in the game window.
     * Paints the dino and the obstacle in the game window, and then moves them.
     *
     * @param g The Graphics2D object used to paint the game.
     */
    public void paint(Graphics2D g){
        dino.paint(g);
        obstacle.paint(g);
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
        Graphics2D g1=g,g2=g;
        Font score = new Font("Arial", Font.BOLD, 25);
        g.setFont(score);
        g.setColor((Color.blue));
        g1.drawString("Score: " + points,1300, 30);
        g1.drawString("Lives: "+ lives,20,30);
        g1.drawString("Level: "+ level,670,30);

        if(gameEnded){
            g2.setColor(Color.red);
            g2.drawString("You lost!!!", ((float)getBounds().getCenterX()/2)+170,70);
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
}
