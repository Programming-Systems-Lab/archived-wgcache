package psl.wgcache.impl; 

import psl.wgcache.exception.*;
import java.io.*;
import java.util.*;

// BinaryHeap class
//
// CONSTRUCTION: with optional capacity (that defaults to 100)
//
// ******************PUBLIC OPERATIONS*********************
// void insert( x )       --> Insert x
// Comparable deleteMax( )--> Return and remove largest item
// Comparable findMax( )  --> Return largest item
// boolean isEmpty( )     --> Return true if empty; else false
// boolean isFull( )      --> Return true if full; else false
// void makeEmpty( )      --> Remove all items
// void printElements()   --> Print the array.
// ******************ERRORS********************************
// Throws Overflow if capacity exceeded

/**
 * Implements a binary heap.
 * Note that all "matching" is based on the compareTo method.
 * @author Alpa 
*/
public class BinaryHeap {
   /**
   * Construct the binary heap.
   * @param capacity the capacity of the binary heap.
  */
  public BinaryHeap(int capacity) {
    currentSize = 0;
    array = new KeyWeightPair[ capacity + 1 ];
  }
   
  public void insert(Comparable x)throws Overflow {
    int index = heapInsert(x);
    h.put(((KeyWeightPair)x).key,new Integer(index));
  }
  
  public void update(Object key) throws Overflow {
    
    KeyWeightPair temp = delete(key);
    temp.calculateWeight();
    int index = heapInsert(temp);
    h.put(key,new Integer(index));
  }
  /**
   * Find the largest item in the priority queue.
   * @return the largest item, or null, if empty.
  */
  public Comparable findMax() {
    if( isEmpty()) {
      return null;
    }
    return (array[1]);
  }

  /**
   * Remove the largest item from the priority queue.
   * @return the largest item, or null, if empty.
  */
  public Comparable deleteMax() {
    if (isEmpty()) {
      return null;
    }

    KeyWeightPair maxItem = (KeyWeightPair) findMax();
    array[1] = array[currentSize--];
    percolateDown(1);
    h.remove(maxItem.key);

    return (maxItem);
  }

  /**
   * Establish heap order property from an arbitrary
   * arrangement of items. Runs in linear time.
  */
  private void buildHeap() {
    for(int i=currentSize/2; i>0; i--) {
      percolateDown(i);
    }
  }

  /**
   * Test if the priority queue is logically empty.
   * @return true if empty, false otherwise.
  */
  public boolean isEmpty() {
    return (currentSize == 0);
  }

  /**
   * Test if the priority queue is logically full.
   * @return true if full, false otherwise.
  */
  public boolean isFull() {
    return (currentSize == array.length-1);
  }

  /**
   * Make the priority queue logically empty.
  */
  public void makeEmpty() {
    currentSize = 0;
  }

  public void printElements() {
    for(int k=0; k<=currentSize; k++) {
      System.out.println("The array element for a["+k+"]====>" + array[k]);  
    }
  }

  // private static final int DEFAULT_CAPACITY = 100;
  private Hashtable h = new Hashtable();
  private int currentSize;      // Number of elements in heap
  private KeyWeightPair [ ] array; // The heap array
  
  /**
   * Insert into the priority queue, maintaining heap order.
   * Duplicates are allowed.
   * @param x the item to insert.
   * @exception Overflow if container is full.
  */

  private int heapInsert( Comparable x ) throws Overflow {
    if (isFull()) {
      throw (new Overflow());
    }

    // Percolate up
    int hole = ++currentSize;
    for( ; hole > 1 && x.compareTo( array[ hole / 2 ] ) > 0; hole /= 2 ) {
      array[ hole ] = array[ hole / 2 ];
      // System.out.println(" INSERTINGGGGG...."+array[hole].key);
    }
    array[ hole ] = (KeyWeightPair)x;
    return(hole);
  }  
  /*
   * Internal method to delete a node from the heap.
   * @param key the key to be deleted.
  */
  
  private KeyWeightPair delete(Object key) {
    int index = ((Integer)h.get(key)).intValue();
    KeyWeightPair temp = array[index];
    array[index]= null;
    percolateDown(index); 
    return(temp);
  }
    
  /**
   * Internal method to percolate down in the heap.
   * @param hole the index at which the percolate begins.
  */
  private void percolateDown(int hole) {
    int child;
    KeyWeightPair tmp = array[hole];
    
    for( ; hole * 2 <= currentSize; hole = child) {
      child = hole * 2;
      if (child!=currentSize && array[child+1].compareTo(array[child])>0) {
        child++;
      }
      if (array[child].compareTo(tmp) > 0 ) {
        array[hole] = array[child];
      } else {
        break;
      }
    }
    array[hole] = tmp;
  }
  // Test program
}
