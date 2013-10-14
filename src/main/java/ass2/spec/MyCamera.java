package ass2.spec;

import javax.media.opengl.GL2;

/**
 * Created with IntelliJ IDEA.
 * User: Andrew2012
 * Date: 14/10/13
 * Time: 10:11 AM
 * To change this template use File | Settings | File Templates.
 */
public class MyCamera {
    public static final double CAMERA_DIST = 5;
    private double verticalRotation = 90;
    private double horizontalRotation = 0;
    private boolean upHeld = false;
    private boolean downHeld = false;
    private boolean leftHeld = false;
    private boolean rightHeld = false;
    private boolean wHeld = false;
    private boolean sHeld = false;
    private boolean aHeld = false;
    private boolean dHeld = false;
    private boolean qHeld = false;
    private boolean eHeld = false;
    private double xPos = 0;
    private double yPos = -3;
    private double zPos = 0;

    public MyCamera() {
    }

    public void setCamera(GL2 gl) {
        gl.glClearColor(0.4f, 0.4f, 1, 1);
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();

//        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_LINE);
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
        gl.glColor4d(0, 0, 0, 1);

        if (upHeld) verticalRotationDown();
        if (downHeld) verticalRotationUp();
        if (leftHeld) horizontalRotationDown();
        if (rightHeld) horizontalRotationUp();
        if (wHeld) zPosUp();
        if (sHeld) zPosDown();
        if (aHeld) xPosUp();
        if (dHeld) xPosDown();
        if (qHeld) yPosUp();
        if (eHeld) yPosDown();
        gl.glRotated(verticalRotation, 1, 0, 0);
        gl.glRotated(horizontalRotation, 0, 1, 0);
        gl.glTranslated(xPos, yPos, zPos);
    }

    public void setUpHeld(boolean upHeld) {
        this.upHeld = upHeld;
    }

    public void setDownHeld(boolean downHeld) {
        this.downHeld = downHeld;
    }

    public void setLeftHeld(boolean leftHeld) {
        this.leftHeld = leftHeld;
    }

    public void setRightHeld(boolean rightHeld) {
        this.rightHeld = rightHeld;
    }

    public void verticalRotationUp() {
        verticalRotation++;
    }

    public void verticalRotationDown() {
        verticalRotation--;
    }

    public void horizontalRotationUp() {
        horizontalRotation++;
    }

    public void horizontalRotationDown() {
        horizontalRotation--;
    }

    public void xPosUp() {
        xPos += 0.1 * Math.cos(Math.toRadians(horizontalRotation));
        zPos += 0.1 * Math.sin(Math.toRadians(horizontalRotation));
    }

    public void xPosDown() {
        xPos -= 0.1 * Math.cos(Math.toRadians(horizontalRotation));
        zPos -= 0.1 * Math.sin(Math.toRadians(horizontalRotation));
    }

    public void yPosUp() {
        yPos += 0.1;
    }

    public void yPosDown() {
        yPos -= 0.1;
    }

    public void zPosUp() {
        zPos += 0.1 * Math.cos(Math.toRadians(horizontalRotation));
        xPos -= 0.1 * Math.sin(Math.toRadians(horizontalRotation));
    }

    public void zPosDown() {
        zPos -= 0.1 * Math.cos(Math.toRadians(horizontalRotation));
        xPos += 0.1 * Math.sin(Math.toRadians(horizontalRotation));
    }

    public void setwHeld(boolean wHeld) {
        this.wHeld = wHeld;
    }

    public void setsHeld(boolean sHeld) {
        this.sHeld = sHeld;
    }

    public void setaHeld(boolean aHeld) {
        this.aHeld = aHeld;
    }

    public void setdHeld(boolean dHeld) {
        this.dHeld = dHeld;
    }

    public void setqHeld(boolean qHeld) {
        this.qHeld = qHeld;
    }

    public void seteHeld(boolean eHeld) {
        this.eHeld = eHeld;
    }
}
