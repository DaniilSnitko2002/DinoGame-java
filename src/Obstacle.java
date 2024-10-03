import javax.swing.*;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.util.Objects;
import java.util.Random;

public class Obstacle {
    Game game;

    /**
     * These Area variables represent the bounds of the obstacle.
     * The bounds of the obstacle is divided in two parts: the body and the head.
     * The body is the main part of the obstacle, and the head is the top part of it.
     * The bounds of the obstacle are used to check for collision with the Dino.
     */
    Area enemy, body, head;

    /**
     * These variables represent the dimensions of the obstacles.
     * The dimensions of the cactus obstacle are 52x90 pixels.
     * The dimensions of the flying enemy obstacle are 90x60 pixels.
     * The initial position of the obstacle is at (X_initial, Y_initial) = (1600, 270).
     * The variable X_aux is used to control the speed of the obstacle.
     */
    int widthCactusObstacle = 52;
    int heightCactusObstacle = 90;
    int widthFlyingEnemyObstacle = 90;
    int heightFlyingEnemyObstacle = 60;

    static int X_initial = 1600;
    static int Y_initial = 270;
    static boolean changeImage = false;

    static int X_aux = -4;

    /**
     *  Constructor for the Obstacle class.
     */
    public Obstacle(Game game){
        this.game = game;
    }
    private int randomNumber = new Random().nextInt(2)+1;

    /**
     * Moves the obstacle in the game window.
     * It moves the obstacle 4 pixels to the left, and checks if the obstacle is out of the screen.
     * If the obstacle is out of the screen, it increases the score by one and resets the obstacle's position.
     * It also checks if a collision has occurred, and if so, it calls the loseLife() method of the Game class.
     * If the game is over, it calls the gameEnd() method of the Game class.
     */
    public void move(){
        if(X_initial <=-100){
            Game.points++;
            X_initial = 1600;
            randomNumber = new Random().nextInt(2)+1;
            if(Game.points <=15 && Game.points % 3 == 0){
                X_aux -= 2;
                Game.level++;
            }
        }else{
            if(collision()){
                if(Game.lives == 0){
                    game.gameEnd();
                }else{
                    game.loseLife();
                }
            }else{
                X_initial+=X_aux;
            }
        }
    }
    /**
     * Paints the obstacle in the game window.
     * Paints the cactus or flying enemy obstacle in the game window at the obstacle's position.
     * The obstacle is chosen randomly when the obstacle is instantiated.
     *
     * @param g The graphics object used to paint the obstacle.
     */
    public void paint(Graphics2D g){
        Image obstacle = new ImageIcon(Objects.requireNonNull(getClass().getResource("/multimedia/sprite.png"))).getImage();
        BufferedImage obstacleBuffered = new BufferedImage(obstacle.getWidth(null),obstacle.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        obstacleBuffered.getGraphics().drawImage(obstacle, 0, 0, null);
        if(randomNumber == 1){
            this.paintCactus(g,obstacleBuffered);
        }else{
            this.paintFlyingEnemy(g,obstacleBuffered);
        }
    }

    /**
     * Returns the bounds of the obstacle.
     * The bounds of the obstacle is an Area object that is either the bounds of the cactus obstacle
     * or the bounds of the flying enemy obstacle.
     *
     * @return the bounds of the obstacle.
     */
    public Area getBounds(){
        if(randomNumber == 1){
            return this.getCactusBounds();
        }else{
            return this.getFlyingEnemyBounds();
        }
    }

    /**
     * Checks if the obstacle has collided with the dino.
     * Calculates the intersection of the obstacle's bounds with the dino's bounds.
     * If the resulting area is not empty, then a collision has occurred.
     *
     * @return true if a collision has occurred, false otherwise.
     */
    public boolean collision(){
        Area areaA= new Area(game.dino.getBounds());
        areaA.intersect(getBounds());

        return !areaA.isEmpty();
    }

    /**
     * Paints the cactus obstacle in the game window.
     * Subsamples a region from the sprite sheet and draws it in the game window at the obstacle's position.
     * The region is defined by the fields widthCactusObstacle and heightCactusObstacle.
     *
     * @param g The Graphics2D object used to draw the cactus.
     * @param obstacleBuffered The sprite sheet containing the cactus obstacle.
     */
    private void paintCactus(Graphics2D g, BufferedImage obstacleBuffered){
        Image obstacleRemastered = obstacleBuffered.getSubimage(800,0,widthCactusObstacle,heightCactusObstacle);
        g.drawImage(obstacleRemastered, X_initial, Y_initial, widthCactusObstacle,heightCactusObstacle, null);
    }

    /**
     * Paints the flying enemy obstacle in the game window.
     * Subsamples a region from the sprite sheet and draws it in the game window at the obstacle's position.
     * The region is defined by the fields widthFlyingEnemyObstacle and heightFlyingEnemyObstacle.
     * The position of the obstacle is adjusted to be 40 pixels higher than the normal position, so that the flying enemy
     * appears to be in the air.
     *
     * @param g The Graphics2D object used to draw the flying enemy.
     * @param obstacleBuffered The sprite sheet containing the flying enemy obstacle.
     */
    private void paintFlyingEnemy(Graphics2D g, BufferedImage obstacleBuffered){
        if(!changeImage){
            Image obstacleRemastered = obstacleBuffered.getSubimage(353,0, widthFlyingEnemyObstacle,heightFlyingEnemyObstacle);
            g.drawImage(obstacleRemastered, X_initial, Y_initial-40, widthFlyingEnemyObstacle,heightFlyingEnemyObstacle, null);
        }else{
            Image obstacleRemastered = obstacleBuffered.getSubimage(260,16, widthFlyingEnemyObstacle,heightFlyingEnemyObstacle);
            g.drawImage(obstacleRemastered, X_initial, Y_initial-40, widthFlyingEnemyObstacle,heightFlyingEnemyObstacle, null);
        }
    }

    /**
     * Returns the bounds of the cactus obstacle.
     * The bounds of the cactus obstacle are defined by a rectangle that is 5 pixels narrower and 3 pixels shorter
     * than the cactus obstacle itself. The upper left corner of the rectangle is at (X_initial, Y_initial+3).
     *
     * @return the bounds of the cactus obstacle.
     */
    private Area getCactusBounds(){
        Rectangle form = new Rectangle(X_initial, Y_initial+3, widthCactusObstacle-5, heightCactusObstacle-3);

        body = new Area(form);

        enemy = body;
        enemy.add(body);

        return enemy;
    }

    /**
     * Returns the bounds of the flying enemy obstacle.
     * The bounds of the flying enemy obstacle are defined by two rectangles. The first rectangle represents the
     * body of the flying enemy and is 35 pixels narrower and 5 pixels shorter than the flying enemy obstacle
     * itself. The upper left corner of this rectangle is at (X_initial+32, Y_initial-38).
     * The second rectangle represents the head of the flying enemy and is 20x20 pixels in size. The upper left
     * corner of this rectangle is at (X_initial, Y_initial-24).
     *
     * @return the bounds of the flying enemy obstacle.
     */
    private Area getFlyingEnemyBounds(){
        Rectangle form1 = new Rectangle(X_initial+32, Y_initial-38, widthFlyingEnemyObstacle-35, heightFlyingEnemyObstacle-5);
        Rectangle form2 = new Rectangle(X_initial, Y_initial-24, 20, 20);

        body = new Area(form1);
        head = new Area(form2);
        enemy = body;
        enemy.add(body);
        enemy.add(head);

        return enemy;
    }
}
