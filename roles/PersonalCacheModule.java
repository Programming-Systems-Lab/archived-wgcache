package psl.wgcache.roles;

import java.util.*;
import psl.wgcache.*;import psl.wgcache.exception.*;import psl.wgcache.impl.manager.*;import psl.wgcache.support.*;
import psl.wgcache.impl.*;
import java.rmi.*;

public interface PersonalCacheModule extends Module {	public void createWorkgroup(String wgName) throws WGCException;
  public void joinWorkgroup(String wgName) throws WGCException;  public void leaveWorkgroup(String wgName);
  public Criteria getCriteria();
  public void setCriteria(Criteria crit);
}
