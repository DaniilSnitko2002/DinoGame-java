import javax.swing.*;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class Obstacle {
    Game game;

    Area head, body, enemy;

    int widthObstacle = 50;
    int heightObstacle = 90;

    static int X_initial = 1600;
    static int Y_initial = 270;

    static int X_aux = -4;
    public Obstacle(Game game){
        this.game = game;
    }

    public void move(){
        if(X_initial <=-100){
            Game.points++;
            X_initial = 1600;
            if(Game.points <=12 && Game.points % 3 == 0){
                X_aux += -2;
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
    public void paint(Graphics2D g){
        Image obstacle = new ImageIcon(Objects.requireNonNull(getClass().getResource("/multimedia/sprite.png"))).getImage();
        BufferedImage obstacleBuffered = new BufferedImage(obstacle.getWidth(null),obstacle.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        obstacleBuffered.getGraphics().drawImage(obstacle, 0, 0, null);
        Image obstacleRemastered = obstacleBuffered.getSubimage(800,0,52,90);
        g.drawImage(obstacleRemastered, X_initial, Y_initial, widthObstacle,heightObstacle, null);
    }

    public Area getBounds(){
        Ellipse2D form1 = new Ellipse2D.Double(X_initial, Y_initial,40,40);
        Rectangle form2 = new Rectangle(X_initial+12, Y_initial+16, 50, 53);

        head = new Area(form1);
        body = new Area(form2);

        enemy = head;
        enemy.add(head);
        enemy.add(body);

        return enemy;
    }

    public boolean collision(){
        Area areaA= new Area(game.dino.getBounds());
        areaA.intersect(getBounds());
        return !areaA.isEmpty();
    }
}
