package psl.wgcache.impl.manager;

import psl.wgcache.exception.*;
import psl.wgcache.roles.*;
import psl.wgcache.support.*;
import psl.wgcache.impl.*;
import java.io.*;
import java.util.*;

public class WorkgroupImpl  implements Workgroup  {
  protected Vector memberVec; // Vector of PersonalCacheModule

  private String name;
  private WorkgroupManager manager;
  private Criteria crit;
  private History hist;
 
  public WorkgroupImpl(String name, WorkgroupManager manager)  {
    super();
    this.name = name;
    this.manager = manager;
    memberVec = new Vector();
    crit = new NopCriteria();
    hist = new History();
  }
  
  public Cacheable pullFrom(RequestTrace trace, Object cname)  {
    Cacheable result = new Cacheable();
    log("received a pullFrom request for \"" + cname + "\"");
    hist.addAccess(cname);

    // 1. check in shared cache
    try {
      result = manager.query(cname);
    }catch(WGCException w){}
     finally {
       if(result == null) {
         log("got from cache");
       }else {
         // maybe a rmi connection to get data from other oracle or something but for now
       }
       // Apply criteria
       CriteriaInfo critInfo = new CriteriaInfoImpl(this, result, trace,CriteriaInfo.VIA_PULL, hist);
       crit.apply(critInfo);
       return result;
     }
  }

  public void pushTo(RequestTrace trace, Cacheable x)  {
    log("received a pushed object: \"" + x.key + "\"");
    hist.addAccess(x.key);

    // Apply criteria
    CriteriaInfo critInfo = new CriteriaInfoImpl(this, x, trace,CriteriaInfo.VIA_PUSH, hist);
    crit.apply(critInfo);
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

