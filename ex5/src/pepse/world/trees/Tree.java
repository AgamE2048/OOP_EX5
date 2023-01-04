package pepse.world.trees;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.util.GroundHeight;
import pepse.world.Block;
import pepse.world.Terrain;

import java.awt.*;
import java.util.Random;

public class Tree {
    // Class variables
    private static final char ROUND_DOWN = '-';
    private final GameObjectCollection gameObjects;
    private final int layer;
    private final Vector2 windowDimensions;
    private final GroundHeight ground;

    /**
     * Creates a GameObject of type Tree
     * @param gameObjects the gameObjects in the game
     * @param layer the layer we want to place the GameObject at
     * @param windowDimensions the vector with the dimensions of the screen
     * @param ground the GroundHeight that calculates the height of the ground at certain x
     * @return a GameObject of type Tree
     */
    public Tree(GameObjectCollection gameObjects, int layer, Vector2 windowDimensions, GroundHeight ground) {
        this.gameObjects = gameObjects;
        this.layer = layer;
        this.windowDimensions = windowDimensions;
        this.ground = ground;
    }

    /**
     * Creates trees on the ground in the given range
     * @param minX the beginning of the range
     * @param maxX the end of the range
     */
    public void createInRange(int minX, int maxX) {
        System.out.println("Window dimension in y " + windowDimensions.y());
        for (int x = minX - 100 + randX(250, 150); x < maxX; x += randX(250, 150)) {
            Trunk trunk = new Trunk(gameObjects, windowDimensions, ground.groundHeightAt(x),x,
                    this.layer);
            trunk.create();
            TreeTop top = new TreeTop(gameObjects, new Vector2(x,
                    (this.windowDimensions.y() - (trunk.getHeight()+ ground.groundHeightAt(x)))));
            top.create();
            }
    }

    /**
     *
     * @param strech how much of a range we want
     * @param bound lower bound of the random number
     * @return a random number with the right constraints
     */
    private int randX(int strech, int bound) {
        Random rand = new Random();
        return (int) (rand.nextDouble() * strech + bound);
    }

}
