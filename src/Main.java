import javax.swing.*;
import java.security.Principal;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static int restartGame = -1;

    public static void main(String[] args) {

        JOptionPane.showMessageDialog(null, "Are you ready?");

        JFrame frame = new JFrame();
        Game game = new Game();
        frame.add(game);
        frame.setSize(1600, 500);
        frame.setLocation(70,250);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        while(true){

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

                if(Game.loseLife){
                    JOptionPane.showMessageDialog(null, "progress carefully!!");
                    Game.loseLife = false;
                    Game.lives--;
                    Dino.Y_initial=270;
                    Dino.jump = false;
                    Obstacle.X_initial = 1600;
                }
            }
        }
    }

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