package breakout;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ResultsScreen {
  private Group root = new Group();

  private Text gameOver = new Text();
  private Text winText = new Text();
  private Text loseText = new Text();

  private boolean begin = false;


  public Scene drawScene(int width, int height, Paint background, boolean won) {
    gameOver.setText("GAME OVER");
    gameOver.setFont(new Font(30));
    gameOver.setFill(Color.LIGHTBLUE);
    gameOver.setX(width/2 - gameOver.getBoundsInParent().getWidth()/2);
    gameOver.setY(2*(height/5));

    winText.setText("YOU WIN!");
    winText.setFont(new Font(50));
    winText.setFill(Color.LIGHTBLUE);
    winText.setX(width/2 - winText.getBoundsInParent().getWidth()/2);
    winText.setY(2*(height/5) + 2*gameOver.getBoundsInParent().getHeight());

    loseText.setText("YOU LOSE");
    loseText.setFont(new Font(50));
    loseText.setFill(Color.LIGHTBLUE);
    loseText.setX(width/2 - loseText.getBoundsInParent().getWidth()/2);
    loseText.setY(2*(height/5) +  2*gameOver.getBoundsInParent().getHeight());

    root.getChildren().add(gameOver);
    if (won) {
      root.getChildren().add(winText);
    }
    else {
      root.getChildren().add(loseText);
    }

    Scene scene = new Scene(root, width, height, background);
    return scene;
  }
}
