package pepse.world;

import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;

import java.awt.*;

/**
 * This class has 2 main responsibilities:
 * 1. Create the ground blocks
 * 2. Enables different objects to know what is the height of the ground at given X coordinate.
 */
public class Terrain {
    private static final Color BASE_GROUND_COLOR = new Color(212, 123, 74);
    private static final int TERRAIN_DEPTH = 20;
    private static final char ROUND_DOWN = '-';
    private static final char ROUND_UO = '+';
    private GameObjectCollection gameObjects;
    private int groundLayer;
    private Vector2 windowDimensions;
    private float groundHeightAtX0 = 300;


    public Terrain(GameObjectCollection gameObjects, int groundLayer, Vector2 windowDimensions, int seed) {
        this.gameObjects = gameObjects;
        this.groundLayer = groundLayer;
        this.windowDimensions = windowDimensions;
        //TODO: finish...
    }

    public float groundHeightAt(float x) {
        return groundHeightAtX0;
        //TODO: change according to functionality.
    }

    public void createInRange(int minX, int maxX) {
        Renderable r = new RectangleRenderable((ColorSupplier.approximateColor(BASE_GROUND_COLOR)));
        for (int x = roundX(minX, '-'); x < roundX(maxX, '+'); x+=Block.SIZE) {
            // roundX((int)groundHeightAt(x), '+')
            for (int i = 0; i < (int)Math.floor(groundHeightAt(x)/Block.SIZE); i++) {
                double y_height =this.windowDimensions.y()- i* Block.SIZE;
                System.out.println(y_height);
                Vector2 vec = new Vector2(x, (float) (y_height));
                gameObjects.addGameObject(new Block(vec, r), Layer.STATIC_OBJECTS);
            }
            System.out.println();
        }

        //TODO: finish...
    }

    private int roundX(int x, char c) {
        int num = 0;
        if(c == ROUND_DOWN){
            while(num > x)
                num -= Block.SIZE;
        }
        else{
            while (num < x)
                num += Block.SIZE;
        }
        return num;
    }
}
