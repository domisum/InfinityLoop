package de.domisum.infinityloop.board.transcoding;

import de.domisum.infinityloop.board.model.Board;
import de.domisum.infinityloop.board.model.Tile;

public class BoardDecoder
{

	// DECODE
	public Board decode(byte[] data)
	{
		int startValue = readNumber(data, 0, 3);
		if(startValue != -7262)
			throw new IllegalArgumentException("The file does not start with the infinity symbol");

		int width = readNumber(data, 3, 4);
		int height = readNumber(data, 7, 4);

		int expectedBytesLeft = (int) Math.ceil((width*height)/2d);
		if(data.length != (11+expectedBytesLeft))
			throw new IllegalArgumentException("The width and height does not fit the length of the data");

		Tile[][] tiles = new Tile[height][width];
		for(int y = 0; y < height; y++)
			for(int x = 0; x < width; x++)
			{
				int tileIndex = (y*width)+x;
				int byteIndex = tileIndex/2;

				int byteValue = readNumber(data, 11+byteIndex, 1);

				int tileNumber = ((tileIndex%2) == 0) ? (byteValue >> 4) : (byteValue&0b1111);
				Tile decodedTile = decodeTile(tileNumber);

				tiles[y][x] = decodedTile;
			}

		return new Board(tiles);
	}


	private int readNumber(byte[] data, int start, int numberOfBytes)
	{
		if((start+numberOfBytes) > data.length)
			throw new IllegalArgumentException("Unexpectedly reached end of file");

		int number = 0;
		int factor = 1;
		for(int i = 0; i < numberOfBytes; i++)
		{
			number += data[start+i]*factor;
			factor *= 8;
		}

		return number;
	}

	private Tile decodeTile(int tileNumber)
	{
		return new Tile((tileNumber&0b1000) > 0, (tileNumber&0b0100) > 0, (tileNumber&0b0010) > 0, (tileNumber&0b0001) > 0);
	}

}
