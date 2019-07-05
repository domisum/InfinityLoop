package de.domisum.infinityloop.board;

import de.domisum.infinityloop.board.model.Board;
import de.domisum.infinityloop.board.model.Direction;
import de.domisum.infinityloop.board.model.Tile;

import java.util.Random;

public class BoardGenerator
{

	private final int minWidth;
	private final int maxWidth;
	private final int minHeight;
	private final int maxHeight;


	// INIT
	public BoardGenerator(int minWidth, int maxWidth, int minHeight, int maxHeight)
	{
		this.minWidth = minWidth;
		this.maxWidth = maxWidth;
		this.minHeight = minHeight;
		this.maxHeight = maxHeight;
	}


	// GENERATE
	public Board generate(long seed)
	{
		Random random = new Random(seed);

		int width = getFromRangeInclusive(random, minWidth, maxWidth);
		int height = getFromRangeInclusive(random, minHeight, maxHeight);

		// create empty tilefield
		Tile[][] tiles = new Tile[height][width];
		Tile emptyTile = new Tile(false, false, false, false);
		for(int y = 0; y < height; y++)
			for(int x = 0; x < width; x++)
				tiles[y][x] = emptyTile;

		int desiredConnections = (int) Math.round(width*height*0.9d);
		int currentConnections = 0;
		while(currentConnections < desiredConnections)
		{
			int baseX = random.nextInt(width);
			int baseY = random.nextInt(height);
			Direction direction = Direction.values()[random.nextInt(4)];

			boolean couldBePlaced = tryPlaceConnection(tiles, baseX, baseY, direction);
			if(couldBePlaced)
				currentConnections++;
		}

		// create board from solved already solved tilefield
		Board board = new Board(tiles);
		board.shuffle();

		return board;
	}

	private boolean tryPlaceConnection(Tile[][] tiles, int baseX, int baseY, Direction direction)
	{
		int width = tiles[0].length;
		int height = tiles.length;

		int otherX = baseX+direction.dX;
		int otherY = baseY+direction.dY;

		// out of bounds
		if(isOutOfBounds(width, height, otherX, otherY))
			return false;

		Tile baseTile = tiles[baseY][baseX];
		Tile otherTile = tiles[otherY][otherX];

		// connection already exists
		if(baseTile.get(direction))
			return false;

		// create connection
		tiles[baseY][baseX] = baseTile.getSet(direction, true);
		tiles[otherY][otherX] = otherTile.getSet(direction.getOpposite(), true);
		return true;
	}

	private boolean isOutOfBounds(int width, int height, int otherX, int otherY)
	{
		return (otherX < 0) || (otherX >= width) || (otherY < 0) || (otherY >= height);
	}


	private int getFromRangeInclusive(Random random, int min, int max)
	{
		return min+random.nextInt((max-min)+1);
	}

}
