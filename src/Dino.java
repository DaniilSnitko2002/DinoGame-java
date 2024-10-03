import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class Dino {
    Game game;

    /**
     * The variables below are used to control the dino's jumping action.
     * jump is true when the dino is jumping and false when it is not.
     * crouch is true when the dino is crouching and false when it is not.
     * goForward is true when the dino is going forward and false when it is not.
     * goBack is true when the dino is going backward and false when it is not.
     * changeImage is true when the dino's image needs to be changed and false when it does not.
     * loseLife is true when the dino has lost a life and false when it does not.
     * gameEnded is true when the game has ended and false when it does not.
     */
    static boolean jump = false;
    static boolean crouch = false;
    static boolean goForward = false;
    static boolean goBack = false;
    static boolean changeImage = false;
    static boolean loseLife = false;
    int countImageBlink = 0;

    /**
     * These variables are used to control the dino's jumping action.
     * goingUp is true when the dino is going up and false when it is going down.
     * goingDown is true when the dino is going down and false when it is going up.
     */
    boolean goingUp = false;
    boolean goingDown = false;

    /**
     * The variables below are used to control the dino's body and its collision.
     * character is the area of the dino, which is the object that interacts with the obstacles.
     * body is the area of the dino's body.
     * tail is the area of the dino's tail.
     * head is the area of the dino's head.
     */
    Area character, body, tail, head;

    int widthCharacter = 87;
    int heightCharacter = 100;

    /**
     * These variables are used to control the dino's position and jumping action.
     * X_initial is the initial x position of the dino.
     * Y_initial is the initial y position of the dino.
     * X_aux is the x position auxiliar variable, used to control the dino's movement.
     * Y_aux is the y position auxiliar variable, used to control the dino's jumping action.
     */
    static int X_initial = 50;
    static int Y_initial = 270;
    static int X_aux = 0;
    int Y_aux = 0;

    /**
     *  Constructor for the Dino class.
     */
    public Dino (Game game){
        this.game = game;
    }

    /**
     * This method is used to move the dino in the game window.
     * The dino is moved according to the booleans jump, goForward and goBack.
     * The dino's position is updated in the X axis.
     */
    public void move(){
        if(X_initial+X_aux>0 && X_initial+X_aux<game.getWidth()-widthCharacter){
            X_initial+=X_aux;
        }

        if(jump){
            this.jumping();
        }
        if(goForward){
            this.goingForward();
        }
        if(goBack){
            this.goingBack();
        }
    }

    /**
     * This method is used to listen for key presses and trigger the corresponding actions.
     * The actions are as follows:
     *      - Space: makes the dino jump
     *      - Down arrow or S: makes the dino crouch
     *      - Right arrow or D: makes the dino move forward
     *      - Left arrow or A: makes the dino move backward
     *
     * @param e The KeyEvent representing the key that was pressed.
     */
    public void keyPressed(KeyEvent e){
        switch (e.getKeyCode()){
            case KeyEvent.VK_SPACE:
                jump = true;
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                crouch = true;
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                goForward = true;
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                goBack = true;
                break;
        }
    }

    /**
     * Handles the release of the keys, and sets the corresponding fields to false.
     * Also sets the X_aux variable to 0, which is used to control the dino's movement.
     * @param e The KeyEvent representing the key that was released.
     */
    public void keyReleased(KeyEvent e){
        switch (e.getKeyCode()){
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                crouch = false;
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                goForward = false;
                X_aux = 0;
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                goBack = false;
                X_aux = 0;
                break;
        }
    }

    /**
     * Paints the dino in the game window.
     * If the game has not ended, it paints the dino's image according to its state (standing or crouching).
     * If the game has ended, it paints the dino's dead image.
     * @param g The graphics object used to draw the dino
     */
    public void paint(Graphics2D g){
        Image dino = new ImageIcon(Objects.requireNonNull(getClass().getResource("/multimedia/sprite.png"))).getImage();
        BufferedImage dinoBuffered = new BufferedImage(dino.getWidth(null),dino.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        dinoBuffered.getGraphics().drawImage(dino, 0, 0, null);

        if(!Game.gameEnded){
            if(loseLife && countImageBlink<=50){
                countImageBlink++;
                Image dinoRemastered = dinoBuffered.getSubimage(1,1,1,1);
                g.drawImage(dinoRemastered, 0, 0, 0,0, null);
            }

            if(countImageBlink==50){
                countImageBlink = 0;
                loseLife = false;
            }

            if(countImageBlink%10==0){
                if(!crouch){
                    this.paintStandingDino(dinoBuffered, g);
                }else{
                    this.paintCrouchingDino(dinoBuffered, g);
                }
            }
        }else{
            this.paintDeadDino(dinoBuffered, g);
        }
    }

    /**
     * This method returns the Area of the dino, which is the object that interacts with the obstacles.
     * The dino is composed of three rectangles: the body, the tail and the head.
     * The position of the head is different if the dino is crouching or not.
     *
     * @return the Area of the dino
     */
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

    /**
     * Handles the jumping action of the dino. The dino will jump to the top of the screen and then
     * fall back down. The jumping action is controlled by the goingUp and goingDown boolean variables.
     */
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

    /**
     * Makes the dino go forward. The dino moves to the right at a constant speed. The movement is
     * stopped when the dino reaches the edge of the screen.
     */
    private void goingForward(){
        X_aux =2;
        if(X_initial==1600){
            goForward = false;
        }
        X_initial += X_aux;
    }

    /**
     * Makes the dino go back. The dino moves to the left at a constant speed. The movement is
     * stopped when the dino reaches the edge of the screen.
     */
    private void goingBack(){
        X_aux =-2;
        if(X_initial==0){
            goBack = false;
        }
        X_initial += X_aux;
    }

    /**
     * Paints the crouching dino in the game window.
     * If the dino is crouching, it draws the crouching dino from the sprite sheet.
     * The crouching dino is 25 pixels wider than the standing dino, so the width is adjusted accordingly.
     *
     * @param dinoBuffered The sprite sheet containing the crouching dino
     * @param g The graphics object used to draw the crouching dino
     */
    private void paintCrouchingDino(BufferedImage dinoBuffered, Graphics2D g){
        if(!changeImage){
            Image dinoRemastered = dinoBuffered.getSubimage(1862,0,widthCharacter+25,heightCharacter);
            g.drawImage(dinoRemastered, X_initial, Y_initial, widthCharacter+25,heightCharacter, null);
        }else{
            Image dinoRemastered = dinoBuffered.getSubimage(1980,0,widthCharacter+25,heightCharacter);
            g.drawImage(dinoRemastered, X_initial, Y_initial, widthCharacter+25,heightCharacter, null);
        }
    }


    /**
     * Paints the standing dino in the game window.
     * If the dino is not crouching, it draws the standing dino from the sprite sheet.
     * The standing dino is 25 pixels narrower than the crouching dino, so the width is adjusted accordingly.
     *
     * @param dinoBuffered The sprite sheet containing the standing dino
     * @param g The graphics object used to draw the standing dino
     */
    private void paintStandingDino(BufferedImage dinoBuffered, Graphics2D g){
        if(!changeImage){
            Image dinoRemastered = dinoBuffered.getSubimage(1602,0,widthCharacter,heightCharacter);
            g.drawImage(dinoRemastered, X_initial, Y_initial, widthCharacter,heightCharacter, null);
        }else{
            Image dinoRemastered = dinoBuffered.getSubimage(1515,0,widthCharacter, heightCharacter);
            g.drawImage(dinoRemastered, X_initial, Y_initial, widthCharacter,heightCharacter, null);
        }
    }

    /**
     * Paints the dead dino in the game window.
     * The dead dino is drawn from the sprite sheet.
     *
     * @param dinoBuffered The sprite sheet containing the dead dino
     * @param g The graphics object used to draw the dead dino
     */
    private void paintDeadDino(BufferedImage dinoBuffered, Graphics2D g){
        Image dinoRemastered = dinoBuffered.getSubimage(1690,0,widthCharacter,heightCharacter);
        g.drawImage(dinoRemastered, X_initial, Y_initial, widthCharacter,heightCharacter, null);
    }
}
