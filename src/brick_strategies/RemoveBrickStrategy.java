package brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;
import gameobjects.Brick;

public class RemoveBrickStrategy implements CollisionStrategy{
    private final GameObjectCollection gameObjects;

    public RemoveBrickStrategy(GameObjectCollection gameObjects){
        this.gameObjects = gameObjects;
    }

    public void onCollision(GameObject collidedObj, GameObject colliderObj, Counter bricksCounter){
        gameObjects.removeGameObject(collidedObj);
        Brick b = (Brick) collidedObj;
        b.setDecramented_counter();
        bricksCounter.decrement();
    }
}
