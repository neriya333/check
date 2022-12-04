package brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.Sound;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.util.Random;

public class StrategyFactory {

    private static final String puckImgPath = "assets/mockBall.png";
    private static final String puckSoundPath = "assets/blop_cut_silenced.wav";
    private static final String botGoodImgPath = "assets/botGood.png";
    private static final String heartImgPath = "assets/heart.png";
    private final Counter paddleHP;

    private final Renderable puckImg;
    private final Renderable heartImg;
    private final Counter gameHP;
    private final Renderable additionalPaddleImg;
    private final GameObjectCollection gameObjects;
    private final Sound puckSound;
    private final Vector2 windim;
    private final UserInputListener inputListener;
    // does not have to be here but its more orderly here.
    private final int howManyStrategiesExists = 4;

    /**
     * an initialization of the Factory so that it could build any of the strategies it should be
     * able to provide
     *
     * @param gameObjects   needed for some strategy
     * @param soundReader   needed for some strategy
     * @param imageReader   needed for some strategy
     * @param winDim        needed for some strategy
     * @param inputListener needed for some strategy
     * @param GameHP        needed for some strategy
     */
    public StrategyFactory(GameObjectCollection gameObjects, SoundReader soundReader
            , ImageReader imageReader, Vector2 winDim, UserInputListener inputListener
            , Counter GameHP) {
        this.gameObjects = gameObjects;
        puckSound = soundReader.readSound(puckSoundPath);
        puckImg = imageReader.readImage(puckImgPath, true);
        additionalPaddleImg = imageReader.readImage(botGoodImgPath, false);
        heartImg = imageReader.readImage(heartImgPath, true);
        gameHP = GameHP;
        this.paddleHP = new Counter(0);
        this.windim = winDim;
        this.inputListener = inputListener;
    }

    /**
     * return a randomly sampled strategy that implements CollisionStrategy
     *
     * @param caller the gameObject that called this item
     * @return a randomly sampled strategy that implements CollisionStrategy
     */
    public CollisionStrategy buildStrategy(
            GameObject caller) {
        Random rand = new Random();
        int strategy = rand.nextInt(howManyStrategiesExists);
        switch (strategy) {
            case 0:
                return new AddPucksStrategy(gameObjects, puckSound, caller, puckImg, windim);
            case 1:
                return null;
            case 2:
                return new AdditionalPaddleStrategy(paddleHP, gameObjects, windim, additionalPaddleImg, inputListener);
            case 3:
                return new HPchanceStrategy(gameObjects, heartImg, windim, gameHP);
        }
        return null;
    }
}
