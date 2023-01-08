package pepse.world.daynight;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;

import java.awt.*;
import java.util.function.Consumer;

public class Night {
    // Class variables
    private static final float MIDNIGHT_OPACITY = 0.7f;
    private static final String NIGHT_TAG = "night";
    private static final float INITIAL_VAL = 0f;

    /**
     * This method returns a GameObject of type Night
     * @param gameObjects the grameObjects in the game
     * @param nightLayer the layer we want to place night at
     * @param windowDimentions the vector of the dimensions of the screen
     * @param cycleLength the time it takes a full day and night
     * @return a gameObject of type Night
     */
    public static GameObject create(GameObjectCollection gameObjects, int nightLayer,
                                    Vector2 windowDimentions,
                                    float cycleLength){
        // Creating the GameObject night
        GameObject night = new GameObject(Vector2.ZERO, windowDimentions,
                new RectangleRenderable(Color.BLACK));
        night.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects.addGameObject(night, nightLayer);
        night.setTag(NIGHT_TAG);

        // Creating the transition for the opacity of the night (goes from day to night)
        new Transition<Float>(night, night.renderer()::setOpaqueness, INITIAL_VAL, MIDNIGHT_OPACITY,
                Transition.CUBIC_INTERPOLATOR_FLOAT, cycleLength/2,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH, null);

        return night;
    }
}
