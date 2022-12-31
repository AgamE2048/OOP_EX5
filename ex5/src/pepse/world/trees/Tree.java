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
    private static final char ROUND_DOWN = '-';

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
        System.out.println("Window dimension in y " + windowDimensions.y());
        for (int x = minX + randX(60, 20); x < maxX; x += randX(60, 20)) {
            }
    }

    private int randX(int strech, int bound) {
        Random rand = new Random();
        return (int) (rand.nextDouble() * strech + bound);
    }

}
