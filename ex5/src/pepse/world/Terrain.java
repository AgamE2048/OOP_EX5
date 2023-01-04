package pepse.world;

import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.util.GroundHeight;

import java.awt.*;

/**
 * This class has 2 main responsibilities:
 * 1. Create the ground blocks
 * 2. Enables different objects to know what is the height of the ground at given X coordinate.
 */
public class Terrain implements GroundHeight {
    private static final Color BASE_GROUND_COLOR = new Color(212, 123, 74);
    private static final char ROUND_DOWN = '-';
    private GameObjectCollection gameObjects;
    private int groundLayer;
    private Vector2 windowDimensions;
    private float groundHeightAtX0 = 300;

    /**
     Creates a GameObject of type Terrain
     * @param gameObjects the gameObjects in the game
     * @param windowDimensions the vector with the dimensions of the screen
     * @param layer the layer we want to place the GameObject at
     * @return a GameObject of type Terrain
     */
    public Terrain(GameObjectCollection gameObjects, int layer, Vector2 windowDimensions, int seed) {
        this.gameObjects = gameObjects;
        this.groundLayer = layer;
        this.windowDimensions = windowDimensions;
        //TODO: finish...
    }

    /**
     *
     * @param x the x location we want to check
     * @return the y (height) of the ground at x
     */
    @Override
    public float groundHeightAt(float x) {
        return (float)(250+25*Math.sin((int)(x/30)*25) + 25*Math.cos((int)(x/30)*45));
//        return groundHeightAtX0;
        //TODO: change according to functionality.
    }

    /**
     * Creates blocks that make up the ground in the given range (up to the height of the ground at that
     * location of x)
     * @param minX the beginning of the range
     * @param maxX the end of the range
     */
    public void createInRange(int minX, int maxX) {
        for (int x = roundX(minX, '-'); x < roundX(maxX, '+'); x+=Block.SIZE) {
            for (int i = 0; i < (int)Math.floor(groundHeightAt(x)/Block.SIZE); i++) {
                Renderable r = new RectangleRenderable((ColorSupplier.approximateColor(BASE_GROUND_COLOR)));
                double y_height = this.windowDimensions.y()- i* Block.SIZE;
                Vector2 vec = new Vector2(x, (float) (y_height));
                gameObjects.addGameObject(new Block(vec, r), this.groundLayer);
            }
        }
    }

    /**
     *
     * @param x the number we want to round
     * @param c chooses where we round Up or down
     * @return the closest multiple of Block.SIZE to x rounded based on c
     */
    private int roundX(int x, char c) {
        int num = 0;
        if(c == ROUND_DOWN){
            while(num < x)
                num += Block.SIZE;
            while(num > x)
                num -= Block.SIZE;
        }
        else{
            while(num > x)
                num -= Block.SIZE;
            while(num < x)
                num += Block.SIZE;
        }
        return num;
    }
}
