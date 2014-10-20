package ass2.spec;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;

import ass2.spec.LevelIO;
import com.jogamp.opengl.util.FPSAnimator;
import drivingExample.Floor;

import javax.media.opengl.*;
import javax.media.opengl.awt.GLJPanel;
import javax.media.opengl.glu.GLU;
import javax.swing.*;


/**
 * COMMENT: Comment Game
 *
 * @author malcolmr
 */
public class Game extends JFrame implements GLEventListener {
    private static final float FOV = 90;
    private Terrain myTerrain;
    private MyCamera myCamera;
//    private Helicopter helicopter;

    public Game(Terrain terrain) {
        myTerrain = terrain;
        myCamera = new MyCamera(terrain);
//        helicopter = new Helicopter();
        terrain.fixSunlight();
    }

    /**
     * Run the game.
     */
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

    /**
     * Load a level file and display it.
     *
     * @param args - The first argument is a level file in JSON format
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
        Terrain terrain = LevelIO.load(new File(args[0]));
        Game game = new Game(terrain);
        game.run();
    }


    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();

        gl.glEnable(GL2.GL_DEPTH_TEST);
        gl.glEnable(GL2.GL_POLYGON_OFFSET_FILL);

        // enable lighting, turn on light 0
        gl.glEnable(GL2.GL_LIGHTING);
        gl.glEnable(GL2.GL_LIGHT0);

        // enable transparency
        gl.glEnable(GL2.GL_BLEND);
//        gl.glDepthMask(false);
//        gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE);
        gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);

        // normalise normals (!)
        // this is necessary to make lighting work properly
        gl.glEnable(GL2.GL_NORMALIZE);

        // enable texturing
        gl.glEnable(GL.GL_TEXTURE_2D);
        myTerrain.loadTexture(gl);
        myTerrain.injectRoadAltitude();
//        helicopter.loadTexture(gl);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();

        myCamera.setCamera(gl);
//        helicopter.draw(gl);
        myTerrain.draw(gl);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width,
                        int height) {
        GL2 gl = drawable.getGL().getGL2();

        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();


        double aspect = 1.0 * width / height;

        GLU glu = new GLU();
        glu.gluPerspective(FOV, aspect, 1, MyCamera.CAMERA_DIST + 2 + 20);
    }
}
