package breakout;

import java.util.List;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Paint;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Random;

/*
    TODOS:
       1 **Figure out how to get bounce correctly
       2 Implement paddle count system -> randomness of bounce back depends on # bounces
           * need to differentiate block bounce from paddle?
      . 3 get all blocks, paddles, balls images
      . 4 read from picture files
       5 game end when touch ball bottom of screen
      . 6 block hit counter
      . 7 get blocks to move
      . 8 life & block destroyed counter
       9 slippery paddle
      x 10 specials flurries
       11 Block specials
            * snow angel, black ice
       12 all power ups + deep freeze
       13 planned cheat keys
       14 different game screens
       15 add additional cheat keys
       16 properly format & document everything

       by section:
       * debugging
      . * pre-reqs (preparing all materials & images)
      . * general game operations (when game end, creating pictures, counters)
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
  public static final String PADDLE_SLIP_IMAGE = RESOURCE_PATH + "paddle-slippery.png";
  public static final String WOOD_IMAGE = RESOURCE_PATH + "wood.png";
  public static final String BRICK_IMAGE = RESOURCE_PATH + "brick.png";
  public static final String STEEL_IMAGE = RESOURCE_PATH + "steel.png";
  public static final String CONCRETE_IMAGE = RESOURCE_PATH + "concrete.png";
  public static final String ICE_IMAGE = RESOURCE_PATH + "ice.png";
  public static final String ANGEL_IMAGE = RESOURCE_PATH + "gold.png";
  public static final String BLACK_IMAGE = RESOURCE_PATH + "carbon-fiber.png";
  public static final String WOOD_ICED_IMAGE = RESOURCE_PATH + "wood-iced.png";
  public static final String BRICK_ICED_IMAGE = RESOURCE_PATH + "brick-iced.png";
  public static final String STEEL_ICED_IMAGE = RESOURCE_PATH + "steel-iced.png";
  public static final String CONCRETE_ICED_IMAGE = RESOURCE_PATH + "concrete-iced.png";
  public static final String ICE_ICED_IMAGE = RESOURCE_PATH + "ice-iced.png";
  public static final String ANGEL_ICED_IMAGE = RESOURCE_PATH + "gold-iced.png";
  public static final String BLACK_ICED_IMAGE = RESOURCE_PATH + "carbon-fiber-iced.png";
  public static final String MAP_FILE = "src/main/resources/test.txt";
  public static final int BALL_SPEED = 300;
  public static final int BALL_SIZE = 10;
  public static final int PADDLE_SPEED = 8;
  public static final int PADDLE_HEIGHT = 10;
  public static final int PADDLE_WIDTH = 75;
  public static final int BASE_BLOCK_SPEED = 0;
  public static final int BLOCK_SPEED_INC = 5;
  public static final int NUM_LIVES = 5;

  private int blockWidth;
  private int blockHeight;
  private int level;
  private int livesLeft = NUM_LIVES;
  private int blocksBroken = 0;

  private Ball ball;
  private Paddle paddle;
  private List<List<Block>> blocks;
  private int wWidth;
  private int wHeight;
  private Group root = new Group();

  public Scene setupGame(int width, int height, Paint background) {
    wWidth = width-50;
    wHeight = height;

    Image ball_img = new Image(getClass().getResourceAsStream(BALL_IMAGE));
    Image[] paddle_imgs = {new Image(getClass().getResourceAsStream(PADDLE_IMAGE)),
        new Image(getClass().getResourceAsStream(PADDLE_SLIP_IMAGE))};

    ball = new Ball(BALL_SIZE, BALL_SPEED, 30, ball_img,
        wWidth/2-BALL_SIZE/2, wHeight-(PADDLE_HEIGHT+BALL_SIZE+1));
    paddle = new Paddle(wWidth/2-PADDLE_WIDTH/2, wHeight-PADDLE_HEIGHT,
        PADDLE_WIDTH, PADDLE_HEIGHT, paddle_imgs);
    try {
      blocks = buildMap(MAP_FILE);
    } catch (IOException e) {
      System.out.println("IOException, unable to build map");
    }

    root.getChildren().add(ball);
    root.getChildren().add(paddle);
    for (List<Block> blkRow : blocks) {
      for (Block blk : blkRow) {
        if (blk != null) {
          root.getChildren().add(blk);
        }
      }
    }

    Scene scene = new Scene(root, width, height, background);
    scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
    return scene;
  }

  public void step(double elapsedTime) {
    List<Boolean> intersect = isIntersecting(blocks, paddle, ball);
    ball.move(wWidth, wHeight, elapsedTime, intersect.get(0), intersect.get(1));
    for (List<Block> blkRow : blocks) {
      for (Block blk : blkRow) {
        if (blk != null) {
          blk.move(wWidth, wWidth, elapsedTime);
          //System.out.printf("(%d, %d)\n", blk.getX(), blk.getY());
        }
      }
    }
    if (ball.lostLife(wHeight)) {
      livesLeft--;
      System.out.printf("lost a life, %d lives left\n", livesLeft);
    }

    checkLost();
  }

  //ERROR WHEN HIT AND BALL IS NOT COMPLETELY WITHIN BLOCK (NEAR CORNERS)
  private List<Boolean> isIntersecting(List<List<Block>> blks, Paddle p, Ball b) {
    for (int i = 0; i < blks.size(); i++) {
      for (int j = 0; j < blks.get(0).size(); j++) {
        Block curr = blks.get(i).get(j);
        List<Boolean> ret = intersect(curr, b);
        if (ret.get(0) || ret.get(1)) {
          curr.hit();
          if (curr.broken()) {
            root.getChildren().remove(curr);
            blocks.get(i).set(j, null);
            blocksBroken++;
            System.out.println(blocksBroken);
          }
          return ret;
        }
      }
    }
    return intersect(p, b);
  }

  private List<Boolean> intersect(Node a, Node b) {
    List<Boolean> ret = new ArrayList<>();
    ret.add(false);
    ret.add(false);
    if (a == null || b == null) {
      return ret;
    }
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
      case RIGHT -> paddle.setX(paddle.newPaddleX(true, wWidth, PADDLE_SPEED));
      case LEFT -> paddle.setX(paddle.newPaddleX(false, wWidth, PADDLE_SPEED));
      case UP, DOWN -> paddle.setX(paddle.getX());
    }
  }

  private List<List<Block>> buildMap(String mapFile) throws IOException {
    List<List<Block>> blocks = new ArrayList<List<Block>>();

    Image[] wood_imgs = {new Image(getClass().getResourceAsStream(WOOD_IMAGE)),
        new Image(getClass().getResourceAsStream(WOOD_ICED_IMAGE))};
    Image[] brick_imgs = {new Image(getClass().getResourceAsStream(BRICK_IMAGE)),
        new Image(getClass().getResourceAsStream(BRICK_ICED_IMAGE))};
    Image[] steel_imgs = {new Image(getClass().getResourceAsStream(STEEL_IMAGE)),
        new Image(getClass().getResourceAsStream(STEEL_ICED_IMAGE))};
    Image[] concrete_imgs = {new Image(getClass().getResourceAsStream(CONCRETE_IMAGE)),
        new Image(getClass().getResourceAsStream(CONCRETE_ICED_IMAGE))};
    Image[] ice_imgs = {new Image(getClass().getResourceAsStream(ICE_IMAGE)),
        new Image(getClass().getResourceAsStream(ICE_ICED_IMAGE))};
    Image[] angel_imgs = {new Image(getClass().getResourceAsStream(ANGEL_IMAGE)),
        new Image(getClass().getResourceAsStream(ANGEL_ICED_IMAGE))};
    Image[] black_imgs = {new Image(getClass().getResourceAsStream(BLACK_IMAGE)),
        new Image(getClass().getResourceAsStream(BLACK_ICED_IMAGE))};

    FileReader map = null;
    try{
      map = new FileReader(mapFile);
    }
    catch (FileNotFoundException e) {
      System.out.println("Incorrect File");
    }

    BufferedReader inStream = new BufferedReader(map);
    String[] data = inStream.readLine().split(" ");
    int numCols = Integer.parseInt(data[0]);
    int numRows = Integer.parseInt(data[1]);
    blockWidth = wWidth / numCols;
    blockHeight = wWidth / numRows;

    level = 2;
    int blockSpeed = BASE_BLOCK_SPEED+(level*BLOCK_SPEED_INC);
    int colIndex = 0;
    int rowIndex = 0;
    int c = inStream.read();
    blocks.add(new ArrayList<Block>());
    Random rand = new Random();
    double angle;
    while (c != -1) {
      angle = rand.nextInt(361);
      switch(c) {
        case 10 -> {
          rowIndex++;
          colIndex = 0;
          blocks.add(new ArrayList<Block>());
        }
        case 32 -> colIndex++;
        case 49 -> blocks.get(rowIndex).add(colIndex, new Block(colIndex*blockWidth,
            rowIndex*blockHeight, blockWidth, blockHeight, ice_imgs,
            1, blockSpeed, angle));
        case 50 -> blocks.get(rowIndex).add(colIndex, new Block(colIndex*blockWidth,
            rowIndex*blockHeight, blockWidth, blockHeight, wood_imgs,
            2, blockSpeed, angle));
        case 51 -> blocks.get(rowIndex).add(colIndex, new Block(colIndex*blockWidth,
            rowIndex*blockHeight, blockWidth, blockHeight, brick_imgs,
            3, blockSpeed, angle));
        case 52 -> blocks.get(rowIndex).add(colIndex, new Block(colIndex*blockWidth,
            rowIndex*blockHeight, blockWidth, blockHeight, concrete_imgs,
            4, blockSpeed, angle));
        case 53 -> blocks.get(rowIndex).add(colIndex, new Block(colIndex*blockWidth,
            rowIndex*blockHeight, blockWidth, blockHeight, steel_imgs,
            5, blockSpeed, angle));
        case 65 -> blocks.get(rowIndex).add(colIndex, new Block(colIndex*blockWidth,
            rowIndex*blockHeight, blockWidth, blockHeight, angel_imgs,
            3, blockSpeed, angle));
        case 66 -> blocks.get(rowIndex).add(colIndex, new Block(colIndex*blockWidth,
            rowIndex*blockHeight, blockWidth, blockHeight, black_imgs,
            3, blockSpeed, angle));
        default -> blocks.get(rowIndex).add(colIndex, null);
      }
      c = inStream.read();
    }
    return blocks;
  }

  private void checkLost() {
    if (livesLeft == 0) {
      System.out.println("LOST");
      System.exit(0);
    }
  }

}
