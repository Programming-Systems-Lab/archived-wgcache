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
import psl.wgcache.exception.*;
import java.io.*;
import java.util.*;import java.net.*;
import siena.*;

public class WGCSienaRuleEngineImpl implements Runnable, Notifiable, WGCRuleEngine {  Siena si = null;  WorkgroupManagerImpl wgm = null;  
  public WGCSienaRuleEngineImpl(WorkgroupManagerImpl wgm) {
    this.wgm = wgm;
        String master = "senp://canal.psl.columbia.edu:4321";    HierarchicalDispatcher hd = new HierarchicalDispatcher();     try {       hd.setMaster(master);      System.out.println("WgCache Siena master is " + master);    }     catch(siena.InvalidSenderException e) {      e.printStackTrace();     } 
    catch(IOException ex) {
      ex.printStackTrace();      }  
    Thread t = new Thread(new WGCSienaRuleEngineImpl(hd)); 
    t.start();  }
    private WGCSienaRuleEngineImpl(Siena si) {
    this.si = si; 
  }
  
  public void run() {
    Filter f = new Filter();
    /* MUST CHANGE HERE */    f.addConstraint("source", "psl.oracle.OracleSienaInterface");     f.addConstraint("type", "get");        /* END MUST CHANGE HERE */    try {      si.subscribe(f, this);     }
    catch(siena.SienaException se) {       se.printStackTrace();    }    catch(Exception e){      e.printStackTrace();
    }        System.out.println("WGCSienaRuleEngineImpl subscribed to " + f); 
  }    public void what_do_i_do_next(String instigator, Cacheable dataHandle) {
    try {
      generatePutEvent(instigator, InetAddress.getLocalHost().getHostName(), dataHandle);
    }catch(Exception e) {
      e.printStackTrace();
    }  }  
  private void generatePutEvent(String instigator, String hostname, Cacheable dataHandle) {
    KXNotification kxnotify = new KXNotification();        log("Generating SmartEvent");        Hashtable metaData = new Hashtable();       metaData.put("hostname", hostname);    metaData.put("Instigator", instigator);    metaData.put("DataHandle",(String)dataHandle.key);    
    Notification  n = EDInputNotification("psl.wgcache.impl.WGCSienaInterface",123455,metaData);
    try {
      si.publish(n);
    }
    catch(siena.SienaException se) {
      se.printStackTrace();
    } 
  }    public void notify(Notification n) {    Cacheable dataHandle = new Cacheable();
    AttributeValue av = n.getAttribute("Action");    if (av != null) {      String action = (av.stringValue()); 
      if (action.equals("Push")) {
        dataHandle.key = n.getAttribute("DataHandle");        if(n.getAttribute("Workgroup").booleanValue()) {
          try {
            wgm.pushToWorkGroup(n.getAttribute("Instigator").stringValue(),dataHandle,n.getAttribute("TargetName").stringValue());
          } catch(Exception e){}        }        else {          if(n.getAttribute("Module").booleanValue()) {
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