package jpp.infinityloop.board;

import jpp.infinityloop.board.model.Board;
import jpp.infinityloop.board.model.Direction;
import jpp.infinityloop.board.model.Tile;
import jpp.infinityloop.board.model.TileCoordinate;

public class BoardSolver
{

	// INPUT
	private final Board board;

	// helper
	private final TileActivityField activeTiles;


	// INIT
	public BoardSolver(Board board)
	{
		this.board = board;

		activeTiles = new TileActivityField(board.getWidth(), board.getHeight());
	}


	// SOLVING
	public boolean solve()
	{
		// add over-edge tiles to active tiles, so tiles illegaly pointin to the edges will be detected
		// also add all empty and all full-cross tiles, so that tiles pointing at them wil be detected as false
		for(int y = -1; y <= board.getHeight(); y++)
			for(int x = -1; x <= board.getWidth(); x++)
				if(isOutOfBounds(y, x))
					activeTiles.setActive(x, y, true);
				else
				{
					Tile tile = board.getTileAt(x, y);
					if(isTileAlwaysSolved(tile))
						activeTiles.setActive(x, y, true);
				}

		TileCoordinate tileCoordinate = getInactiveTile(0);
		if(tileCoordinate == null)
			return true;

		boolean solved = solveTile(tileCoordinate.x, tileCoordinate.y);
		return solved;
	}

	private boolean isOutOfBounds(int y, int x)
	{
		return (x == -1) || (x == board.getWidth()) || (y == -1) || (y == board.getHeight());
	}

	private boolean solveTile(int x, int y)
	{
		activeTiles.setActive(x, y, true);

		int states = 4;
		// a line only has two states, if you turn it 2 times it is effectively unchanged
		if(isTileLine(board.getTileAt(x, y)))
			states = 2;

		for(int i = 0; i < states; i++)
		{
			boolean tileValid = isTileValid(x, y);
			if(tileValid)
			{
				TileCoordinate next = getInactiveTile(y);
				if(next == null) // termination of recursion, if this if is true, the entire board has been solved
					return true;

				boolean subSolved = solveTile(next.x, next.y);
				if(subSolved) // propagating the message that the board has been solved upwards
					return true;

				// if not solved, continue with trying other rotations for this tile
			}

			board.rotateTileAt(x, y);
		}

		// if this part is reached, the recursion could not solve the inactive part of the board.
		// this means the board is either unsolvable or that the active parts are wrong
		// return so the layers above can try something else
		activeTiles.setActive(x, y, false);
		return false;
	}

	private boolean isTileValid(int x, int y)
	{
		Tile tile = board.getTileAt(x, y);

		for(Direction dir : Direction.values())
		{
			int relativeTileX = x+dir.dX;
			int relativeTileY = y+dir.dY;
			if(!activeTiles.isActive(relativeTileX, relativeTileY))
				continue;

			Tile relativeTile = board.getRelativeTile(x, y, dir);
			if(tile.get(dir) != relativeTile.get(dir.getOpposite()))
				return false;
		}

		return true;
	}

	private TileCoordinate getInactiveTile(int currY)
	{
		for(int y = currY; y < board.getHeight(); y++)
			for(int x = 0; x < board.getWidth(); x++)
			{
				if(activeTiles.isActive(x, y))
					continue;

				return new TileCoordinate(x, y);
			}

		// no inactive tile left, return null
		return null;
	}


	private static boolean isTileAlwaysSolved(Tile tile)
	{
		if(!tile.getLeft() && !tile.getTop() && !tile.getRight() && !tile.getBottom()) // 0-sided piece
			return true;

		if(tile.getLeft() && tile.getTop() && tile.getRight() && tile.getBottom()) // 4-sided piece
			return true;

		return false;
	}

	private static boolean isTileLine(Tile tile)
	{
		if(tile.getLeft() && !tile.getTop() && tile.getRight() && !tile.getBottom())
			return true;

		if(!tile.getLeft() && tile.getTop() && !tile.getRight() && tile.getBottom())
			return true;

		return false;
	}


	// ACTIVITY FIELD
	private static class TileActivityField
	{

		private final boolean[][] tileActive;


		// INIT
		public TileActivityField(int width, int height)
		{
			tileActive = new boolean[height+2][width+2];
		}


		// GETTERS
		public boolean isActive(int x, int y)
		{
			return tileActive[y+1][x+1];
		}


		// SETTERS
		public void setActive(int x, int y, boolean active)
		{
			tileActive[y+1][x+1] = active;
		}

	}

}
