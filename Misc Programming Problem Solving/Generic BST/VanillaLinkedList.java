// Sean Szumlanski
// COP 3503, Spring 2020

// VanillaLinkedList.java
// ======================
// This is a vanilla implementation of linked lists, where nodes contain
// integers. This is the first version we implemented in class today.


import java.io.*;

// Basic node class holds a piece of data (int) and a next field.
class VanillaNode
{
	int data;
	VanillaNode next;
	
	// Constructor method. Sets this object's 'data' field to 'data'.
	VanillaNode(int data)
	{
		this.data = data;
	}
}

// Basic linked lists.
public class VanillaLinkedList
{
	// Don't jack up my head and tail! (private members)
	private VanillaNode head, tail;

	// Insert at the tail of the list.
	public void tailInsert(int data)
	{
		// If the list is empty, set 'head' and 'tail' to the new node.
		if (head == null)
		{
			head = tail = new VanillaNode(data);
		}
		// Otherwise, append the new node to the end of the list and move the
		// tail reference forward.
		else
		{
			tail.next = new VanillaNode(data);
			tail = tail.next;
		}
	}

	// Insert at the head of the list.
	public void headInsert(int data)
	{
		// First, create the node to be inserted.
		VanillaNode newNode = new VanillaNode(data);

		// Insert it at the beginning of the list.
		newNode.next = head;
		head = newNode;

		// If the list was empty before adding this node, 'head' AND 'tail'
		// need to reference this new node.
		if (tail == null)
			tail = newNode;
	}

	// Remove the head of the list (and return its 'data' value). We're using
	// Integer so that we can return a null reference if the list is empty.
	// Otherwise, the return value can be used in int contexts. This is a bit
	// nicer than returning -1 or Integer.MIN_VALUE, because we might actually
	// want to allow those values in our linked list nodes!
	public Integer deleteHead()
	{
		// If the list is empty, signify that by returning null.
		if (head == null)
			return null;
		
		// Store the data from the head, then move the head reference forward.
		// Java will take care of the memory management when it realizes there
		// are no references to the old head anymore.
		int temp = head.data;
		head = head.next;
		
		// If the list is now empty (i.e., if the node we just removed was the
		// only node in the list), update the tail reference, too!
		if (head == null)
			tail = null;
		
		// Return the value from the old head node.
		return temp;
	}

	// This one is left to you as an exercise. Don't forget to update the tail
	// pointer after removing the tail node!
	public Integer deleteTail()
	{
		return null;  // not yet implemented
	}

	// Print the contents of the linked list.
	public void print()
	{
		for (VanillaNode temp = head; temp != null; temp = temp.next)
			System.out.print(temp.data + ((temp.next == null) ? "\n" : " "));
	}

	// Returns true if the list is empty, false otherwise.
	boolean isEmpty()
	{
		return (head == null);
	}

	public static void main(String [] args)
	{
		// Create a new linked list.
		VanillaLinkedList list = new VanillaLinkedList();

		list.headInsert(43);
		list.headInsert(58);
		list.headInsert(52);
		list.tailInsert(33);
		list.tailInsert(19);
		list.headInsert(12);

		// Print the list to verify everything got in there correctly.
		list.print();

		while (!list.isEmpty())
		{
			list.deleteHead();
			list.print();
		}

	}
}