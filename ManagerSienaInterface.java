package psl.wgcache;

/** Copyright (c) 2000: The Trustees of Columbia University and the City of New York.
 * All Rights Reserved.
 * 
 *  Name:        WGCSienaInterface.java
 * 
 *  Description: This class implements the Siena interface for the cache.
 *               Handle get and put events - an inner class to handle put events.
 *                 
 * 
 * Construction: A cache module.
 *              
 *   
 * @author  Alpa
 *  
 */

import java.io.*;
import siena.*;

public class ManagerSienaInterface implements Runnable, Notifiable {
  Siena si = null; 
  
  public ManagerSienaInterface(){
    String master = "senp://canal.psl.columbia.edu:4321";
    HierarchicalDispatcher hd = new HierarchicalDispatcher(); 
    try { 
      hd.setMaster(master);
      System.out.println("WgCache Siena master is " + master);
    } 
    catch(siena.InvalidSenderException e) {
      e.printStackTrace(); 
    } 
  catch(IOException ex) {
    ex.printStackTrace();  
  }  
    ManagerSienaInterface wgsi = new ManagerSienaInterface(hd); 
    Thread t = new Thread(wgsi); 
    t.start();
  }
  
  public ManagerSienaInterface(Siena si) {
   this.si = si; 
  }
  
  public void run(){
    Filter f = new Filter();
    f.addConstraint("source", "psl.oracle.OracleSienaInterface");
    f.addConstraint("type", "get");    
    try {
      si.subscribe(f, this); 
    }
    catch(siena.SienaException se) { 
      se.printStackTrace();
    }
    catch(Exception e){
      e.printStackTrace();
    }    
    System.out.println("ManagerSienaInterface subscribed to " + f); 
  }
  /*********************
   * 
hostname=strafebox
source=psl.metaparser.Metaparser
SrcID=3284502389
Push=true|false
Target=String (workgroup or client)
Datahandle=StringID
*/
  
  public void notify(Notification n) {
    AttributeValue av = n.getAttribute("Action");
    if (av != null) {
      String action = (av.stringValue()); 
      if (action == "Push") {
        av = n.getAttribute("");
      } else { 
        System.out.println("Error: query is null."); 
      }    
    } 
  }
  
  public void notify(Notification[] n) { }
  
  protected void log(String mesg) {
    System.out.println("ManagerSienaInterface : " + mesg);
  }
}
  
  
 /******************************************************
//PART OF THE OLD SIENAINTERFACE ....  
  public void testSendEvent(){
    System.out.println("GOT HERE");
    Filter f = new Filter();
    f.addConstraint("source", "psl.oracle.OracleSienaInterface");
    f.addConstraint("type", "get");
    if(si == null){    
      System.out.println("IT IS NULL1");
    }    
    try {
      si.subscribe(f, this); 
    }
    catch(siena.SienaException se) { 
      se.printStackTrace();
    }
    catch(Exception e){
      e.printStackTrace();
    }
    System.out.println("WGCache subscribed to " + f);    
    f.clear();
    f.addConstraint("type", "putRequest");
    try {
      si.subscribe(f, new HandlePutEvent(this));
    } catch (siena.SienaException se) {
      se.printStackTrace();
    }
    System.out.println(" subscribed to " + f);
  }  
  public void processPut(Object toPut){
    Cacheable putObj = (Cacheable)toPut;
    Notification  n = new Notification();
    String status = null;
    String retVal;
    if (putObj.key == null) {
      if(putObj.data == null){
        status = "Fault"; 
      } 
      System.out.println("Put without key");     
      retVal = (String) wg.put(putObj.data,putObj.size);
      status = "PutKey";
    }else {  
      wg.put(putObj.key,putObj.data,putObj.size);
      System.out.println("Put with key");
      status = "Success"; 
      return;
    }    
    n.putAttribute("source", "psl.wgcache.impl.WGCSienaInterface");
    n.putAttribute("type", "putResult"); 
    n.putAttribute("putResult",status); 
    n.putAttribute("putKey",retVal);   
    try {
      si.publish(n);
    }
    catch(siena.SienaException se) {
      se.printStackTrace();
    } 
  }  
  public void processGet(Object q) {
    Cacheable query = (Cacheable)q; 
    Notification  n = new Notification();
    String status = null;
    Cacheable retVal = new Cacheable(); 
    try {  
      retVal.data = wg.query(query.key); 
      System.out.println("Get Hit");
      status = "Hit";
      n.putAttribute("value", retVal.toString());
    } catch(WGCException m) { 
      status = "Miss";  
      System.out.println("Get Miss");    
      n.putAttribute("value", (String)null);
    }catch(Exception e){
      status = "Exception occured at WGCache: " + e; 
      n.putAttribute("value", (String)null); 
    }   
    n.putAttribute("source", "psl.wgcache.impl.WGCSienaInterface");
    n.putAttribute("type", "getResult");    n.putAttribute("getResult",status);   
    try {
      si.publish(n);
    }
    catch(siena.SienaException se) {
      se.printStackTrace();
    }
  }
  public void notify(Notification n) {
    AttributeValue av = n.getAttribute("get");
    if (av != null) {
      String q = (av.stringValue()); 
      System.out.println("WGCache got a get request " + q);
      processGet(q);  
    } else { 
      System.out.println("Error: query is null."); 
    }    
  }    public void notify(Notification[] no) {}
}


class HandlePutEvent implements Notifiable {
  WGCSienaInterface wgCache = null;  
  public HandlePutEvent(WGCSienaInterface wgCache) {
    this.wgCache = wgCache;
  }  
  public void notify(Notification n) {
    Cacheable toBeCached  = new Cacheable(); 
    AttributeValue avk = n.getAttribute("key");  
    AttributeValue avd = n.getAttribute("data");    
    if (avk != null && avd != null) {
      toBeCached.key = avk.stringValue();
      toBeCached.data = avd.stringValue();
      toBeCached.size = (avd.stringValue()).length(); 
      System.out.println(" got put key " + toBeCached.key + " with data as "+toBeCached.data);
      wgCache.processPut(toBeCached);
    } else if (avk == null && avd != null){
      toBeCached.data = avd.stringValue();
      toBeCached.size = (avd.stringValue()).length();
      wgCache.processPut(toBeCached);
      System.out.println(" got put request with data as (no key)"+toBeCached.data); 
    }else
      System.out.println(" Error: wgCache without putPayload"); 
  }
  public void notify(Notification[] n) { }
}

  public static void main(String[] args){ 
    String master = "senp://localhost:3137";
    if (args.length > 0) { 
      master = args[0];
    } 
    HierarchicalDispatcher hd = new HierarchicalDispatcher(); 
    try { 
      hd.setMaster(master);
      System.out.println("WgCache Siena master is " + master);
    } 
    catch(siena.InvalidSenderException e) {
      e.printStackTrace(); 
    } 
  catch(IOException ex) {
    ex.printStackTrace();  
  }  
  WGCSienaInterface wgsi = new WGCSienaInterface(hd); 
  Thread t = new Thread(wgsi); 
  t.start();
  }  
*/
