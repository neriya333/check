package gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.awt.*;

public class NumericLifeCounter extends GameObject {
    private String baseString = "Life: ";
    private TextRenderable textRenderable;
    private Counter livesCounter;
    private GameObjectCollection gameObjectCollection;
    private Color color;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     */
    public NumericLifeCounter(Counter livesCounter, Vector2 topLeftCorner, Vector2 dimensions, GameObjectCollection gameObjectCollection) {
        super(topLeftCorner, dimensions, null);
        this.livesCounter = livesCounter;
        this.gameObjectCollection = gameObjectCollection;
        this.textRenderable = new TextRenderable(baseString+livesCounter.value());
        textRenderable.setColor(Color.GREEN);
        this.renderer().setRenderable(textRenderable);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if(livesCounter.value() == 2) textRenderable.setColor(Color.yellow);
        else if (livesCounter.value() == 1) textRenderable.setColor(Color.red);
        textRenderable.setString(baseString + livesCounter.value());
    }
}
