package psl.wgcache.impl;

import psl.wgcache.exception.*;
import psl.wgcache.roles.*;
import java.util.*;
import java.io.*;


public class CacheService implements CacheManager {
  public static final String roleName = "CacheManager";
  public static final String serviceName = "Cache";
  
  public KeyWeightPair tmp = new KeyWeightPair();
  private final static long MAX_CACHE_SIZE  = 4000;
  public long currSize;
  public KeyWeightPair kwp;
  public static BinaryHeap bh = new BinaryHeap((int)MAX_CACHE_SIZE);
  public DBInterface db;  
  //Hashtable db;

  public CacheService(String name) throws Exception {
    Runtime.getRuntime().addShutdownHook(new Thread() {
      public void run() {
        shutdown();
      }    });               
    System.runFinalization(); 
    currSize = 0;
    //db = new Hashtable();
    db = new  DBInterface(name);
    //db = new HashtableDBInterface(name);
  }
  public Object query(Object queryTag)throws WGCException {    //need to change this to     Object resultDTD = db.get(queryTag);    if(resultDTD == null) {      System.out.println(serviceName + ":query(MISS)\" " + queryTag + "\" : " + resultDTD);      throw new WGCException("MISS");    }    System.out.println(serviceName + " :query(HIT)\" " + queryTag + "\" : " + resultDTD);    return resultDTD;   }
  
  public boolean full(long newSize) {
   // System.out.println(" THE SIZE IS ==> " + currSize);      
    return((currSize + newSize) > MAX_CACHE_SIZE);
  }

  public Object put(Object data,long size) {
    int hashcode = data.hashCode();
    Integer tmp = new  Integer(hashcode);
    put(tmp.toString(),data,size);  
    return(tmp.toString());
 }    protected void log(String mesg)  {
    System.out.println("Cache:" + mesg);
  }

  public void put(Object key,Object data,long size) {
    //System.out.println(" THE NEW SIZE IS ==> " + size);    
    if (!full(size)) {
      log(" NOT FULL");
      kwp = new KeyWeightPair(key,size);
      kwp.calculateWeight();
      try {
        bh.insert(kwp);
        currSize += size;
      } catch( Overflow e ) {  
       System.out.println( "Maximum number of objects exceeded");
        bh.deleteMax();
      }  
      // bh.printElements();
    } else {
      log("IS FULL");
      tmp = (KeyWeightPair)bh.deleteMax();
      System.out.println("The deleted size is =" + tmp.size);
      currSize -= tmp.size;      
      this.put(key,data,size);
    }
    db.put(key,data);	     }  public synchronized void shutdown() {
    // System.out.println("Shutting down");    db.shutdown();
  }  public void finalize(){    System.out.println("BYE");    shutdown();   }} 


