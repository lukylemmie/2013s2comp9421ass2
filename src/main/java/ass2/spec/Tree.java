package ass2.spec;

import javax.media.opengl.GL2;
import java.util.ArrayList;
import java.util.Arrays;
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

    public Tree(double x, double y, double z) {
        myPos = new double[3];
        myPos[0] = x;
        myPos[1] = y;
        myPos[2] = z;
    }

    public double[] getPosition() {
        return myPos;
    }

    private void drawTrunk(GL2 gl) {
        gl.glPushMatrix();

        gl.glRotated(90, 1, 0, 0);
        gl.glTranslatef(0, 0, -2);

        float[] difColor = {0.5f, 0.25f, 0f, 1};
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, difColor, 0);

        gl.glBegin(GL2.GL_TRIANGLE_FAN);
        {
            gl.glNormal3d(0, 0, -1);
            gl.glVertex3d(0, 0, 0);

            double angleStep = 2 * Math.PI / CIRCLE_STEPS;
            for (int i = 0; i < CIRCLE_STEPS + 1; i++) {
                double x = Math.cos(i * angleStep);
                double y = Math.sin(i * angleStep);

                gl.glVertex3d(x, y, 0);
            }
            gl.glVertex3d(1, 0, 0);
        }
        gl.glEnd();

        gl.glBegin(GL2.GL_TRIANGLE_FAN);
        {
            gl.glNormal3d(0, 0, 1);
            gl.glVertex3d(0, 0, 1);

            double angleStep = 2 * Math.PI / CIRCLE_STEPS;
            for (int i = 0; i < CIRCLE_STEPS + 1; i++) {
                double x = Math.cos(i * angleStep);
                double y = Math.sin(i * angleStep);

                gl.glVertex3d(-x, y, 0);
            }
            gl.glVertex3d(1, 0, 0);

        }
        gl.glEnd();

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

                gl.glNormal3d(x0, y0, 0);
                gl.glVertex3d(x0, y0, 0);
                gl.glVertex3d(x0, y0, 5);

                gl.glNormal3d(x1, y1, 0);
                gl.glVertex3d(x1, y1, 5);
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

        float[] difColor = {0f, 0.05f, 0f, 1};
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, difColor, 0);

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

                Double u[] = {x0, y0, 4d};
                Double v[] = {x1, y1, 4d};

                ArrayList<Double> U = new ArrayList<Double>();
                U.addAll(Arrays.asList(u));
                ArrayList<Double> V = new ArrayList<Double>();
                V.addAll(Arrays.asList(v));
                ArrayList<Double> s = MathUtil.crossProduct(V, U);

                gl.glNormal3d(s.get(0), s.get(1), s.get(2));
                gl.glVertex3d(0, 0, 0);
                gl.glVertex3d(x0, y0, 3);
                gl.glVertex3d(x1, y1, 3);
            }
        }
        gl.glEnd();

        gl.glTranslatef(0, 0, -1);
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

                Double u[] = {x0, y0, 4d};
                Double v[] = {x1, y1, 4d};

                ArrayList<Double> U = new ArrayList<Double>();
                U.addAll(Arrays.asList(u));
                ArrayList<Double> V = new ArrayList<Double>();
                V.addAll(Arrays.asList(v));
                ArrayList<Double> s = MathUtil.crossProduct(V, U);

                gl.glNormal3d(s.get(0), s.get(1), s.get(2));
                gl.glVertex3d(0, 0, 0);
                gl.glVertex3d(x0, y0, 3);
                gl.glVertex3d(x1, y1, 3);
            }
        }
        gl.glEnd();

        gl.glTranslatef(0, 0, -1);
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

                Double u[] = {x0, y0, 4d};
                Double v[] = {x1, y1, 4d};

                ArrayList<Double> U = new ArrayList<Double>();
                U.addAll(Arrays.asList(u));
                ArrayList<Double> V = new ArrayList<Double>();
                V.addAll(Arrays.asList(v));
                ArrayList<Double> s = MathUtil.crossProduct(V, U);

                gl.glNormal3d(s.get(0), s.get(1), s.get(2));
                gl.glVertex3d(0, 0, 0);
                gl.glVertex3d(x0, y0, 3);
                gl.glVertex3d(x1, y1, 3);
            }
        }
        gl.glEnd();

        gl.glPopMatrix();
    }

    public void draw(GL2 gl) {
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glPushMatrix();
        gl.glTranslated(myPos[0], myPos[1], myPos[2]);

        drawTrunk(gl);
        drawTreeTop(gl);

        gl.glPopMatrix();
    }
}
