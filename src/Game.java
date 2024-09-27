import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game extends JPanel {

    Dino dino = new Dino(this);
    Obstacle obstacle = new Obstacle(this);

    public static boolean gameEnded = false;
    public static boolean loseLife = false;
    public static int lives = 3;
    public static int points = 0;
    public static int level = 1;
    public Game(){

       addKeyListener(new KeyListener() {
           @Override
           public void keyTyped(KeyEvent e) {

           }

           @Override
           public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_SPACE){
                    dino.keyPressed(e);
                }
           }

           @Override
           public void keyReleased(KeyEvent e) {

           }
       });
       setFocusable(true);
    }

    public void move (){
        obstacle.move();
        dino.move();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        paint(g2);
        paintScore(g2);
    }

    public void paint(Graphics2D g){
        dino.paint(g);
        obstacle.paint(g);
        move();
    }

    public void paintScore(Graphics2D g){
        Graphics2D g1=g,g2=g;
        Font score = new Font("Arial", Font.BOLD, 30);
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

    public void gameEnd(){
        gameEnded = true;
    }

    public void loseLife(){
        loseLife = true;
    }
}
