import ass2.spec.Terrain;
import org.junit.Assert;
import org.junit.Test;

import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Andrew2012
 * Date: 23/09/13
 * Time: 11:09 AM
 * To change this template use File | Settings | File Templates.
 */
public class TestAltitude {
    static Logger logger = Logger.getLogger(TestAltitude.class.getName());

    private static final double[][] altitudeSet1 = new double[][]
            {
                    {0, 0},
                    {0, 0}
            };

    private static final double[][] altitudeSet2 = new double[][]
            {
                    {0, 1},
                    {0, 1}
            };

    private static final double[][] altitudeSet3 = new double[][]
            {
                    {0, 0},
                    {1, 1}
            };

    private static final double[][] altitudeSet4 = new double[][]
            {
                    {0, 0},
                    {0, 1}
            };

    private static final double[][] altitudeSet5 = new double[][]
            {
                    {2, 8},
                    {6, 10}
            };

    @Test
    public void testSet1(){
        Terrain terrain = new Terrain(altitudeSet1);
        logger.info("\nRunning tests on altitudeSet1...\n");
        expectedInputOutput(0.3, 0.7, 0, terrain);
        expectedInputOutput(0.2, 0.9, 0, terrain);
        expectedInputOutput(0, 0, 0, terrain);
//        expectedInputOutput(1, 1, 0, terrain);
    }

    @Test
    public void testSet4(){
        Terrain terrain = new Terrain(altitudeSet4);
        logger.info("\nRunning tests on altitudeSet4...\n");
        expectedInputOutput(0, 0, 0, terrain);
        expectedInputOutput(0, 1, 0, terrain);
        expectedInputOutput(1, 0, 0, terrain);
        expectedInputOutput(1, 1, 1, terrain);
        expectedInputOutput(0, 0.5, 0, terrain);
        expectedInputOutput(0.5, 0, 0, terrain);
        expectedInputOutput(0.5, 1, 0.5, terrain);
        expectedInputOutput(1, 0.5, 0.5, terrain);
        expectedInputOutput(0.5, 0.5, 0.25, terrain);
    }

    @Test
    public void testSet5(){
        Terrain terrain = new Terrain(altitudeSet5);
        logger.info("\nRunning tests on altitudeSet5...\n");
        expectedInputOutput(0, 0, 2, terrain);
        expectedInputOutput(0, 1, 8, terrain);
        expectedInputOutput(1, 0, 6, terrain);
        expectedInputOutput(1, 1, 10, terrain);
        expectedInputOutput(0.5, 0, 4, terrain);
        expectedInputOutput(0, 0.5, 5, terrain);
        expectedInputOutput(1, 0.5, 8, terrain);
        expectedInputOutput(0.5, 1, 9, terrain);
        expectedInputOutput(0.5, 0.5, 6.5, terrain);
    }

    private static void expectedInputOutput(double x, double z, double altitude, Terrain terrain){
        double calculatedAltitude = terrain.altitude(x, z, false);
        logger.info("Testing: x = " + x + "; z = " + z + "; expected altitude = " + altitude +
                "; calculated altitude = " + calculatedAltitude);
        Assert.assertTrue(altitude == calculatedAltitude);
        logger.info("Expected altitude calculated\n");
    }
}
