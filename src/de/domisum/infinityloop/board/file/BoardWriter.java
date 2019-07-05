package de.domisum.infinityloop.board.file;

import de.domisum.infinityloop.board.transcoding.BoardEncoder;
import de.domisum.infinityloop.board.model.Board;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

public class BoardWriter
{

	private final BoardEncoder boardEncoder = new BoardEncoder();


	// WRITING
	public void write(Board board, File file) throws IOException
	{
		byte[] data = boardEncoder.encode(board);
		Files.write(file.toPath(), data, StandardOpenOption.CREATE);
	}

}
