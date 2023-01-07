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
import pepse.world.daynight.Night;
import pepse.world.daynight.Sun;
import pepse.world.daynight.SunHalo;
import pepse.world.trees.Tree;

import java.awt.*;

public class pepseGameManager extends GameManager {
    // Class variables
    private static final int CYCLE = 30;
    private static final int LOCATION_EXTRA_WORLD = 1000;
    private static final int DISTANCE_TO_EXTRA_WORLD = 600;
    private static int windowWidth;
    private int initial_center;
    private Avatar avatar;
    private int beginningWorld;
    private int endWorld;
    private LayerFactory layerFactory;
    private Terrain groundCreator;
    private Tree treeCreator;

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
    public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener
            inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        this.layerFactory = new LayerFactory();
        Vector2 windowDims = windowController.getWindowDimensions();
        this.beginningWorld = (int)(-windowDims.x()* 0.5);
        this.endWorld = (int)(windowDims.x() * 1.5);
        this.windowWidth = (int) windowDims.x();
        this.initial_center = (int) (windowDims.x()/2);
        // Create sky-related objects
        skyCreate(windowDims);
        // Creates ground-related objects
        groundCreate(windowDims);
        // Creates the avatar
        avatarCreate(windowDims, inputListener, imageReader);
    }

    private void skyCreate(Vector2 windowDims) {
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
    }

    private void groundCreate(Vector2 windowDims) {
        // Creates the ground
        this.groundCreator = new Terrain(gameObjects(), this.layerFactory.chooseLayer("terrain"),
                windowDims
                , 0);
        groundCreator.createInRange(beginningWorld, endWorld);
        // Creates the trees
        this.treeCreator = new Tree(gameObjects(), layerFactory.chooseLayer("tree"), windowDims,
                groundCreator::groundHeightAt );
        treeCreator.createInRange(beginningWorld, endWorld);
    }

    private void avatarCreate(Vector2 windowDims, UserInputListener inputListener, ImageReader imageReader) {
        // Creates the avatar
        Vector2 initialAvatarLoc = new Vector2(windowDims.x() * 0.5F, (float) ((Math.floor(groundCreator.groundHeightAt(windowDims.x() * 0.5F)/ Block.SIZE) - 1) * Block.SIZE));//windowDims.mult(0.5F);
        this.avatar = Avatar.create(this.gameObjects(), layerFactory.chooseLayer("avatar"),
                initialAvatarLoc, inputListener, imageReader);
        avatar.setCenter(initialAvatarLoc);
        setCamera(new Camera(avatar, new Vector2(windowDims.x()*0.5F - initialAvatarLoc.x(), -initialAvatarLoc.y() * 0.6F),
                windowDims, windowDims));
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if(Math.abs(this.avatar.getCenter().x() -initial_center) >= LOCATION_EXTRA_WORLD - DISTANCE_TO_EXTRA_WORLD){
            expandWorld();
            initial_center = (int) this.avatar.getCenter().x();
        }
        deleteObjects();
    }

    private void expandWorld() {
        if(this.avatar.getCenter().x() > initial_center){
            groundCreator.createInRange(endWorld - DISTANCE_TO_EXTRA_WORLD,
                    endWorld +LOCATION_EXTRA_WORLD - DISTANCE_TO_EXTRA_WORLD);
            treeCreator.createInRange(endWorld - DISTANCE_TO_EXTRA_WORLD,
                    endWorld +LOCATION_EXTRA_WORLD - DISTANCE_TO_EXTRA_WORLD);
            this.beginningWorld = endWorld;
            this.endWorld += windowWidth + DISTANCE_TO_EXTRA_WORLD;
        }
        else{
            groundCreator.createInRange(beginningWorld - (LOCATION_EXTRA_WORLD - DISTANCE_TO_EXTRA_WORLD),
                    beginningWorld + DISTANCE_TO_EXTRA_WORLD);
            treeCreator.createInRange(beginningWorld - (LOCATION_EXTRA_WORLD - DISTANCE_TO_EXTRA_WORLD),
                    beginningWorld + DISTANCE_TO_EXTRA_WORLD);
            this.endWorld = beginningWorld;
            this.beginningWorld -= (windowWidth + DISTANCE_TO_EXTRA_WORLD);
        }
        deleteObjects();
    }

    private void deleteObjects() {
        for (GameObject obj:this.gameObjects()) {
            if(obj.getCenter().x() > this.endWorld || obj.getCenter().x() < this.beginningWorld){
                System.out.println(this.beginningWorld);
                System.out.println(this.endWorld);
                System.out.println();
                this.gameObjects().removeGameObject(obj, this.layerFactory.chooseLayer(obj.getTag()));
                //, this.layerFactory.chooseLayer(obj.getTag())
            }
        }
    }
}
