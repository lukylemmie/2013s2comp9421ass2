package ass2.spec;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLProfile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * COMMENT: Comment Tree
 *
 * @author malcolmr
 */
public class Tree {
    private static Logger logger = Logger.getLogger(Tree.class.getName());

    private static final int CIRCLE_STEPS = 10;
    private double[] myPos;
    private Texture trunkTexture = null;
    private Texture topTexture = null;
    private Double topRotation[] = new Double[3];
    private double theta1 = 0;
    private double theta2 = 0;
    private double theta3 = 0;

    public Tree(double x, double y, double z) {
        logger.setLevel(Level.OFF);
        myPos = new double[3];
        myPos[0] = x;
        myPos[1] = y;
        myPos[2] = z;
        topRotation[0] = new Double(0);
        topRotation[1] = new Double(0);
        topRotation[2] = new Double(0);
    }

    public double[] getPosition() {
        return myPos;
    }

    public void loadTexture(GL2 gl) {
        trunkTexture = new Texture(GLProfile.getDefault(), gl, "barkTexture.jpg", "jpg");
        topTexture = new Texture(GLProfile.getDefault(), gl, "treeTop2.jpg", "jpg");
    }

    private void drawTrunk(GL2 gl) {
        gl.glPushMatrix();

        gl.glRotated(90, 1, 0, 0);
        gl.glTranslatef(0, 0, -2);

//        float[] difColor = {0.5f, 0.25f, 0f, 1};
        float[] difColor = {1f, 1f, 1f, 1};
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, difColor, 0);

        gl.glBegin(GL2.GL_QUADS);
        {
            double angleStep = 2 * Math.PI / CIRCLE_STEPS;
            for (int i = 0; i < CIRCLE_STEPS; i++) {
                double a0 = i * angleStep;
                double a1 = ((i + 1) % CIRCLE_STEPS) * angleStep;

                double x0 = Math.cos(a0);
                double y0 = Math.sin(a0);

                double x1 = Math.cos(a1);
                double y1 = Math.sin(a1);

                double a[] = {x0, y0, 0};
                double b[] = {x0, y0, 7};
                double c[] = {x1, y1, 7};
                double u[] = {a[0] - b[0], a[1] - b[1], a[2] - b[2]};
                double v[] = {b[0] - c[0], b[1] - c[1], b[2] - c[2]};
                double s[] = MathUtil.crossProduct(v, u);

                gl.glNormal3d(s[0], s[1], s[2]);
                gl.glTexCoord2d(0, 0);
                gl.glVertex3d(x0, y0, 0);
                gl.glTexCoord2d(1, 0);
                gl.glVertex3d(x0, y0, 7);
                gl.glTexCoord2d(1, 1);
                gl.glVertex3d(x1, y1, 7);
                gl.glTexCoord2d(0, 1);
                gl.glVertex3d(x1, y1, 0);
            }
        }
        gl.glEnd();

        gl.glPopMatrix();
    }

    private void drawTreeTop(GL2 gl) {
        gl.glPushMatrix();

        gl.glRotated(90, 1, 0, 0);
        gl.glTranslated(0, 0, -4);

        float[] difColor = {1f, 1f, 1f, 1};
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, difColor, 0);

        spinTree();

        gl.glRotated(theta1, 0, 0, 1);
        drawOneTop(gl);

        gl.glTranslatef(0, 0, -1.5f);
        gl.glRotated(theta2, 0, 0, 1);
        logger.info("theta2 = " + theta2);
        drawOneTop(gl);

        gl.glTranslatef(0, 0, -1.5f);
        gl.glRotated(theta3, 0, 0, 1);
        drawOneTop(gl);

        gl.glPopMatrix();
    }

    private void drawOneTop(GL2 gl) {
        gl.glBegin(GL2.GL_TRIANGLES);
        {
            double angleStep = 2 * Math.PI / CIRCLE_STEPS;
            for (int i = 0; i < CIRCLE_STEPS; i++) {
                double a0 = i * angleStep;
                double a1 = ((i + 1) % CIRCLE_STEPS) * angleStep;

                double x0 = Math.cos(a0) * 2;
                double y0 = Math.sin(a0) * 2;

                double x1 = Math.cos(a1) * 2;
                double y1 = Math.sin(a1) * 2;

                double u[] = {x0, y0, 4d};
                double v[] = {x1, y1, 4d};

                double[] s = MathUtil.crossProduct(v, u);

                gl.glNormal3d(s[0], s[1], s[2]);
                gl.glTexCoord2d(0, 1);
                gl.glVertex3d(0, 0, 0);
                gl.glTexCoord2d(1, 1);
                gl.glVertex3d(x0, y0, 4);
                gl.glTexCoord2d(0, 0);
                gl.glVertex3d(x1, y1, 4);
            }
        }
        gl.glEnd();
    }

    public void draw(GL2 gl) {
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glPushMatrix();
        gl.glTranslated(myPos[0], myPos[1] + 0.5, myPos[2]);
        gl.glScalef(0.2f, 0.2f, 0.2f);

        // use the texture to modulate diffuse and ambient lighting
        gl.glTexEnvf(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_MODULATE);
        // bind the texture
        gl.glBindTexture(GL.GL_TEXTURE_2D, trunkTexture.getTextureID());
        drawTrunk(gl);
        gl.glBindTexture(GL.GL_TEXTURE_2D, topTexture.getTextureID());
        drawTreeTop(gl);

        gl.glPopMatrix();
    }

    private void spinTree() {
        theta1++;
        theta2 -= 2;
        theta3 += 3;
    }
}
