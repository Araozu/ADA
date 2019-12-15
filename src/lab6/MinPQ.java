package lab6;

import java.util.*;
public class MinPQ<E extends Comparable<E>> {
	private int capacity = 10;
	protected int size = 0;
	
	private Object[] items = new Object[capacity];
	
	private int getLeftChildIndex(int parentIndex) { 
		return 2*parentIndex+1; 
	}
	private int getRightChildIndex(int parentIndex) { 
		return 2*parentIndex+2; 
	}
	private int getParentIndex(int childIndex) { 
		return (childIndex - 1)/2; 
	}
	
	private boolean hasLeftChild(int index) {
		return getLeftChildIndex(index)<size;
	}
	private boolean hasRightChild(int index) {
		return getRightChildIndex(index)<size;
	}
	private boolean hasParent(int index) {
		return getParentIndex(index)>=0;
	}
	
	private E leftChild(int index) {
		return (E)items[getLeftChildIndex(index)];
	}
	private E rightChild(int index) {
		return (E)items[getRightChildIndex(index)];
	}
	private E parent(int index) {
		return (E)items[getParentIndex(index)];
	}
	
	private void swap(int indexOne, int indexTwo) {
		E temp = (E)items[indexOne];
		items[indexOne]=items[indexTwo];
		items[indexTwo]=temp;
	}
	
	private void ensureExtraCapacity() {
		if(size==capacity) {
			items = Arrays.copyOf(items, capacity*2);
			capacity*=2;
		}
	}
	
	public E min() {
		if(size==0) throw new IllegalStateException();
		return (E)items[0];
	}

	public E delMin() {
		if(size==0) throw new IllegalStateException();
		E item = (E)items[0];
		items[0] = items[size-1];
		size--;
		demotion();
		return item;
	}
	
	public void insert(E item) {
		ensureExtraCapacity();
		items[size]=item;
		size++;
		promotion();
	}

	
	private void promotion() {
		int index = size-1;
		while(hasParent(index) && parent(index).compareTo((E)items[index])>0) {
			swap(getParentIndex(index),index);
			index = getParentIndex(index);
		}
	}
	
	private void demotion() {
		int index = 0;
		while(hasLeftChild(index)) {
			int smallerChildIndex = getLeftChildIndex(index);
			if(hasRightChild(index) && rightChild(index).compareTo(leftChild(index))<0) {
				smallerChildIndex = getRightChildIndex(index);
			}
			
			if(((E) (items[index])).compareTo((E)items[smallerChildIndex])<0) {
				break;
			} else {
				swap(index,smallerChildIndex);
			}
			index = smallerChildIndex;
		}
	}

}