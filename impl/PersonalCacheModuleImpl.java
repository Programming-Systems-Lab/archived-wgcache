package psl.wgcache.impl;

import psl.wgcache.exception.*;
import psl.wgcache.roles.*;
import psl.wgcache.impl.manager.*;
import psl.wgcache.support.*;
import java.util.*;

/**
 * This class is the interface to the WorkgroupCache.
 * 
 * It provides methods to put,query the cache and to join,leave,list workgroups.
 * 
 * @author  Alpa
 *  
 */

public class PersonalCacheModuleImpl  implements PersonalCacheModule {
  protected Criteria crit;
  protected Vector wgVec;
  protected CacheService cache;
  private String roleName;
  private WorkgroupManagerImpl wgm;
  private Manager manager;

  /**
   * Constructs a new Cache Module with the specified roleName.
   */
  public PersonalCacheModuleImpl(String roleName){
    this.wgVec = new Vector();
    this.roleName = roleName;
    try {
     this.cache = new CacheService(roleName);
    }catch(Exception e){
      System.out.println("CACHE NOT CREATED");    
    }    
    this.manager = new Manager();    
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
      log("Data or Size fields are Null for");  // checks if the data and size parameters fro the Cacheable are null and break
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
  
  public Cacheable query(Object queryTag)throws WGCException {
    Cacheable retVal = new Cacheable();
    if(queryTag != null) { 
      if(queryTag instanceof Cacheable){ 
        retVal.data = cache.query(((Cacheable)queryTag).key);
        retVal.key = ((Cacheable)queryTag).key;
        retVal.size = ((Cacheable)queryTag).size;
      }
      else {
        retVal.data = cache.query(queryTag);
        retVal.key = queryTag;
      }
    }else {
      log("QueryTag provided is null");
    }
    return retVal;
  }
  /**
   * Method to implement the shared cache.
   * This method is called when the criteria is applied.
   * It first checks for the data in it's own cache.
   * If not found it searchs through the shared cache and then notifies it's workgroups about it.
   *  
   * 
   */
  public Cacheable pullFrom(RequestTrace trace, Object queryTag) {
    Cacheable result = new Cacheable();
    trace.addHop(roleName);
   
    // 1. check cache
    try {
      result = query(queryTag);
    }catch (WGCException w) {}
     finally {
       if(result!= null) {
         log("found \"" + queryTag + "\" in cache");    
         // we found it in our cache, but we still need to notify our
         // workgroups that we accessed the document
         Workgroup wg;
         for(int i = 0; i < wgVec.size(); i++) {
           wg = (Workgroup)wgVec.elementAt(i);
           wg.accessNotify(trace, queryTag);
         }
       } else {
         // 2. pull from shared cache
         Workgroup wg;
         int i;
         for(i = 0; i < wgVec.size(); i++) {
           wg = (Workgroup)wgVec.elementAt(i);
           result = wg.pullFrom(trace, queryTag);
           if(result != null) {
             log("got \"" + queryTag + "\" from workgroup " + wg.getName());
             break;
           }
         }
         // Notify all wg's of the pull, even if we receive it from the
         // first one queried
         for(i++; i < wgVec.size(); i++) {
           wg = (Workgroup)wgVec.elementAt(i);
           wg.accessNotify(trace, queryTag);
         }
       }
       if(result == null) {
         // we could get from outside here, say other oracle or an oracle serverbut we'll just assume that if
         // the shared cache couldn't find it, we won't either
       }
       return result;
     }
  } 
  /**
   * Method to implement the shared cache.
   * This method is called when the criteria is applied.
   * The specified data is saved by the module.
   */  
  
  public void printJoinedWorkgroupNames(){
    String[] names = new String[wgVec.size()];
    int i = 0;
    int j=0;
    log("Workgroups joined :");

    for(Enumeration e = wgVec.elements(); e.hasMoreElements();){
      names[i++] = ((Workgroup)e.nextElement()).getName();
      j = i-1;
      System.out.println(names[j]);
    }
  }
  
  public void pushTo(RequestTrace trace, Cacheable x)  {
    if((!trace.getLastHop().equals(roleName))&&(x.key != null)) {
      trace.addHop(roleName);
      cache.put(x.key,x.data,x.size);
    }
  }
  
  /**
   * Prints the workgroups to which the module is subscribed.
   *
   */
  public void printAllWorkgroupNames(){
    log("WORKGROUPS:");
    String names[] = wgm.getWorkgroupNames();
    for (int i=0; i < names.length;i++) {
      System.out.println(names[i]);
    }
  }
  
  /**
   * Creates a new Workgroup with the specified name
   * 
   * @exception   WGCException if the workgroup already exists.
   */
  public void createWorkgroup(String wgName) throws WGCException  {
    wgm = manager.getManager();
    Workgroup wg = wgm.newWorkgroup(wgName);
    wgVec.addElement(wg);    
  }
  
  /**
   * Joins the specified workgroup.
   * 
   * @exception   if no such workgroup exists.
   * 
   */
  public void joinWorkgroup(String wgName) throws WGCException  {
    wgm = manager.getManager();
    Workgroup wg = wgm.getWorkgroup(wgName);
    if(wg == null)
      throw new NoSuchModuleException("No such workgroup");
    joinWorkgroup(wg);
  }
  protected void joinWorkgroup(Workgroup wg)  {
    wg.addMember(this);
    wgVec.addElement(wg);
  }
  
  /**
   * Leaves the specified workgroup. 
   * 
   */
  public void leaveWorkgroup(String wgName)  {
    Workgroup wg;
    for(Enumeration e = wgVec.elements();e.hasMoreElements();) {
      wg = (Workgroup)e.nextElement();
      if(wgName.equals(wg.getName())) {
        leaveWorkgroup(wg);
        break;
      }
    }
  }
  
  protected void leaveWorkgroup(Workgroup wg)  {
    wg.removeMember(this);
    wgVec.removeElement(wg);
  }

  protected void leaveAllWorkgroups()  {
    log("leaving all workgroups");
    Workgroup wg;
    while(wgVec.size() > 0) {
      wg = (Workgroup)wgVec.elementAt(0);
      wg.removeMember(this);
      wgVec.removeElementAt(0);
    }
  }
  public Workgroup[] workgroups()  {
    Workgroup[] retval = new Workgroup[wgVec.size()];
    wgVec.copyInto(retval);
    return retval;
  }

  public Criteria getCriteria()  {
    return crit;
  }

  public void setCriteria(Criteria crit)  {
    this.crit = crit;
  }

  protected void log(String mesg)  {
    System.out.println("PCM " + getName() + ": " + mesg);
  }

  protected void finalize()  {
    leaveAllWorkgroups();
    //manager.removePCM(this);
  }
}

