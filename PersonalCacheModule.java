package psl.wgcache;

import java.util.*;
import java.rmi.*;

public interface PersonalCacheModule extends Module {
  public void createWorkgroup(String wgName) throws WGCException;
  public void joinWorkgroup(String wgName) throws WGCException;
  public void leaveWorkgroup(String wgName);
  public Criteria getCriteria();
  public void setCriteria(Criteria crit);
}
