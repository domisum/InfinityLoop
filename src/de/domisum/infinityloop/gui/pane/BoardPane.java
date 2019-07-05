package de.domisum.infinityloop.gui.pane;

import de.domisum.infinityloop.board.BoardSolver;
import de.domisum.infinityloop.board.model.Board;
import de.domisum.infinityloop.board.model.TileCoordinate;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import de.domisum.infinityloop.gui.MainGUI;

import java.util.LinkedList;
import java.util.List;

public class BoardPane extends HBox
{

	// REFERENCES
	private final MainGUI mainGUI;
	private Board board;

	// GUI
	private final GridPane gridLayout;
	private BoardTilePane[][] tilePanes;

	private double boardWidth = 50;
	private double boardHeight = 50;

	// STATUS
	private boolean solving = false;
	private boolean solved = false;

	private final List<TileCoordinate> solvingAnimationsQueue = new LinkedList<>();


	// INIT
	public BoardPane(MainGUI mainGUI)
	{
		this.mainGUI = mainGUI;

		// this boxing is done to center the grid both vertically and horizontally
		setAlignment(Pos.CENTER);

		VBox vBox = new VBox();
		vBox.setAlignment(Pos.CENTER);
		getChildren().setAll(vBox);

		gridLayout = new GridPane();
		gridLayout.setHgap(-2);
		gridLayout.setVgap(-2);
		vBox.getChildren().addAll(gridLayout);
	}

	private void displayBoard()
	{
		gridLayout.getChildren().clear();
		tilePanes = new BoardTilePane[board.getHeight()][board.getWidth()];

		for(int y = 0; y < board.getHeight(); y++)
			for(int x = 0; x < board.getWidth(); x++)
			{
				BoardTilePane boardTilePane = new BoardTilePane(this, x, y);

				tilePanes[y][x] = boardTilePane;
				gridLayout.add(boardTilePane, x, y);
			}

		setTileSize(boardWidth, boardHeight);
	}


	// SETTERS
	public void setBoard(Board board)
	{
		this.board = board;
		displayBoard();

		solved = false;
	}

	public void setTileSize(double boardWidth, double boardHeight)
	{
		this.boardWidth = boardWidth;
		this.boardHeight = boardHeight;

		setPrefWidth(boardWidth);
		setPrefHeight(boardHeight);

		if(board == null)
			return;

		double widthPerTile = boardWidth/board.getWidth();
		double heightPerTile = boardHeight/board.getHeight();

		double size = Math.min(widthPerTile, heightPerTile);

		for(int y = 0; y < board.getHeight(); y++)
			for(int x = 0; x < board.getWidth(); x++)
				tilePanes[y][x].setSize(size);
	}

	public void setTileColor(Color color)
	{
		if(board == null)
			return;

		for(int y = 0; y < board.getHeight(); y++)
			for(int x = 0; x < board.getWidth(); x++)
				tilePanes[y][x].setColor(color);
	}


	// GETTERS
	public Board getBoard()
	{
		return board;
	}

	public boolean isSolving()
	{
		return solving;
	}


	// UPDATE
	protected void onTileClick(int x, int y)
	{
		if(solved)
		{
			mainGUI.displayRandomBoard();
			return;
		}

		if(solving)
			return;

		board.rotateTileAt(x, y);
		tilePanes[y][x].rotate(300);

		if(board.isSolved())
			onSolved();
	}


	// SOLVING
	public boolean solve()
	{
		if(board == null)
			return true;

		if(board.isSolved())
			return true;

		solvingAnimationsQueue.clear();

		Board boardCopy = board.copy();
		BoardSolver boardSolver = new BoardSolver(boardCopy);

		boolean solved = boardSolver.solve();
		if(!solved)
			return false;

		// this locks the controls
		solving = true;

		for(int d = 0; d < ((board.getWidth()+board.getHeight())-1); d++)
		{
			int direction = ((d%2) == 0) ? 1 : -1;
			int start = (direction == 1) ? 0 : d;
			int end = (direction == 1) ? d : 0;

			for(int x = start; (direction == 1) ? (x <= end) : (x >= end); x += direction)
			{
				int y = d-x;

				if((x >= board.getWidth()) || (y >= board.getHeight()))
					continue;

				while(!board.getTileAt(x, y).equals(boardCopy.getTileAt(x, y)))
				{
					board.rotateTileAt(x, y);
					solvingAnimationsQueue.add(new TileCoordinate(x, y));
				}
			}
		}

		continueAnimationQueue();
		return true;
	}

	private void continueAnimationQueue()
	{
		if(solvingAnimationsQueue.isEmpty())
		{
			solving = false;

			onSolved();
			return;
		}

		double combinedDuration = 7000;
		double tileDuration = combinedDuration/(board.getWidth()*board.getHeight());
		long rotationDuration = Math.round(tileDuration/2);

		TileCoordinate tileCoordinate = solvingAnimationsQueue.remove(0);
		BoardTilePane boardTilePane = tilePanes[tileCoordinate.y][tileCoordinate.x];
		boardTilePane.rotate(rotationDuration, e->continueAnimationQueue());
	}


	private void onSolved()
	{
		solved = true;

		mainGUI.changeToRandomColorCombination();
	}

}
