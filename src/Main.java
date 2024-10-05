import javax.swing.*;
import java.security.Principal;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    /**
     * This is an object used for synchronization.
     * It is used by the Game class to pause the game when the game is over.
     * It is also used by the Main class to pause the game when the user chooses to restart the game.
     */
    public static final Object lock = new Object();

    /**
     * Main method of the application.
     * <p>
     * This method creates a {@link JFrame} and a {@link Game} object, and adds the game to the frame.
     * It also sets the size and location of the frame, makes it visible, and sets the default close operation.
     * The game is then run in an infinite loop. The game is repainted every 10 milliseconds, and if the game is not over,
     * the game state is updated. If the game is over, the game is paused until the user chooses to restart the game.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        JFrame frame = new JFrame();
        Game game = new Game();
        frame.add(game);
        frame.setSize(1600,420);
        frame.setLocation(70,200);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        int counterForImgChange = 0;

        while(true){

            counterForImgChange++;
            game.repaint();
            if(!Game.gameEnded){
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, e);
                }
                if(counterForImgChange == 30){
                    counterForImgChange = 0;
                    Dino.changeImage = !Dino.changeImage;
                    Obstacle.changeImage = !Obstacle.changeImage;
                }
                if(Game.loseLife){
                    Game.loseLife = false;
                    Game.lives--;
                    Dino.Y_initial=270;
                    Dino.jump = false;
                    Dino.crouch = false;
                    Dino.goBack = false;
                    Dino.goForward = false;
                    Dino.X_aux = 0;
                    Dino.X_initial = 50;
                    Obstacle.X_initial = 1600;
                    Dino.loseLife = true;
                }

                if(Game.isGamePaused){
                    synchronized (lock) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, e);
                        }
                    }
                }
            }
        }
    }
}