package jpp.infinityloop.board.file;

import jpp.infinityloop.board.Board;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

public class BoardWriter
{

	private BoardEncoder boardEncoder = new BoardEncoder();


	// INIT
	public BoardWriter()
	{

	}


	// WRITING
	public void write(Board board, File file) throws IOException
	{
		byte[] data = boardEncoder.encode(board);

		Files.write(file.toPath(), data, StandardOpenOption.CREATE);
	}

}
