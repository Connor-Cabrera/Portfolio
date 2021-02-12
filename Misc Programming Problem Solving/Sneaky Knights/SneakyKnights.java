// Name: Connor Cabrera
// Course: COP 3503C
// Section: Spring 0001
// NID: co472243

import java.io.*;
import java.util.*;
import java.lang.*;

public class SneakyKnights
{
	public static int positionToRow(String pos)
	{
		int len = pos.length();
		int i;
		for (i = 0; i < len; i++)
		{
			char c = pos.charAt(i);
			if ( c >= '0' && c <= '9')
			{
				break;
			}
		}
		String numbers = pos.substring(i);
		int numVal = Integer.parseInt(numbers);
		return numVal;	
	}
	
	public static int positionToCol(String pos)
	{
		int len = pos.length();
		int i;
		for (i = 0; i < len; i++)
		{
			char c = pos.charAt(i);
			if ( c >= '0' && c <= '9')
			{
				break;
			}
		}
		String letters = pos.substring(0, i);
		int letLen = letters.length();
		int temp = letLen;
		int letPos = 0;
		for (int x = 0; x < letLen; x++)
		{
			letPos += (letters.charAt(x) - 96) * (Math.pow(26, temp - 1));
			temp --;
		}
		return letPos;	
	}
	
	public static long cordToNum(long row, long col, long sideLen)
	{
		long offset = sideLen - col;
		long boardNum = (sideLen * row) - offset;
		return boardNum;
	}
	
	public static boolean allTheKnightsAreSafe(ArrayList<String> coordinateStrings, int boardSize)
	{
		int len = coordinateStrings.size();
		if (boardSize < 1)
		{
			return false;
		}
		HashSet<Long> takenSpots = new HashSet<>();
		for (int i = 0; i < len; i++)
		{
			int row = positionToRow(coordinateStrings.get(i));
			int col = positionToCol(coordinateStrings.get(i));
			long number = cordToNum(row, col, boardSize);
			if (takenSpots.add(number) == false)
			{
				return false;
			}
			if (row + 2 <= boardSize && col + 1 <= boardSize)
			{
				number = cordToNum(row + 2, col + 1, boardSize);
				takenSpots.add(number);

			}
			if (row + 2 <= boardSize && col - 1 > 0)
			{
				number = cordToNum(row + 2, col - 1, boardSize);
				takenSpots.add(number);
			}
			if (row + 1 <= boardSize && col + 2 <= boardSize)
			{
				number = cordToNum(row + 1, col + 2, boardSize);
				takenSpots.add(number);
			}
			if (row + 1 <= boardSize && col - 2 > 0)
			{
				number = cordToNum(row + 1, col - 2, boardSize);
				takenSpots.add(number);
			}
			if (row - 2 > 0 && col + 1 <= boardSize)
			{
				number = cordToNum(row - 2, col + 1, boardSize);
				takenSpots.add(number);
			}
			if (row - 2 > 0 && col - 1 > 0)
			{
				number = cordToNum(row - 2, col - 1, boardSize);
				takenSpots.add(number);
			}
			if (row - 1 > 0 && col + 2 <= boardSize)
			{
				number = cordToNum(row - 1, col + 2, boardSize);
				takenSpots.add(number);
			}
			if (row - 1 > 0 && col - 2 > 0)
			{
				number = cordToNum(row - 1, col - 2, boardSize);
				takenSpots.add(number);
			}
		}
		return true;
	}
	
	public static double difficultyRating()
	{
		return 1.5;
	}
	
	public static double hoursSpent()
	{
		return 3;
	}
	
}