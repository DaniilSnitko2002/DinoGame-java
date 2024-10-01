import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class Dino {
    Game game;

    static boolean jump = false;
    static boolean crouch = false;
    static boolean changeImage = false;

    boolean goingUp = false;
    boolean goingDown = false;

    Area character, body, tail, head;
    int widthCharacter = 87;
    int heightCharacter = 100;

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
            this.jumping();
        }
    }

    public void keyPressed(KeyEvent e){
        switch (e.getKeyCode()){
            case KeyEvent.VK_SPACE:
                jump = true;
                break;
            case KeyEvent.VK_S:
                crouch = true;
                break;
        }
    }

    public void keyReleased(KeyEvent e){
        switch (e.getKeyCode()){
            case KeyEvent.VK_S:
                crouch = false;
                break;
        }
    }

    public void paint(Graphics2D g){
        Image dino = new ImageIcon(Objects.requireNonNull(getClass().getResource("/multimedia/sprite.png"))).getImage();
        BufferedImage dinoBuffered = new BufferedImage(dino.getWidth(null),dino.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        dinoBuffered.getGraphics().drawImage(dino, 0, 0, null);
        if(!crouch){
            this.paintStandingDino(dinoBuffered, g);
        }else{
            this.paintCrouchingDino(dinoBuffered, g);
        }
    }

    public Area getBounds(){
        Rectangle form1 = new Rectangle(X_initial+(widthCharacter/3), crouch? Y_initial+40: Y_initial+5, 20, crouch? heightCharacter/2 : heightCharacter-15);
        Rectangle form2 = new Rectangle(X_initial+5, Y_initial+40, 20, 30);
        Rectangle formHeadCrouch = new Rectangle(X_initial+65, Y_initial+41, 45, 29);
        Rectangle formHeadStand = new Rectangle(X_initial+58,  Y_initial+10,  23, 40);


        body = new Area(form1);
        tail = new Area(form2);
        head = crouch ? new Area(formHeadCrouch): new Area(formHeadStand);

        character = body;
        character.add(body);
        character.add(tail);
        character.add(head);

        return character;
    }

    private void jumping(){
        if(Y_initial==270){
            goingUp = true;
            Y_aux =-3;
            goingDown = false;
        }else if(Y_initial==150){
            goingDown = true;
            Y_aux =3;
            goingUp = false;
        }

        if(goingUp){
            Y_initial+=Y_aux;
        }else if(goingDown){
            Y_initial+=Y_aux;
            if(Y_initial==270){
                jump = false;
            }
        }
    }

    private void paintCrouchingDino(BufferedImage dinoBuffered, Graphics2D g){
        if(!changeImage){
            Image dinoRemastered = dinoBuffered.getSubimage(1862,0,widthCharacter+25,heightCharacter);
            g.drawImage(dinoRemastered, X_initial, Y_initial, widthCharacter+25,heightCharacter, null);
        }else{
            Image dinoRemastered = dinoBuffered.getSubimage(1980,0,widthCharacter+25,heightCharacter);
            g.drawImage(dinoRemastered, X_initial, Y_initial, widthCharacter+25,heightCharacter, null);
        }
    }


    private void paintStandingDino(BufferedImage dinoBuffered, Graphics2D g){
        if(!changeImage){
            Image dinoRemastered = dinoBuffered.getSubimage(1602,0,widthCharacter,heightCharacter);
            g.drawImage(dinoRemastered, X_initial, Y_initial, widthCharacter,heightCharacter, null);
        }else{
            Image dinoRemastered = dinoBuffered.getSubimage(1515,0,widthCharacter, heightCharacter);
            g.drawImage(dinoRemastered, X_initial, Y_initial, widthCharacter,heightCharacter, null);
        }
    }
}
