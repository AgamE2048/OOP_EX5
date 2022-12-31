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
        float height = randX(150, 100);
        float x = (float) roundX(this.locOfX, '-');
        float y = windowDimensions.y() - (roundX((int) this.groundHeight,'-'));
        Renderable r = new RectangleRenderable((ColorSupplier.approximateColor(BASE_TRUNK_COLOR)));
        for (int i = 0; i < (int)Math.ceil(height/Block.SIZE); i++) {
            double y_height = y - i* Block.SIZE;
            Vector2 vec = new Vector2(x, (float) (y_height));
            gameObjects.addGameObject(new Block(vec, r), this.layer);
        }
        return null;
    }

    private int randX(int strech, int bound) {
        Random rand = new Random();
        return (int) (rand.nextDouble() * strech + bound);
    }

    private int roundX(int x, char c) {
        int num = Block.SIZE*x;
        if(c == ROUND_DOWN){
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
