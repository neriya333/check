package brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import gameobjects.BuffHP;

public class HPchanceStrategy implements CollisionStrategy{
    private GameObjectCollection collection;
    private Renderable heartImg;
    private Vector2 winDim;
    private Counter hpCounter;

    HPchanceStrategy(GameObjectCollection collection, Renderable heartImg, Vector2 winDim, Counter hpCounter){
        this.collection = collection;
        this.heartImg = heartImg;
        this.winDim = winDim;
        this.hpCounter = hpCounter;
    }
    @Override
    public void onCollision(GameObject collidedObj, GameObject colliderObj, Counter bricksCounter) {
        collection.addGameObject(new BuffHP(collidedObj.getTopLeftCorner(),new Vector2(20,20),
                heartImg, collection,hpCounter,winDim));
    }
}
