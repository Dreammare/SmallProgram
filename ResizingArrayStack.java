package edu.princeton;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.common.StdIn;
import edu.princeton.common.StdOut;

public class ResizingArrayStack<Item> implements Iterable<Item> {
	private Item[] a;	//array of items
	private int n;		//number of elements on stack
	
	//Initializes an empty stack
	public ResizingArrayStack() {
		a = ((Item[]) new Object[2]);
		n = 0;
	}
	
	//IS this stack empty?
	public boolean isEmpty() {
		return n == 0;
	}
	
	//Returns the number of items in the stack
	public int size() {
		return n;
	}
	
	//Resize the underlying array holding the elements
	private void resize(int capacity) {
		assert capacity >= n;
		
		//textbook implementation
		Item[] temp = (Item[]) new Object[capacity];
		for (int i = 0; i < n; i++) {
			temp[i] = a[i];
		}
		a = temp;
	}
	
	//Adds the item to this stack
	public void push(Item item) {
		if (n == a.length)
			resize(2*a.length);
		a[n++] = item;
	}
	
	//Removes and returns the item most recently added to this stack
	public Item pop() {
		if (isEmpty())
			throw new NoSuchElementException("Stack underflow");
		Item item = a[n-1];
		a[n-1] = null;
		n--;
		
		//shrink size of array if necessary
		if (n > 0 && n == a.length/4)
			resize(a.length/2);
		return item;
	}
	
	//Returns(but does not remove) the item most recently added to this stack
	public Item peek() {
		if (isEmpty())
			throw new NoSuchElementException("Stack underflow");
		return a[n-1];
	}

	@Override
	public Iterator<Item> iterator() {
		return new ReverseArrayIterator();
	}
	
	//an iterator does not implement remove() since it is optional
	private class ReverseArrayIterator implements Iterator<Item> {
		private int i;
		
		public ReverseArrayIterator() {
			i = n - 1;
		}
		
		public boolean hasNext() {
			return i >= 0;
		}
		
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
		public Item next() {
			if (!hasNext())
				throw new NoSuchElementException();
			return a[i--];
		}
	}
	
	public static void main(String[] args) {
		ResizingArrayStack<String> stack = new ResizingArrayStack<String>();
		while(!StdIn.isEmpty()) {
			String item = StdIn.readString();
			if(!item.equals("-"))
				stack.push(item);
			else if (!stack.isEmpty())
				StdOut.print(stack.pop() + " ");
		}
		StdOut.println("(" + stack.size() + "left on stack)");
	}
	
}
