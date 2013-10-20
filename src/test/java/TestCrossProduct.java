import ass2.spec.MathUtil;
import ass2.spec.Terrain;
import org.junit.Test;

import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Andrew2012
 * Date: 19/10/13
 * Time: 7:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestCrossProduct {
    static Logger logger = Logger.getLogger(TestCrossProduct.class.getName());

    @Test
    public void testSet1() {
        double[] u = {1, 0, 0};
        double[] v = {0, 1, 0};
        double[] cp = MathUtil.crossProduct(u, v);
        double[] s = MathUtil.sumVectors(u, cp);
        double m = MathUtil.magnitude(s);

        logger.info("cp = {" + cp[0] + ", " + cp[1] + ", " + cp[2] + "}");
        logger.info("s = {" + s[0] + ", " + s[1] + ", " + s[2] + "}");
        logger.info("cp = {" + cp[0] + ", " + cp[1] + ", " + cp[2] + "}");
        logger.info("m = " + m);
    }
}
