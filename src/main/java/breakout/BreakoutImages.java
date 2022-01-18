package breakout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.scene.image.Image;

/**
 * This is the class that manages all the Images the Breakout game/class uses.
 * <p>
 * Its purpose is to keep track of all images, load and create Image objects, and properly organize
 * them for the Breakout class to easily use
 * <p>
 * Depends on ArrayList, Collections, List, and Image
 * <p>
 * to create: BreakoutImages myImages = new BreakoutImages(); This creates Images objects for all
 * the needed images
 * <p>
 * Use methods below to access them
 *
 * @author Cynthia France
 * @see Image
 */
public class BreakoutImages {

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
  public static final String BLACK_ICE_IMAGE = RESOURCE_PATH + "carbon-fiber.png";
  public static final String WOOD_ICED_IMAGE = RESOURCE_PATH + "wood-iced.png";
  public static final String BRICK_ICED_IMAGE = RESOURCE_PATH + "brick-iced.png";
  public static final String STEEL_ICED_IMAGE = RESOURCE_PATH + "steel-iced.png";
  public static final String CONCRETE_ICED_IMAGE = RESOURCE_PATH + "concrete-iced.png";
  public static final String ICE_ICED_IMAGE = RESOURCE_PATH + "ice-iced.png";
  public static final String SNOW_ANGEL_ICED_IMAGE = RESOURCE_PATH + "gold-iced.png";
  public static final String BLACK_ICE_ICED_IMAGE = RESOURCE_PATH + "carbon-fiber-iced.png";

  Image ballImg;
  Image[] paddleImgs;
  List<Image[]> blockImgs;

  /**
   * Creates an instance of BreakoutImages
   * <p>
   * Loads and creates Images for all necessary images, puts the proper images together in arrays
   * and Lists
   */
  public BreakoutImages() {
    createImages();
  }

  private void createImages() {
    ballImg = new Image(getClass().getResourceAsStream(BALL_IMAGE));
    paddleImgs = new Image[]{new Image(getClass().getResourceAsStream(PADDLE_IMAGE)),
        new Image(getClass().getResourceAsStream(PADDLE_SLIP_IMAGE))};
    blockImgs = createBlockImages();

  }

  private List<Image[]> createBlockImages() {
    List<Image[]> blockImages = new ArrayList<>(Collections.nCopies(70, null));
    blockImages.add(49, new Image[]{new Image(getClass().getResourceAsStream(ICE_IMAGE)),
        new Image(getClass().getResourceAsStream(ICE_ICED_IMAGE))});
    blockImages.add(50, new Image[]{new Image(getClass().getResourceAsStream(WOOD_IMAGE)),
        new Image(getClass().getResourceAsStream(WOOD_ICED_IMAGE))});
    blockImages.add(51, new Image[]{new Image(getClass().getResourceAsStream(BRICK_IMAGE)),
        new Image(getClass().getResourceAsStream(BRICK_ICED_IMAGE))});
    blockImages.add(52, new Image[]{new Image(getClass().getResourceAsStream(CONCRETE_IMAGE)),
        new Image(getClass().getResourceAsStream(CONCRETE_ICED_IMAGE))});
    blockImages.add(53, new Image[]{new Image(getClass().getResourceAsStream(STEEL_IMAGE)),
        new Image(getClass().getResourceAsStream(STEEL_ICED_IMAGE))});
    blockImages.add(65, new Image[]{new Image(getClass().getResourceAsStream(SNOW_ANGEL_IMAGE)),
        new Image(getClass().getResourceAsStream(SNOW_ANGEL_ICED_IMAGE))});
    blockImages.add(66, new Image[]{new Image(getClass().getResourceAsStream(BLACK_ICE_IMAGE)),
        new Image(getClass().getResourceAsStream(BLACK_ICE_ICED_IMAGE))});

    return blockImages;
  }

  /**
   * returns the Image for the ball
   *
   * @return the ball Image
   */
  public Image getBallImg() {
    return ballImg;
  }

  /**
   * returns an Image[] with the 2 paddle Images
   *
   * @return array of 2 Images: index 0 contains the paddle's normal look and index 1 contains the
   * paddle's "slippery" look (effect of a disadvantage)
   */
  public Image[] getPaddleImgs() {
    return paddleImgs;
  }

  /**
   * returns a List of Image[] with each block's the 2 block Images, in the index of their
   * corresponding map char (the char that represents each block, ie ice = 1 = 49)
   *
   * @return List of array of 2 Images: index 0 contains the block's normal look and index 1
   * contains the block's "iced/brittle" look (effect of a power up). The Image[] for each type of
   * block is located at the index of their corresponding map char (the char that represents each
   * block, ie ice = 1 = 49)
   */
  public List<Image[]> getBlockImgs() {
    return blockImgs;
  }
}
