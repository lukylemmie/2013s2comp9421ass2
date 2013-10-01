package ass2.spec;

import java.util.logging.Logger;

/**
 * COMMENT: Comment Tree 
 *
 * @author malcolmr
 */
public class Tree {
    private static Logger logger = Logger.getLogger(Tree.class.getName());
    private double[] myPos;
    
    public Tree(double x, double y, double z) {
        myPos = new double[3];
        myPos[0] = x;
        myPos[1] = y;
        myPos[2] = z;
    }
    
    public double[] getPosition() {
        return myPos;
    }
    

}
