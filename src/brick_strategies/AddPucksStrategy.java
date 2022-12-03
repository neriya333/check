package brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import gameobjects.Puck;

public class AddPucksStrategy implements CollisionStrategy{

    private static final int NUMBER_OF_BALLS = 3;
    private GameObjectCollection gameObjects;
    private Sound puckSound;
    private GameObject caller;
    private Renderable img;
    private Vector2 windim;

    public AddPucksStrategy(GameObjectCollection gameObjects, Sound puckSound, GameObject caller, Renderable img, Vector2 windim){
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
                    new Vector2(caller.getDimensions().multX(1/6f).x(),caller.getDimensions().multX(1/6f).x()),
                    img, puckSound, gameObjects, windim);
//            if(i<NUMBER_OF_BALLS/2.f)
//                puckBall.setCenter(new Vector2(caller.getCenter().x() - i*caller.getDimensions().x()/3,caller.getCenter().y()));
//            if(i>NUMBER_OF_BALLS/2.f)
//                puckBall.setCenter(new Vector2(caller.getCenter().x() + i*caller.getDimensions().x()/3,caller.getCenter().y()));
            gameObjects.addGameObject(puckBall);

        }
    }
}
