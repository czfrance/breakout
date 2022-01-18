package breakout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.scene.image.Image;

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

  public Image getBallImg() {
    return ballImg;
  }

  public Image[] getPaddleImgs() {
    return paddleImgs;
  }

  public List<Image[]> getBlockImgs() {
    return blockImgs;
  }
}
