// Name: Connor Cabrera
// Course: COP 3503C
// Section: Spring 0001
// NID: co472243

import java.io.*;
import java.util.*;
import java.lang.*;

public class ConstrainedTopoSort
{
	// The following initialization is adapted from Dr. Szumlanski's 'Graph.java'
	// with modifications to accomodate the different input format.
	
	boolean [][] matrix;
	
	// Initialize 'matrix' from input file
	
	public ConstrainedTopoSort(String filename) throws IOException
	{
		Scanner in = new Scanner(new File(filename));
		int N = in.nextInt();
		matrix = new boolean[N][N];
		
		int numEdges = 0;
		
		// Creates an adjacency matrix where a value is 'true' if the row vertex
		// has an edge directed to the column vertex. Ex: If [0][2] == 'true' then Vertex 1 -> Vertex 3.
		
		for (int i = 0; i < N; i++)
		{
			numEdges = in.nextInt();
			for (int j = 0; j < numEdges; j++)
			{
				matrix[i][in.nextInt() - 1] = true;
			}
		}
	}
	
	// The following code for finding a valid Topological Sort is adapted from Dr. Szumlanski's 'toposort.java'
	// with the modifications of changing the Queue to an ArrayList and adding a series of logic gates required
	// to solve the problem.
	
	public boolean hasConstrainedTopoSort(int x, int y)
	{
		int [] incoming = new int[matrix.length];
		boolean firstIn = false;
		
		// Creates the Array of incoming edges that must be monitored during Topo Sort traversal
		
		for (int i = 0; i < matrix.length; i++)
		{
			for (int j = 0; j < matrix.length; j++)
			{
				incoming[j] += (matrix[i][j] ? 1 : 0);
			}
		}
		
		ArrayList<Integer> q = new ArrayList<Integer>();
		
		// Adds vertices with 0 incoming edges to the ArrayList (AKA no requirements)
		
		for (int i = 0; i < matrix.length; i++)
		{
			if (incoming[i] == 0)
			{
				q.add(i);
			}
		}
		
		while (!q.isEmpty())
		{
			// Logic statements that, in summary, check if x precedes y.
			
			if (q.contains(x - 1))
			{
				firstIn = true;
			}
			if (q.contains(x - 1) && q.contains(y - 1))
			{
				return true;
			}
			if (firstIn == false && q.contains(y - 1))
			{
				return false;
			}
			if (firstIn == true && q.contains(y - 1))
			{
				return true;
			}
			
			int vertex = q.remove(q.size() - 1);
			
			// Updates the 'incoming' Array to allow any vertices whose requirements have recently been met.
			
			for (int i = 0; i < matrix.length; i++)
			{
				if (matrix[vertex][i] && --incoming[i] == 0)
				{
					q.add(i);
				}
			}
		}
		
		// If the entire graph has been traversed and x didn't precede y, then by default return false.
		
		return false;
		
	}
	
	public static double difficultyRating()
	{
		return 2.0;
	}
	
	public static double hoursSpent()
	{
		return 3.5;
	}
	
}