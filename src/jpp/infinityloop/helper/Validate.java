package jpp.infinityloop.helper;

public final class Validate
{

	// INIT
	private Validate()
	{

	}

	// VALIDATE
	public static <T> void notEmpty(T[] array, String message)
	{
		if(array.length == 0)
			throw new IllegalArgumentException(message);
	}

}
