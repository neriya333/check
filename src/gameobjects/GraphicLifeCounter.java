package gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * Handler of smaller and similr items that each represent - visually - 1 hp.
 */
public class GraphicLifeCounter extends GameObject {
    
    private final Vector2 widgetTopLeftCorner;
    private final Vector2 widgetDimensions;
    private final Counter livesCounter;
    private final Renderable widgetRenderable;
    private final GameObjectCollection gameObjectsCollection;
    private final int NUM_EXTRA_LIFE = 1; // how many lives can be gained more than the initial
    // some at once
    private GameObject[] heartCollection;
    private int numOfLives;
    
    /**
     * @param widgetTopLeftCorner   top left corner of the GraphicLifeCounter.
     * @param widgetDimensions      the dims of the GraphicLifeCounter.
     * @param livesCounter          Count how many Lives the player has.
     * @param widgetRenderable      A renderable image of a heart. represent single life.
     * @param gameObjectsCollection the collection
     */
    public GraphicLifeCounter(Vector2 widgetTopLeftCorner, Vector2 widgetDimensions,
                              Counter livesCounter, Renderable widgetRenderable,
                              GameObjectCollection gameObjectsCollection) {
        super(widgetTopLeftCorner, widgetDimensions.multX(10).multY(2), null);
        this.widgetTopLeftCorner = widgetTopLeftCorner;
        this.widgetDimensions = widgetDimensions;
        this.livesCounter = livesCounter;
        this.widgetRenderable = widgetRenderable;
        this.gameObjectsCollection = gameObjectsCollection;
        this.numOfLives = livesCounter.value();
        
        createGameObjectHearts();
    }
    
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (livesCounter.value() < numOfLives) {
            gameObjectsCollection.removeGameObject(heartCollection[livesCounter.value()],
                    Layer.BACKGROUND + 1);
            numOfLives--;
        }
        if (livesCounter.value() > numOfLives) {
            gameObjectsCollection.addGameObject(heartCollection[numOfLives],
                    Layer.BACKGROUND + 1);
            numOfLives++;
        }
    }
    
    /**
     * creates and fill an array named [heartCollection] with GameObjects images that each represents a life.
     * Add [numOfLives] of them to the gameObjectCollection, where [numOfLives+NUM_EXTRA_LIFE] is the size of the array.
     */
    private void createGameObjectHearts() {
        heartCollection = new GameObject[numOfLives + NUM_EXTRA_LIFE];
        Vector2 xSize = new Vector2(widgetDimensions.x() + 1, 0);
        for (int heartNumber = 0; heartNumber < numOfLives + NUM_EXTRA_LIFE; heartNumber++) {
            Vector2 location = widgetDimensions.add(xSize.multX(heartNumber));
            GameObject heart = new GameObject(widgetTopLeftCorner.add(location),
                    widgetDimensions, widgetRenderable);
            if (heartNumber < numOfLives) {
                gameObjectsCollection.addGameObject(heart, Layer.BACKGROUND + 1);
            }
            heartCollection[heartNumber] = heart;
        }
    }
}
