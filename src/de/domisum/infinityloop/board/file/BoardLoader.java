package de.domisum.infinityloop.board.file;

import de.domisum.infinityloop.board.model.Board;
import de.domisum.infinityloop.board.transcoding.BoardDecoder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class BoardLoader
{

	private final BoardDecoder boardDecoder = new BoardDecoder();


	// LOADING
	public Board load(File file) throws IOException
	{
		byte[] data = Files.readAllBytes(file.toPath());
		return boardDecoder.decode(data);
	}

}
