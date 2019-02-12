package jpp.infinityloop.board.model;

import java.util.Objects;

public class TileCoordinate
{

	public final int x;
	public final int y;


	// INIT
	public TileCoordinate(int x, int y)
	{
		this.x = x;
		this.y = y;
	}


	// OBJECT
	@Override
	public boolean equals(Object o)
	{
		if(!(o instanceof TileCoordinate))
			return false;

		TileCoordinate that = (TileCoordinate) o;

		if(x != that.x)
			return false;
		if(y != that.y)
			return false;

		return true;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(x, y);
	}

}
