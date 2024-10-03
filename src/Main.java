import javax.swing.*;
import java.security.Principal;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static int restartGame = -1;


    /**
     * This is the main method of the program. It creates a new JFrame and adds
     * an instance of the Game class to it. It sets the size of the frame to
     * 1600x400 and sets it to be centered on the screen at a location of (70,200).
     * It makes the frame visible and sets it to close when the window is closed.
     * It then enters an infinite loop, which checks if the game is over and if
     * so, it prompts the user to restart the game. If the user chooses to restart,
     * it calls the restartValues() method to reset the game. If the game is not over, it calls the
     * paintComponent() method of the Game class to repaint the game and then
     * waits for 10 milliseconds. It then checks if the Dino should change its
     * image and if so, it does. If the game is over and the user has lost a life,
     * it shows a message dialog and resets the game.
     * @param args The command line arguments.
     */
    public static void main(String[] args) {

        JFrame frame = new JFrame();
        Game game = new Game();
        frame.add(game);
        frame.setSize(1600, 400);
        frame.setLocation(70,200);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        int counterForImgChange = 0;

        while(true){
            counterForImgChange++;
            if(Game.gameEnded){
                restartGame = JOptionPane.showConfirmDialog(null, "You lost, want to restart Game", "You lost", JOptionPane.YES_NO_OPTION);
                if(restartGame == 0){
                    restartValues();
                }else if (restartGame == 1){
                    System.exit((0));
                }
            }else{
                game.repaint();
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
                    JOptionPane.showMessageDialog(null, "be careful!!");
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

                }
            }
        }
    }

    /**
     * Resets the values of the game to their initial state, so that the game can be restarted.
     * This method is called when the user chooses to restart the game after losing.
     * It resets the values of the game to their initial state.
     */
    private static void restartValues() {
        Game.gameEnded = false;
        Obstacle.X_aux = -4;
        Game.points = 0;
        Game.level = 1;
        Game.lives = 3;
        restartGame = -1;
        Obstacle.X_initial=1600;
    }
}