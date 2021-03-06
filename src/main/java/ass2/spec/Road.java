package ass2.spec;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLProfile;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * COMMENT: Comment Road
 *
 * @author malcolmr
 */
// extrusion code from Malcolm Ryan's extrusion example
public class Road {
    private static Logger logger = Logger.getLogger(Road.class.getName());
    private Texture roadTexture = null;
    private Terrain myTerrain;
    private List<Double> myPoints;
    private double myWidth;
    private List<Polygon> myMesh = null;

    public void setMyTerrain(Terrain myTerrain) {
        this.myTerrain = myTerrain;
    }

    /**
     * Create a new road starting at the specified point
     */
    public Road(double width, double x0, double y0) {
        myWidth = width;
        myPoints = new ArrayList<Double>();
        myPoints.add(x0);
        myPoints.add(y0);
        logger.setLevel(Level.OFF);
    }

    /**
     * Create a new road with the specified spine
     *
     * @param width
     * @param spine
     */
    public Road(double width, double[] spine) {
        myWidth = width;
        myPoints = new ArrayList<Double>();
        for (int i = 0; i < spine.length; i++) {
            myPoints.add(spine[i]);
        }
        logger.setLevel(Level.OFF);
    }

    /**
     * The width of the road.
     *
     * @return
     */
    public double width() {
        return myWidth;
    }

    /**
     * Add a new segment of road, beginning at the last point added and ending at (x3, y3).
     * (x1, y1) and (x2, y2) are interpolated as bezier control points.
     *
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @param x3
     * @param y3
     */
    public void addSegment(double x1, double y1, double x2, double y2, double x3, double y3) {
        myPoints.add(x1);
        myPoints.add(y1);
        myPoints.add(x2);
        myPoints.add(y2);
        myPoints.add(x3);
        myPoints.add(y3);
    }

    /**
     * Get the number of segments in the curve
     *
     * @return
     */
    public int size() {
        return myPoints.size() / 6;
    }

    /**
     * Get the specified control point.
     *
     * @param i
     * @return
     */
    public double[] controlPoint(int i) {
        double[] p = new double[2];
        p[0] = myPoints.get(i * 2);
        p[1] = myPoints.get(i * 2 + 1);
        return p;
    }

    /**
     * Get a point on the spine. The parameter t may vary from 0 to size().
     * Points on the kth segment take have parameters in the range (k, k+1).
     *
     * @param t
     * @return
     */
    public double[] point(double t) {
        int i = (int) Math.floor(t);
        t = t - i;

        i *= 6;

        double x0 = myPoints.get(i++);
        double y0 = myPoints.get(i++);
        double x1 = myPoints.get(i++);
        double y1 = myPoints.get(i++);
        double x2 = myPoints.get(i++);
        double y2 = myPoints.get(i++);
        double x3 = myPoints.get(i++);
        double y3 = myPoints.get(i++);

        double[] p = new double[2];

        p[0] = b(0, t) * x0 + b(1, t) * x1 + b(2, t) * x2 + b(3, t) * x3;
        p[1] = b(0, t) * y0 + b(1, t) * y1 + b(2, t) * y2 + b(3, t) * y3;

        return p;
    }

    /**
     * Calculate the Bezier coefficients
     *
     * @param i
     * @param t
     * @return
     */
    private double b(int i, double t) {

        switch (i) {

            case 0:
                return (1 - t) * (1 - t) * (1 - t);

            case 1:
                return 3 * (1 - t) * (1 - t) * t;

            case 2:
                return 3 * (1 - t) * t * t;

            case 3:
                return t * t * t;
        }

        // this should never happen
        throw new IllegalArgumentException("" + i);
    }

    // extrusion code from Malcolm Ryan's extrusion example

    public void draw(GL2 gl) {
        List<Point> spine = getSpine();
        logger.info("Road size " + size());
        logger.info("My Points size " + myPoints.size());
        logger.info("myPoints: " + myPoints);
        logger.info(spine.toString());

        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glPushMatrix();

        float[] difColor = {1f, 1f, 1f, 1};
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, difColor, 0);

        // use the texture to modulate diffuse and ambient lighting
        gl.glTexEnvf(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_MODULATE);
        // bind the texture
        gl.glBindTexture(GL.GL_TEXTURE_2D, roadTexture.getTextureID());
        // draw the mesh as a sequence of polygons
        logger.info("Calling getMesh()");
        List<Polygon> mesh = getMesh();
        if (mesh != null) {

            for (Polygon p : mesh) {
                p.draw(gl);
            }
        }
        gl.glPopMatrix();

//        gl.glMatrixMode(GL2.GL_MODELVIEW);
//        gl.glPushMatrix();
//
//        float[] difColor = {0f, 0f, 0f, 1};
//        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, difColor, 0);
//
//        gl.glBegin(GL2.GL_QUADS);
//        {
//            for (Point point: spine) {
//                double x0 = point.x - myWidth/2;
//                double y0 = point.y;
//                double z0 = point.z - myWidth/2;
//
//                double x1 = point.x + myWidth/2;
//                double y1 = point.y;
//                double z1 = point.z - myWidth/2;
//
//                double x2 = point.x + myWidth/2;
//                double y2 = point.y;
//                double z2 = point.z + myWidth/2;
//
//                double x3 = point.x - myWidth/2;
//                double y3 = point.y;
//                double z3 = point.z + myWidth/2;
//
//                double a[] = {x0, y0, z0};
//                double b[] = {x1, y1, z1};
//                double c[] = {x2, y2, z2};
//                double u[] = {a[0] - b[0], a[1] - b[1], a[2] - b[2]};
//                double v[] = {b[0] - c[0], b[1] - c[1], b[2] - c[2]};
//                double s[] = MathUtil.crossProduct(v, u);
//
//                gl.glNormal3d(s[0], s[1], s[2]);
//                gl.glVertex3d(x0, y0, z0);
//                gl.glVertex3d(x1, y1, z1);
//                gl.glVertex3d(x2, y2, z2);
//                gl.glVertex3d(x3, y3, z3);
//            }
//        }
//        gl.glEnd();
//
//        gl.glPopMatrix();
    }

    private List<Point> getSpine() {
        List<Point> points = new ArrayList<Point>();

        for (double t = 0; t < size(); t += 0.1) {
            double[] coord = point(t);
            points.add(new Point(coord[0], myTerrain.altitude(coord[0], coord[1]), coord[1]));
        }

        return points;
    }

    public Polygon getCrossSection() {
        Polygon rectangle = new Polygon();
        rectangle.addPoint(0.5 * myWidth, 0.25 * myWidth, 0);
        rectangle.addPoint(-0.5 * myWidth, 0.25 * myWidth, 0);
        rectangle.addPoint(-0.5 * myWidth, -0.25 * myWidth, 0);
        rectangle.addPoint(0.5 * myWidth, -0.25 * myWidth, 0);
        return rectangle;
    }

    public List<Polygon> getMesh() {
        logger.info("Running getMesh()");
        // compute the mesh if necessary
        if (myMesh == null) {
            computeMesh();
        }
        logger.info("Mesh size: " + myMesh.size());
        return myMesh;
    }

    /**
     * Transform the points in the cross-section using the Frenet frame
     * and add them to the vertex list.
     *
     * @param crossSection The cross section
     * @param vertices     The vertex list
     * @param pPrev        The previous point on the spine
     * @param pCurr        The current point on the spine
     * @param pNext        The next point on the spine
     */
    private void addPoints(List<Point> crossSection, List<Point> vertices,
                           Point pPrev, Point pCurr, Point pNext) {
        logger.info("Running addPoints");
        // compute the Frenet frame as an affine matrix
        double[][] m = new double[4][4];

        // phi = pCurr
        m[0][3] = pCurr.x;
        m[1][3] = pCurr.y;
        m[2][3] = pCurr.z;
        m[3][3] = 1;

        // k = pNext - pPrev (approximates the tangent)
        m[0][2] = pNext.x - pPrev.x;
        m[1][2] = pNext.y - pPrev.y;
        m[2][2] = pNext.z - pPrev.z;
        m[3][2] = 0;

        // normalise k
        double d = Math.sqrt(m[0][2] * m[0][2] + m[1][2] * m[1][2] + m[2][2] * m[2][2]);
        m[0][2] /= d;
        m[1][2] /= d;
        m[2][2] /= d;

        // i = simple perpendicular to k
        m[0][0] = -m[2][2];
        m[1][0] = 0;
        m[2][0] = m[0][2];
        m[3][0] = 0;

        // j = k x i
        m[0][1] = m[1][2] * m[2][0] - m[2][2] * m[1][0];
        m[1][1] = m[2][2] * m[0][0] - m[0][2] * m[2][0];
        m[2][1] = m[0][2] * m[1][0] - m[1][2] * m[0][0];
        m[3][1] = 0;

        // transform the points

        for (Point cp : crossSection) {
            Point q = cp.transform(m);
            vertices.add(q);
        }
    }

    /**
     * The extrusion code.
     * <p/>
     * This method extrudes the cross section along the spine
     */
    private void computeMesh() {

        Polygon cs = getCrossSection();
        if (cs == null) {
            logger.info("ERROR: no cross-section");
            return;
        }

        List<Point> crossSection = cs.getPoints();
        List<Point> spine = getSpine();
        if (spine == null) {
            logger.info("ERROR: no spine");
            return;
        }

        //
        // Step 1: create a vertex list by moving the cross section along the spine
        //

        List<Point> vertices = new ArrayList<Point>();

        Point pPrev;
        Point pCurr = spine.get(0);
        Point pNext = spine.get(1);

        // first point is a special case
        addPoints(crossSection, vertices, pCurr, pCurr, pNext);

        // mid points
        for (int i = 1; i < spine.size() - 1; i++) {
            pPrev = pCurr;
            pCurr = pNext;
            pNext = spine.get(i + 1);
            addPoints(crossSection, vertices, pPrev, pCurr, pNext);
        }

        // last point is a special case
        pPrev = pCurr;
        pCurr = pNext;
        addPoints(crossSection, vertices, pPrev, pCurr, pCurr);

        //
        // Step 2: connect points in successive cross-sections to form quads
        //

        myMesh = new ArrayList<Polygon>();

        int n = crossSection.size();

        // for each point along the spine
        for (int i = 0; i < spine.size() - 1; i++) {

            // for each point in the cross section
            for (int j = 0; j < n; j++) {
                // create a quad joining this point and the next one
                // to the equivalent points in the next cross-section

                // note: make sure they are in anti-clockwise order
                // so they are facing outwards

                Polygon quad = new Polygon();
                quad.addPoint(vertices.get(i * n + j));
                quad.addPoint(vertices.get(i * n + (j + 1) % n));
                quad.addPoint(vertices.get((i + 1) * n + (j + 1) % n));
                quad.addPoint(vertices.get((i + 1) * n + j));
                myMesh.add(quad);
            }

        }

    }

    public void loadTexture(GL2 gl) {
        roadTexture = new Texture(GLProfile.getDefault(), gl, "yellowBrickRoad.jpg", "jpg");
    }
}
