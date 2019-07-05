package de.domisum.infinityloop.gui.color;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class Colors
{

	// REFERENCES
	private static final Colors instance = new Colors();
	private final Random random = new Random();

	// COLORS
	private final List<ColorCombination> colorCombinations = new ArrayList<>();


	// INIT
	private Colors()
	{
		prepareColorCombinations();
	}

	private void prepareColorCombinations()
	{
		colorCombinations.add(new ColorCombination(Color.web("#FF420E"), Color.web("#89DA59")));
		colorCombinations.add(new ColorCombination(Color.web("#2A3132"), Color.web("#90AFC5")));
		colorCombinations.add(new ColorCombination(Color.web("#BA5536"), Color.web("#46211A")));
		colorCombinations.add(new ColorCombination(Color.web("#AEBD38"), Color.web("#505160")));
		colorCombinations.add(new ColorCombination(Color.web("#C4DFE6"), Color.web("#003B46")));
		colorCombinations.add(new ColorCombination(Color.web("#6FB98F"), Color.web("#021C1E")));
		colorCombinations.add(new ColorCombination(Color.web("#FFBB00"), Color.web("#375E97")));
		colorCombinations.add(new ColorCombination(Color.web("#F18D9E"), Color.web("#98DBC6")));
		colorCombinations.add(new ColorCombination(Color.web("#B7B8B6"), Color.web("#34675C")));
		colorCombinations.add(new ColorCombination(Color.web("#C99E10"), Color.web("#8D230F")));
	}


	// GETTERS
	public static Colors getInstance()
	{
		return instance;
	}

	public ColorCombination getRandomColorCombination(ColorCombination exclude)
	{
		ColorCombination colorCombination = colorCombinations.get(random.nextInt(colorCombinations.size()));

		if(colorCombination == exclude)
			return getRandomColorCombination(exclude);

		return colorCombination;
	}


	// COLOR COMBINATION
	public static final class ColorCombination
	{

		// COLORS
		private final Color foregroundColor;
		private final Color backgroundColor;


		// INIT
		private ColorCombination(Color foregroundColor, Color backgroundColor)
		{
			this.foregroundColor = foregroundColor;
			this.backgroundColor = backgroundColor;
		}


		// GETTERS
		public Color getForegroundColor()
		{
			return foregroundColor;
		}

		public Color getBackgroundColor()
		{
			return backgroundColor;
		}

	}

}
