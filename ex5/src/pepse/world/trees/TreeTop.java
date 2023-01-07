package pepse.world.trees;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Vector2;
import pepse.world.Block;
import pepse.world.trees.leaves.Leaf;

import static pepse.world.Terrain.rand;

public class TreeTop {

    private static final int LEAVES_LAYER_ADDITION = 10;
    private final int layer;
    private GameObjectCollection gameObjects;
    private final Vector2 center;

    public TreeTop(GameObjectCollection gameObjects, Vector2 center, int layer){
        this.gameObjects = gameObjects;
        this.center = center;
        this.layer = layer;
    }

    public void create(){
        int radius = randX(2, 5);
        for (int r = -radius; r < radius; r++) {
            for (int c = -radius; c < radius; c++) {
                float num = rand.nextFloat() *(radius - Math.abs(r)) * (radius - Math.abs(c))
                        /(radius);
                if(num > 0.3){
                    Vector2 location = new Vector2(this.center.x() + r* Block.SIZE, this.center.y() + c * Block.SIZE);
                    Leaf leaf = new Leaf(gameObjects, this.layer+LEAVES_LAYER_ADDITION, this.layer, location);
                    //System.out.println(this.layer+LEAVES_LAYER_ADDITION);
                    leaf.create();
                }
            }
        }
    }

    private int randX(int strech, int bound) {
        return (int) (rand.nextDouble() * strech + bound);
    }

}
