package jpp.infinityloop.gui.pane;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import jpp.infinityloop.gui.Images;
import jpp.infinityloop.gui.color.ColorChangeEffect;

public class MenuIconPane extends StackPane
{

	// CONSTANTS
	private static final String BUTTON_STYLE_NORMAL = "-fx-padding: 0,0,0,0;-fx-background-color: transparent;";
	private static final String BUTTON_STYLE_HOVER = "-fx-padding: 0,0,0,0;-fx-background-color: rgba(250, 250, 250, 0.2); -fx-background-radius: 0 0 0 0;";

	// REFERENCES
	private ImageView iconImageView;


	// INIT
	public MenuIconPane(String iconName, String tooltip, EventHandler<MouseEvent> onClick)
	{
		Image image = Images.getInstance().getIcon(iconName);

		this.iconImageView = new ImageView(image);
		this.iconImageView.setFitWidth(MenuPane.ICON_SIZE);
		this.iconImageView.setFitHeight(MenuPane.ICON_SIZE);

		Button imageButton = new Button();
		imageButton.setGraphic(this.iconImageView);
		imageButton.setStyle(BUTTON_STYLE_NORMAL);
		imageButton.setTooltip(new Tooltip(tooltip));

		imageButton.setOnMouseEntered((e)->imageButton.setStyle(BUTTON_STYLE_HOVER));
		imageButton.setOnMouseExited((e)->imageButton.setStyle(BUTTON_STYLE_NORMAL));
		imageButton.setOnMouseClicked(onClick);

		getChildren().setAll(imageButton);
	}

	// SETTERS
	public void setImageColor(Color imageColor)
	{
		this.iconImageView.setEffect(new ColorChangeEffect(imageColor));
	}

}
