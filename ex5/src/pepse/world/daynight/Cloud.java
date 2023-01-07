package pepse.world.daynight;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;

import java.awt.*;

public class Cloud {

    public static GameObject create(GameObjectCollection gameObjects, int layer, Vector2 windowDimensions,
                                    float cycleLength){
        GameObject cloud = new GameObject(Vector2.ZERO, new Vector2(20, 20),
                new OvalRenderable(Color.YELLOW));

        // Initiates the GameObject
        Vector2 cloudLoc = new Vector2(windowDimensions.x()*0.5f, windowDimensions.y()*0.15f);
        cloud.setCenter(cloudLoc);

        gameObjects.addGameObject(cloud, layer);
        cloud.setTag("sun");
        cloud.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);

        // Create the transition of the sun going round the world
//        new Transition<Float>(cloud,
//                (angle) -> sun.setCenter(new Vector2((float)(windowDimensions.x()/2 + windowDimensions.x()/2*Math.sin(5*angle)),
//                        (float)(windowDimensions.y()*1.3 -windowDimensions.y()*1.15*Math.cos(5*angle)))),
//                INITIAL_DEGREES, INITIAL_DEGREES+FULL_CIRCLE,
//                Transition.LINEAR_INTERPOLATOR_FLOAT, (int)Math.pow(cycleLength, CYCLE_LENGTH_TIME),
//                Transition.TransitionType.TRANSITION_LOOP, null);
        return cloud;
    }
}
