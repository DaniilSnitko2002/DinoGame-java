import javax.swing.*;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class Obstacle {
    Game game;

    Area body, enemy;

    int widthObstacle = 52;
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
    public void paint(Graphics2D g){
        Image obstacle = new ImageIcon(Objects.requireNonNull(getClass().getResource("/multimedia/sprite.png"))).getImage();
        BufferedImage obstacleBuffered = new BufferedImage(obstacle.getWidth(null),obstacle.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        obstacleBuffered.getGraphics().drawImage(obstacle, 0, 0, null);
        Image obstacleRemastered = obstacleBuffered.getSubimage(800,0,widthObstacle,heightObstacle);
        g.drawImage(obstacleRemastered, X_initial, Y_initial, widthObstacle,heightObstacle, null);
    }

    public Area getBounds(){
        Rectangle form = new Rectangle(X_initial+15, Y_initial+18, widthObstacle, heightObstacle);

        body = new Area(form);

        enemy = body;
        enemy.add(body);

        return enemy;
    }

    public boolean collision(){
        Area areaA= new Area(game.dino.getBounds());
        areaA.intersect(getBounds());
        return !areaA.isEmpty();
    }
}
