package pepse.world;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.AnimationRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Avatar extends GameObject{

    private static final String[] CLIPS = {"assets/pig_standing_while_walking.png", "assets" +
            "/flying_pig_walking.png",
             "assets/flying_pig_flying.png"};

    private static final float VELOCITY_X = 300;
    private static final float VELOCITY_Y = -300;
    private static final float GRAVITY = 500;
    public static final int INITIAL_ENERGY = 100;
    private static final double TIME_BETWEEN_FRAMES = 4;
    private static final int STANDING_PIG = 0;
    private static final int PIG_WALKING = 1;
    private static final int PIG_FLYING = 2;

    private static float energy;
    private UserInputListener inputListener;
    private static Renderable[] renderedAvatarImages;

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
                  UserInputListener inputListener) {
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
    }

    public static Avatar create(GameObjectCollection gameObjects,
                                int layer, Vector2 topLeftCorner,
                                UserInputListener inputListener,
                                ImageReader imageReader){

        //Renderable avatarRenderable = new RectangleRenderable(Color.black);
        Renderable avatarRenderable = new AnimationRenderable(CLIPS, imageReader, true, TIME_BETWEEN_FRAMES);
        renderedAvatarImages = new Renderable[CLIPS.length];
        for(int i=0;i<CLIPS.length;i++){
            renderedAvatarImages[i] = imageReader.readImage(CLIPS[i], true);
        }

        Avatar avatar = new Avatar(topLeftCorner, new Vector2(100, 100),
                avatarRenderable, inputListener);


        energy = INITIAL_ENERGY;

        avatar.physics().preventIntersectionsFromDirection(Vector2.ZERO);

        avatar.transform().setAccelerationY(GRAVITY);

        gameObjects.addGameObject(avatar, layer);

        avatar.renderer().setRenderable(avatarRenderable);

        avatar.renderer().setRenderable(renderedAvatarImages[STANDING_PIG]);

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
        if(inputListener.isKeyPressed(KeyEvent.VK_LEFT)) {
            renderer().setRenderable(renderedAvatarImages[PIG_WALKING]);
            renderer().setIsFlippedHorizontally(false);
            xVel -= VELOCITY_X;
        }

        if(inputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            renderer().setRenderable(renderedAvatarImages[PIG_WALKING]);
            renderer().setIsFlippedHorizontally(true);
            xVel += VELOCITY_X;
        }

        transform().setVelocityX(xVel);
        if(inputListener.isKeyPressed(KeyEvent.VK_SPACE) && inputListener.isKeyPressed(KeyEvent.VK_SHIFT)){
            renderer().setRenderable(renderedAvatarImages[PIG_FLYING]);
            if(energy > 0){
                energy -= 0.5;
                transform().setVelocityY(VELOCITY_Y);
                physics().preventIntersectionsFromDirection(Vector2.ZERO);
            }

        }
        else if(inputListener.isKeyPressed(KeyEvent.VK_SPACE)){
            if(getVelocity().y() == 0){
                renderer().setRenderable(renderedAvatarImages[PIG_FLYING]);
                transform().setVelocityY(VELOCITY_Y);
                transform().setVelocityX(0);
                physics().preventIntersectionsFromDirection(Vector2.ZERO);
            }
        }
        if(getVelocity().y() == 0){
            renderer().setRenderable(renderedAvatarImages[STANDING_PIG]);
            energy += 0.5;
        }
    }
}


