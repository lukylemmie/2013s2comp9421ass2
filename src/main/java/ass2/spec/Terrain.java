package ass2.spec;

import javax.media.opengl.GL2;
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
    }

    public Terrain(Dimension size) {
        this(size.width, size.height);
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
        altitudeX1X2Z1 = interpolate(altitudeX1Z1, altitudeX2Z1, dx);
        altitudeX1X2Z2 = interpolate(altitudeX1Z2, altitudeX2Z2, dx);

        //handle the other direction
        altitude = interpolate(altitudeX1X2Z1, altitudeX1X2Z2, dz);

        //fix rounding errors
        altitude = MathUtil.cleanNumberTo10dp(altitude);

        return altitude;
    }

    //@param x is the first value
    //@param y is the second value
    //@param t is the weighting between the points
    public double interpolate(double x, double y, double t) {
        return ((1 - t) * x + t * y);
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

    }
}
