package de.domisum.infinityloop.board.model;

import java.util.Arrays;

public class Tile
{

	private final boolean[] sides;


	// INIT
	public Tile(boolean... sides)
	{
		if(sides.length != 4)
			throw new IllegalArgumentException("The number of sides has to be 4");

		this.sides = sides.clone();
	}


	// OBJECT
	@Override
	public String toString()
	{
		if(getLeft() && getTop() && getRight() && getBottom())
			return "╋";


		if(!getLeft() && getTop() && getRight() && getBottom())
			return "┣";

		if(getLeft() && !getTop() && getRight() && getBottom())
			return "┳";

		if(getLeft() && getTop() && !getRight() && getBottom())
			return "┫";

		if(getLeft() && getTop() && getRight() && !getBottom())
			return "┻";


		if(!getLeft() && !getTop() && getRight() && getBottom())
			return "┏";

		if(!getLeft() && getTop() && !getRight() && getBottom())
			return "┃";

		if(!getLeft() && getTop() && getRight() && !getBottom())
			return "┗";

		if(getLeft() && !getTop() && !getRight() && getBottom())
			return "┓";

		if(getLeft() && !getTop() && getRight() && !getBottom())
			return "━";

		if(getLeft() && getTop() && !getRight() && !getBottom())
			return "┛";


		if(getLeft() && !getTop() && !getRight() && !getBottom())
			return "╸";
		if(!getLeft() && getTop() && !getRight() && !getBottom())
			return "╹";
		if(!getLeft() && !getTop() && getRight() && !getBottom())
			return "╺";
		if(!getLeft() && !getTop() && !getRight() && getBottom())
			return "╻";

		return " ";
	}

	@Override
	public boolean equals(Object o)
	{
		if(this == o)
			return true;
		if((o == null) || (getClass() != o.getClass()))
			return false;

		Tile tile = (Tile) o;

		if(!Arrays.equals(sides, tile.sides))
			return false;

		return true;
	}

	@Override
	public int hashCode()
	{
		return Arrays.hashCode(sides);
	}


	// GETTERS
	public boolean getLeft()
	{
		return sides[0];
	}

	public boolean getTop()
	{
		return sides[1];
	}

	public boolean getRight()
	{
		return sides[2];
	}

	public boolean getBottom()
	{
		return sides[3];
	}

	public boolean get(Direction direction)
	{
		return sides[direction.id];
	}


	// CHANGE
	public Tile getRotated()
	{
		return new Tile(getBottom(), getLeft(), getTop(), getRight());
	}

	public Tile getSet(Direction direction, boolean set)
	{
		boolean[] sidesCopy = Arrays.copyOf(sides, 4);
		sidesCopy[direction.id] = set;

		return new Tile(sidesCopy);
	}

}
