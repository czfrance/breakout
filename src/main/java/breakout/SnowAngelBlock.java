package breakout;

import java.util.List;
import javafx.geometry.BoundingBox;
import javafx.scene.image.Image;

/**
 * SnowAngelBlock is a subclass of Block has the ability to turn blocks within ICE_RADIUS into
 * "iced" blocks.
 * <p>
 * Extends Block, depends on List, BoundingBox, and Image.
 * <p>
 * To create: Block a = new SnowAngelBlock(initialXCoord, initialYCoord, blockWidth, blockHeight,
 * blockImages, hitsNeedToBreak, blockSpeed, initialTravelAngle)
 * <p>
 * Call any of its methods to perform actions.
 *
 * @author Cynthia France
 * @see Block
 */
public class SnowAngelBlock extends Block {

  public static final int ICE_RADIUS = 100;

  /**
   * Creates an instance of SnowAngelBlock
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
  public SnowAngelBlock(double x, double y, double width, double height, Image[] imgs,
      int hitsNeeded, int speed, double angle) {
    super(x, y, width, height, imgs, hitsNeeded, speed, angle);
  }

  /**
   * Turns all the blocks within ICE_RADIUS into "iced" blocks
   *
   * @param blocks a 2D List of all the blocks currently in the game
   */
  public void iceBlocks(List<List<Block>> blocks) {
    BoundingBox effectRange = new BoundingBox(this.getX() - ICE_RADIUS,
        this.getY() - ICE_RADIUS, ICE_RADIUS * 2 + this.getWidth(),
        ICE_RADIUS * 2 + this.getHeight());
    for (List<Block> blkRow : blocks) {
      for (Block blk : blkRow) {
        if (blk != null && effectRange.contains(blk.getBoundsInParent())) {
          blk.makeIced();
        }
      }
    }
  }
}
