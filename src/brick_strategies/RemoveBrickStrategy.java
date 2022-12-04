package brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;
import gameobjects.Brick;

/**
 * A must-have strategy. Remove the brick from the game.
 */
public class RemoveBrickStrategy implements CollisionStrategy {
    private final GameObjectCollection gameObjects;
    
    /**
     * constrocor.
     *
     * @param gameObjects from which the brick will be removed
     */
    public RemoveBrickStrategy(GameObjectCollection gameObjects) {
        this.gameObjects = gameObjects;
    }
    
    /**
     * the strat body
     *
     * @param collidedObj   the brick
     * @param colliderObj   the ball
     * @param bricksCounter the Counter to update of the removal of this brick.
     */
    public void onCollision(GameObject collidedObj, GameObject colliderObj, Counter bricksCounter) {
        gameObjects.removeGameObject(collidedObj);
        Brick b = (Brick) collidedObj;
        b.setDecramentedCounter();
        bricksCounter.decrement();
    }
}
