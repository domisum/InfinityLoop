package jpp.infinityloop.board.transcoding;

import jpp.infinityloop.board.model.Board;
import jpp.infinityloop.board.model.Tile;

public class BoardEncoder
{

	// ENCODING
	public byte[] encode(Board board)
	{
		int dataLength = 11+(int) Math.ceil((board.getWidth()*board.getHeight())/2d);
		byte[] data = new byte[dataLength];

		data[0] = (byte) 0xe2;
		data[1] = (byte) 0x88;
		data[2] = (byte) 0x9e;

		writeNumber(data, 3, 4, board.getWidth());
		writeNumber(data, 7, 4, board.getHeight());

		for(int byteIndex = 0; byteIndex < (dataLength-11); byteIndex++)
		{
			int tileIndex1 = byteIndex*2;
			int tileIndex2 = tileIndex1+1;

			Tile tile1 = board.getTileAt(tileIndex1%board.getWidth(), tileIndex1/board.getWidth());
			Tile tile2 = null;
			if((tileIndex2/board.getWidth()) < board.getHeight())
				tile2 = board.getTileAt(tileIndex2%board.getWidth(), tileIndex2/board.getWidth());

			int byteValue = (encodeTile(tile1)<<4)|encodeTile(tile2);
			writeNumber(data, 11+byteIndex, 1, byteValue);
		}

		return data;
	}

	private void writeNumber(byte[] data, int start, int numberOfBytes, int number)
	{
		int mask = 0b11111111;
		for(int i = 0; i < numberOfBytes; i++)
		{
			int byteValue = (number >> (8*i))&mask;
			data[start+i] = (byte) byteValue;
		}
	}

	private int encodeTile(Tile tile)
	{
		if(tile == null)
			return 0;

		int number = 0;
		if(tile.getLeft())
			number |= 0b1000;
		if(tile.getTop())
			number |= 0b0100;
		if(tile.getRight())
			number |= 0b0010;
		if(tile.getBottom())
			number |= 0b0001;

		return number;
	}

}
