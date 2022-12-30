package pepse.world.daynight;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;

import java.awt.*;

public class Night {
    public static GameObject create(GameObjectCollection gameObjects, int nightLayer,
                                    Vector2 windowDimentions,
                                    float cycleLength){
        GameObject night = new GameObject(Vector2.ZERO, windowDimentions,
                new RectangleRenderable(Color.BLACK));
        night.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects.addGameObject(night, nightLayer);
        night.setTag("night");

        return night;
    }
}
