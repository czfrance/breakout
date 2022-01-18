package breakout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.geometry.BoundingBox;
import javafx.scene.image.Image;

public class BlackIceBlock extends Block {

  private static final int DESTROY_RADIUS = 50;

  public BlackIceBlock(double x, double y, double w, double h, Image[] imgs,
      int hitsNeeded, int sp, double a) {
    super(x, y, w, h, imgs, hitsNeeded, sp, a);
  }

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

  public List<Integer[]> destroySurroundingBlocks(List<List<Block>> blocks) {
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


