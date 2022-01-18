package breakout;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

/**
 * Block is the class that holds all information regarding the blocks being used in the game. The
 * blocks can be hit a certain number of times before breaking ane can bounce around in a set space
 * in the window
 * <p>
 * It contains 2 states: normal and "iced", which affects the damage one hit from a ball does on it.
 * <p>
 * This class is the for basic blocks, there are subclasses for each special type of block.
 * <p>
 * Extends Rectangle, depends on Image and ImagePattern. Is parent class for SnowAngelBlock and
 * BlackIceBlock.
 * <p>
 * To create: Block b = new Block(initialXCoord, initialYCoord, blockWidth, blockHeight,
 * blockImages, hitsNeedToBreak, blockSpeed, initialTravelAngle)
 * <p>
 * Call any of its methods to perform actions.
 *
 * @author Cynthia France
 */
public class Block extends Rectangle {

  public static final int BLOCK_EDGE_SPACE_MARGIN = 1;

  private final Image[] images;
  private int hitsToBreak;
  private boolean isIced = false;
  private final int speed;
  private int currSpeed;
  private double angle;
  double speedFraction = 0.001;

  /**
   * Creates an instance of Block
   *
   * @param x          initial X coordinate of Block object
   * @param y          initial Y coordinate of Block object
   * @param width      width of the block
   * @param height     height of the block
   * @param imgs       array of 2 Images: index 0 contains the block's normal look and index 1
   *                   contains the block's "iced/brittle" look (effect of a power up)
   * @param hitsNeeded the number of times a ball must hit the block for it to be broken
   * @param speed      the speed at which the block travels
   * @param angle      the initial angle the block travels at, determines its direction of travel.
   *                   Is measured increasing counterclockwise with 0 degrees being to the right
   */
  public Block(double x, double y, double width, double height, Image[] imgs, int hitsNeeded,
      int speed, double angle) {
    super(x + BLOCK_EDGE_SPACE_MARGIN, y + BLOCK_EDGE_SPACE_MARGIN, width, height);
    hitsToBreak = hitsNeeded;
    this.speed = speed;
    currSpeed = this.speed;
    images = imgs;
    this.angle = angle;
    this.setFill(new ImagePattern(images[0]));
  }

  /**
   * Calculates the position of the block after elapsedTime seconds and moves the block there
   *
   * @param width       width of the area the block's allowed to travel in
   * @param height      width of the area the block's allowed to travel in
   * @param margin      the Text margin, to be subtracted from height to calculate the ball's
   *                    movement
   * @param elapsedTime the amount of time that has passed since the last frame (in seconds)
   */
  public void move(int width, int height, int margin, double elapsedTime) {
    if (onVertBorder(width)) {
      if (angle <= 180) {
        angle = 180 - angle;
      } else {
        angle = 540 - angle;
      }
    }

    if (onHorizBorder(height, margin)) {
      angle = 360 - angle;
    }

    this.setX(
        this.getX() + (currSpeed * speedFraction * Math.cos(Math.toRadians(angle))) * elapsedTime);
    this.setY(
        this.getY() - (currSpeed * speedFraction * Math.sin(Math.toRadians(angle))) * elapsedTime);

    if (speedFraction < 1) {
      speedFraction += 0.0001;
    }
  }

  /**
   * Is called when block is hit by a ball. Subtracts the correct # of hits from hitsToBreak based
   * on the state of the block.
   * <p>
   * Subtracts 1 if block is normal and subtracts 2 if block is "iced"
   */
  public void hit() {
    if (isIced) {
      hitsToBreak -= 2;
    } else {
      hitsToBreak--;
    }
  }

  /**
   * Reveals if the block has been hit enough times to be broken.
   *
   * @return true if the block is broken (# of hits required to break is met), false otherwise
   */
  public boolean isBroken() {
    return hitsToBreak <= 0;
  }

  /**
   * Turns a normal block into an "iced" block
   */
  public void makeIced() {
    this.setFill(new ImagePattern(images[1]));
    isIced = true;
  }

  /**
   * Freezes the block's movement by setting its speed to 0
   */
  public void freeze() {
    currSpeed = 0;
  }

  /**
   * Unfreezes the block by restoring speed to its normal value (passed in on creation)
   */
  public void unfreeze() {
    currSpeed = speed;
  }

  private boolean onVertBorder(int w) {
    return this.getX() >= (w - this.getWidth()) || this.getX() <= 0;
  }

  private boolean onHorizBorder(int h, int margin) {
    return this.getY() >= (h - this.getHeight()) || this.getY() <= margin;
  }
}
