package psl.wgcache.impl;
import java.io.*;
import siena.*;

class Oracle implements Runnable, Notifiable {
  public static final String me = "Oracle";
  Siena s = null;
  public Oracle(Siena s) {
    this.s = s;
  }  public static void main(String args[]) {      String master = "senp://localhost:3137";    if (args.length > 0) {      master = args[0];    }    
    HierarchicalDispatcher h = new HierarchicalDispatcher();
    try {
      h.setMaster(master);
      System.out.println(me + ": master is " + master);
    } catch (siena.InvalidSenderException ihe) {
      ihe.printStackTrace();
    }
  catch (IOException ioe) {
      ioe.printStackTrace();
    }
    Oracle m = new Oracle(h);
    Thread t = new Thread(m);
    t.start();
  }
  public void run() {
    Notification  n = new Notification();
    n.putAttribute("source", "psl.oracle.OracleSienaInterface");
    n.putAttribute("type", "putRequest");
    n.putAttribute("key","KEY1");    n.putAttribute("data","I am trying this for the first time");
    try{
      s.publish(n);
    }catch(siena.SienaException se) {
     se.printStackTrace();
   }
    Filter f = new Filter();
    f.addConstraint("source", "psl.wgcache.impl.WGCacheSienaInterface");
    try {
      s.subscribe(f,this);
    } catch (siena.SienaException se) {
      se.printStackTrace();
    }
    System.out.println(me + " subscribed to " + f);
  }

  public void notify(Notification n) {
    AttributeValue av = n.getAttribute("putResult");
    if (av != null) {
      String query = av.stringValue();
      System.out.println(me + " got query " + query);
    } else {
      System.out.println(me + " Error: queryresult without query");
    }
  }
  public void notify(Notification[] e) {}}
