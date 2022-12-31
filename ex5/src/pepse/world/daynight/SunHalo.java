package pepse.world.daynight;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;

import java.awt.*;

public class SunHalo {
    private static final float SUN_DIMS = 150;

    public static GameObject create(GameObjectCollection gameObjects, int layer, GameObject sun,
                                    Color color){
        GameObject sunHalo = new GameObject(Vector2.ZERO, Vector2.ONES.mult(SUN_DIMS), new OvalRenderable(color));
        sunHalo.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sunHalo.setTag("sunHalo");
//        sunHalo.setCenter(sun.getCenter());
        sunHalo.addComponent((deltaTime) -> sunHalo.setCenter(sun.getCenter()));
        gameObjects.addGameObject(sunHalo, layer);
        return sunHalo;
    }
}
