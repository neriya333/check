package gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.util.Random;

/**
 *
 */
public class Ball extends GameObject {
    private static final float BALL_SPEED = 200;
    private final Sound collisionSound;
    
    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     */
    public Ball(Vector2 topLeftCorner, Vector2 dimensions,
                Renderable renderable, Sound collisionSound) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionSound = collisionSound;
        Random rand = new Random();
        
        this.setVelocity(new Vector2(1, 1));
        float velocity_X = BALL_SPEED, velocity_Y = BALL_SPEED;
        if (rand.nextBoolean())
            velocity_X *= -1;
        if (rand.nextBoolean())
            velocity_Y *= -1;
        this.setVelocity(new Vector2(velocity_X, velocity_Y));
    }
    
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        collisionSound.play();
        setVelocity(getVelocity().flipped(collision.getNormal()));
    }
}
