package psl.wgcache.impl;
import psl.wgcache.exception.*;
import java.io.*;
import siena.*;

// a rough implementation of Siena interface 
/*TODO: add the same for a put request 
 *    :might need a general get and put
 *    :send the data received in case of hit
 */
public class WGCacheSienaInterface implements Runnable, Notifiable {  Siena si = null;  CacheService wg;
    public WGCacheSienaInterface(Siena si) {
    this.si = si;    try {
      wg = new CacheService("WGCache");
    }catch(Exception e){
      System.out.println("Could not create a cache");    }    
  }
  public static void main(String[] args){     String master = "senp://localhost:3137";    if (args.length > 0) {      master = args[0];    }    HierarchicalDispatcher hd = new HierarchicalDispatcher();    try {      hd.setMaster(master);      System.out.println("WgCache Siena master is " + master);    }    catch(siena.InvalidHandlerException e) {      e.printStackTrace();    } catch(IOException ex) {      ex.printStackTrace();    }    WGCacheSienaInterface wgsi = new WGCacheSienaInterface(hd);    Thread t = new Thread(wgsi);    t.start();  }
  public void run() {
    Filter f = new Filter();    f.addConstraint("source", "psl.oracle.OracleSienaInterface");    f.addConstraint("type", "get");    try {      si.subscribe(f, this);    }catch(siena.SienaException se) {      se.printStackTrace();    }    System.out.println("WGCache subscribed to " + f);  }  public void processGet(Object q) {   //need to instantiate a cache module or maybe shared cache    //not too sure so assume wg
    Cacheable query = (Cacheable)q;   Notification  n = new Notification();   String status = null;   Cacheable retVal = new Cacheable();      try {
     retVal.data = wg.query(query.key);
     System.out.println("Get Hit");
     status = "Hit";     n.putAttribute("value",retVal.toString());
   } catch(WGCException m) {
     status = "Miss";
     System.out.println("Get Miss");       
      n.putAttribute("value","");
   }catch(Exception e){       status = "Exception occured at WGCache: " + e;       n.putAttribute("value","");
   }   
   n.putAttribute("source", "psl.wgcache.impl.WGCacheSienaInterface");   n.putAttribute("type", "getResult");   n.putAttribute("getResult",status);   
   try {     si.publish(n);   }   catch(siena.SienaException se) {     se.printStackTrace();   }  }  public void notify(Notification n) {    AttributeValue av = n.getAttribute("get");    if (av != null) {      String q = (av.stringValue());      System.out.println("WGCache got a get request " + q);      processGet(q);    } else {      System.out.println("Error: query is null.");     }    
  }  
  /*//OLD INTERFACE 
  public void receivedEvent(GroupspaceEvent ge) {   Cacheable retVal = new Cacheable();
   String status = null; 
   String eventDescription = ge.getEventDescription();    Cacheable object = (Cacheable)ge.getDbo();   if((eventDescription == "Oracle_WGCache_Put")||(eventDescription == "Metaparser_WGCache_Put")) {     if (object.key == null) {       if(object.data == null){         status = "Fault";             }       System.out.println("Put without key");       retVal.key = put(object.data,object.size);       status = "PutKey";    
     }else {       put(object.key,object.data,object.size);
       System.out.println("Put with key");       return;
     }   }else if((eventDescription == "Oracle_WGCache_Get")||(eventDescription =="Metaparser_WGCache_Get")){     try {       retVal.data = query(object.key);       System.out.println("Get Hit");       status = "Hit";     } catch(WGCException m) {       status = "Miss";       System.out.println("Get Miss");       System.err.println("Data Not Found[MISS]");     }catch(Exception e){}   }else {     //status = "Fault";      System.err.println("USAGE: [Oracle_WGCache_Put] [Oracle_WGCache_Get]");     return;   }   try{
    GroupspaceEvent gev = new GroupspaceEvent(retVal,"WGCache_Oracle_"+status,null,retVal,false);    gc.groupspaceEvent(gev);   }catch(PropertyVetoException e){}  
  }*/    public void notify(Notification[] no) {}
}









