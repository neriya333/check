package brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import gameobjects.BuffHP;

/**
 * add an extra life object that falls down as constant speed
 */
public class HPchanceStrategy implements CollisionStrategy {
    private final GameObjectCollection collection;
    private final Renderable heartImg;
    private final Vector2 winDim;
    private final Counter hpCounter;
    private final int SIZE = 20;
    
    /*
    constructor
     */
    HPchanceStrategy(GameObjectCollection collection, Renderable heartImg,
                     Vector2 winDim, Counter hpCounter) {
        this.collection = collection;
        this.heartImg = heartImg;
        this.winDim = winDim;
        this.hpCounter = hpCounter;
    }
    
    @Override
    public void onCollision(GameObject collidedObj, GameObject colliderObj, Counter bricksCounter) {
        collection.addGameObject(new BuffHP(collidedObj.getTopLeftCorner(), new Vector2(SIZE, SIZE),
                heartImg, collection, hpCounter, winDim));
    }
}
