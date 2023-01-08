package pepse.world.trees;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.world.Block;

import java.awt.*;

import static pepse.world.Terrain.rand;

public class Trunk {
    // Class variables
    private static final Color BASE_TRUNK_COLOR = new Color(100, 50, 20);
    private final float groundHeight;
    private final GameObjectCollection gameObjects;
    private final Vector2 windowDimensions;
    private int locOfX;
    private int layer;
    private int height;
    protected static final String TREE_TAG = "tree";

    /**
     * Creates a GameObject of type Trunk
     *
     * @param gameObjects      the gameObjects in the game
     * @param windowDimensions the vector with the dimensions of the screen
     * @param groundHeight     the GroundHeight that calculates the height of the ground at certain x
     * @param locOfX           the location of where we want to place the trunk
     * @param layer            the layer we want to place the GameObject at
     */
    public Trunk(GameObjectCollection gameObjects, Vector2 windowDimensions, float groundHeight, int locOfX
            , int layer) {
        this.groundHeight = groundHeight;
        this.locOfX = locOfX;
        this.gameObjects = gameObjects;
        this.windowDimensions = windowDimensions;
        this.layer = layer;
    }

    /**
     * @return a GameObject of type Trunk
     */
    public GameObject create() {
        this.height = rand.nextInt(150) + 300;
        float x = (float) (Block.SIZE * (Math.floor(((float) this.locOfX / Block.SIZE))));
        float y = (float) (windowDimensions.y() -
                (Block.SIZE * (Math.floor(Math.abs(groundHeight / Block.SIZE)))));
        Renderable r = new RectangleRenderable((ColorSupplier.approximateColor(BASE_TRUNK_COLOR)));
        Block trunk = new Block(new Vector2(x, y - height + Block.SIZE), r, height);
        gameObjects.addGameObject(trunk, this.layer);
        trunk.setTag(TREE_TAG);
        return trunk;
    }

    /**
     * @return the height of the trunk
     */
    public float getHeight() {
        return this.height;
    }
}
