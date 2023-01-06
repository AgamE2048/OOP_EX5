package pepse.world;

import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.util.GroundHeight;
import pepse.util.PerlinNoise;

import java.awt.*;
import java.util.Random;

/**
 * This class has 2 main responsibilities:
 * 1. Create the ground blocks
 * 2. Enables different objects to know what is the height of the ground at given X coordinate.
 */
public class Terrain implements GroundHeight {
    public static Random rand;
    private static final Color BASE_GROUND_COLOR = new Color(212, 123, 74);
    private static final char ROUND_DOWN = '-';
    private static final int NUM_TOP_BLOCKS_COLLISION = 2;
    private GameObjectCollection gameObjects;
    private int groundLayer;
    private int leavesLayer;
    private Vector2 windowDimensions;
    private float groundHeightAtX0 = 300;
    private int PERLIN_MULT = 50;
    private final PerlinNoise perlinNoise;

    /**
     Creates a GameObject of type Terrain
     * @param gameObjects the gameObjects in the game
     * @param windowDimensions the vector with the dimensions of the screen
     * @param layer the layer we want to place the GameObject at
     * @return a GameObject of type Terrain
     */
    public Terrain(GameObjectCollection gameObjects, int layer, Vector2 windowDimensions, int seed) {
        gameObjects.layers().shouldLayersCollide(leavesLayer, this.groundLayer, true);
        this.rand = new Random(seed);
        this.gameObjects = gameObjects;
        this.groundLayer = layer;
        this.windowDimensions = windowDimensions;
        this.leavesLayer = Layer.STATIC_OBJECTS + 20;
        this.perlinNoise = new PerlinNoise(seed);
        //TODO: finish...
    }

    public Terrain(GameObjectCollection gameObjects, int layer, int leavesLayer, Vector2 windowDimensions,
                   int seed) {
        this.gameObjects = gameObjects;
        this.groundLayer = layer;
        this.windowDimensions = windowDimensions;
        this.leavesLayer = leavesLayer;
        //TODO: finish...
        this.perlinNoise = new PerlinNoise(seed);
    }

    /**
     *
     * @param x the x location we want to check
     * @return the y (height) of the ground at x
     */
    @Override
    public float groundHeightAt(float x) {
//        return (float)(250+25*Math.sin((int)(x/30)*25) + 25*Math.cos((int)(x/30)*45));
//        return groundHeightAtX0;
        return groundHeightAtX0 + Block.SIZE * ((int) perlinNoise.noise(x/Block.SIZE)*PERLIN_MULT);
        //TODO: change according to functionality.
    }

    /**
     * Creates blocks that make up the ground in the given range (up to the height of the ground at that
     * location of x)
     * @param minX the beginning of the range
     * @param maxX the end of the range
     */
    public void createInRange(int minX, int maxX) {
        for (int x = (int) (Block.SIZE*(Math.floor(Math.abs(minX/Block.SIZE)))); x <
                Block.SIZE*(Math.ceil(Math.abs(maxX/Block.SIZE))); x+=Block.SIZE) {
            for (int i = 0; i < (int)Math.floor(groundHeightAt(x)/Block.SIZE); i++) {
                Renderable r = new RectangleRenderable((ColorSupplier.approximateColor(BASE_GROUND_COLOR)));
                double y_height = this.windowDimensions.y()- i* Block.SIZE;
                Vector2 vec = new Vector2(x, (float) (y_height));
                gameObjects.addGameObject(new Block(vec, r), this.groundLayer);
            }
        }
    }
}
