package ass2.spec;

import drivingExample.Wheel;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLProfile;

/**
 * Created with IntelliJ IDEA.
 * User: Andrew2012
 * Date: 20/10/13
 * Time: 11:25 AM
 * To change this template use File | Settings | File Templates.
 */
public class Helicopter {
    private static final double WIDTH = 1;
    private static final double LENGTH = 2;
    private static final double HEIGHT = 0.5;
    private Texture heliTexture;

    public Helicopter() {

    }

    public void draw(GL2 gl) {
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glPushMatrix();

        drawBody(gl);

        gl.glPopMatrix();
    }

    public void loadTexture(GL2 gl) {
        heliTexture = new Texture(GLProfile.getDefault(), gl, "plane.png", "png");
    }

    private void drawBody(GL2 gl) {
        float[] blackColor = {0f, 0f, 0f, 1};
        float[] whiteColor = {1f, 1f, 1f, 1};

        gl.glPushMatrix();

        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, blackColor, 0);
//        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT, blackColor, 0);
//        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, whiteColor, 0);
        // use the texture to modulate diffuse and ambient lighting
        gl.glTexEnvf(GL2.GL_TEXTURE_ENV, GL2.GL_BLEND, GL2.GL_MODULATE);
        // bind the texture
        gl.glBindTexture(GL.GL_TEXTURE_2D, heliTexture.getTextureID());

        gl.glScaled(WIDTH, HEIGHT, LENGTH);
        gl.glBegin(GL2.GL_QUADS);
        {
            gl.glNormal3d(0, 0, 1);

            gl.glTexCoord2d(0, 0);
            gl.glVertex3d(-1, -1, 0);
            gl.glTexCoord2d(0, 1);
            gl.glVertex3d(-1, 1, 0);
            gl.glTexCoord2d(1, 1);
            gl.glVertex3d(1, 1, 0);
            gl.glTexCoord2d(1, 0);
            gl.glVertex3d(1, -1, 0);
        }
        gl.glEnd();

        gl.glPopMatrix();
    }
}
