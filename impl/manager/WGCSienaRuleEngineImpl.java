package psl.wgcache.impl.manager;/** Copyright (c) 2000: The Trustees of Columbia University and the City of New York.
 * All Rights Reserved.
 * 
 *  Name:        WGCSienaInterface.java
 *  *  Description: This class implements the Siena interface for the cache.
 *               Handle get and put events - an inner class to handle put events. *                 
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
import java.util.*;import java.net.*;
import siena.*;

public class WGCSienaRuleEngineImpl implements Runnable, Notifiable, WGCRuleEngine {  Siena si = null;  WorkgroupManagerImpl wgm = null;  
  public WGCSienaRuleEngineImpl(WorkgroupManagerImpl wgm) {
    this.wgm = wgm;
        String master = "senp://canal.psl.cs.columbia.edu:4321";    HierarchicalDispatcher hd = new HierarchicalDispatcher(); 
    si = hd;    try { 
      hd.setReceiver(new TCPPacketReceiver(9156));      hd.setMaster(master);      System.out.println("WgCache Siena master is " + master);    }     catch(siena.InvalidSenderException e) {      e.printStackTrace();     } 
    catch(IOException ex) {
      ex.printStackTrace();      }  
    Thread t = new Thread(this); 
    t.start();    Filter f = new Filter();
    f.addConstraint("source", "EventDistiller");     f.addConstraint("type", "FiredRule");    
    try {      si.subscribe(f, this);     }
    catch(siena.SienaException se) {       se.printStackTrace();    }    catch(Exception e){      e.printStackTrace();
    }        System.out.println("WGCSienaRuleEngineImpl subscribed to " + f); 
  }
    public void run() { }    public void what_do_i_do_next(String instigator, Cacheable dataHandle) {
    try {
      generatePutEvent(instigator, InetAddress.getLocalHost().getHostName(), dataHandle);
    } catch(Exception e) {
      e.printStackTrace();
    }  }  
  private void generatePutEvent(String instigator, String hostname, Cacheable dataHandle) {
    log("Generating SmartEvent");        Hashtable metaData = new Hashtable();
    int SrcId = 235323;   
    metaData.put("type", new siena.AttributeValue("WGCRule"));    metaData.put("hostname", new siena.AttributeValue(hostname));    metaData.put("Instigator", new siena.AttributeValue(instigator));
    metaData.put("DataHandle", new siena.AttributeValue((String) dataHandle.key));
        Notification n = KXNotification.EDInputNotification("psl.wgcache.impl.WGCSienaInterface",SrcId,metaData);
    try {      si.publish(n);
    } catch(siena.SienaException se) {
      se.printStackTrace();
    } 
  }    public void notify(Notification n) {    Cacheable dataHandle = new Cacheable();
    AttributeValue av = n.getAttribute("Action");    if (av != null) {      String action = (av.stringValue()); 
      if (action.equals("Push")) {
        dataHandle.key = n.getAttribute("DataHandle");        if(n.getAttribute("Target").stringValue().equals("Workgroup")) {
          try {
            wgm.pushToWorkGroup(n.getAttribute("Instigator").stringValue(),dataHandle,n.getAttribute("TargetName").stringValue());
          } catch(Exception e){}        }        else {          if(n.getAttribute("Target").stringValue().equals("Module")) {
            try {
              wgm.pushToModule(n.getAttribute("Instigator").stringValue(),dataHandle,n.getAttribute("TargetName").stringValue());            } catch(Exception e) {              e.printStackTrace();            }
          }
        }      }else if (action.equals("Pull")) {        dataHandle.key = n.getAttribute("DataHandle");
        try {
          wgm.pullFrom(dataHandle,n.getAttribute("TargetName").stringValue());
        } catch(Exception e){          e.printStackTrace();
        }      }
    }    else { 
      System.out.println("Error: Action is null.");     }      } 
    public void notify(Notification[] n) { }
    protected void log(String mesg) {
    System.out.println("WGCSienaInterface : " + mesg);
  }
}