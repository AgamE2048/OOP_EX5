package pepse.world;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Avatar extends GameObject{
    private static final int MOVE_SPEED = 300;
    private static final int JUMP_VELOCITY = -300;
    private static final int ACCELERATION_FROM_SKY  = 500;
    public static final int INITIAL_ENERGY = 100;

    private static float energy;
    private UserInputListener inputListener;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     */
    public Avatar(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable) {
        super(topLeftCorner, dimensions, renderable);
    }

    public static Avatar create(GameObjectCollection gameObjects,
                                int layer, Vector2 topLeftCorner,
                                UserInputListener inputListener,
                                ImageReader imageReader){

        Renderable avatarRenderable = new RectangleRenderable(Color.black);
        Avatar avatar = new Avatar(topLeftCorner, new Vector2(100, 100),
                avatarRenderable);
        avatar.renderer().setIsFlippedHorizontally(true);

        energy = INITIAL_ENERGY;

        avatar.physics().preventIntersectionsFromDirection(Vector2.ZERO);

        return null;
    }


    /**
     * Should be called once per frame.
     *
     * @param deltaTime The time elapsed, in seconds, since the last frame. Can
     *                  be used to determine a new position/velocity by multiplying
     *                  this delta with the velocity/acceleration respectively
     *                  and adding to the position/velocity:
     *                  velocity += deltaTime*acceleration
     *                  pos += deltaTime*velocity
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Vector2 movementDir = Vector2.ZERO;
        if(inputListener.isKeyPressed(KeyEvent.VK_LEFT)){
            movementDir = movementDir.add(Vector2.LEFT);
        }

        if(inputListener.isKeyPressed(KeyEvent.VK_RIGHT)){
            movementDir = movementDir.add(Vector2.RIGHT);
        }

        if(inputListener.isKeyPressed(KeyEvent.VK_SPACE) && inputListener.isKeyPressed(KeyEvent.VK_SHIFT)){
            if(energy > 0){
                energy -= 0.5;
                movementDir = new Vector2(movementDir.x(), JUMP_VELOCITY);
            }
            else{
                this.transform().setAccelerationY(ACCELERATION_FROM_SKY);
            }
        }

        if(inputListener.isKeyPressed(KeyEvent.VK_SPACE)){
            if(movementDir.y() == 0){
                movementDir = new Vector2(0, JUMP_VELOCITY);
            }
        }

        //TODO: add option to stay in place

        this.setVelocity(movementDir.mult(MOVE_SPEED));
    }
}


