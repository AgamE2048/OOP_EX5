package pepse.world.trees;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.world.Block;

import java.awt.*;
import java.util.Random;

public class Trunk {
    private static final char ROUND_DOWN = '-';
    private static final Color BASE_TRUNK_COLOR = new Color(61, 27, 4);
    private final float groundHeight;
    private final GameObjectCollection gameObjects;
    private final Vector2 windowDimensions;
    private int locOfX;
    private int layer;
    public Trunk(GameObjectCollection gameObjects, Vector2 windowDimensions ,float groundHeight, int locOfX
            , int layer) {
        this.groundHeight = groundHeight;
        this.locOfX = locOfX;
        this.gameObjects = gameObjects;
        this.windowDimensions = windowDimensions;
        this.layer = layer;
    }

    public GameObject create(){
        Renderable r = new RectangleRenderable((ColorSupplier.approximateColor(BASE_TRUNK_COLOR)));
        float height = randX(40, 60);
        float width = randX(30, 20);
        Vector2 dims = new Vector2(width, height);
        System.out.println(height);
        System.out.println("Ground height: " + ground.groundHeightAt((float)x));
        System.out.println("Rounded Ground height: " + (roundX((int) ground.groundHeightAt((float)x),
                '-')));
        Vector2 topLeftCorner = new Vector2((float) x,
                windowDimensions.y() - (roundX((int) ground.groundHeightAt((float)x),'-') + height));
        System.out.println("Top left corner " + topLeftCorner.y());
        GameObject treeTrunk = new GameObject(topLeftCorner, dims, r);
        gameObjects.addGameObject(treeTrunk, layer);


    }

    private int randX(int strech, int bound) {
        Random rand = new Random();
        return (int) (rand.nextDouble() * strech + bound);
    }

    private int roundX(int x, char c) {
        int num = 0;
        if(c == ROUND_DOWN){
            num = Block.SIZE*Block.SIZE;
            while(num > x)
                num -= Block.SIZE;

        }
        else{
            while (num < x)
                num += Block.SIZE;
        }
        return num;
    }

}
