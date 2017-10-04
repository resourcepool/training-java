package model;

import java.util.Random;

public class RandomStatic {
	private static Random randomGenerator = new Random();
	
	public static long nextLong()
	{
		return randomGenerator.nextLong();
	}
}
