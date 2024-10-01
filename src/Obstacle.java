import javax.swing.*;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.util.Objects;
import java.util.Random;

public class Obstacle {
    Game game;

    Area enemy, body, head;

    //Enemy's width and height
    int widthCactusObstacle = 52;
    int heightCactusObstacle = 90;
    int widthFlyingEnemyObstacle = 90;
    int heightFlyingEnemyObstacle = 60;

    static int X_initial = 1600;
    static int Y_initial = 270;
    static boolean changeImage = false;

    static int X_aux = -4;
    public Obstacle(Game game){
        this.game = game;
    }
    private int randomNumber = new Random().nextInt(2)+1;

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

    public Area getBounds(){
        if(randomNumber == 1){
            return this.getCactusBounds();
        }else{
            return this.getFlyingEnemyBounds();
        }
    }

    public boolean collision(){
        Area areaA= new Area(game.dino.getBounds());
        areaA.intersect(getBounds());

        return !areaA.isEmpty();
    }

    private void paintCactus(Graphics2D g, BufferedImage obstacleBuffered){
        Image obstacleRemastered = obstacleBuffered.getSubimage(800,0,widthCactusObstacle,heightCactusObstacle);
        g.drawImage(obstacleRemastered, X_initial, Y_initial, widthCactusObstacle,heightCactusObstacle, null);
    }

    private void paintFlyingEnemy(Graphics2D g, BufferedImage obstacleBuffered){
        if(!changeImage){
            Image obstacleRemastered = obstacleBuffered.getSubimage(353,0, widthFlyingEnemyObstacle,heightFlyingEnemyObstacle);
            g.drawImage(obstacleRemastered, X_initial, Y_initial-40, widthFlyingEnemyObstacle,heightFlyingEnemyObstacle, null);
        }else{
            Image obstacleRemastered = obstacleBuffered.getSubimage(260,16, widthFlyingEnemyObstacle,heightFlyingEnemyObstacle);
            g.drawImage(obstacleRemastered, X_initial, Y_initial-40, widthFlyingEnemyObstacle,heightFlyingEnemyObstacle, null);
        }
    }

    private Area getCactusBounds(){
        Rectangle form = new Rectangle(X_initial, Y_initial+3, widthCactusObstacle-5, heightCactusObstacle-3);

        body = new Area(form);

        enemy = body;
        enemy.add(body);

        return enemy;
    }

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
