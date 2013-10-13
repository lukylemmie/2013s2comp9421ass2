package ass2.spec;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Andrew2012
 * Date: 13/10/13
 * Time: 3:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class MathUtil {
    private static Logger logger = Logger.getLogger(MathUtil.class.getName());

    public static double[] crossProduct(double[] u, double[] v) {
        double[] s = new double[3];

        logger.info("u[] = " + u.toString());
        logger.info("v[] = " + v.toString());
        s[0] = u[1] * v[2] - u[2] * v[1];
        s[1] = u[2] * v[0] - u[0] * v[2];
        s[2] = u[0] * v[1] - u[1] * v[0];
        logger.info("s[] = " + s.toString());

        return s;
    }

    public static double cleanNumberTo10dp(double x) {
        x = Math.round(x * (1e10)) / 1e10;
        return x;
    }

    //@param x is the first value
    //@param y is the second value
    //@param t is the weighting between the points
    public static double interpolate(double x, double y, double t) {
        return ((1 - t) * x + t * y);
    }
}
