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

    public static ArrayList<Double> crossProduct(ArrayList<Double> u, ArrayList<Double> v) {
        ArrayList<Double> s = new ArrayList<Double>();

        logger.info("u[] = " + u.toString());
        logger.info("v[] = " + v.toString());
        s.add(u.get(1) * v.get(2) - u.get(2) * v.get(1));
        s.add(u.get(2) * v.get(0) - u.get(0) * v.get(2));
        s.add(u.get(0) * v.get(1) - u.get(1) * v.get(0));
        logger.info("s[] = " + s.toString());

        return s;
    }

    public static double cleanNumberTo10dp(double x) {
        x = Math.round(x * (1e10)) / 1e10;
        return x;
    }
}
