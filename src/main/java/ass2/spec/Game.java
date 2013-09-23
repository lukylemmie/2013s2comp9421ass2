package ass2.spec;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * COMMENT: Comment Game 
 *
 * @author malcolmr
 */
public class Game {

    private Terrain myTerrain;

    public Game(Terrain terrain) {
        myTerrain = terrain;
    }
    
    /** 
     * Run the game.
     *
     */
    public void run() {
        
    }
    
    public static void main(String[] args) throws FileNotFoundException {
        Terrain terrain = LevelIO.load(new File(args[0]));
        Game game = new Game(terrain);
        game.run();
    }
}
