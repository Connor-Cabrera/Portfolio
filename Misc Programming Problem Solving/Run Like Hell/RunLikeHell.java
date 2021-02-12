// Name: Connor Cabrera
// Course: COP 3503C
// Section: Spring 0001
// NID: co472243

import java.io.*;
import java.util.*;

public class RunLikeHell
{
	public static int maxGain(int [] blocks)
	{
		int len = blocks.length;
		
		// Redundant Base Cases because the solution covers lengths 1 & 2,
		// but they were the first things I thought of when I was trying
		// to figure out the solution and they don't hurt so I kept them.
		
		if (len == 1)
		{
			return blocks[0];
		}
		
		if (len == 2)
		{
			return Math.max(blocks[0], blocks[1]);
		}
		
		// Tracking Sums
		int withCurrentIndex = blocks[0];
		int withoutCurrentIndex = 0;
		int holderSum = 0;
		
		// When you hit a block, you can either skip one or two blocks.
		// There is no point in skipping three because then the second
		// one you skipped would just be free points.
		
		for (int i = 1; i < len; i++)
		{
			holderSum = Math.max(withCurrentIndex, withoutCurrentIndex);
			
			withCurrentIndex = withoutCurrentIndex + blocks[i];
			withoutCurrentIndex = holderSum;
		}
		
		return Math.max(withCurrentIndex, withoutCurrentIndex);
	}
	
	public static double difficultyRating()
	{
		return 2.0;
	}
	
	public static double hoursSpent()
	{
		return 4.0;
	}
}