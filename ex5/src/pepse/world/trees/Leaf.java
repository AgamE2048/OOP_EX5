package pepse.world.trees;

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

import static pepse.world.Terrain.rand;

public class Leaf extends GameObject{
    // Class variables
    private static final Color BASE_COLOR_LEAVES = new Color(50, 200, 30);
    private static final int FADE_TIME = 7;
    private static final String LEAF_TAG = "leaf";
    private final int lifeTime  =  rand.nextInt(30) + 5;;
    private final int clearTime = rand.nextInt(5) + 5;;

    private GameObjectCollection gameObjects;
    private Vector2 topLeftCorner;
    private Transition<Float> horizrontalTransition;

    /**
     * Basic leaf constructor
     * @param gameObjects the gameObjects in the game
     * @param layer the layer we want to place the GameObject at
     * @param topLeftCorner the top left corner where we want to place the Block
     */
    public Leaf(GameObjectCollection gameObjects, int layer, Vector2 topLeftCorner){
        super(topLeftCorner, Vector2.ONES.mult(Block.SIZE),
                new RectangleRenderable(ColorSupplier.approximateColor(BASE_COLOR_LEAVES)));
        this.topLeftCorner = topLeftCorner;
        this.gameObjects = gameObjects;
        this.gameObjects.addGameObject(this, layer);
        this.setTag(LEAF_TAG);
    }

    /**
     *
     * @return a GameObject of type Sun
     */
    public GameObject create(){

        new ScheduledTask(this, (float)randX(20, 1), false, this::run);
        new ScheduledTask(this, this.lifeTime, false, this::falling);

        return this;
    }

    /**
     *
     * @param strech
     * @param bound
     * @return
     */
    private int randX(int strech, int bound) {
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
        this.renderer().setRenderable(new RectangleRenderable(ColorSupplier.approximateColor(chooseColor())));
        this.horizrontalTransition = new Transition<Float>(this,
                v -> this.transform().setVelocityX(v),
                -30.0f, 30.0f,
                Transition.CUBIC_INTERPOLATOR_FLOAT, 5,
                Transition.TransitionType.TRANSITION_LOOP, null);
        this.renderer().fadeOut(FADE_TIME, () -> new ScheduledTask(this, this.clearTime,
                false, this::releaf));
        addComponent(horizrontalTransition);
    }

    private Color chooseColor() {
        Random rand = new Random();
        double num = rand.nextDouble();
        if(num < 0.5) return new Color(156, 36, 6);
        return new Color(243, 188, 46);
    }

    private void releaf(){
        this.setTopLeftCorner(this.topLeftCorner);
        this.renderer().setOpaqueness(1);
        this.transform().setVelocityY(0);
        this.transform().setVelocityX(0);
        create();
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
        if(other.getTag().equals("ground")){
            super.onCollisionEnter(other, collision);
//            super.update(0);
            this.removeComponent(horizrontalTransition);
            this.transform().setVelocityX(0);
            this.transform().setVelocityY(0);
            this.transform().setAcceleration(0,0);

            //TODO- explain n README that we wanted the leaves to move slightly on the ground with the breeze, like in reality
        }
    }
}
