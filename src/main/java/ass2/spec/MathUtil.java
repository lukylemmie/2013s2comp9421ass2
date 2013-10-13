package ass2.spec;

/**
 * Created with IntelliJ IDEA.
 * User: Andrew2012
 * Date: 13/10/13
 * Time: 3:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class MathUtil {
    public static double[] crossProduct(double[] u, double[] v) {
        double[] s = new double[3];

        s[0] = u[1] * v[2] - u[2] * v[1];
        s[1] = u[2] * v[0] - u[0] * v[2];
        s[2] = u[0] * v[1] - u[1] * v[0];

        return s;
    }
}
