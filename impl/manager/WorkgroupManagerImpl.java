package psl.wgcache.impl.manager;

import psl.wgcache.*;
import psl.wgcache.exception.*;
import psl.wgcache.impl.*;
import psl.wgcache.roles.*;
import psl.groupspace.*;
import java.util.*;

public class WorkgroupManagerImpl implements WorkgroupManager {
  protected Hashtable workgroups;
  protected CacheService cache;
  
  public WorkgroupManagerImpl()  {
   workgroups = new Hashtable();
   try {
     cache = new CacheService("WorkGroupManager");
   }catch(Exception e){e.printStackTrace();}
  }
  
  public Workgroup newWorkgroup(String name) throws WGCException  {
    String wgName;
    wgName = name;
        
    if(workgroups.containsKey(wgName))
      throw new WGCException("Workgroup already exists");
    WorkgroupImpl wg = new WorkgroupImpl(wgName, this);
    log("creating workgroup " + wgName);
    workgroups.put(wgName, wg);
    return wg;
   }

  public void deleteWorkgroup(String name)  {
    workgroups.remove(name);
  }

  public Workgroup getWorkgroup(String wgName) throws WGCException  {
    log("getting workgroup " + wgName);
    if(!workgroups.containsKey(wgName))
      throw new NoSuchModuleException(wgName);
    return (Workgroup)workgroups.get(wgName);
  }
  
  public Enumeration getWorkgroups()  {
    return workgroups.elements();
  }

  public String[] getWorkgroupNames()  {
    String[] names = new String[workgroups.size()];
    int i = 0;
    int j=0;

    for(Enumeration e = workgroups.elements(); e.hasMoreElements();){
      names[i++] = ((Workgroup)e.nextElement()).getName();
      j = i-1;
      //System.out.println(names[j]);
    }
    
    return names;
  }  
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
  protected void log(String mesg)  {
    System.out.println("WorkgroupManager:" + mesg);
  }
}
