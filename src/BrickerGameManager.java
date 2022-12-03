import brick_strategies.CollisionStrategy;
import brick_strategies.RemoveBrickStrategy;
import brick_strategies.StrategyFactory;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import gameobjects.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

public class BrickerGameManager extends GameManager {
    private static final int BORDER_WIDTH = 10;
    private static final int PADDLE_HEIGHT = 20;
    private static final int PADDLE_WIDTH = 100;
    private static final int BALL_RADIUS = 35;
    private static final int STD_SIZE = 20;

    private static final int BRICK_HEIGHT = 15;
    private static final int BRICKS_N_COL = 8;
    private static final int BRICKS_N_ROWS = 5;

    private static final int INITIAL_LIFE = 3;
    private static final float BALL_SPEED = 200;
    private static final float HALF = 0.5f;
    private static final Renderable BORDER_RENDERABLE =
            new RectangleRenderable(new Color(80, 140, 250));
    private static final String WIN_MSG = "You won! ";
    private static final String LOOSE_MSG = "You lost! ";
    private static final String GO_AT_IT_AGAIN = "play again?";
    private static final String BALL_IMG_PATH = "assets/ball.png";
    private static final String PADDLE_IMG_PATH = "assets/paddle.png";
    private static final String HEART_IMG_PATH = "assets/heart.png";
    private static final String BALL_SOUND_PATH = "assets/blop_cut_silenced.wav";
    private static final String BACKGOUND_IMG_PATH = "assets/DARK_BG2_small.jpeg";
    private static final String BRICK_IMG_PATH = "assets/brick.png";


    Vector2 winDim;
    Random rand;
    GameObject ball;
    private UserInputListener inputListener;
    private WindowController winController;
    private Counter lifeCounter;
    private Counter brickCounter;
    private ImageReader imageReader;
    private SoundReader soundReader;


    public BrickerGameManager(String windowTitle, Vector2 windowDim){
        super(windowTitle, windowDim);
        rand = new Random();
    }

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        winDim = windowController.getWindowDimensions();
        this.inputListener = inputListener;
        this.winController = windowController;
        this.lifeCounter = new Counter(INITIAL_LIFE);
        this.brickCounter = new Counter(0);
        this.soundReader = soundReader;
        this.imageReader = imageReader;
        windowController.setTargetFramerate(80);


        // create ball
        Renderable ballImage = imageReader.readImage(BALL_IMG_PATH, true);
        Sound collisionSound = soundReader.readSound(BALL_SOUND_PATH);
        this.ball = createBall(Vector2.DOWN.mult(BALL_SPEED), ballImage, collisionSound);
        this.gameObjects().addGameObject(ball);

        // create paddle
        Renderable paddleImage = imageReader.readImage(PADDLE_IMG_PATH, false);
        GameObject paddle = new Paddle(Vector2.ZERO,new Vector2(PADDLE_WIDTH ,PADDLE_HEIGHT), paddleImage, inputListener, winDim);
        paddle.setCenter(new Vector2(winDim.x()/2, winDim.y()-STD_SIZE));
        this.gameObjects().addGameObject(paddle);

        // create borders
        createBorders();

        // creates numeric/textual Life
        Vector2 numericLifeLoc = new Vector2(STD_SIZE, winDim.y()-30);
        GameObject numericLife = new NumericLifeCounter(lifeCounter,numericLifeLoc,
                new Vector2(STD_SIZE,STD_SIZE));
        this.gameObjects().addGameObject(numericLife, Layer.BACKGROUND+1);

        // create graphicLife
        Renderable heartImg = imageReader.readImage(HEART_IMG_PATH, true);
        Vector2 heartShape = new Vector2(STD_SIZE,STD_SIZE), heartsLocation = numericLifeLoc.add(new Vector2(-STD_SIZE,-50));
        GameObject graphicLifeCounter = new GraphicLifeCounter(heartsLocation,heartShape,
                lifeCounter, heartImg, gameObjects());
        gameObjects().addGameObject(graphicLifeCounter, Layer.BACKGROUND+1);

        // creates background
        createGameObjectBackgraound(imageReader, windowController, BACKGOUND_IMG_PATH);

        // creates bricks
        Renderable brickImg = imageReader.readImage(BRICK_IMG_PATH,false);
        RemoveBrickStrategy std_collision = new RemoveBrickStrategy(gameObjects());
        StrategyFactory strategyFactory = new StrategyFactory(gameObjects(),soundReader,imageReader,winDim,inputListener, lifeCounter);
        // create strategy factory

        // creates NUMBER_OF_BRICKS*N_ROWS brick objects, looking link brickImage, with height BRICK_HEIGHT
        createBricks(winDim, brickImg, BRICK_HEIGHT, BRICKS_N_COL, BRICKS_N_ROWS, strategyFactory);
    }


    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        checkForGameEnd();
    }

    /**
     * check if game ended in win or loose Or reduction of HP is needed
     * operate accordingly - remove HP or prompt won/loss massage
     */
    private void checkForGameEnd() {
        String msg = "";
        if(brickCounter.value() == -3 || inputListener.isKeyPressed(KeyEvent.VK_K)){ // -1 for doing it a frame after removing last brick
            msg += WIN_MSG;
        }
        if(ball.getCenter().y() > winDim.y()){
            if(!removeHP()){ // if no more lives:
                msg += LOOSE_MSG;
            }
        }
        if(!msg.equals("")){
            msg += GO_AT_IT_AGAIN;
            if(winController.openYesNoDialog(msg)){
                winController.resetGame();
            }
            else winController.closeWindow();
        }
        // so that the game will update the screen before shouting Winner
        if(brickCounter.value() <= 0)
            brickCounter.decrement();
    }

    /**
     * remove 1 HP from lifeCounter
     * @return true upon success, false for failure (0 hp)
     */
    private boolean removeHP() {
        lifeCounter.decrement();
        ball.setCenter(winDim.mult(HALF));
        return lifeCounter.value() != 0;
    }


    private void createBricks(Vector2 windowDimentios, Renderable brickImage, int BRICK_HEIGHT,
                              int NUMBER_OF_BRICKS, int N_ROWS, StrategyFactory strategyFactory) {
        // width - borders - distance between bricks divided by num of bricks:
        int brick_size = (int) (windowDimentios.x()-(NUMBER_OF_BRICKS -1) - 2 * BORDER_WIDTH)/ NUMBER_OF_BRICKS;
        Vector2 brickShape = new Vector2(brick_size, BRICK_HEIGHT);

        // for row
        for (int brick_row = 0; brick_row < N_ROWS; brick_row++) {
            int brick_x_placement = (int)BORDER_WIDTH,
                    brick_y_placement = (int)BORDER_WIDTH + (BRICK_HEIGHT +1) * brick_row;
            // for column
            for (int n_brick = 0; n_brick < NUMBER_OF_BRICKS; n_brick++) {

                Brick new_brick = new Brick(new Vector2(brick_x_placement, brick_y_placement),
                        brickShape, brickImage, brickCounter);

                int numOfCollisionStrats = rand.nextInt(39);
                if(numOfCollisionStrats%3 == numOfCollisionStrats%5) numOfCollisionStrats = 4; // 3 + removal
                else if (numOfCollisionStrats%3 == numOfCollisionStrats%2) numOfCollisionStrats = 3; // 2 + removal
                else numOfCollisionStrats = 2; // 1 + removal, where 1 might be empty->only removal.

                CollisionStrategy[] cs = new CollisionStrategy[numOfCollisionStrats];
                for (int i = 0; i < numOfCollisionStrats - 1; i++) {
                    cs[i] = strategyFactory.buildStrategy(new_brick);
                }
                cs[numOfCollisionStrats-1] = new RemoveBrickStrategy(gameObjects());

                new_brick.loadStrategies(cs);
                this.gameObjects().addGameObject(new_brick);
                brick_x_placement += brick_size + 1; // as I take into account the distance between blocks
            }
        }
    }


    /**
     *
     * @param velocity
     * @param ballImage
     * @param collisionSound
     * @return
     */
    private GameObject createBall(Vector2 velocity, Renderable ballImage,  Sound collisionSound) {
        GameObject ball = new Ball(Vector2.ZERO, new Vector2(BALL_RADIUS*HALF,BALL_RADIUS*HALF), ballImage, collisionSound);
        ball.setCenter(winDim.mult(HALF));
        return ball;
    }

    /**
     * creates the boarders of the game as objects.
     */
    private void createBorders() {
        gameObjects().addGameObject(
                new GameObject(
                        Vector2.ZERO,
                        new Vector2(BORDER_WIDTH, winDim.y()),
                        BORDER_RENDERABLE)
        );
        gameObjects().addGameObject(
                new GameObject(
                        new Vector2(winDim.x()-BORDER_WIDTH, 0),
                        new Vector2(BORDER_WIDTH, winDim.y()),
                        BORDER_RENDERABLE)
        );
        gameObjects().addGameObject(
                new GameObject(
                        new Vector2(Vector2.ZERO),
                        new Vector2(winDim.x(), BORDER_WIDTH),
                        BORDER_RENDERABLE)
        );
    }
    /*
    straight forwards - from a path ,open image and add it to the background as an image
     */
    private void createGameObjectBackgraound(ImageReader imageReader, WindowController windowController, String path) {
        GameObject background = new GameObject(Vector2.ZERO, windowController.getWindowDimensions(),
                imageReader.readImage(path,false));
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects().addGameObject(background, Layer.BACKGROUND);
    }

    public static void main(String[] args) {
        new BrickerGameManager("Bricker", new Vector2(700, 500)).run();
    }
}
