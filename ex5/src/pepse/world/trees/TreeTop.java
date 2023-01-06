package pepse.world.trees;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Vector2;
import pepse.world.Block;
import pepse.world.trees.leaves.Leaf;

import java.util.Random;

public class TreeTop {

    private static final int LEAVES_LAYET_ADDITION = 20;
    private final int layer;
    private GameObjectCollection gameObjects;
    private final Vector2 center;

    public TreeTop(GameObjectCollection gameObjects, Vector2 center, int layer){
        this.gameObjects = gameObjects;
        this.center = center;
        this.layer = layer;
    }

    public void create(){

        Random rand = new Random();
        int radius = randX(3, 5);
        for (int r = -radius; r < radius; r++) {
            for (int c = -radius; c < radius; c++) {
                float num = rand.nextFloat() *(radius - Math.abs(r)) * (radius - Math.abs(c))
                        /(radius);
                if(num > 0.3){
                    Vector2 location = new Vector2(this.center.x() + r* Block.SIZE, this.center.y() + c * Block.SIZE);
                    Leaf leaf = new Leaf(gameObjects, this.layer+LEAVES_LAYET_ADDITION, this.layer, location);
                    leaf.create();
                }
            }
        }
    }

    private int randX(int strech, int bound) {
        Random rand = new Random();
        return (int) (rand.nextDouble() * strech + bound);
    }

}
