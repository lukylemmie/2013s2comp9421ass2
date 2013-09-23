package ass2.spec;

import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * COMMENT: Comment LevelIO 
 *
 * @author malcolmr
 */
public class LevelIO {

    /**
     * Load a terrain object from a JSON file
     * 
     * @param mapFile
     * @return
     * @throws FileNotFoundException 
     */
    public static Terrain load(File mapFile) throws FileNotFoundException {

        Reader in = new FileReader(mapFile);
        JSONTokener jtk = new JSONTokener(in);
        JSONObject jsonTerrain = new JSONObject(jtk);

        int width = jsonTerrain.getInt("width");
        int height = jsonTerrain.getInt("height");
        Terrain terrain = new Terrain(width, height);

        JSONArray jsonAltitude = jsonTerrain.getJSONArray("altitude");
        for (int i = 0; i < jsonAltitude.length(); i++) {
            int x = i % width;
            int y = i / width;

            double h = jsonAltitude.getDouble(i);
            terrain.setGridAltitude(x, y, h);
        }

        if (jsonTerrain.has("trees")) {
            JSONArray jsonTrees = jsonTerrain.getJSONArray("trees");
            for (int i = 0; i < jsonTrees.length(); i++) {
                JSONObject jsonTree = jsonTrees.getJSONObject(i);
                double x = jsonTree.getDouble("x");
                double y = jsonTree.getDouble("y");
                terrain.addTree(x, y);
            }
        }
        
        if (jsonTerrain.has("roads")) {
            JSONArray jsonRoads = jsonTerrain.getJSONArray("roads");
            for (int i = 0; i < jsonRoads.length(); i++) {
                JSONObject jsonRoad = jsonRoads.getJSONObject(i);
                double w = jsonRoad.getDouble("width");
                
                JSONArray jsonSpine = jsonRoad.getJSONArray("spine");
                double[] spine = new double[jsonSpine.length()];
                
                for (int j = 0; j < jsonSpine.length(); j++) {
                    spine[j] = jsonSpine.getDouble(j);
                }
                terrain.addRoad(width, spine);
            }
        }
        return terrain;
    }

    /**
     * Write Terrain to a JSON file
     * 
     * @param file
     * @throws IOException 
     */
    public static void save(Terrain terrain, File file) throws IOException {
        JSONObject json = new JSONObject();
                
        Dimension size = terrain.size();
        json.put("width", size.width);
        json.put("height", size.height);

        JSONArray altitude = new JSONArray();
        for (int i = 0; i < size.width; i++) {
            for (int j = 0; j < size.height; j++) {
                altitude.put(terrain.getGridAltitude(i, j));
            }
        }
        json.put("altitiude", altitude);
        
        JSONArray trees = new JSONArray();
        for (Tree t : terrain.trees()) {
            JSONObject j = new JSONObject();
            double[] position = t.getPosition();
            j.put("x", position[0]);
            j.put("y", position[1]);
            trees.put(j);
        }
        json.put("trees", trees);

        JSONArray roads = new JSONArray();
        for (Road r : terrain.roads()) {
            JSONObject j = new JSONObject();
            j.put("width", r.width());
            
            JSONArray spine = new JSONArray();
            int n = r.size();
            
            for (int i = 0; i <= n*3; i++) {
                double[] p = r.controlPoint(i);
                spine.put(p[0]);
                spine.put(p[1]);
            }
            j.put("spine", spine);
            roads.put(j);
        }
        json.put("roads", roads);

        FileWriter out = new FileWriter(file);
        json.write(out);
        out.close();

    }
    
    /**
     * For testing.
     * 
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        Terrain terrain = LevelIO.load(new File(args[0]));
        LevelIO.save(terrain, new File(args[1]));
    }

}
