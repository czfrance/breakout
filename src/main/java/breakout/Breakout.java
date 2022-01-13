package breakout;

import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.input.KeyCode;
//import javafx.scene.paint.Color;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Paint;
//import javafx.scene.shape.Circle;
//import javafx.scene.shape.Rectangle;
//import javafx.scene.shape.Shape;
import java.util.ArrayList;

/*
    TODOS:
       1 **Figure out how to get bounce correctly
       2 Implement paddle count system -> randomness of bounce back depends on # bounces
           * need to differentiate block bounce from paddle?
       3 get all blocks, paddles, balls images
       4 read from picture files
       5 game end when touch ball bottom of screen
       6 block hit counter
       7 get blocks to move
       8 life & block destroyed counter
       9 slippery paddle
       10 specials flurries
       11 Block specials
            * snow angel, black ice
       12 all power ups + deep freeze
       13 planned cheat keys
       14 different game screens
       15 add additional cheat keys
       16 properly format & document everything

       by section:
       * debugging
       * pre-reqs (preparing all materials & images)
       * general game operations (when game end, creating pictures, counters)
       * game screens
       * blocks
       * paddles
       * power ups
       * cheat keys
       * format & document
 */

public class Breakout {

  public static final String RESOURCE_PATH = "/";
  public static final String BALL_IMAGE = RESOURCE_PATH + "snowball.png";
  public static final String PADDLE_IMAGE = RESOURCE_PATH + "paddle.png";
  public static final String WOOD_IMAGE = RESOURCE_PATH + "wood.png";
  public static final String BRICK_IMAGE = RESOURCE_PATH + "brick.png";
  public static final String STEEL_IMAGE = RESOURCE_PATH + "steel.png";
  public static final String CONCRETE_IMAGE = RESOURCE_PATH + "concrete.png";
  public static final String ICE_IMAGE = RESOURCE_PATH + "ice.png";
  public static final String ANGEL_IMAGE = RESOURCE_PATH + "gold.png";
  public static final String BLACK_IMAGE = RESOURCE_PATH + "carbon-fiber.png";
  public static final int BALL_SPEED = 80;
  public static final int PADDLE_SPEED = 8;
  public static final int BLOCK_SIZE = 20;

  private Ball ball;
  private Paddle paddle;
  private Block wood;
  private Block brick;
  private Block concrete;
  private Block steel;
  private Block ice;
  private Block angel;
  private Block black;
  private ArrayList<Block> blocks = new ArrayList<>();
  private int wWidth;
  private int wHeight;
  private Group root = new Group();

  public Scene setupGame(int width, int height, Paint background) {
    wWidth = width;
    wHeight = height;
    //Group root = new Group();
    Image ball_img = new Image(getClass().getResourceAsStream(BALL_IMAGE));
    Image paddle_img = new Image(getClass().getResourceAsStream(PADDLE_IMAGE));
    Image wood_img = new Image(getClass().getResourceAsStream(WOOD_IMAGE));
    Image brick_img = new Image(getClass().getResourceAsStream(BRICK_IMAGE));
    Image steel_img = new Image(getClass().getResourceAsStream(STEEL_IMAGE));
    Image concrete_img = new Image(getClass().getResourceAsStream(CONCRETE_IMAGE));
    Image ice_img = new Image(getClass().getResourceAsStream(ICE_IMAGE));
    Image angel_img = new Image(getClass().getResourceAsStream(ANGEL_IMAGE));
    Image black_img = new Image(getClass().getResourceAsStream(BLACK_IMAGE));

    ball = new Ball(10, BALL_SPEED, 30, ball_img, 200, 200);
    paddle = new Paddle(175, 360, 50, 15, paddle_img);
    wood = new Block(0, 0, BLOCK_SIZE, BLOCK_SIZE, wood_img);
    brick = new Block(100, 0, BLOCK_SIZE, BLOCK_SIZE, brick_img);
    steel = new Block(200, 0, BLOCK_SIZE, BLOCK_SIZE, steel_img);
    concrete = new Block(300, 0, BLOCK_SIZE, BLOCK_SIZE, concrete_img);
    ice = new Block(0, 100, BLOCK_SIZE, BLOCK_SIZE, ice_img);
    angel = new Block(100, 100, BLOCK_SIZE, BLOCK_SIZE, angel_img);
    black = new Block(200, 100, BLOCK_SIZE, BLOCK_SIZE, black_img);
    blocks.add(wood);
    blocks.add(brick);
    blocks.add(steel);
    blocks.add(concrete);
    blocks.add(ice);
    blocks.add(angel);
    blocks.add(black);

    root.getChildren().add(ball);
    root.getChildren().add(paddle);
    root.getChildren().add(wood);
    root.getChildren().add(brick);
    root.getChildren().add(steel);
    root.getChildren().add(concrete);
    root.getChildren().add(ice);
    root.getChildren().add(angel);
    root.getChildren().add(black);

    Scene scene = new Scene(root, width, height, background);
    scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
    return scene;
  }

  public void step(double elapsedTime) {
    ArrayList<Boolean> intersect = isIntersecting(blocks, paddle, ball);
    ball.move(wWidth, wHeight, elapsedTime, intersect.get(0), intersect.get(1));
  }

  //ERROR WHEN HIT AND BALL IS NOT COMPLETELY WITHIN BLOCK (NEAR CORNERS)
  private ArrayList<Boolean> isIntersecting(ArrayList<Block> blks, Paddle p, Ball b) {
    for (Block blk : blks) {
      ArrayList<Boolean> ret = intersect(blk, b);
      if (ret.get(0) || ret.get(1)) {
        root.getChildren().remove(blk);
        blocks.remove(blk);
        return ret;
      }
    }
    return intersect(p, b);
  }

  private ArrayList<Boolean> intersect(Node a, Node b) {
    ArrayList<Boolean> ret = new ArrayList<>();
    ret.add(false);
    ret.add(false);
    Bounds aBounds = a.getBoundsInParent();
    Bounds bBounds = b.getBoundsInParent();
    if (aBounds.intersects(bBounds)) {
      if (contains(aBounds.getMinX(), aBounds.getMaxX(), bBounds.getMinX(), bBounds.getMaxX())) {
        ret.set(0, true);
        return ret;
      }
      ret.set(1, true);
      return ret;
    }
    return ret;
  }

  private boolean contains(double a1, double a2, double b1, double b2) {
    return ((a1 < b1 && a2 > b2) || (b1 < a1 && b2 > a2));
  }

  private void handleKeyInput(KeyCode code) {
    switch (code) {
      case RIGHT -> paddle.setX(newPaddleX(true));
      case LEFT -> paddle.setX(newPaddleX(false));
      case UP, DOWN -> paddle.setX(paddle.getX());
    }
  }

  private double newPaddleX(boolean right) {
    if (right && !paddle.atBorder(wWidth, true)) {
      return paddle.getX() + PADDLE_SPEED;
    } else if ((!right) && !paddle.atBorder(wWidth, false)) {
      return paddle.getX() - PADDLE_SPEED;
    } else {
      return paddle.getX();
    }
  }

}
