package jpp.infinityloop.board.file;

import jpp.infinityloop.board.Board;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class BoardLoader
{

	private BoardDecoder boardDecoder = new BoardDecoder();


	// INIT
	public BoardLoader()
	{

	}


	// LOADING
	public Board load(File file) throws IOException
	{
		byte[] data = Files.readAllBytes(file.toPath());

		return this.boardDecoder.decode(data);
	}

}
