package drivingExample;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Andrew2012
 * Date: 9/10/13
 * Time: 11:35 AM
 * To change this template use File | Settings | File Templates.
 */
public class MyKeyboard implements KeyListener {
    DrivingGame drivingGame;
    static Logger logger = Logger.getLogger(MyKeyboard.class.getName());

    public MyKeyboard(DrivingGame drivingGame){
        this.drivingGame = drivingGame;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(KeyEvent.getKeyText(e.getKeyCode()).equalsIgnoreCase("Left")){
            drivingGame.setLeftHeld(true);
            logger.info("Left press detected");
        }
        if(KeyEvent.getKeyText(e.getKeyCode()).equalsIgnoreCase("Right")){
            drivingGame.setRightHeld(true);
            logger.info("Right press detected");
        }
        if(KeyEvent.getKeyText(e.getKeyCode()).equalsIgnoreCase("Up")){
            drivingGame.setUpHeld(true);
            logger.info("Up press detected");
        }
        if(KeyEvent.getKeyText(e.getKeyCode()).equalsIgnoreCase("Down")){
            drivingGame.setDownHeld(true);
            logger.info("Down press detected");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(KeyEvent.getKeyText(e.getKeyCode()).equalsIgnoreCase("Left")){
            drivingGame.setLeftHeld(false);
            logger.info("Left press released");
        }
        if(KeyEvent.getKeyText(e.getKeyCode()).equalsIgnoreCase("Right")){
            drivingGame.setRightHeld(false);
            logger.info("Right press released");
        }
        if(KeyEvent.getKeyText(e.getKeyCode()).equalsIgnoreCase("Up")){
            drivingGame.setUpHeld(false);
            logger.info("Up press released");
        }
        if(KeyEvent.getKeyText(e.getKeyCode()).equalsIgnoreCase("Down")){
            drivingGame.setDownHeld(false);
            logger.info("Down press released");
        }
    }
}
