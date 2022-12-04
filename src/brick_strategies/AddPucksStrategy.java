package brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import gameobjects.Puck;

/**
 * strategy the place 3 pucks(non-main-balls) in the game.
 * they function as the ball but do not count for missing HP is they went out of the board.
 */
public class AddPucksStrategy implements CollisionStrategy {
    
    private static final int NUMBER_OF_BALLS = 3;
    private final GameObjectCollection gameObjects;
    private final Sound puckSound;
    private final GameObject caller;
    private final Renderable img;
    private final Vector2 windim;
    
    /*
    constructor
     */
    public AddPucksStrategy(GameObjectCollection gameObjects, Sound puckSound, GameObject caller,
                            Renderable img, Vector2 windim) {
        this.gameObjects = gameObjects;
        this.puckSound = puckSound;
        this.caller = caller;
        this.img = img;
        this.windim = windim;
    }
    
    @Override
    public void onCollision(GameObject collidedObj, GameObject colliderObj, Counter bricksCounter) {
        for (int i = 0; i < NUMBER_OF_BALLS; i++) {
            GameObject puckBall = new Puck(caller.getCenter(),
                    new Vector2(caller.getDimensions().multX(1 / 6f).x(),
                            caller.getDimensions().multX(1 / 6f).x()),
                    img, puckSound, gameObjects, windim);
            gameObjects.addGameObject(puckBall);
            
        }
    }
}
