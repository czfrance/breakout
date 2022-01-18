package breakout;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Paint;

//RULE: ONLY ONE POWERUP AT A TIME
//IF BALL HITS TOO CLOSE TO EDGE OF BLOCK, IT WILL ROCKET OFF AT DIFFERENT ANGLE
//BUG: ball sometimes goes through the block, idk why

public class Breakout {

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
  public static final List<String> POWERUPS =
      Arrays.asList("winter freeze", "avalanche", "spread the holiday cheer");
  public static final List<String> DISADVGS = Arrays.asList("slippery paddle");

  private int level;
  private int livesLeft = NUM_LIVES;
  private int blocksBroken = 0;
  private int blocksHit = 0;
  private String powerUpActive;
  private double powerUpActiveTime = 0;
  private String disAdvActive;
  private double disAdvActiveTime = 0;

  private Ball ball;
  private Paddle paddle;
  private List<List<Block>> blocks;
  private BreakoutText myText;
  private BreakoutImages myImages;
  private int wWidth;
  private int wHeight;
  private boolean inPlay = false;
  private boolean won = false;

  private Group root = new Group();

  public Scene setupGame(int width, int height, Paint background, int lvl) {
    level = lvl;
    int blockMargin = 1;

    myText = new BreakoutText(width, height);
    myImages = new BreakoutImages();

    wWidth = width - 2 * blockMargin;
    wHeight = height - (myText.TEXT_MARGIN_SIZE);

    ball = new Ball(BALL_SIZE, BALL_SPEED, INIT_BALL_ANGLE, myImages.getBallImg(),
        wWidth / 2 - BALL_SIZE / 2, wHeight - (PADDLE_HEIGHT + BALL_SIZE + 1));
    paddle = new Paddle(wWidth / 2 - PADDLE_WIDTH / 2, wHeight - PADDLE_HEIGHT,
        PADDLE_WIDTH, PADDLE_HEIGHT, myImages.getPaddleImgs());

    powerUpActive = null;
    disAdvActive = null;

    String map = getMap(level);
    try {
      blocks = buildMap(map);
    } catch (IOException e) {
      System.out.println("IOException, unable to build map");
    }

    root.getChildren().add(ball);
    root.getChildren().add(paddle);
    addBlocksToRoot(root);

    myText.drawText(root, level, livesLeft, blocksHit, blocksBroken);

    Scene scene = new Scene(root, width, height, background);
    scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
    return scene;
  }

  private void addBlocksToRoot(Group root) {
    for (List<Block> blkRow : blocks) {
      for (Block blk : blkRow) {
        if (blk != null) {
          root.getChildren().add(blk);
        }
      }
    }
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
          blk.move(wWidth, wWidth, myText.TEXT_MARGIN_SIZE, elapsedTime);
        }
      }
    }
  }

  public boolean step(double elapsedTime) {
    if (inPlay) {
      checkEffects(elapsedTime);
      List<Boolean> intersect = isIntersecting(blocks, paddle, ball);
      ball.move(wWidth, wHeight, myText.TEXT_MARGIN_SIZE, intersect.get(0), intersect.get(1), elapsedTime);
      moveBlocks(elapsedTime);

      if (ball.lostLife(wHeight)) {
        livesLeft--;
        resetPaddleBall();
        inPlay = false;
      }
      checkWon();
      myText.updateText(root, livesLeft, blocksHit, blocksBroken, powerUpActive,
          disAdvActive, won);
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
        List<Boolean> ret = ball.intersects(curr);
        if (ret.get(0) || ret.get(1)) {
          blockIsHit(curr);
          if (curr.broken()) {
            destroyBlock(i, j);
          }
          return ret;
        }
      }
    }

    List<Boolean> paddleIntersect = ball.intersects(p);
    if (paddleIntersect.contains(true)) {
      p.hit();
      b.deviatePath(p.getPercentDeviation());
      if (p.isSlippery()) {
        b.slip();
      }
    }
    return paddleIntersect;
  }

  private void handleKeyInput(KeyCode code) {
    switch (code) {
      case RIGHT -> setPaddleX(true);
      case LEFT -> setPaddleX(false);
      case UP, DOWN -> paddle.setX(paddle.getX());
      case S -> ball.incSpeed();
      case L -> livesLeft++;
      case E -> paddle.enlargePaddle(wWidth);
      case D -> doPowerUp("deep freeze", null);
      case F -> {
        inPlay = false;
        won = true;
        myText.updateText(root, livesLeft, blocksHit, blocksBroken, powerUpActive,
            disAdvActive, won);
      }
      case R -> {
        resetPaddleBall();
        inPlay = false;
      }
      case SPACE -> inPlay = true;
      default -> {
      }
    }
  }

  private void setPaddleX(boolean right) {
    if (inPlay) {
      paddle.setX(paddle.newPaddleX(right, wWidth, PADDLE_SPEED));
    }
  }

  private List<List<Block>> buildMap(String mapFile) throws IOException {
    List<List<Block>> blocks = new ArrayList<>();
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
    int blockWidth = wWidth / numCols;
    int blockHeight = wWidth / numRows;

    int blockSpeed = BASE_BLOCK_SPEED + ((level - 1) * BLOCK_SPEED_INC);
    addBlocksFromFile(inStream, blocks, blockWidth, blockHeight, blockSpeed);

    return blocks;
  }

  private void addBlocksFromFile(BufferedReader inStream, List<List<Block>> blks, int blockWidth,
      int blockHeight, int blockSpeed) throws IOException {
    List<Image[]> blockImages = myImages.getBlockImgs();
    int colIndex = 0;
    int rowIndex = 0;
    int currChar = inStream.read();
    blks.add(new ArrayList<>());
    Random rand = new Random();
    double angle;
    while (currChar != -1) {
      angle = rand.nextInt(361);
      switch (currChar) {
        case 10 -> {
          rowIndex++;
          colIndex = 0;
          blks.add(new ArrayList<>());
        }
        case 32 -> colIndex++;
        case 49, 50, 51, 52, 53 -> blks.get(rowIndex)
            .add(colIndex, new Block(colIndex * blockWidth,
                rowIndex * blockHeight + myText.TEXT_MARGIN_SIZE, blockWidth, blockHeight,
                blockImages.get(currChar),
                currChar - 48, blockSpeed, angle));
        case 65, 66 -> blks.get(rowIndex).add(colIndex, new Block(colIndex * blockWidth,
            rowIndex * blockHeight + myText.TEXT_MARGIN_SIZE, blockWidth, blockHeight,
            blockImages.get(currChar),
            3, blockSpeed, angle));
        default -> blks.get(rowIndex).add(colIndex, null);
      }
      currChar = inStream.read();
    }
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
      default -> {
      }
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
      default -> {
      }
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

  private void slipperyPaddle(boolean active) {
    paddle.makeSlippery(active);
  }

  private void blockIsHit(Block b) {
    b.hit();
    blocksHit++;
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
}
