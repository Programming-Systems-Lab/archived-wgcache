package psl.wgcache;

/** Copyright (c) 2000: The Trustees of Columbia University and the City of New York.
 * All Rights Reserved.
 * 
 *  Name:        WorkgroupManagerImpl.java
 *  Description: This class implements the workgroup manager.
 *               Currently there is only one manager for the workgroups.
 *               It maintains a list of all the workgroups and provides methods to
 *               create new workgroup, delete workgroups.
 * 
 * Construction: A manager that has its own cache which is the shared cache and a list of the workgroups.
 * 
 * 
 * 
 * @author  Alpa
 *  
 */

 /*****************************************************************
  wgm.newWorkgroup(wgName);
  wgm.pushTo(toBePushed,wgName);
  wgm.getWorkgroupNames()
  wgm.accessNotify(queryTag, wgName);
  wgm.joinWorkgroup(wgName, url);
  wgm.leaveWorkgroup(wgName, url);
  wgm.pullFrom(queryData, wgName);
  *****************************************************************/

import java.io.*;
import java.util.*;
import java.rmi.*;
import java.rmi.server.*;

public class WorkgroupManagerImpl extends UnicastRemoteObject implements java.io.Serializable,WorkgroupManager {
  private static Hashtable workgroups = new Hashtable();
  private CacheService cache;  
  private WGCSienaRuleEngineImpl wgRule;

  public WorkgroupManagerImpl() throws RemoteException {   
    try {
      cache = new CacheService("WorkgroupManager");
			cache.clear(); // gskc: COMMENT, clear cache data from previous instantiations
      wgRule = new WGCSienaRuleEngineImpl(this);
    } catch(Exception e) {
      e.printStackTrace();
    }
  }
  
  private boolean IS_VISUAL = false;
  private WGC_Manager_Visual wgcmv = null;
  public void setVisual(WGC_Manager_Visual _wgcmv) {
    IS_VISUAL = true;
    wgcmv = _wgcmv;
  }

  public void newWorkgroup(String wgName) throws WGCException,RemoteException {
    log("creating workgroup " + wgName);
    if(workgroups.containsKey(wgName)) throw new WGCException("Workgroup already exists");    
    WorkgroupImpl wg = new WorkgroupImpl(wgName, this);    
    workgroups.put(wgName, wg);
    if (IS_VISUAL) wgcmv.workgroupAdded(wgName);
    return;
  }

  public void leaveWorkgroup(String wgName,String memName) throws WGCException,RemoteException {
    log("removing" + memName + "from the workgroup " +wgName);
    if(!workgroups.containsKey(wgName)) throw new NoSuchModuleException(wgName);
    Workgroup wg = getWorkgroup(wgName);    
    wg.removeMember(memName);
    return;
  }

  public void pushToWorkGroup(String dataSrc, Cacheable toBePushed,String wgName) throws WGCException,RemoteException{
    log("pushing to workgroup "+wgName);
    if(!workgroups.containsKey(wgName)) throw new NoSuchModuleException(wgName);
    Workgroup wg = getWorkgroup(wgName);    
    wg.pushToWorkgroup(dataSrc,toBePushed);
  }

  public void pushToModule(String dataSrc, Cacheable toBePushed,String memName) throws WGCException {
    log("Entered pushToModule: " + dataSrc + " -> " + memName);
    Workgroup target = null;
    String wgName = null;    

    // log("enumeration: " + workgroups.size());
    Enumeration e = workgroups.elements();    while (e.hasMoreElements()) {
      Workgroup tmpTarget = (Workgroup) e.nextElement();
      log("workgroup: " + tmpTarget.getName());
      if (tmpTarget.containsMember(dataSrc) && tmpTarget.containsMember(memName)) {
        log("Found wg w/ both dataSrc & memName: " + tmpTarget.getName());        target = tmpTarget;
        break;
      }
    }

    if (target == null) {
      log("NULL");
      return;
    }     

    wgName = target.getName();
    log("wgName: " + wgName);
    if(!workgroups.containsKey(wgName)) throw new NoSuchModuleException(wgName);
    Workgroup wg = getWorkgroup(wgName);       
    wg.pushToMember(dataSrc,toBePushed,memName);
  }

  public void accessNotify(String pcmName, Cacheable data) throws RemoteException {
    if (!notPushedEarlier(pcmName, data)) {
      wgRule.what_do_i_do_next(pcmName, data);      // log("what_do_i_do_next returned: ");
    } else {
    }
  }

  public boolean notPushedEarlier(String pcmName, Cacheable data) {
    Object res = null; 
    try {
      res = cache.query(data.key);
    } catch (Exception e) { }
		log("and res is: " + res);
    if (res == null) {
      cache.put(data.key, data.data, data.size);
      return false;
    } else {
      return true;
    }
  }

  /*
  public void accessNotify(RequestTrace trace, Object data, String wgName) throws WGCException,RemoteException {
    log("notifying workgroup " +wgName);
    if(!workgroups.containsKey(wgName)) throw new NoSuchModuleException(wgName);
    Workgroup wg = getWorkgroup(wgName);
    wg.accessNotify(trace,data);
  }
  */

  public void joinWorkgroup(String wgName, String url, String memName) throws WGCException,RemoteException {
    log("adding " + memName + " to the workgroup " +wgName);
    if(!workgroups.containsKey(wgName)) throw new NoSuchModuleException(wgName);
    Workgroup wg = getWorkgroup(wgName);
    workgroups.put(wgName,wg);
    wg.addMember(url, memName);
    if (IS_VISUAL) wgcmv.memberJoined(wgName, memName);
  }

  public void deleteWorkgroup(String wgName) throws WGCException, RemoteException {
    log("deleting workgroup" +wgName);
    if(!workgroups.containsKey(wgName)) throw new NoSuchModuleException(wgName);
    Workgroup wg = getWorkgroup(wgName);
    wg.removeAll();
    workgroups.remove(wgName);
  }

  private Workgroup getWorkgroup(String wgName) throws WGCException {
    log("getting workgroup " + wgName);
    if(!workgroups.containsKey(wgName)) throw new NoSuchModuleException(wgName);
    return (Workgroup)workgroups.get(wgName);
  }

  public Enumeration getWorkgroups() throws RemoteException {
    return workgroups.elements();
  }

  public String[] getWorkgroupNames() throws RemoteException {
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

  public Cacheable pullFrom(Object queryData, String wgName)throws WGCException,RemoteException {
    log("pulling from workgroup" +wgName);
    if(!workgroups.containsKey(wgName)) throw new NoSuchModuleException(wgName);
    Workgroup wg = getWorkgroup(wgName);
    return (wg.pullFrom(queryData));
  }

  /*public synchronized void setWorkgroup(Workgroup frmClient) throws RemoteException {
    Workgroup tempwg = null;
    tempwg = (Workgroup)workgroups.get(frmClient.getName());
    if (tempwg == null) {
      log("Something really wrong the client workgroup is not known to the server");
      return;
    }
    if (!tempwg.compareTo(frmClient)) {
      workgroups.remove(tempwg.getName());
      workgroups.put(frmClient.getName(),frmClient);
      log("Workgroup" + tempwg.getName() + "was updated");          
    }else 
      log("Workgroup" + tempwg.getName() + "was not updated");
  }

  public Workgroup isModified(Workgroup fromClient)throws RemoteException {
    Workgroup tempwg = null;
    tempwg = (Workgroup)workgroups.get(fromClient.getName());
    if (tempwg == null) {
      log("Something really wrong the client workgroup is not known to the server");
      return null;      
    }
    if (!tempwg.compareTo(fromClient)) {
      log("Workgroup" + tempwg.getName() + "was updated");          
    }else 
      log("Workgroup" + tempwg.getName() + "was not updated");
    return tempwg;
  }*/

  
  public Cacheable query(Object queryTag)throws WGCException,RemoteException {
    Cacheable retVal = new Cacheable();
    if(queryTag != null) { 
      if(queryTag instanceof Cacheable){ 
        retVal.data = cache.query(((Cacheable)queryTag).key);
        retVal.key = ((Cacheable)queryTag).key;
        retVal.size = ((Cacheable)queryTag).size;
      } else {
        retVal.data = cache.query(queryTag);
        retVal.key = queryTag;
      }
    } else {
      log("QueryTag provided is null");  
    }
    return retVal;
  }    public void addRule(String instigator, String target, boolean isModule) {
    wgRule.addRule(instigator, target, isModule);    if (IS_VISUAL) wgcmv.pushRuleAdded(instigator, target, isModule);
  }

  private void log(String mesg)  {
    System.out.println("WorkgroupManager: " + mesg);
  }
}
