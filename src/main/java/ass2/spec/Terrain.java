package ass2.spec;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLProfile;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * COMMENT: Comment HeightMap
 *
 * @author malcolmr
 */
public class Terrain {
    private Logger logger = Logger.getLogger(Terrain.class.getName());
    private Dimension mySize;
    private double[][] myAltitude;
    private List<Tree> myTrees;
    private List<Road> myRoads;
    private float[] mySunlight;
    private Texture terrainTexture = null;

    /**
     * Create a new terrain
     *
     * @param width The number of vertices in the x-direction
     * @param depth The number of vertices in the z-direction
     */
    public Terrain(int width, int depth) {
        mySize = new Dimension(width, depth);
        myAltitude = new double[width][depth];
        myTrees = new ArrayList<Tree>();
        myRoads = new ArrayList<Road>();
        mySunlight = new float[3];
    }

    //
    @Deprecated
    public Terrain(double[][] altitudeSet) {
        mySize = new Dimension(altitudeSet.length, altitudeSet[0].length);
        myAltitude = altitudeSet.clone();
        myTrees = new ArrayList<Tree>();
        myRoads = new ArrayList<Road>();
        mySunlight = new float[3];
        mySunlight[0] = 2f;
        mySunlight[1] = 2f;
        mySunlight[2] = 2f;
    }

    public Terrain(Dimension size) {
        this(size.width, size.height);
    }

    public void loadTexture(GL2 gl) {
        terrainTexture = new Texture(GLProfile.getDefault(), gl, "BrightPurpleMarble.png", "png");
        for (Tree tree : myTrees) {
            tree.loadTexture(gl);
        }
    }

    public Dimension size() {
        return mySize;
    }

    public List<Tree> trees() {
        return myTrees;
    }

    public List<Road> roads() {
        return myRoads;
    }

    public float[] getSunlight() {
        return mySunlight;
    }

    /**
     * Set the sunlight direction.
     * <p/>
     * Note: the sun should be treated as a directional light, without a position
     *
     * @param dx
     * @param dy
     * @param dz
     */
    public void setSunlightDir(float dx, float dy, float dz) {
        mySunlight[0] = dx;
        mySunlight[1] = dy;
        mySunlight[2] = dz;
    }

    /**
     * Resize the terrain, copying any old altitudes.
     *
     * @param width
     * @param height
     */
    public void setSize(int width, int height) {
        mySize = new Dimension(width, height);
        double[][] oldAlt = myAltitude;
        myAltitude = new double[width][height];

        for (int i = 0; i < width && i < oldAlt.length; i++) {
            for (int j = 0; j < height && j < oldAlt[i].length; j++) {
                myAltitude[i][j] = oldAlt[i][j];
            }
        }
    }

    /**
     * Get the altitude at a grid point
     *
     * @param x
     * @param z
     * @return
     */
    public double getGridAltitude(int x, int z) {
        return myAltitude[x][z];
    }

    /**
     * Set the altitude at a grid point
     *
     * @param x
     * @param z
     * @return
     */
    public void setGridAltitude(int x, int z, double h) {
        myAltitude[x][z] = h;
    }

    /**
     * Get the altitude at an arbitrary point.
     * Non-integer points should be interpolated from neighbouring grid points
     * <p/>
     * TO BE COMPLETED
     *
     * @param x
     * @param z
     * @return
     */
    public double altitude(double x, double z) {
        double altitude = 0, altitudeX1Z1, altitudeX1Z2, altitudeX2Z1, altitudeX2Z2;
        int x1, x2, z1, z2;
        double altitudeX1X2Z1, altitudeX1X2Z2, dx, dz;

        x1 = (int) Math.floor(x);
        dx = x - x1;
        x2 = (int) Math.ceil(x);
        logger.info("x1 = " + x1 + "; x2 = " + x2 + "; dx = " + dx);

        z1 = (int) Math.floor(z);
        dz = z - z1;
        z2 = (int) Math.ceil(z);
        logger.info("z1 = " + z1 + "; z2 = " + z2 + "; dz = " + dz);

        altitudeX1Z1 = myAltitude[x1][z1];
        altitudeX1Z2 = myAltitude[x1][z2];
        altitudeX2Z1 = myAltitude[x2][z1];
        altitudeX2Z2 = myAltitude[x2][z2];

        //handle 1 direction
        altitudeX1X2Z1 = MathUtil.interpolate(altitudeX1Z1, altitudeX2Z1, dx);
        altitudeX1X2Z2 = MathUtil.interpolate(altitudeX1Z2, altitudeX2Z2, dx);

        //handle the other direction
        altitude = MathUtil.interpolate(altitudeX1X2Z1, altitudeX1X2Z2, dz);

        //fix rounding errors
        altitude = MathUtil.cleanNumberTo10dp(altitude);

        return altitude;
    }

    /**
     * Add a tree at the specified (x,z) point.
     * The tree's y coordinate is calculated from the altitude of the terrain at that point.
     *
     * @param x
     * @param z
     */
    public void addTree(double x, double z) {
        double y = altitude(x, z);
        Tree tree = new Tree(x, y, z);
        myTrees.add(tree);
    }


    /**
     * Add a road.
     *
     * @param width
     * @param spine
     */
    public void addRoad(double width, double[] spine) {
        Road road = new Road(width, spine);
        myRoads.add(road);
    }

    public void draw(GL2 gl) {
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glPushMatrix();

        float[] pos = {mySunlight[0], mySunlight[1], mySunlight[2], 1f};
        float[] a = {1.0f, 1.0f, 1.0f, 1f};
        float[] d = {1.0f, 1.0f, 1.0f, 1f};
        float[] s = {1.0f, 1.0f, 1.0f, 1f};

        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, pos, 0);
//        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPOT_DIRECTION, pos, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, a, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, d, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, s, 0);

        // use the texture to modulate diffuse and ambient lighting
        gl.glTexEnvf(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_MODULATE);
        // bind the texture
        gl.glBindTexture(GL.GL_TEXTURE_2D, terrainTexture.getTextureID());
        for (int i = 0; i < myAltitude.length - 1; i++) {
            for (int j = 0; j < myAltitude[i].length - 1; j++) {
                drawSection1(gl, i, j);
                drawSection2(gl, i, j);
                drawSection3(gl, i, j);
                drawSection4(gl, i, j);
            }
        }


        gl.glPopMatrix();

        for (Tree tree : myTrees) {
            tree.draw(gl);
        }
    }

    private void drawSection1(GL2 gl, int i, int j) {
        gl.glPushMatrix();

//        float[] difColor = {0f, 0.7f, 0f, 1};
        float[] difColor = {1f, 1f, 1f, 1};

        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, difColor, 0);

        gl.glBegin(GL2.GL_TRIANGLES);
        {
            double a[] = {Double.valueOf(i), myAltitude[i][j], Double.valueOf(j)};
            double b[] = {Double.valueOf(i), myAltitude[i][j + 1], Double.valueOf(j + 1)};
            double c[] = {i + 0.5, altitude(i + 0.5, j + 0.5), j + 0.5};
            double u[] = {a[0] - b[0], a[1] - b[1], a[2] - b[2]};
            double v[] = {b[0] - c[0], b[1] - c[1], b[2] - c[2]};
            double s[] = MathUtil.crossProduct(u, v);

            gl.glNormal3d(s[0], s[1], s[2]);
            gl.glTexCoord2d(0, 0);
            gl.glVertex3d(a[0], a[1], a[2]);
            gl.glTexCoord2d(0, 1);
            gl.glVertex3d(b[0], b[1], b[2]);
            gl.glTexCoord2d(0.5, 0.5);
            gl.glVertex3d(c[0], c[1], c[2]);
        }
        gl.glEnd();

        gl.glPopMatrix();
    }

    private void drawSection2(GL2 gl, int i, int j) {
        gl.glPushMatrix();

        gl.glTranslated(i, 0, j);

//        float[] difColor = {0f, 0f, 0.7f, 1};
        float[] difColor = {1f, 1f, 1f, 1};

        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, difColor, 0);

        gl.glBegin(GL2.GL_TRIANGLES);
        {
            double a[] = {Double.valueOf(0), myAltitude[i][j + 1], Double.valueOf(1)};
            double b[] = {Double.valueOf(1), myAltitude[i + 1][j + 1], Double.valueOf(1)};
            double c[] = {0.5, altitude(i + 0.5, j + 0.5), 0.5};
            double u[] = {a[0] - b[0], a[1] - b[1], a[2] - b[2]};
            double v[] = {b[0] - c[0], b[1] - c[1], b[2] - c[2]};
            double s[] = MathUtil.crossProduct(u, v);

            gl.glNormal3d(s[0], s[1], s[2]);
            gl.glTexCoord2d(0, 1);
            gl.glVertex3d(a[0], a[1], a[2]);
            gl.glTexCoord2d(1, 1);
            gl.glVertex3d(b[0], b[1], b[2]);
            gl.glTexCoord2d(0.5, 0.5);
            gl.glVertex3d(c[0], c[1], c[2]);
        }
        gl.glEnd();

        gl.glPopMatrix();
    }

    private void drawSection3(GL2 gl, int i, int j) {
        gl.glPushMatrix();

        gl.glTranslated(i, 0, j);

//        float[] difColor = {0.7f, 0f, 0f, 1};
        float[] difColor = {1f, 1f, 1f, 1};

        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, difColor, 0);

        gl.glBegin(GL2.GL_TRIANGLES);
        {
            double a[] = {Double.valueOf(1), myAltitude[i + 1][j + 1], Double.valueOf(1)};
            double b[] = {Double.valueOf(1), myAltitude[i + 1][j], Double.valueOf(0)};
            double c[] = {0.5, altitude(i + 0.5, j + 0.5), 0.5};
            double u[] = {a[0] - b[0], a[1] - b[1], a[2] - b[2]};
            double v[] = {b[0] - c[0], b[1] - c[1], b[2] - c[2]};
            double s[] = MathUtil.crossProduct(u, v);

            gl.glNormal3d(s[0], s[1], s[2]);
            gl.glTexCoord2d(1, 1);
            gl.glVertex3d(a[0], a[1], a[2]);
            gl.glTexCoord2d(1, 0);
            gl.glVertex3d(b[0], b[1], b[2]);
            gl.glTexCoord2d(0.5, 0.5);
            gl.glVertex3d(c[0], c[1], c[2]);
        }
        gl.glEnd();

        gl.glPopMatrix();
    }

    private void drawSection4(GL2 gl, int i, int j) {
        gl.glPushMatrix();

        gl.glTranslated(i, 0, j);

//        float[] difColor = {0.7f, 0.7f, 0.7f, 1};
        float[] difColor = {1f, 1f, 1f, 1};

        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, difColor, 0);

        gl.glBegin(GL2.GL_TRIANGLES);
        {
            double a[] = {Double.valueOf(1), myAltitude[i + 1][j], Double.valueOf(0)};
            double b[] = {Double.valueOf(0), myAltitude[i][j], Double.valueOf(0)};
            double c[] = {0.5, altitude(i + 0.5, j + 0.5), 0.5};
            double u[] = {a[0] - b[0], a[1] - b[1], a[2] - b[2]};
            double v[] = {b[0] - c[0], b[1] - c[1], b[2] - c[2]};
            double s[] = MathUtil.crossProduct(u, v);

            gl.glNormal3d(s[0], s[1], s[2]);
            gl.glTexCoord2d(1, 0);
            gl.glVertex3d(a[0], a[1], a[2]);
            gl.glTexCoord2d(0, 0);
            gl.glVertex3d(b[0], b[1], b[2]);
            gl.glTexCoord2d(0.5, 0.5);
            gl.glVertex3d(c[0], c[1], c[2]);
        }
        gl.glEnd();

        gl.glPopMatrix();
    }

    public void fixSunlight() {
        mySunlight[1] = -mySunlight[1];
    }
}
