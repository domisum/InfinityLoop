package jpp.infinityloop.board;

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
	@Override public boolean equals(Object o)
	{
		if(!(o instanceof TileCoordinate))
			return false;

		TileCoordinate that = (TileCoordinate) o;

		if(this.x != that.x)
			return false;
		if(this.y != that.y)
			return false;

		return true;
	}

	@Override public int hashCode()
	{
		int result = this.x;
		result = 419*result+this.y;

		return result;
	}

}
