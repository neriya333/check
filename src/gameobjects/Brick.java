package gameobjects;

import brick_strategies.CollisionStrategy;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * Brick - a GameObject the winCondition is to remove all of its instances from the game.
 * removing is done by collision of it and another object.
 * removing it apply the strategies each brick has.
 */
public class Brick extends GameObject {
    private final Counter numberOfBricks;
    private CollisionStrategy[] collisionStrategies;
    private boolean decramentedCounter;
    
    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     */
    public Brick(Vector2 topLeftCorner, Vector2 dimensions,
                 Renderable renderable,
                 Counter counter) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionStrategies = collisionStrategies;
        this.numberOfBricks = counter;
        counter.increment();
        decramentedCounter = false;
    }
    
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        for (CollisionStrategy strategy : collisionStrategies)
            if (strategy != null && !decramentedCounter)
                strategy.onCollision(this, other, numberOfBricks);
    }
    
    /*
    setter
     */
    public void setStrategies(CollisionStrategy[] collisionStrategies) {
        this.collisionStrategies = collisionStrategies;
    }
    
    /*
    used to find if a brick used counter.decrease() once already.
    Meaningful in case of 2 balls that occupy the same location & same direction, that hit a
     brick a once - we want to decrease the brick only once.
     */
    public void setDecramentedCounter() {
        this.decramentedCounter = true;
    }
}
