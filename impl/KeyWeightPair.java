package psl.wgcache.impl; 

/** Copyright (c) 2000: The Trustees of Columbia University and the City of New York.
  *  All Rights Reserved.
  
  *  Name:        KeyWeightPair class
  *  Description: Represents the weight assigned to each key corresponding to the data in the cache.
  *               The weight is a combination of the time since it has been in cache and the size.
  *               LRU combined with size of the data.
  *  
  * CONSTRUCTION: initialise size and key
  * 
  * ******************PUBLIC OPERATIONS************************************************
  * void calculateWeight( KeyWeightPair )  --> calculate weight for the associated key
  *  void printElements( )                  --> print members
  * ***********************************************************************************
  * 
  * 
  * @author Alpa 
 */ 

import psl.wgcache.exception.*;
import java.io.*;
import java.util.*;

 public class KeyWeightPair implements Comparable, Serializable {
  public KeyWeightPair() {}
  
  public KeyWeightPair(Object key, long size) {
    this.size = size;
    this.key  = key;
  }
  
  public void calculateWeight() {
    currentTime = new Date().getTime();
    weight = size + (currentTime/1000);
  }
  
  public int compareTo(Object rhs) {
    return (weight<((KeyWeightPair)rhs).weight)
           ? -1 : ((weight == ((KeyWeightPair)rhs).weight) ? 0 : 1);
  }
  
  public long longValue() {
    return weight;
  }
  
  public String toString() {
    return Long.toString(weight);
  }
  
  public boolean equals(Object rhs) {
    return rhs!= null && weight == ((KeyWeightPair)rhs).weight;
  }
  
  public void printElements() {
    System.out.println(" WEIGHT IS ====>" + weight);
    System.out.println(" SIZE   IS ====>" + size);
    System.out.println(" KEY IS ====>" + key);
  }
  
  public long size;
  public Object key;
  public long weight;
  public long currentTime;

  /*public static void main(String[] args) {
    long size  = 4000;
    BinaryHeap toInsert = new BinaryHeap((int)size);
    String key = "TESTING";
    KeyWeightPair k = new KeyWeightPair(key,size);
    k.calculateWeight();
    k.printElements();
    System.out.println("SENDING ==> " +k.key+ "  to heap");
    try {
      toInsert.insert(k);
    } catch (Overflow e) {
      System.out.println("Overflow (expected)!");
    }  
    toInsert.printElements();
    
    String key2 = "TESTING2";
    long size2  = 40002;
    KeyWeightPair k2 = new KeyWeightPair(key2,size2);
    k2.calculateWeight();
    k2.printElements();
    System.out.println("SENDING ==> " +k2.key+ "  to heap");
    try {
      toInsert.insert(k2);
    } catch( Overflow e ) { 
      System.out.println( "Overflow (expected)!");
    }
    // toInsert.printElements();
  } */
}