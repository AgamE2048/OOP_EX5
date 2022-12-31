package pepse.world.trees;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.world.Block;
import pepse.world.Terrain;

import java.awt.*;
import java.util.Random;

public class Tree {
    private static final Color BASE_TRUNK_COLOR = new Color(61, 27, 4);
    private final GameObjectCollection gameObjects;
    private final int layer;
    private final Vector2 windowDimensions;
    private final Terrain ground;

    public Tree(GameObjectCollection gameObjects, int layer, Vector2 windowDimensions, Terrain ground) {
        this.gameObjects = gameObjects;
        this.layer = layer;
        this.windowDimensions = windowDimensions;
        this.ground = ground;
    }

    public void createInRange(int minX, int maxX) {
        for (int x = minX + randX(60, 20); x < maxX; x += randX(60, 20)) {
            Renderable r = new RectangleRenderable((ColorSupplier.approximateColor(BASE_TRUNK_COLOR)));
            float height = randX(40, 60);
            float width = randX(30, 20);
            Vector2 dims = new Vector2(width, height);
            Vector2 topLeftCorner = new Vector2((float) x, this.windowDimensions.y()-(height + ground.groundHeightAt((float)x)));
            gameObjects.addGameObject(new GameObject(topLeftCorner, dims, r), layer);
        }
    }

    private int randX(int strech, int bound) {
        Random rand = new Random();
        return (int) (rand.nextDouble() * strech + bound);
    }
}
