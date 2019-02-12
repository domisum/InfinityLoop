package jpp.infinityloop.gui.pane;

import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import jpp.infinityloop.board.model.Board;
import jpp.infinityloop.board.file.BoardLoader;
import jpp.infinityloop.board.file.BoardWriter;
import jpp.infinityloop.gui.MainGUI;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class MenuPane extends VBox
{

	// CONSTANTS
	protected static final double ICON_SIZE = 40;

	// REFERENCES
	private MainGUI mainGUI;
	private Set<MenuIconPane> iconPanes = new HashSet<>();


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
		this.iconPanes.add(loadIconPane);

		MenuIconPane saveIconPane = new MenuIconPane("save", "Save board", e->save());
		gridPane.add(saveIconPane, 0, 1);
		this.iconPanes.add(saveIconPane);

		MenuIconPane shuffleIconPane = new MenuIconPane("shuffle", "Generate new shuffled board", e->shuffle());
		gridPane.add(shuffleIconPane, 0, 2);
		this.iconPanes.add(shuffleIconPane);

		MenuIconPane solveIconPane = new MenuIconPane("solve", "Solve current board", e->solve());
		gridPane.add(solveIconPane, 0, 3);
		this.iconPanes.add(solveIconPane);

		MenuIconPane fullscreenIconPane = new MenuIconPane("fullscreen", "Fullscreen mode", e->fullscreen());
		gridPane.add(fullscreenIconPane, 0, 4);
		this.iconPanes.add(fullscreenIconPane);

		MenuIconPane windowedIconPane = new MenuIconPane("windowed", "Windowed mode", e->windowed());
		gridPane.add(windowedIconPane, 0, 5);
		this.iconPanes.add(windowedIconPane);
	}


	// SETTERS
	public void setImageColor(Color color)
	{
		for(MenuIconPane menuIconPane : this.iconPanes)
			menuIconPane.setImageColor(color);
	}


	// INTERACTION
	private void load()
	{
		if(this.mainGUI.getBoardPane().isSolving())
			return;

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Load InfinityLoop board file");
		File chosenFile = fileChooser.showOpenDialog(this.mainGUI.getPrimaryStage());

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
			this.mainGUI.displayError("The selected board could not be loaded:\n\n"+e.getMessage());
			return;
		}

		this.mainGUI.getBoardPane().setBoard(board);
		this.mainGUI.changeToRandomColorCombination();
	}

	private void save()
	{
		if(this.mainGUI.getBoardPane().isSolving())
			return;

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save InfinityLoop board file");
		fileChooser.setInitialFileName("ininityLoopBoard.bin");
		File chosenFile = fileChooser.showSaveDialog(this.mainGUI.getPrimaryStage());

		if(chosenFile == null)
			return;

		Board board = this.mainGUI.getBoardPane().getBoard();

		try
		{
			BoardWriter boardWriter = new BoardWriter();
			boardWriter.write(board, chosenFile);
		}
		catch(Exception e)
		{
			this.mainGUI.displayError("The current board could not be saved:\n\n"+e.getMessage());
			return;
		}
	}

	private void shuffle()
	{
		if(this.mainGUI.getBoardPane().isSolving())
			return;

		this.mainGUI.displayRandomBoard();
	}

	private void solve()
	{
		boolean solved = this.mainGUI.getBoardPane().solve();

		if(!solved)
			this.mainGUI.displayError("The board couldn't be solved, since it is unsolvable.");
	}

	private void fullscreen()
	{
		this.mainGUI.getPrimaryStage().setFullScreen(true);
	}

	private void windowed()
	{
		this.mainGUI.getPrimaryStage().setFullScreen(false);
	}

}
