package de.domisum.infinityloop.gui;

import de.domisum.infinityloop.board.model.Tile;
import javafx.scene.image.Image;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Images
{

	// CONSTANTS
	private static final String TILES_PATH = "/img/tiles/";
	private static final String ICONS_PATH = "/img/icons/";

	// REFERENCES
	private static final Images instance = new Images();

	// IMAGES
	private Image cross;
	private Image tee;
	private Image straight;
	private Image bend;
	private Image end;
	private Image empty;

	private final Map<String, Image> icons = new HashMap<>();


	// INIT
	public void loadImages()
	{
		cross = loadImage(TILES_PATH+"cross.png");
		tee = loadImage(TILES_PATH+"tee.png");
		straight = loadImage(TILES_PATH+"straight.png");
		bend = loadImage(TILES_PATH+"bend.png");
		end = loadImage(TILES_PATH+"end.png");
		empty = loadImage(TILES_PATH+"empty.png");

		icons.put("load", loadImage(ICONS_PATH+"load.png"));
		icons.put("save", loadImage(ICONS_PATH+"save.png"));
		icons.put("shuffle", loadImage(ICONS_PATH+"shuffle.png"));
		icons.put("solve", loadImage(ICONS_PATH+"solve.png"));
		icons.put("fullscreen", loadImage(ICONS_PATH+"fullscreen.png"));
		icons.put("windowed", loadImage(ICONS_PATH+"windowed.png"));
	}

	private Image loadImage(String path)
	{
		InputStream inputStream = ClassLoader.class.getResourceAsStream(path);
		return new Image(inputStream);
	}


	// GETTERS
	public static Images getInstance()
	{
		return instance;
	}


	public ImageAndRotation getTileImage(Tile tile)
	{
		// cross
		if(tile.getLeft() && tile.getTop() && tile.getRight() && tile.getBottom())
			return new ImageAndRotation(cross, 0);


		// tee
		if(tile.getLeft() && tile.getTop() && tile.getRight() && !tile.getBottom())
			return new ImageAndRotation(tee, 0);

		if(!tile.getLeft() && tile.getTop() && tile.getRight() && tile.getBottom())
			return new ImageAndRotation(tee, 90);

		if(tile.getLeft() && !tile.getTop() && tile.getRight() && tile.getBottom())
			return new ImageAndRotation(tee, 180);

		if(tile.getLeft() && tile.getTop() && !tile.getRight() && tile.getBottom())
			return new ImageAndRotation(tee, 270);


		// straight
		if(tile.getLeft() && !tile.getTop() && tile.getRight() && !tile.getBottom())
			return new ImageAndRotation(straight, 0);

		if(!tile.getLeft() && tile.getTop() && !tile.getRight() && tile.getBottom())
			return new ImageAndRotation(straight, 90);


		// bend
		if(tile.getLeft() && tile.getTop() && !tile.getRight() && !tile.getBottom())
			return new ImageAndRotation(bend, 0);

		if(!tile.getLeft() && tile.getTop() && tile.getRight() && !tile.getBottom())
			return new ImageAndRotation(bend, 90);

		if(!tile.getLeft() && !tile.getTop() && tile.getRight() && tile.getBottom())
			return new ImageAndRotation(bend, 180);

		if(tile.getLeft() && !tile.getTop() && !tile.getRight() && tile.getBottom())
			return new ImageAndRotation(bend, 270);


		// end
		if(tile.getLeft() && !tile.getTop() && !tile.getRight() && !tile.getBottom())
			return new ImageAndRotation(end, 0);

		if(!tile.getLeft() && tile.getTop() && !tile.getRight() && !tile.getBottom())
			return new ImageAndRotation(end, 90);

		if(!tile.getLeft() && !tile.getTop() && tile.getRight() && !tile.getBottom())
			return new ImageAndRotation(end, 180);

		if(!tile.getLeft() && !tile.getTop() && !tile.getRight() && tile.getBottom())
			return new ImageAndRotation(end, 270);


		return new ImageAndRotation(empty, 0);
	}

	public Image getIcon(String name)
	{
		return icons.get(name);
	}


	// IMAGEANDROTATION
	public static class ImageAndRotation
	{

		private final Image image;
		private final int rotation;


		// INIT
		public ImageAndRotation(Image image, int rotation)
		{
			this.image = image;
			this.rotation = rotation;
		}


		// GETTERS
		public Image getImage()
		{
			return image;
		}

		public int getRotation()
		{
			return rotation;
		}
	}

}
