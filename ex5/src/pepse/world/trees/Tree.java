package pepse.world.trees;

import danogl.collisions.GameObjectCollection;
import danogl.util.Vector2;
import pepse.util.GroundHeight;
import pepse.world.Block;
import pepse.world.Terrain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

public class Tree {
    // Class variables
    private final GameObjectCollection gameObjects;
    private final int layer;
    private final Vector2 windowDimensions;
    private final GroundHeight ground;

    /**
     * Creates a GameObject of type Tree
     *
     * @param gameObjects      the gameObjects in the game
     * @param layer            the layer we want to place the GameObject at
     * @param windowDimensions the vector with the dimensions of the screen
     * @param ground           the GroundHeight that calculates the height of the ground at certain x
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
     *
     * @param minX the beginning of the range
     * @param maxX the end of the range
     */
//    public void createInRange(int minX, int maxX) {
//        for (int x = minX - 100 + randX(1000, 10); x < maxX - 6*Block.SIZE; x += randX(1000, 10)) {
//            int newX = (int) (Block.SIZE*(Math.floor((x/Block.SIZE))));
//            Trunk trunk = new Trunk(gameObjects, windowDimensions, ground.groundHeightAt(newX),newX,
//                    this.layer);
//            trunk.create();
//            TreeTop top = new TreeTop(gameObjects, new Vector2(newX,
//                    (this.windowDimensions.y() - (trunk.getHeight()+ ground.groundHeightAt(newX)))), this.layer);
//            top.create();
//            }
//    }
    public void createInRange(int minX, int maxX) {
        for (int x = (int) (Block.SIZE * (Math.floor((minX / Block.SIZE))));
             x < (int) (Block.SIZE * (Math.floor((maxX / Block.SIZE)))); x += Block.SIZE) {
//            int newX = (int) (Block.SIZE*(Math.floor((x/Block.SIZE))));
            Random rand = new Random(Objects.hash(x, Terrain.seed));
            if (rand.nextInt(100) > 90) {
                Trunk trunk = new Trunk(gameObjects, windowDimensions, ground.groundHeightAt(x), x,
                        this.layer);
                trunk.create();
                TreeTop top = new TreeTop(gameObjects, new Vector2(x,
                        (this.windowDimensions.y() - (trunk.getHeight() + ground.groundHeightAt(x)))), this.layer);
                top.create();
            }
        }
    }

    /**
     * @param strech how much of a range we want
     * @param bound  lower bound of the random number
     * @return a random number with the right constraints
     */
    private int randX(int strech, int bound) {
        return (int) (Terrain.rand.nextDouble() * strech + bound);
    }

}
