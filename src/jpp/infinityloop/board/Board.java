package jpp.infinityloop.board;

import jpp.infinityloop.helper.Validate;

import java.util.Random;

public class Board
{

	// CONSTANTS
	private static final Tile EMPTY_TILE = new Tile(false, false, false, false);

	// DATA
	private Tile[][] tiles;


	// INIT
	public Board(Tile[][] tiles)
	{
		Validate.notEmpty(tiles, "The tile array has to be at least 1 tile long in each dimension");
		Validate.notEmpty(tiles[0], "The tile array has to be at least 1 tile long in each dimension");

		this.tiles = tiles;
	}

	public Board copy()
	{
		Tile[][] tilesCopy = new Tile[getHeight()][getWidth()];
		for(int y = 0; y < getHeight(); y++)
			for(int x = 0; x < getWidth(); x++)
				tilesCopy[y][x] = getTileAt(x, y);

		return new Board(tilesCopy);
	}


	// OBJECT
	@Override public String toString()
	{
		String string = "";

		for(int y = 0; y < getHeight(); y++)
		{
			for(int x = 0; x < getWidth(); x++)
				string += getTileAt(x, y).toString();

			if(y != getHeight()-1)
				string += "\n";
		}

		return string;
	}


	// GETTERS
	public int getWidth()
	{
		return this.tiles[0].length;
	}

	public int getHeight()
	{
		return this.tiles.length;
	}

	public Tile getTileAt(int x, int y)
	{
		return this.tiles[y][x];
	}


	// CHANGERS
	public void rotateTileAt(int x, int y)
	{
		Tile rotatedTile = getTileAt(x, y).getRotated();
		this.tiles[y][x] = rotatedTile;
	}

	public void shuffle()
	{
		Random random = new Random();

		for(int y = 0; y < getHeight(); y++)
			for(int x = 0; x < getWidth(); x++)
			{
				int rotations = random.nextInt(4);
				for(int i = 0; i < rotations; i++)
					rotateTileAt(x, y);
			}
	}


	// SOLVED
	public boolean isSolved()
	{
		for(int y = 0; y < getHeight(); y++)
			for(int x = 0; x < getWidth(); x++)
				if(!isTileSolved(x, y))
					return false;

		return true;
	}

	public boolean isTileSolved(int x, int y)
	{
		Tile tile = getTileAt(x, y);

		for(Direction dir : Direction.values())
		{
			Tile relTile = getRelativeTile(x, y, dir);
			if(tile.get(dir) != relTile.get(dir.getOpposite()))
				return false;
		}

		return true;
	}

	public Tile getRelativeTile(int x, int y, Direction direction)
	{
		return getTileAtOrOutOfBoundsEmpty(x+direction.dX, y+direction.dY);
	}

	private Tile getTileAtOrOutOfBoundsEmpty(int x, int y)
	{
		if(x < 0 || x >= getWidth() || y < 0 || y >= getHeight())
			return EMPTY_TILE;

		return getTileAt(x, y);
	}

}
