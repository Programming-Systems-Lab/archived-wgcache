package psl.wgcache.impl;

import java.io.*;
import siena.*;

// a rough implementation of Siena interface 
 /*TODO: add the same for a put request 
 *    :might need a general get and put
 *    :send the data received in case of hit
 */
  WGCacheSienaInterface wgCache = null;  
    this.wgCache = wgCache;
  }
  public void notify(Notification n) {
    Cacheable toBeCached  = new Cacheable();
    if (avk != null && avd != null) {
      toBeCached.data = avd.stringValue();
      toBeCached.size = (avd.stringValue()).length();
      wgCache.processPut(toBeCached);
    } else if (avk == null && avd != null){
      toBeCached.data = avd.stringValue();
      wgCache.processPut(toBeCached);
      System.out.println(" got put request with data as (no key)"+toBeCached.data);
      System.out.println(" Error: wgCache without putPayload");
  public void notify(Notification[] n) { }
}

public class WGCacheSienaInterface implements Runnable, Notifiable {
  
    this.si = si;
      wg = new CacheService("WGCacheSiena");
    }catch(Exception e){
      System.out.println("Could not create a cache");
  }
  public static void main(String[] args){ 
  public void run() {
    Filter f = new Filter();
    f.clear();
    f.addConstraint("type", "putRequest");
    try {
  }  
  public void processPut(Object toPut){
    Cacheable putObj = (Cacheable)toPut;
    String status = null;
    if (putObj.key == null) {
      retVal = (String) wg.put(putObj.data,putObj.size);
      status = "PutKey";
    }else { 
      System.out.println("Put with key");
      status = "Success";
    }    
    n.putAttribute("source", "psl.wgcache.impl.WGCacheSienaInterface");
    n.putAttribute("type", "putResult");
    try {
      si.publish(n);
    }
    catch(siena.SienaException se) {
      se.printStackTrace();
    }
  public void processGet(Object q) {
    String status = null;
    Cacheable retVal = new Cacheable();   
      status = "Hit";
      n.putAttribute("value", retVal.toString());
    }catch(Exception e){
      status = "Exception occured at WGCache: " + e;
    n.putAttribute("source", "psl.wgcache.impl.WGCacheSienaInterface");
    n.putAttribute("type", "getResult");
    try {
      si.publish(n);
    }
    catch(siena.SienaException se) {
      se.printStackTrace();
    }
  }
  public void notify(Notification n) {
    AttributeValue av = n.getAttribute("get");
  }
}