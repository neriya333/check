package brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import gameobjects.TimedPaddle;

/**
 * Adds a single puddle at a time to the game, it lasts [MAX_HIT_POINTS] and function as Paddle
 * but can't catch extra HP
 */
public class AdditionalPaddleStrategy implements CollisionStrategy {
    
    private static final int MAX_HIT_POINTS = 4;
    private static final int PADDLE_HEIGHT = 20;
    private static final int PADDLE_WIDTH = 100;
    private final Renderable img;
    private final UserInputListener inputListener;
    private final Counter paddle_timer;
    private final GameObjectCollection gameObjects;
    private final Vector2 windim;
    
    /*
    constructor
     */
    public AdditionalPaddleStrategy(Counter additionalPaddleCounter,
                                    GameObjectCollection gameObjects, Vector2 windim, Renderable img
            , UserInputListener inputListener) {
        this.paddle_timer = additionalPaddleCounter;
        this.gameObjects = gameObjects;
        this.windim = windim;
        this.img = img;
        this.inputListener = inputListener;
    }
    
    @Override
    public void onCollision(GameObject collidedObj, GameObject colliderObj, Counter bricksCounter) {
        if (0 != paddle_timer.value())
            paddle_timer.increaseBy(MAX_HIT_POINTS - paddle_timer.value());
        else {
            paddle_timer.increaseBy(MAX_HIT_POINTS);
            GameObject additionalPaddle = new TimedPaddle(Vector2.ZERO,
                    new Vector2(PADDLE_WIDTH, PADDLE_HEIGHT)
                    , img, inputListener, windim, paddle_timer, gameObjects);
            additionalPaddle.setCenter(windim.mult(0.5f));
            gameObjects.addGameObject(additionalPaddle);
        }
    }
}
