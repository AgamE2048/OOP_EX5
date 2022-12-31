package pepse;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.Transition;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import pepse.util.LayerFactory;
import pepse.world.Block;
import pepse.world.Sky;
import pepse.world.Terrain;
import pepse.world.daynight.Night;
import pepse.world.daynight.Sun;
import pepse.world.daynight.SunHalo;

import java.awt.*;
import java.time.temporal.TemporalAccessor;

public class pepseGameManager extends GameManager {
    private static final int CYCLE = 30;

    public static void main(String[] args) {
        new pepseGameManager().run();
    }

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        LayerFactory layerFactory = new LayerFactory();

        Sky.create(gameObjects(), windowController.getWindowDimensions(), layerFactory.chooseLayer("sky"));
        Terrain t = new Terrain(gameObjects(), layerFactory.chooseLayer("terrain"),
                windowController.getWindowDimensions()
                , 0);
        t.createInRange(0, (int) windowController.getWindowDimensions().x());
        Night.create(gameObjects(), layerFactory.chooseLayer("night"),
                windowController.getWindowDimensions(), CYCLE);

        GameObject sun = Sun.create(gameObjects(), layerFactory.chooseLayer("sun"),
                windowController.getWindowDimensions(), CYCLE);
        SunHalo.create(gameObjects(), layerFactory.chooseLayer("sunHalo"), sun, new Color(255, 255, 0, 60));
    }
}
