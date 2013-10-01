package ass2.spec;

import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.media.opengl.GL2;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;


//import sailing.objects.Island;

/**
 * COMMENT: Comment HeightMap 
 *
 * @author malcolmr
 */
public class Terrain {
    static Logger logger = Logger.getLogger(Terrain.class.getName());
    private Dimension mySize;
    private double[][] myAltitude;
    private List<Tree> myTrees;
    private List<Road> myRoads;

    public Terrain(int width, int height) {
        mySize = new Dimension(width, height);
        myAltitude = new double[width][height];
        myTrees = new ArrayList<Tree>();
        myRoads = new ArrayList<Road>();
    }

    public Terrain(double[][] altitudeSet){
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

    public double getGridAltitude(int x, int z) {
        return myAltitude[x][z];
    }

    public void setGridAltitude(int x, int z, double h) {
        myAltitude[x][z] = h;
    }

    /**
     * Get the altitude at a point. 
     * Non-integer points should be interpolated from neighbouring grid points
     * 
     * TO BE COMPLETED
     * 
     * @param x
     * @param z
     * @return
     */

    public double altitude(double x, double z){
        double altitude = 0;

        altitude = altitude(x, z, false);

        return altitude;
    }

    public double altitude(double x, double z, boolean DEBUG) {
        double altitude = 0, altitudeX1Z1, altitudeX1Z2, altitudeX2Z1, altitudeX2Z2;
        int x1, x2, z1, z2;
        double altitudeX1X2Z1, altitudeX1X2Z2, dx, dz;

        x1 = (int) x;
        dx = x - x1;
        if(x1 == x){
            x2 = x1;
        } else {
            x2 = x1 + 1;
        }
        logger.info("x1 = " + x1 + "; x2 = " + x2 + "; dx = " + dx);

        z1 = (int) z;
        dz = z - z1;
        if(z1 == z){
            z2 = z1;
        } else {
            z2 = z1 + 1;
        }
        logger.info("z1 = " + z1 + "; z2 = " + z2 + "; dz = " + dz);

        altitudeX1Z1 = myAltitude[x1][z1];
        altitudeX1Z2 = myAltitude[x1][z2];
        altitudeX2Z1 = myAltitude[x2][z1];
        altitudeX2Z2 = myAltitude[x2][z2];

        altitudeX1X2Z1 = dx * altitudeX1Z1 + (1 - dx) * altitudeX2Z1;
        altitudeX1X2Z2 = dx * altitudeX1Z2 + (1 - dx) * altitudeX2Z2;

        altitude = dz * altitudeX1X2Z1 + (1 - dz) * altitudeX1X2Z2;

        if(DEBUG){
            System.out.println("x1 = " + x1 + "; x2 = " + x2 + "; z1 = " + z1 + "; z2 = " + z2);
            System.out.println("altitudeX1Z1 = " + altitudeX1Z1 + "; altitudeX1Z2 = " + altitudeX1Z2 +
                    "; altitudeX2Z1 = " + altitudeX2Z1 + "; altitudeX2Z2 = " + altitudeX2Z2);
            System.out.println("altitudeX1X2Z1 = " + altitudeX1X2Z1 + "; altitudeX1X2Z2 = " + altitudeX1X2Z2);
        }

        return altitude;
    }

    public void addTree(double x, double z) {
        double y = altitude(x, z);
        Tree tree = new Tree(x, y, z);
        myTrees.add(tree);
    }


    public void addRoad(int width, double[] spine) {
        Road road = new Road(width, spine);
        myRoads.add(road);
    }


}
