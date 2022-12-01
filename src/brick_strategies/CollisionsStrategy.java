package brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;

public class CollisionsStrategy {
    private GameObjectCollection gameObjects;

    public CollisionsStrategy(GameObjectCollection gameObjects){
        this.gameObjects = gameObjects;
    }

    public void onCollision(GameObject collidedObj, GameObject colliderObj, Counter bricksCounter){
        gameObjects.removeGameObject(collidedObj);
        bricksCounter.decrement();
    }
}
