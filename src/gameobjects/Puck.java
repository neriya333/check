package gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.util.Random;

public class Puck extends GameObject{
    private static final float BALL_SPEED = 10;
    private Sound collisionSound;
    private GameObjectCollection objectCollection;
    private Vector2 windim;
    private int order;

    /**
     * Construct a new GameObject instance.
     *  @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     */
    public Puck(Vector2 topLeftCorner, Vector2 dimensions,
                Renderable renderable, Sound collisionSound,
                GameObjectCollection objectCollection, Vector2 windim) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionSound = collisionSound;
        this.objectCollection = objectCollection;
        this.windim = windim;
        this.order = order;


        Random rand = new Random();

        this.setVelocity(new Vector2(1,1));
        float velocity_X = BALL_SPEED, velocity_Y  = BALL_SPEED;
        if (rand.nextBoolean())
            velocity_X*=-1;
        if (rand.nextBoolean())
            velocity_Y*=-1;
        this.setVelocity(new Vector2(velocity_X, velocity_Y));
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        collisionSound.play();
        setVelocity(getVelocity().flipped(collision.getNormal()));
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if(this.getTopLeftCorner().y() > windim.y()) objectCollection.removeGameObject(this);
    }
}
