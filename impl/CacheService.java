package psl.wgcache.impl;
/** Copyright (c) 2000: The Trustees of Columbia University and the City of New York.
 * All Rights Reserved.
 * 
 *  Name:        CacheService.java *  Description: This class implements the real Cache.It is the Cache Interface used by the PersonalCacheModule 
 *                and the Shared Cache. *                It provides the standard methods like put and query
 *                provides a key if the data is not provided with a key for future use.
 *                In addition it performs other functions like *                    - purges the data if the cache is full and 
 *                    - update the history and the weight of the data when it is accessed. *                 
 * 
 * Construction: Cache by opening connection with the underlying database.
 *              
 *   * 
 * @author  Alpa
 *  
 */
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
  //public DBInterface db;  
  public HashtableDBInterface db;

  public CacheService(String name) throws Exception {
    Runtime.getRuntime().addShutdownHook(new Thread() {
      public void run() {
        shutdown();
      }    });               
    System.runFinalization(); 
    currSize = 0;
    //db = new Hashtable();
    //db = new  DBInterface(name);
    db = new HashtableDBInterface(name);
  }
  public Object query(Object queryTag)throws WGCException {
    Object result = db.get(queryTag);    if(result == null) {      System.out.println(serviceName + ":query(MISS)\" " + queryTag);
			//throw new WGCException("MISS");
		}else 
			System.out.println(serviceName + " :query(HIT)\" " + queryTag + "\" : " + result);
		return result;   }
  
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


