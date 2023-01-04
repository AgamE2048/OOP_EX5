package pepse.world.trees.leaves;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.components.ScheduledTask;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.world.Block;

import java.awt.*;
import java.util.Random;

public class Leaf {
    private static final Color BASE_COLOR_LEAVES = new Color(50, 200, 30);
    private static final int FADE_TIME = 5;
    private GameObject leaf;
    private int lifeTime;
    private int clearTime;

    private GameObjectCollection gameObjects;
    private Vector2 topLeftCorner;
    private int layer;
    private int groundLayer;

    public Leaf(GameObjectCollection gameObjects, int layer, int groundLayer){
        this.gameObjects = gameObjects;
        Random rand = new Random();
        this.lifeTime = rand.nextInt(30) + 5;
        this.clearTime = rand.nextInt(5) + 5;
        this.layer = layer;
        this.groundLayer = groundLayer;
    }

    public GameObject create(Vector2 topLeftCorner){
        this.topLeftCorner = topLeftCorner;
        GameObject leaf = new GameObject(topLeftCorner, Vector2.ONES.mult(Block.SIZE), new
                RectangleRenderable(ColorSupplier.approximateColor(BASE_COLOR_LEAVES)));
        this.leaf = leaf;
        this.gameObjects.addGameObject(leaf);
        leaf.setTag("leaf"); //TODO: add proper layer

        new ScheduledTask(leaf, (float)randX(10, 1), true, this::run);
        new ScheduledTask(leaf, this.lifeTime, true, this::falling);

        return leaf;
    }

    private int randX(int strech, int bound) {
        Random rand = new Random();
        return (int) (rand.nextDouble() * strech + bound);
    }

    private void run(){
        float angle = randX(10, 10);
        float size = randX(5,3);

        new Transition<Float>(leaf,
                a -> leaf.renderer().setRenderableAngle(a),
                -angle, angle,
                Transition.LINEAR_INTERPOLATOR_FLOAT, 7,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH, null);

        new Transition<Vector2>(leaf,
                leaf::setDimensions,
                Vector2.ONES.mult(Block.SIZE - size), Vector2.ONES.mult(Block.SIZE + size),
                Transition.LINEAR_INTERPOLATOR_VECTOR, 7,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH, null);
    }

    private void falling(){
        //this.gameObjects.layers().shouldLayersCollide(layer, groundLayer, true);
        leaf.transform().setVelocityY(70);
        new Transition<Float>(leaf,
                v -> leaf.transform().setVelocityX(v),
                -30.0f, 30.0f,
                Transition.CUBIC_INTERPOLATOR_FLOAT, 5,
                Transition.TransitionType.TRANSITION_LOOP, null);
        leaf.renderer().fadeOut(FADE_TIME, () -> new ScheduledTask(leaf, this.clearTime,
                true, this::releaf));
    }

    private void releaf(){
        this.gameObjects.removeGameObject(leaf);
        create(topLeftCorner);
    }
}
