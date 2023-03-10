package pepse.world;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.components.CoordinateSpace;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.AnimationRenderable;
import danogl.gui.rendering.Renderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

public class Avatar extends GameObject {
    // Class variables
    private static final String[] CLIPS = {"assets/pig_standing_while_walking.png", "assets" +
            "/flying_pig_walking.png",
            "assets/flying_pig_flying.png", "assets/pig_flying_2.png"};

    private static final String[] CLIPS_STANDING = {"assets/pig_standing_1.png", "assets/pig_standing_2" +
            ".png", "assets/pig_standing_3.png", "assets/pig_standing_4.png"};

    private static final String[] CLIPS_WALKING = {"assets/pig_walking_1.png", "assets/pig_walking_2.png",
            "assets/pig_walking_3.png", "assets/pig_walking_4.png", "assets/pig_walking_5.png", "assets" +
            "/pig_standing_1.png"};

    private static final String[] CLIPS_FLYING = {"assets/pig_standing_1.png", "assets/pig_standing_2" +
            ".png", "assets/pig_standing_3.png"};
    private static final float VELOCITY_X = 300;
    private static final float VELOCITY_Y = -300;
    private static final float GRAVITY = 400;
    public static final int INITIAL_ENERGY = 100;
    private static final double TIME_BETWEEN_FRAMES = 0.2;
    private static final int MAX_VEL = 500;

    private static float energy;
    private final TextRenderable textRenderable;
    private final GameObject text;
    private UserInputListener inputListener;
    private static Renderable[] renderedAvatarImagesStanding;
    private static Renderable[] renderedAvatarImagesWalking;
    private static Renderable[] renderedAvatarImagesFalying;

    private static AnimationRenderable avatarRenderableStanding;
    private static AnimationRenderable avatarRenderableWalking;
    private static AnimationRenderable avatarRenderableFlying;

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
                  UserInputListener inputListener, GameObjectCollection gameObjects) {
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
        this.textRenderable = new TextRenderable("" + energy);
        this.text = new GameObject(Vector2.ZERO, new Vector2(100, 50), this.textRenderable);
        this.text.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects.addGameObject(this.text);
    }

    /**
     * This method returns a GameObject of type Avatar
     *
     * @param gameObjects   the GameObjects in the game
     * @param layer         the layer we will add the
     * @param topLeftCorner the location of the top left corner where we want to place the avatar
     * @param inputListener the inputlistener
     * @param imageReader   the image reader
     * @return an instance of Avatar anng
     */
    public static Avatar create(GameObjectCollection gameObjects,
                                int layer, Vector2 topLeftCorner,
                                UserInputListener inputListener,
                                ImageReader imageReader) {
        avatarRenderableStanding = new AnimationRenderable(CLIPS_STANDING, imageReader, true,
                TIME_BETWEEN_FRAMES);

        avatarRenderableWalking = new AnimationRenderable(CLIPS_WALKING, imageReader, true,
                TIME_BETWEEN_FRAMES);

        avatarRenderableFlying = new AnimationRenderable(CLIPS_FLYING, imageReader, true,
                TIME_BETWEEN_FRAMES);

        //        TIME_BETWEEN_FRAMES);
        renderedAvatarImagesStanding = new Renderable[CLIPS_STANDING.length];
        for (int i = 0; i < CLIPS_STANDING.length; i++) {
            renderedAvatarImagesStanding[i] = imageReader.readImage(CLIPS_STANDING[i], true);
        }

        renderedAvatarImagesWalking = new Renderable[CLIPS_WALKING.length];
        for (int i = 0; i < CLIPS_WALKING.length; i++) {
            renderedAvatarImagesWalking[i] = imageReader.readImage(CLIPS_WALKING[i], true);
        }

        renderedAvatarImagesFalying = new Renderable[CLIPS_FLYING.length];
        for (int i = 0; i < CLIPS_FLYING.length; i++) {
            renderedAvatarImagesFalying[i] = imageReader.readImage(CLIPS_FLYING[i], true);
        }

        Avatar avatar = new Avatar(topLeftCorner, new Vector2(100, 100),
                avatarRenderableStanding, inputListener, gameObjects);

        energy = INITIAL_ENERGY;
        avatar.physics().preventIntersectionsFromDirection(Vector2.ZERO);
        avatar.transform().setAccelerationY(GRAVITY);
        gameObjects.addGameObject(avatar, layer);
        return avatar;
    }

    /**
     * @param other     The GameObject with which a collision occurred.
     * @param collision Information regarding this collision.
     *                  A reasonable elastic behavior can be achieved with:
     *                  setVelocity(getVelocity().flipped(collision.getNormal()));
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        if (other.getTag().equals("ground")) {
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

        if (getVelocity().y() == 0) {
            renderer().setRenderable(avatarRenderableStanding);
            if (energy < 100) energy += 0.5;
        }

        if (inputListener.isKeyPressed(KeyEvent.VK_LEFT)) {
            renderer().setRenderable(avatarRenderableWalking);
            renderer().setIsFlippedHorizontally(false);
            xVel -= VELOCITY_X;
        }

        if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            renderer().setRenderable(avatarRenderableWalking);
            renderer().setIsFlippedHorizontally(true);
            xVel += VELOCITY_X;
        }

        transform().setVelocityX(xVel);

        if (inputListener.isKeyPressed(KeyEvent.VK_SPACE) && inputListener.isKeyPressed(KeyEvent.VK_SHIFT)) {
            renderer().setRenderable(avatarRenderableFlying);
            if (energy > 0) {
                energy -= 0.5;
                transform().setVelocityY(VELOCITY_Y);
                physics().preventIntersectionsFromDirection(Vector2.ZERO);
            }

        } else if (inputListener.isKeyPressed(KeyEvent.VK_SPACE)) {
            if (getVelocity().y() == 0) {
                renderer().setRenderable(avatarRenderableFlying);
                transform().setVelocityY(VELOCITY_Y);
                transform().setVelocityX(0);
                physics().preventIntersectionsFromDirection(Vector2.ZERO);
            }
        }
        
        if (getVelocity().y() >= MAX_VEL) {
            transform().setVelocityY(MAX_VEL);
        }
        this.textRenderable.setString("" + energy);
    }
}


