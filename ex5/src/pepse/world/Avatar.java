package pepse.world;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.components.ScheduledTask;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Avatar extends GameObject{

    private static final float VELOCITY_X = 300;
    private static final float VELOCITY_Y = -300;
    private static final float GRAVITY = 500;


    private static final int MOVE_SPEED = 300;
    private static final int JUMP_VELOCITY = -1;
    private static final int ACCELERATION_FROM_SKY = 500;
    public static final int INITIAL_ENERGY = 100;

    private static float energy;
    private final ImageReader imageReader;
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
    public Avatar(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                  UserInputListener inputListener, ImageReader imageReader) {
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
        this.imageReader = imageReader;
    }

    public static Avatar create(GameObjectCollection gameObjects,
                                int layer, Vector2 topLeftCorner,
                                UserInputListener inputListener,
                                ImageReader imageReader){

        Renderable avatarRenderable = new RectangleRenderable(Color.black);
        Avatar avatar = new Avatar(topLeftCorner, new Vector2(100, 100),
                avatarRenderable, inputListener, imageReader);
        avatar.renderer().setIsFlippedHorizontally(true);

        energy = INITIAL_ENERGY;

        avatar.physics().preventIntersectionsFromDirection(Vector2.ZERO);

        avatar.transform().setAccelerationY(GRAVITY);

        gameObjects.addGameObject(avatar, layer);

        return avatar;
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        if(other.getTag().equals("ground")){
            super.onCollisionEnter(other, collision);
            this.transform().setVelocityX(0);
            this.transform().setVelocityY(0);
        }
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
        float xVel = 0;
        if(inputListener.isKeyPressed(KeyEvent.VK_LEFT))
            xVel -= VELOCITY_X;
        if(inputListener.isKeyPressed(KeyEvent.VK_RIGHT))
            xVel += VELOCITY_X;
        transform().setVelocityX(xVel);
//        if(inputListener.isKeyPressed(KeyEvent.VK_SPACE) && getVelocity().y() == 0)
//            transform().setVelocityY(VELOCITY_Y);
        if(inputListener.isKeyPressed(KeyEvent.VK_SPACE) && inputListener.isKeyPressed(KeyEvent.VK_SHIFT)){
            if(energy > 0){
                energy -= 0.5;
                transform().setVelocityY(VELOCITY_Y);
            }
        }
        else if(inputListener.isKeyPressed(KeyEvent.VK_SPACE)){
            if(getVelocity().y() == 0){
                transform().setVelocityY(VELOCITY_Y);
                transform().setVelocityX(0);
            }
        }
//        else{
//            this.transform().setAccelerationY(ACCELERATION_FROM_SKY);
//        }
//
        if(getVelocity().y() == 0){
            energy += 0.5;
        }
    }
}


