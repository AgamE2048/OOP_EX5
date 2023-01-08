package pepse;

import danogl.GameManager;
import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.Camera;
import danogl.util.Vector2;
import pepse.util.LayerFactory;
import pepse.world.Avatar;
import pepse.world.Block;
import pepse.world.Sky;
import pepse.world.Terrain;
import pepse.world.daynight.*;
import pepse.world.trees.Tree;

import java.awt.*;

public class pepseGameManager extends GameManager {
    // Class variables
    private static final int CYCLE = 30;
    private static final int LOCATION_EXTRA_WORLD = 1000;
    private static final int SEED = 100;
    private static final int RADIUS_SIZE = 7;
    private static int windowWidth;
    private int initial_center;
    private Avatar aang;
    private int beginningWorld;
    private int endWorld;
    private LayerFactory layerFactory;
    private Terrain groundCreator;
    private Tree treeCreator;

    public static void main(String[] args) {
        new pepseGameManager().run();
    }

    /**
     * @param imageReader      Contains a single method: readImage, which reads an image from disk.
     *                         See its documentation for help.
     * @param soundReader      Contains a single method: readSound, which reads a wav file from
     *                         disk. See its documentation for help.
     * @param inputListener    Contains a single method: isKeyPressed, which returns whether
     *                         a given key is currently pressed by the user or not. See its
     *                         documentation.
     * @param windowController Contains an array of helpful, self explanatory methods
     *                         concerning the window.
     */
    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener
            inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        windowController.setTargetFramerate(20);
        this.layerFactory = new LayerFactory();
        Vector2 windowDims = windowController.getWindowDimensions();
        this.beginningWorld = (int) (-windowDims.x());
        this.endWorld = (int) (windowDims.x() * 2);
        this.windowWidth = (int) windowDims.x();
        this.initial_center = (int) (windowDims.x() / 2);
        // Create sky-related objects
        skyCreate(windowDims, imageReader);
        // Creates ground-related objects
        groundCreate(windowDims);
        // Creates the avatar
        avatarCreate(windowDims, inputListener, imageReader);

        this.gameObjects().layers().shouldLayersCollide(layerFactory.chooseLayer("leaf"),
                layerFactory.chooseLayer("ground"),
                true);
        this.gameObjects().layers().shouldLayersCollide(layerFactory.chooseLayer("avatar"),
                layerFactory.chooseLayer("tree"),
                true);
    }

    /**
     * Creates the Sky-related objects
     *
     * @param windowDims  the vector with teh size of the dimensions
     * @param imageReader the image reader
     */
    private void skyCreate(Vector2 windowDims, ImageReader imageReader) {
        // Creates the sky
        Sky.create(gameObjects(), windowDims, this.layerFactory.chooseLayer("sky"));
        // Creates the night
        Night.create(gameObjects(), this.layerFactory.chooseLayer("night"),
                windowDims, CYCLE);
        // Creates the sun
        GameObject sun = Sun.create(gameObjects(), this.layerFactory.chooseLayer("sun"),
                windowDims, CYCLE);
        // Creates the sun-halo
        SunHalo.create(gameObjects(), this.layerFactory.chooseLayer("sunHalo"), sun, new Color(255, 255,
                0, 60));
        GameObject moon = Moon.create(gameObjects(), this.layerFactory.chooseLayer("sun"),
                windowDims, CYCLE);
    }

    /**
     * Creates the Ground-related objects
     *
     * @param windowDims the vector with teh size of the dimensions
     */
    private void groundCreate(Vector2 windowDims) {
        // Creates the ground
        this.groundCreator = new Terrain(gameObjects(), this.layerFactory.chooseLayer("ground"),
                windowDims
                , SEED);
        groundCreator.createInRange(beginningWorld, endWorld);
        // Creates the trees
        this.treeCreator = new Tree(gameObjects(), layerFactory.chooseLayer("tree"), windowDims,
                groundCreator::groundHeightAt);
        treeCreator.createInRange(0, windowWidth);
    }

    /**
     * Creates the Avatar
     *
     * @param windowDims    the vector with teh size of the dimensions
     * @param inputListener the input listener
     * @param imageReader   the image reader
     */
    private void avatarCreate(Vector2 windowDims, UserInputListener inputListener, ImageReader imageReader) {
        // Creates the avatar
        Vector2 initialAvatarLoc = new Vector2(windowDims.x() * 0.5F, (float) ((Math.floor(groundCreator.groundHeightAt(windowDims.x() * 0.5F) / Block.SIZE) - 1) * Block.SIZE));//windowDims.mult(0.5F);
        this.aang = Avatar.create(this.gameObjects(), layerFactory.chooseLayer("avatar"),
                initialAvatarLoc, inputListener, imageReader);
        this.aang.setTag("avatar");
        aang.setCenter(new Vector2(windowDims.x() * 0.5F, windowDims.y() * 0.2F));
        setCamera(new Camera(aang, new Vector2(windowDims.x() * 0.5F - initialAvatarLoc.x(), -initialAvatarLoc.y() * 0.3F),
                windowDims, windowDims));
    }

    /**
     * @param deltaTime The time, in seconds, that passed since the last invocation
     *                  of this method (i.e., since the last frame). This is useful
     *                  for either accumulating the total time that passed since some
     *                  event, or for physics integration (i.e., multiply this by
     *                  the acceleration to get an estimate of the added velocity or
     *                  by the velocity to get an estimate of the difference in position).
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (Math.abs(this.aang.getCenter().x() - initial_center) >= windowWidth / 3f) {
            expandWorld();
        }
    }

    /**
     * Increases teh size of the world needed for the game
     */
    private void expandWorld() {
        float center = aang.transform().getCenter().x();
        deleteObjects(this.layerFactory.chooseLayer("leaf"), center);
        deleteObjects(this.layerFactory.chooseLayer("tree"), center);
        deleteObjects(this.layerFactory.chooseLayer("ground"), center);

        if (center > initial_center) {
            treeCreator.createInRange(
                    (int) windowWidth + initial_center,
                    (int) (center + 2 * windowWidth));
            groundCreator.createInRange(
                    (int) windowWidth + initial_center,
                    (int) (center + 2 * windowWidth));

        }
        if (center < initial_center) {
            treeCreator.createInRange(
                    (int) (center - 2 * windowWidth),
                    (int) ((-windowWidth) + initial_center));
            groundCreator.createInRange(
                    (int) (center - windowWidth * 2),
                    (int) ((-windowWidth) + initial_center));
        }
        initial_center = (int) center;
    }

    /**
     * Delets out of bound objects at certain layers
     *
     * @param layer       the layer where we want to erase objects
     * @param worldCenter the center from which we want to erase
     */
    private void deleteObjects(int layer, float worldCenter) {
        for (GameObject obj : gameObjects()) {
            float distanceScreenCenter = Math.abs(obj.getCenter().x() - worldCenter);
            if (distanceScreenCenter > LOCATION_EXTRA_WORLD) {
                String str = obj.getTag();
                float x = obj.getCenter().x();
                gameObjects().removeGameObject(obj, layer);
                if (str.equals("tree")) {
                    for (GameObject newObj : gameObjects()) {
                        float dist = Math.abs(x - worldCenter);
                        if (dist < RADIUS_SIZE) {
                            gameObjects().removeGameObject(newObj, layerFactory.chooseLayer("leaf"));
                        }
                    }
                }
            }
        }
    }
}
