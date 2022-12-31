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
    private static final float INITIAL_DEGREES = 5.0f;
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

        new Transition<Float>(sun,
//                (angle) -> sun.setCenter(new Vector2((float)(windowDimensions.x()/2 + windowDimensions.x()*Math.cos(3*angle)),
//                        (float)(windowDimensions.y()*1.15 - windowDimensions.y()*Math.sin(3*angle)))) ,
                (angle) -> sun.setCenter(new Vector2((float)(windowDimensions.x()/2 + windowDimensions.x()/2*Math.sin(5*angle)),
                        (float)(windowDimensions.y()*1.3 -windowDimensions.y()*1.15*Math.cos(5*angle)))),
                INITIAL_DEGREES, INITIAL_DEGREES+360.0F,
                Transition.LINEAR_INTERPOLATOR_FLOAT, (int)Math.pow(cycleLength, 2.65),
                Transition.TransitionType.TRANSITION_LOOP, null);

        return sun;
    }

    private static Vector2 calcSunPosition(Vector2 windowDimensions, float angleInSky){
        float x = (float)(windowDimensions.x()*Math.sin(angleInSky));
        float y = (float)(windowDimensions.x()*Math.cos(angleInSky));
        return new Vector2(x, y);
    }
}
