package psl.wgcache.impl;

/** Copyright (c) 2000: The Trustees of Columbia University and the City of New York.
 * All Rights Reserved.
 *  * Name:        PersonalCacheModuleImpl.java 
 * 
 * Description: This class is the interface/client to the WorkgroupCache.
 *              It represents the personal cache module which is the local cache. 
 *              It provides methods to put,query the cache and to join,leave,list workgroups.  
 *              In addition it allows methods like pushToWorkgroup, pullFrom,pushTo  
 *              that can be used when a workgroup criteria is applied. 
 * Construction:Creates a new Cache Module with the specified roleName.
 * 
 * 
 * @author  Alpa
 *  
 */
 
import psl.wgcache.exception.*;
import psl.wgcache.roles.*;
import psl.wgcache.impl.manager.*;
import psl.wgcache.support.*;
import java.util.*;
import java.io.*;
import java.rmi.*;
import java.rmi.server.*;import java.net.*;

public class PersonalCacheModuleImpl implements PersonalCacheModule {
  protected Criteria crit;
  protected Vector wgVec;
  protected CacheService cache = null;
  private String roleName;
  private static WorkGroupManager wgm;
  //private static Manager manager = null; 
  private String filename;  
  private Properties prop; 
  private String url;
  //private Workgroup wg = null;  
  private String wgName = null;    /**
   * Constructs a new Cache Module with the specified roleName.
   */      
  public PersonalCacheModuleImpl(String roleName){
    this.filename = "WorkgroupServer.conf";  //hardcoded bad needs to be passed as an argument....
    this.prop = new Properties();
    this.wgVec = new Vector();
    this.roleName = roleName;
    try {
      this.url = InetAddress.getLocalHost().getHostName()+ "/"+ roleName;
      RMI_PCMimpl rpcmi = new RMI_PCMimpl(this);      log(url);    }catch (Exception e) {}
        try {
      this.cache = new CacheService(roleName);
    }catch(Exception e){ 
      e.printStackTrace();
      System.out.println("CACHE NOT CREATED");
    }
    System.setSecurityManager(new java.rmi.RMISecurityManager());
    try {
      FileInputStream in = new FileInputStream(filename);
      prop.load(in);
      String managerUrl = prop.getProperty("workgroupserver.url"); 
      if(managerUrl == null)        
        managerUrl = "rmi://disco.cs.columbia.edu/manager";
      //System.out.println("URL IS:" + url);
      //manager = new ManagerImpl();      
      this.wgm = (WorkGroupManager)Naming.lookup(managerUrl);    
    }catch (Exception e) {
       System.out.println("ERROR: Could not connect to the server");      
       e.printStackTrace();
     }
  }  
  public String getName() {                    
    return roleName;
  }
  /**
   * caches the specified data.
   * 
   * @param    Cacheable object to be cached.
   * 
   * @return   key for the data cached if not provided, for future references.   
   * 
   */  
  
  public Object put(Cacheable x) { 
    Object retVal = null;
    if ((x.data != null) && (x.size > 0 )) {
      if (x.key != null)
        cache.put(x.key,x.data,x.size);
      else {
         retVal = cache.put(x.data,x.size);
      }
    }else {
      log("Data or Size fields are Null for");  
      // checks if the data and size parameters fro the Cacheable are null and break
    }
    return retVal;
  }
  /**
   * Tests if the specified object is present in the Cache.
   * 
   * @return      The object if found.
   * 
   * @exception   WGCException if not found.
   * 
   */
  
  public Cacheable query(Object queryData)throws WGCException {    
    Cacheable retVal = new Cacheable();
    if(queryData != null) { 
      if(queryData instanceof Cacheable){ 
        log("Is an instance of Cacheable");
	//log("about to blow off");
	Object temp = cache.query(((Cacheable)queryData).key);
	//log("JUST CHECKING WITHIN THE LOOP");
	//log("in the finally wonder if it comes here");
	if(temp !=null) {
	  retVal.data = temp;
	  retVal.key = ((Cacheable)queryData).key;
          retVal.size = ((Cacheable)queryData).size;				
	}
	else {
          log("was null in this pcm");
	  retVal = null;
	}
        return retVal;
   }
   else {
    System.out.println("Not an instance of Cacheable");
    Object temp = cache.query(queryData);
    System.out.println("just checking");
    log("JUST CHECKING WITHIN THE LOOP");
    if(temp !=null) {
      retVal.data = temp;
      retVal.key = queryData;
    }
    else
      retVal = null;
    return retVal;
   } 
   }
  else {
    log("queryData provided is null");
  }
  return retVal;
 }

  public void pushToWorkgroup(Cacheable toBePushed){		
    for(int i = 0; i < wgVec.size(); i++) {
      String wgName = (String)wgVec.elementAt(i);
      log("THE workgroup being pushed to is :" + wgName);      try {        wgm.pushTo(new RequestTrace(),toBePushed,wgName);      }catch (Exception e) {
        System.out.println("ERROR: Server connection problem in pushToWorkgroup");        e.printStackTrace();      }
    }
  }
		
  /**
   * Method to implement the shared cache.
   * This method is called when the criteria is applied.
   * It first checks for the data in it's own cache.
   * If not found it searchs through the shared cache and then notifies it's workgroups about it.
   *  
   * 
   */

  public Cacheable pullFrom(RequestTrace trace, Object queryData) {
    Cacheable result = null;
    trace.addHop(roleName);
   // 1. check cache
    try {
      result = this.query(queryData);
    } 
    catch (WGCException w) {}    finally {      if(result!= null) {
        log("found \"" + queryData + "\" in cache");    
        // we found it in our cache, but we still need to notify our
        // workgroups that we accessed the document       
        for(int i = 0; i < wgVec.size(); i++) {
          wgName = (String)wgVec.elementAt(i);             try {            wgm.accessNotify(trace, queryData, wgName);          }          catch (Exception e) {            System.out.println("ERROR: Server connection problem in pullFrom");
            e.printStackTrace();          }
        }
      } else {
        //System.out.println("pulling from the workgroup");        // 2. pull from shared cache         
        int i;        log("Number of workgroups joined are:" + wgVec.size());
        for(i = 0; i < wgVec.size(); i++) {
          wgName = (String)wgVec.elementAt(i); 
          log("Workgroup name in the for loop:" + wgName);          try {            result = wgm.pullFrom(trace, queryData, wgName);          } catch (Exception e) {
            System.out.println("ERROR: Server connection problem in pullFrom");
            e.printStackTrace();          }          if(result != null) {            log("got \"" + queryData + "\" from workgroup " + wgName);
            break;
          }
        }
         // Notify all wg's of the pull, even if we receive it from the
         // first one queried
         for(i++; i < wgVec.size(); i++) {
           wgName = (String)wgVec.elementAt(i);
           try {             wgm.accessNotify(trace, queryData, wgName);           }           catch (Exception e) {
            System.out.println("ERROR: Server connection problem in pullFrom");
            e.printStackTrace();          }         }      }      if(result == null) {        // we could get from outside here, say other oracle or an oracle serverbut we'll just assume that if        // the shared cache couldn't find it, we won't either      }      return result;    }  }   
  /**
   * Method to implement the shared cache.
   * This method is called when the criteria is applied.
   * The specified data is saved by the module.
   */  

  public String getURL() {    
    return this.url;
  }  

  public void printJoinedWorkgroupNames() {
    String[] names = new String[wgVec.size()];
    int i = 0;
    int j=0;
    log("Workgroups joined :");
    for(Enumeration e = wgVec.elements(); e.hasMoreElements();){
      names[i++] = ((String)e.nextElement());
      j = i-1;
      System.out.println(names[j]);
    }
  }  

  public void pushTo(RequestTrace trace, Cacheable x) {
    if(trace.getLastHop() != null){      if((!trace.getLastHop().equals(roleName))&&(x.key != null)) {        trace.addHop(roleName);        cache.put(x.key,x.data,x.size);      }    }    else {      trace.addHop(roleName);      cache.put(x.key,x.data,x.size);    }  }  
  /**
   * Prints the workgroups to which the module is subscribed.
   *
   */

  public void printAllWorkgroupNames() {
    log("WORKGROUPS:");  
    try {
      String names[] = wgm.getWorkgroupNames();
      for (int i=0; i < names.length;i++) {
        System.out.println(names[i]); 
     }
    } catch (Exception e) {
      System.out.println("ERROR: Server connection problem in printAllWorkgroups");
      e.printStackTrace();
    }
  }
  
  /**
   * Creates a new Workgroup with the specified name
   * 
   * @exception   WGCException if the workgroup already exists.
   */

  public void createWorkgroup(String wgName) throws WGCException {
    /* if (manager == null) 
    System.out.println("MANAGER IS NULL");*/
    try {
      wgm.newWorkgroup(wgName);
      wgm.joinWorkgroup(wgName, url,roleName);
      //wg.addmember(this);
      //wgm.setWorkgroup(wg);      
      wgVec.addElement(wgName);      
    } catch (Exception e) {
        System.out.println("ERROR: Server connection problem in createWorkgroup");
        e.printStackTrace();    
     }
  }  

  /**
   * Joins the specified workgroup.
   * 
   * @exception   if no such workgroup exists.
   * 
   */

  public void joinWorkgroup(String wgName) throws WGCException  {
    try {
      wgm.joinWorkgroup(wgName,url,roleName);
    }catch (Exception e){
      System.out.println("ERROR: Server connection problem in joinWorkgroup");
      e.printStackTrace();    
    }
    wgVec.addElement(wgName);
  }
  
  /**
   * Leaves the specified workgroup. 
   * 
   */

  public void leaveWorkgroup(String wgName)  {    
    for(Enumeration enum = wgVec.elements();
      enum.hasMoreElements();) {
      String tmpWg = (String)enum.nextElement();
      if(wgName.equals(tmpWg)) {
        try {
          wgm.leaveWorkgroup(wgName, roleName);
          wgVec.removeElement(wgName);  
        } catch (Exception e) {
            System.out.println("ERROR: Server connection problem in leaveWorkgroup");
            e.printStackTrace();
        }
        break;
      }
    }
  }  

  protected void leaveAllWorkgroups() {
    log("leaving all workgroups");        
    while(wgVec.size() > 0) {      
      wgName = (String)wgVec.elementAt(0);      
      leaveWorkgroup(wgName);
    }
  }
  
  /*public Workgroup[] workgroups() {
    Workgroup[] retval = new Workgroup[wgVec.size()];
    wgVec.copyInto(retval);
    return retval;
  }*/

  public Criteria getCriteria() {
    return crit;
  }

  public void setCriteria(Criteria crit) {
    this.crit = crit;
  }

  protected void log(String mesg) {
    System.out.println("PCM " + getName() + ": " + mesg);
  }

  protected void finalize() {
    //leaveAllWorkgroups();
    //manager.removePCM(this);
  }
}

