package gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;


/**
 * A GameObject that collides only with Paddle (not TimedPaddle), and collision with it adds 1 HP
 * if the HP is in legit range
 */
public class BuffHP extends GameObject {
    private static final int SPEED = 100;
    private static final int MAX_HP = 4;
    private final GameObjectCollection collection;
    private final Counter hp;
    private final Vector2 winDim;
    
    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     */
    public BuffHP(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                  GameObjectCollection collection, Counter hp, Vector2 winDim) {
        super(topLeftCorner, dimensions, renderable);
        this.collection = collection;
        this.hp = hp;
        this.winDim = winDim;
        this.setVelocity(Vector2.DOWN.mult(SPEED));
    }
    
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (hp.value() < MAX_HP) hp.increment();
        collection.removeGameObject(this);
    }
    
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (this.getCenter().y() > winDim.y())
            collection.removeGameObject(this);
    }
    
    @Override
    public boolean shouldCollideWith(GameObject other) {
        boolean res = super.shouldCollideWith(other);
        if ((other instanceof Paddle) && (!(other instanceof TimedPaddle)))
            return res;
        return false;
    }
}
