package pepse;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.components.Transition;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.Camera;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.LayerFactory;
import pepse.world.Avatar;
import pepse.world.Block;
import pepse.world.Sky;
import pepse.world.Terrain;
import pepse.world.daynight.Night;
import pepse.world.daynight.Sun;
import pepse.world.daynight.SunHalo;
import pepse.world.trees.Tree;
import pepse.world.trees.leaves.Leaf;

import java.awt.*;
import java.time.temporal.TemporalAccessor;

public class pepseGameManager extends GameManager {
    // Class variables
    private static final int CYCLE = 30;

    public static void main(String[] args) {
        new pepseGameManager().run();
    }

    /**
     *
     * @param imageReader Contains a single method: readImage, which reads an image from disk.
     *                 See its documentation for help.
     * @param soundReader Contains a single method: readSound, which reads a wav file from
     *                    disk. See its documentation for help.
     * @param inputListener Contains a single method: isKeyPressed, which returns whether
     *                      a given key is currently pressed by the user or not. See its
     *                      documentation.
     * @param windowController Contains an array of helpful, self explanatory methods
     *                         concerning the window.
     */
    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        LayerFactory layerFactory = new LayerFactory();
        Vector2 windowDims = windowController.getWindowDimensions();
        // Creates the sky
        Sky.create(gameObjects(), windowDims, layerFactory.chooseLayer("sky"));
        // Creates the ground
        Terrain t = new Terrain(gameObjects(), layerFactory.chooseLayer("terrain"),
                windowDims
                , 0);
        t.createInRange(0, (int) windowDims.x());
        // Creates the night
        Night.create(gameObjects(), layerFactory.chooseLayer("night"),
                windowDims, CYCLE);
        // Creates the sun
        GameObject sun = Sun.create(gameObjects(), layerFactory.chooseLayer("sun"),
                windowDims, CYCLE);
        // Creates the sun-halo
        SunHalo.create(gameObjects(), layerFactory.chooseLayer("sunHalo"), sun, new Color(255, 255, 0, 60));
        // Creates the trees
        Tree tree = new Tree(gameObjects(), layerFactory.chooseLayer("tree"), windowDims, t );
        tree.createInRange(0, (int) windowDims.x());

//        Avatar avatar = new Avatar(new Vector2(300, 300), new Vector2(100, 100), new RectangleRenderable(Color.CYAN));

        Vector2 initialAvatarLoc = new Vector2(300,
                600);
        Avatar avatar = Avatar.create(this.gameObjects(), layerFactory.chooseLayer("avatar"), new Vector2(300,
                        600),
                inputListener, imageReader);
        setCamera(new Camera(avatar,
                windowController.getWindowDimensions().mult(0.5f).subtract(initialAvatarLoc),
                //Vector2.ZERO,
                windowController.getWindowDimensions(),
                windowController.getWindowDimensions()));
    }
}
