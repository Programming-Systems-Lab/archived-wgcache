package psl.wgcache.impl.manager;
 * All Rights Reserved.
 * 
 *  Name:        WGCSienaInterface.java
 * 
 *               Handle get and put events - an inner class to handle put events.
 * 
 * Construction: A cache module.
 *              
 *   
 * @author  Alpa
 *  
 */
import psl.wgcache.exception.*;
import java.io.*;
import siena.*;

public class ManagerSienaInterface implements Runnable, Notifiable {
  public ManagerSienaInterface(){
  catch(IOException ex) {
    ManagerSienaInterface wgsi = new ManagerSienaInterface(hd); 
    Thread t = new Thread(wgsi); 
    t.start();
  
   this.si = si; 
  }
  
  public void run(){
    catch(siena.SienaException se) { 
    }    
  }
   * 
hostname=strafebox
source=psl.metaparser.Metaparser
SrcID=3284502389
Push=true|false
Target=String (workgroup or client)
Datahandle=StringID
*/
  
    AttributeValue av = n.getAttribute("Action");
      if (action == "Push") {
        av = n.getAttribute("");
      } else { 
        System.out.println("Error: query is null."); 
    } 
  }
  
    System.out.println("ManagerSienaInterface : " + mesg);
  }
}
 /******************************************************
//PART OF THE OLD SIENAINTERFACE ....  
  public void testSendEvent(){
    Filter f = new Filter();
    if(si == null){    
      System.out.println("IT IS NULL1");
    catch(siena.SienaException se) { 
    }
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
    n.putAttribute("source", "psl.wgcache.impl.WGCSienaInterface");
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
    } else { 
  }    public void notify(Notification[] no) {}
}

  WGCSienaInterface wgCache = null;  
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

  public static void main(String[] args){ 
  catch(IOException ex) {
*/