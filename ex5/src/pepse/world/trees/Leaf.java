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

import static pepse.world.Terrain.rand;

public class Leaf extends GameObject{
    private static final Color BASE_COLOR_LEAVES = new Color(50, 200, 30);
    private static final int FADE_TIME = 7;
    private final int layer;

    private int lifeTime;
    private int clearTime;

    private GameObjectCollection gameObjects;
    private Vector2 topLeftCorner;
    private Transition<Float> horizrontalTransition;

    public Leaf(GameObjectCollection gameObjects, int layer, int groundLayer, Vector2 topLeftCorner){
        super(topLeftCorner, Vector2.ONES.mult(Block.SIZE),
                new RectangleRenderable(ColorSupplier.approximateColor(BASE_COLOR_LEAVES)));
        this.topLeftCorner = topLeftCorner;
        this.gameObjects = gameObjects;
        this.lifeTime =  rand.nextInt(30) + 5;
        this.clearTime = rand.nextInt(5) + 5;
        this.layer = layer;
//        this.groundLayer = groundLayer;
        this.gameObjects.addGameObject(this, layer);
        //System.out.println(this.layer);
        this.setTag("leaf"); //TODO: add proper layer
    }

    public GameObject create(){
//        this.gameObjects.addGameObject(this);
//        this.setTag("leaf"); //TODO: add proper layer

        new ScheduledTask(this, (float)randX(10, 1), false, this::run);
        new ScheduledTask(this, this.lifeTime, false, this::falling);

        return this;
    }

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
        this.horizrontalTransition = new Transition<Float>(this,
                v -> this.transform().setVelocityX(v),
                -30.0f, 30.0f,
                Transition.CUBIC_INTERPOLATOR_FLOAT, 5,
                Transition.TransitionType.TRANSITION_LOOP, null);
        this.renderer().fadeOut(FADE_TIME, () -> new ScheduledTask(this, this.clearTime,
                false, this::releaf));
        addComponent(horizrontalTransition);
    }

    private void releaf(){
        create();
        this.setTopLeftCorner(this.topLeftCorner);
        this.renderer().setOpaqueness(1);
        this.transform().setVelocityY(0);
        this.transform().setVelocityX(0);
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
