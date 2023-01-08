package pepse.world.daynight;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;

import java.awt.*;

public class Sun {
    // Class variables
    protected static final float SUN_DIM = 100.0f;
    protected static final String SUN_TAG = "sun";
    private static final float INITIAL_DEGREES = 5.0f;
    protected static final float FULL_CIRCLE  = 360.0f;
    protected static final double CYCLE_LENGTH_TIME =  2.65;

    /**
     * Creates a GameObject of type Sun
     * @param gameObjects the gameObjects in the game
     * @param layer the layer we want to place the GameObject at
     * @param windowDimensions the vector with the dimensions of the screen
     * @param cycleLength how long it takes a full night and day
     * @return a GameObject of type Sun
     */
    public static GameObject create(GameObjectCollection gameObjects, int layer, Vector2 windowDimensions,
                                    float cycleLength){
        GameObject sun = new GameObject(Vector2.ZERO, new Vector2(SUN_DIM, SUN_DIM),
                new OvalRenderable(Color.YELLOW));

        // Initiates the GameObject
        Vector2 sunLoc = new Vector2(windowDimensions.x()*0.5f, windowDimensions.y()*0.15f);
        sun.setCenter(sunLoc);

        gameObjects.addGameObject(sun, layer);
        sun.setTag(SUN_TAG);
        sun.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);

        // Create the transition of the sun going round the world
        new Transition<Float>(sun,
                (angle) -> sun.setCenter(new Vector2((float)(windowDimensions.x()/2 + windowDimensions.x()/2*Math.sin(5*angle)),
                        (float)(windowDimensions.y()*1.3 -windowDimensions.y()*1.15*Math.cos(5*angle)))),
                INITIAL_DEGREES, INITIAL_DEGREES+FULL_CIRCLE,
                Transition.LINEAR_INTERPOLATOR_FLOAT, (int)Math.pow(cycleLength, CYCLE_LENGTH_TIME),
                Transition.TransitionType.TRANSITION_LOOP, null);
        return sun;
    }

    /**
     *
     * @param windowDimensions vector with the dimensions if the screen
     * @param angleInSky the angle we want to have the sun at
     * @return the location that matches the angleInSky
     */
    private static Vector2 calcSunPosition(Vector2 windowDimensions, float angleInSky){
        float x = (float)(windowDimensions.x()*Math.sin(angleInSky));
        float y = (float)(windowDimensions.x()*Math.cos(angleInSky));
        return new Vector2(x, y);
    }
}
