package de.domisum.infinityloop.gui.pane;

import de.domisum.infinityloop.board.file.BoardLoader;
import de.domisum.infinityloop.board.file.BoardWriter;
import de.domisum.infinityloop.board.model.Board;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import de.domisum.infinityloop.gui.MainGUI;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class MenuPane extends VBox
{

	// CONSTANTS
	protected static final double ICON_SIZE = 40;

	// REFERENCES
	private final MainGUI mainGUI;
	private final Set<MenuIconPane> iconPanes = new HashSet<>();


	// INIT
	public MenuPane(double width, MainGUI mainGUI)
	{
		this.mainGUI = mainGUI;
		setPrefWidth(width);
		setMaxWidth(width);
		setAlignment(Pos.CENTER);

		//setStyle("-fx-background-color: rgb(0, 230, 0);");

		GridPane gridPane = new GridPane();
		gridPane.setVgap(20);
		getChildren().setAll(gridPane);


		// components
		MenuIconPane loadIconPane = new MenuIconPane("load", "Load board", e->load());
		gridPane.add(loadIconPane, 0, 0);
		iconPanes.add(loadIconPane);

		MenuIconPane saveIconPane = new MenuIconPane("save", "Save board", e->save());
		gridPane.add(saveIconPane, 0, 1);
		iconPanes.add(saveIconPane);

		MenuIconPane shuffleIconPane = new MenuIconPane("shuffle", "Generate new shuffled board", e->shuffle());
		gridPane.add(shuffleIconPane, 0, 2);
		iconPanes.add(shuffleIconPane);

		MenuIconPane solveIconPane = new MenuIconPane("solve", "Solve current board", e->solve());
		gridPane.add(solveIconPane, 0, 3);
		iconPanes.add(solveIconPane);

		MenuIconPane fullscreenIconPane = new MenuIconPane("fullscreen", "Fullscreen mode", e->fullscreen());
		gridPane.add(fullscreenIconPane, 0, 4);
		iconPanes.add(fullscreenIconPane);

		MenuIconPane windowedIconPane = new MenuIconPane("windowed", "Windowed mode", e->windowed());
		gridPane.add(windowedIconPane, 0, 5);
		iconPanes.add(windowedIconPane);
	}


	// SETTERS
	public void setImageColor(Color color)
	{
		for(MenuIconPane menuIconPane : iconPanes)
			menuIconPane.setImageColor(color);
	}


	// INTERACTION
	private void load()
	{
		if(mainGUI.getBoardPane().isSolving())
			return;

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Load InfinityLoop board file");
		File chosenFile = fileChooser.showOpenDialog(mainGUI.getPrimaryStage());

		if(chosenFile == null)
			return;

		Board board;
		try
		{
			BoardLoader boardLoader = new BoardLoader();
			board = boardLoader.load(chosenFile);
		}
		catch(Exception e)
		{
			mainGUI.displayError("The selected board could not be loaded:\n\n"+e.getMessage());
			return;
		}

		mainGUI.getBoardPane().setBoard(board);
		mainGUI.changeToRandomColorCombination();
	}

	private void save()
	{
		if(mainGUI.getBoardPane().isSolving())
			return;

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save InfinityLoop board file");
		fileChooser.setInitialFileName("ininityLoopBoard.bin");
		File chosenFile = fileChooser.showSaveDialog(mainGUI.getPrimaryStage());

		if(chosenFile == null)
			return;

		Board board = mainGUI.getBoardPane().getBoard();

		try
		{
			BoardWriter boardWriter = new BoardWriter();
			boardWriter.write(board, chosenFile);
		}
		catch(Exception e)
		{
			mainGUI.displayError("The current board could not be saved:\n\n"+e.getMessage());
			return;
		}
	}

	private void shuffle()
	{
		if(mainGUI.getBoardPane().isSolving())
			return;

		mainGUI.displayRandomBoard();
	}

	private void solve()
	{
		boolean solved = mainGUI.getBoardPane().solve();

		if(!solved)
			mainGUI.displayError("The board couldn't be solved, since it is unsolvable.");
	}

	private void fullscreen()
	{
		mainGUI.getPrimaryStage().setFullScreen(true);
	}

	private void windowed()
	{
		mainGUI.getPrimaryStage().setFullScreen(false);
	}

}
