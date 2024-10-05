import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class Background {

    Game game;

    /**
     * X position of the background sprite.
     * The initial position of the background is off the screen, so it is ready to be moved to the right.
     */
    static int X_initial = 1600;

    /**
     * Y position of the background sprite.
     * The initial y position is at the bottom of the screen.
     */
    int Y_initial = 350;


    /**
     * Auxiliar variable for the x position.
     * It is used to control the movement of the background sprite.
     */
    static int X_aux = 0;

    /**
     * Auxiliar variable for the y position.
     * It is used to control the movement of the background sprite.
     */
    int Y_aux = 350;

    /**
     * Width of the background sprite.
     */
    int widthBackground = 1600;
    /**
     * Height of the background sprite.
     */
    int heightBackground = 25;

    /**
     *  Constructor for the Background class.
     */
    public Background(Game game) {
        this.game = game;
    }

    /**
     * Paints the background of the game in the game window.
     * Subsamples two regions from the sprite sheet and draws them in the game window at the background's position.
     * The regions are defined by the fields widthBackground and heightBackground.
     *
     * @param g The Graphics2D object used to draw the background.
     */
    public void paint(Graphics2D g) {
        Image background = new ImageIcon(Objects.requireNonNull(getClass().getResource("/multimedia/sprite.png"))).getImage();
        BufferedImage backgroundBuffered = new BufferedImage(background.getWidth(null), background.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        backgroundBuffered.getGraphics().drawImage(background, 0, 0, null);
        Image obstacleRemastered = backgroundBuffered.getSubimage(5, 102, widthBackground, heightBackground);
        g.drawImage(obstacleRemastered, X_initial, Y_initial, widthBackground, heightBackground, null);
        g.drawImage(obstacleRemastered, X_aux, Y_aux, widthBackground, heightBackground, null);
    }

    /**
     * Moves the background of the game in the game window.
     * The background is moved at the same speed as the obstacle.
     * When the game is paused or the background has moved out of the screen,
     * the background is reset to its initial position.
     */
    public void move() {
        X_initial += Obstacle.X_aux;
        X_aux += Obstacle.X_aux;

        if (Game.isGamePaused || (X_initial <= 1 && X_aux <= -1599)) {
            X_initial = 1600;
            X_aux = 0;
        }
    }
}
