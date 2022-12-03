package brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;

public interface CollisionStrategy {
        void onCollision(GameObject collidedObj, GameObject colliderObj, Counter bricksCounter);
}
