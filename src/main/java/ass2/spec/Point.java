package ass2.spec;

import sun.rmi.runtime.Log;

import javax.media.opengl.GL2;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * COMMENT: Comment Vertex
 *
 * @author malcolmr
 */
public class Point {
    private static Logger logger = Logger.getLogger(Point.class.getName());
    public double x;
    public double y;
    public double z;

    public Point(double x, double y, double z) {
        logger.setLevel(Level.OFF);
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Point(double[] p) {
        this.x = p[0];
        this.y = p[1];
        this.z = p[2];
    }

    public void draw(GL2 gl) {
        logger.info("x: " + x);
        logger.info("y: " + y);
        logger.info("z: " + z);
        gl.glVertex3d(x, y, z);
    }

    public Point transform(double[][] m) {
        double[] p = new double[4];

        p[0] = x;
        p[1] = y;
        p[2] = z;
        p[3] = 1;

        double[] q = MatrixMath.multiply(m, p);
        return new Point(q);
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("(");
        sb.append(x);
        sb.append(", ");
        sb.append(y);
        sb.append(", ");
        sb.append(z);
        sb.append(")");

        return sb.toString();
    }

}
