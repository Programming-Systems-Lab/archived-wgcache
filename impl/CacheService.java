package psl.wgcache.impl;

import java.beans.*;
import psl.wgcache.exception.*;
import psl.wgcache.roles.*;
import psl.groupspace.*;						
import psl.groupspace.impl.*;	
//import psl.worklets2.worklets.*;					
//import psl.worklets2.wvm.WVM;import java.util.*;
import java.io.*;
import com.odi.*;
import com.odi.util.*;

public class CacheService implements CacheManager, GroupspaceCallback, 
				     GroupspaceService{
  public static final String roleName = "CacheManager";
  public static final String serviceName = "Cache";
  protected GroupspaceController gc;
  
  public KeyWeightPair tmp = new KeyWeightPair();
  private final static long MAX_CACHE_SIZE  = 4000;
  public long currSize;
  public KeyWeightPair kwp;
  public static BinaryHeap bh = new BinaryHeap((int)MAX_CACHE_SIZE);
  //DBInterface db;    Hashtable db;
  
  //Constructor for worklets  
  /*public CacheService(String hn, String rn) throws Exception {    this();    
    WVM myWVM = new WVM(this,hn,rn);     myWVM.start();  } */   
  //Constructor
  public CacheService(String name) throws Exception {    
    Runtime.getRuntime().addShutdownHook(new Thread() {
	public void run() {
	  shutdown();
	}});               
    System.runFinalization(); 
    currSize = 0;
    db = new Hashtable();
    //db = new  DBInterface(name);
  }
  /*public void sendWorkletTo(String targetHost, String targetRole) {
    Worklet w = new Worklet(null);    String key = "testkey";    String data = "testData";    long size = data.length();
    w.addJunction(new WGC_WorkletJunction(targetHost,targetRole,key,data,size));
    w.deployWorklet(myWVM);
    }*/    
  public Object query(Object queryTag)throws WGCException {
    Object resultDTD = db.get(queryTag);
    if(resultDTD == null) {
      gc.Log(serviceName, "query(MISS)\" " + queryTag + "\" : " + resultDTD);
      throw new WGCException("MISS");
    }
    gc.Log(serviceName, "query(HIT)\" " + queryTag + "\" : " + resultDTD);
    return resultDTD; 
  }
  
  public boolean full(long newSize) {
    // System.out.println(" THE SIZE IS ==> " + currSize);      
    return((currSize + newSize) > MAX_CACHE_SIZE);
  }
  
  public Object put(Object data,long size) {
    int hashcode = data.hashCode();
    Integer tmp = new  Integer(hashcode);
    put(tmp.toString(),data,size);  
    return(tmp.toString());
  }
  
  public void put(Object key,Object data,long size) {
    //System.out.println(" THE NEW SIZE IS ==> " + size);    
    if (!full(size)) {
      System.out.println(" NOT FULL");
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
      System.out.println(" IS FULL");
      tmp = (KeyWeightPair)bh.deleteMax();
      System.out.println("The deleted size is =" + tmp.size);
      currSize -= tmp.size;
      System.out.println(" THE SIZE AFTER DELETING IS ==> " + currSize);    
      this.put(key,data,size);
    }
    db.put(key,data);	   
  }
  public synchronized void shutdown() {
    // db.shutdown();
  }
  
  public boolean gsInit(GroupspaceController gc) {
    this.gc = gc;
    gc.registerRole(roleName, this);
    gc.subscribeEvent(this,"Oracle_WGCache_Put");
    // gc.subscribeEvent(this,"Metaparser_WGCache_Put");
    gc.subscribeEvent(this,"Oracle_WGCache_Get");
    // gc.subscribeEvent(this,"Metaparser_WGCache_Get");
    // gc.subscribeAllEvents((GroupspaceCallback)this);
    gc.Log(serviceName, "Initialization completed");
    return true;
  }     
  
  public void gsUnload() {    
    shutdown();
    gc.Log(serviceName, "Unloaded");
  }
  
  public void run() { }
  /* for (Enumeration e = gc.listRoles() ; e.hasMoreElements() ;) {
     System.out.println(e.nextElement());
     }    */    
  /*try {      OracleService o = (OracleService)gc.findRole("OracleRole");
    if(o == null) 
    gc.Log(serviceName, "unable to find role OracleService");      else {        // System.out.println("Got here c != null");              gc.Log(serviceName, "found role OracleService");
    }    }catch(Exception e){}  }*/
  
  public int callback(GroupspaceEvent ge) {    
    // System.out.println("Got into the CALLBACK function atleast");    
    gc.Log(serviceName, "Received event: " + ge.getEventDescription());
    receivedEvent(ge);
    return GroupspaceCallback.CONTINUE;
  }
  
  public String roleName() {
    return roleName;
  }
  
  public void finalize(){
    System.out.println("BYE PEOPLE");
    shutdown(); 
  }

  public void receivedEvent(GroupspaceEvent ge) {
    Cacheable retVal = new Cacheable();
    String status = null;    String eventDescription = ge.getEventDescription();    
    Cacheable object = (Cacheable)ge.getDbo();
    
    if((eventDescription == "Oracle_WGCache_Put")||
       (eventDescription == "Metaparser_WGCache_Put")) {
      if (object.key == null) {
        if(object.data == null){
          status = "Fault";
        }
        System.out.println("Put without key");
        retVal.key = put(object.data,object.size);
        status = "PutKey";
      }
      else {
        put(object.key,object.data,object.size);        System.out.println("Put with key");
        return;
      }    
    } else if((eventDescription == "Oracle_WGCache_Get")||
	      (eventDescription =="Metaparser_WGCache_Get")){
      try {
        retVal.data = query(object.key);
        System.out.println("Get Hit");
        status = "Hit";
      } catch(WGCException m) {
        status = "Miss";
        System.out.println("Get Miss");
        System.err.println("Data Not Found[MISS]");
      }catch(Exception e){}
    }else {
      //status = "Fault";      System.err.println("USAGE: [Oracle_WGCache_Put] [Oracle_WGCache_Get]");
      return;
    }
    try {
      GroupspaceEvent gev = new GroupspaceEvent(retVal,
						"WGCache_Oracle_"+status,null,
						retVal,false);
      gc.groupspaceEvent(gev);
    } catch(PropertyVetoException e){ }  
  }
} 
