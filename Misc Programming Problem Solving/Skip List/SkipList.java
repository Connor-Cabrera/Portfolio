// Name: Connor Cabrera
// Course: COP 3503C
// Section: Spring 0001
// NID: co472243


import java.io.*;
import java.util.*;
import java.lang.*;


class Node<T>
{
	T data;
	int height;
	ArrayList<Node<T>> nextTower = new ArrayList<Node<T>>();
	
	Node(int height)
	{
		this.height = height;
		for (int i = 0; i < height; i++)
		{
			this.nextTower.add(null);
		}
	}
	
	Node(T data, int height)
	{
		this.height = height;
		for (int i = 0; i < height; i++)
		{
			this.nextTower.add(null);
		}
		this.data = data;
	}
	
	public T value()
	{
		return this.data;
	}
	
	public int height()
	{
		return this.height;
	}
	
	public Node<T> next(int level)
	{
		if (level < 0)
		{
			return null;
		}
		
		else if (level > this.height - 1)
		{
			return null;
		}
		
		return this.nextTower.get(level);
	}
	
	public void setNext(int level, Node<T> node)
	{
		this.nextTower.set(level, node);
	}
	
	public void grow()
	{
		this.nextTower.add(null);
		this.height++;
	}
	
	public boolean maybeGrow()
	{
		if (Math.random() < 0.5)
		{
			this.grow();
			return true;
		}
		return false;
	}
	
	public void trim(int height)
	{
		while(this.height > height)
		{
			this.nextTower.remove(this.height - 1);
			this.height--;
		}
	}
	
}

public class SkipList<T extends Comparable<T>>
{
	
	int size;
	int maxHeight;
	Node<T> head;
	Node<T> end;
	
	SkipList()
	{
		this.head = new Node<T>(1);
		this.end = this.head;
	}
	
	SkipList(int height)
	{
		if (height < 1)
		{
			this.head = new Node<T>(1);
			this.end = this.head;
		}
		else
		{
			this.head = new Node<T>(height);
			this.end = this.head;
		}
	}
	private static int getMaxHeight(int n)
	{
		return (int)(Math.log(n) / Math.log(2));
	}
	
	private static int generateRandomHeight(int maxHeight)
	{
		int headsCount = 1;
		
		while (Math.random() < 0.5 && headsCount < maxHeight)
		{
			headsCount++;
		}
		return headsCount;
	}
	
	private void growSkipList(int level)
	{
		Node<T> temp = this.head;
		Node<T>tempTwo = this.head.nextTower.get(0);
		while (tempTwo.nextTower.get(0) != null)
		{
			if (tempTwo.maybeGrow())
			{
				temp.nextTower.set(level - 1, tempTwo);
				temp = tempTwo;
				
			}
			tempTwo = tempTwo.nextTower.get(0);
		}
	}
	
	private void trimSkipList()
	{
		
	}
	
	public int size()
	{
		return this.size;
	}
	
	public int height()
	{
		return this.head.height;
	}
	
	public Node<T> head()
	{
		return this.head;
	}
	
	public void printSkipList()
	{
		Node<T> temp = this.head;
		while (temp.nextTower.get(0) != null)
		{
			temp = temp.nextTower.get(0);
			System.out.println("Data: " + temp.data + ", Height: " + temp.height + ", Size " + this.size);
		}
	}
	
	public void insert(T data)
	{
		int currentMaxHeight = 1;
		System.out.println("Size: " + this.size);
		if (this.size > 1)
		{
			currentMaxHeight = getMaxHeight(this.size);
		}
		System.out.println("Max Height: " + currentMaxHeight);
		int randomHeight = generateRandomHeight(currentMaxHeight);
		System.out.println("Random Height: " + randomHeight);
		Node<T> newNode = new Node<T>(data, randomHeight);
		System.out.println(currentMaxHeight);
		
		if (this.head.nextTower.get(0) == null)
		{
			this.head.nextTower.set(0, newNode);
			this.end = newNode;
		}
		if (data.compareTo(this.end.data) > 0)
		{
			this.end.nextTower.set(0, newNode);
			this.end = newNode;
		}
		if (data.compareTo(this.end.data) < 0)
		{
			int tempLevel = currentMaxHeight;
			Node<T> temp = this.head;
			while (data.compareTo(temp.nextTower.get(tempLevel - 1).data) > 0)
			{
				if (tempLevel == randomHeight)
				{
					newNode.nextTower.set(tempLevel - 1, temp.nextTower.get(tempLevel - 1));
					temp.nextTower.set(tempLevel - 1, newNode);
				}
				if (tempLevel > 0)
				{
					temp = temp.nextTower.get(tempLevel - 1);
					if (tempLevel > 1)
					{	
						tempLevel--;
					}
					System.out.println("Temp Level 3rd: " + tempLevel);
				}
				else if (tempLevel == 0)
				{
					temp = temp.nextTower.get(tempLevel);
				}
			}
			// while (temp.nextTower.get(tempLevel - 1) != null)
			// {
				// System.out.println("Temp Level 1st: " + tempLevel);
				// if (data.compareTo(temp.nextTower.get(tempLevel - 1).data) > 0)
				// {
					// temp = temp.nextTower.get(tempLevel - 1);
				// }
				// System.out.println("Temp Level 2nd: " + tempLevel);
				// if (data.compareTo(temp.nextTower.get(tempLevel - 1).data) < 0)
				// {
					// if (data.compareTo(temp.nextTower.get(tempLevel - 1).data) > 0)
					// {
						// temp = temp.nextTower.get(tempLevel - 1);
					// }
					// if (tempLevel == randomHeight)
					// {
						// newNode.nextTower.set(tempLevel - 1, temp.nextTower.get(tempLevel - 1));
						// temp.nextTower.set(tempLevel - 1, newNode);
					// }
					// if (tempLevel > 0)
					// {
						// temp = temp.nextTower.get(tempLevel - 1);
						// if (tempLevel > 1)
						// {	
							// tempLevel--;
						// }
						// System.out.println("Temp Level 3rd: " + tempLevel);
					// }
					// else if (tempLevel == 0)
					// {
						// temp = temp.nextTower.get(tempLevel);
					// }
				// }
				
			// }
			newNode.nextTower.set(0, temp.nextTower.get(0));
			temp.nextTower.set(0, newNode);

		}
		this.size++;
		currentMaxHeight = getMaxHeight(this.size);
		if (this.head.height < currentMaxHeight)
		{
			this.head.grow();
			this.growSkipList(currentMaxHeight);
		}
	}
	
	public static double difficultyRating()
	{
		return 5.0;
	}
	
	public static double hoursSpent()
	{
		return 36.0;
	}
	
	// public static void main(String [] args)
	// {
		// Node<Integer> n1 = new Node<Integer>(3, 5);
		// System.out.println(n1.value());
		// System.out.println(n1.height());
		// Node<Integer> n2 = new Node<Integer>(2, 7);
		// n1.setNext(0, n2);
		// Node<Integer> n3 = n1.next(0);
		// n3.trim(1);
		// System.out.println(n3.value());
		// System.out.println(n3.height());
		// SkipList<Integer> s = new SkipList<Integer>();
		// s.insert(15);
		// s.insert(17);
		// s.insert(18);
		// s.insert(16);
		// System.out.println(s.height());
		// s.printSkipList();
		// int oneCounter = 0, twoCounter = 0, threeCounter = 0, fourCounter = 0;
		// int x;
		// for (int i = 0; i < 10000; i++)
		// {
			// x = s.generateRandomHeight(s.getMaxHeight(8));
			// if (x == 1){
				// oneCounter++;
			// }
			// if (x == 2){
				// twoCounter++;
			// }
			// if (x == 3){
				// threeCounter++;
			// }
			// if (x == 4){
				// fourCounter++;
			// }
		// }
		// System.out.println((oneCounter * 100) / 10000 + "%");
		// System.out.println((twoCounter * 100) / 10000 + "%");
		// System.out.println((threeCounter * 100) / 10000 + "%");
		// System.out.println((fourCounter * 100) / 10000 + "%");
	// }
	
}


