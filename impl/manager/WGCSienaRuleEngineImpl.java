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
import psl.wgcache.impl.*;
import psl.wgcache.roles.*;
import psl.wgcache.exception.*;
import java.io.*;
import java.util.*;
import siena.*;

public class WGCSienaRuleEngineImpl implements Runnable, Notifiable, WGCRuleEngine {
  HierarchicalDispatcher hd = null;
  public WGCSienaRuleEngineImpl(WorkgroupManagerImpl wgm) {
    this.wgm = wgm;
    
    si = hd;
      hd.setReceiver(new TCPPacketReceiver(9156));
    catch(IOException ex) {
      ex.printStackTrace();  
    Thread t = new Thread(this); 
    t.start();
    f.addConstraint("Source", "EventDistiller"); 
    try {
    catch(siena.SienaException se) { 
    }    
      public void run() {
        hd.shutdown();
      } });
  }
  
    try {
      generatePutEvent(instigator, InetAddress.getLocalHost().getHostName(), dataHandle);
    } catch(Exception e) {
      e.printStackTrace();
    }
  private void generatePutEvent(String instigator, String hostname, Cacheable dataHandle) {
    log("Generating SmartEvent");    
    int SrcId = 235323;
    metaData.put("type", new siena.AttributeValue("WGCRule"));
    metaData.put("DataHandle", new siena.AttributeValue((String) dataHandle.key));
    
    try {
    } catch(siena.SienaException se) {
      se.printStackTrace();
    } 
  }
    this.log("Received a notification\n" + n);
    AttributeValue av = n.getAttribute("Action");
      if (action.equals("Push")) {
        dataHandle.key = n.getAttribute("DataHandle").stringValue();
        log("attribute Target: " + n.getAttribute("Target").stringValue());
          try {
            wgm.pushToWorkGroup(n.getAttribute("Instigator").stringValue(),dataHandle,n.getAttribute("TargetName").stringValue());
          } catch(Exception e){}
          try {
            wgm.pushToModule(n.getAttribute("Instigator").stringValue(),dataHandle,n.getAttribute("TargetName").stringValue());
        }
        try {
          wgm.pullFrom(dataHandle,n.getAttribute("TargetName").stringValue());
        } catch(Exception e){
        }
    }
      System.out.println("Error: Action is null."); 
  
  
    System.out.println("WGCSienaInterface : " + mesg);
  }
}