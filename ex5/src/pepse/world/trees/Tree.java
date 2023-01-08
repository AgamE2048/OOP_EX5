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
    private final static int indeces[] = {210, 1260};

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
    public void createInRange(int minX, int maxX) {
        minX = minX - Math.floorMod(minX, Block.SIZE);
        maxX = maxX - Math.floorMod(maxX, Block.SIZE);
        for (int y : indeces) {
//            int newX = (int) (Block.SIZE*(Math.floor((x/Block.SIZE))));
            //Random rand = new Random(Objects.hash(x, Terrain.seed));
            //if (shouldPlantTreeAt(x, rand)) {
            int x = y + minX;
            Trunk trunk = new Trunk(gameObjects, windowDimensions, ground.groundHeightAt(x), x, this.layer);
            trunk.create();
            TreeTop top = new TreeTop(gameObjects, new Vector2(x, (this.windowDimensions.y() - (trunk.getHeight() + ground.groundHeightAt(x)))), this.layer);
            top.create();
            //}
        }
    }
//    public void createInRange(int minX, int maxX) {
//        for (int x = (int) (Block.SIZE * (Math.floor((minX / Block.SIZE))));
//             x < (int) (Block.SIZE * (Math.floor((maxX / Block.SIZE)))); x += 40*Block.SIZE) {
////            int newX = (int) (Block.SIZE*(Math.floor((x/Block.SIZE))));
//            //Random rand = new Random(Objects.hash(x, Terrain.seed));
//            //if (shouldPlantTreeAt(x, rand)) {
//                Trunk trunk = new Trunk(gameObjects, windowDimensions, ground.groundHeightAt(x), x,
//                        this.layer);
//                trunk.create();
//                TreeTop top = new TreeTop(gameObjects, new Vector2(x,
//                        (this.windowDimensions.y() - (trunk.getHeight() + ground.groundHeightAt(x)))), this.layer);
//                top.create();
//            //}
//        }
//    }

    private boolean shouldPlantTreeAt(int index, Random rand) {
        int divideFactor = 4 * (int) Block.SIZE;
        return index % divideFactor == 0 && rand.nextInt(100) / 100.0 < 0.2;
    }
}
