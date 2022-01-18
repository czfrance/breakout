package breakout;

import java.util.List;
import javafx.geometry.BoundingBox;
import javafx.scene.image.Image;


public class SnowAngelBlock extends Block {

  private static final int ICE_RADIUS = 100;

  public SnowAngelBlock(double x, double y, double w, double h, Image[] imgs,
      int hitsNeeded, int sp, double a) {
    super(x, y, w, h, imgs, hitsNeeded, sp, a);
  }

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
