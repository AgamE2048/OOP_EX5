package pepse.world.trees;

import danogl.collisions.GameObjectCollection;
import danogl.util.Vector2;
import pepse.world.Block;

import static pepse.world.Terrain.rand;

public class TreeTop {
    // Class variables
    private static final int LEAVES_LAYER_ADDITION = 10;
    private static final double LEAVES_PROBABILITY = 0.3;
    private final int layer;
    private GameObjectCollection gameObjects;
    private final Vector2 center;

    /**
     * Basic constructor for TreeTop
     *
     * @param gameObjects the gameObjects in the game
     * @param center      the vector with the center of the TreeTop location
     * @param layer       the layer where we add the TreeTop
     */
    public TreeTop(GameObjectCollection gameObjects, Vector2 center, int layer) {
        this.gameObjects = gameObjects;
        this.center = center;
        this.layer = layer;
    }

    /**
     * Creates the TreeTop
     */
    public void create() {
        int radius = rand.nextInt(2) + 5;
        for (int r = -radius; r < radius; r++) {
            for (int c = -radius; c < radius; c++) {
                float num = rand.nextFloat() * (radius - Math.abs(r)) * (radius - Math.abs(c))
                        / (radius);
                if (num > LEAVES_PROBABILITY) {
                    Vector2 location = new Vector2(this.center.x() + r * Block.SIZE, this.center.y() + c * Block.SIZE);
                    Leaf leaf = new Leaf(gameObjects, this.layer + LEAVES_LAYER_ADDITION, location);
                    leaf.create();
                }
            }
        }
    }
}
