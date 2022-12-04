package gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

public class TimedPaddle extends Paddle {
    private Counter hp;
    private GameObjectCollection gameObjects;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner     Position of the object, in window coordinates (pixels).
     *                          Note that (0,0) is the top-left corner of the window.
     * @param dimensions        Width and height in window coordinates.
     * @param renderable        The renderable representing the object. Can be null, in which case
     * @param userInputListener
     * @param windowDimensions
     */
    public TimedPaddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                       UserInputListener userInputListener, Vector2 windowDimensions, Counter hp,
                       GameObjectCollection gameObjects) {
        super(topLeftCorner, dimensions, renderable, userInputListener, windowDimensions);
        this.hp = hp;
        this.gameObjects = gameObjects;
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        hp.decrement();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (hp.value() == 0)
            gameObjects.removeGameObject(this);
    }

}
