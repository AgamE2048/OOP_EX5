package pepse.world;

import danogl.GameObject;
import danogl.components.GameObjectPhysics;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class Block extends GameObject {
    // Class variables
    public static final int SIZE = 30;

    /**
     * Basic constructor for the Block type GameObject
     * @param topLeftCorner the top left corner where we want to place the Block
     * @param renderable the renderable for the Block
     */
    public Block(Vector2 topLeftCorner, Renderable renderable){
        super(topLeftCorner, Vector2.ONES.mult(SIZE), renderable);
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        physics().setMass(GameObjectPhysics.IMMOVABLE_MASS);
        this.setTag("ground"); //TODO: check
    }

    /**
     * Basic constructor for the Block type GameObject
     * @param topLeftCorner the top left corner where we want to place the Block
     * @param renderable the renderable for the Block
     * @param height the height we want the block to be
     */
    public Block(Vector2 topLeftCorner, Renderable renderable, int height){
        super(topLeftCorner, new Vector2(SIZE, height), renderable);
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        physics().setMass(GameObjectPhysics.IMMOVABLE_MASS);
        this.setTag("tree");

    }

}
