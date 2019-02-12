package jpp.infinityloop.gui.pane;

import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import jpp.infinityloop.board.model.Board;
import jpp.infinityloop.board.BoardSolver;
import jpp.infinityloop.board.model.TileCoordinate;
import jpp.infinityloop.gui.MainGUI;

import java.util.LinkedList;
import java.util.List;

public class BoardPane extends HBox
{

	// REFERENCES
	private MainGUI mainGUI;
	private Board board;

	// GUI
	private GridPane gridLayout;
	private BoardTilePane[][] tilePanes;

	private double boardWidth = 50;
	private double boardHeight = 50;

	// STATUS
	private boolean solving = false;
	private boolean solved = false;

	private List<TileCoordinate> solvingAnimationsQueue = new LinkedList<>();


	// INIT
	public BoardPane(MainGUI mainGUI)
	{
		this.mainGUI = mainGUI;

		// this boxing is done to center the grid both vertically and horizontally
		setAlignment(Pos.CENTER);

		VBox vBox = new VBox();
		vBox.setAlignment(Pos.CENTER);
		getChildren().setAll(vBox);

		this.gridLayout = new GridPane();
		this.gridLayout.setHgap(-2);
		this.gridLayout.setVgap(-2);
		vBox.getChildren().addAll(this.gridLayout);
	}

	private void displayBoard()
	{
		this.gridLayout.getChildren().clear();
		this.tilePanes = new BoardTilePane[this.board.getHeight()][this.board.getWidth()];

		for(int y = 0; y < this.board.getHeight(); y++)
			for(int x = 0; x < this.board.getWidth(); x++)
			{
				BoardTilePane boardTilePane = new BoardTilePane(this, x, y);

				this.tilePanes[y][x] = boardTilePane;
				this.gridLayout.add(boardTilePane, x, y);
			}

		setTileSize(this.boardWidth, this.boardHeight);
	}


	// SETTERS
	public void setBoard(Board board)
	{
		this.board = board;
		displayBoard();

		this.solved = false;
	}

	public void setTileSize(double boardWidth, double boardHeight)
	{
		this.boardWidth = boardWidth;
		this.boardHeight = boardHeight;

		setPrefWidth(boardWidth);
		setPrefHeight(boardHeight);

		if(this.board == null)
			return;

		double widthPerTile = boardWidth/this.board.getWidth();
		double heightPerTile = boardHeight/this.board.getHeight();

		double size = Math.min(widthPerTile, heightPerTile);

		for(int y = 0; y < this.board.getHeight(); y++)
			for(int x = 0; x < this.board.getWidth(); x++)
				this.tilePanes[y][x].setSize(size);
	}

	public void setTileColor(Color color)
	{
		if(this.board == null)
			return;

		for(int y = 0; y < this.board.getHeight(); y++)
			for(int x = 0; x < this.board.getWidth(); x++)
				this.tilePanes[y][x].setColor(color);
	}


	// GETTERS
	public Board getBoard()
	{
		return this.board;
	}

	public boolean isSolving()
	{
		return this.solving;
	}


	// UPDATE
	protected void onTileClick(int x, int y)
	{
		if(this.solved)
		{
			this.mainGUI.displayRandomBoard();
			return;
		}

		if(this.solving)
			return;

		this.board.rotateTileAt(x, y);
		this.tilePanes[y][x].rotate(300);

		if(this.board.isSolved())
			onSolved();
	}


	// SOLVING
	public boolean solve()
	{
		if(this.board == null)
			return true;

		if(this.board.isSolved())
			return true;

		this.solvingAnimationsQueue.clear();

		Board boardCopy = this.board.copy();
		BoardSolver boardSolver = new BoardSolver(boardCopy);

		boolean solved = boardSolver.solve();
		if(!solved)
			return false;

		// this locks the controls
		this.solving = true;

		for(int d = 0; d < this.board.getWidth()+this.board.getHeight()-1; d++)
		{
			int direction = d%2 == 0 ? 1 : -1;
			int start = direction == 1 ? 0 : d;
			int end = direction == 1 ? d : 0;

			for(int x = start; direction == 1 ? x <= end : x >= end; x += direction)
			{
				int y = d-x;

				if(x >= this.board.getWidth() || y >= this.board.getHeight())
					continue;

				while(!this.board.getTileAt(x, y).equals(boardCopy.getTileAt(x, y)))
				{
					this.board.rotateTileAt(x, y);
					this.solvingAnimationsQueue.add(new TileCoordinate(x, y));
				}
			}
		}

		continueAnimationQueue();
		return true;
	}

	private void continueAnimationQueue()
	{
		if(this.solvingAnimationsQueue.isEmpty())
		{
			this.solving = false;

			onSolved();
			return;
		}

		double combinedDuration = 7000;
		double tileDuration = combinedDuration/(this.board.getWidth()*this.board.getHeight());
		long rotationDuration = Math.round(tileDuration/2);

		TileCoordinate tileCoordinate = this.solvingAnimationsQueue.remove(0);
		BoardTilePane boardTilePane = this.tilePanes[tileCoordinate.y][tileCoordinate.x];
		boardTilePane.rotate(rotationDuration, (e)->continueAnimationQueue());
	}


	private void onSolved()
	{
		this.solved = true;

		this.mainGUI.changeToRandomColorCombination();
	}

}
