import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class Dino {
    Game game;

    static boolean jump = false;
    boolean goingUp = false;
    boolean goingDown = false;

    Area character, livingBeing, tail, head;
    int widthCharacter = 90;
    int heightCharacter = 90;

    static int X_initial = 50;
    static int Y_initial = 270;

    int X_aux = 0;
    int Y_aux = 0;
    public Dino (Game game){
        this.game = game;
    }

    public void move(){
        if(X_initial+X_aux>0 && X_initial+X_aux<game.getWidth()-widthCharacter){
            X_initial+=X_aux;
        }

        if(jump){
            if(Y_initial == 270){
                goingUp = true;
                Y_aux = -2;
                goingDown = false;
            }else if(Y_initial == 150){
                goingDown = true;
                Y_aux =2;
                goingUp = false;
            }

            if(goingUp){
                Y_initial+=Y_aux;
            }else if(goingDown){
                Y_initial += Y_aux;
                if(Y_initial == 270){
                    jump = false;
                }
            }
        }
    }

    public void keyPressed(KeyEvent e){
        if(e.getKeyCode()==KeyEvent.VK_SPACE){
            jump = true;
        }
    }

    public void paint(Graphics2D g){
        Image dino = new ImageIcon(Objects.requireNonNull(getClass().getResource("/multimedia/sprite.png"))).getImage();
        BufferedImage dinoBuffered = new BufferedImage(dino.getWidth(null),dino.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        dinoBuffered.getGraphics().drawImage(dino, 0, 0, null);
        Image dinoRemastered = dinoBuffered.getSubimage(1335,0,90,90);
        g.drawImage(dinoRemastered, X_initial, Y_initial, widthCharacter,heightCharacter, null);
    }

    public Area getBounds(){
        Rectangle form1 = new Rectangle(X_initial, Y_initial, 95, 62);
        Ellipse2D form2 = new Ellipse2D.Double(X_initial, Y_initial+28,48,48);
        Ellipse2D form3 = new Ellipse2D.Double(X_initial+73, Y_initial+39,38,38);


        livingBeing = new Area(form1);
        tail = new Area(form2);
        head = new Area(form3);

        character = livingBeing;
        character.add(livingBeing);
        character.add(tail);
        character.add(head);

        return character;
    }
}
