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
import jpp.infinityloop.gui.color.ColorChangeEffect;

public class BoardTilePane extends StackPane
{

	// REFERENCES
	private ImageView imageView;

	// TEMP
	private int currentAngle;


	// INIT
	public BoardTilePane(BoardPane boardPane, int x, int y)
	{
		Tile tile = boardPane.getBoard().getTileAt(x, y);

		this.imageView = new ImageView();
		this.imageView.setPreserveRatio(true);
		this.imageView.setCache(true);
		getChildren().setAll(this.imageView);

		Images.ImageAndRotation imageAndRotation = Images.getInstance().getTileImage(tile);
		this.imageView.setImage(imageAndRotation.image);
		this.imageView.setRotate(imageAndRotation.rotation);
		this.currentAngle = imageAndRotation.rotation;

		setSize(5);

		setOnMouseClicked(event->boardPane.onTileClick(x, y));
	}


	// SETTERS
	public void setSize(double size)
	{
		this.imageView.setFitHeight(size);
		this.imageView.setFitWidth(size);
	}

	public void setColor(Color color)
	{
		this.imageView.setEffect(new ColorChangeEffect(color));
	}


	// ROTATION
	public void rotate(long duration)
	{
		rotate(duration, (e)->
		{
		});
	}

	public void rotate(long duration, EventHandler<ActionEvent> onFinished)
	{
		this.currentAngle += 90;

		RotateTransition rotateTransition = new RotateTransition(Duration.millis(duration), this.imageView);
		rotateTransition.setToAngle(this.currentAngle);
		rotateTransition.setOnFinished(onFinished);

		rotateTransition.play();
	}

}
