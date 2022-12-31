package pepse.world.trees;

import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.world.Block;

public class Tree {
    private final GameObjectCollection gameObjects;
    private final int layer;
    private final Vector2 windowDimensions;

    public Tree(GameObjectCollection gameObjects, int layer, Vector2 windowDimensions, int seed) {
        this.gameObjects = gameObjects;
        this.layer = layer;
        this.windowDimensions = windowDimensions;
    }

    public void createInRange(int minX, int maxX){
        for (int x = roundX(minX, '-'); x < roundX(maxX, '+'); x+= Block.SIZE) {
            for (int i = 0; i < (int)Math.floor(groundHeightAt(x)/Block.SIZE); i++) {
                Renderable r = new RectangleRenderable((ColorSupplier.approximateColor(BASE_GROUND_COLOR)));
                double y_height =this.windowDimensions.y()- i* Block.SIZE;
                Vector2 vec = new Vector2(x, (float) (y_height));
                gameObjects.addGameObject(new Block(vec, r), Layer.STATIC_OBJECTS);
            }
        }
    }
}
