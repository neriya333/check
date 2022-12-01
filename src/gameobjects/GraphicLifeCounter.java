package gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

public class GraphicLifeCounter extends GameObject {

    private Vector2 widgetTopLeftCorner;
    private Vector2 widgetDimensions;
    private Counter livesCounter;
    private Renderable widgetRenderable;
    private GameObjectCollection gameObjectsCollection;
    private GameObject[] heartCollection;
    private int numOfLives;

    /**
     *
     * @param widgetTopLeftCorner
     * @param widgetDimensions
     * @param livesCounter
     * @param widgetRenderable
     * @param gameObjectsCollection
     * @param numOfLives
     */

    public GraphicLifeCounter(Vector2 widgetTopLeftCorner, Vector2 widgetDimensions,
                              Counter livesCounter, Renderable widgetRenderable,
                              GameObjectCollection gameObjectsCollection, int numOfLives) {
        super(widgetTopLeftCorner, widgetDimensions.multX(10).multY(2), null);
        this.widgetTopLeftCorner = widgetTopLeftCorner;
        this.widgetDimensions = widgetDimensions;
        this.livesCounter = livesCounter;
        this.widgetRenderable = widgetRenderable;
        this.gameObjectsCollection = gameObjectsCollection;
        this.numOfLives = numOfLives;

        createGameObjectHearts();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (livesCounter.value() < numOfLives){
            updateLife();
        }
    }

    private void createGameObjectHearts(){
        heartCollection = new GameObject[numOfLives];
        Vector2 xSize = new Vector2(widgetDimensions.x()+1,0);
        for (int heartNumber = 0; heartNumber < numOfLives; heartNumber++) {
            Vector2 location = widgetDimensions.add(xSize.multX(heartNumber));
            GameObject heart = new GameObject(widgetTopLeftCorner.add(location), widgetDimensions, widgetRenderable);

            gameObjectsCollection.addGameObject(heart, Layer.BACKGROUND+1);
            heartCollection[heartNumber] = heart;
        }
    }

    private void updateLife() {
        gameObjectsCollection.removeGameObject(heartCollection[livesCounter.value()],Layer.BACKGROUND+1);
    }


}
