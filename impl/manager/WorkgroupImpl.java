package psl.wgcache.impl.manager;

import psl.wgcache.exception.*;
import psl.wgcache.roles.*;
import psl.wgcache.support.*;
import psl.wgcache.impl.*;
import java.io.*;
import java.util.*;

public class WorkgroupImpl  implements Workgroup  {
  protected Vector memberVec; // Vector of PersonalCacheModules
  private String name;
  private WorkgroupManager manager;
  private Criteria crit;
  private History hist;
 
  public WorkgroupImpl(String name, WorkgroupManager manager)  {
    super();
    this.name = name;
    this.manager = manager;
    this.memberVec = new Vector();
    this.crit = new NopCriteria();
    this.hist = new History();
  }
  
  public Cacheable pullFrom(RequestTrace trace, Object cname)  {
		log("Workgroup name is:" +this.name);
		if(cname != null) { 
      if(cname instanceof Cacheable){ 
			//log("Is an instance of Cacheable in the workgroup");
				log("received a pullFrom request for \"" + ((Cacheable)cname).key + "\"");
			}else
				log("received a pullFrom request for \"" + cname + "\"");
			hist.addAccess(cname);
		}
    //1. check with all the members of the workgroup.
		log("Size of the workgroup is:"+ memberVec.size());
		Cacheable result = null;
		if(memberVec.size() > 0){
			for(int i = 0; i < memberVec.size(); i++) {
				PersonalCacheModuleImpl memPCM = (PersonalCacheModuleImpl)memberVec.elementAt(i);
				log("JUST CEHCKING");
				log("MEMBER PCM :"+ memPCM.getName());
				try {
					result = memPCM.query(cname);				
					if(result != null){
						log("Result from other pcm:" + result);
						break;
					}
				}
				catch(WGCException w){}
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
				CriteriaInfo critInfo = new CriteriaInfoImpl(this, result, trace,CriteriaInfo.VIA_PULL, hist);
				crit.apply(critInfo);	
			}
		}
		if (result == null)
			// maybe a rmi connection to get data from other oracle or something but for now
			log("need to get it from somewhere else");
		return result;
	}
	
  public void pushTo(RequestTrace trace, Cacheable x)  {
    log("received a pushed object: \"" + x.key + "\"");
    hist.addAccess(x.key);
    // Apply criteria
		Workgroup wg;
		for(int i = 0; i < this.memberVec.size(); i++){
			pcm = (PersonalCacheModule)memberVec.elementAt(i);
			CriteriaInfo critInfo = new CriteriaInfoImpl(this, x, pcm, trace,CriteriaInfo.VIA_PUSH, hist);
			crit.apply(critInfo);
		}
  }

  public String getName()  {
    return name;
  }

  public void setName(String name)  {
    this.name = name;
  }

  public void addMember(PersonalCacheModule member)  {
    log("adding member " + member.getName());
    memberVec.addElement(member);
  }

  public void removeMember(PersonalCacheModule member)  {
    log("removing member " + member.getName());
    //member.leaveWorkgroup(getName());
    memberVec.removeElement(member);
  }

  protected void removeAllMembers()  {
    log("removing all members");
    PersonalCacheModule pcm;
    for(Enumeration e = memberVec.elements(); e.hasMoreElements();) {
      pcm = (PersonalCacheModule)e.nextElement();
      removeMember(pcm);
    }
  }

  public PersonalCacheModule[] members()  {
    PersonalCacheModule[] retval = new PersonalCacheModule[memberVec.size()];
    memberVec.copyInto(retval);
    return retval;
  }

  public Criteria getCriteria()  {
    return crit;
  }

  public void setCriteria(Criteria crit)  {
    this.crit = crit;
    log("Setting new cache criteria");
  }
  
  protected void log(String mesg)  {
    System.out.println("PCM " + getName() + ": " + mesg);
  }

  public void accessNotify(RequestTrace trace,Object name) {
    log("notified of access of \\"+ name + "\\ by " + trace.getLastHop());  
    hist.addAccess(name);
    CriteriaInfoImpl critInfo = new CriteriaInfoImpl(this,new Cacheable(),trace,CriteriaInfo.VIA_UNKNOWN,hist);
    crit.apply(critInfo);
  }
  protected void finalize()  {
   removeAllMembers();
  }
}

