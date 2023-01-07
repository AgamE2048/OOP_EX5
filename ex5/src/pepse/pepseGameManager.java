package pepse;

import danogl.GameManager;
import danogl.GameObject;
import danogl.components.ScheduledTask;
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
    private static final int DISTANCE_TO_EXTRA_WORLD = 2;
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
        this.beginningWorld = (int)(-windowDims.x());
        this.endWorld = (int)(windowDims.x() * 1.5);
        this.windowWidth = (int) windowDims.x();
        this.initial_center = (int) (windowDims.x()/2);
        // Create sky-related objects
        skyCreate(windowDims);
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
        this.groundCreator = new Terrain(gameObjects(), this.layerFactory.chooseLayer("ground"),
                windowDims
                , 0);
        groundCreator.createInRange(beginningWorld, endWorld);
        // Creates the trees
        this.treeCreator = new Tree(gameObjects(), layerFactory.chooseLayer("tree"), windowDims,
                groundCreator::groundHeightAt);
        treeCreator.createInRange(beginningWorld, endWorld);
    }

    private void avatarCreate(Vector2 windowDims, UserInputListener inputListener, ImageReader imageReader) {
        // Creates the avatar
        Vector2 initialAvatarLoc = new Vector2(windowDims.x() * 0.5F, (float) ((Math.floor(groundCreator.groundHeightAt(windowDims.x() * 0.5F)/ Block.SIZE) - 1) * Block.SIZE));//windowDims.mult(0.5F);
        this.aang = Avatar.create(this.gameObjects(), layerFactory.chooseLayer("avatar"),
                initialAvatarLoc, inputListener, imageReader);
        this.aang.setTag("avatar");
        aang.setCenter(new Vector2(windowDims.x() * 0.5F, windowDims.y() * 0.2F));
        setCamera(new Camera(aang, new Vector2(windowDims.x()*0.5F - initialAvatarLoc.x(), -initialAvatarLoc.y() * 0.3F),
                windowDims, windowDims));
        new ScheduledTask(camera(), 0.5F, true, this::expandWorld);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if(Math.abs(this.aang.getCenter().x() - initial_center) >= windowWidth/2){
//            deleteObjects();
//            expandWorld();
        }
//        deleteObjects();
    }

    private void expandWorld() {
        float center = aang.transform().getCenter().x();
        deleteObjects(this.layerFactory.chooseLayer("tree"), center);
        deleteObjects(this.layerFactory.chooseLayer("leaf"), center);
        deleteObjects(this.layerFactory.chooseLayer("ground"), center);

        if (aang.getCenter().x() > initial_center) {
            groundCreator.createInRange(
                    (int) LOCATION_EXTRA_WORLD + initial_center,
                    (int) (aang.getCenter().x() + LOCATION_EXTRA_WORLD));

            treeCreator.createInRange(
                    (int) LOCATION_EXTRA_WORLD + initial_center,
                    (int) (aang.getCenter().x() + LOCATION_EXTRA_WORLD));

        } else if (aang.getCenter().x() < initial_center) {

            groundCreator.createInRange(
                    (int) (aang.getCenter().x() - LOCATION_EXTRA_WORLD),
                    (int) (-LOCATION_EXTRA_WORLD + initial_center));

            treeCreator.createInRange(
                    (int) (aang.getCenter().x() - LOCATION_EXTRA_WORLD),
                    (int) -(LOCATION_EXTRA_WORLD + initial_center));
        }
        initial_center = (int) center;
//        activeTerrainRange = new Vector2(-REFRESH_DISTANCE + center, REFRESH_DISTANCE + center);
    }

//    private void expandWorld() {
//        if(this.aang.getCenter().x() > initial_center){
//            groundCreator.createInRange(endWorld, endWorld + windowWidth - DISTANCE_TO_EXTRA_WORLD);
//            treeCreator.createInRange(endWorld, endWorld + windowWidth - DISTANCE_TO_EXTRA_WORLD);
//            this.beginningWorld = endWorld - DISTANCE_TO_EXTRA_WORLD;
//            this.endWorld += windowWidth + DISTANCE_TO_EXTRA_WORLD;
//        }
//        if(this.aang.getCenter().x() < initial_center){
//            groundCreator.createInRange(beginningWorld - (windowWidth + DISTANCE_TO_EXTRA_WORLD),
//                    beginningWorld);
//            treeCreator.createInRange(beginningWorld - (windowWidth + DISTANCE_TO_EXTRA_WORLD),
//                    beginningWorld);
//            this.endWorld = beginningWorld + DISTANCE_TO_EXTRA_WORLD;
//            this.beginningWorld -= (windowWidth + DISTANCE_TO_EXTRA_WORLD);
//        }
//        initial_center = (int) this.aang.getCenter().x();
//    }

    private void deleteObjects(int layer, float worldCenter) {
        for (var obj : gameObjects()) {
            var distanceScreenCenter = Math.abs(obj.getCenter().x() - worldCenter);
            if (distanceScreenCenter > LOCATION_EXTRA_WORLD) {
                gameObjects().removeGameObject(obj, layer);
            }
        }
            //int layer) {
//        for (GameObject obj:this.gameObjects()) {
//            if(obj.getCenter().x() > this.endWorld || obj.getCenter().x() < this.beginningWorld){
//                if(this.layerFactory.shouldErase(obj.getTag())) {
//                    this.gameObjects().removeGameObject(obj, this.layerFactory.chooseLayer(obj.getTag()));
//                }
//            }
//        }
    }
}
