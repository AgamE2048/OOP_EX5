package pepse.world.daynight;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;

import java.awt.*;

public class SunHalo {
    private static final Vector2 SUN_DIMS = new Vector2(100, 100);

    public static GameObject create(GameObjectCollection gameObjects, int layer, GameObject sun,
                                    Color color){
        GameObject sunHalo = new GameObject(Vector2.ZERO, SUN_DIMS, new OvalRenderable(color));
        sunHalo.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sunHalo.setTag("sunHalo");
        sunHalo.setCenter(sun.getCenter());
        gameObjects.addGameObject(sunHalo, layer);
        return sunHalo;
    }

}
