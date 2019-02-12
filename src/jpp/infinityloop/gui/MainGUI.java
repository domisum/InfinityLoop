package jpp.infinityloop.gui;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.stage.Stage;
import jpp.infinityloop.board.model.Board;
import jpp.infinityloop.board.BoardGenerator;
import jpp.infinityloop.gui.color.Colors;
import jpp.infinityloop.gui.color.Colors.ColorCombination;
import jpp.infinityloop.gui.pane.BoardPane;
import jpp.infinityloop.gui.pane.MenuPane;

import java.util.Random;

public class MainGUI extends Application
{

	// CONSTANTS
	private static final int WINDOW_WIDTH_DEFAULT = 640;
	private static final int WINDOW_HEIGHT_DEFAULT = 480;

	private static final double INSETS = 20;
	private static final double MENU_WIDTH = 45;

	// GAME
	private final BoardGenerator boardGenerator = new BoardGenerator(7, 20, 4, 15);
	private final Random random = new Random();

	// GUI
	private Stage primaryStage;

	private BorderPane mainLayoutPane;
	private BoardPane boardPane;
	private MenuPane menuPane;

	private ColorCombination currentColorCombination;


	// INIT
	public static void main(String[] args)
	{
		launch(args);
	}


	// GETTER
	public Stage getPrimaryStage()
	{
		return primaryStage;
	}

	public BoardPane getBoardPane()
	{
		return boardPane;
	}


	// JAVAFX
	@Override
	public void start(Stage primaryStage)
	{
		this.primaryStage = primaryStage;

		// preparation
		Images.getInstance().loadImages();


		// board pane
		boardPane = new BoardPane(this);
		addResizeListener();

		// menu pane
		menuPane = new MenuPane(MENU_WIDTH, this);

		// layout
		mainLayoutPane = new BorderPane();
		mainLayoutPane.setPadding(new Insets(INSETS));
		//this.mainLayoutPane.setStyle("-fx-background-color: rgb(43, 43, 43);");
		mainLayoutPane.setLeft(boardPane);
		mainLayoutPane.setRight(menuPane);

		changeToRandomColorCombination();


		// scene
		Scene guiScene = new Scene(mainLayoutPane);

		// stage
		primaryStage.setTitle("Infinity Loop (by Dominik Weißenseel) (Icons by Madebyoliver)");
		primaryStage.setScene(guiScene);
		primaryStage.setMinWidth(WINDOW_WIDTH_DEFAULT);
		primaryStage.setMinHeight(WINDOW_HEIGHT_DEFAULT);
		primaryStage.show();
	}


	// INTERACTION: RESIZE
	private void addResizeListener()
	{
		ChangeListener<Number> changeListener = (observalbe, oldValue, newValue)->
		{
			double availableWidth = primaryStage.getWidth()-((3*INSETS)+MENU_WIDTH)-5;
			double availableHeight = primaryStage.getHeight()-(2*INSETS)-38;

			boardPane.setTileSize(availableWidth, availableHeight);
		};

		primaryStage.widthProperty().addListener(changeListener);
		primaryStage.heightProperty().addListener(changeListener);
	}


	// INTERACTION: BOARD
	public void displayRandomBoard()
	{
		long seed = random.nextLong();
		Board board = boardGenerator.generate(seed);
		boardPane.setBoard(board);

		changeToRandomColorCombination();
	}

	public void changeToRandomColorCombination()
	{
		ColorCombination newColorCombination = Colors.getInstance().getRandomColorCombination(currentColorCombination);
		setColorCombination(newColorCombination);
	}

	private void setColorCombination(ColorCombination colorCombination)
	{
		currentColorCombination = colorCombination;

		boardPane.setTileColor(colorCombination.getForegroundColor());
		menuPane.setImageColor(colorCombination.getForegroundColor());

		mainLayoutPane.setBackground(new Background(new BackgroundFill(colorCombination.getBackgroundColor(),
				CornerRadii.EMPTY,
				Insets.EMPTY
		)));
	}


	// ERROR
	public void displayError(String message)
	{
		Alert alert = new Alert(AlertType.ERROR, message);
		alert.setTitle("An error has occurred");
		alert.showAndWait();
	}

}
