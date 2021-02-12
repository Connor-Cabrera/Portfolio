// Name: Connor Cabrera
// Course: COP 3503C
// Section: Spring 0001
// NID: co472243

// The following comments are from the source code's author

// Sean Szumlanski
// COP 3503, Spring 2020

// ====================
// GenericBST: BST.java
// ====================
// Basic binary search tree (BST) implementation that supports insert() and
// delete() operations. This framework is provide for you to modify as part of
// Programming Assignment #2.


import java.io.*;
import java.util.*;

// By using type T, we can essentially substitute T for any type
// we currently need to use.
class Node<T>
{
	T data;
	Node<T> left, right;

	Node(T data)
	{
		this.data = data;
	}
}

// We use AnyType just like we used T for Node above.
// Extending Comparable allows us to make any comparisons necessary
// to maintain BST properties.
public class GenericBST<AnyType extends Comparable<AnyType>>
{
	private Node<AnyType> root;
	
	// The following two methods insert a node by creating a BST if it is empty
	// or placing it left or right depending on its value compared to the current root.
	public void insert(AnyType data)
	{
		root = insert(root, data);
	}

	private Node<AnyType> insert(Node<AnyType> root, AnyType data)
	{
		if (root == null)
		{
			return new Node<AnyType>(data);
		}
		
		// Smaller data goes to the left of the current root.
		else if (data.compareTo(root.data) < 0)
		{
			root.left = insert(root.left, data);
		}
		
		// Larger data goes to the right of the current root.
		else if (data.compareTo(root.data) > 0)
		{
			root.right = insert(root.right, data);
		}

		return root;
	}

	// The following two methods delete a node from the BST and makes
	// the necessary adjustments depending on if the position of the node.
	public void delete(AnyType data)
	{
		root = delete(root, data);
	}

	private Node<AnyType> delete(Node<AnyType> root, AnyType data)
	{
		if (root == null)
		{
			return null;
		}
		
		// Searching for the desired Node to delete.
		else if (data.compareTo(root.data) < 0)
		{
			root.left = delete(root.left, data);
		}
		else if (data.compareTo(root.data) > 0)
		{
			root.right = delete(root.right, data);
		}
		else
		{
			// If the node has no children, deleting it means an empty BST.
			if (root.left == null && root.right == null)
			{
				return null;
			}
			
			// If the node has one child (left or right) that child takes its place.
			else if (root.left == null)
			{
				return root.right;
			}
			else if (root.right == null)
			{
				return root.left;
			}
			else
			{
				// If the node has two children, the max of the left subtree will take its place
				// to maintain BST properties.
				root.data = findMax(root.left);
				root.left = delete(root.left, root.data);
			}
		}

		return root;
	}

	// This method assumes root is non-null, since this is only called by
	// delete() on the left subtree, and only when that subtree is not empty.
	private AnyType findMax(Node<AnyType> root)
	{
		while (root.right != null)
		{
			root = root.right;
		}

		return root.data;
	}
	
	// The following two methods recursively search the BST for data
	// and return true if is there and false otherwise.
	public boolean contains(AnyType data)
	{
		return contains(root, data);
	}
	 
	private boolean contains(Node<AnyType> root, AnyType data)
	{
		if (root == null)
		{
			return false;
		}
		
		// By finding if our desired data is less than or greater than the 
		// current root, we know to search to the left or right.
		else if (data.compareTo(root.data) < 0)
		{
			return contains(root.left, data);
		}
		else if (data.compareTo(root.data) > 0)
		{
			return contains(root.right, data);
		}
		else
		{
			return true;
		}
	}
	
	// The following two methods print the BST in order.
	public void inorder()
	{
		System.out.print("In-order Traversal:");
		inorder(root);
		System.out.println();
	}

	private void inorder(Node<AnyType> root)
	{
		if (root == null)
			return;

		inorder(root.left);
		System.out.print(" " + root.data);
		inorder(root.right);
	}
	
	// The following two methods print the BST in Pre Order.
	public void preorder()
	{
		System.out.print("Pre-order Traversal:");
		preorder(root);
		System.out.println();
	}

	private void preorder(Node<AnyType> root)
	{
		if (root == null)
			return;

		System.out.print(" " + root.data);
		preorder(root.left);
		preorder(root.right);
	}
	
	// The following two methods print the BST in Post Order.
	public void postorder()
	{
		System.out.print("Post-order Traversal:");
		postorder(root);
		System.out.println();
	}

	private void postorder(Node<AnyType> root)
	{
		if (root == null)
			return;

		postorder(root.left);
		postorder(root.right);
		System.out.print(" " + root.data);
	}
	
	// Returns my opinion of the difficulty of this assignment.
	public static double difficultyRating()
	{
		return 1.5;
	}
	
	// Returns how long it took me to complete this assignment.
	public static double hoursSpent()
	{
		return 8;
	}

	public static void main(String [] args)
	{
		GenericBST<Integer> myTree = new GenericBST<Integer>();

		for (int i = 0; i < 5; i++)
		{
			int r = (int)(Math.random() * 100) + 1;
			System.out.println("Inserting " + r + "...");
			myTree.insert(r);
		}

		myTree.inorder();
		myTree.preorder();
		myTree.postorder();
	}
}
