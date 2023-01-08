package pepse.world;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;

import java.awt.*;

public class Sky {
    // Class variables
    private static final Color BASIC_SKY_COLOR = Color.decode("#80C6E5");

    /**
     Creates a GameObject of type Sky
     * @param gameObjects the gameObjects in the game
     * @param windowDimensions the vector with the dimensions of the screen
     * @param layer the layer we want to place the GameObject at
     * @return a GameObject of type Sky
     */
    public static GameObject create(GameObjectCollection gameObjects, Vector2 windowDimensions,
                                    int layer){
        GameObject sky = new GameObject(Vector2.ZERO, windowDimensions,
                new RectangleRenderable(BASIC_SKY_COLOR));
        sky.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects.addGameObject(sky, layer);
        sky.setTag("sky");
        return sky;

    }
}
