// Name: Connor Cabrera
// Course: COP 3503C
// Section: Spring 0001
// NID: co472243

import java.io.*;
import java.util.*;
import java.lang.*;

public class SneakyQueens
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
	
	public static boolean allTheQueensAreSafe(ArrayList<String> coordinateStrings, int boardSize)
	{
		int len = coordinateStrings.size();
		if (boardSize < 1)
		{
			return false;
		}
		if (len > boardSize)
		{
			return false;
		}
		ArrayList<Integer> takenRows = new ArrayList<Integer>();
		ArrayList<Integer> takenCols = new ArrayList<Integer>();
		ArrayList<Long> diagModsDownRight = new ArrayList<Long>();
		ArrayList<Long> diagModsUpRight = new ArrayList<Long>();
		
		for (int i = 0; i < len; i++)
		{
			int row = positionToRow(coordinateStrings.get(i));
			int col = positionToCol(coordinateStrings.get(i));
			long space = cordToNum(row, col, boardSize);
			long modOne = space % (boardSize + 1);
			long modTwo = space % (boardSize - 1);
			int half = boardSize / 2;
			if (i > 0)
			{
				if(takenRows.contains(row) || takenCols.contains(col))
				{
					return false;
				}
				if(diagModsUpRight.contains(modOne) || diagModsDownRight.contains(modTwo))
				{
					if(diagModsUpRight.contains(modOne))
					{	
						int index = diagModsUpRight.indexOf(modOne);
						int diffRow = Math.abs(takenRows.get(index) - row);
						int diffCol = Math.abs(takenCols.get(index) - col);
						if (diffRow == diffCol)
						{
							return false;
						}
					}
					if(diagModsDownRight.contains(modTwo))
					{	
						int index = diagModsDownRight.indexOf(modTwo);
						int diffRow = Math.abs(takenRows.get(index) - row);
						int diffCol = Math.abs(takenCols.get(index) - col);
						if (diffRow == diffCol)
						{
							return false;
						}
					}
				}
			}
			takenRows.add(row);
			takenCols.add(col);
			diagModsUpRight.add(modOne);
			diagModsDownRight.add(modTwo);
		}
		
		return true;
	}
	
	public static double difficultyRating()
	{
		return 3.5;
	}
	
	public static double hoursSpent()
	{
		return 8;
	}
	
}