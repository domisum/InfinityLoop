package jpp.infinityloop.gui.pane;

import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import jpp.infinityloop.board.model.Tile;
import jpp.infinityloop.gui.Images;
import jpp.infinityloop.gui.Images.ImageAndRotation;
import jpp.infinityloop.gui.color.ColorChangeEffect;

public class BoardTilePane extends StackPane
{

	// REFERENCES
	private final ImageView imageView;

	// TEMP
	private int currentAngle;


	// INIT
	public BoardTilePane(BoardPane boardPane, int x, int y)
	{
		Tile tile = boardPane.getBoard().getTileAt(x, y);

		imageView = new ImageView();
		imageView.setPreserveRatio(true);
		imageView.setCache(true);
		getChildren().setAll(imageView);

		ImageAndRotation imageAndRotation = Images.getInstance().getTileImage(tile);
		imageView.setImage(imageAndRotation.getImage());
		imageView.setRotate(imageAndRotation.getRotation());
		currentAngle = imageAndRotation.getRotation();

		setSize(5);

		setOnMouseClicked(event->boardPane.onTileClick(x, y));
	}


	// SETTERS
	public void setSize(double size)
	{
		imageView.setFitHeight(size);
		imageView.setFitWidth(size);
	}

	public void setColor(Color color)
	{
		imageView.setEffect(new ColorChangeEffect(color));
	}


	// ROTATION
	public void rotate(long duration)
	{
		rotate(duration, e->
		{
		});
	}

	public void rotate(long duration, EventHandler<ActionEvent> onFinished)
	{
		currentAngle += 90;

		RotateTransition rotateTransition = new RotateTransition(Duration.millis(duration), imageView);
		rotateTransition.setToAngle(currentAngle);
		rotateTransition.setOnFinished(onFinished);

		rotateTransition.play();
	}

}
