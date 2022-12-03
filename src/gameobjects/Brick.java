package gameobjects;

import brick_strategies.CollisionStrategy;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

public class Brick extends GameObject {
    private CollisionStrategy []collisionStrategies;
    private Counter counter;
    private boolean decramented_counter;

    /**
     * Construct a new GameObject instance.
     *  @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     */
    public Brick(Vector2 topLeftCorner, Vector2 dimensions,
                 Renderable renderable,
                 Counter counter) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionStrategies = collisionStrategies;
        this.counter = counter;
        counter.increment();
        decramented_counter = false;
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        for (CollisionStrategy strategy: collisionStrategies)
            if (strategy!=null && !decramented_counter)
                strategy.onCollision(this,other,counter);
    }

    public void loadStrategies(CollisionStrategy []collisionStrategies){
        this.collisionStrategies = collisionStrategies;
    }

    public void setDecramented_counter(){this.decramented_counter = true;}
}
