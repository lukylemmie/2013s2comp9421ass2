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
    MyCamera myCamera;
    static Logger logger = Logger.getLogger(MyKeyboard.class.getName());

    public MyKeyboard(MyCamera myCamera) {
        this.myCamera = myCamera;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (Character.toString(e.getKeyChar()).equalsIgnoreCase("w")) {
            myCamera.setwHeld(true);
            logger.info("w typed detected");
        }
        if (Character.toString(e.getKeyChar()).equalsIgnoreCase("s")) {
            myCamera.setsHeld(true);
            logger.info("s typed detected");
        }
        if (Character.toString(e.getKeyChar()).equalsIgnoreCase("a")) {
            myCamera.setaHeld(true);
            logger.info("a typed detected");
        }
        if (Character.toString(e.getKeyChar()).equalsIgnoreCase("d")) {
            myCamera.setdHeld(true);
            logger.info("d typed detected");
        }
        if (Character.toString(e.getKeyChar()).equalsIgnoreCase("q")) {
            myCamera.setqHeld(true);
            logger.info("q typed detected");
        }
        if (Character.toString(e.getKeyChar()).equalsIgnoreCase("e")) {
            myCamera.seteHeld(true);
            logger.info("e typed detected");
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            myCamera.setLeftHeld(true);
            logger.info("Left press detected");
        }
        if (KeyEvent.getKeyText(e.getKeyCode()).equalsIgnoreCase("Right")) {
            myCamera.setRightHeld(true);
            logger.info("Right press detected");
        }
        if (KeyEvent.getKeyText(e.getKeyCode()).equalsIgnoreCase("Up")) {
            myCamera.setUpHeld(true);
            logger.info("Up press detected");
        }
        if (KeyEvent.getKeyText(e.getKeyCode()).equalsIgnoreCase("Down")) {
            myCamera.setDownHeld(true);
            logger.info("Down press detected");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (KeyEvent.getKeyText(e.getKeyCode()).equalsIgnoreCase("Left")) {
            myCamera.setLeftHeld(false);
            logger.info("Left press released");
        }
        if (KeyEvent.getKeyText(e.getKeyCode()).equalsIgnoreCase("Right")) {
            myCamera.setRightHeld(false);
            logger.info("Right press released");
        }
        if (KeyEvent.getKeyText(e.getKeyCode()).equalsIgnoreCase("Up")) {
            myCamera.setUpHeld(false);
            logger.info("Up press released");
        }
        if (KeyEvent.getKeyText(e.getKeyCode()).equalsIgnoreCase("Down")) {
            myCamera.setDownHeld(false);
            logger.info("Down press released");
        }
        if (Character.toString(e.getKeyChar()).equalsIgnoreCase("w")) {
            myCamera.setwHeld(false);
            logger.info("w typed released");
        }
        if (Character.toString(e.getKeyChar()).equalsIgnoreCase("s")) {
            myCamera.setsHeld(false);
            logger.info("s typed released");
        }
        if (Character.toString(e.getKeyChar()).equalsIgnoreCase("a")) {
            myCamera.setaHeld(false);
            logger.info("a typed released");
        }
        if (Character.toString(e.getKeyChar()).equalsIgnoreCase("d")) {
            myCamera.setdHeld(false);
            logger.info("d typed released");
        }
        if (Character.toString(e.getKeyChar()).equalsIgnoreCase("q")) {
            myCamera.setqHeld(false);
            logger.info("q typed released");
        }
        if (Character.toString(e.getKeyChar()).equalsIgnoreCase("e")) {
            myCamera.seteHeld(false);
            logger.info("e typed released");
        }
    }
}
