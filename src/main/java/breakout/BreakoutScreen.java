package breakout;

import java.util.Arrays;
import java.util.List;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Random;
import javafx.scene.text.Text;
//RULE: ONLY ONE POWERUP AT A TIME
//IF BALL HITS TOO CLOSE TO EDGE OF BLOCK, IT WILL ROCKET OFF AT DIFFERENT ANGLE
//BUG: ball sometimes goes through the block, idk why

public class BreakoutScreen extends Screen {

  public static final String RESOURCE_PATH = "/";
  public static final String BALL_IMAGE = RESOURCE_PATH + "snowball.png";
  public static final String PADDLE_IMAGE = RESOURCE_PATH + "paddle.png";
  public static final String PADDLE_SLIP_IMAGE = RESOURCE_PATH + "paddle-slippery.png";
  public static final String WOOD_IMAGE = RESOURCE_PATH + "wood.png";
  public static final String BRICK_IMAGE = RESOURCE_PATH + "brick.png";
  public static final String STEEL_IMAGE = RESOURCE_PATH + "steel.png";
  public static final String CONCRETE_IMAGE = RESOURCE_PATH + "concrete.png";
  public static final String ICE_IMAGE = RESOURCE_PATH + "ice.png";
  public static final String SNOW_ANGEL_IMAGE = RESOURCE_PATH + "gold.png";
  public static final String BLACK_IMAGE = RESOURCE_PATH + "carbon-fiber.png";
  public static final String WOOD_ICED_IMAGE = RESOURCE_PATH + "wood-iced.png";
  public static final String BRICK_ICED_IMAGE = RESOURCE_PATH + "brick-iced.png";
  public static final String STEEL_ICED_IMAGE = RESOURCE_PATH + "steel-iced.png";
  public static final String CONCRETE_ICED_IMAGE = RESOURCE_PATH + "concrete-iced.png";
  public static final String ICE_ICED_IMAGE = RESOURCE_PATH + "ice-iced.png";
  public static final String SNOW_ANGEL_ICED_IMAGE = RESOURCE_PATH + "gold-iced.png";
  public static final String BLACK_ICED_IMAGE = RESOURCE_PATH + "carbon-fiber-iced.png";
  public static final String NOV_MAP_FILE = "src/main/resources/november.txt";
  public static final String DEC_MAP_FILE = "src/main/resources/december.txt";
  public static final String JAN_MAP_FILE = "src/main/resources/january.txt";
  public static final int BALL_SPEED = 100;
  public static final int BALL_SIZE = 10;
  public static final int INIT_BALL_ANGLE = 75;
  public static final int PADDLE_SPEED = 8;
  public static final int PADDLE_HEIGHT = 10;
  public static final int PADDLE_WIDTH = 75;
  public static final int BASE_BLOCK_SPEED = 0;
  public static final int BLOCK_SPEED_INC = 5;
  public static final int NUM_LIVES = 5;
  public static final int POWERUP_TIME_LIMIT = 15;
  public static final int DISADV_TIME_LIMIT = 15;
  public static final int TEXT_MARGIN_SIZE = 25;
  public static final int BASIC_FONT_SIZE = 18;
  public static final int TITLE_FONT_SIZE = 50;
  public static final List<String> POWERUPS =
      Arrays.asList("winter freeze", "avalanche", "spread the holiday cheer");
  public static final List<String> DISADVGS = Arrays.asList("slippery paddle");

  private int blockWidth;
  private int blockHeight;
  private int level;
  private int livesLeft = NUM_LIVES;
  private int blocksBroken = 0;
  private int blocksHit = 0;
  private int powerUpTimer = 0;
  private int disAdvTimer = 0;
  private String powerUpActive;
  private double powerUpActiveTime = 0;
  private String disAdvActive;
  private double disAdvActiveTime = 0;

  private Ball ball;
  private Paddle paddle;
  private List<List<Block>> blocks;
  private int wWidth;
  private int wHeight;
  private boolean inPlay = false;
  private boolean won = false;

  private Text lives = new Text();
  private Text hit = new Text();
  private Text destroyed = new Text();
  private Text currPower = new Text();
  private Text currDisAdv = new Text();
  private Text winMsg = new Text();
  private Text nextLvlMsg = new Text();
  private Text loseMsg = new Text();
  private Text lvlText = new Text();

  private double[] hitsTextLoc;
  private double[] livesTextLoc;
  private double[] destroyedTextLoc;
  private double[] powerupTextLoc;
  private double[] disadvTextLoc;
  private double[] lvlTextLoc;

  public Scene setupGame(int width, int height, Paint background, int lvl) {
    level = lvl;

    lvlTextLoc = new double[]{5, TEXT_MARGIN_SIZE - 5};
    livesTextLoc = new double[]{width / 5, TEXT_MARGIN_SIZE - 5};
    hitsTextLoc = new double[]{width / 3 + 30, TEXT_MARGIN_SIZE - 5};
    destroyedTextLoc = new double[]{2 * (width / 3), TEXT_MARGIN_SIZE - 5};
    powerupTextLoc = new double[]{5, height - 5};
    disadvTextLoc = new double[]{width / 2, height - 5};

    wWidth = width - 2;
    wHeight = height - (TEXT_MARGIN_SIZE);

    powerUpActive = null;
    disAdvActive = null;

    Image ball_img = new Image(getClass().getResourceAsStream(BALL_IMAGE));
    Image[] paddle_imgs = {new Image(getClass().getResourceAsStream(PADDLE_IMAGE)),
        new Image(getClass().getResourceAsStream(PADDLE_SLIP_IMAGE))};

    ball = new Ball(BALL_SIZE, BALL_SPEED, INIT_BALL_ANGLE, ball_img,
        wWidth / 2 - BALL_SIZE / 2, wHeight - (PADDLE_HEIGHT + BALL_SIZE + 1));
    paddle = new Paddle(wWidth / 2 - PADDLE_WIDTH / 2, wHeight - PADDLE_HEIGHT,
        PADDLE_WIDTH, PADDLE_HEIGHT, paddle_imgs);

    String map = getMap(level);
    try {
      blocks = buildMap(map);
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

    drawText(root);

    Scene scene = new Scene(root, width, height, background);
    scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
    return scene;
  }

  private String getMap(int lvl) {
    switch (lvl) {
      case 1 -> {
        return NOV_MAP_FILE;
      }
      case 2 -> {
        return DEC_MAP_FILE;
      }
      default -> {
        return JAN_MAP_FILE;
      }
    }
  }

  public void moveBlocks(double elapsedTime) {
    for (List<Block> blkRow : blocks) {
      for (Block blk : blkRow) {
        if (blk != null) {
          blk.move(wWidth, wWidth, TEXT_MARGIN_SIZE, elapsedTime);
        }
      }
    }
  }

  public boolean step(double elapsedTime) {
    if (inPlay) {
      checkEffects(elapsedTime);
      List<Boolean> intersect = isIntersecting(blocks, paddle, ball);
      ball.move(wWidth, wHeight, TEXT_MARGIN_SIZE, intersect.get(0), intersect.get(1), elapsedTime);
      moveBlocks(elapsedTime);

      if (ball.lostLife(wHeight)) {
        livesLeft--;
        resetPaddleBall();
        inPlay = false;
      }
      checkWon();
      updateText(root);
    }
    return gameIsRunning();
  }

  private void resetPaddleBall() {
    ball.setSpecifics(wWidth / 2 - BALL_SIZE / 2, wHeight - (PADDLE_HEIGHT + BALL_SIZE + 1),
        BALL_SIZE, BALL_SPEED);
    paddle.setSpecifics(wWidth / 2 - PADDLE_WIDTH / 2, wHeight - PADDLE_HEIGHT, PADDLE_WIDTH);
  }

  private void checkEffects(double elapsedTime) {
    if (powerUpActive != null) {
      if (powerUpActiveTime <= POWERUP_TIME_LIMIT) {
        powerUpActiveTime += elapsedTime;
      } else {
        stopPowerUp();
      }
    }
    if (disAdvActive != null) {
      if (disAdvActiveTime <= DISADV_TIME_LIMIT) {
        disAdvActiveTime += elapsedTime;
      } else {
        stopDisAdv();
      }
    }
  }

  private List<Boolean> isIntersecting(List<List<Block>> blks, Paddle p, Ball b) {
    for (int i = 0; i < blks.size(); i++) {
      for (int j = 0; j < blks.get(0).size(); j++) {
        Block curr = blks.get(i).get(j);
        List<Boolean> ret = intersect(b, curr);
        if (ret.get(0) || ret.get(1)) {
          blockIsHit(curr);
          if (curr.broken()) {
            destroyBlock(i, j);
          }
          return ret;
        }
      }
    }

    List<Boolean> paddleIntersect = intersect(b, p);
    if (paddleIntersect.contains(true)) {
      p.hit();
      b.deviatePath(p.getPercentDeviation());
      if (p.isSlippery()) {
        b.slip();
      }
    }
    return paddleIntersect;
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
      if (intersectHoriz(aBounds, bBounds)) {
        ret.set(0, true);
      }
      if (intersectVert(aBounds, bBounds)) {
        ret.set(1, true);
      }
    }
    return ret;
  }

  private boolean intersectHoriz(Bounds a, Bounds b) {
    double centerX = a.getCenterX();
    double centerY = a.getCenterY();
    double extraX = Math.min(Math.abs(centerX - b.getMaxX()), Math.abs(centerX - b.getMinX()));
    double minX = b.getMinX() - extraX;
    double maxX = b.getMaxX() + extraX;
    if (!contains(b.getMinY(), b.getMaxY(), centerY)) {
      if (contains(b.getMinX(), b.getMaxX(), centerX)) {
        return true;
      } else if (contains(minX, maxX, centerX)) {
        return true;
      }
      return false;
    }
    return false;
    //if y coord of ball is not in bounds of block y
    //and if center x of ball is within (block min x - abs(ballcenter-blockmaxx)) or
    //(block )block maxx + abs(ballcenter-blockmaxx)) then horz intersect
  }

  private boolean intersectVert(Bounds a, Bounds b) {
    double centerX = a.getCenterX();
    double centerY = a.getCenterY();
    double extraY = Math.min(Math.abs(centerY - b.getMaxY()), Math.abs(centerY - b.getMinY()));
    double minY = b.getMinY() - extraY;
    double maxY = b.getMaxY() + extraY;
    if (!contains(b.getMinX(), b.getMaxX(), centerX)) {
      if (contains(b.getMinY(), b.getMaxY(), centerY)) {
        return true;
      } else if (contains(minY, maxY, centerY)) {
        return true;
      }
      return false;
    }
    return false;
  }

  private boolean contains(double a1, double a2, double b) {
    return (a1 <= b && a2 >= b);
  }

  private void handleKeyInput(KeyCode code) {
    switch (code) {
      case RIGHT -> {
        if (inPlay) {
          paddle.setX(paddle.newPaddleX(true, wWidth, PADDLE_SPEED));
        }
      }
      case LEFT -> {
        if (inPlay) {
          paddle.setX(paddle.newPaddleX(false, wWidth, PADDLE_SPEED));
        }
      }
      case UP, DOWN -> paddle.setX(paddle.getX());
      case S -> ball.incSpeed();
      case L -> livesLeft++;
      case E -> paddle.enlargePaddle(wWidth);
      case D -> doPowerUp("deep freeze", null);
      case F -> {
        inPlay = false;
        won = true;
        updateText(root);
      }
      case R -> {
        resetPaddleBall();
        inPlay = false;
      }
      case SPACE -> inPlay = true;
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
    Image[] snow_angel_imgs = {new Image(getClass().getResourceAsStream(SNOW_ANGEL_IMAGE)),
        new Image(getClass().getResourceAsStream(SNOW_ANGEL_ICED_IMAGE))};
    Image[] black_imgs = {new Image(getClass().getResourceAsStream(BLACK_IMAGE)),
        new Image(getClass().getResourceAsStream(BLACK_ICED_IMAGE))};

    FileReader map = null;
    try {
      map = new FileReader(mapFile);
    } catch (FileNotFoundException e) {
      System.out.println("Incorrect File");
    }

    BufferedReader inStream = new BufferedReader(map);
    String[] data = inStream.readLine().split(" ");
    int numCols = Integer.parseInt(data[0]);
    int numRows = Integer.parseInt(data[1]);
    blockWidth = wWidth / numCols;
    blockHeight = wWidth / numRows;

    int blockSpeed = BASE_BLOCK_SPEED + ((level - 1) * BLOCK_SPEED_INC);
    int colIndex = 0;
    int rowIndex = 0;
    int c = inStream.read();
    blocks.add(new ArrayList<Block>());
    Random rand = new Random();
    double angle;
    while (c != -1) {
      angle = rand.nextInt(361);
      switch (c) {
        case 10 -> {
          rowIndex++;
          colIndex = 0;
          blocks.add(new ArrayList<Block>());
        }
        case 32 -> colIndex++;
        case 49 -> blocks.get(rowIndex).add(colIndex, new Block(colIndex * blockWidth + 1,
            rowIndex * blockHeight + TEXT_MARGIN_SIZE, blockWidth, blockHeight, ice_imgs,
            1, blockSpeed, angle));
        case 50 -> blocks.get(rowIndex).add(colIndex, new Block(colIndex * blockWidth + 1,
            rowIndex * blockHeight + TEXT_MARGIN_SIZE, blockWidth, blockHeight, wood_imgs,
            2, blockSpeed, angle));
        case 51 -> blocks.get(rowIndex).add(colIndex, new Block(colIndex * blockWidth + 1,
            rowIndex * blockHeight + TEXT_MARGIN_SIZE, blockWidth, blockHeight, brick_imgs,
            3, blockSpeed, angle));
        case 52 -> blocks.get(rowIndex).add(colIndex, new Block(colIndex * blockWidth + 1,
            rowIndex * blockHeight + TEXT_MARGIN_SIZE, blockWidth, blockHeight, concrete_imgs,
            4, blockSpeed, angle));
        case 53 -> blocks.get(rowIndex).add(colIndex, new Block(colIndex * blockWidth + 1,
            rowIndex * blockHeight + TEXT_MARGIN_SIZE, blockWidth, blockHeight, steel_imgs,
            5, blockSpeed, angle));
        case 65 -> blocks.get(rowIndex).add(colIndex, new SnowAngelBlock(colIndex * blockWidth + 1,
            rowIndex * blockHeight + TEXT_MARGIN_SIZE, blockWidth, blockHeight, snow_angel_imgs,
            3, blockSpeed, angle));
        case 66 -> blocks.get(rowIndex).add(colIndex, new BlackIceBlock(colIndex * blockWidth + 1,
            rowIndex * blockHeight + TEXT_MARGIN_SIZE, blockWidth, blockHeight, black_imgs,
            3, blockSpeed, angle));
        default -> blocks.get(rowIndex).add(colIndex, null);
      }
      c = inStream.read();
    }
    return blocks;
  }

  private void destroyBlock(int i, int j) {
    Block b = blocks.get(i).get(j);
    root.getChildren().remove(b);
    blocks.get(i).set(j, null);
    blocksBroken++;
    if (b instanceof SnowAngelBlock) {
      ((SnowAngelBlock) b).iceBlocks(blocks);
    }
    if (b instanceof BlackIceBlock) {
      String effect = ((BlackIceBlock) b).getEffect(POWERUPS, DISADVGS);
      if (POWERUPS.contains(effect) && powerUpActive == null) {
        doPowerUp(effect, b);
      } else if (DISADVGS.contains(effect) && disAdvActive == null) {
        doDisAdv(effect);
      }
    }
  }

  private void doPowerUp(String powerup, Block b) {
    switch (powerup) {
      case "winter freeze" -> winterFreeze(true);
      case "avalanche" -> avalanche(true);
      case "spread the holiday cheer" -> spreadHolidayCheer((BlackIceBlock) b);
      case "deep freeze" -> deepFreeze();
    }
    powerUpActive = powerup;
    powerUpActiveTime = 0;
  }

  private void stopPowerUp() {
    winterFreeze(false);
    avalanche(false);
    powerUpActive = null;
    powerUpActiveTime = 0;
  }

  private void doDisAdv(String disadv) {
    switch (disadv) {
      case "slippery paddle" -> slipperyPaddle(true);
    }

    disAdvActive = disadv;
    disAdvActiveTime = 0;
  }

  private void stopDisAdv() {
    slipperyPaddle(false);
    disAdvActive = null;
    disAdvActiveTime = 0;
  }

  private void winterFreeze(boolean start) {
    for (List<Block> blkRow : blocks) {
      for (Block blk : blkRow) {
        if (blk != null && start) {
          blk.freeze();
        } else if (blk != null && !start) {
          blk.unfreeze();
        }
      }
    }
  }

  private void avalanche(boolean start) {
    if (start) {
      ball.makeAvalanche();
    } else {
      ball.unAvalanche();
    }
  }

  private void spreadHolidayCheer(BlackIceBlock blk) {
    List<Integer[]> blocksToDestroy = blk.destroySurroundingBlocks(blocks);
    for (Integer[] blockLocation : blocksToDestroy) {
      destroyBlock(blockLocation[0], blockLocation[1]);
    }
  }

  private void deepFreeze() {
    for (List<Block> blkRow : blocks) {
      for (Block blk : blkRow) {
        if (blk != null) {
          blk.makeIced();
        }
      }
    }
  }

  private void blockIsHit(Block b) {
    b.hit();
    blocksHit++;
  }

  private void slipperyPaddle(boolean active) {
    paddle.makeSlippery(active);
  }

  private void checkWon() {
    for (List<Block> blkRow : blocks) {
      for (Block blk : blkRow) {
        if (blk != null) {
          won = false;
          return;
        }
      }
    }
    inPlay = false;
    won = true;
  }

  public boolean gameIsRunning() {
    if (won) {
      return false;
    }
    if (livesLeft <= 0) {
      return false;
    }
    return true;
  }

  public boolean gameIsWon() {
    return won;
  }

  private void drawText(Group root) {

    setBasicTextOptions(lvlText, "Level: " + level, BASIC_FONT_SIZE, Color.LIGHTBLUE);
    lvlText.setX(lvlTextLoc[0]);
    lvlText.setY(lvlTextLoc[1]);

    setBasicTextOptions(lives, "Lives: " + livesLeft, BASIC_FONT_SIZE, Color.LIGHTBLUE);
    lives.setX(livesTextLoc[0]);
    lives.setY(livesTextLoc[1]);

    setBasicTextOptions(hit, "Blocks Hit: " + blocksHit, BASIC_FONT_SIZE, Color.LIGHTBLUE);
    hit.setX(hitsTextLoc[0]);
    hit.setY(hitsTextLoc[1]);

    setBasicTextOptions(destroyed, "Blocks Destroyed: " + blocksBroken,
        BASIC_FONT_SIZE, Color.LIGHTBLUE);
    destroyed.setX(destroyedTextLoc[0]);
    destroyed.setY(destroyedTextLoc[1]);

    setBasicTextOptions(currPower, "Power Up: --", BASIC_FONT_SIZE, Color.LIGHTBLUE);
    currPower.setX(powerupTextLoc[0]);
    currPower.setY(powerupTextLoc[1]);

    setBasicTextOptions(currDisAdv, "Disadvantage: --", BASIC_FONT_SIZE, Color.LIGHTBLUE);
    currDisAdv.setX(disadvTextLoc[0]);
    currDisAdv.setY(disadvTextLoc[1]);

    setBasicTextOptions(winMsg, "YOU WIN!", TITLE_FONT_SIZE, Color.MEDIUMSPRINGGREEN);
    winMsg.setX(wWidth / 2 - winMsg.getBoundsInParent().getWidth() / 2);
    winMsg.setY(2 * (wHeight / 5));

    setBasicTextOptions(nextLvlMsg, "Press enter to continue",
        BASIC_FONT_SIZE, Color.MEDIUMSPRINGGREEN);
    nextLvlMsg.setX(wWidth / 2 - nextLvlMsg.getBoundsInParent().getWidth() / 2);
    nextLvlMsg.setY(2 * (wHeight / 5) + 2 * winMsg.getBoundsInParent().getHeight());

    setBasicTextOptions(loseMsg, "YOU LOSE", TITLE_FONT_SIZE, Color.MEDIUMSPRINGGREEN);
    loseMsg.setX(wWidth / 2 - loseMsg.getBoundsInParent().getWidth() / 2);
    loseMsg.setY(2 * (wHeight / 5));

    root.getChildren().add(lives);
    root.getChildren().add(hit);
    root.getChildren().add(destroyed);
    root.getChildren().add(currPower);
    root.getChildren().add(currDisAdv);
    root.getChildren().add(lvlText);
  }

  private void updateText(Group root) {
    lives.setText("Lives: " + livesLeft);
    hit.setText("Blocks Hit: " + blocksHit);
    destroyed.setText("Blocks Destroyed: " + blocksBroken);

    if (powerUpActive == null) {
      currPower.setText("Power Up: --");
    } else {
      currPower.setText("Power Up: " + powerUpActive);
    }

    if (disAdvActive == null) {
      currDisAdv.setText("Disadvantage: --");
    } else {
      currDisAdv.setText("Disadvantage: " + disAdvActive);
    }

    if (won) {
      root.getChildren().add(winMsg);
      root.getChildren().add(nextLvlMsg);
    }

    if (livesLeft <= 0) {
      root.getChildren().add(loseMsg);
      root.getChildren().add(nextLvlMsg);
    }
  }
}