package pepse.world.trees.leaves;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.world.Block;

import java.awt.*;

public class Leaf {
    private static final Color BASE_COLOR_LEAVES = new Color(50, 200, 30);

    private GameObjectCollection gameObjects;

    public Leaf(GameObjectCollection gameObjects){
        this.gameObjects = gameObjects;
    }

    public GameObject create(Vector2 topLeftCorner){
        GameObject leaf = new GameObject(topLeftCorner, Vector2.ONES.mult(Block.SIZE), new
                RectangleRenderable(ColorSupplier.approximateColor(BASE_COLOR_LEAVES)));
        this.gameObjects.addGameObject(leaf);
        return leaf;
    }
}
