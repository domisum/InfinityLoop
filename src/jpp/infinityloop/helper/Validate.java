package jpp.infinityloop.helper;

import java.util.Collection;
import java.util.Map;

public class Validate
{

	public static void notNull(Object o, String message)
	{
		if(o == null)
			throw new NullPointerException(message);
	}

	public static <T> void notEmpty(T[] array, String message)
	{
		if(array.length == 0)
			throw new IllegalArgumentException(message);
	}


	public static void notEmpty(Collection<?> collection, String message)
	{
		if(collection.isEmpty())
			throw new IllegalArgumentException(message);
	}

	public static void notEmpty(Map<?, ?> map, String message)
	{
		if(map.isEmpty())
			throw new IllegalArgumentException(message);
	}

	public static void inRangeIllegalArgs(int lowerBound, int upperBound, int value, String parameterName)
	{
		if(value < lowerBound || value > upperBound)
			throw new IllegalArgumentException(
					"'"+parameterName+"' has to be between "+lowerBound+" and "+upperBound+" but was "+value);
	}

	public static void inRangeOutOfBounds(int lowerBound, int upperBound, int value, String parameterName)
	{
		if(value < lowerBound || value > upperBound)
			throw new IndexOutOfBoundsException(
					"'"+parameterName+"' has to be between "+lowerBound+" and "+upperBound+" but was "+value);
	}


	public static void doubleArrayValues(double[] dArray, String parameterName)
	{
		for(double d : dArray)
			if(Double.isNaN(d) || Double.isInfinite(d))
				throw new IllegalArgumentException("The double array '"+parameterName+"' contains NaN or Infinity");
	}

}
