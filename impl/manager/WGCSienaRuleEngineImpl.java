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
import java.io.*;import java.net.*;
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
  private void generatePutEvent(String instigator, String hostname, Cacheable dataHandle) {    log("Generating SmartEvent");
    Notification  n = new Notification();    n.putAttribute("source", "psl.wgcache.impl.WGCSienaInterface");
    n.putAttribute("SrcID","123455");    n.putAttribute("hostname", hostname);
    n.putAttribute("Instigator", instigator);         n.putAttribute("DataHandle",(String)dataHandle.key);
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