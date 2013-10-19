package drivingExample;

import ass2.spec.MyCamera;
import ass2.spec.MyKeyboard;
import ass2.spec.Terrain;
import ass2.spec.Tree;
import com.jogamp.opengl.util.FPSAnimator;

import javax.media.opengl.*;
import javax.media.opengl.awt.GLJPanel;
import javax.media.opengl.glu.GLU;
import javax.swing.*;
import java.awt.*;

//import pong3d.Keyboard;

/**
 * COMMENT: Comment DrivingGame
 *
 * @author malcolmr
 */
public class DrivingGame extends JFrame implements GLEventListener {
    private static final float FOV = 90;
    private static final double[][] altitudeSet1 = new double[][]
            {
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0}
            };
    private static final double[][] altitudeSet2 = new double[][]
            {
                    {0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0},
                    {0, 0, 1, 1, 0, 0},
                    {0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0}
            };
    private static final double[][] altitudeSet3 = new double[][]
            {
                    {0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 1, 0, 0, 0, 0},
                    {0, 0, 0, 1, 2, 1, 0, 0, 0},
                    {0, 0, 1, 2, 3, 2, 1, 0, 0},
                    {0, 1, 2, 3, 4, 3, 2, 1, 0},
                    {0, 0, 1, 2, 3, 2, 1, 0, 0},
                    {0, 0, 0, 1, 2, 1, 0, 0, 0},
                    {0, 0, 0, 0, 1, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0}
            };
    private static final double[][] altitudeSet5 = new double[][]
            {
                    {2, 8, 6, 10},
                    {6, 10, 8, 2},
                    {8, 2, 10, 6},
                    {10, 6, 2, 8}
            };
    //    private Car myCar;
    private Terrain myTerrain;
    private Tree myTree;
    private Floor myFloor;
    private MyCamera myCamera;

    public DrivingGame() {
        super("Driving Game!");

        myTerrain = new Terrain(altitudeSet3);
        myFloor = new Floor(10, 10);
        myTree = new Tree(0, 0, 0);
//        myCar = new Car();
        myCamera = new MyCamera();
    }

    private void run() {
        GLProfile glprofile = GLProfile.getDefault();
        GLCapabilities glcapabilities = new GLCapabilities(glprofile);
        GLJPanel panel = new GLJPanel(glcapabilities);
        MyKeyboard myKeyboard = new MyKeyboard(myCamera);

        panel.addGLEventListener(this);
        panel.addKeyListener(myKeyboard);
        panel.setFocusable(true);
        panel.requestFocus();

        // Add an animator to call 'display' at 60fps        
        FPSAnimator animator = new FPSAnimator(60);
        animator.add(panel);
        animator.start();

        getContentPane().add(panel, BorderLayout.CENTER);
        setSize(800, 800);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }


    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();

        gl.glEnable(GL2.GL_DEPTH_TEST);

        // enable lighting, turn on light 0
        gl.glEnable(GL2.GL_LIGHTING);
        gl.glEnable(GL2.GL_LIGHT0);

        // normalise normals (!)
        // this is necessary to make lighting work properly
        gl.glEnable(GL2.GL_NORMALIZE);

        // enable texturing
        gl.glEnable(GL.GL_TEXTURE_2D);
        myTerrain.loadTexture(gl);
        myTree.loadTexture(gl);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();

        myCamera.setCamera(gl);

        myFloor.draw(gl);
//        myCar.draw(gl);
        myTree.draw(gl);
        myTerrain.draw(gl);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width,
                        int height) {
        GL2 gl = drawable.getGL().getGL2();

        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();

//        gl.glOrtho(-8, 8, -8, 8, -8, 8);

        double aspect = 1.0 * width / height;

        GLU glu = new GLU();
        glu.gluPerspective(FOV, aspect, 1, MyCamera.CAMERA_DIST + 2 + 20);
    }

    public static void main(String[] args) {
        DrivingGame game = new DrivingGame();

        game.run();
    }
}
