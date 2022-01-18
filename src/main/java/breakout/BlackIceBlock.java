package breakout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.geometry.BoundingBox;
import javafx.scene.image.Image;

/**
 * BlackIceBlock is a subclass of Block has the ability to randomly select from a list of power ups
 * and disadvantages one to implement.
 * <p>
 * Extends Block, depends on ArrayList, List, Random, BoundingBox, and Image.
 * <p>
 * To create: Block a = new SnowAngelBlock(initialXCoord, initialYCoord, blockWidth, blockHeight,
 * blockImages, hitsNeedToBreak, blockSpeed, initialTravelAngle)
 * <p>
 * Call any of its methods to perform actions.
 *
 * @author Cynthia France
 * @see Block
 */
public class BlackIceBlock extends Block {

  public static final int DESTROY_RADIUS = 50;

  /**
   * Creates an instance of BlackIceBlock
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
  public BlackIceBlock(double x, double y, double width, double height, Image[] imgs,
      int hitsNeeded, int speed, double angle) {
    super(x, y, width, height, imgs, hitsNeeded, speed, angle);
  }

  /**
   * Randomly selects an effect from a given list of power ups and disadvantages to implement
   *
   * @param powerups a List of game power ups
   * @param disAdvgs a List of game disadvantages
   * @return the randomly selected effect
   */
  public String getEffect(List<String> powerups, List<String> disAdvgs) {
    Random rand = new Random();
    int decideType = rand.nextInt(2);
    if (decideType == 0) {
      int powerup = rand.nextInt(powerups.size());
      return powerups.get(powerup);
    } else {
      int disAdv = rand.nextInt(disAdvgs.size());
      return disAdvgs.get(disAdv);
    }
  }

  /**
   * finds the blocks within DESTROY_RADIUS. NOTE: this method does not actually destroy the blocks
   *
   * @param blocks a 2D List of all the blocks currently in the game
   * @return A List of locations (in the Block 2D List) of blocks that are within DESTROY_RADIUS
   */
  public List<Integer[]> getSurroundingBlocks(List<List<Block>> blocks) {
    List<Integer[]> inRange = new ArrayList<>();
    BoundingBox effectRange = new BoundingBox(this.getX() - DESTROY_RADIUS,
        this.getY() - DESTROY_RADIUS, DESTROY_RADIUS * 2 + this.getWidth(),
        DESTROY_RADIUS * 2 + this.getHeight());
    for (int i = 0; i < blocks.size(); i++) {
      for (int j = 0; j < blocks.get(0).size(); j++) {
        Block b = blocks.get(i).get(j);
        if (b != null && effectRange.contains(b.getBoundsInParent())) {
          inRange.add(new Integer[]{i, j});
        }
      }
    }
    return inRange;
  }
}


