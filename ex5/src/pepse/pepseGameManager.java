package pepse;

import danogl.GameManager;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import pepse.util.LayerFactory;
import pepse.world.Block;
import pepse.world.Sky;
import pepse.world.Terrain;
import pepse.world.daynight.Night;

import java.time.temporal.TemporalAccessor;

public class pepseGameManager extends GameManager {

    public static void main(String[] args){
        new pepseGameManager().run();
    }

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        LayerFactory layerFactory = new LayerFactory();

        Sky.create(gameObjects(), windowController.getWindowDimensions(), layerFactory.chooseLayer("sky"));
        Terrain t = new Terrain(gameObjects(), layerFactory.chooseLayer("terrain"),
                windowController.getWindowDimensions()
                ,0);
        t.createInRange(0, 10);
        Night.create(gameObjects(), layerFactory.chooseLayer("night"),
                windowController.getWindowDimensions(),
                30);
    }

}
