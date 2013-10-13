package ass2.spec;

import drivingExample.DrivingGame;

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

    public MyKeyboard(DrivingGame drivingGame) {
        this.drivingGame = drivingGame;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (Character.toString(e.getKeyChar()).equalsIgnoreCase("w")) {
            drivingGame.setwHeld(true);
            logger.info("w typed detected");
        }
        if (Character.toString(e.getKeyChar()).equalsIgnoreCase("s")) {
            drivingGame.setsHeld(true);
            logger.info("s typed detected");
        }
        if (Character.toString(e.getKeyChar()).equalsIgnoreCase("a")) {
            drivingGame.setaHeld(true);
            logger.info("a typed detected");
        }
        if (Character.toString(e.getKeyChar()).equalsIgnoreCase("d")) {
            drivingGame.setdHeld(true);
            logger.info("d typed detected");
        }
        if (Character.toString(e.getKeyChar()).equalsIgnoreCase("q")) {
            drivingGame.setqHeld(true);
            logger.info("q typed detected");
        }
        if (Character.toString(e.getKeyChar()).equalsIgnoreCase("e")) {
            drivingGame.seteHeld(true);
            logger.info("e typed detected");
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (KeyEvent.getKeyText(e.getKeyCode()).equalsIgnoreCase("Left")) {
            drivingGame.setLeftHeld(true);
            logger.info("Left press detected");
        }
        if (KeyEvent.getKeyText(e.getKeyCode()).equalsIgnoreCase("Right")) {
            drivingGame.setRightHeld(true);
            logger.info("Right press detected");
        }
        if (KeyEvent.getKeyText(e.getKeyCode()).equalsIgnoreCase("Up")) {
            drivingGame.setUpHeld(true);
            logger.info("Up press detected");
        }
        if (KeyEvent.getKeyText(e.getKeyCode()).equalsIgnoreCase("Down")) {
            drivingGame.setDownHeld(true);
            logger.info("Down press detected");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (KeyEvent.getKeyText(e.getKeyCode()).equalsIgnoreCase("Left")) {
            drivingGame.setLeftHeld(false);
            logger.info("Left press released");
        }
        if (KeyEvent.getKeyText(e.getKeyCode()).equalsIgnoreCase("Right")) {
            drivingGame.setRightHeld(false);
            logger.info("Right press released");
        }
        if (KeyEvent.getKeyText(e.getKeyCode()).equalsIgnoreCase("Up")) {
            drivingGame.setUpHeld(false);
            logger.info("Up press released");
        }
        if (KeyEvent.getKeyText(e.getKeyCode()).equalsIgnoreCase("Down")) {
            drivingGame.setDownHeld(false);
            logger.info("Down press released");
        }
        if (Character.toString(e.getKeyChar()).equalsIgnoreCase("w")) {
            drivingGame.setwHeld(false);
            logger.info("w typed released");
        }
        if (Character.toString(e.getKeyChar()).equalsIgnoreCase("s")) {
            drivingGame.setsHeld(false);
            logger.info("s typed released");
        }
        if (Character.toString(e.getKeyChar()).equalsIgnoreCase("a")) {
            drivingGame.setaHeld(false);
            logger.info("a typed released");
        }
        if (Character.toString(e.getKeyChar()).equalsIgnoreCase("d")) {
            drivingGame.setdHeld(false);
            logger.info("d typed released");
        }
        if (Character.toString(e.getKeyChar()).equalsIgnoreCase("q")) {
            drivingGame.setqHeld(false);
            logger.info("q typed released");
        }
        if (Character.toString(e.getKeyChar()).equalsIgnoreCase("e")) {
            drivingGame.seteHeld(false);
            logger.info("e typed released");
        }
    }
}
