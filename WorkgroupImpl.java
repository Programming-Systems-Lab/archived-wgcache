package psl.wgcache;

/** Copyright (c) 2000: The Trustees of Columbia University and the City of New York.
 * All Rights Reserved.
 * 
 *  Name:        WorkgroupImpl.java
 * 
 *  Description: This class implements a Workgroup.
 *               It maintains a list of all the member personalcachemodules.
 *               Provides functions like pullFrom, pushTo to perform the actions
 *                 of a group criteria -applies citeria everytime these functions are called.
 *               Also provides functions to remove and add and other functions for the member cachemodules.
 *                
 *          
 * Construction: A Workgroup with specified name and a list of the members and a criteria for the workgroup.
 * 
 * 
 * TODO: need to maintain history of all the accesses --- not too sure till what depth and if required.
 *              
 *   
 * @author  Alpa
 *  
 */

/***********************************************************
 * wg.pullFrom(queryData);
 * wg.removeMember(url);
 * wg.pushTo(toBePushed);
 * wg.accessNotify(data);
 * wg.addMember(url);
 * wg.removeAll();
 ************************************************************/

import java.io.*;
import java.util.*;
import java.rmi.*;

public class WorkgroupImpl implements Workgroup,java.io.Serializable {
  protected Hashtable memberVec; // Vector of PersonalCacheModules
  private String name;
  private static WorkgroupManager manager;
  private String workgpMemURL;
  private RMI_PCM memPCM;
 
  public WorkgroupImpl(String name, WorkgroupManager manager)  {
    super();
    this.name = name;
    this.manager = manager;
    this.memberVec = new Hashtable();
  }  
  
  public Cacheable pullFrom(Object cname)  {
	  log("Workgroup name is:" + name);
		if(cname != null) { 
      if(cname instanceof Cacheable){ 
		  //log("Is an instance of Cacheable in the workgroup");
		  log("received a pullFrom request for \"" + ((Cacheable)cname).key + "\"");
	  } else
		  log("received a pullFrom request for \"" + cname + "\"");
		}
    //1. check with all the members of the workgroup.
		log("Size of the workgroup is:"+ memberVec.size());
		Cacheable result = null;
		if(memberVec.size() > 0){
      for(Enumeration e = memberVec.keys(); e.hasMoreElements();) {
        workgpMemURL = ((String) memberVec.get(e.nextElement()));
        log("Member URL: " + workgpMemURL);
        try {
           memPCM = (RMI_PCM) Naming.lookup(workgpMemURL);
        }
        catch (Exception ex) {
          System.out.println("ERROR: Server connection problem while pulling from " + workgpMemURL);
          ex.printStackTrace();
        }
				log("JUST CHECKING");				
				try {
					result = memPCM.query(cname);	
          if(result != null){
						log("Result from other pcm:" + result);
						break;
					}
				}
        catch(Exception ex){}
					// 2.check in shared cache
					//right now nothing is put in it so it is a miss always 
					// not too sure when would this be used
					/* try {
					result = manager.query(cname);
					}catch(WGCException w){}*/	
				finally {
				   if(result != null) {
				     log("got from cache");
						 log("THE RESULT:" + result.data);
				   }else {
						 continue;			     
				   }
				}
				// Apply criteria
				//CriteriaInfo critInfo = new CriteriaInfoImpl(this, result, trace,CriteriaInfo.VIA_PULL, hist);
				//crit.apply(critInfo);	
			}
		}
		if (result == null)
			// maybe a rmi connection to get data from other oracle or something but for now
			log("need to get it from somewhere else");
		return result;
	}
	
  public void pushToWorkgroup(String memDataSrc, Cacheable x)  {
    Cacheable toBePushed = null;
    log("received a pushed object: " + x.key );    				
    if(memberVec.size() > 0){
      String srcUrl = (String)memberVec.get(memDataSrc);
      try {
        memPCM = (RMI_PCM) Naming.lookup(srcUrl);
        toBePushed = memPCM.query(x.key);
      }catch(Exception e){
        e.printStackTrace();
      }
      
      if(toBePushed ==null) {
        log("data was NULL after query");
        return;     
      }
      
      for(Enumeration e = memberVec.keys(); e.hasMoreElements();) {
        
        String currKey = (String)e.nextElement();
        if(currKey.equalsIgnoreCase(memDataSrc))
          continue;
        
        workgpMemURL = ((String) memberVec.get(currKey));
        log("Member URL: " +workgpMemURL);
        try {
          RMI_PCM memPCM2 = (RMI_PCM) Naming.lookup(workgpMemURL);
          memPCM2.WGCPut(toBePushed);          System.out.println("Found the target");
        }
        catch (Exception ex) {
          System.out.println("ERROR: Server connection problem in pushToWorkgroup to: " + workgpMemURL);
          ex.printStackTrace();
        }
      } 
    }
  }
  
  public boolean containsMember(String pcmName){
    log("entered containsMember in " + this.getName() + " w/ " + pcmName);
    boolean retVal = this.memberVec.containsKey(pcmName);
    log("and returns: " + retVal);
    return(retVal);  
  }
  
  public void pushToMember (String memDataSrc, Cacheable x, String targetMember) {
    Cacheable toBePushed = null;
    log("received a pushed object: \"" + x.key + "\"");    	
    if(memberVec.size() > 0){
      log("DATA SOURCE: " + memDataSrc);
      String srcUrl = (String) memberVec.get(memDataSrc);
      try {
        memPCM = (RMI_PCM)Naming.lookup(srcUrl);
        toBePushed = memPCM.query(x.key);
      }
      catch (Exception ex) {
        System.out.println("ERROR: Server connection problem in pushToMember to: " + srcUrl);
        ex.printStackTrace();
      }
      if(toBePushed ==null) {        System.out.println("Data was null so returning ... ");
        return;            }
      workgpMemURL = ((String) memberVec.get(targetMember));
      log("Member URL: " +workgpMemURL);
      try {
        RMI_PCM memPCM2 = (RMI_PCM) Naming.lookup(workgpMemURL);
        memPCM2.WGCPut(toBePushed);
        System.out.println("Found the target in pushtomember");
      }
      catch (Exception ex) {
        System.out.println("ERROR: Server connection problem in pushToWorkgroup to: " + workgpMemURL);
        ex.printStackTrace();
      }
    }
  }
  
  public boolean compareTo (Workgroup fromClient) {
    if(fromClient == null) {
      if (this.memberVec.size() == 0)
        return true;
      else return false;
    }
    else
      return(this.memberVec.size() == fromClient.numMembers());
  }
  
  public String getName()  {
    return name;
  }

  public void setName(String name)  {
    this.name = name;
  }

  public void addMember(String memberUrl, String PCMName)  {
    //log("adding member --- " + "key: " + PCMName + ", value: " + memberUrl);
    memberVec.put(PCMName,memberUrl);    
  }

  public void removeMember(String PCMName)  {
    log("removing member " + PCMName);    
    if (PCMName!=null)
      memberVec.remove(PCMName);
  }

  public void removeAll()  {
    log("removing all members");
    String PCMName;
    for(Enumeration e = memberVec.keys(); e.hasMoreElements();) {
      PCMName = (String)e.nextElement();
      removeMember(PCMName);
    }
  }

/*  public PersonalCacheModule[] members()  {
    PersonalCacheModule[] retval = new PersonalCacheModule[memberVec.size()];
    memberVec.copyInto(retval);
    return retval;
  }*/

  public Criteria getCriteria()  {
  //  return crit;
    return null;
  }
  public int numMembers() {
    return this.memberVec.size();
  }

  public void setCriteria(Criteria crit)  {
    //this.crit = crit;
    log("Setting new cache criteria");
  }
  
  protected void log(String mesg)  {
    System.out.println("PCM " + getName() + ": " + mesg);
  }

  /*
  public void accessNotify(RequestTrace trace,Object name) {
    log("notified of access of \\"+ name + "\\ by " + trace.getLastHop());  
    hist.addAccess(name);
    CriteriaInfoImpl critInfo = new CriteriaInfoImpl(this,new Cacheable(),trace,CriteriaInfo.VIA_UNKNOWN,hist);
    crit.apply(critInfo);
  }
  */
  
  protected void finalize()  {
   removeAll();
  }
}
