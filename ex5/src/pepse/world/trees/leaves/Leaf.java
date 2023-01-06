package pepse.world.trees.leaves;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.components.ScheduledTask;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.world.Block;

import java.awt.*;
import java.util.Random;

public class Leaf extends GameObject{
    private static final Color BASE_COLOR_LEAVES = new Color(50, 200, 30);
    private static final int FADE_TIME = 5;

    private int lifeTime;
    private int clearTime;

    private GameObjectCollection gameObjects;
    private Vector2 topLeftCorner;
    private int layer;
    private int groundLayer;
    private Transition<Float> horizrontalTransition;

    public Leaf(GameObjectCollection gameObjects, int layer, int groundLayer, Vector2 topLeftCorner){
        super(topLeftCorner, Vector2.ONES.mult(Block.SIZE),
                new RectangleRenderable(ColorSupplier.approximateColor(BASE_COLOR_LEAVES)));
        this.topLeftCorner = topLeftCorner;
        this.gameObjects = gameObjects;
        Random rand = new Random();
        this.lifeTime = rand.nextInt(30) + 5;
        this.clearTime = rand.nextInt(5) + 5;
        this.layer = layer;
        this.groundLayer = groundLayer;
        this.gameObjects.addGameObject(this);
        this.setTag("leaf"); //TODO: add proper layer
    }

    public GameObject create(){
//        this.gameObjects.addGameObject(this);
//        this.setTag("leaf"); //TODO: add proper layer

        new ScheduledTask(this, (float)randX(10, 1), true, this::run);
        new ScheduledTask(this, this.lifeTime, true, this::falling);

        return this;
    }

    private int randX(int strech, int bound) {
        Random rand = new Random();
        return (int) (rand.nextDouble() * strech + bound);
    }

    private void run(){
        float angle = randX(10, 10);
        float size = randX(5,3);

        new Transition<Float>(this,
                a -> this.renderer().setRenderableAngle(a),
                -angle, angle,
                Transition.LINEAR_INTERPOLATOR_FLOAT, 7,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH, null);

        new Transition<Vector2>(this,
                this::setDimensions,
                Vector2.ONES.mult(Block.SIZE - size), Vector2.ONES.mult(Block.SIZE + size),
                Transition.LINEAR_INTERPOLATOR_VECTOR, 7,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH, null);
    }

    private void falling(){
        this.transform().setVelocityY(70);
        this.horizrontalTransition = new Transition<Float>(this,
                v -> this.transform().setVelocityX(v),
                -30.0f, 30.0f,
                Transition.CUBIC_INTERPOLATOR_FLOAT, 5,
                Transition.TransitionType.TRANSITION_LOOP, null);
        this.renderer().fadeOut(FADE_TIME, () -> new ScheduledTask(this, this.clearTime,
                true, this::releaf));
        addComponent(horizrontalTransition);


    }

    private void releaf(){
        //TODO: fix leaves!!!!
        //this.gameObjects.removeGameObject(this);
        create();
        this.setTopLeftCorner(this.topLeftCorner);
        this.renderer().setOpaqueness(1);
 //       this.transform().setVelocityY(0);
 //       this.transform().setVelocityX(0);
    }

    /**
     * Called on the first frame of a collision.
     *
     * @param other     The GameObject with which a collision occurred.
     * @param collision Information regarding this collision.
     *                  A reasonable elastic behavior can be achieved with:
     *                  setVelocity(getVelocity().flipped(collision.getNormal()));
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        this.transform().setVelocityX(0);
        this.transform().setVelocityY(0);
        this.removeComponent(horizrontalTransition);
    }
}
