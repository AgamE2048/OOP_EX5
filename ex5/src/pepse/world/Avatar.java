package pepse.world;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Avatar{
    private static final int MOVE_SPEED = 300;
    private static final int JUMP_VELOCITY = -300;
    private static final int ACCELERATION_FROM_SKY  = 500;
    public static final int INITIAL_ENERGY = 100;

    private static float energy;
    private UserInputListener inputListener;

    public static Avatar create(GameObjectCollection gameObjects,
                                int layer, Vector2 topLeftCorner,
                                UserInputListener inputListener,
                                ImageReader imageReader){

        Renderable avatarRenderable = new RectangleRenderable(Color.black);
        GameObject avatar = new GameObject(topLeftCorner, new Vector2(100, 100),
                avatarRenderable);
        avatar.renderer().setIsFlippedHorizontally(true);

        energy = INITIAL_ENERGY;

        avatar.physics().preventIntersectionsFromDirection(Vector2.ZERO);

        return null;
    }


    public void update(GameObject avatar) {
        Vector2 movementDir = Vector2.ZERO;
        if(inputListener.isKeyPressed(KeyEvent.VK_LEFT)){
            movementDir = movementDir.add(Vector2.LEFT);
        }

        if(inputListener.isKeyPressed(KeyEvent.VK_RIGHT)){
            movementDir = movementDir.add(Vector2.RIGHT);
        }

        if(inputListener.isKeyPressed(KeyEvent.VK_SPACE) && inputListener.isKeyPressed(KeyEvent.VK_SHIFT)){
            if(energy > 0){
                energy -= 0.5;
                movementDir = new Vector2(movementDir.x(), JUMP_VELOCITY);
            }
            else{
                avatar.transform().setAccelerationY(ACCELERATION_FROM_SKY);
            }

        }

        if(inputListener.isKeyPressed(KeyEvent.VK_SPACE)){
            if(movementDir.y() == 0){
                movementDir = new Vector2(0, JUMP_VELOCITY);
            }
        }

        //TODO: add option to stay in place

        avatar.setVelocity(movementDir.mult(MOVE_SPEED));
    }
}
