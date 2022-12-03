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

    private static String puckImgPath = "assets/mockBall.png";
    private static String puckSoundPath = "assets/blop_cut_silenced.wav";
    private static String botGoodImgPath = "assets/botGood.png";
    private static String heartImgPath = "assets/heart.png";
    private final Counter paddleHP;

    private Renderable puckImg;
    private Renderable heartImg;
    private Counter gameHP;
    private Renderable additionalPaddleImg;
    private GameObjectCollection gameObjects;
    private Sound puckSound;
    private Vector2 windim;
    private UserInputListener inputListener;
    private int opt_number = 4;

    public StrategyFactory(GameObjectCollection gameObjects, SoundReader soundReader
            , ImageReader imageReader, Vector2 windim, UserInputListener inputListener
            , Counter GameHP){
        this.gameObjects = gameObjects;
        puckSound = soundReader.readSound(puckSoundPath);
        puckImg = imageReader.readImage(puckImgPath,true);
        additionalPaddleImg = imageReader.readImage(botGoodImgPath,false);
        heartImg = imageReader.readImage(heartImgPath,true);
        gameHP = GameHP;
        this.paddleHP = new Counter(0);
        this.windim = windim;
        this.inputListener = inputListener;
    }

    /**
     *
     * basicly build an strategy given a number
     * @param caller the gameObject that called this item
     * @return
     */
    public CollisionStrategy buildStrategy(
            GameObject caller){
        Random rand = new Random();
        int strategy = rand.nextInt(opt_number);
        switch (strategy) {
            case 0:
                return new AddPucksStrategy(gameObjects, puckSound, caller, puckImg, windim);
            case 1:
                return null;
            case 2:
                return new AdditionalPaddleStrategy(paddleHP,gameObjects,windim,additionalPaddleImg,inputListener);
            case 3:
                return new HPchanceStrategy(gameObjects,heartImg,windim,gameHP);
        }
        return null;
    }
}
