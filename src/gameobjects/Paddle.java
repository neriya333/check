package gameobjects;

import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

public class Paddle extends GameObject {
    private static final float MOVE_SPEED = 300;
    private UserInputListener userInputListener;
    private Vector2 windowDimensions;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     */
    public Paddle(Vector2 topLeftCorner, Vector2 dimensions,
                  Renderable renderable, UserInputListener userInputListener,
                  Vector2 windowDimensions) {
        super(topLeftCorner, dimensions, renderable);
        this.userInputListener = userInputListener;
        this.windowDimensions = windowDimensions;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Vector2 movement_dir = Vector2.ZERO;
        if(this.userInputListener.isKeyPressed(KeyEvent.VK_LEFT) && this.getTopLeftCorner().x() > 0) {
            movement_dir = movement_dir.add(Vector2.LEFT);
        }
        else if(userInputListener.isKeyPressed(KeyEvent.VK_RIGHT) && getTopLeftCorner().x() + this.getDimensions().x() < windowDimensions.x()) {
            movement_dir = movement_dir.add(Vector2.RIGHT);
        }
        setVelocity(movement_dir.mult(MOVE_SPEED));
    }
}
