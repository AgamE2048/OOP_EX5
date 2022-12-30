package pepse.world.daynight;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;

import java.awt.*;

public class Sun {
    private static final float SUN_DIM = 100.0f;
    public static GameObject create(GameObjectCollection gameObjects, int layer, Vector2 windowDimensions,
                                    float cycleLength){
        GameObject sun = new GameObject(Vector2.ZERO,
                new Vector2(SUN_DIM,
                SUN_DIM),
                new OvalRenderable(Color.YELLOW));

        Vector2 sunLoc = new Vector2(windowDimensions.x()*0.5f, windowDimensions.y()*0.15f);
        sun.setCenter(sunLoc);
        sun.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects.addGameObject(sun, layer);
        sun.setTag("sun");

//        new Transition<Float>(sun, sun.renderer()::, sunLoc, sunLoc,
//                Transition.LINEAR_INTERPOLATOR_FLOAT, cycleLength/2,
//                Transition.TransitionType.TRANSITION_LOOP, null);

        return sun;
    }
}
