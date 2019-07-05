package de.domisum.infinityloop.board.model;

public enum Direction
{

	// @formatter:off
	LEFT(0, -1, 0),
	UP(1, 0, -1),
	RIGHT(2, 1, 0),
	DOWN(3, 0, 1);
	// @formatter:on


	public final int id;
	public final int dX;
	public final int dY;


	// INIT
	Direction(int id, int dX, int dY)
	{
		this.id = id;
		this.dX = dX;
		this.dY = dY;
	}


	// GETTERS
	public Direction getOpposite()
	{
		if(this == LEFT)
			return RIGHT;
		if(this == UP)
			return DOWN;
		if(this == RIGHT)
			return LEFT;
		// if(this == DOWN)
		return UP;
	}

}
