package pepse.world.daynight;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.ImageReader;
import danogl.gui.rendering.ImageRenderable;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;

import java.awt.*;

public class Moon extends Sun{
    protected static final float INITIAL_DEGREES = 115.0f;


    public static GameObject create(GameObjectCollection gameObjects, int layer, Vector2 windowDimensions,
                                    float cycleLength){
        GameObject moon = new GameObject(Vector2.ZERO, new Vector2(SUN_DIM, SUN_DIM),
                new OvalRenderable(Color.LIGHT_GRAY));


        // Initiates the GameObject
        Vector2 moonLoc = new Vector2(windowDimensions.x()*0.5f, windowDimensions.y()*0.15f);
        moon.setCenter(moonLoc);

        gameObjects.addGameObject(moon, layer);
        moon.setTag("sun");
        moon.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);

        // Create the transition of the moon going round the world
        new Transition<Float>(moon,
                (angle) -> moon.setCenter(new Vector2((float)(windowDimensions.x()/2 + windowDimensions.x()/2*Math.sin(5*angle)),
                        (float)(windowDimensions.y()*1.3 -windowDimensions.y()*1.15*Math.cos(5*angle)))),
                INITIAL_DEGREES, INITIAL_DEGREES+FULL_CIRCLE,
                Transition.LINEAR_INTERPOLATOR_FLOAT, (int)Math.pow(cycleLength, CYCLE_LENGTH_TIME),
                Transition.TransitionType.TRANSITION_LOOP, null);
        return moon;
    }
}
