package psl.wgcache.roles;

import java.util.*;
import psl.wgcache.*;import psl.wgcache.exception.*;import psl.wgcache.impl.manager.*;import psl.wgcache.support.*;import psl.wgcache.impl.*;

public interface Workgroup extends Module {
  public void setName(String name);  public void addMember(String memberUrl, String PCMName);  public void removeMember(String PCMName);  
  public void accessNotify(RequestTrace trace, Object name);  public boolean compareTo(Workgroup fromClient);  public Cacheable pullFrom(RequestTrace trace, Object cname);
  public int numMembers();
  public void removeAll();
  public void pushTo(RequestTrace trace, Cacheable x);  }
