package psl.wgcache;
import java.io.*;
import java.util.Properties;

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

public class WGCSienaInterface implements Runnable, Notifiable {
  Siena si = null; 
  HierarchicalDispatcher hd = null;
  
  public WGCSienaInterface(){
    Properties p = new Properties();
    try {
      FileInputStream fis = new FileInputStream(".siena_master");
      p.load(fis);
    } catch (IOException ioe) {
      System.out.println("Warning: can't open properties file; using default: senp://canal:4321");
      System.out.println(ioe);
    }
    String master = p.getProperty("sienaURL", "senp://canal.psl.cs.columbia.edu:4321");
    hd = new HierarchicalDispatcher(); 
    si = hd;
    try { 
      hd.setReceiver(new TCPPacketReceiver(9157));
      hd.setMaster(master);
      System.out.println("WgCache Siena master is " + master);
    } catch(siena.InvalidSenderException e) {
      e.printStackTrace(); 
    } catch(IOException ex) {
      ex.printStackTrace();  
    }
    Thread t = new Thread(this); 
    t.start();
    System.out.println("WGCSienaRuleEngineImpl subscribed to " + master); 
    Runtime.getRuntime().addShutdownHook(new Thread() {
      public void run() {
        hd.shutdown();
      }});
  }
  
  public WGCSienaInterface(Siena si) {
   this.si = si; 
  }
  
  public void run() {}
  
  public void generatePutEvent(String instigator, String hostname, Object dataHandle){
    log("Generating SmartEvent");
    Notification  n = new Notification();
    n.putAttribute("source", "psl.wgcache.impl.WGCSienaInterface");
    n.putAttribute("SrcID","123455");
    n.putAttribute("hostname", hostname);
    n.putAttribute("Instigator", instigator);     
    n.putAttribute("DataHandle",(String)dataHandle);
    try {
      si.publish(n);
    }
    catch(siena.SienaException se) {
      se.printStackTrace();
    } 
  }
  
  public void notify(Notification n) { }
  
  public void notify(Notification[] n) { }
  
  protected void log(String mesg) {
    System.out.println("WGCSienaInterface : " + mesg);
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
